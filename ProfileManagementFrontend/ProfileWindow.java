/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ProfileManagementFrontend;

/**
 *
 * @author hebai
 */
import java.net.URL;
import java.net.MalformedURLException;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import ProfileManagementBackend.*;
import javax.swing.plaf.FileChooserUI;

import UserAccountManagementBackend.User;
import UserAccountManagementBackend.getUser;

public class ProfileWindow extends javax.swing.JFrame {

    /**
     * Creates new form ProfileWindow
     */
    private Profile profile;
    private UpdateProfile updateProfile;
    
    public ProfileWindow( Profile profile) {
       this.profile = profile;
       this.updateProfile = new UpdateProfile(this.profile);
       initComponents();
 
    
    
    
    if (this.profile.getProfileImageUrl()!= null) {
        this.profileImageAvatar.setImage(new ImageIcon(this.profile.getProfileImageUrl()));
    } else {
        System.err.println("profile Image not found");
        
}
    if (this.profile.getCoverImageUrl()!= null) {
        this.coverImageAvatar.setImage(new ImageIcon(this.profile.getCoverImageUrl()));
    } else {
        System.err.println("profile Image not found");
        
}
    
    
    User user = getUser.getUser(this.profile.getUserId());
    if(user != null ){
        System.out.println("user found to get it's user name");
        this.userNameField.setText(user.getUserName());
    }
    else{
        System.err.println("User not found to get it's user name");
    }
    
   
       
    this.setLocationRelativeTo(null);
    setTitle("Profile");
    this.setVisible(true);
    

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        profileImageAvatar1 = new ProfileManagementFrontend.ProfileImageAvatar();
        connectpanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        editProfileButton = new javax.swing.JButton();
        userNameField = new javax.swing.JLabel();
        coverImageAvatar = new ProfileManagementFrontend.CoverImageAvatar();
        profileImageAvatar = new ProfileManagementFrontend.ProfileImageAvatar();
        cameraButton1 = new javax.swing.JButton();
        cameraButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        connectpanel.setBackground(new java.awt.Color(153, 153, 153));

        jLabel4.setBackground(new java.awt.Color(204, 204, 204));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Connect Hub");

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\hebai\\Downloads\\letter-b(1).png")); // NOI18N
        jLabel1.setText("jLabel1");

        editProfileButton.setBackground(new java.awt.Color(242, 242, 242));
        editProfileButton.setIcon(new javax.swing.ImageIcon("C:\\Users\\hebai\\Downloads\\edit-profile.png")); // NOI18N
        editProfileButton.setBorderPainted(false);
        editProfileButton.setContentAreaFilled(false);
        editProfileButton.setLabel("Edit");
        editProfileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProfileButtonActionPerformed(evt);
            }
        });

        userNameField.setBackground(new java.awt.Color(153, 153, 153));
        userNameField.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        userNameField.setText("jLabel2");
        userNameField.setOpaque(true);

        cameraButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ProfileManagementFrontend/camera.png"))); // NOI18N
        cameraButton1.setBorderPainted(false);
        cameraButton1.setContentAreaFilled(false);
        cameraButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cameraButton1ActionPerformed(evt);
            }
        });
        profileImageAvatar.add(cameraButton1);
        cameraButton1.setBounds(70, 60, 30, 31);

        coverImageAvatar.add(profileImageAvatar);
        profileImageAvatar.setBounds(10, 20, 100, 100);

        cameraButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ProfileManagementFrontend/camera.png"))); // NOI18N
        cameraButton2.setBorderPainted(false);
        cameraButton2.setContentAreaFilled(false);
        cameraButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cameraButton2ActionPerformed(evt);
            }
        });
        coverImageAvatar.add(cameraButton2);
        cameraButton2.setBounds(420, 80, 30, 31);

        javax.swing.GroupLayout connectpanelLayout = new javax.swing.GroupLayout(connectpanel);
        connectpanel.setLayout(connectpanelLayout);
        connectpanelLayout.setHorizontalGroup(
            connectpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(connectpanelLayout.createSequentialGroup()
                .addGap(172, 172, 172)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(168, Short.MAX_VALUE))
            .addGroup(connectpanelLayout.createSequentialGroup()
                .addGroup(connectpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, connectpanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(coverImageAvatar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(connectpanelLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(userNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(editProfileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        connectpanelLayout.setVerticalGroup(
            connectpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(connectpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(connectpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(editProfileButton)
                    .addGroup(connectpanelLayout.createSequentialGroup()
                        .addComponent(coverImageAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(userNameField)
                        .addGap(27, 27, 27)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 341, Short.MAX_VALUE)
                .addGroup(connectpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(connectpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(connectpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cameraButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cameraButton1ActionPerformed
        // TODO add your handling code here:
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose a Profile Photo");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg"));
        int result = fileChooser.showOpenDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
            
            File chosenFile = fileChooser.getSelectedFile();
            String imagePath = chosenFile.getAbsolutePath();
            System.out.println(imagePath);
           
            updateProfile.updateProfileImage(imagePath);
            this.profileImageAvatar.setImage(new ImageIcon(imagePath));
            this.profileImageAvatar.repaint();
            
            
            
        }
        
    }//GEN-LAST:event_cameraButton1ActionPerformed

    private void cameraButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cameraButton2ActionPerformed
        // TODO add your handling code here:
      
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose a cover Photo");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg"));
        int result = fileChooser.showOpenDialog(null);
        if(result == JFileChooser.APPROVE_OPTION){
            File chosenFile = fileChooser.getSelectedFile();
            String imagePath = chosenFile.getAbsolutePath();
            
            updateProfile.updateCoverimage(imagePath);
            this.coverImageAvatar.setImage(new ImageIcon(imagePath));
            this.coverImageAvatar.repaint();
           
            
            
        }
    }//GEN-LAST:event_cameraButton2ActionPerformed

    private void editProfileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProfileButtonActionPerformed
        // TODO add your handling code here:
        EditProfileWindow e= new EditProfileWindow(profile, updateProfile);
        e.setVisible(true);
    }//GEN-LAST:event_editProfileButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cameraButton1;
    private javax.swing.JButton cameraButton2;
    private javax.swing.JPanel connectpanel;
    private ProfileManagementFrontend.CoverImageAvatar coverImageAvatar;
    private javax.swing.JButton editProfileButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private ProfileManagementFrontend.ProfileImageAvatar profileImageAvatar;
    private ProfileManagementFrontend.ProfileImageAvatar profileImageAvatar1;
    private javax.swing.JLabel userNameField;
    // End of variables declaration//GEN-END:variables
}
