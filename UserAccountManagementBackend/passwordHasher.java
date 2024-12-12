/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserAccountManagementBackend;

/**
 *
 * @author hebai
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class passwordHasher {
     public static String hashPassword(String password) {
        try {
           
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            
            byte[] hashBytes = md.digest(password.getBytes());  //bytes

            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public static void main(String[] args) {
        String password = "mySecurePassword";
        String hashedPassword = hashPassword(password);
        System.out.println("Hashed Password: " + hashedPassword);
    }
}
