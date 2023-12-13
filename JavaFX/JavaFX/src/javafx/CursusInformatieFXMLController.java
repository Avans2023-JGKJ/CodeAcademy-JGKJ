package Validatie;

import Java2Database.DataBaseSQL;
import Objects.ContentItem;
import Objects.Cursist;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.CursistFXMLController;
import javafx.DialogCursistFXMLController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CursusInformatieFXMLController implements Initializable {

    @FXML
    private TableColumn<?, ?> ContentItemBeschrjivingColumn;

    @FXML
    private TableColumn<?, ?> ContentItemTitelColumn;

    @FXML
    private TableColumn<?, ?> ContentItemVersieColumn;

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
//        initTable();
//        loadTableContentItems();
    }
    
    private void initTable() {
        observableContentItems = FXCollections.observableArrayList();
        ContentItemTitelColumn.setCellValueFactory(new PropertyValueFactory<>("titel"));
        ContentItemVersieColumn.setCellValueFactory(new PropertyValueFactory<>("versie"));
        ContentItemBeschrjivingColumn.setCellValueFactory(new PropertyValueFactory<>("beschrijving"));
    }
    
    public void loadTableContentItems() {
        try {
            initTable();
            try (ResultSet rs = DataBaseSQL.createConnection(DialogCursistFXMLController.cursistDbConnection).prepareStatement("SELECT * from contentItems").executeQuery()) {
                while (rs.next()) {
                    ContentItem ContentItem = new ContentItem();
                    ContentItem.setTitel(rs.getString("titel"));
                    ContentItem.setVersie(rs.getString("versie"));
                    ContentItem.setBeschrijving(rs.getString("beschrijving"));
                    observableContentItems.add(ContentItem);
                }
            }

            ContentItemsCursus.setItems(observableContentItems);
            ContentItemsCursus.refresh();

        } catch (SQLException ex) {
            Logger.getLogger(CursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
