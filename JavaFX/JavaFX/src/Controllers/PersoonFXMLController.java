package Controllers;

import static Controllers.CursistFXMLController.ErrorAlert;
import Controllers.DialogPersoonFXMLController;
import Java2Database.DataBaseSQL;
import Java2Database.DataShare;
import Objects.Persoon;
import Objects.Rol;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class PersoonFXMLController implements Initializable {

    @FXML
    private TableView<Persoon> PersoonTableView;

    @FXML
    private TableColumn<Persoon, String> rolePersoonColumn;

    @FXML
    private TableColumn<Persoon, String> userNamePersoonColumn;

    @FXML
    private TableColumn<Persoon, String> emailPersoonColumn;

    @FXML
    private ObservableList<Persoon> observablePersoon;

    private void initTable() {
        observablePersoon = FXCollections.observableArrayList();
        rolePersoonColumn.setCellValueFactory(new PropertyValueFactory<>("Rol"));
        userNamePersoonColumn.setCellValueFactory(new PropertyValueFactory<>("UserName"));
        emailPersoonColumn.setCellValueFactory(new PropertyValueFactory<>("Email"));
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override //Methode laad de Table and daarna de Data
    public void initialize(URL url, ResourceBundle rb) {
        initTable();
        loadTablePersoon();
    }

    //Laad de table in met data uit database
    public void loadTablePersoon() {
        try {
            initTable();
            try ( ResultSet rs = DataBaseSQL.createConnection().prepareStatement("SELECT * FROM Persoon").executeQuery()) {
                while (rs.next()) {
                    Persoon persoon = new Persoon();
                    persoon.setRol(rs.getString("Rol"));
                    persoon.setUserName(rs.getString("UserName"));
                    persoon.setPassWord(rs.getString("PassWord"));
                    persoon.setEmail(rs.getString("Email"));

                    observablePersoon.add(persoon);
                }
            }

            PersoonTableView.setItems(observablePersoon);
            PersoonTableView.refresh();

        } catch (SQLException ex) {
            Logger.getLogger(PersoonFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML //Methode wordt gebruikt als je een Persoon wilt aanmaken
    void PersoonAanmakenClicked(MouseEvent event) {
        DialogPersoonFXMLController.setIsUpdatePersoon(false);
        DataShare.getInstance().resetPersoon();
        DataShare.getInstance().setRol(Rol.ADMIN);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/createPersoonDialog.fxml"));
            DialogPane pane = loader.load();

            DialogPersoonFXMLController createPersoonController = loader.getController();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(pane);

            dialog.getDialogPane().getButtonTypes().clear();
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

            Button applyButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.APPLY);
            applyButton.addEventFilter(ActionEvent.ACTION, ae -> {
                if (!createPersoonController.ValidateAndCreatePersoon()) {
                    ae.consume();
                }
            });

            Optional<ButtonType> clickedButton = dialog.showAndWait();

            if (clickedButton.isPresent() && clickedButton.get() == ButtonType.APPLY) {
                loadTablePersoon();
            }
            dialog.setTitle("Persoon aanmaken");

        } catch (IOException ex) {
            Logger.getLogger(PersoonFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML //Methode wordt gebruikt als je een Persoon wilt aanpassen
    void PersoonAanpassenClicked(MouseEvent event) {
        DialogPersoonFXMLController.setIsUpdatePersoon(true);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/updatePersoonDialog.fxml"));
            DialogPane pane = loader.load();

            DialogPersoonFXMLController updatePersoonController = loader.getController();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(pane);

            dialog.getDialogPane().getButtonTypes().clear();
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.FINISH, ButtonType.CANCEL);

            Button finishButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.FINISH);
            finishButton.addEventFilter(ActionEvent.ACTION, ae -> {
                if (!updatePersoonController.ValidateAndUpdatePersoon()) {
                    ae.consume();
                }
            });

            Optional<ButtonType> clickedButton = dialog.showAndWait();

            if (clickedButton.isPresent() && clickedButton.get() == ButtonType.FINISH) {
                loadTablePersoon();
            }

            dialog.setTitle("Persoon aanpassen");

        } catch (IOException ex) {
            Logger.getLogger(PersoonFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML //Methode wordt gebruikt als je een Persoon wilt verwijdren
    void PersoonVerwijderenClicked(MouseEvent event) {
        Persoon selectedPersoon = PersoonTableView.getSelectionModel().getSelectedItem();
        if (selectedPersoon != null && removePersoonAlert(selectedPersoon.getUserName())) {
            try {
                String delete = "DELETE FROM Persoon WHERE userName = '" + selectedPersoon.getUserName() + "'";
                DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), delete);
            } catch (SQLException ex) {
                // Behandel fouten hier
                Logger.getLogger(PersoonFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            loadTablePersoon();
        }
    }

    //Methode wordt gebruikt om Alert te plaatsen op scherm, confirmation
    private boolean removePersoonAlert(String userName) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Persoon Verwijderen!");
        alert.setHeaderText("Weet je zeker dat je de Persoon met naam: " + userName + " wilt verwijderen?");

        // Ja, Nee knoppen
        ButtonType buttonTypeYes = new ButtonType("Ja");
        ButtonType buttonTypeNo = new ButtonType("Nee");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, ButtonType.CANCEL);

        Optional<ButtonType> result = alert.showAndWait();

        // Controleer het resultaat
        return result.isPresent() && result.get() == buttonTypeYes;
    }

    @FXML //Methode wordt gebruikt als je op een Row klikt
    void rowClicked(MouseEvent event) {
        try {
            Persoon clickedPersoon = PersoonTableView.getSelectionModel().getSelectedItem();

            if (clickedPersoon != null) {
                ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT * FROM Persoon WHERE userName = '" + clickedPersoon.getUserName() + "'");

                if (rs.next()) {
                    DataShare.getInstance().setRol(Rol.valueOf(rs.getString("Rol")));
                    DataShare.getInstance().setPersoonUserName(rs.getString("UserName"));
                    DataShare.getInstance().setPassWord(rs.getString("PassWord"));
                    DataShare.getInstance().setEmail(rs.getString("Email"));
                } else {
                    ErrorAlert("Deze Persoon bestaat niet meer!", "Onbekende Persoon");
                }
            }
        } catch (SQLException | DateTimeParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML //Methode wordt gebruikt als je een op de backbutton drukt
    void PersoonBackClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML_Bestanden/homeScreenAdmin.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
