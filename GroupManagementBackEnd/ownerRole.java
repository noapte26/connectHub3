/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GroupManagementBackEnd;

import Account.UserAccount;
import groupDataBase.updateGroup;
import java.util.ArrayList;

/**
 *
 * @author CONNECT
 */
public class ownerRole extends adminRole {

    public void promoteMember(UserAccount member, Group g) {
        ArrayList<UserAccount> admins = g.getAdmins();
        admins.add(member);
        g.setAdmins(admins);
        ArrayList<UserAccount> members = g.getMember();
        members.remove(member);
        g.setMember(members);
        new updateGroup(g);
    }

    public void demoteMember(UserAccount admin, Group g) {
        ArrayList<UserAccount> admins = g.getAdmins();
        admins.remove(admin);
        g.setAdmins(admins);
        ArrayList<UserAccount> members = g.getMember();
        members.add(admin);
        g.setMember(members);
         new updateGroup(g);
    }

    public void removeAdmin(UserAccount acc, Group g) {
        ArrayList<UserAccount> admins = g.getAdmins();  
          admins.remove(acc);
          g.setAdmins(admins);
           new updateGroup(g);
         
        
    }

}
