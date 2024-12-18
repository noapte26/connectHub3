/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TailoredNewsFeedBackend;

/**
 *
 * @author hebai
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.core.type.TypeReference;
import ContentCreation.Content;
import ContentCreation.Post;
import ContentCreation.Story;
import Account.UserAccount;
import Account.GetAccount;
import FriendMangement.BackEnd.FriendListFileManager;
import GroupManagementBackEnd.Group;
import ContentCreation.Post;
import groupDataBase.ContentFileManager;
import groupDataBase.UserGroupsFileManager;


import java.io.*;
import java.util.*;
public class TailoredPosts {
    private Map<String, Post> postDatabase;
    private String userId;
   
    private final ObjectMapper objectMapper;
    public TailoredPosts(String postFilePath,String userId ){
        this.userId = userId;
        
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());  // Register JavaTimeModule for LocalDateTime handling
        postDatabase = new HashMap<>();
        loadPostDatabase(postFilePath);
     
    }
    // Load Posts
    public void loadPostDatabase(String filePath) {
        try (Reader reader = new FileReader(filePath)) {
            postDatabase = objectMapper.readValue(reader, new TypeReference<Map<String, Post>>() {});
            System.out.println("Loaded " + postDatabase.size() + " posts."); // Debugging statement
            for (Map.Entry<String, Post> entry : postDatabase.entrySet()) {
                System.out.println("Post ID: " + entry.getKey() + ", Content: " + entry.getValue().getContent());
            }
        } catch (IOException e) {
            System.err.println("Error loading posts: " + e.getMessage());
            postDatabase = new HashMap<>();
        }
    }
    public List<String> getUserFriends(){
        FriendListFileManager friendListManager = new FriendListFileManager();
        ArrayList<UserAccount> friends = new ArrayList<>();
        friends = friendListManager.loadFriendList(this.userId);
        List<String> friendsIds = new ArrayList<>();
        for(UserAccount friend : friends){
            friendsIds.add(friend.getUser().getUserId());
        }
        return friendsIds;
    }
   public ArrayList<Post> getGroupsPosts(){
        List<Content> groupsPosts = new ArrayList<>();
        UserGroupsFileManager userGroupsFileManager = new UserGroupsFileManager();
        ArrayList<Group> groups = userGroupsFileManager.loadGroups(userId);
        ArrayList<Post> posts = new ArrayList();
        for(Group g: groups){
            ContentFileManager contentFileManager = new ContentFileManager("posts_lists", "Posts");
            ArrayList<Post> p = contentFileManager.loadPosts(g.getGroupId());
            for(Post post:p){
                posts.add(post);
            }
        }
        return posts;
    }
   
    
    // Get all Posts
    public ArrayList<Post> getAllPosts() {
        List<String> friendsIds = getUserFriends();
        ArrayList<Post> posts = new ArrayList<>();
        for (Post content : postDatabase.values()) {
            if(friendsIds.contains(content.getAuthorId()))
               posts.add(content);
        }
        ArrayList<Post> groupsPosts = new ArrayList<>();
        groupsPosts = getGroupsPosts();
        if(groupsPosts != null){
            posts.addAll(groupsPosts);
        }
        return posts;
    }
    
}
