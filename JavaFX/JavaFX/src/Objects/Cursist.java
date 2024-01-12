package Objects;

import java.time.LocalDate;

public class Cursist {

    private String email;
    private String naam;
    private LocalDate geboorteDatum;
    private char geslacht;
    private String postCode;
    private String huisnummer;
    private String woonPlaats;
    private String landCode;

    public Cursist(String email, String naam, LocalDate geboorteDatum, char geslacht, String postCode, String huisnummer, String woonPlaats, String landCode) {
        this.email = email;
        this.naam = naam;
        this.geboorteDatum = geboorteDatum;
        this.geslacht = geslacht;
        this.postCode = postCode;
        this.huisnummer = huisnummer;
        this.woonPlaats = woonPlaats;
        this.landCode = landCode;
    }

    public Cursist() {
    }

    public String getEmail() {
        return email;
    }

    public String getNaam() {
        return naam;
    }

    public LocalDate getGeboorteDatum() {
        return geboorteDatum;
    }

    public char getGeslacht() {
        return geslacht;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public String getWoonPlaats() {
        return woonPlaats;
    }

    public String getLandCode() {
        return landCode;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setGeboorteDatum(LocalDate geboorteDatum) {
        this.geboorteDatum = geboorteDatum;
    }

    public void setGeslacht(char geslacht) {
        this.geslacht = geslacht;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public void setWoonPlaats(String woonPlaats) {
        this.woonPlaats = woonPlaats;
    }

    public void setLandCode(String landCode) {
        this.landCode = landCode;
    }

}
