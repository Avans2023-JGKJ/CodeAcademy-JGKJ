public class Voortgang {
    private int inschrijfId;
    private String naamCursus;
    private int contentItemId;
    private float voortgangsPercentage;
    
    public Voortgang(int inschrijfId, String naamCursus, int contentItemId, float voortgangsPercentage) {
        this.inschrijfId = inschrijfId;
        this.naamCursus = naamCursus;
        this.contentItemId = contentItemId;
        this.voortgangsPercentage = voortgangsPercentage;
    }

    public int getInschrijfId() {
        return inschrijfId;
    }

    public String getNaamCursus() {
        return naamCursus;
    }

    public int getContentItemId() {
        return contentItemId;
    }

    public float getVoortgangsPercentage() {
        return voortgangsPercentage;
    }

    public void setInschrijfId(int inschrijfId) {
        this.inschrijfId = inschrijfId;
    }

    public void setNaamCursus(String naamCursus) {
        this.naamCursus = naamCursus;
    }

    public void setContentItemId(int contentItemId) {
        this.contentItemId = contentItemId;
    }

    public void setVoortgangsPercentage(float voortgangsPercentage) {
        this.voortgangsPercentage = voortgangsPercentage;
    }

    @Override
    public String toString() {
        return "Voortgang: \nInschrijfid: " + inschrijfId + "\nCursus: " + naamCursus + "\nContent item id: "
                + contentItemId + "\nVoortgangspercentage: " + voortgangsPercentage;
    }

    
}
