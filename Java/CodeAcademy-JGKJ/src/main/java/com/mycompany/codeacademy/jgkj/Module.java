/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.codeacademy.jgkj;

/**
 *
 * @author Jochem
 */
public class Module {

    private String versie;
    private String Beschrijving;
    private String naamContactPersoon;
    private String emailContactPersoon;
    private byte volgordeNr;

    // CONSTRUCTORS -----------------------------------------------------------------
    public Module(String versie, String Beschrijving, String naamContactPersoon) {
        if (contactPersoonControle(naamContactPersoon)) {
            this.versie = versie;
            this.Beschrijving = Beschrijving;
            this.naamContactPersoon = naamContactPersoon;
            this.emailContactPersoon = "CONNECTIE MET DATABASE, COMBINATIE VAN NAAM!";
        } else {
            System.out.println("ERROR contactPersoonControle METHODE");
        }
    }

    public Module(String versie, String Beschrijving, String naamContactPersoon, String emailContactPersoon) {
        if (contactPersoonControle(naamContactPersoon)) {
            this.versie = versie;
            this.Beschrijving = Beschrijving;
            this.naamContactPersoon = naamContactPersoon;
            this.emailContactPersoon = emailContactPersoon;
        } else {
            System.out.println("ERROR contactPersoonControle METHODE");
        }
    }

    @Override
    public String toString() {
        return "Module{" + "versie=" + versie + ", Beschrijving=" + Beschrijving + ", naamContactPersoon=" + naamContactPersoon + ", emailContactPersoon=" + emailContactPersoon + ", volgordeNr=" + volgordeNr + '}';
    }

    // PRIVE METHODE -----------------------------------------------------------------
    private boolean contactPersoonControle(String naamContact) {
        return true;
        // HIER KOMT PROGRAMMA
        // Naam van contactpersoon wordt gecontroleerd in database, indien aanwezig, geeft true terug. 
        // Indien naam niet aanwezig, return false.
    }

    // GETTERS -----------------------------------------------------------------
    public String getVersie() {
        return versie;
    }

    public String getBeschrijving() {
        return Beschrijving;
    }

    public String getNaamContactPersoon() {
        return naamContactPersoon;
    }

    public String getEmailContactPersoon() {
        return emailContactPersoon;
    }

    public byte getVolgordeNr() {
        return volgordeNr;
    }

    // SETTERS -----------------------------------------------------------------
    public void setVersie(String versie) {
        this.versie = versie;
    }

    public void setBeschrijving(String Beschrijving) {
        this.Beschrijving = Beschrijving;
    }

    public void setNaamContactPersoon(String naamContactPersoon) {
        this.naamContactPersoon = naamContactPersoon;
    }

    public void setEmailContactPersoon(String emailContactPersoon) {
        this.emailContactPersoon = emailContactPersoon;
    }

    public void setVolgordeNr(byte volgordeNr) {
        this.volgordeNr = volgordeNr;
    }

}
