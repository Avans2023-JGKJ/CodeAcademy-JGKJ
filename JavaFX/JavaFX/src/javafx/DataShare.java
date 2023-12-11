package javafx;

import java.time.LocalDate;

public class DataShare {

    private static final DataShare instance = new DataShare();

    // Cursist-gerelateerde velden
    private String cursistEmail;
    private String cursistNaam;
    private LocalDate cursistGeboorteDatum;
    private char cursistGeslacht;
    private String cursistPostCode;
    private String cursistHuisnummer;
    private String cursistWoonPlaats;
    private String cursistLandCode;

    // Cursus-gerelateerde velden
    private String cursusNaam;

    private DataShare() {
    }

    public static DataShare getInstance() {
        return instance;
    }

    // Cursist-gerelateerde getters en setters
    public String getCursistEmail() {
        return cursistEmail;
    }

    public String getCursistNaam() {
        return cursistNaam;
    }

    public LocalDate getCursistGeboorteDatum() {
        return cursistGeboorteDatum;
    }

    public char getCursistGeslacht() {
        return cursistGeslacht;
    }

    public String getCursistPostCode() {
        return cursistPostCode;
    }

    public String getCursistHuisnummer() {
        return cursistHuisnummer;
    }

    public String getCursistWoonPlaats() {
        return cursistWoonPlaats;
    }

    public String getCursistLandCode() {
        return cursistLandCode;
    }

    public void setCursistEmail(String cursistEmail) {
        this.cursistEmail = cursistEmail;
    }

    public void setCursistNaam(String cursistNaam) {
        this.cursistNaam = cursistNaam;
    }

    public void setCursistGeboorteDatum(LocalDate cursistGeboorteDatum) {
        this.cursistGeboorteDatum = cursistGeboorteDatum;
    }

    public void setCursistGeslacht(char cursistGeslacht) {
        this.cursistGeslacht = cursistGeslacht;
    }

    public void setCursistPostCode(String cursistPostCode) {
        this.cursistPostCode = cursistPostCode;
    }

    public void setCursistHuisnummer(String cursistHuisnummer) {
        this.cursistHuisnummer = cursistHuisnummer;
    }

    public void setCursistWoonPlaats(String cursistWoonPlaats) {
        this.cursistWoonPlaats = cursistWoonPlaats;
    }

    public void setCursistLandCode(String cursistLandCode) {
        this.cursistLandCode = cursistLandCode;
    }

    // Cursus-gerelateerde getters en setters
    public String getCursusNaam() {
        return cursusNaam;
    }

    public void setCursusNaam(String cursusNaam) {
        this.cursusNaam = cursusNaam;
    }

}
