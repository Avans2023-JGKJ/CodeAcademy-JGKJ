package Controllers;

import Java2Database.DataShare;

import Java2Database.DataBaseSQL;
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
    private TableView<ContentItem> ContentItemsCursus;

    @FXML
    private Label LabelCursusName;

    @FXML
    private Label LabelPercentage;

    @FXML
    private ProgressBar ProgressBarCursus;

    private ObservableList<ContentItem> observableContentItems;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LoadDataCursus();
        loadTableContentItems();
    }

    private void initTable() {
        observableContentItems = FXCollections.observableArrayList();
        ContentItemTitelColumn.setCellValueFactory(new PropertyValueFactory<>("titel"));
        ContentItemVersieColumn.setCellValueFactory(new PropertyValueFactory<>("versie"));
        ContentItemBeschrjivingColumn.setCellValueFactory(new PropertyValueFactory<>("beschrijving"));
    }

    public void loadTableContentItems()  {
        try {
            initTable();
            System.out.println("1");
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT c.titel, c.beschrijving, m.versie FROM contentItems c JOIN Module m ON m.contentItemId = c.contentItemId WHERE c.naamCursus = '" + DataShare.getInstance().getNaamCursus() + "';");
            while (rs.next()) {
                System.out.println("2");
                ContentItem ContentItem = new ContentItem();
                ContentItem.setTitel(rs.getString("titel"));
                ContentItem.setVersie(rs.getString("versie"));
                ContentItem.setBeschrijving(rs.getString("beschrijving"));
                observableContentItems.add(ContentItem);
            }

            System.out.println("3");
            ContentItemsCursus.setItems(observableContentItems);
            ContentItemsCursus.refresh();

        } catch (SQLException ex) {
            Logger.getLogger(CursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void LoadDataCursus() {
        LabelCursusName.setText("Cursus: " + DataShare.getInstance().getNaamCursus());
        double progress = 0.0;
        try ( ResultSet rs = DataBaseSQL.createConnection().prepareStatement("SELECT totaalVoortgang from Inschrijven WHERE email = '" + DataShare.getInstance().getCursistEmail() + "' AND naamCursus = '" + DataShare.getInstance().getNaamCursus() + "';").executeQuery()) {
            while (rs.next()) {
                progress = Double.valueOf(rs.getDouble("totaalVoortgang") / 100);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ProgressBarCursus.setProgress(progress);
        LabelPercentage.setText(String.valueOf((int) (progress * 100)) + "%");

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
