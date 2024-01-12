package Objects;

import Java2Database.DataBaseSQL;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Certificaat {

    private int inschrijfId;
    private String naamCursist;
    private int certificaatId;
    private byte beoordeling;
    private String medeWerkerNaam;
    private String naamCursus;

    public Certificaat(int inschrijfId, int certificaatId, byte beoordeling, String medeWerkerNaam) throws SQLException {
        this.inschrijfId = inschrijfId;
        this.certificaatId = certificaatId;
        this.beoordeling = beoordeling;
        this.medeWerkerNaam = medeWerkerNaam;
        ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT * FROM Cursist WHERE email = (SELECT email FROM Inschrijven WHERE inschrijfId = " + inschrijfId + ")");
        rs.next();
        this.naamCursist = rs.getString("Naam");

    }

    public Certificaat() {
    }

    public void setInschrijfId(int inschrijfId) throws SQLException {
        this.inschrijfId = inschrijfId;
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

    public void setCertificaatId(Integer certificaatId) {
        this.certificaatId = certificaatId;
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

    public Integer getInschrijfId() {
        return inschrijfId;
    }

    public String getNaamCursus() {
        return naamCursus;
    }

    public void setNaamCursus(String naamCursus) {
        this.naamCursus = naamCursus;
    }

    @Override
    public String toString() {
        return "Certificaat:\n" + " Certificaatid: " + certificaatId + "\n" + "Beoordeling: " + beoordeling + "\n"
                + "Ondertekend door: " + medeWerkerNaam;
    }

}
