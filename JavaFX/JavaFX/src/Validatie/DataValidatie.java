package Validatie;

import Java2Database.DataBaseSQL;
import static Controllers.CursistFXMLController.parseDate;
import Java2Database.DataShare;
import Objects.Niveau;
import Objects.Status;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidatie {

    // General Info
    private static short minimaleLeeftijdCursist = 8;

    // Error Object
    private static Error Error = new Error();

    // DataBase velden Cursus Tabel
    private static int naamCursusLength = 255;
    private static int aantalContentitemsCursusMax = 32767;
    private static int onderwerpCursusLength = 255;
    private static int introductieTekstCursusLength = 43679;
    private static int niveauCursusLength = 9;

    // DataBase velden Cursist Tabel
    private static int emailCursistLenght = 255;
    private static int naamCursistLength = 255;
    private static int postCodeCursistLenght = 15;
    private static int huisNummerCursistLenght = 25;
    private static int woonPlaatsCursistLenght = 100;
    private static int landCodeCursistLenght = 10;
    private static int geboorteDatumCursistLenght = 10;
    private static boolean isUpdate = false;

    // DataBase velden Certificaat Tabel
    private static int naamMedewerkerCertificaatLenght = 255;
    private static int BeoordelingCertificaatMin = 1;
    private static int BeoordelingCertificaatMax = 10;

    // DataBase velden Persoon Tabel
    private static int RolLength = 255;
    private static int UserNameLength = 255;
    private static int PassWordLength = 255;
    private static int EmailPersoonLength = 255;

    // DataBase velen ContentItem
    private static int naamCursusContentItemLength = 255;
    private static int beschrijvingContentItemLength = 255;
    private static int titelContentItemLength = 255;
    private static int statusContentItemLength = 12;

    // DataBase velden Webcast
    private static int titelWebcastLength = 255;
    private static int tijdsDuurWebcastLength = 14400; // Maximale duur is 10 dagen...
    private static int urlWebcastLength = 511;
    private static int naamSprekerWebcastLength = 255;
    private static int organisatieSprekerWebcastLength = 255;

    // DataBase velden Module
    private static int titelModuleLength = 255;
    private static int versieModuleLength = 255;
    private static int naamContactModuleLength = 255;
    private static int emailContactModuleLength = 255;
    private static int volgNrModuleMax = 255;
    private static int volgNrModuleMin = 1;

    public static boolean InsertCursusValid(String naamCursus, String aantalItems, String onderwerp, String intro, Niveau niveau) {
        if (checkForNull(naamCursus, aantalItems, onderwerp, intro)) {
            if (checkPKCursus(naamCursus)) {
                if (checkNVarChar(naamCursus, naamCursusLength)) {
                    if (Integer.valueOf(aantalItems) <= aantalContentitemsCursusMax
                            && Integer.valueOf(aantalItems) >= 1) {
                        if (checkNVarChar(onderwerp, onderwerpCursusLength)) {
                            if (checkNVarChar(intro, introductieTekstCursusLength)) {
                                if (niveau != null && niveau.name() != "" && !niveau.name().isEmpty()) {
                                    System.out.println("VALIDATION SUCCEEDED");
                                    return true;
                                } else {
                                    Error.ErrorNull("Niveau kan niet leeg zijn.");
                                }
                            } else {
                                Error.ErrorLength(intro, introductieTekstCursusLength);
                            }
                        } else {
                            Error.ErrorLength(onderwerp, onderwerpCursusLength);
                        }
                    } else {
                        Error.ErrorLimit(Integer.valueOf(aantalItems), 1, aantalContentitemsCursusMax);
                    }
                } else {
                    Error.ErrorLength(naamCursus, naamCursusLength);
                }
            } else {
                Error.ErrorPKViolation(naamCursus, "CursusNaam", "Cursus");
            }
        } else {
            Error.ErrorEmpty();
        }
        return false;
    }

    public static boolean UpdateCursusValid(String naamCursus, String aantalItems, String onderwerp, String intro, Niveau niveau) {
        if (InsertCursusValid(naamCursus, aantalItems, onderwerp, intro, niveau)) {
            return true;
        }
        return false;
    }

    public static boolean InsertCursistValid(String naamCursist, String postCode, String email, LocalDate geboorteDatum,
            boolean man, boolean vrouw, String huisnr, String woonplaats, String landcode) {
        if (checkForNull(naamCursist, postCode, email, String.valueOf(geboorteDatum), huisnr, woonplaats, landcode)) {
            if (checkPKCursist(email)) {
                if (checkVarChar(naamCursist, naamCursistLength)) {
                    if (checkNVarChar(postCode, postCodeCursistLenght)) {
                        if (checkPostcode(postCode)) {
                            if (checkNVarChar(email, emailCursistLenght)) {
                                if (checkEmailAddress(email)) {
                                    if (checkNVarChar(String.valueOf(geboorteDatum), geboorteDatumCursistLenght)
                                            || isUpdate) {
                                        if (checkBirthDate(geboorteDatum) || isUpdate) {
                                            if ((man == true && vrouw == false) || (man == false && vrouw == true)) {
                                                if (checkNVarChar(huisnr, huisNummerCursistLenght)) {
                                                    if (checkNVarChar(woonplaats, woonPlaatsCursistLenght)) {
                                                        if (checkNVarChar(landcode, landCodeCursistLenght)) {
                                                            System.out.println("VERIFICATION SUCCEEDED!");
                                                            isUpdate = false;
                                                            return true;
                                                        } else {
                                                            Error.ErrorLength(landcode, landCodeCursistLenght);
                                                        }
                                                    } else {
                                                        Error.ErrorLength(woonplaats, woonPlaatsCursistLenght);
                                                    }
                                                } else {
                                                    Error.ErrorLength(huisnr, huisNummerCursistLenght);
                                                }
                                            } else {
                                                Error.ErrorNull(
                                                        "Er is iets mis gegaan bij het selecteren van de Gender.");
                                            }
                                        } else {
                                            Error.ErrorAge(geboorteDatum, minimaleLeeftijdCursist);
                                        }
                                    } else {
                                        Error.ErrorLength(String.valueOf(geboorteDatum), geboorteDatumCursistLenght);
                                    }
                                } else {
                                    Error.ErrorEmail(email);
                                }
                            } else {
                                Error.ErrorLength(email, emailCursistLenght);
                            }
                        } else {
                            Error.ErrorPostCode(postCode);
                        }
                    } else {
                        Error.ErrorLength(postCode, postCodeCursistLenght);
                    }
                } else {
                    Error.ErrorLength(naamCursist, naamCursistLength);
                }
            } else {
                Error.ErrorPKViolation(email, "Email", "Cursist");
            }
        } else {
            Error.ErrorEmpty();
        }
        return false;
    }

    public static boolean UpdateCursistValid(String naamCursist, String postCode, String email, boolean man,
            boolean vrouw, String huisnr, String woonplaats, String landcode) {
        isUpdate = true;
        if (InsertCursistValid(naamCursist, postCode, email, LocalDate.now(), man, vrouw, huisnr, woonplaats,
                landcode)) {
            return true;
        }
        return false;
    }

    public static boolean InsertCertificaatValid(String Beoordeling, String naamMedewerker, String inschrijfId) {
        if (checkForNull(Beoordeling, naamMedewerker, inschrijfId)) {
            if (checkNVarChar(naamMedewerker, naamMedewerkerCertificaatLenght)) {
                if (Integer.valueOf(Beoordeling) <= BeoordelingCertificaatMax
                        && Integer.valueOf(Beoordeling) >= BeoordelingCertificaatMin) {
                    if (checkFK1Certificaat(naamMedewerker)) {
                        if (checkFK2Certificaat(inschrijfId)) {
                            System.out.println("VALIDATION SUCCEEDED");
                            return true;
                        } else {
                            Error.ErrorFKViolation(inschrijfId, "Inschrijving", "inschrijfId");
                        }
                    } else {
                        Error.ErrorFKViolation(naamMedewerker, "Medewerker", "Medewerker Naam");
                    }
                } else {
                    Error.ErrorLimit(Integer.valueOf(Beoordeling), BeoordelingCertificaatMin,
                            BeoordelingCertificaatMax);
                }
            } else {
                Error.ErrorLength(naamMedewerker, naamMedewerkerCertificaatLenght);
            }
        } else {
            Error.ErrorEmpty();
        }
        return false;
    }

    public static boolean AlterContentItemValid(String naamCursus, String beschrijving, String titel, Status status) {
        if (checkForNull(naamCursus, titel)) {
            if (checkNVarChar(naamCursus, naamCursusContentItemLength)) {
                if ((beschrijving == null || beschrijving.isEmpty())
                        || checkNVarChar(beschrijving, beschrijvingContentItemLength)) {
                    if (checkNVarChar(titel, titelContentItemLength)) {
                        if (status != null && status.name() != "" && !status.name().isEmpty()) {
                            if (checkVarChar(status.name(), statusContentItemLength)) {
                                System.out.println("VERIFICATION SUCCEEDED!");
                                return true;
                            } else {
                                Error.ErrorLength(status.name(), statusContentItemLength);
                            }
                        } else {
                            Error.ErrorNull("Status kan niet leeg zijn!");

                        }
                    } else {
                        Error.ErrorLength(titel, titelContentItemLength);
                    }
                } else {
                    Error.ErrorLength(beschrijving, beschrijvingContentItemLength);
                }
            } else {
                Error.ErrorLength(naamCursus, naamCursusContentItemLength);
            }
        } else {
            Error.ErrorEmpty();
        }
        return false;
    }

    public static boolean AlterWebcastValid(String titel, String tijdsDuur, LocalDate publicatieDatum, String URL,
            String naamSpreker, String organisatieSpreker) {
        if (checkMinModule(DataShare.getInstance().getNaamCursus())) {
            if (checkForNull(titel, tijdsDuur, String.valueOf(publicatieDatum), URL, naamSpreker)) {
                if (checkNVarChar(titel, titelWebcastLength)) {
                    if (checkValidNumber(tijdsDuur, tijdsDuurWebcastLength)) {
                        if (checkLegalDate(publicatieDatum)) {
                            if (checkURL(URL)) {
                                if (checkNVarChar(URL, urlWebcastLength)) {
                                    if (checkNVarChar(naamSpreker, naamSprekerWebcastLength)) {
                                        if (organisatieSpreker == null || organisatieSpreker.isEmpty()
                                                || checkNVarChar(organisatieSpreker, organisatieSprekerWebcastLength)) {
                                            System.out.println("VERIFICATION SUCCEEDED!");
                                            return true;
                                        } else {
                                            Error.ErrorLength(organisatieSpreker, organisatieSprekerWebcastLength);
                                        }
                                    } else {
                                        Error.ErrorLength(naamSpreker, naamSprekerWebcastLength);
                                    }
                                } else {
                                    Error.ErrorLength(URL, urlWebcastLength);
                                }
                            } else {
                                Error.ErrorURL(URL);
                            }
                        } else {
                            Error.ErrorLegalDate(publicatieDatum);
                        }

                    } else {
                        Error.ErrorLimit(tijdsDuur, 1, tijdsDuurWebcastLength);
                    }
                } else {
                    Error.ErrorLength(titel, titelWebcastLength);
                }
            } else {
                Error.ErrorEmpty();
            }
        } else {
            Error.ErrorNull("Er moet minimaal 1 Module zijn toegevoegd aan deze Cursus!");
        }
        return false;
    }

    public static boolean AlterModuleValid(String titel, String versie, String naamContact, String emailContact,
            short volgNr) {
        if (checkForNull(titel, versie) && volgNr != -1) {
            if (checkNVarChar(titel, titelModuleLength)) {
                if (checkNVarChar(versie, versieModuleLength)) {
                    if (checkNVarChar(naamContact, naamContactModuleLength)) {
                        if (checkNVarChar(emailContact, emailContactModuleLength)) {
                            if (volgNr <= volgNrModuleMax && volgNr >= volgNrModuleMin) {
                                System.out.println("VERIFICATION SUCCEEDED!");
                                return true;
                            } else {
                                Error.ErrorLimit(volgNr, volgNrModuleMax, volgNrModuleMin);
                            }
                        } else {
                            Error.ErrorLength(emailContact, emailContactModuleLength);
                        }
                    } else {
                        Error.ErrorLength(naamContact, naamContactModuleLength);
                    }
                } else {
                    Error.ErrorLength(versie, versieModuleLength);
                }
            } else {
                Error.ErrorLength(titel, titelModuleLength);
            }
        } else {
            Error.ErrorEmpty();
        }
        return false;
    }

    public static boolean checkVarChar(String str, int lgh) {
        if (str != null) {
            if (str.length() <= lgh) {
                // Check if the string contains only non-Unicode characters
                if (!containsUnicodeCharacter(str)) {
                    return true;
                }
            }
        }
        // If any of the conditions fail, return false
        return false;
    }

    public static boolean checkNVarChar(String str, Integer lgh) {
        if (str != "") {
            if (str.length() < lgh + 1) {
                return true;
            }
        }
        // If any of the conditions fail, return false
        return false;
    }

    private static boolean containsUnicodeCharacter(String str) {
        // Use a regular expression to check if the string contains any Unicode
        // character
        return Pattern
                .compile("[\\p{InBasicLatin}\\p{InLatin-1Supplement}-\\p{InLatinExtended-C}&&[^\\p{InBasicLatin}]]")
                .matcher(str).find();
    }

    public static boolean checkPostcode(String postCode) {
        return postCode.matches("[1-9]{1}[0-9]{3} [A-Z]{2}");
    }

    private static boolean checkValidNumber(String str, int limit) {
        if (str != null && !str.isEmpty()) {
            try {

                if (Integer.parseInt(str) > 0 && Integer.parseInt(str) <= limit) {
                    return true;
                } else {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    public static boolean checkEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean checkURL(String url) {
        String urlPattern = "^(https?://)?[a-zA-Z]+\\.[a-zA-Z]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(urlPattern);
        Matcher m = p.matcher(url);
        return m.matches();
    }

    public static boolean checkPercentage(short perct) {
        if (perct <= 100 && perct >= 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkBirthDate(LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            if (LocalDate.now().getYear() - date.getYear() >= minimaleLeeftijdCursist) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkDate(LocalDate date) {
        if (date.getMonthValue() <= 12 && date.getMonthValue() >= 1) {
            return true;
        }
        return false;

    }

    public static boolean checkLegalDate(LocalDate date) {
        if (date != null && date.isBefore(LocalDate.now())) {
            return true;
        }
        return false;

    }

    public static boolean formatDate(String date) {
        LocalDate parsedDate = parseDate(date);
        String formattedDate = parsedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (checkDate(LocalDate.parse(formattedDate))) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkCijfer(short cijfer) {
        if (cijfer >= 1 && cijfer <= 10) {
            return true;
        }
        return false;
    }

    private static boolean checkForNull(String a) {
        return a != null && !a.isEmpty();
    }

    private static boolean checkForNull(String a, String b) {
        return a != null && !a.isEmpty() && b != null && !b.isEmpty();
    }

    private static boolean checkForNull(String a, String b, String c) {
        return a != null && !a.isEmpty() && b != null && !b.isEmpty() && c != null && !c.isEmpty();
    }

    private static boolean checkForNull(String a, String b, String c, String d) {
        return a != null && !a.isEmpty() && b != null && !b.isEmpty() && c != null && !c.isEmpty() && d != null && !d.isEmpty();
    }

    private static boolean checkForNull(String a, String b, String c, String d, String e) {
        return a != null && !a.isEmpty() && b != null && !b.isEmpty() && c != null && !c.isEmpty() && d != null && !d.isEmpty() && e != null && !e.isEmpty();
    }

    private static boolean checkForNull(String a, String b, String c, String d, String e, String f, String g) {
        return a != null && !a.isEmpty() && b != null && !b.isEmpty() && c != null && !c.isEmpty() && d != null && !d.isEmpty() && e != null && !e.isEmpty() && f != null && !f.isEmpty() && g != null && !g.isEmpty();
    }

    private static boolean checkPKCursus(String naamCursus) {
        try {
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(),
                    "SELECT naamCursus FROM Cursus WHERE naamCursus = '" + naamCursus + "'");
            if (rs.next() && !rs.getString("naamCursus").equals(DataShare.getInstance().getNaamCursus())) {
                return false;
            } else {
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataValidatie.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private static boolean checkPKCursist(String email) {
        try {
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(),
                    "SELECT email FROM Cursist WHERE email = '" + email + "'");
            if (rs.next()) {
                if(!rs.getString("email").equals(DataShare.getInstance().getCursistEmail())){
                    return false;
                }
                return true;
            } else {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataValidatie.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private static boolean checkFK1Certificaat(String naamMedewerker) {
        try {
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(),
                    "SELECT UserName FROM Persoon WHERE UserName = '" + naamMedewerker + "'");
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataValidatie.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private static boolean checkFK2Certificaat(String inschrijfId) {
        try {
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(),
                    "SELECT inschrijfId FROM Inschrijven WHERE inschrijfId = '" + inschrijfId + "'");
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataValidatie.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private static boolean checkPKPersoon(String UserName) {
        try {
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(),
                    "SELECT UserName FROM Persoon WHERE UserName = '" + UserName + "'");
            if (rs.next() && !rs.getString("UserName").equals(DataShare.getInstance().getPersoonUserName())) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataValidatie.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    private static boolean checkFK1Persoon(String email) {
        try {
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(),
                    "SELECT email FROM Cursist WHERE email = '" + email + "'");
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataValidatie.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static boolean InsertPersoonValid(String rol, String userName, String passWord, String email, boolean isUpdatePersoon) {
        if (checkForNull(rol, userName, passWord)) {
            if (checkPKPersoon(userName)) {
                if (checkNVarChar(rol, RolLength)) {
                    if (checkNVarChar(userName, UserNameLength)) {
                        if (checkNVarChar(passWord, PassWordLength)) {
                            if (checkVarChar(email, EmailPersoonLength)) {
                                if (checkEmailAddress(email) || email.isEmpty()) {
                                    if (isUpdatePersoon || checkFK1Persoon(email) || email.isEmpty()) {
                                        return true;
                                    } else {
                                        Error.ErrorFKViolation(email, "Persoon", "email");
                                    }
                                } else {
                                    Error.ErrorEmail(email);
                                }
                            } else {
                                Error.ErrorLength(email, EmailPersoonLength);
                            }
                        } else {
                            Error.ErrorLength(passWord, PassWordLength);
                        }
                    } else {
                        Error.ErrorLength(userName, UserNameLength);
                    }
                } else {
                    Error.ErrorLength(rol, RolLength);
                }
            } else {
                Error.ErrorPKViolation(userName, "UserName", "Persoon");
            }
        } else {
            Error.ErrorEmpty();
        }

        return false;
    }

    public static boolean UpdatePersoonValid(String rol, String userName, String passWord, String email, String newEmail) {
        if (InsertPersoonValid(rol, userName, passWord, email, true)) {
            if (newEmail.equals(email) || checkPKCursist(newEmail)) {
                return true;
            } 
            else {
             Error.ErrorNull("Nieuwe Email: '"+newEmail+"' bestaat al.");
             return false;
            }
        }
        return false;
    }

    private static boolean checkMinModule(String naamCursus) {
        try {
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT m.versie FROM Module m "
                    + "JOIN contentItems c ON c.contentItemId = m.contentitemId "
                    + "JOIN Cursus cu ON cu.naamCursus = c.naamCursus "
                    + "WHERE cu.naamCursus = '" + naamCursus + "'");
            if (rs.next()) {
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(DataValidatie.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
