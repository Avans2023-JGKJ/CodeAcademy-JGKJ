package Validatie;

import Java2Database.DataBaseSQL;
import static Controllers.CursistFXMLController.parseDate;
import Java2Database.DataShare;
import Objects.Niveau;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataValidatie {

    //General Info
    private static short minimaleLeeftijdCursist = 8;

    //Error Object
    private static Error Error = new Error();

    //DataBase velden Cursus Tabel
    private static int naamCursusLength = 255;
    private static int aantalContentitemsCursusMax = 32767;
    private static int onderwerpCursusLength = 255;
    private static int introductieTekstCursusLength = 43679;
    private static int niveauCursusLength = 9;

    //DataBase velden Cursist Tabel
    private static int emailCursistLenght = 255;
    private static int naamCursistLength = 255;
    private static int postCodeCursistLenght = 15;
    private static int huisNummerCursistLenght = 25;
    private static int woonPlaatsCursistLenght = 100;
    private static int landCodeCursistLenght = 10;
    private static int geboorteDatumCursistLenght = 10;
    private static boolean isUpdate = false;

    //DataBase velden Certificaat Tabel
    private static int naamMedewerkerCertificaatLenght = 255;
    private static int BeoordelingCertificaatMin = 1;
    private static int BeoordelingCertificaatMax = 10;

    public static boolean InsertCursusValid(String naamCursus, String aantalItems, String onderwerp, String intro, Niveau niveau) {
        if (checkForNull(naamCursus, aantalItems, onderwerp, intro)) {
            if (checkPKCursus(naamCursus)) {
                if (checkNVarChar(naamCursus, naamCursusLength)) {
                    if (Integer.valueOf(aantalItems) <= aantalContentitemsCursusMax && Integer.valueOf(aantalItems) >= 1) {
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

    public static boolean InsertCursistValid(String naamCursist, String postCode, String email, LocalDate geboorteDatum, boolean man, boolean vrouw, String huisnr, String woonplaats, String landcode) {
        if (checkForNull(naamCursist, postCode, email, String.valueOf(geboorteDatum), huisnr, woonplaats, landcode)) {
            if (checkPKCursist(email)) {
                if (checkVarChar(naamCursist, naamCursistLength)) {
                    if (checkNVarChar(postCode, postCodeCursistLenght)) {
                        if (checkPostcode(postCode)) {
                            if (checkNVarChar(email, emailCursistLenght)) {
                                if (checkEmailAddress(email)) {
                                    if (checkNVarChar(String.valueOf(geboorteDatum), geboorteDatumCursistLenght) || isUpdate) {
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
                                                Error.ErrorNull("Er is iets mis gegaan bij het selecteren van de Gender.");
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

    public static boolean UpdateCursistValid(String naamCursist, String postCode, String email, boolean man, boolean vrouw, String huisnr, String woonplaats, String landcode) {
        isUpdate = true;
        if (InsertCursistValid(naamCursist, postCode, email, LocalDate.now(), man, vrouw, huisnr, woonplaats, landcode)) {
            return true;
        }
        return false;
    }

    public static boolean InsertCertificaatValid(String Beoordeling, String naamMedewerker, String inschrijfId) {
        if (checkForNull(Beoordeling, naamMedewerker, inschrijfId)) {
            if (checkNVarChar(naamMedewerker, naamMedewerkerCertificaatLenght)) {
                if (Integer.valueOf(Beoordeling) <= BeoordelingCertificaatMax && Integer.valueOf(Beoordeling) >= BeoordelingCertificaatMin) {
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
                    Error.ErrorLimit(Integer.valueOf(Beoordeling), BeoordelingCertificaatMin, BeoordelingCertificaatMax);
                }
            } else {
                Error.ErrorLength(naamMedewerker, naamMedewerkerCertificaatLenght);
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
        // Use a regular expression to check if the string contains any Unicode character
        return Pattern.compile("[\\p{InBasicLatin}\\p{InLatin-1Supplement}-\\p{InLatinExtended-C}&&[^\\p{InBasicLatin}]]").matcher(str).find();
    }

    public static boolean checkPostcode(String postCode) {
        return postCode.matches("[1-9]{1}[0-9]{3} [A-Z]{2}");
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
        if (a != null) {
            return true;
        }
        return false;
    }

    private static boolean checkForNull(String a, String b) {
        if (a != null && b != null) {
            return true;
        }
        return false;
    }

    private static boolean checkForNull(String a, String b, String c) {
        if (!a.isEmpty() && !b.isEmpty() && !c.isEmpty()) {
            return true;
        }
        return false;
    }

    private static boolean checkForNull(String a, String b, String c, String d) {
        if (!a.isEmpty() && !b.isEmpty() && !c.isEmpty() && !d.isEmpty()) {
            return true;
        }
        return false;
    }

    private static boolean checkForNull(String a, String b, String c, String d, String e, String f, String g) {
        if (!a.isEmpty() && !b.isEmpty() && !c.isEmpty() && !d.isEmpty() && !e.isEmpty() && !f.isEmpty() && !g.isEmpty()) {
            return true;
        }
        return false;
    }

    private static boolean checkPKCursus(String naamCursus) {
        try {
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT naamCursus FROM Cursus WHERE naamCursus = '" + naamCursus + "'");
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
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT email FROM Cursist WHERE email = '" + email + "'");
            if (rs.next() && !rs.getString("email").equals(DataShare.getInstance().getCursistEmail())) {
                return false;
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
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT UserName FROM Persoon WHERE UserName = '" + naamMedewerker + "'");
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
            ResultSet rs = DataBaseSQL.sendCommandReturn(DataBaseSQL.createConnection(), "SELECT inschrijfId FROM Inschrijven WHERE inschrijfId = '" + inschrijfId + "'");
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
}
