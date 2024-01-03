/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

/**
 *
 * @author gijsv
 */
public class Persoon {
    private String rol;
    private String userName;
    private String passWord;
    private String email;

    public Persoon(String rol, String userName, String passWord, String email) {
        this.rol = rol;
        this.userName = userName;
        this.passWord = passWord;
        this.email = email;
    }

    public Persoon() {
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
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

   
    
}
