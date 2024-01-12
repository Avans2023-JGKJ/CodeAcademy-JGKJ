package Controllers;

import Java2Database.DataShare;
import Java2Database.DataBaseSQL;
import Validatie.Error;
import Objects.Cursist;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CursistFXMLController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Error Error = new Error();
    @FXML
    private TableView<Cursist> CursistTableView;

    @FXML
    private TableColumn<Cursist, String> naamCursistColumn;

    @FXML
    private TableColumn<Cursist, LocalDate> geboorteDatumCursistColumn;

    @FXML
    private TableColumn<Cursist, String> geslachtCursistColumn;

    @FXML
    private TableColumn<Cursist, String> postCodeCursistColumn;

    private ObservableList<Cursist> observableCursist;

    //Initialize wordt aangeroepen bij het inladen van de pagina
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTable();
        loadTableCursist();
    }

    //Deze methode bepaalt de kolommen van de tableview
    private void initTable() {
        observableCursist = FXCollections.observableArrayList();
        naamCursistColumn.setCellValueFactory(new PropertyValueFactory<>("naam"));
        geboorteDatumCursistColumn.setCellValueFactory(new PropertyValueFactory<>("geboorteDatum"));
        geslachtCursistColumn.setCellValueFactory(new PropertyValueFactory<>("geslacht"));
        postCodeCursistColumn.setCellValueFactory(new PropertyValueFactory<>("postCode"));
    }

    //Deze methode laad de tableview met de gewenste data
    public void loadTableCursist() {
        try {
            initTable();
            try ( ResultSet rs = DataBaseSQL.createConnection().prepareStatement("SELECT naam, geboorteDatum, geslacht, postCode, email FROM Cursist").executeQuery()) {
                while (rs.next()) {
                    Cursist cursist = new Cursist();
                    cursist.setNaam(rs.getString("naam"));
                    cursist.setGeboorteDatum(LocalDate.parse(rs.getString("geboorteDatum")));
                    char[] c = rs.getString("geslacht").toCharArray();
                    cursist.setGeslacht(c[0]);
                    cursist.setPostCode(rs.getString("postCode"));
                    cursist.setEmail(rs.getString("email"));

                    observableCursist.add(cursist);
                }
            }

            CursistTableView.setItems(observableCursist);
            CursistTableView.refresh();

        } catch (SQLException ex) {
            Logger.getLogger(CursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Deze methode wordt gebruikt voor het inladen van de Pop up om een cursist aan te passen
    @FXML
    void CursistAanpassenClicked(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/updateCursistDialog.fxml"));
            DialogPane pane = loader.load();

            DialogCursistFXMLController updateCursistController = loader.getController();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(pane);
            dialog.setTitle("Cursist aanpassen");

            dialog.getDialogPane().getButtonTypes().clear();
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

            Button applyButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.APPLY);
            applyButton.addEventFilter(ActionEvent.ACTION, ae -> {
                if (!updateCursistController.ValidateAndUpdateCursist()) {
                    ae.consume();
                }
            });

            Optional<ButtonType> clickedApply = dialog.showAndWait();

            if (clickedApply.isPresent() && clickedApply.get() == ButtonType.APPLY) {
                loadTableCursist(); // Herlaad table
            }

        } catch (IOException ex) {
            Logger.getLogger(CursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Deze methode wordt gebruikt voor het inladen van de Pop up om een contentitem aan te maken
    @FXML
    void CursistAanmakenClicked(MouseEvent event) {
        DataShare.getInstance().resetCursist();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/createCursistDialog.fxml"));
            DialogPane pane = loader.load();

            DialogCursistFXMLController createCursistController = loader.getController();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(pane);
            dialog.setTitle("Cursist toevoegen");

            dialog.getDialogPane().getButtonTypes().clear();
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.FINISH, ButtonType.CANCEL);

            Button applyButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.FINISH);
            applyButton.addEventFilter(ActionEvent.ACTION, ae -> {
                if (!createCursistController.ValidateAndCreateCursist()) {
                    ae.consume();
                }
            });

            Optional<ButtonType> clickedFinish = dialog.showAndWait();

            if (clickedFinish.isPresent() && clickedFinish.get() == ButtonType.FINISH) {
                loadTableCursist(); // Reload the table after update
            }

        } catch (IOException ex) {
            Logger.getLogger(CursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Deze methode verwijdert de data van een geselecteerde rij in de tableview
    @FXML
    void CursistVerwijderenClicked(MouseEvent event) {
        if (Error.removeCursistAlert(DataShare.getInstance().getCursistEmail())) {
            try {

                String delete = "DELETE FROM Cursist WHERE email = '" + DataShare.getInstance().getCursistEmail() + "'";
                DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), delete);
            } catch (SQLException ex) {
                //Alert NIET GEVONDEN OF NIET VOLTOOID
                Logger.getLogger(CursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        loadTableCursist();
    }

    //De terugknop voor de pagina
    @FXML
    void CursistBackClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/homeScreenAdmin.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Deze methode haalt de data van een geselecteerde rij op
    @FXML
    void rowClicked(MouseEvent event) {
        try {
            Cursist clickedCursist = CursistTableView.getSelectionModel().getSelectedItem();

            if (clickedCursist != null) {
                ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT * FROM Cursist WHERE email = '" + clickedCursist.getEmail() + "'");

                if (rs.next()) {
                    DataShare.getInstance().setCursistEmail(rs.getString("email"));
                    DataShare.getInstance().setCursistNaam(rs.getString("naam"));
                    DataShare.getInstance().setCursistGeboorteDatum(LocalDate.parse(rs.getString("geboorteDatum")));
                    DataShare.getInstance().setCursistGeslacht(rs.getString("geslacht").charAt(0));
                    DataShare.getInstance().setCursistPostCode(rs.getString("postCode"));
                    DataShare.getInstance().setCursistHuisnummer(rs.getString("huisNummer"));
                    DataShare.getInstance().setCursistWoonPlaats(rs.getString("woonPlaats"));
                    DataShare.getInstance().setCursistLandCode(rs.getString("landCode"));
                } else {
                    ErrorAlert("Deze cursist bestaat niet meer!", "Onbekende cursist");
                }
            }
        } catch (SQLException | DateTimeParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Deze methode geeft een waarschuwing bij het verwijderen van data
    boolean removeAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Cursist Verwijderen!");
        alert.setHeaderText("Weet je zeker dat je Cursist met email: " + DataShare.getInstance().getCursistEmail() + "?");

        // Yes, No knoppen
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, ButtonType.CLOSE);

        Optional<ButtonType> result = alert.showAndWait();

        // Check the result
        return result.isPresent() && result.get() == buttonTypeYes;
    }

    //Deze methode configureert de datum naar een leesbare String
    public static LocalDate parseDate(String dateString) {

        String[] formats = {"yyyy-MM-dd", "yyyy-dd-MM", "dd-MM-yyyy", "MM-dd-yyyy"};
        String[] dateParts = dateString.split("[^\\d]+");
        if (dateParts.length < 3) {
            ErrorAlert("Er mist een Jaar, Maand of Dag!", "Geboortedatum incorrect!");
            return null;
        }

        for (String format : formats) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
                LocalDate date;
                if (Integer.parseInt(dateParts[0]) > 12) {
                    date = LocalDate.parse(dateString, formatter);
                } else {
                    int year = Integer.parseInt(dateParts[2]);
                    int month;
                    int day;
                    if (Integer.parseInt(dateParts[1]) > 12) {
                        month = Integer.parseInt(dateParts[0]);
                        day = Integer.parseInt(dateParts[1]);
                    } else {
                        month = Integer.parseInt(dateParts[1]);
                        day = Integer.parseInt(dateParts[0]);
                    }
                    String formattedDate = String.format("%04d-%02d-%02d", year, month, day);
                    date = LocalDate.parse(formattedDate, formatter);
                }
                return date;
            } catch (DateTimeParseException e) {
                // niks doen. dit hoort niet eens te gebeuren.
            }
        }

        ErrorAlert("Je geboortedatum klopt niet helemaal!", "Geboortedatum incorrect!");
        return null;
    }

    //Deze methode geeft een error waarschuwing voor als iets wordt afgevangen door een restrictie
    public static void ErrorAlert(String message, String header) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
