/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GroupManagementBackEnd;

import Account.UserAccount;
import java.util.ArrayList;

/**
 *
 * @author CONNECT
 */
public class adminRole {
    public void addMember (UserAccount member, Group g)
    { ArrayList<UserAccount> members = g.getMember();
        members.add(member);
        g.setMember(members);
    }
     public void removeMember (UserAccount member, Group g)
    { ArrayList<UserAccount> members = g.getMember();
        members.remove(member);
        g.setMember(members);
    }
     public void approve (UserAccount member, Group g)
     { ArrayList<UserAccount> requests = g.getRequests();
         requests.remove(member);
      ArrayList<UserAccount> members = g.getMember();
      members.add(member);
     }
      public void decline (UserAccount member, Group g)
     { ArrayList<UserAccount> requests = g.getRequests();
         requests.remove(member);
     }
    
}
