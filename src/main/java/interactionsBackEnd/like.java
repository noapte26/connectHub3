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
public class like {
    private UserAccount account ;
    private String contentId ;

    public like(UserAccount account, String contentId) {
        this.account = account;
        this.contentId = contentId;
    }

    public like() {
    }
    

    public UserAccount getAccount() {
        return account;
    }

    public String getContentId() {
        return contentId;
    }
    
    
}
