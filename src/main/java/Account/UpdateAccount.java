/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Account;

import ProfileManagementBackend.Profile;
import UserAccountManagementBackend.User;
import static UserAccountManagementBackend.UserSaver.JSONFILE;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UpdateAccount {
    public static final String JSONFILE = "accounts.json";
    
    public  void save(UserAccount account){
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        
        ArrayList<UserAccount> accounts = new ArrayList<>();
        File file = new File(JSONFILE);
        if(file.exists() && file.length() >0){  // if there are users in the file
            AccountLoad a = new AccountLoad();
            accounts = a.loadAccounts() ;
            for(UserAccount p : accounts){
                if(p.getUser().getUserId().equals(account.getUser().getUserId())){
                    accounts.remove(p);
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
         accounts.add(account);
        for (UserAccount u : accounts)
        { new  AccountSave(u);
        }
    }
   
    
}
