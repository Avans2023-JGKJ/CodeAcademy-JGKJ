/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Java2Database.DataBaseSQL;
import Java2Database.DataShare;
import Validatie.Error;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class DialogCursistHomeScreenFXMLController implements Initializable {

    @FXML
    private Label Voorstel1;

    @FXML
    private Label Voorstel2;

    @FXML
    private Label Voorstel3;

    @FXML
    private Label Voorstel4;

    @FXML
    private ComboBox<String> InschrijvenNaamCursusBox;

    @FXML
    private ObservableList<String> naamCursusList = FXCollections.observableArrayList();

    private Error Error = new Error();

    private StringBuilder build = new StringBuilder();
    private StringBuilder builder = new StringBuilder();

    HashMap<String, Double> similarity = new HashMap<String, Double>();

    //Initialize wordt aangeroepen bij het inladen van de pagina
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        setUpInschrijvenNaamCursus();
    }

    //Deze methode vult de combobox die bij deze dialog hoort
    void setUpInschrijvenNaamCursus() {
        try {
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT naamCursus "
                    + "FROM Cursus");
            while (rs.next()) {
                naamCursusList.add(rs.getString("naamCursus"));
            }
            ResultSet rs1 = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT c.naamCursus "
                    + "FROM Cursus c JOIN Inschrijven i ON i.naamCursus = c.naamCursus "
                    + "JOIN Cursist cu ON cu.email = i.email "
                    + "JOIN Persoon p ON p.Email = cu.email "
                    + "WHERE p.UserName = '" + DataShare.getInstance().getUsername() + "'");

            while (rs1.next()) {
                naamCursusList.remove(naamCursusList.indexOf(rs1.getString("naamCursus")));
            }
            InschrijvenNaamCursusBox.setItems(naamCursusList);
            ResultSet rs2 = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT c.naamCursus, c.onderwerp, c.introductieTekst, c.niveau "
                    + "FROM Cursus c "
                    + "JOIN Inschrijven i ON i.naamCursus = c.naamCursus "
                    + "JOIN Cursist cu ON cu.email = i.email "
                    + "JOIN Persoon p ON p.Email = cu.email "
                    + "WHERE p.UserName = '" + DataShare.getInstance().getUsername() + "'"
            );
            while (rs2.next()) {
                build.append(rs2.getString("naamCursus"));
                build.append(rs2.getString("onderwerp"));
                build.append(rs2.getString("introductieTekst"));
            }
            setMostSimilar(build.toString());
        } catch (SQLException ex) {
            Logger.getLogger(DialogCursistHomeScreenFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Deze methode voert de tests uit op de ingevoerde data en stuurt deze naar de database

    boolean ValidateAndSignUp() {
        try {
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT naamCursus FROM Cursus");
            ResultSet rs1 = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT c.email FROM Cursist c JOIN Persoon p ON p.Email = c.email WHERE UserName = '" + DataShare.getInstance().getUsername() + "'");
            if (InschrijvenNaamCursusBox.getValue() != null && !InschrijvenNaamCursusBox.getValue().isEmpty()) {
                if (rs.next()) {
                    rs1.next();
                    DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), "INSERT INTO Inschrijven (email, naamCursus, datum, totaalVoortgang) VALUES "
                            + "('" + rs1.getString("email") + "', '" + InschrijvenNaamCursusBox.getValue() + "', '" + LocalDate.now() + "', '0')");

                    ResultSet rs2 = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT inschrijfId FROM Inschrijven ORDER BY inschrijfId DESC");
                    rs2.next();
                    DataShare.getInstance().setInschrijfId(rs2.getInt("inschrijfId"));
                    DataShare.getInstance().setCursistEmail(rs1.getString("email"));
                    DataShare.getInstance().setNaamCursus(InschrijvenNaamCursusBox.getValue());

                    createAccessories(DataShare.getInstance().getInschrijfId());
                    return true;
                }
            } else {
                Error.ErrorNull("Je hebt nog geen Cursus geselecteerd");
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DialogCursistHomeScreenFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    //Deze methode vult de voortgang in aan de hand van de content items tabel
    private void createAccessories(int inschrijfId) {
        try {
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT contentItemId FROM contentItems WHERE naamCursus = '" + DataShare.getInstance().getNaamCursus() + "'");
            while (rs.next()) {
                DataBaseSQL.sendCommand(DataBaseSQL.createConnection(), "INSERT INTO Voortgang (inschrijfId, naamCursus, contentItemId, voortgangsPercentage) VALUES ("
                        + "'" + inschrijfId + "', '"
                        + DataShare.getInstance().getNaamCursus() + "', '" + rs.getString("contentItemId") + "', '0')");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DialogCursistHomeScreenFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Deze methode berekent welke cursussen lijken op elkaar
    private void setMostSimilar(String cursus1) {
        try {
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT naamCursus, onderwerp, introductieTekst  "
                    + "FROM Cursus");
            while (rs.next()) {
                builder.setLength(0);
                builder.append(rs.getString("naamCursus"));
                builder.append(rs.getString("onderwerp"));
                builder.append(rs.getString("introductieTekst"));

                Set<String> set1 = new HashSet<>();
                Set<String> set2 = new HashSet<>();

                String[] words1 = cursus1.split("\\s+");
                String[] words2 = builder.toString().split("\\s+");

                for (String word : words1) {
                    set1.add(word.toLowerCase());
                }

                for (String word : words2) {
                    set2.add(word.toLowerCase());
                }

                int commonWords = 0;
                for (String word : set1) {
                    if (set2.contains(word)) {
                        commonWords++;
                    }
                }
                int totalUniqueWords = set1.size() + set2.size() - commonWords;
                double similarityNumber = (double) commonWords / totalUniqueWords;

                similarity.put(rs.getString("naamCursus"), similarityNumber);
            }

            TreeMap<Double, String> sortedSimilarity = new TreeMap<>(Collections.reverseOrder());

            for (Map.Entry<String, Double> x : similarity.entrySet()) {
                sortedSimilarity.put(x.getValue(), x.getKey());
            }
            ArrayList list = new ArrayList();
            ResultSet rs1 = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT c.naamCursus "
                    + "FROM Cursus c JOIN Inschrijven i ON i.naamCursus = c.naamCursus "
                    + "JOIN Cursist cu ON cu.email = i.email "
                    + "JOIN Persoon p ON p.Email = cu.email "
                    + "WHERE p.UserName = '" + DataShare.getInstance().getUsername() + "'");
            while (rs1.next()) {
                list.add(rs1.getString("naamCursus"));
            }
            int count = 0;
            for (Map.Entry<Double, String> x : sortedSimilarity.entrySet()) {
                if (count < 4) {
                    if (!list.contains(x.getValue())) {
                        switch (count) {
                            case 0:
                                Voorstel1.setText("1:\n" + x.getValue());
                                break;
                            case 1:
                                Voorstel2.setText("2:\n" + x.getValue());
                                break;
                            case 2:
                                Voorstel3.setText("3:\n" + x.getValue());
                                break;
                            case 3:
                                Voorstel4.setText("4:\n" + x.getValue());
                                break;
                        }
                        count++;
                    }

                } else {
                    break;
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(DialogCursistHomeScreenFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
