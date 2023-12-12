package javafx;

import Objects.Niveau;
import java.time.LocalDate;

public class DataShare {

    private static final DataShare instance = new DataShare();

    //Cursist DONE
    // Certificaat 
    
    // Username Inlog
    private String username;
    
    // Cursist-gerelateerde velden
    private String cursistEmail;
    private String cursistNaam;
    private LocalDate cursistGeboorteDatum;
    private char cursistGeslacht;
    private String cursistPostCode;
    private String cursistHuisnummer;
    private String cursistWoonPlaats;
    private String cursistLandCode;
    
    // Certificaat-gerelateerde velden
    private Integer CertificaatInschrijfId;
    private String naamCursist;
    private Integer certificaatId;
    private byte beoordeling;
    private String medeWerkerNaam;
    
    // ContentItem-gerelateerde velden
    private Integer contentItemId;
    private String titel;
    private LocalDate datum;
    private String status;

    // Cursus-gerelateerde velden
    private String naamCursus;
    private short aantalContentItems;
    private String onderwerp;
    private String introductieTekst;
    private Niveau niveau;
    
    //Inschrijven-gerelateerde velden
    private String inschrijvenEmail;
    private String inschrijvenNaamCursus;
    private LocalDate inschrijvenDatum;
    private int inschrijfId;
    private float totaalVoortgang;
    
    //Module-gerelateerde velden
    private String versie;
    private String ModuleBeschrijving;
    private String naamContactPersoon;
    private String emailContactPersoon;
    private byte volgordeNr;
    
    //Webcast-gerelateerde velden
     private short Tijdsduur;
    private LocalDate DatumPublicatie;
    private String URL;
    private String WebcastBeschrijving;
    private String NaamSpreker;
    private String OrganisatieSpreker;

    private DataShare() {
    }

    public static DataShare getInstance() {
        return instance;
    }

    public String getUsername() {
        return username;
    }

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

    public Integer getCertificaatInschrijfId() {
        return CertificaatInschrijfId;
    }

    public String getNaamCursist() {
        return naamCursist;
    }

    public Integer getCertificaatId() {
        return certificaatId;
    }

    public byte getBeoordeling() {
        return beoordeling;
    }

    public String getMedeWerkerNaam() {
        return medeWerkerNaam;
    }

    public Integer getContentItemId() {
        return contentItemId;
    }

    public String getTitel() {
        return titel;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public String getStatus() {
        return status;
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

    public String getInschrijvenEmail() {
        return inschrijvenEmail;
    }

    public String getInschrijvenNaamCursus() {
        return inschrijvenNaamCursus;
    }

    public LocalDate getInschrijvenDatum() {
        return inschrijvenDatum;
    }

    public int getInschrijfId() {
        return inschrijfId;
    }

    public float getTotaalVoortgang() {
        return totaalVoortgang;
    }

    public String getVersie() {
        return versie;
    }

    public String getModuleBeschrijving() {
        return ModuleBeschrijving;
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

    public short getTijdsduur() {
        return Tijdsduur;
    }

    public LocalDate getDatumPublicatie() {
        return DatumPublicatie;
    }

    public String getURL() {
        return URL;
    }

    public String getWebcastBeschrijving() {
        return WebcastBeschrijving;
    }

    public String getNaamSpreker() {
        return NaamSpreker;
    }

    public String getOrganisatieSpreker() {
        return OrganisatieSpreker;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public void setCertificaatInschrijfId(Integer CertificaatInschrijfId) {
        this.CertificaatInschrijfId = CertificaatInschrijfId;
    }

    public void setNaamCursist(String naamCursist) {
        this.naamCursist = naamCursist;
    }

    public void setCertificaatId(Integer certificaatId) {
        this.certificaatId = certificaatId;
    }

    public void setBeoordeling(byte beoordeling) {
        this.beoordeling = beoordeling;
    }

    public void setMedeWerkerNaam(String medeWerkerNaam) {
        this.medeWerkerNaam = medeWerkerNaam;
    }

    public void setContentItemId(Integer contentItemId) {
        this.contentItemId = contentItemId;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public void setInschrijvenEmail(String inschrijvenEmail) {
        this.inschrijvenEmail = inschrijvenEmail;
    }

    public void setInschrijvenNaamCursus(String inschrijvenNaamCursus) {
        this.inschrijvenNaamCursus = inschrijvenNaamCursus;
    }

    public void setInschrijvenDatum(LocalDate inschrijvenDatum) {
        this.inschrijvenDatum = inschrijvenDatum;
    }

    public void setInschrijfId(int inschrijfId) {
        this.inschrijfId = inschrijfId;
    }

    public void setTotaalVoortgang(float totaalVoortgang) {
        this.totaalVoortgang = totaalVoortgang;
    }

    public void setVersie(String versie) {
        this.versie = versie;
    }

    public void setModuleBeschrijving(String ModuleBeschrijving) {
        this.ModuleBeschrijving = ModuleBeschrijving;
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

    public void setTijdsduur(short Tijdsduur) {
        this.Tijdsduur = Tijdsduur;
    }

    public void setDatumPublicatie(LocalDate DatumPublicatie) {
        this.DatumPublicatie = DatumPublicatie;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setWebcastBeschrijving(String WebcastBeschrijving) {
        this.WebcastBeschrijving = WebcastBeschrijving;
    }

    public void setNaamSpreker(String NaamSpreker) {
        this.NaamSpreker = NaamSpreker;
    }

    public void setOrganisatieSpreker(String OrganisatieSpreker) {
        this.OrganisatieSpreker = OrganisatieSpreker;
    }

    


}
