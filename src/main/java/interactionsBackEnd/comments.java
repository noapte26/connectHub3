/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interactionsBackEnd;

import Account.UserAccount;

/**
 *
 * @author CONNECT
 */
public class comments {
    private UserAccount account ;
    private String contentId ;
    private String commentText ;

    public comments(UserAccount account, String contentId, String commentText) {
        this.account = account;
        this.contentId = contentId;
        this.commentText = commentText;
    }

    public comments() {
    }

    public UserAccount getAccount() {
        return account;
    }

    public String getContentId() {
        return contentId;
    }

    public String getCommentText() {
        return commentText;
    }
    
}
