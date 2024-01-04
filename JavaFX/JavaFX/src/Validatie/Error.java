package Validatie;

import Java2Database.DataShare;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Error {

    private Alert ConfirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    private Alert ErrorAlert = new Alert(Alert.AlertType.ERROR);
    private Alert InformationAlert = new Alert(Alert.AlertType.INFORMATION);
    private Alert NoneAlert = new Alert(Alert.AlertType.NONE);
    private Alert WarningAlert = new Alert(Alert.AlertType.WARNING);

    private ButtonType buttonTypeYes = new ButtonType("Ja");
    private ButtonType buttonTypeNo = new ButtonType("Nee");
    private ButtonType buttonTypeOk = new ButtonType("Oke");
   

    public boolean removeCursusAlert(String str) {
        if (ConfirmationAlert("Cursus Verwijderen!", "Weet je zeker dat je de Cursus met naam: " + str + " wilt verwijderen?", ""
                + "Cursus naam = '"+str+"'\nAantalContentItems = '"+DataShare.getInstance().getAantalContentItems()+"'\nOnderwerp = '"+DataShare.getInstance().getOnderwerp()+"'\n"
                        + "Niveau = '"+DataShare.getInstance().getNiveau().name()+"'")) {
            return true;
        }
        return false;
    }
    
    public boolean removeCertificaatAlert(Integer certificaatId) {
        if (ConfirmationAlert("Certificaat Verwijderen!", "Weet je zeker dat je de Certificaat met certificaatId: " + certificaatId + " wilt verwijderen?", ""
                + "CertificaatId = '"+certificaatId+"'\nBeoordeling = '"+DataShare.getInstance().getBeoordeling()+"'\nMedewerker Naam = '"+DataShare.getInstance().getMedeWerkerNaam()+"'\nInschrijfId = '"+DataShare.getInstance().getCertificaatInschrijfId()+"'")) {
            return true;
        }
        return false;
    }
    
    public boolean removeCursistAlert(String str) {
        if (ConfirmationAlert("Cursist Verwijderen!", "Weet je zeker dat je de Cursist met naam: " + str + " wilt verwijderen?", ""
                + "GeboorteDatum = '"+DataShare.getInstance().getCursistGeboorteDatum()+"'\nGeslacht = '"+DataShare.getInstance().getCursistGeslacht()+"'\n"
                        + "PostCode = '"+DataShare.getInstance().getCursistPostCode()+"'")) {
            return true;
        }
        return false;
    }
    
    public boolean removeContentItemsAlert(String str) {
        if (ConfirmationAlert("ContentItem Verwijderen!", "Weet je zeker dat je het ContentItem met naam: " + str + " wilt verwijderen?", "")) {
            return true;
        }
        return false;
    }

    public void ErrorLength(String str, int lgh) {
        WarningAlert("Input is te lang", "Ingevoerde tekst overschrijd het limiet van: " + lgh + "", "Ingevoerde tekst: \n" + str);
    }

    public void ErrorLimit(int input, int min, int max) {
        WarningAlert("Limiet Overschreden", "Het ingevoerde getal: " + input + " valt niet binnen het bereik van: " + min + " - " + max + "", "");
    }

    public void ErrorNull(String str) {
        WarningAlert("Error", str, "");
    }

    void ErrorEmpty() {
        InformationAlert("Verplicht!", "1 of meerdere velden zijn niet ingevuld.", "");
    }

    boolean ConfirmationAlert(String title, String header, String contentText) {
        ConfirmationAlert.setTitle("");
        ConfirmationAlert.setHeaderText("");
        ConfirmationAlert.setContentText("");
        if (!title.isEmpty()) {
            ConfirmationAlert.setTitle(title);
        }
        if (!header.isEmpty()) {
            ConfirmationAlert.setHeaderText(header);
        }
        if (!contentText.isEmpty()) {
            ConfirmationAlert.setContentText(contentText);
        }
        
        
        ConfirmationAlert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, ButtonType.CANCEL);
        Optional<ButtonType> result = ConfirmationAlert.showAndWait();
        return result.isPresent() && result.get() == buttonTypeYes;
    }

    boolean ErrorAlert(String title, String header, String contentText) {
        ErrorAlert.setTitle("");
        ErrorAlert.setHeaderText("");
        ErrorAlert.setContentText("");
        if (!title.isEmpty()) {
            ErrorAlert.setTitle(title);
        }
        if (!header.isEmpty()) {
            ErrorAlert.setHeaderText(header);
        }
        if (!contentText.isEmpty()) {
            ErrorAlert.setContentText(contentText);
        }
        ErrorAlert.getButtonTypes().setAll(ButtonType.CANCEL);
        Optional<ButtonType> result = ErrorAlert.showAndWait();
        return result.isPresent() && result.get() == buttonTypeOk;
    }

    boolean InformationAlert(String title, String header, String contentText) {
        InformationAlert.setTitle("");
        InformationAlert.setHeaderText("");
        InformationAlert.setContentText("");
        if (!title.isEmpty()) {
            InformationAlert.setTitle(title);
        }
        if (!header.isEmpty()) {
            InformationAlert.setHeaderText(header);
        }
        if (!contentText.isEmpty()) {
            InformationAlert.setContentText(contentText);
        }
        InformationAlert.getButtonTypes().setAll(buttonTypeOk, ButtonType.CANCEL);
        Optional<ButtonType> result = InformationAlert.showAndWait();
        return result.isPresent() && result.get() == buttonTypeOk;
    }

    boolean NoneAlert(String title, String header, String contentText) {
        NoneAlert.setTitle("");
        NoneAlert.setHeaderText("");
        NoneAlert.setContentText("");
        if (!title.isEmpty()) {
            NoneAlert.setTitle(title);
        }
        if (!header.isEmpty()) {
            NoneAlert.setHeaderText(header);
        }
        if (!contentText.isEmpty()) {
            NoneAlert.setContentText(contentText);
        }
        NoneAlert.getButtonTypes().setAll(buttonTypeOk, ButtonType.CANCEL);
        Optional<ButtonType> result = NoneAlert.showAndWait();
        return result.isPresent() && result.get() == buttonTypeOk;
    }

    boolean WarningAlert(String title, String header, String contentText) {
        WarningAlert.setTitle("");
        WarningAlert.setHeaderText("");
        WarningAlert.setContentText("");
        if (!title.isEmpty()) {
            WarningAlert.setTitle(title);
        }
        if (!header.isEmpty()) {
            WarningAlert.setHeaderText(header);
        }
        if (!contentText.isEmpty()) {
            WarningAlert.setContentText(contentText);
        }
        WarningAlert.getButtonTypes().setAll(ButtonType.CANCEL);
        WarningAlert.showAndWait();
        return true;
    }

    void ErrorPostCode(String input) {
        WarningAlert("PostCode incorrect", "Uw ingevoerde waarde voor postcode: '"+input+"' is niet correct geformatteerd.", "Voorbeeld: \nPostcode: 9999 AB \n4 getallen - 1 spatie - 2 hoofdletters.");
    }

    void ErrorEmail(String input) {
       WarningAlert("Email incorrect", "Uw ingevoerde waarde voor email: '"+input+"' is niet correct geformatteerd.", "Voorbeeld: \nEmail: test@test.nl");
    }

    void ErrorLeefTijd() {
        System.out.println("LEEFTIJD ERROR");
    }

    void ErrorAge(LocalDate input, short min) {
        WarningAlert("Leeftijd incorrect", "Uit uw ingevoerde waarde voor geboortedatum: '"+input+"' wordt afgeleid,\n dat u niet voldoet aan onze minimumleeftijd van: "+min, "");
    }

    public void ErrorPassWord() {
       WarningAlert("Wachtwoord incorrect", "De 2 wachtwoord velden komen niet overeen! \n Controleerd u nogmaals uw wachtwoord.", "");
    }

    public void ErrorSucces() {
        InformationAlert("Succesvol","Succesvol Cursist Aangemaakt Je kunt nu inloggen!", "");
    }

    void ErrorPKViolation(String input, String ondrwrp, String var) {
        ErrorAlert(ondrwrp+" is al in gebruik!", "Er bestaat al een "+var+" met "+ondrwrp+": '"+input + "'","");
    }

    void ErrorFKViolation(String var, String ondrwrp, String type) {
        ErrorAlert(ondrwrp+" niet gevonden!", "Er bestaat geen "+ondrwrp+" met "+type+": '"+var+"'.", "");
    }

    void ErrorLegalDate(LocalDate publicatieDatum) {
        ErrorAlert(publicatieDatum+" is niet geldig!", "De ingevoerde Datum: '"+publicatieDatum+"' is niet geldig.", "");
    }

    void ErrorURL(String URL) {
        ErrorAlert("URL incorrect!", "De ingevoerde URL is niet juist geformatteerd:", "Ingevoerde URL: '"+URL+"'\nControleer de URL en probeer opnieuw.");
    }

}
