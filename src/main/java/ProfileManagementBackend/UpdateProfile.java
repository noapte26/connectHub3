/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ProfileManagementBackend;

/**
 *
 * @author hebai
 */
import Account.UpdateAccount;
import Account.UserAccount;
import java.net.URL;
import UserAccountManagementBackend.User;
import UserAccountManagementBackend.passwordHasher;
import UserAccountManagementBackend.UpdateUser;
import UserAccountManagementBackend.getUser;

public class UpdateProfile {

    private Profile profile;
    private UserAccount account;

    public UpdateProfile(Profile profile, UserAccount account) {
        this.profile = profile;
        this.account = account;
    }

    public void updateCoverimage(String coverImageUrl) {
        this.profile.setCoverImageUrl(coverImageUrl);
        this.account.getProfile().setCoverImageUrl(coverImageUrl);
        ProfileSaveAfterUpdate.save(profile);
        UpdateAccount u = new UpdateAccount();
        u.save(account);
    }

    public void updateProfileImage(String profileImageUrl) {
        this.profile.setProfileImageUrl(profileImageUrl);
        this.account.getProfile().setProfileImageUrl(profileImageUrl);
        ProfileSaveAfterUpdate.save(profile);
        UpdateAccount u = new UpdateAccount();
        u.save(account);
    }

    public void updateBio(String bio) {
        this.profile.setBio(bio);
        this.account.getProfile().setBio(bio);
        ProfileSaveAfterUpdate.save(profile);
        UpdateAccount u = new UpdateAccount();
        u.save(account);
    }

    public void updatepassword(String password) {
        String hashedPassword = passwordHasher.hashPassword(password);
        User user = getUser.getUser(this.profile.getUserId());
        if (user != null) {
            user.setPassword(hashedPassword);
            this.account.getUser().setPassword(hashedPassword);
            UpdateUser.updateUserInFile(user);
            UpdateAccount u = new UpdateAccount();
            u.save(account);
        }

    }

    public void updateUserName(String userName) {
        User user = getUser.getUser(this.profile.getUserId());
        if (user != null) {
            user.setUserName(userName);
            this.account.getUser().setUserName(userName);
            UpdateUser.updateUserInFile(user);
            UpdateAccount u = new UpdateAccount();
            u.save(account);
        }
    }

}
