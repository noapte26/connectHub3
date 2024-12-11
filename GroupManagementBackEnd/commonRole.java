/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GroupManagementBackEnd;

import ContentCreation.Post;
import groupDataBase.updateGroup;
import java.util.ArrayList;

/**
 *
 * @author CONNECT
 */
public class commonRole {

    public void editPost(Post post, String newText, String newPhoto,Group g) {
        if (!newText.equals(null)) {
            
            post.setContent(newText);
        }
        if (!newPhoto.equals(null)) {
            
            post.setImagePath(newPhoto);
        }
         new updateGroup(g);
    }
    public void deletePost (Post post, Group g)
   {
       ArrayList posts = g.getContent();
       posts.remove(post);
       g.setContent(posts);
        new updateGroup(g);
   }
    public void addPostRequest  (Post post, Group g)
    {
        ArrayList requestPosts = g.getRequestPosts();
        requestPosts.add(post);
        g.setRequestPosts(requestPosts);
         new updateGroup(g);
    }
   
}
