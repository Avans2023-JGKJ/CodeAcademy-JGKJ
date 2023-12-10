package Objects;

import java.util.HashMap;
import java.util.Map;

public enum Status {
    ACTIEF("Actief"), 
    CONCEPT("Concept"),
    GEARCHIVEERD("Gearchiveerd");

    private final String statusString;
    private static final Map<String, Status> stringToEnum = new HashMap<>();

    static {
        for (Status status : values()) {
            stringToEnum.put(status.toString(), status);
        }
    }

    Status(String statusString) {
        this.statusString = statusString;
    }

    @Override
    public String toString() {
        return statusString;
    }

    public static Status fromString(String statusString) {
        return stringToEnum.get(statusString);
    }
}
