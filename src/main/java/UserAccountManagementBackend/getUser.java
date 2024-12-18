/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserAccountManagementBackend;

/**
 *
 * @author hebai
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class getUser {
    public static final String JSONFILE = "users.json";
    
    public static User getUser(String userId){
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        try{
            List<User> users = new ArrayList<>();
            File file = new File(JSONFILE);
            if(file.exists() && file.length() >0){  // if there are users in the file
                CollectionType list = objectMapper.getTypeFactory().constructCollectionType(List.class, User.class);
                users = objectMapper.readValue(file, list);
                for(User u : users){
                    if(u.getUserId().equals(userId)){
                        return u;
                        
                    }
                }
               
               
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        System.err.println("user not found");
        return null;
    }
    
}
