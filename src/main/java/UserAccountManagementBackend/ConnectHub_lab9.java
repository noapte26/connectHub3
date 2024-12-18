/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package UserAccountManagementBackend;

import static UserAccountManagementBackend.UserSaver.saveUser;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.*;
import java.util.Locale;

/**
 *
 * @author hebai
 */
public class ConnectHub_lab9 {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-dd");
        formatter = formatter.withLocale( Locale.ENGLISH);
        LocalDate date = LocalDate.parse("2002-5-03",formatter);
        saveUser("U12", "heba@example.com","heba ","mySecurePassword",date,"offline");
    }
}
