package Objects;

import Java2Database.DataBaseSQL;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Certificaat {
    
    private String naamCursist;
    private Integer certificaatId;
    private byte beoordeling;
    private String medeWerkerNaam;



    public Certificaat(int certificaatId, byte beoordeling, String medeWerkerNaam) throws SQLException {
        this.certificaatId = certificaatId;
        this.beoordeling = beoordeling;
        this.medeWerkerNaam = medeWerkerNaam;
        ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT * FROM Cursist WHERE email = (SELECT email FROM Inschrijven WHERE certificaatId = "+certificaatId+")");;
        rs.next();
        this.naamCursist = rs.getString("Naam");
                
                
    }
    
    public Certificaat(){
    }

    public boolean medeWerkerControle(String medeWerkerNaam) {
        return false;
    // Hier comt een controle die checkt of een naam die opgegeven wordt 
    // ook daadwerkelijk in het systeem bekend is
    }

    public void setCertificaatId(int certificaatId) throws SQLException {
        this.certificaatId = certificaatId;
        ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT * FROM Cursist WHERE email = (SELECT email FROM Inschrijven WHERE certificaatId = "+certificaatId+")");;
        rs.next();
        this.naamCursist = rs.getString("Naam");
    }

    public void setBeoordeling(byte beoordeling) {
        this.beoordeling = beoordeling;
    }

    public void setMedeWerkerNaam(String medeWerkerNaam) {
        this.medeWerkerNaam = medeWerkerNaam;
    }

    public void setNaamCursist(String NaamCursist) {
        this.naamCursist = NaamCursist;
    }
    
    public int getCertificaatId() {
        return certificaatId;
    }

    public byte getBeoordeling() {
        return beoordeling;
    }

    public String getMedeWerkerNaam() {
        return medeWerkerNaam;
    }

    public String getNaamCursist() {
        return naamCursist;
    }
    
    

    @Override
    public String toString() {
        return "Certificaat:\n" + " Certificaatid: " + certificaatId + "\n" + "Beoordeling: " + beoordeling + "\n"
                + "Ondertekend door: " + medeWerkerNaam;
    }

}
