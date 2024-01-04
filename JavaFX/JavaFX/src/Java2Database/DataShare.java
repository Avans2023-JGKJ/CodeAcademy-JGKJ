package Java2Database;

import Java2Database.DataBaseSQL;
import Objects.Cursist;
import Objects.Niveau;
import Objects.Status;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import static Controllers.CursistFXMLController.ErrorAlert;
import Objects.Rol;

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
    private String createdItem;
    private Integer contentItemId;
    private String titel;
    private LocalDate datum;
    private String Beschrijving;
    private Status status;

    // Cursus-gerelateerde velden
    private String naamCursus;
    private Short aantalContentItems;
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
    private Short volgordeNr;

    //Webcast-gerelateerde velden
    private short Tijdsduur;
    private LocalDate DatumPublicatie;
    private String URL;
    private String WebcastBeschrijving;
    private String NaamSpreker;
    private String OrganisatieSpreker;

    //Persoon-gerelateerde velden
    private Rol rol;
    private String userName;
    private String passWord;
    private String email;
    //Cursist Homescreen
    private String naamCursusEen;
    private String naamCursusTwee;
    private String naamCursusDrie;

    public DataShare() {
    }

    public void resetCursist() {
        this.cursistEmail = "";
        this.cursistNaam = ("");
        this.cursistGeboorteDatum = null;
        this.cursistGeslacht = '0';
        this.cursistPostCode = "";
        this.cursistHuisnummer = "";
        this.cursistWoonPlaats = "";
        this.cursistLandCode = "";
    }

    public void resetCertificaat() {
        this.CertificaatInschrijfId = null;
        this.naamCursist = "";
        this.certificaatId = null;
        this.beoordeling = -1;
        this.medeWerkerNaam = "";
    }

    public void resetPersoon() {
        this.rol = null;
        this.userName = null;
        this.passWord = null;
        this.email = null;
    }

    public void resetContentItem() {
        this.contentItemId = null;
        this.naamCursus = null;
        this.titel = "";
        this.datum = null;
        this.status = null;
    }

    public void resetCursus() {
        this.naamCursus = "";
        this.aantalContentItems = -1;

        this.onderwerp = "";
        this.introductieTekst = "";
        this.niveau = null;
    }

    public void resetInschrijvingen() {
        this.inschrijvenEmail = "";
        this.inschrijvenNaamCursus = "";
        this.inschrijvenDatum = null;
        this.inschrijfId = Integer.valueOf(null);
        this.totaalVoortgang = Float.valueOf(null);
    }

    public void resetModule() {
        this.versie = "";
        this.ModuleBeschrijving = "";
        this.naamContactPersoon = "";
        this.emailContactPersoon = "";
        this.volgordeNr = Short.valueOf(null);
    }

    public void resetWebcast() {
        this.Tijdsduur = Short.valueOf(null);
        this.DatumPublicatie = null;
        this.URL = "";
        this.WebcastBeschrijving = "";
        this.NaamSpreker = "";
        this.OrganisatieSpreker = "";
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

    public String getCreatedItem() {
        return createdItem;
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

    public String getBeschrijving() {
        return Beschrijving;
    }

    public Status getStatus() {
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

    public Short getVolgordeNr() {
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

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getPersoonUserName() {
        return userName;
    }

    public void setPersoonUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNaamCursusEen() {
        return naamCursusEen;
    }

    public String getNaamCursusTwee() {
        return naamCursusTwee;
    }

    public String getNaamCursusDrie() {
        return naamCursusDrie;
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

    public void setCreatedItem(String createdItem) {
        this.createdItem = createdItem;
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

    public void setStatus(Status status) {
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

    public void setVolgordeNr(Short volgordeNr) {
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

    public void setNaamCursusEen(String naamCursusEen) {
        this.naamCursusEen = naamCursusEen;
    }

    public void setNaamCursusTwee(String naamCursusTwee) {
        this.naamCursusTwee = naamCursusTwee;
    }

    public void setNaamCursusDrie(String naamCursusDrie) {
        this.naamCursusDrie = naamCursusDrie;
    }

}
