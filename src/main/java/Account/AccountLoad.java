/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AccountLoad {

    public static final String JSONFILE = "accounts.json";

    public ArrayList<UserAccount> loadAccounts() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        
        ArrayList<UserAccount> accounts = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(JSONFILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
               
               System.out.println(line);
               UserAccount account = objectMapper.readValue(line, UserAccount.class);
               accounts.add(account); // Add it to the list
               
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return accounts;
    }
}
