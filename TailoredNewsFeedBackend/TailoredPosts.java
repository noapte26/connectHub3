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
    public List<Content> getGroupsPosts(){
        List<Content> groupsPosts = new ArrayList<>();
        ArrayList<Group> groups = new ArrayList<>();
        UserAccount user = GetAccount.getAccount(userId);
        if(user != null){
            groups = user.getGroups();
        
            for(Group group: groups){
                for(Post post: group.getContent()){
                    groupsPosts.add(post);
                }
            }
            return groupsPosts;
      }
        return null;
    }
   
    
    // Get all Posts
    public List<Content> getAllPosts() {
        List<String> friendsIds = getUserFriends();
        List<Content> posts = new ArrayList<>();
        for (Content content : postDatabase.values()) {
            if(friendsIds.contains(content.getAuthorId()))
               posts.add(content);
        }
        List groupsPosts = new ArrayList<>();
        groupsPosts = getGroupsPosts();
        if(groupsPosts != null){
            posts.addAll(groupsPosts);
        }
        return posts;
    }
    
}
