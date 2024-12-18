/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chatsBackEnd;

import Account.UserAccount;

/**
 *
 * @author CONNECT
 */
public class Builder {
    private String chatId;
    private UserAccount sender;
     private String messageText = ""; // Setting the default value
    private String imagePath = ""; // Setting the default value

    public Builder(String chatId, UserAccount sender) {
        this.chatId = chatId;
        this.sender = sender;
    }

    
    
    // Setters for each attribute that return the Builder instance
    

    public Builder setMessageText(String messageText) {
        this.messageText = messageText;
        return this;
    }

    public Builder setImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    // Build method to create an instance of ChatMessage
    
    
    public message build() {
        return new message(chatId, sender, messageText, imagePath);
    }
}

