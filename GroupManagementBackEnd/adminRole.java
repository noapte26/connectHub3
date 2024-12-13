/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GroupManagementBackEnd;

import Account.UserAccount;
import ContentCreation.Post;
import NotificationsBackend.Notification;
import NotificationsBackend.NotificationFileManager;
import groupDataBase.ContentFileManager;
import groupDataBase.MembersFileManager;

import java.util.ArrayList;

/**
 *
 * @author CONNECT
 */
public class adminRole extends commonRole {

    public void addMember(UserAccount member, Group g) {
        MembersFileManager membersFileManagermember = new MembersFileManager("Members_lists", "Member");
        ArrayList<UserAccount> members = membersFileManagermember.loadMembers(g.getGroupId());
        members.add(member);
        membersFileManagermember.saveMembers(g.getGroupId(), members);
    }

    public void removeMember(UserAccount member, Group g) {
        MembersFileManager membersFileManagermember = new MembersFileManager("Members_lists", "Member");
        ArrayList<UserAccount> members = membersFileManagermember.loadMembers(g.getGroupId());
        UserAccount user2 = null;
        for (UserAccount user : members) {
            if (user.getUser().getUserId().equals(member.getUser().getUserId())) {
                user2 = user;
            }
        }
        members.remove(user2);
        membersFileManagermember.saveMembers(g.getGroupId(), members);
    }

    public void approveMember(UserAccount member, Group g) {
        MembersFileManager membersFileManagermember = new MembersFileManager("Members_lists", "Member");
        MembersFileManager membersFileManagerrequests = new MembersFileManager("requests_lists", "Requests");
        ArrayList<UserAccount> requests = membersFileManagerrequests.loadMembers(g.getGroupId());
        UserAccount user2 = null;
        for (UserAccount user : requests) {
            if (user.getUser().getUserId().equals(member.getUser().getUserId())) {
                user2 = user;
            }
        }
        requests.remove(user2);
        System.out.println(requests);
        ArrayList<UserAccount> members = membersFileManagermember.loadMembers(g.getGroupId());
        members.add(member);
        membersFileManagermember.saveMembers(g.getGroupId(), members);
        membersFileManagerrequests.saveMembers(g.getGroupId(), requests);
        NotificationFileManager notificationManager = new NotificationFileManager();
        Notification notification = new Notification("Joined Group", member.getUser().getUserId(), g.getGroupId());
        notificationManager.addNotification(member.getUser().getUserId(), notification);

    }

    public void declineMember(UserAccount member, Group g) {
        MembersFileManager membersFileManagerrequests = new MembersFileManager("requests_lists", "Requests");
        ArrayList<UserAccount> requests = membersFileManagerrequests.loadMembers(g.getGroupId());
        UserAccount user2 = null;
        for (UserAccount user : requests) {
            if (user.getUser().getUserId().equals(member.getUser().getUserId())) {
                user2 = user;
            }
        }
        requests.remove(user2);
        membersFileManagerrequests.saveMembers(g.getGroupId(), requests);
    }

    public void appovePost(Post post, Group g) {
        ContentFileManager contentFileManagerrequests = new ContentFileManager("requests_lists", "Requests");
        ContentFileManager contentFileManagerposts = new ContentFileManager("posts_lists", "Posts");
        ArrayList<Post> Postrequests = contentFileManagerrequests.loadPosts(g.getGroupId());
        Post user2 = null;
        for (Post user : Postrequests) {
            if (user.getContentId().equals(post.getContentId())) {
                user2 = user;
            }
        }
        Postrequests.remove(user2);
        contentFileManagerrequests.savePosts(g.getGroupId(), Postrequests);
        ArrayList<Post> posts = contentFileManagerposts.loadPosts(g.getGroupId());
        posts.add(post);
        contentFileManagerposts.savePosts(g.getGroupId(), posts);
        MembersFileManager membersFileManager = new MembersFileManager("Members_lists", "Member");
        ArrayList<UserAccount> members = membersFileManager.loadMembers(g.getGroupId());
        NotificationFileManager notificationManager = new NotificationFileManager();
        for (UserAccount u : members) {
            Notification notification = new Notification("New Group Post", u.getUser().getUserId(), g.getGroupId());
            notificationManager.addNotification(u.getUser().getUserId(), notification);
        }
    }

    public void declinePost(Post post, Group g) {
        ContentFileManager contentFileManagerrequests = new ContentFileManager("requests_lists", "Requests");
        ArrayList<Post> Postrequests = contentFileManagerrequests.loadPosts(g.getGroupId());
        Post user2 = null;
        for (Post user : Postrequests) {
            if (user.getContentId().equals(post.getContentId())) {
                user2 = user;
            }
        }
        Postrequests.remove(user2);
        contentFileManagerrequests.savePosts(g.getGroupId(), Postrequests);
    }

}
