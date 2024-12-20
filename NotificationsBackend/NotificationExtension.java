/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NotificationsBackend;

/**
 *
 * @author hebai
 */
import ContentCreation.Post;
public class NotificationExtension extends Notification {
    private Post post;
    public NotificationExtension(String type, String recipientId, String actionId,Post post, String id){
        super(type, recipientId, actionId, post, id);
        this.post = post;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
