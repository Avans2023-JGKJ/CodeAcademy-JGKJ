import java.util.Arrays;

public class Cursus {
    private String naamCursus;
    private short aantalContentItems;
    // Object veranderen naar ContentItems zodra java bestand gemaakt is
    private Object[] contentItems;
    private String onderwerp;
    private String introductieTekst;
    private String niveau;

    public Cursus(String naamCursus, short aantalContentItems, String onderwerp, String introductieTekst, byte niveau) {
        this.naamCursus = naamCursus;
        this.aantalContentItems = aantalContentItems;
        this.onderwerp = onderwerp;
        this.introductieTekst = introductieTekst;
        String[] list = new String[]{"Beginner", "Gevorderd", "Expert"};
        niveau--;
        this.niveau = list[niveau];
        this.contentItems = new ContentItems[aantalContentItems];
    }

    public String getNaamCursus() {
        return naamCursus;
    }

    public short getAantalContentItems() {
        return aantalContentItems;
    }

    public String getOnderwerp() {
        return onderwerp;
    }

    public String getIntroductieTekst() {
        return introductieTekst;
    }

    public String[] getNiveau() {
        return new String[]{niveau};
    }

    public void setNaamCursus(String naamCursus) {
        this.naamCursus = naamCursus;
    }

    public void setAantalContentItems(short aantalContentItems) {
        this.aantalContentItems = aantalContentItems;
    }

    public void setOnderwerp(String onderwerp) {
        this.onderwerp = onderwerp;
    }

    public void setIntroductieTekst(String introductieTekst) {
        this.introductieTekst = introductieTekst;
    }

    public void setNiveau(String[] niveau) {
        this.niveau = Arrays.toString(niveau);
    }

    @Override
    public String toString() {
        return "Cursus{" +
                "naamCursus='" + naamCursus + '\'' +
                ", aantalContentItems=" + aantalContentItems +
                ", onderwerp='" + onderwerp + '\'' +
                ", introductieTekst='" + introductieTekst + '\'' +
                ", niveau=" + Arrays.toString(new String[]{niveau}) +
                '}';
    }
}
