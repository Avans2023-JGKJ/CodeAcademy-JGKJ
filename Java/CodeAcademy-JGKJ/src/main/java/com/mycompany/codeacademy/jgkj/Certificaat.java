

public class Certificaat {
    private int certificaatId;
    private byte beoordeling;
    private String medeWerkerNaam;



    public Certificaat(int certificaatId, byte beoordeling, String medeWerkerNaam) {
        this.certificaatId = certificaatId;
        this.beoordeling = beoordeling;
        this.medeWerkerNaam = medeWerkerNaam;
    }

    public boolean medeWerkerControle(String medeWerkerNaam) {
        return false;
    // Hier comt een controle die checkt of een naam die opgegeven wordt 
    // ook daadwerkelijk in het systeem bekend is
    }

    public void setCertificaatId(int certificaatId) {
        this.certificaatId = certificaatId;
    }

    public void setBeoordeling(byte beoordeling) {
        this.beoordeling = beoordeling;
    }

    public void setMedeWerkerNaam(String medeWerkerNaam) {
        this.medeWerkerNaam = medeWerkerNaam;
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

    @Override
    public String toString() {
        return "Certificaat:\n" + " Certificaatid: " + certificaatId + "\n" + "Beoordeling: " + beoordeling + "\n"
                + "Ondertekend door: " + medeWerkerNaam;
    }

}
