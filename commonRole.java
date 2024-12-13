/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GroupManagementBackEnd;

import Account.UpdateAccount;
import Account.UserAccount;
import ContentCreation.Post;
import groupDataBase.*;

import java.util.ArrayList;

/**
 *
 * @author CONNECT
 */
public class commonRole {

    public void editPost(Post post, String newText, String newPhoto, Group g) {
        ContentFileManager contentFileManager = new ContentFileManager("posts_lists", "Posts");
        ArrayList<Post> posts = contentFileManager.loadPosts(g.getGroupId());
        Post p = null;
        for (Post p1 : posts) {
            if (p1.getContentId().equals(post.getContentId())) {
                p = p1;
                break;
            }
        }
        Post user2 = null;
        for (Post user : posts) {
            if (user.getContentId().equals(post.getContentId())) {
                user2 = user;
            }
        }
        posts.remove(user2);

        if (newText != null) {

            user2.setContent(newText);
        }
        if (newPhoto != null) {

            user2.setImagePath(newPhoto);
        }
        posts.add(user2);
        contentFileManager.savePosts(g.getGroupId(), posts);

    }

    public void deletePost(Post post, Group g) {
        ContentFileManager contentFileManagerposts = new ContentFileManager("posts_lists", "Posts");
        ArrayList<Post> posts = contentFileManagerposts.loadPosts(g.getGroupId());
        Post user2 = null;
        for (Post user : posts) {
            if (user.getContentId().equals(post.getContentId())) {
                user2 = user;
            }
        }
        posts.remove(user2);
        contentFileManagerposts.savePosts(g.getGroupId(), posts);
    }

    public void addPostRequest(Post post, Group g) {
        ContentFileManager contentFileManagerrequests = new ContentFileManager("requests_lists", "Requests");
        ArrayList<Post> requestPosts = contentFileManagerrequests.loadPosts(g.getGroupId());
        requestPosts.add(post);
        contentFileManagerrequests.savePosts(g.getGroupId(), requestPosts);
    }

    public void leaveGroup(UserAccount user, Group group) {
        MembersFileManager membersFileManagermember = new MembersFileManager("Members_lists", "Member");
        MembersFileManager membersFileManageradmin = new MembersFileManager("Admins_lists", "admin");
        UserAccount account = null;
        ArrayList<UserAccount> admins = membersFileManageradmin.loadMembers(group.getGroupId());
        ArrayList<UserAccount> members = membersFileManagermember.loadMembers(group.getGroupId());
        for (UserAccount user1 : admins) {
            if (user1.getUser().getUserId().equals(user.getUser().getUserId())) {
                account = user;
            }
        }
        if (account != null) {
            UserAccount user2 = null;
            for (UserAccount user55 : admins) {
                if (user55.getUser().getUserId().equals(user.getUser().getUserId())) {
                    user2 = user55;
                }
            }
            admins.remove(user2);
            membersFileManageradmin.saveMembers(group.getGroupId(), admins);

        } else {
             UserAccount user2 = null;
            for (UserAccount user55 : members) {
                if (user55.getUser().getUserId().equals(user.getUser().getUserId())) {
                    user2 = user55;
                }
            }
            members.remove(user2);
            membersFileManagermember.saveMembers(group.getGroupId(), members);
        }

        UserGroupsFileManager userGroupsFileManager = new UserGroupsFileManager();
        ArrayList<Group> groups = userGroupsFileManager.loadGroups(user.getUser().getUserId());
        Group user2 = null;
        for (Group user3 : groups) {
            if (user3.getGroupId().equals(group.getGroupId())) {
                user2 = user3;
            }
        }
        groups.remove(user2);
        userGroupsFileManager.saveGroups(user.getUser().getUserId(), groups);
    }

}
