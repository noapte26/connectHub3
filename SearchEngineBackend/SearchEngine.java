/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SearchEngineBackend;

import Account.AccountLoad;
import Account.UserAccount;
import GroupManagementBackEnd.Group;
import java.util.HashSet;


public class SearchEngine {
    
   public HashSet<UserAccount> searchAccount(String input)
   {
   HashSet<UserAccount> accounts = new AccountLoad().loadAccounts();
   HashSet<UserAccount> accs= new HashSet<>();
   String normalizedInput =input.toLowerCase();
   for(UserAccount user:accounts )
   {
       if(user.getUser().getUserName().toLowerCase().contains(normalizedInput))
               {
                   accs.add(user);
               }
       
   }
   return accs;
   }
   
   /*
   public HashSet<Group> searchGroup(String input)
   {
   HashSet<Group> Groups = new AccountLoad().loadAccounts();
   HashSet<Group> groups= new HashSet<>();
   String normalizedInput =input.toLowerCase();
   for(Group gro: Groups )
   {
       
       if(gro.getName().toLowerCase().contains(normalizedInput))
               {
                   groups.add(gro);
               }
       
   }
   return groups;
   }

    */ 
    
    
    
    
}
