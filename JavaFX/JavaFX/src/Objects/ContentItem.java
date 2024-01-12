package Objects;

import java.time.LocalDate;

public class ContentItem {

    private Integer contentItemId;
    private String titel;
    private LocalDate datum;
    private String status;
    private String versie;
    private String beschrijving;
    private int tijdsDuur;
    private String URL;
    private String naamSpreker;
    private String organisatieSpreker;
    private LocalDate datumPublicatie;
    private String naamContactPersoon;
    private String emailContactPersoon;
    private int volgNr;
    private Short percentage;

    public ContentItem(int contentItemId, String titel, LocalDate datum, String Status) {
        this.contentItemId = contentItemId;
        this.titel = titel;
        this.datum = datum;
        this.status = Status.valueOf(Status);
    }

    public ContentItem() {
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

    public String getVersie() {
        return versie;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public int getTijdsDuur() {
        return tijdsDuur;
    }

    public String getURL() {
        return URL;
    }

    public String getNaamSpreker() {
        return naamSpreker;
    }

    public String getOrganisatieSpreker() {
        return organisatieSpreker;
    }

    public LocalDate getDatumPublicatie() {
        return datumPublicatie;
    }

    public String getNaamContactPersoon() {
        return naamContactPersoon;
    }

    public String getEmailContactPersoon() {
        return emailContactPersoon;
    }

    public int getVolgNr() {
        return volgNr;
    }

    public Short getPercentage() {
        return percentage;
    }

    public void setPercentage(Short percentage) {
        this.percentage = percentage;
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

    public void setVersie(String versie) {
        this.versie = versie;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public void setTijdsDuur(int tijdsDuur) {
        this.tijdsDuur = tijdsDuur;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setNaamSpreker(String naamSpreker) {
        this.naamSpreker = naamSpreker;
    }

    public void setOrganisatieSpreker(String organisatieSpreker) {
        this.organisatieSpreker = organisatieSpreker;
    }

    public void setDatumPublicatie(LocalDate datumPublicatie) {
        this.datumPublicatie = datumPublicatie;
    }

    public void setNaamContactPersoon(String naamContactPersoon) {
        this.naamContactPersoon = naamContactPersoon;
    }

    public void setEmailContactPersoon(String emailContactPersoon) {
        this.emailContactPersoon = emailContactPersoon;
    }

    public void setVolgNr(int volgNr) {
        this.volgNr = volgNr;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ContentItem{contentItemId=").append(contentItemId);
        sb.append(", titel=").append(titel);
        sb.append(", datum=").append(datum);
        sb.append(", Status=").append(status);
        sb.append('}');
        return sb.toString();
    }

}
