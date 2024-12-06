/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ProfileManagementBackend;

/**
 *
 * @author hebai
 */

import java.net.URL;
import UserAccountManagementBackend.User;
import UserAccountManagementBackend.passwordHasher;
import UserAccountManagementBackend.UpdateUser;
import UserAccountManagementBackend.getUser;


public class UpdateProfile {
    
    private Profile profile;
    public UpdateProfile(Profile profile){
        this.profile = profile;
    }
    public void updateCoverimage(String coverImageUrl){
        this.profile.setCoverImageUrl(coverImageUrl);
        ProfileSaveAfterUpdate.save(profile);
                
    }
    public void updateProfileImage(String profileImageUrl){
        this.profile.setProfileImageUrl(profileImageUrl);
        ProfileSaveAfterUpdate.save(profile);
    }
    public void updateBio(String bio){
        this.profile.setBio(bio);
        ProfileSaveAfterUpdate.save(profile);
    }
    public void updatepassword(String password){
        String hashedPassword = passwordHasher.hashPassword(password);
        User user = getUser.getUser(this.profile.getUserId());
        if(user != null){
            user.setPassword(hashedPassword);
            UpdateUser.updateUserInFile(user);
        }
        
        
    }
    public void updateUserName(String userName){
        User user = getUser.getUser(this.profile.getUserId());
        if(user != null){
            user.setUserName(userName);
            UpdateUser.updateUserInFile(user);
        }
    }
    
}
