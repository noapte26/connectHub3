/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Account;

/**
 *
 * @author hebai
 */
import GroupManagementBackEnd.Group;
import UserAccountManagementBackend.*;
import ProfileManagementBackend.*;
import java.util.ArrayList;
public class UserAccount {
     
    private UserAccountManagementBackend.User user;
    private ProfileManagementBackend.Profile profile;
    private ArrayList <Group> groups ;
    public UserAccount() {
    }

    public UserAccount(User user, Profile profile) {
        this.user = user;
        this.profile = profile;
        groups = new ArrayList<>();
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }

    public ArrayList<Group> getGroups() {
        return groups;
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
