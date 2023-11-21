import java.time.LocalDate;
import java.util.Arrays;

public class Cursist {
    private String email;
    private String name;
    private LocalDate birthDate;
    private char gender;
    private String address;
    private String residence;
    private int countryCode;

    public Cursist(String email, String name, LocalDate birthDate, char gender, String address, String residence, int countryCode) {
        this.email = email;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.address = address;
        this.residence = residence;
        this.countryCode = countryCode;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public char getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public String getResidence() {
        return residence;
    }

    public int getCountryCode() {
        return countryCode;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String toString() {
        return "Cursist{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                ", address='" + address + '\'' +
                ", residence='" + residence + '\'' +
                ", countryCode=" + countryCode +
                '}';
    }
}