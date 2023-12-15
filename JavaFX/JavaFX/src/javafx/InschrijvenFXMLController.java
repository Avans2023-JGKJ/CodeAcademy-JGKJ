package javafx;

import Java2Database.DataBaseSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date; 
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import Objects.Inschrijven;
import java.sql.Connection;
import java.time.format.DateTimeParseException;
import javafx.scene.control.DatePicker;



public class InschrijvenFXMLController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadTableInschrijven();
    }

    @FXML
    private TableView<Inschrijven> InschrijvenTableView;

    
    @FXML
    void InschrijvenAanmakenClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("createInschrijvenDialog.fxml"));
            DialogPane dialogPane = loader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Nieuwe Inschrijving");

            Optional<ButtonType> clickedButton = dialog.showAndWait();
            if (clickedButton.isPresent() && clickedButton.get() == ButtonType.FINISH) {
                TextField emailTextField = (TextField) dialogPane.lookup("#emailTextFieldId");
                TextField cursusNaamTextField = (TextField) dialogPane.lookup("#cursusNaamTextFieldId");
                DatePicker inschrijvenDatumPicker = (DatePicker) dialogPane.lookup("#inschrijvenDatumPickerId");
                TextField inschrijfIdTextField = (TextField) dialogPane.lookup("#inschrijfIdTextFieldId");
                TextField totaalVoortgangTextField = (TextField) dialogPane.lookup("#totaalVoortgangTextFieldId");

                String email = emailTextField.getText();
                String cursusNaam = cursusNaamTextField.getText();
                LocalDate inschrijvenDatum = inschrijvenDatumPicker.getValue();
                int inschrijfId = Integer.parseInt(inschrijfIdTextField.getText());
                float totaalVoortgang = Float.parseFloat(totaalVoortgangTextField.getText());

                Inschrijven newInschrijving = new Inschrijven(email, cursusNaam, inschrijvenDatum, inschrijfId);
                newInschrijving.setTotaalVoortgang(totaalVoortgang);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (NumberFormatException ex) {
        }
    }


        @FXML
        void InschrijvenAanpassenClicked(ActionEvent event) {
            Inschrijven selectedInschrijving = InschrijvenTableView.getSelectionModel().getSelectedItem();
            if (selectedInschrijving == null) {
                // Toon een foutmelding als er geen record is geselecteerd
                Alert alert = new Alert(Alert.AlertType.ERROR, "Selecteer een inschrijving om aan te passen.");
                alert.showAndWait();
                return;
            }

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("updateInschrijvenDialog.fxml"));
                DialogPane dialogPane = loader.load();
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(dialogPane);
                dialog.setTitle("Inschrijving Aanpassen");

                // Vul de dialoogvelden vooraf in met gegevens van selectedInschrijving
                TextField emailUpdateTextField = (TextField) dialogPane.lookup("#emailUpdateTextField");
                TextField naamCursusUpdateTextField = (TextField) dialogPane.lookup("#naamCursusUpdateTextField");
                DatePicker datumUpdatePicker = (DatePicker) dialogPane.lookup("#datumUpdatePicker");
                TextField inschrijfIdUpdateTextField = (TextField) dialogPane.lookup("#inschrijfIdUpdateTextField");

                emailUpdateTextField.setText(selectedInschrijving.getEmail());
                naamCursusUpdateTextField.setText(selectedInschrijving.getNaamCursus());
                datumUpdatePicker.setValue(selectedInschrijving.getDatum());
                inschrijfIdUpdateTextField.setText(String.valueOf(selectedInschrijving.getInschrijfId()));

                Optional<ButtonType> clickedButton = dialog.showAndWait();
                if (clickedButton.isPresent() && clickedButton.get() == ButtonType.FINISH) {
                    // Haal bijgewerkte gegevens op uit de dialoogvelden
                    String updatedEmail = emailUpdateTextField.getText();
                    String updatedCursusNaam = naamCursusUpdateTextField.getText();
                    LocalDate updatedDatum = datumUpdatePicker.getValue();
                    int updatedInschrijfId = Integer.parseInt(inschrijfIdUpdateTextField.getText());

                    // Voer SQL UPDATE-operatie uit met bijgewerkte gegevens
                    try (Connection conn = DataBaseSQL.createConnection();
                         PreparedStatement pstmt = conn.prepareStatement(
                            "UPDATE Inschrijven SET email = ?, naamCursus = ?, datum = ? WHERE inschrijfId = ?")) {

                        pstmt.setString(1, updatedEmail);
                        pstmt.setString(2, updatedCursusNaam);
                        pstmt.setDate(3, java.sql.Date.valueOf(updatedDatum));
                        pstmt.setInt(4, updatedInschrijfId);

                        pstmt.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        // Behandel de SQLException
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setHeaderText("Databasefout");
                        errorAlert.setContentText("Er is een fout opgetreden bij het bijwerken van de database.");
                        errorAlert.showAndWait();
                    }

                    // Refresh de tabelweergave
                    loadTableInschrijven();
                }
            } catch (IOException | NumberFormatException ex) {
                ex.printStackTrace();
                // Behandel de uitzonderingen op gepaste wijze
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Fout bij het verwerken");
                errorAlert.setContentText("Er is een fout opgetreden tijdens het verwerken van de gegevens.");
                errorAlert.showAndWait();
            }
        }


    @FXML
    void InschrijvenVerwijderenClicked(ActionEvent event) {
        Inschrijven selectedInschrijving = InschrijvenTableView.getSelectionModel().getSelectedItem();
        if (selectedInschrijving == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Selecteer een inschrijving om te verwijderen.");
            alert.showAndWait();
            return;
        }

        try (Connection conn = DataBaseSQL.createConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                "DELETE FROM Inschrijven WHERE inschrijfId = ?")) {

            pstmt.setInt(1, selectedInschrijving.getInschrijfId());
            pstmt.executeUpdate();

            // Refresh de tabelweergave
            loadTableInschrijven();

        } catch (SQLException e) {
            e.printStackTrace();
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Databasefout");
            errorAlert.setContentText("Er is een fout opgetreden bij het verwijderen.");
            errorAlert.showAndWait();
        }
    }


    @FXML
    void InschrijvenBackClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homeScreen.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    TableColumn<Inschrijven, String> emailInschrijvenColumn;
    @FXML
    TableColumn<Inschrijven, String> naamCursusInschrijvenColumn;
    @FXML
    TableColumn<Inschrijven, Integer> inschrijfIdInschrijvenColumn;
