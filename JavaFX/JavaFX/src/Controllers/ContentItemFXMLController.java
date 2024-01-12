package Controllers;

import static Controllers.CursistFXMLController.ErrorAlert;

import Java2Database.DataShare;
import Java2Database.DataBaseSQL;
import Objects.ContentItem;
import Objects.Status;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ContentItemFXMLController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTable();
        loadTableContentItem();
    }

    @FXML
    private TableView<ContentItem> ContentItemTableView;

    @FXML
    void ContentItemsAanmakenClicked(MouseEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/chooseContentItemDialog.fxml"));
            DialogPane pane = loader.load();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(pane);
            dialog.setTitle("Kies een soort ContentItem");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> clickedButton = dialog.showAndWait();

            if (clickedButton.isPresent() && clickedButton.get() == ButtonType.CANCEL) {
                loadTableContentItem();
            }

        } catch (IOException ex) {
            Logger.getLogger(ContentItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void ContentItemsAanpassenClicked(MouseEvent event) {
        if (DataShare.getInstance().getVersie() == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/updateWebcastDialog.fxml"));
                DialogPane pane = loader.load();

                DialogContentItemFXMLController updateContentItemController = loader.getController();

                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(pane);
                dialog.setTitle("Webcast aanpassen");
                dialog.getDialogPane().getButtonTypes().clear();
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

                Button applyButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.APPLY);
                applyButton.addEventFilter(ActionEvent.ACTION, ae -> {
                    if (!updateContentItemController.validateAndUpdateContentItem()) {
                        ae.consume();
                    }
                });

                Optional<ButtonType> clickedApply = dialog.showAndWait();

                if (clickedApply.isPresent() && clickedApply.get() == ButtonType.APPLY) {
                    loadTableContentItem();
                }

            } catch (IOException ex) {
                Logger.getLogger(ContentItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/updateModuleDialog.fxml"));
                DialogPane pane = loader.load();

                DialogContentItemFXMLController updateContentItemController = loader.getController();

                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(pane);
                dialog.setTitle("Module aanpassen");
                dialog.getDialogPane().getButtonTypes().clear();
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

                Optional<ButtonType> clickedApply = dialog.showAndWait();

                if (clickedApply.isPresent() && clickedApply.get() == ButtonType.APPLY) {
                    updateContentItemController.validateAndUpdateContentItem();
                    loadTableContentItem();
                }

            } catch (IOException ex) {
                Logger.getLogger(ContentItemFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    void ContentItemsVerwijderenClicked(MouseEvent event) {
        ContentItem selectedContentItem = ContentItemTableView.getSelectionModel().getSelectedItem();
        if (selectedContentItem != null && removeContentItemAlert(selectedContentItem.getContentItemId(), selectedContentItem.getTitel())) {
            try {
                String delete = "DELETE FROM contentItems WHERE contentItemId = '" + selectedContentItem.getContentItemId() + "'";
                DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), delete);
            } catch (SQLException ex) {
                // Behandel fouten hier
                Logger.getLogger(CursusFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            loadTableContentItem();
        }
    }

    private boolean removeContentItemAlert(int ContentItemId, String ContentItemTitel) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ContentItem Verwijderen!");
        alert.setHeaderText("Weet je zeker dat je het ContentItem met Id: " + ContentItemId + " en Titel: " + ContentItemTitel + " wilt verwijderen?");

        // Ja, Nee knoppen
        ButtonType buttonTypeYes = new ButtonType("Ja");
        ButtonType buttonTypeNo = new ButtonType("Nee");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        alert.getButtonTypes().add(ButtonType.CLOSE);

        Optional<ButtonType> result = alert.showAndWait();

        // Controleer het resultaat
        return result.isPresent() && result.get() == buttonTypeYes;
    }

    @FXML
    TableColumn<ContentItem, Integer> contentItemsIdColumn;
    @FXML
    TableColumn<ContentItem, String> contentItemsTitelColumn;
    @FXML
    TableColumn<ContentItem, LocalDate> contentItemsDatumColumn;
    @FXML
    TableColumn<ContentItem, String> contentItemsStatusColumn;

    ObservableList<ContentItem> observableContentItem;

    @FXML
    void rowClicked(MouseEvent event) {
        try {
            ContentItem clickedContentItem = ContentItemTableView.getSelectionModel().getSelectedItem();

            if (clickedContentItem != null) {
                ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT c.naamCursus,c.contentItemId,c.beschrijving,c.titel,c.datum,c.status, m.versie, m.naamContactPersoon, m.emailContactPersoon, m.volgNr, w.datumPublicatie, w.url, w.naamSpreker, w.organisatieSpreker, w.tijdsDuur FROM contentItems c LEFT JOIN Module m ON m.contentItemId = c.contentItemId LEFT JOIN Webcast w ON w.contentItemId = c.contentItemId WHERE c.contentItemId = '" + clickedContentItem.getContentItemId() + "'");

                if (rs.next()) {
                    DataShare.getInstance().setNaamCursus(rs.getString("naamCursus"));
                    DataShare.getInstance().setContentItemId(rs.getInt("contentItemId"));
                    DataShare.getInstance().setModuleBeschrijving(rs.getString("beschrijving"));
                    DataShare.getInstance().setTitel(rs.getString("titel"));
                    DataShare.getInstance().setDatum(LocalDate.parse(rs.getString("datum")));
                    DataShare.getInstance().setStatus(Status.valueOf(rs.getString("status").toUpperCase().strip()));
                    DataShare.getInstance().setVersie(rs.getString("versie"));
                    DataShare.getInstance().setNaamContactPersoon(rs.getString("naamContactPersoon"));
                    DataShare.getInstance().setEmailContactPersoon(rs.getString("emailContactPersoon"));
                    DataShare.getInstance().setVolgordeNr(rs.getShort("volgNr"));
                    if (rs.getString("datumPublicatie") != null) {
                        DataShare.getInstance().setDatumPublicatie(LocalDate.parse(rs.getString("datumPublicatie")));
                    }
                    DataShare.getInstance().setURL(rs.getString("url"));
                    DataShare.getInstance().setNaamSpreker(rs.getString("naamSpreker"));
                    DataShare.getInstance().setOrganisatieSpreker(rs.getString("organisatieSpreker"));
                    DataShare.getInstance().setTijdsduur(rs.getShort("tijdsDuur"));

                } else {
                    ErrorAlert("Dit ContentItem bestaat niet meer!", "Onbekend ContentItem");
                }
            }
        } catch (SQLException | DateTimeParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initTable() {
        observableContentItem = FXCollections.observableArrayList(); // Initialize the list
        contentItemsIdColumn.setCellValueFactory(new PropertyValueFactory<>("contentItemId"));
        contentItemsTitelColumn.setCellValueFactory(new PropertyValueFactory<>("titel"));
        contentItemsDatumColumn.setCellValueFactory(new PropertyValueFactory<>("datum"));
        contentItemsStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    void loadTableContentItem() {
        try {
            initTable();
            try ( ResultSet rs = DataBaseSQL.createConnection().prepareStatement("SELECT * FROM contentItems").executeQuery()) {
                while (rs.next()) {
                    ContentItem ContentItem = new ContentItem();
                    ContentItem.setContentItemId(rs.getInt("contentItemId"));
                    ContentItem.setTitel(rs.getString("titel"));
                    ContentItem.setDatum(LocalDate.parse(rs.getString("datum")));
                    ContentItem.setStatus(rs.getString("status"));
                    observableContentItem.add(ContentItem);

                }
            }
            ContentItemTableView.setItems(observableContentItem);
            ContentItemTableView.refresh();

        } catch (SQLException ex) {
            Logger.getLogger(InschrijvenFXMLController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void ContentItemsBackClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/homeScreenAdmin.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
