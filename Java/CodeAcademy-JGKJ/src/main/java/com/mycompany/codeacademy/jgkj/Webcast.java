package com.mycompany.codeacademy.jgkj;

import java.time.LocalDate;

/**
 *
 * @author Jochem
 */
public class Webcast {

    private short Tijdsduur;
    private LocalDate DatumPublicatie;
    private String URL;
    private String Beschrijving;
    private String NaamSpreker;
    private String OrganisatieSpreker;

// CONSTRUCTORS -----------------------------------------------------------------
    public Webcast(short Tijdsduur, LocalDate DatumPublicatie, String URL, String Beschrijving, String NaamSpreker) {
        this.Tijdsduur = Tijdsduur;
        this.DatumPublicatie = DatumPublicatie;
        this.URL = URL;
        this.Beschrijving = Beschrijving;
        this.NaamSpreker = NaamSpreker;
    }

    public Webcast(short Tijdsduur, LocalDate DatumPublicatie, String URL, String Beschrijving, String NaamSpreker, String OrganisatieSpreker) {
        if (sprekerControle(NaamSpreker)) {
            this.Tijdsduur = Tijdsduur;
            this.DatumPublicatie = DatumPublicatie;
            this.URL = URL;
            this.Beschrijving = Beschrijving;
            this.NaamSpreker = NaamSpreker;
            this.OrganisatieSpreker = "CONNECTIE MET DATABASE, COMBINATIE VAN NAAM!";
        } else {
            System.out.println("ERROR sprekerControle METHODE");
        }
    }

    // PRIVE METHODE -----------------------------------------------------------------
    private boolean sprekerControle(String NaamSpreker) {
        return true;
        // HIER KOMT PROGRAMMA
        // Naam van spreker wordt gecontroleerd in database, indien aanwezig, geeft true terug. 
        // Indien naam niet aanwezig, return false.

    }

// GETTERS -----------------------------------------------------------------
    public short getTijdsduur() {
        return Tijdsduur;
    }

    public LocalDate getDatumPublicatie() {
        return DatumPublicatie;
    }

    public String getURL() {
        return URL;
    }

    public String getBeschrijving() {
        return Beschrijving;
    }

    public String getNaamSpreker() {
        return NaamSpreker;
    }

    public String getOrganisatieSpreker() {
        return OrganisatieSpreker;
    }

// SETTERS -----------------------------------------------------------------
    public void setTijdsduur(short Tijdsduur) {
        this.Tijdsduur = Tijdsduur;
    }

    public void setDatumPublicatie(LocalDate DatumPublicatie) {
        this.DatumPublicatie = DatumPublicatie;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setBeschrijving(String Beschrijving) {
        this.Beschrijving = Beschrijving;
    }

    public void setNaamSpreker(String NaamSpreker) {
        this.NaamSpreker = NaamSpreker;
    }

    public void setOrganisatieSpreker(String OrganisatieSpreker) {
        this.OrganisatieSpreker = OrganisatieSpreker;
    }

}
