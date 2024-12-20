/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ProfileManagementFrontend;

/**
 *
 * @author hebai
 */
import Account.GetAccount;
import Account.UserAccount;
import ContentCreation.Content;
import ContentCreation.Post;
import ContentCreation.Story;
import ContentCreation.backendContent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.net.MalformedURLException;
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

import GroupManagementBackEnd.commonRole;
import GroupManagementFrontEnd.EditPostWindow;
import InteractionFrontEnd.commentsWindow;
import InteractionFrontEnd.likesWindow;
import NotificationsBackend.Notification;
import NotificationsBackend.NotificationFileManager;
import ProfileManagementBackend.*;
import javax.swing.plaf.FileChooserUI;

import UserAccountManagementBackend.User;
import UserAccountManagementBackend.getUser;
import interactionsBackEnd.like;
import interactionsDataBase.likeFileManeger;

import static UserAccountManagementBackend.getUser.getUser;

public class ProfileWindow extends javax.swing.JFrame {

    private JPanel storyPanel;
    private JPanel postPanel;
    public String authorId;
    private java.util.List<Content> stories;
    private java.util.List<Content> posts;
    private Profile profile;
    private UpdateProfile updateProfile;
    private UserAccount account;

    public ProfileWindow(Profile profile, UserAccount account) {
        this.profile = profile;
        this.account = account;
        this.updateProfile = new UpdateProfile(this.profile, account);
        initComponents();

        if (this.profile.getProfileImageUrl() != null) {
            this.profileImageAvatar.setImage(new ImageIcon(this.profile.getProfileImageUrl()));
        } else {
            System.err.println("Profile Image URL is null");
        }

        if (this.profile.getCoverImageUrl() != null) {
            this.coverImageAvatar.setImage(new ImageIcon((this.profile.getCoverImageUrl())));
        } else {
            System.err.println("Cover Image URL is null");
        }

        User user = getUser.getUser(this.profile.getUserId());
        if (user != null) {
            System.out.println("user found to get it's user name");
            this.userNameField.setText(user.getUserName());
            System.out.println(profile.getBio());
            this.bio.setText(profile.getBio());
        } else {
            System.err.println("User not found to get it's user name");
        }

        this.setLocationRelativeTo(null);
        setTitle("Profile");
        this.setVisible(true);
        
        backendContent backend = new backendContent("post_database.json", "story_database.json");
        stories = backend.getAllStories();  // Load stories correctly
        posts = backend.getAllPosts();

        // Initialize storyPanel with BoxLayout
        storyPanel = new JPanel();
        storyPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Set horizontal alignment
        jScrollPane1.setViewportView(storyPanel);

        postPanel = new JPanel();
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
        jScrollPane2.setViewportView(postPanel);

        // Show stories
        showStories();
        showPosts();

    }

   
   
    public void showStories() {
        // Clear the existing stories in the panel before adding new ones
        storyPanel.removeAll();

        // Check if there are no stories
        if (stories.isEmpty()) {
            JLabel noStoriesLabel = new JLabel("No stories available.");
            noStoriesLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            noStoriesLabel.setForeground(Color.GRAY);
            noStoriesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            storyPanel.add(noStoriesLabel);
        } else {
            // Loop through the list of stories and add each one to the panel
            for (Content content : stories) {
                if (content instanceof Story && content.getAuthorId().equals(account.getUser().getUserId())) {
                    Story story = (Story) content;
                    createStoryPanel(story);

                }
            }
        }

        // Ensure the UI components are updated and the new components are visible
        storyPanel.revalidate();
        storyPanel.repaint();
    }

    public void showPosts() {
        // Clear the existing stories in the panel before adding new ones
        postPanel.removeAll();

        // Check if there are no stories
        if (posts.isEmpty()) {
            JLabel noStoriesLabel = new JLabel("No Posts available.");
            noStoriesLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            noStoriesLabel.setForeground(Color.GRAY);
            noStoriesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            postPanel.add(noStoriesLabel);
        } else {
            // Loop through the list of stories and add each one to the panel
            for (Content content : posts) {
                if (content instanceof Post&& content.getAuthorId().equals(account.getUser().getUserId())) {
                    Post post = (Post) content;
                    createPostPanel(post);
                    postPanel.add(Box.createVerticalStrut(10));

                }
            }
        }

        // Ensure the UI components are updated and the new components are visible
        postPanel.revalidate();
        postPanel.repaint();
    }

