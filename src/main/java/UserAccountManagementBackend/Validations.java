/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserAccountManagementBackend;

import static UserAccountManagementFrontend.LoginWindpw.JSONFILE;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author hebai
 */
public class Validations {
   public static final String REGEXPATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
   public static final String JSONfILE = "users.json";
   public static boolean isValid(String email){
       return Pattern.compile(REGEXPATTERN).matcher(email).matches();
   }
   public static boolean isRepeated(String userName){
       try{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
            List<User> users = new ArrayList<>();
            File file = new File(JSONFILE);
            if(file.exists() && file.length() >0){
                CollectionType list = objectMapper.getTypeFactory().constructCollectionType(List.class, User.class);
                users = objectMapper.readValue(file, list);
               
                for(User user:users){
                    if(user.getUserName().equals(userName)){
                        return true;
                    }
            }
                return false;
         }
            
        }catch(IOException e){
            e.printStackTrace();
        }
       return false;
   }
   
}
