package javafx;

import Java2Database.DataBaseSQL;
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
import static javafx.DialogCursistFXMLController.cursistDbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CursistFXMLController implements Initializable {

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

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTable();
        loadTableCursist();
        cursistDbConnection = DataBaseSQL.createConnection(cursistDbConnection);
    }

    private void initTable() {
        observableCursist = FXCollections.observableArrayList();
        naamCursistColumn.setCellValueFactory(new PropertyValueFactory<>("naam"));
        geboorteDatumCursistColumn.setCellValueFactory(new PropertyValueFactory<>("geboorteDatum"));
        geslachtCursistColumn.setCellValueFactory(new PropertyValueFactory<>("geslacht"));
        postCodeCursistColumn.setCellValueFactory(new PropertyValueFactory<>("postCode"));
    }

    public void loadTableCursist() {
        try {
            initTable();
            try (ResultSet rs = DataBaseSQL.createConnection(DialogCursistFXMLController.cursistDbConnection).prepareStatement("SELECT naam, geboorteDatum, geslacht, postCode, email FROM Cursist").executeQuery()) {
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

    @FXML
    void CursistAanpassenClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("updateCursistDialog.fxml"));
            DialogPane pane = loader.load();

            DialogCursistFXMLController updateCursistController = loader.getController();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(pane);
            Optional<ButtonType> clickedApply = dialog.showAndWait();

            if (clickedApply.isPresent() && clickedApply.get() == ButtonType.APPLY) {
                updateCursistController.ApplyButtonUpdateCursistClicked();
                loadTableCursist(); // Herlaad table
            }

            dialog.setTitle("Cursist aanpassen");

        } catch (IOException ex) {
            Logger.getLogger(CursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void CursistAanmakenClicked(ActionEvent event) {
        resetData();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("createCursistDialog.fxml"));
            DialogPane pane = loader.load();

            DialogCursistFXMLController createCursistController = loader.getController();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(pane);
            Optional<ButtonType> clickedFinish = dialog.showAndWait();

            if (clickedFinish.isPresent() && clickedFinish.get() == ButtonType.FINISH) {
                createCursistController.FinishButtonCreateCursistClicked();
                loadTableCursist(); // Reload the table after update
            }

            dialog.setTitle("Cursist toevoegen");

        } catch (IOException ex) {
            Logger.getLogger(CursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    

    @FXML
    void CursistVerwijderenClicked(ActionEvent event) {
        if (removeAlert()){
            try {
               
                String delete = "DELETE FROM Cursist WHERE email = '" + DataShare.getInstance().getCursistEmail() + "'";
                DataBaseSQL.sendCommand(DataBaseSQL.createConnection(DialogCursistFXMLController.cursistDbConnection), delete);
            } catch (SQLException ex) {
                //Alert NIET GEVONDEN OF NIET VOLTOOID
                Logger.getLogger(CursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }
        loadTableCursist();
    }
    

    @FXML
    void CursistBackClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homeScreen.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void FinishButtonCreateCursistClicked(ActionEvent event) {
        // Handle finish button click
        
    }

    @FXML
    void CloseButtonCreateCursistClicked(ActionEvent event) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.hide();
    }

    @FXML
    void rowClicked(MouseEvent event) {
      try {
        Cursist clickedCursist = CursistTableView.getSelectionModel().getSelectedItem();

        if (clickedCursist != null) {
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(DialogCursistFXMLController.cursistDbConnection), "SELECT * FROM Cursist WHERE email = '" + clickedCursist.getEmail() + "'");
            
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
    
    void resetData() {
      try {
        Cursist clickedCursist = CursistTableView.getSelectionModel().getSelectedItem();

        if (clickedCursist != null) {
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(DialogCursistFXMLController.cursistDbConnection), "SELECT * FROM Cursist WHERE email = '" + clickedCursist.getEmail() + "'");
            
            if (rs.next()) {
                DataShare.getInstance().setCursistEmail("");
                DataShare.getInstance().setCursistNaam("");
                DataShare.getInstance().setCursistGeboorteDatum(null);
                DataShare.getInstance().setCursistGeslacht('0');
                DataShare.getInstance().setCursistPostCode(null);
                DataShare.getInstance().setCursistHuisnummer(null);
                DataShare.getInstance().setCursistWoonPlaats(null);
                DataShare.getInstance().setCursistLandCode(null);
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

    
     boolean removeAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Cursist Verwijderen!");
        alert.setHeaderText("Weet je zeker dat je Cursist met email: " + DataShare.getInstance().getCursistEmail() + "?");

        // Yes, No knoppen
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();

        // Check the result
        return result.isPresent() && result.get() == buttonTypeYes;
    }
     
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
     
     public static void ErrorAlert(String message, String header) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
