/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx;

import Java2Database.DataBaseSQL;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 *
 * @author gijsv
 */
class DialogCursusFXMLController {

    @FXML
    TextField naamCursusCursusColumnInput;

    @FXML
    TextField aantalContentItemsCursusColumnInput;

    @FXML
    TextField onderwerpCursusColumnInput;

    @FXML
    TextField introductieTekstCursusColumnInput;

    @FXML
    TextField niveauCursusColumnInput;


    void FinishButtonCreateCursusClicked() {
        System.out.println("test");
        try {
            System.out.println("Test 1");
            DataBaseSQL.sendCommand(DataBaseSQL.createConnection(),
                    "INSERT INTO Cursist (naamCursus,aantalContentItems,onderwerp,introductieTekst) VALUES"
                    + "  '" + naamCursusCursusColumnInput.getText()
                    + "',  '" + aantalContentItemsCursusColumnInput.getText()
                    + "',  '" + onderwerpCursusColumnInput.getText()
                    + "',   '" + introductieTekstCursusColumnInput.getText());
            ;
            System.out.println("Test 2");
            System.out.println("SUCCESVOL GEDAAN");

        } catch (SQLException ex) {
            Logger.getLogger(DialogCursusFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        void FinishButtonUpdateCursusClicked() {

        try {
            DataBaseSQL.sendCommand(DataBaseSQL.createConnection(),
                    "INSERT INTO Cursist (naamCursus,aantalContentItems,onderwerp,introductieTekst,niveau) VALUES"
                    + "  '" + naamCursusCursusColumnInput.getText()
                    + "',  '" + aantalContentItemsCursusColumnInput.getText()
                    + "',   '" + introductieTekstCursusColumnInput.getText()
                    + "',   '" + niveauCursusColumnInput.getText());
            ;

            System.out.println("SUCCESVOL GEDAAN");

        } catch (SQLException ex) {
            Logger.getLogger(DialogCursistFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