    private void createStoryPanel(Story story) {
        // Create a single story container
        JPanel singleStoryPanel = new JPanel(new BorderLayout(1, 1)); // Margin between elements
        singleStoryPanel.setBackground(Color.LIGHT_GRAY);
        singleStoryPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1)); // Padding

        // Text content
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.LIGHT_GRAY);

        // Username
        String username = account.getUser().getUserName();
        JLabel usernameLabel = new JLabel(username);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameLabel.setForeground(Color.BLACK);

        // Story content
        String content = (story.getContent() != null && !story.getContent().isEmpty()) ? story.getContent() : "";
        JLabel contentLabel = new JLabel(content);
        contentLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        contentLabel.setForeground(Color.BLACK);

        textPanel.add(usernameLabel);
        textPanel.add(Box.createVerticalStrut(5)); // Add spacing between username and content
        textPanel.add(contentLabel);

        singleStoryPanel.add(textPanel, BorderLayout.CENTER);

        // Image (if available)
        if (story.getImagePath() != null) {
            ImageIcon storyImageIcon = new ImageIcon(story.getImagePath());
            // Scale the image to a smaller size to fit within the panel
            int scaledWidth = 100;  // Set desired width
            int scaledHeight = 50; // Set desired height
            Image scaledImage = storyImageIcon.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
            JLabel storyImageLabel = new JLabel(new ImageIcon(scaledImage));
            storyImageLabel.setHorizontalAlignment(SwingConstants.CENTER);

            // Add the scaled image to the bottom of the single story panel
            singleStoryPanel.add(storyImageLabel, BorderLayout.SOUTH);
        }

        // Add the single story panel to the storyPanel container
        storyPanel.add(singleStoryPanel);

        // Refresh to display the new story immediately
        storyPanel.revalidate();
        storyPanel.repaint();
    }

    public void createPostPanel(Post post) {
        // Main panel for the post
        JPanel singlePostPanel = new JPanel(new BorderLayout(10, 10)); // Space between components
        singlePostPanel.setBackground(Color.WHITE);
        singlePostPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // Profile Picture
        JLabel profilePictureLabel = new JLabel();
        if (account != null) {
            ImageIcon profileIcon = new ImageIcon(account.getProfile().getProfileImageUrl());
            Image profileImage = profileIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Adjust profile image size
            profilePictureLabel.setIcon(new ImageIcon(profileImage));
        } else {
            profilePictureLabel.setText("No Image"); // Placeholder text if no profile image
            profilePictureLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
        JPanel postautherdata = new JPanel(new BorderLayout(10, 10));
        postautherdata.add(profilePictureLabel,BorderLayout.WEST);
        postautherdata.setBackground(Color.WHITE);
        // Text Content Panel (Username + Content + Buttons)
        JPanel textContentPanel = new JPanel(new BorderLayout(10, 10)); // Space between sections
        textContentPanel.setBackground(Color.WHITE);

        // Username
        String username = account.getUser().getUserName();
        JLabel usernameLabel = new JLabel(username);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        postautherdata.add(usernameLabel,BorderLayout.CENTER);
        // Post Content
        String content = (post.getContent() != null && !post.getContent().isEmpty()) ? post.getContent() : "No content available";
        JLabel contentLabel = new JLabel(content);
        contentLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        // Like and Comment Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Small gap between buttons
        //buttonPanel.setBackground(Color.decode("#1E1E1E"));
        JLabel likes1=new JLabel("likes");
        likes1.setFont(new Font("Arial", Font.BOLD, 14));
        buttonPanel.add(likes1,BorderLayout.NORTH);
        JButton likeButton = new JButton("Like");
        JButton commentButton = new JButton("Comment");
        likeFileManeger l=new likeFileManeger("Likes");
        ArrayList<like>likes=l.loadLike(post.getContentId());
        JPanel buttonPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        like li=null;
        for(like l1:likes)
        {
            if(l1.getAccount().getUser().getUserId().equals(account.getUser().getUserId()))
            {
                li=l1;
            }
        }
        if(li!=null)
        {
            likeButton.setBackground(Color.decode("#1877F2"));
        }
        else {
            likeButton.setBackground(Color.decode("#1E1E1E"));
        }
        likeButton.setForeground(Color.WHITE);
        likeButton.setPreferredSize(new Dimension(400, 30));
        likeButton.setFocusPainted(false);

        commentButton.setBackground(Color.decode("#1E1E1E"));
        commentButton.setForeground(Color.WHITE);
        commentButton.setPreferredSize(new Dimension(400, 30));
        commentButton.setFocusPainted(false);
        likes1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        likes1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                likesWindow lw=new likesWindow(post);
            }
        });
        likeButton.addActionListener(e -> {
            like ll = new like(account, post.getContentId());
            if(likeButton.getBackground().equals(Color.decode("#1E1E1E"))) {
                likes.add(ll);
                l.saveLike(post.getContentId(), likes);
            }
            else{
                like lll=null;
                for(like l2:likes)
                {
                    if(l2.getAccount().getUser().getUserId().equals(account.getUser().getUserId()))
                    {
                        lll=l2;
                    }
                }
                likes.remove(lll);
                l.saveLike(post.getContentId(),likes);
            }
            showPosts();
             NotificationFileManager notificationFileManager = new NotificationFileManager();
            UserAccount postOwner = GetAccount.getAccount(post.getAuthorId());
            String type = "Post Like";
            Notification notification = new Notification(type, postOwner.getUser().getUserId(), authorId , post, null);
            notificationFileManager.addNotification(postOwner.getUser().getUserId(), notification);
        });
        commentButton.addActionListener(e -> {
            commentsWindow c=new commentsWindow(post,account);
        });

        buttonPanel2.add(likeButton);
        JLabel label2=new JLabel("      ");
        buttonPanel2.add(label2);
        buttonPanel2.add(commentButton);
        buttonPanel.add(buttonPanel2,BorderLayout.SOUTH);
        // Add components to textContentPanel
        textContentPanel.add(postautherdata, BorderLayout.NORTH); // Username at the top
        textContentPanel.add(contentLabel, BorderLayout.CENTER); // Content in the middle
        // Image Section (optional, displayed below the text content)
        if (post.getImagePath() != null) {
            JLabel postImageLabel = new JLabel();
            ImageIcon postImageIcon = new ImageIcon(post.getImagePath());
            Image postImage = postImageIcon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH); // Adjust the size as needed
            postImageLabel.setIcon(new ImageIcon(postImage));
            postImageLabel.setHorizontalAlignment(SwingConstants.CENTER);

            // Add the image to the bottom of the singlePostPanel
            textContentPanel.add(postImageLabel, BorderLayout.SOUTH);
        }
        singlePostPanel.add(textContentPanel,BorderLayout.CENTER);
        singlePostPanel.add(buttonPanel,BorderLayout.SOUTH);
        // Add the post panel to the main post container (postPanel)
        postPanel.add(singlePostPanel);
        // Refresh the panel to make the new content visible
        postPanel.revalidate();
        postPanel.repaint();
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
        cameraButton2 = new javax.swing.JButton();
        profileImageAvatar = new ProfileManagementFrontend.ProfileImageAvatar();
        cameraButton1 = new javax.swing.JButton();
        bio = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        connectpanel.setBackground(new java.awt.Color(153, 153, 153));

        jLabel4.setBackground(new java.awt.Color(204, 204, 204));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("AN Hub");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/b..png"))); // NOI18N

        editProfileButton.setBackground(new java.awt.Color(242, 242, 242));
        editProfileButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edit.png"))); // NOI18N
        editProfileButton.setBorderPainted(false);
        editProfileButton.setContentAreaFilled(false);
        editProfileButton.setLabel("Edit");
        editProfileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editProfileButtonActionPerformed(evt);
            }
        });

        userNameField.setBackground(new java.awt.Color(153, 153, 153));
        userNameField.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        userNameField.setText("jLabel2");
        userNameField.setOpaque(true);

        cameraButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cameraButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/camera.png"))); // NOI18N
        cameraButton2.setBorderPainted(false);
        cameraButton2.setContentAreaFilled(false);
        cameraButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cameraButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout profileImageAvatarLayout = new javax.swing.GroupLayout(profileImageAvatar);
        profileImageAvatar.setLayout(profileImageAvatarLayout);
        profileImageAvatarLayout.setHorizontalGroup(
            profileImageAvatarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 92, Short.MAX_VALUE)
        );
        profileImageAvatarLayout.setVerticalGroup(
            profileImageAvatarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 91, Short.MAX_VALUE)
        );

        cameraButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cameraButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/camera.png"))); // NOI18N
        cameraButton1.setBorderPainted(false);
        cameraButton1.setContentAreaFilled(false);
        cameraButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cameraButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout coverImageAvatarLayout = new javax.swing.GroupLayout(coverImageAvatar);
        coverImageAvatar.setLayout(coverImageAvatarLayout);
        coverImageAvatarLayout.setHorizontalGroup(
            coverImageAvatarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(coverImageAvatarLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(profileImageAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cameraButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cameraButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        coverImageAvatarLayout.setVerticalGroup(
            coverImageAvatarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, coverImageAvatarLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(cameraButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, coverImageAvatarLayout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addGroup(coverImageAvatarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, coverImageAvatarLayout.createSequentialGroup()
                        .addComponent(cameraButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, coverImageAvatarLayout.createSequentialGroup()
                        .addComponent(profileImageAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16))))
        );

        bio.setText("jLabel2");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel2.setText("Your Stories");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel3.setText("Your Posts");

        javax.swing.GroupLayout connectpanelLayout = new javax.swing.GroupLayout(connectpanel);
        connectpanel.setLayout(connectpanelLayout);
        connectpanelLayout.setHorizontalGroup(
            connectpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(connectpanelLayout.createSequentialGroup()
                .addGroup(connectpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(connectpanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(coverImageAvatar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(connectpanelLayout.createSequentialGroup()
                        .addGap(198, 198, 198)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(connectpanelLayout.createSequentialGroup()
                        .addGroup(connectpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(connectpanelLayout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(userNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(connectpanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2)))
                        .addGap(18, 18, 18)
                        .addGroup(connectpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(connectpanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(editProfileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(connectpanelLayout.createSequentialGroup()
                                .addComponent(bio, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 210, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(connectpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
            .addComponent(jScrollPane2)
        );
        connectpanelLayout.setVerticalGroup(
            connectpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(connectpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(coverImageAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(connectpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(connectpanelLayout.createSequentialGroup()
                        .addGroup(connectpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(userNameField)
                            .addComponent(bio))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2))
                    .addGroup(connectpanelLayout.createSequentialGroup()
                        .addGap(0, 34, Short.MAX_VALUE)
                        .addComponent(editProfileButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(connectpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1)))
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
        if (result == JFileChooser.APPROVE_OPTION) {

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
        if (result == JFileChooser.APPROVE_OPTION) {
            File chosenFile = fileChooser.getSelectedFile();
            String imagePath = chosenFile.getAbsolutePath();

            updateProfile.updateCoverimage(imagePath);
            this.coverImageAvatar.setImage(new ImageIcon(imagePath));
            this.coverImageAvatar.repaint();

        }
    }//GEN-LAST:event_cameraButton2ActionPerformed

    private void editProfileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editProfileButtonActionPerformed
        // TODO add your handling code here:
        EditProfileWindow e = new EditProfileWindow(profile, updateProfile);
        e.setVisible(true);
    }//GEN-LAST:event_editProfileButtonActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bio;
    private javax.swing.JButton cameraButton1;
    private javax.swing.JButton cameraButton2;
    private javax.swing.JPanel connectpanel;
    private ProfileManagementFrontend.CoverImageAvatar coverImageAvatar;
    private javax.swing.JButton editProfileButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private ProfileManagementFrontend.ProfileImageAvatar profileImageAvatar;
    private ProfileManagementFrontend.ProfileImageAvatar profileImageAvatar1;
    private javax.swing.JLabel userNameField;
    // End of variables declaration//GEN-END:variables
}
