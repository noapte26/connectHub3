/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GroupManagementBackEnd;

import Account.UserAccount;
import groupDataBase.MembersFileManager;

import java.util.ArrayList;

/**
 *
 * @author CONNECT
 */
public class ownerRole extends adminRole {


    public void promoteMember(UserAccount member, Group g) {
        MembersFileManager membersFileManagermember=new MembersFileManager("Members_lists","Member");
        MembersFileManager membersFileManageradmin=new MembersFileManager("Admins_lists","admin");

        ArrayList<UserAccount> admins = membersFileManageradmin.loadMembers(g.getGroupId());
        admins.add(member);
        membersFileManageradmin.saveMembers(g.getGroupId(),admins);
        ArrayList<UserAccount> members = membersFileManagermember.loadMembers(g.getGroupId());
         UserAccount user2 = null;
        for (UserAccount user:members)
        {   if (user.getUser().getUserId().equals(member.getUser().getUserId()))
        { user2=user;}
        } 
        members.remove(user2);
        membersFileManagermember.saveMembers(g.getGroupId(),members);
    }

    public void demoteAdmin(UserAccount admin, Group g) {
        MembersFileManager membersFileManagermember=new MembersFileManager("Members_lists","Member");
        MembersFileManager membersFileManageradmin=new MembersFileManager("Admins_lists","admin");

        ArrayList<UserAccount> admins = membersFileManageradmin.loadMembers(g.getGroupId());
         UserAccount user2 = null;
        for (UserAccount user:admins)
        {   if (user.getUser().getUserId().equals(admin.getUser().getUserId()))
        { user2=user;}
        } 
        admins.remove(user2);
        membersFileManageradmin.saveMembers(g.getGroupId(),admins);
        ArrayList<UserAccount> members = membersFileManagermember.loadMembers(g.getGroupId());
        members.add(admin);
        membersFileManagermember.saveMembers(g.getGroupId(),members);
    }

    public void removeAdmin(UserAccount acc, Group g) {
        MembersFileManager membersFileManageradmin=new MembersFileManager("Admins_lists","admin");
        ArrayList<UserAccount> admins = membersFileManageradmin.loadMembers(g.getGroupId());
         UserAccount user2 = null;
        for (UserAccount user:admins)
        {   if (user.getUser().getUserId().equals(acc.getUser().getUserId()))
        { user2=user;}
        } 
          admins.remove(user2);
          membersFileManageradmin.saveMembers(g.getGroupId(),admins);
    }

}
