package javafx;

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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
    private ObservableList<Niveau> observableNiveau;


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
    }   



    public void loadTableCursus() {
    try {
        initTable();
        try (ResultSet rs = DataBaseSQL.createConnection().prepareStatement("SELECT * FROM Cursus").executeQuery()) {
            while (rs.next()) {
                Cursus cursus = new Cursus();
                cursus.setNaamCursus(rs.getString("naamCursus"));
                cursus.setAantalContentItems((short) rs.getInt("aantalContentItems"));
                cursus.setOnderwerp(rs.getString("onderwerp"));
                cursus.setIntroductieTekst(rs.getString("introductieTekst"));
                String niveauString = rs.getString("niveau");
                Niveau niveau = Niveau.valueOf(niveauString);
                cursus.setNiveau(niveau);

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
    void CursusAanmakenClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("createCursusDialog.fxml"));
            DialogPane pane = loader.load();

            DialogCursusFXMLController createCursusController = loader.getController();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(pane);
            Optional<ButtonType> clickedFinish = dialog.showAndWait();

            if (clickedFinish.isPresent() && clickedFinish.get() == ButtonType.FINISH) {
                createCursusController.FinishButtonCreateCursusClicked();
                loadTableCursus(); // Reload the table after update
            }

            dialog.setTitle("Cursus aanmaken");

        } catch (IOException ex) {
            Logger.getLogger(CursusFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void CursusAanpassenClicked(ActionEvent event) {
        System.out.println("CursusAanpassenClicked");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("updateCursusDialog.fxml"));
            DialogPane pane = loader.load();

            DialogCursusFXMLController updateCursusController = loader.getController();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(pane);
            Optional<ButtonType> clickedFinish = dialog.showAndWait();

            if (clickedFinish.isPresent() && clickedFinish.get() == ButtonType.FINISH) {
                updateCursusController.FinishButtonUpdateCursusClicked();
                loadTableCursus(); // Reload the table after update
            }

            dialog.setTitle("Cursus aanpassen");

        } catch (IOException ex) {
            Logger.getLogger(CursusFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void CursusVerwijderenClicked(ActionEvent event) {
        System.out.println("CursusVerwijderenClicked");
    }

    @FXML
    void CursusBackClicked(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homeScreen.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
