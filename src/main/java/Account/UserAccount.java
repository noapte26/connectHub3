/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Account;

/**
 *
 * @author hebai
 */
import UserAccountManagementBackend.*;
import ProfileManagementBackend.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
public class UserAccount {
     
    private UserAccountManagementBackend.User user;
    private ProfileManagementBackend.Profile profile;

    public UserAccount() {
    }

    public UserAccount(User user, Profile profile) {
        this.user = user;
        this.profile = profile;
    }
   

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    
    
}
