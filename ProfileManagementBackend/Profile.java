/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ProfileManagementBackend;

/**
 *
 * @author hebai
 */
import ProfileManagementFrontend.*;
import Account.UserAccount;
import java.net.URL;
public class Profile {
    private String profileImageUrl;
    private String coverImageUrl;
    private String bio;
    
    private UserAccount account;
    private String userId;
    public Profile(){}
    public Profile(String userId){
        
        this.userId = userId;
        
        URL profileImage = getClass().getResource("/ProfileManagementFrontend/profileImage.png"); // Adjust the path
        URL coverImage = getClass().getResource("/ProfileManagementFrontend/coverPhoto.jpg"); // Adjust the path
    
        System.out.println("Classloader path: " + getClass().getResource("/ProfileManagementFrontend/profileImage.png"));
        System.out.println("Classloader path: " + getClass().getResource("/ProfileManagementFrontend/coverPhoto.jpg"));
          
        this.coverImageUrl = "D:\\java netbeans\\ConnectHub_lab9\\src\\main\\resources\\ProfileManagementFrontend\\newCoverImage.jpeg";
        this.profileImageUrl = "D:\\java netbeans\\ConnectHub_lab9\\src\\main\\resources\\ProfileManagementFrontend\\newProfileImage.jpeg";
        profileSaver.saveProfile(this);
        
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public UserAccount getAccount() {
        return account;
    }

    public void setAccount(UserAccount account) {
        this.account = account;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    
    
    
    
}
