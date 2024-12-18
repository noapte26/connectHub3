/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package groupDataBase;

import GroupManagementBackEnd.Group;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

/**
 *
 * @author CONNECT
 */
public class deleteGroup {
       public static final String JSONFILE = "groups.json";
    
    public deleteGroup(Group group){
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        
        HashSet<Group> groups = new HashSet<>();
        File file = new File(JSONFILE);
        if(file.exists() && file.length() >0){  // if there are users in the file
            groupLoad a = new groupLoad();
            groups = a.loadGroups() ;
            for(Group p : groups){
              if(p.getGroupId().equals(group.getGroupId())){
                    groups.remove(p);
                    break;  
                }
            }
        }
         try (FileWriter writer = new FileWriter(JSONFILE, false)) { // `false` means overwrite
            writer.write(""); // Write an empty string
        } catch (IOException e) {
            System.err.println("Error clearing file: " + e.getMessage());
            e.printStackTrace();
        }
        
        for (Group u : groups)
        {
           new groupSave(u);
        }
    }
  
}