//    @FXML
//    TableColumn<Inschrijven, String> totaalVoortgangInschrijvenColumn;

    ObservableList<Inschrijven> observableInschrijven;

    private void initTable() {
        observableInschrijven = FXCollections.observableArrayList();
        emailInschrijvenColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        naamCursusInschrijvenColumn.setCellValueFactory(new PropertyValueFactory<>("naamCursus"));
        inschrijfIdInschrijvenColumn.setCellValueFactory(new PropertyValueFactory<>("inschrijfId"));
//        totaalVoortgangInschrijvenColumn
    }

    public void loadTableInschrijven() {
        try {
//            , totaalVoortgang
            System.out.println("test");
            initTable();
            System.out.println("test");
            try ( ResultSet rs = DataBaseSQL.createConnection().prepareStatement("SELECT email, naamCursus, inschrijfId FROM Inschrijven").executeQuery()) {
                while (rs.next()) {
                    Inschrijven Inschrijven = new Inschrijven();
                    Inschrijven.setEmail(rs.getString("email"));
                    Inschrijven.setNaamCursus(rs.getString("naamCursus"));
                    Inschrijven.setInschrijfId(rs.getInt("inschrijfId"));
//                    Inschrijven.setEmail//totaalVoortgang maar deze is nog niet af
        //(rs.getString("email"));
        observableInschrijven.add(Inschrijven); 
                }
            }
            System.out.println("PUNT 4");
            InschrijvenTableView.setItems(observableInschrijven);
            InschrijvenTableView.refresh();
            System.out.println("PUNT 5");

        } catch (SQLException ex) {
            Logger.getLogger(InschrijvenFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}