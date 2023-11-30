package javafx;

import Objects.Cursist;

import Java2Database.DataBaseSQL;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.sql.ResultSetMetaData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class CursistFXMLController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private TableView<Cursist> CursistTableView;

    @FXML
    void CursistAanmakenClicked(ActionEvent event) {

    }

    @FXML
    void CursistAanpassenClicked(ActionEvent event) {
loadTableCursist();
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
    TableColumn<Cursist, String> naamCursistColumn;
    @FXML
    TableColumn<Cursist, LocalDate> geboorteDatumCursistColumn;
    @FXML
    TableColumn<Cursist, String> geslachtCursistColumn;
    @FXML
    TableColumn<Cursist, String> postCodeCursistColumn;

    ObservableList<Cursist> observableCursist;

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
            try ( ResultSet rs = DataBaseSQL.createConnection().prepareStatement("SELECT naam, geboorteDatum, geslacht, postCode FROM Cursist").executeQuery()) {
                while (rs.next()) {
                    Cursist Cursist = new Cursist();
                    Cursist.setNaam(rs.getString("naam"));
                    Cursist.setGeboorteDatum(LocalDate.parse(rs.getString("geboorteDatum")));
                    char[] c = rs.getString("geslacht").toCharArray();
                    Cursist.setGeslacht(c[0]);
                    Cursist.setPostCode(rs.getString("postCode"));

                    observableCursist.add(Cursist);
                }
            }
            System.out.println("PUNT 4");
            CursistTableView.setItems(observableCursist);
            CursistTableView.refresh();

        } catch (SQLException ex) {
            Logger.getLogger(CursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
