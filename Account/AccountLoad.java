/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

/**
 *
 * @author CONNECT
 */
public class AccountLoad {
     private static final String JSONFILE = "accounts.json";
     
    public HashSet<UserAccount> loadAccounts() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        HashSet<UserAccount> accounts = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(JSONFILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                UserAccount account = objectMapper.readValue(line, UserAccount.class);
                accounts.add(account);
            }
        } catch (IOException e) {
            System.err.println("Error reading accounts from file: " + e.getMessage());
            throw new RuntimeException("Failed to load accounts", e);
        }

        return accounts;
    }
    
}
