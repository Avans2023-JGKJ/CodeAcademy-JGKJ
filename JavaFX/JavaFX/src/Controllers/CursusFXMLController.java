package Controllers;
import Java2Database.DataShare;

import Java2Database.DataShare;
import Java2Database.DataBaseSQL;
import Objects.Cursist;
import Objects.Cursus;
import Objects.Niveau;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.sql.Connection;
import java.time.format.DateTimeParseException;
import static Controllers.CursistFXMLController.ErrorAlert;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.input.MouseEvent;

public class CursusFXMLController implements Initializable {

    @FXML
    private TableView<Cursus> CursusTableView;

    @FXML
    private TableColumn<Cursus, String> naamCursusCursusColumn;

    @FXML
    private TableColumn<Cursus, Short> aantalContentItemsCursusColumn;

    @FXML
    private TableColumn<Cursus, String> onderwerpCursusColumn;

    @FXML
    private TableColumn<Cursus, String> introductieTekstCursusColumn;

    @FXML
    private TableColumn<Cursus, Niveau> niveauCursusColumn;

    @FXML
    private ObservableList<Cursus> observableCursus;

    @FXML
    private Menu CursusAanmakenButton;

    @FXML
    private Menu CursusAanpassenButton;

    @FXML
    private Menu CursusVerwijderenButton;

    @FXML
    private static Connection cursusDbConnection;

    private void initTable() {
        observableCursus = FXCollections.observableArrayList();
        naamCursusCursusColumn.setCellValueFactory(new PropertyValueFactory<>("naamCursus"));
        aantalContentItemsCursusColumn.setCellValueFactory(new PropertyValueFactory<>("aantalContentItems"));
        onderwerpCursusColumn.setCellValueFactory(new PropertyValueFactory<>("onderwerp"));
        introductieTekstCursusColumn.setCellValueFactory(new PropertyValueFactory<>("introductieTekst"));
        niveauCursusColumn.setCellValueFactory(new PropertyValueFactory<>("niveau"));
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTable();
        loadTableCursus();
        cursusDbConnection = DataBaseSQL.createConnection(cursusDbConnection);
    }

    public void loadTableCursus() {
        try {
            initTable();
            try ( ResultSet rs = DataBaseSQL.createConnection().prepareStatement("SELECT * FROM Cursus").executeQuery()) {
                while (rs.next()) {
                    Cursus cursus = new Cursus();
                    cursus.setNaamCursus(rs.getString("naamCursus"));
                    cursus.setAantalContentItems((short) rs.getInt("aantalContentItems"));
                    cursus.setOnderwerp(rs.getString("onderwerp"));
                    cursus.setIntroductieTekst(rs.getString("introductieTekst"));

                    // zorgt ervoor dat de string uit de database overeenkomt met de enum-constanten
                    String niveauString = rs.getString("niveau");
                    try {
                        Niveau niveau = Niveau.valueOf(niveauString.toUpperCase());
                        cursus.setNiveau(niveau);
                    } catch (IllegalArgumentException e) {
                        Logger.getLogger(CursusFXMLController.class.getName()).log(Level.SEVERE,
                                "Niveuwaarde uit de database komt niet overeen met enige enum-constanten " + niveauString, e);
                        continue; // toevoegen van deze cursus (aan de lijst) overslaan
                    }

                    observableCursus.add(cursus);
                }
            }

            CursusTableView.setItems(observableCursus);
            CursusTableView.refresh();

        } catch (SQLException ex) {
            Logger.getLogger(CursusFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void CursusAanmakenClicked(MouseEvent event) {
        DataShare.getInstance().resetCursus();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/createCursusDialog.fxml"));
            DialogPane pane = loader.load();

            DialogCursusFXMLController createCursusController = loader.getController();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(pane);

            dialog.getDialogPane().getButtonTypes().clear();
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

            Button applyButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.APPLY);
            applyButton.addEventFilter(ActionEvent.ACTION, ae -> {
                if (!createCursusController.validateAndCreateCursus()) {
                    ae.consume();
                }
            });

            Optional<ButtonType> clickedButton = dialog.showAndWait();

            if (clickedButton.isPresent() && clickedButton.get() == ButtonType.APPLY) {
                loadTableCursus();
            }

            dialog.setTitle("Cursus aanmaken");

        } catch (IOException ex) {
            Logger.getLogger(CursusFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

@FXML
void CursusAanpassenClicked(MouseEvent event) {
    System.out.println("CursusAanpassenClicked");
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/updateCursusDialog.fxml"));
        DialogPane pane = loader.load();

        DialogCursusFXMLController updateCursusController = loader.getController();

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setDialogPane(pane);

        dialog.getDialogPane().getButtonTypes().clear();
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.FINISH, ButtonType.CANCEL);

        Button finishButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.FINISH);
        finishButton.addEventFilter(ActionEvent.ACTION, ae -> {
            if (!updateCursusController.FinishButtonUpdateCursusClicked()) {
                ae.consume();
            }
        });

        Optional<ButtonType> clickedButton = dialog.showAndWait();

        if (clickedButton.isPresent() && clickedButton.get() == ButtonType.FINISH) {
            loadTableCursus();
        }

        dialog.setTitle("Cursus aanpassen");

    } catch (IOException ex) {
        Logger.getLogger(CursusFXMLController.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    @FXML
    void CursusVerwijderenClicked(MouseEvent event) {
        Cursus selectedCursus = CursusTableView.getSelectionModel().getSelectedItem();
        if (selectedCursus != null && removeCursusAlert(selectedCursus.getNaamCursus())) {
            try {
                String delete = "DELETE FROM Cursus WHERE naamCursus = '" + selectedCursus.getNaamCursus() + "'";
                DataBaseSQL.sendCommand(DataBaseSQL.createConnection(cursusDbConnection), delete);
            } catch (SQLException ex) {
                // Behandel fouten hier
                Logger.getLogger(CursusFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            loadTableCursus();
        }
    }

    private boolean removeCursusAlert(String cursusNaam) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cursus Verwijderen!");
        alert.setHeaderText("Weet je zeker dat je de cursus met naam: " + cursusNaam + " wilt verwijderen?");

        // Ja, Nee knoppen
        ButtonType buttonTypeYes = new ButtonType("Ja");
        ButtonType buttonTypeNo = new ButtonType("Nee");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo,ButtonType.CANCEL);

        Optional<ButtonType> result = alert.showAndWait();

        // Controleer het resultaat
        return result.isPresent() && result.get() == buttonTypeYes;
    }

    @FXML
    void rowClicked(MouseEvent event) {
        try {
            Cursus clickedCursus = CursusTableView.getSelectionModel().getSelectedItem();

            if (clickedCursus != null) {
                ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT * FROM Cursus WHERE naamCursus = '" + clickedCursus.getNaamCursus() + "'");

                if (rs.next()) {
                    DataShare.getInstance().setNaamCursus(rs.getString("naamCursus"));
                    DataShare.getInstance().setAantalContentItems(rs.getShort("aantalContentItems"));
                    DataShare.getInstance().setOnderwerp(rs.getString("onderwerp"));
                    DataShare.getInstance().setIntroductieTekst(rs.getString("introductieTekst"));
                    DataShare.getInstance().setNiveau(Niveau.valueOf(rs.getString("niveau").toUpperCase()));
                } else {
                    ErrorAlert("Deze cursus bestaat niet meer!", "Onbekende cursist");
                }
            }
        } catch (SQLException | DateTimeParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void CursusBackClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/homeScreenAdmin.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
