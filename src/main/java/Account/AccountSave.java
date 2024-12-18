/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Account;

import static ProfileManagementBackend.profileSaver.JSONFILE;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author CONNECT
 */
public class AccountSave {

    public static final String JSONFILE = "accounts.json";

    public  AccountSave(UserAccount account) {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
    

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(JSONFILE, true))) {
        String profileJson = objectMapper.writeValueAsString(account);
        writer.write(profileJson);
        writer.newLine(); // Add a newline after each JSON object
    } catch (IOException e) {
        e.printStackTrace();
    }
}


}
