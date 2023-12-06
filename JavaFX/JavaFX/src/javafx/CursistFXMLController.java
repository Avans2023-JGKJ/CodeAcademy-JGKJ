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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
            try (ResultSet rs = DataBaseSQL.createConnection().prepareStatement("SELECT naam, geboorteDatum, geslacht, postCode, email FROM Cursist").executeQuery()) {
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
                loadTableCursist(); // Reload the table after update
            }

            dialog.setTitle("Cursist aanpassen");

        } catch (IOException ex) {
            Logger.getLogger(CursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void CursistAanmakenClicked(ActionEvent event) {
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
    void rowClicked(MouseEvent event) throws IOException, SQLException {
        Cursist clickedCursist = CursistTableView.getSelectionModel().getSelectedItem();
        ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT * FROM Cursist WHERE email = '" + clickedCursist.getEmail() + "'");
        rs.next();
        DataShare.getInstance().setCursistEmail(rs.getString("email"));
        DataShare.getInstance().setCursistNaam(rs.getString("naam"));
        DataShare.getInstance().setCursistGeboorteDatum(LocalDate.parse(rs.getString("geboorteDatum")));
        DataShare.getInstance().setCursistGeslacht(rs.getString("geslacht").charAt(0));
        DataShare.getInstance().setCursistPostCode(rs.getString("postCode"));
        DataShare.getInstance().setCursistHuisnummer(rs.getString("huisNummer"));
        DataShare.getInstance().setCursistWoonPlaats(rs.getString("woonPlaats"));
        DataShare.getInstance().setCursistLandCode(rs.getString("landCode"));
    }
}
