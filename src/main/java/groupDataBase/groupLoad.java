/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package groupDataBase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import GroupManagementBackEnd.Group;
import java.io.BufferedReader;
import java.io.FileReader;

public class groupLoad {

    private static final String JSONFILE = "groups.json";
     
    public HashSet<Group> loadGroups() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        HashSet<Group> accounts = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(JSONFILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Group account = objectMapper.readValue(line, Group.class);
                accounts.add(account);
            }
        } catch (IOException e) {
            System.err.println("Error reading accounts from file: " + e.getMessage());
            throw new RuntimeException("Failed to load accounts", e);
        }

        return accounts;
    }
    
}
