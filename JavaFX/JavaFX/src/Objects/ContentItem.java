package Objects;
import java.time.LocalDate;


public class ContentItem {
    private Integer contentItemId;
    private String titel;
    private LocalDate datum;
    private String status;

    public ContentItem(int contentItemId, String titel, LocalDate datum, String Status) {
        this.contentItemId = contentItemId;
        this.titel = titel;
        this.datum = datum;
        this.status = Status.valueOf(Status);
    }

    public ContentItem() {
        
    }

    public int getContentItemId() {
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

    public void setContentItemId(int contentItemId) {
        this.contentItemId = contentItemId;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public void setStatus(String Status) {
        this.status = Status;
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
