package groupDataBase;

import Account.AccountLoad;
import Account.AccountSave;
import Account.UserAccount;
import GroupManagementBackEnd.Group;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class Updategroup {
    public static final String JSONFILE = "groups.json";

    public   Updategroup(Group account){

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

        groupLoad a = new groupLoad();
        HashSet<Group> accounts = a.loadGroups();

        File file = new File(JSONFILE);
        if(file.exists() && file.length() >0){  // if there are users in the file

            for(Group p : accounts){
                if(p.getGroupId().equals(account.getGroupId())){
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
        for (Group u : accounts)
        {
            new groupSave(u);
        }
    }
}
