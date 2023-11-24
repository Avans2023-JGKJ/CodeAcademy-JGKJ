
import java.time.LocalDate;


public class ContentItem {
    private int contentItemId;
    private String titel;
    private LocalDate datum;
    private Status Status;

    public ContentItem(int contentItemId, String titel, LocalDate datum, Status Status) {
        this.contentItemId = contentItemId;
        this.titel = titel;
        this.datum = datum;
        this.Status = Status;
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

    public Status getStatus() {
        return Status;
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

    public void setStatus(Status Status) {
        this.Status = Status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ContentItem{contentItemId=").append(contentItemId);
        sb.append(", titel=").append(titel);
        sb.append(", datum=").append(datum);
        sb.append(", Status=").append(Status);
        sb.append('}');
        return sb.toString();
    }
    
}
