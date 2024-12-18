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
import java.io.FileNotFoundException;
import java.net.URL;

public class Profile {

    private String profileImageUrl;
    private String coverImageUrl;
    private String bio;
    private UserAccount account;
    private String userId;

    public Profile() {
    }

    public Profile(String userId) {

        this.userId = userId;

        URL profileImage = Profile.class.getResource("/profile.jpg"); // Adjust the path
        URL coverImage = Profile.class.getResource("/profile.jpg"); // Adjust the path
      
        System.out.println("Classloader path: " + profileImage.toString());
        System.out.println("Classloader path: " + profileImage.toString());
        System.out.println(profileImage.toString().replace("/", "//").replace("file:", ""));

        this.coverImageUrl =profileImage.toString().replace("file:", "");
        this.profileImageUrl = profileImage.toString().replace("file:", "");
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
