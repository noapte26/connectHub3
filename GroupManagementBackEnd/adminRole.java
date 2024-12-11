/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GroupManagementBackEnd;

import Account.UserAccount;
import ContentCreation.Post;
import groupDataBase.updateGroup;
import java.util.ArrayList;

/**
 *
 * @author CONNECT
 */
public class adminRole extends commonRole {
    public void addMember (UserAccount member, Group g)
    { ArrayList<UserAccount> members = g.getMember();
        members.add(member);
        g.setMember(members);
         new updateGroup(g);
    }
     public void removeMember (UserAccount member, Group g)
    { ArrayList<UserAccount> members = g.getMember();
        members.remove(member);
        g.setMember(members);
         new updateGroup(g);
    }
     public void approveMember (UserAccount member, Group g)
     { ArrayList<UserAccount> requests = g.getRequests();
         requests.remove(member);
      ArrayList<UserAccount> members = g.getMember();
      members.add(member);
       new updateGroup(g);
     }
      public void declineMember (UserAccount member, Group g)
     { ArrayList<UserAccount> requests = g.getRequests();
         requests.remove(member);
          new updateGroup(g);
     }
      public void appovePost (Post post, Group g)
      {
      ArrayList<Post> Postrequests = g.getRequestPosts();
      Postrequests.remove(post);
      g.setRequestPosts(Postrequests);
      ArrayList<Post> posts = g.getContent();
      posts.add(post);
      g.setContent(posts);
       new updateGroup(g);
      }
       public void declinePost (Post post, Group g)
      {
      ArrayList<Post> Postrequests = g.getRequestPosts();
      Postrequests.remove(post);
       new updateGroup(g);
      }
       
    
}
