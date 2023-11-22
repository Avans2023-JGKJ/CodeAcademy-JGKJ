/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseprogram2.pkg0;


import com.sun.jdi.connect.spi.Connection;
import java.beans.Statement;
import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author joche
 */
public class addNaam {

    public static void main(String[] args) {
        DatabaseProgram20 dat = new DatabaseProgram20();
        String url = DatabaseProgram20.getUrl();
        System.out.println(url);
        try ( java.sql.Connection con = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connection successful!");
            // Add your database operations here
            java.sql.Statement st = con.createStatement();
            Scanner first = new Scanner(System.in);
            System.out.println("Typ naam, voor insert: ");
            String name = first.nextLine();

            String SQL = "INSERT INTO firstName VALUES ('" + name + "')";
            st.execute(SQL);

            System.out.println("Input succesvol");
            con.close();
        } catch (SQLException e) {
            System.out.println("Error connecting to the database:");
            e.printStackTrace();
        }

    }

}
