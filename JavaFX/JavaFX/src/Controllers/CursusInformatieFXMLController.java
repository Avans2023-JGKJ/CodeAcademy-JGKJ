package Controllers;

import Java2Database.DataShare;

import Java2Database.DataBaseSQL;
import Validatie.Error;
import Objects.ContentItem;
import Objects.Cursist;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import Controllers.CursistFXMLController;
import Controllers.DialogCursistFXMLController;
import java.util.Random;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CursusInformatieFXMLController implements Initializable {

    @FXML
    private TableColumn<ContentItem, String> ContentItemBeschrjivingColumn;

    @FXML
    private TableColumn<ContentItem, String> ContentItemTitelColumn;

    @FXML
    private TableColumn<ContentItem, String> ContentItemVersieColumn;

    @FXML
    private TableColumn<ContentItem, Short> ContentItemPercentageColumn;

    @FXML
    private TableView<ContentItem> ContentItemsCursus;

    @FXML
    private Label LabelCursusName;

    @FXML
    private Label LabelPercentage;

    @FXML
    private ProgressBar ProgressBarCursus;

    private ObservableList<ContentItem> observableContentItems;

    private Error Error = new Error();

    private Random rand = new Random();

    private ContentItem clickedContentItem;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            LabelCursusName.setText("Cursus: " + DataShare.getInstance().getNaamCursus());
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "(SELECT inschrijfId FROM Inschrijven WHERE email = '" + DataShare.getInstance().getCursistEmail() + "' AND naamCursus = '" + DataShare.getInstance().getNaamCursus() + "')");
            rs.next();
            DataShare.getInstance().setInschrijfId(rs.getInt("inschrijfId"));
            loadTableContentItems();

