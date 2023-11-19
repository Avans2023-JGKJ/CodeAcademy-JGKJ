import java.util.ArrayList;

public class Certificaat {
    private int certificaatId;
    private byte beoordeling;
    private String medeWerkerNaam;

    ArrayList<String> medewerkers = new ArrayList<String>();

    public Certificaat(int certificaatId, byte beoordeling, String medeWerkerNaam) {
        this.certificaatId = certificaatId;
        this.beoordeling = beoordeling;
        this.medeWerkerNaam = medeWerkerNaam;
    }

    public boolean medeWerkerControle(String medeWerkerNaam) {
        for (int i = 0; i < medewerkers.size(); i++) {
            if (medeWerkerNaam.equals(medewerkers.get(i))) {
                return true;
            }
        }
        return false;
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

    public void setMedewerkers(ArrayList<String> medewerkers) {
        this.medewerkers = medewerkers;
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

    public ArrayList<String> getMedewerkers() {
        return medewerkers;
    }

    @Override
    public String toString() {
        return "Certificaat:\n" + " Certificaatid: " + certificaatId + "\n" + "Beoordeling: " + beoordeling + "\n"
                + "Ondertekend door: " + medeWerkerNaam;
    }

}
