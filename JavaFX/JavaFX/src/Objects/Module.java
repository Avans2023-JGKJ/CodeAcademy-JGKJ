package Objects;

public class Module {

    private String versie;
    private String Beschrijving;
    private String naamContactPersoon;
    private String emailContactPersoon;
    private byte volgordeNr;

    // CONSTRUCTORS -----------------------------------------------------------------
    public Module(String versie, String Beschrijving, String naamContactPersoon) {

        this.versie = versie;
        this.Beschrijving = Beschrijving;
        this.naamContactPersoon = naamContactPersoon;
        this.emailContactPersoon = "CONNECTIE MET DATABASE, COMBINATIE VAN NAAM!";

    }

    public Module(String versie, String Beschrijving, String naamContactPersoon, String emailContactPersoon) {

        this.versie = versie;
        this.Beschrijving = Beschrijving;
        this.naamContactPersoon = naamContactPersoon;
        this.emailContactPersoon = emailContactPersoon;

    }

    @Override
    public String toString() {
        return "Module{" + "versie=" + versie + ", Beschrijving=" + Beschrijving + ", naamContactPersoon=" + naamContactPersoon + ", emailContactPersoon=" + emailContactPersoon + ", volgordeNr=" + volgordeNr + '}';
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