//        LoadDataCursus();
        } catch (SQLException ex) {
            Logger.getLogger(CursusInformatieFXMLController.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    private void initTable() {
        observableContentItems = FXCollections.observableArrayList();
        ContentItemTitelColumn.setCellValueFactory(new PropertyValueFactory<>("titel"));
        ContentItemVersieColumn.setCellValueFactory(new PropertyValueFactory<>("versie"));
        ContentItemBeschrjivingColumn.setCellValueFactory(new PropertyValueFactory<>("beschrijving"));
        ContentItemPercentageColumn.setCellValueFactory(new PropertyValueFactory<>("percentage"));
    }

    private void loadTableContentItems() {
        try {
            initTable();
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT v.contentItemId ,v.voortgangsPercentage, c.titel, c.beschrijving, m.versie FROM "
                    + "contentItems c "
                    + "JOIN Voortgang v ON v.contentItemId = c.contentItemId "
                    + "LEFT JOIN Module m ON m.contentitemId = c.contentItemId "
                    + "LEFT JOIN Webcast w ON w.contentitemId = c.contentItemId "
                    + "WHERE c.naamCursus = '" + DataShare.getInstance().getNaamCursus() + "' "
                    + "AND v.inschrijfId IN (SELECT inschrijfId FROM Inschrijven WHERE email = '" + DataShare.getInstance().getCursistEmail() + "') "
                    + "ORDER BY m.volgNr");
            while (rs.next()) {
                ContentItem ContentItem = new ContentItem();
                ContentItem.setTitel(rs.getString("titel"));
                ContentItem.setVersie(rs.getString("versie"));
                ContentItem.setBeschrijving(rs.getString("beschrijving"));
                ContentItem.setPercentage(rs.getShort("voortgangsPercentage"));
                ContentItem.setContentItemId(Integer.valueOf(rs.getString("contentItemId")));
                observableContentItems.add(ContentItem);
            }
            refreshTotalProgress();
            ContentItemsCursus.setItems(observableContentItems);
            ContentItemsCursus.refresh();

        } catch (SQLException ex) {
            Logger.getLogger(CursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void LoadDataCursus() {

        double progress = 0.0;
        try ( ResultSet rs = DataBaseSQL.createConnection().prepareStatement("SELECT totaalVoortgang from Inschrijven WHERE email = '" + DataShare.getInstance().getCursistEmail() + "' AND naamCursus = '" + DataShare.getInstance().getNaamCursus() + "';").executeQuery()) {
            while (rs.next()) {
                progress = Double.valueOf(rs.getDouble("totaalVoortgang") / 100);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ProgressBarCursus.setProgress(progress);
        LabelPercentage.setText(String.valueOf((float) progress / 100 + "%"));

    }

    private void refreshTotalProgress() {
        try {
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT COUNT(*) AS row, SUM(v.voortgangsPercentage) AS totalPerc  FROM "
                    + "contentItems c "
                    + "JOIN Voortgang v ON v.contentItemId = c.contentItemId "
                    + "LEFT JOIN Module m ON m.contentitemId = c.contentItemId "
                    + "LEFT JOIN Webcast w ON w.contentitemId = c.contentItemId "
                    + "WHERE c.naamCursus = '" + DataShare.getInstance().getNaamCursus() + "' "
                    + "AND v.inschrijfId IN (SELECT inschrijfId FROM Inschrijven WHERE email = '" + DataShare.getInstance().getCursistEmail() + "')");
            rs.next();
            float TotaalPercentage = 0;
            if (rs.getInt("row") != 0) {
                TotaalPercentage = rs.getInt("totalPerc") / (rs.getInt("row"));
            }

            DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), "UPDATE Inschrijven SET totaalVoortgang = '" + Short.valueOf((short) TotaalPercentage) + "' WHERE inschrijfId = '" + DataShare.getInstance().getInschrijfId() + "'");
            ProgressBarCursus.setProgress(TotaalPercentage / 100);
            LabelPercentage.setText(String.valueOf((float) TotaalPercentage + "%"));

        } catch (SQLException ex) {
            Logger.getLogger(CursusInformatieFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void rowClicked(MouseEvent event) {
        try {
            clickedContentItem = ContentItemsCursus.getSelectionModel().getSelectedItem();
            if (clickedContentItem != null) {
                if (clickedContentItem.getVersie() == null || clickedContentItem.getVersie().isEmpty()) {
                    try {
                        Error.ContentItemBekeken("Webcast", 100);
                        DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), "UPDATE Voortgang SET voortgangsPercentage = '100' "
                                + "WHERE naamCursus = '" + DataShare.getInstance().getNaamCursus() + "' "
                                + "AND contentItemId = '" + clickedContentItem.getContentItemId() + "' "
                                + "AND inschrijfId = '" + DataShare.getInstance().getInschrijfId() + "'");
                    } catch (SQLException ex) {
                        Logger.getLogger(CursusInformatieFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    try {
                        int random = rand.nextInt(25) + 20;
                        Error.ContentItemBekeken("Module", random);
                        if (clickedContentItem.getPercentage() + random <= 100) {
                            random = random + clickedContentItem.getPercentage();
                        } else if (clickedContentItem.getPercentage() + random > 100) {
                            random = 100;
                        }
                        DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), "UPDATE Voortgang SET voortgangsPercentage = '" + random + "' "
                                + "WHERE naamCursus = '" + DataShare.getInstance().getNaamCursus() + "' "
                                + "AND contentItemId = '" + clickedContentItem.getContentItemId() + "' "
                                + "AND inschrijfId = '" + DataShare.getInstance().getInschrijfId() + "'");
                    } catch (SQLException ex) {
                        Logger.getLogger(CursusInformatieFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } finally {
            loadTableContentItems();
            refreshTotalProgress();
        }
    }

    @FXML
    void CursusInformatieBackClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/homeScreenCursist.fxml"));
        Object root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene((Parent) root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void CursusInformatieLogoClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/homeScreenCursist.fxml"));
        Object root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene((Parent) root);
        stage.setScene(scene);
        stage.show();
    }

}
