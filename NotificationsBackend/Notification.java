/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NotificationsBackend;

/**
 *
 * @author hebai
 */
import ProfileManagementBackend.Profile;
public class Notification {
    private String type;
    private String recipientId;
    private String actionId;

    public Notification(){}
    public Notification(String type, String recipientId, String actionId) {
        this.type = type;
        this.recipientId = recipientId;
        this.actionId = actionId;
        
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

  
}
