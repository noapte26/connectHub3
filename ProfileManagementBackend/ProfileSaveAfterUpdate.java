/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ProfileManagementBackend;

/**
 *
 * @author hebai
 */

import UserAccountManagementBackend.User;
import static UserAccountManagementBackend.UserSaver.JSONFILE;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ProfileSaveAfterUpdate {
    public static final String JSONFILE = "profiles.json";
    
    public static void save(Profile profile){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        
        try{
            List<Profile> profiles = new ArrayList<>();
            File file = new File(JSONFILE);
            if(file.exists() && file.length() >0){  // if there are users in the file
                CollectionType list = objectMapper.getTypeFactory().constructCollectionType(List.class, Profile.class);
                profiles = objectMapper.readValue(file, list);
                for(Profile p : profiles){
                    if(p.getUserId().equals(profile.getUserId())){
                        profiles.remove(p);
                        break;
                      
                        
                    }
                }
             }
            profiles.add(profile);
            objectMapper.writeValue(file, profiles);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
   
    
}
