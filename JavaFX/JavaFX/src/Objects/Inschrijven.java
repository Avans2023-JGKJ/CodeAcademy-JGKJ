package Objects;

import java.time.LocalDate;

public class Inschrijven {
    private String email;
    private String naamCursus;
    private LocalDate datum;
    private int inschrijfId;
    private float totaalVoortgang;
    
    public Inschrijven(String email, String naamCursus, LocalDate datum, int inschrijfId) {
        this.email = email;
        this.naamCursus = naamCursus;
        this.datum = datum;
        this.inschrijfId = inschrijfId;
        this.totaalVoortgang = totaalVoortgang;
    }

    public Inschrijven() {
    }
    public String getEmail() {
        return email;
    }
    public String getNaamCursus() {
        return naamCursus;
    }
    public LocalDate getDatum() {
        return datum;
    }
    public int getInschrijfId() {
        return inschrijfId;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setNaamCursus(String naamCursus) {
        this.naamCursus = naamCursus;
    }
    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }
    public void setInschrijfId(int inschrijfId) {
        this.inschrijfId = inschrijfId;
    }
    @Override
    public String toString() {
        return "Inschrijving \nEmail:" + email + "\nCursus: " + naamCursus + "\nDatum: " + datum + "\nInschrijfid: "
                + inschrijfId;
    }

    public void setInschrijfId(String inschrijfId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
