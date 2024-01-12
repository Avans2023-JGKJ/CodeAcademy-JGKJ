package Objects;

import java.time.LocalDate;

public class Cursus {
    private String naamCursus;
    private short aantalContentItems;
    private String onderwerp;
    private String introductieTekst;
    private Niveau niveau;
    private LocalDate datum;
    
    public Cursus(String naamCursus, short aantalContentItems, String onderwerp, String introductieTekst, Niveau niveau) {
        this.naamCursus = naamCursus;
        this.aantalContentItems = aantalContentItems;
        this.onderwerp = onderwerp;
        this.introductieTekst = introductieTekst;
        this.niveau = niveau;
    }

    public Cursus() {
        
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

    public Niveau getNiveau() {
        return niveau;
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

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Naam cursus: ").append(naamCursus).append("\n");
        sb.append("Aantal content items: ").append(aantalContentItems).append("\n");
        sb.append("Onderwerp: ").append(onderwerp).append("\n");
        sb.append("Introductie tekst: ").append(introductieTekst).append("\n");
        sb.append("Niveau: ").append(niveau).append("\n");
        return sb.toString();
    }
}
