package GroupManagementFrontEnd;

import Account.AccountLoad;
import Account.UserAccount;
import ContentCreation.*;

import GroupManagementBackEnd.*;

import UserAccountManagementBackend.User;
import static UserAccountManagementBackend.getUser.getUser;
import groupDataBase.ContentFileManager;
import groupDataBase.MembersFileManager;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.*;

public class GroupWindow extends javax.swing.JFrame {

    private Group group;
    private ArrayList<UserAccount> accounts;
    private UserAccount account;
    MembersFileManager membersFileManager = new MembersFileManager("Members_lists", "Member");
    MembersFileManager adminsFileManager = new MembersFileManager("Admins_lists","admin");
    ContentFileManager contentFileManager = new ContentFileManager("posts_lists", "Posts");

    public GroupWindow(UserAccount account, Group group) {

        initComponents();
        setVisible(true);

        this.account = account;
        accounts = new AccountLoad().loadAccounts();
        this.group = group;
        if (this.group.getPhoto() != null) {
            this.coverImageAvatar.setImage(new ImageIcon((this.group.getPhoto())));
        } else {
            System.err.println("Cover Image URL is null");
        }
        this.Name.setText(this.group.getName());
        this.description.setText(this.group.getDiscription());
        PostPanel = new JPanel();
        PostPanel.setLayout(new BoxLayout(PostPanel, BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(PostPanel);
        ArrayList<String> adminsIds = new ArrayList<>();
        for (UserAccount user : adminsFileManager.loadMembers(group.getGroupId())) {
            adminsIds.add(user.getUser().getUserId());
        }
        // Ellipsis button (Options)

        JButton optionsButton = new JButton("..."); // Ellipsis for options
        optionsButton.setBackground(Color.decode("#121212"));
        optionsButton.setForeground(Color.WHITE);
        optionsButton.setPreferredSize(new Dimension(20, 15));
        optionsButton.setFocusPainted(false);

        //create the popup menu
        JPopupMenu popupMenu = new JPopupMenu();
        if (adminsIds.contains(account.getUser().getUserId())|| account.getUser().getUserId().equals(group.getOwner().getUser().getUserId())) {
            JMenuItem requestItem = new JMenuItem("Join Requests");
            requestItem.addActionListener(e -> {
               RequestsWindow a = new RequestsWindow(account,group);
            });
            popupMenu.add(requestItem);
             JMenuItem requestPostItem = new JMenuItem("Posts Requests");
            requestPostItem.addActionListener(e -> {
               PostRequestsWindow a = new PostRequestsWindow(account,group);
            });
            popupMenu.add(requestPostItem);
            
        }
        if (account.getUser().getUserId().equals(group.getOwner().getUser().getUserId())) {

            JMenuItem editItem = new JMenuItem("Edit");
            editItem.addActionListener(e -> {
                EditWindow a = new EditWindow(group);
            });
            popupMenu.add(editItem);

        } else {
            JMenuItem leaveItem = new JMenuItem("Leave");
            leaveItem.addActionListener(e -> {
                commonRole c = new commonRole();
                c.leaveGroup(account, group);
            });
            popupMenu.add(leaveItem);
        }
        optionsButton.addActionListener(e -> popupMenu.show(optionsButton, optionsButton.getWidth() / 2, optionsButton.getHeight() / 2));
        mainPanel.add(optionsButton, BorderLayout.EAST);

// Adjust the GroupLayout for positioning the Edit button at the end of the line
        GroupLayout layout = (GroupLayout) mainPanel.getLayout();
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap() // Add padding at the start
                                .addComponent(Name, GroupLayout.PREFERRED_SIZE, 235, GroupLayout.PREFERRED_SIZE) // Name label
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE) // Push Edit button to the far right
                                .addComponent(optionsButton, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE) // Edit button
                                .addContainerGap()) // Add padding at the end
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(coverImageAvatar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addComponent(AddPost, GroupLayout.PREFERRED_SIZE, 805, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(coverImageAvatar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(Name, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(optionsButton, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)) // Align vertically with Name
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(description, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(AddPost, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                                .addComponent(jLabel1, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 329, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
showPosts();
    }

    public UserAccount getAcc(String id) {
        for (UserAccount user : accounts) {
            if (user.getUser().getUserId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public void showPosts() {
        // Clear the existing stories in the panel before adding new ones
        PostPanel.removeAll();

        // Check if there are no stories
        if (contentFileManager.loadPosts(group.getGroupId()).isEmpty()) {
            JLabel noStoriesLabel = new JLabel("No Posts available.");
            noStoriesLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            noStoriesLabel.setForeground(Color.GRAY);
            noStoriesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            PostPanel.add(noStoriesLabel);
        } else {
            // Loop through the list of stories and add each one to the panel
            for (Content content : contentFileManager.loadPosts(group.getGroupId())) {
                if (content instanceof Post) {
                    Post post = (Post) content;
                    CreatePanel(post);
                    PostPanel.add(Box.createVerticalStrut(10));

                }
            }
        }

        // Ensure the UI components are updated and the new components are visible
        PostPanel.revalidate();
        PostPanel.repaint();
    }

    public void CreatePanel(Post post) {
        // Main panel for the post
        JPanel singlePostPanel = new JPanel(new BorderLayout(10, 10)); // Space between components
        singlePostPanel.setBackground(Color.WHITE);
        singlePostPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // Profile Picture
        JLabel profilePictureLabel = new JLabel();
        if (getAcc(post.getAuthorId()) != null) {
            ImageIcon profileIcon = new ImageIcon(getAcc(post.getAuthorId()).getProfile().getProfileImageUrl());
            Image profileImage = profileIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Adjust profile image size
            profilePictureLabel.setIcon(new ImageIcon(profileImage));
        } else {
            profilePictureLabel.setText("No Image"); // Placeholder text if no profile image
            profilePictureLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
        singlePostPanel.add(profilePictureLabel, BorderLayout.WEST);

        // Text Content Panel (Username + Content)
        JPanel textContentPanel = new JPanel();
        textContentPanel.setLayout(new BoxLayout(textContentPanel, BoxLayout.Y_AXIS));
        textContentPanel.setBackground(Color.WHITE);

        // Username
        String username = getAcc(post.getAuthorId()).getUser().getUserName();
        JLabel usernameLabel = new JLabel(username);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // Post Content
        String content = (post.getContent() != null && !post.getContent().isEmpty()) ? post.getContent() : "No content available";
        JLabel contentLabel = new JLabel(content);
        contentLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        // Add username and content to the text panel
        textContentPanel.add(usernameLabel);

        textContentPanel.add(contentLabel);

        singlePostPanel.add(textContentPanel, BorderLayout.CENTER);

        // Ellipsis button (Options)
        JButton optionsButton = new JButton("..."); // Ellipsis for options
        optionsButton.setBackground(Color.decode("#121212"));
        optionsButton.setForeground(Color.WHITE);
        optionsButton.setPreferredSize(new Dimension(20, 15));
        optionsButton.setFocusPainted(false);

        //create the popup menu
        JPopupMenu popupMenu = new JPopupMenu();
        //Add options
        UserAccount user = null;
        if (group.getOwner().getUser().getUserId().equals(account.getUser().getUserId()) || post.getAuthorId().equals(account.getUser().getUserId())) {
            user = account;
        }
        for (UserAccount acc : adminsFileManager.loadMembers(group.getGroupId())) {
            if (acc.getUser().getUserId().equals(account.getUser().getUserId())) {
                user = acc;
            }
        }
        if (user != null) {
            JMenuItem editItem = new JMenuItem("Edit");
            editItem.addActionListener(e -> {
               EditPostWindow a = new EditPostWindow(post,group);
            });
            popupMenu.add(editItem);
            JMenuItem deleteItem = new JMenuItem("Delete");
            deleteItem.addActionListener(e -> {
                commonRole c = new commonRole();
                c.deletePost(post, group);

            });
            popupMenu.add(deleteItem);
            optionsButton.addActionListener(e -> popupMenu.show(optionsButton, optionsButton.getWidth() / 2, optionsButton.getHeight() / 2));
            singlePostPanel.add(optionsButton, BorderLayout.EAST);

        }

        // Image Section (optional, displayed below the text content)
        if (post.getImagePath() != null) {
            JLabel postImageLabel = new JLabel();
            ImageIcon postImageIcon = new ImageIcon(post.getImagePath());
            Image postImage = postImageIcon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH); // Adjust the size as needed
            postImageLabel.setIcon(new ImageIcon(postImage));
            postImageLabel.setHorizontalAlignment(SwingConstants.CENTER);

            // Add the image to the bottom of the singlePostPanel
            singlePostPanel.add(postImageLabel, BorderLayout.SOUTH);
        }

        // Add the post panel to the main post container (postPanel)
        PostPanel.add(singlePostPanel);

        // Refresh the panel to make the new content visible
        PostPanel.revalidate();
        PostPanel.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        mainPanel = new javax.swing.JPanel();
        coverImageAvatar = new ProfileManagementFrontend.CoverImageAvatar();
        cameraButton2 = new javax.swing.JButton();
        Name = new javax.swing.JLabel();
        description = new javax.swing.JLabel();
        AddPost = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        PostPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        admins = new javax.swing.JLabel();
        members = new javax.swing.JLabel();

        jPopupMenu1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPopupMenu1ComponentShown(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Group");

        mainPanel.setBackground(new java.awt.Color(153, 153, 153));

        coverImageAvatar.setBackground(new java.awt.Color(255, 255, 255));
        coverImageAvatar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 4));
        coverImageAvatar.setOpaque(true);

        cameraButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cameraButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/camera.png"))); // NOI18N
        cameraButton2.setBorderPainted(false);
        cameraButton2.setContentAreaFilled(false);
        cameraButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cameraButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout coverImageAvatarLayout = new javax.swing.GroupLayout(coverImageAvatar);
        coverImageAvatar.setLayout(coverImageAvatarLayout);
        coverImageAvatarLayout.setHorizontalGroup(
            coverImageAvatarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(coverImageAvatarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cameraButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        coverImageAvatarLayout.setVerticalGroup(
            coverImageAvatarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, coverImageAvatarLayout.createSequentialGroup()
                .addContainerGap(141, Short.MAX_VALUE)
                .addComponent(cameraButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        Name.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        Name.setText("jLabel1");

        description.setText("jLabel1");

        AddPost.setBackground(new java.awt.Color(255, 255, 255));
        AddPost.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        AddPost.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AddPost.setText("What's on your mind ??");
        AddPost.setOpaque(true);
        AddPost.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                AddPostMousePressed(evt);
            }
        });

        javax.swing.GroupLayout PostPanelLayout = new javax.swing.GroupLayout(PostPanel);
        PostPanel.setLayout(PostPanelLayout);
        PostPanelLayout.setHorizontalGroup(
            PostPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 894, Short.MAX_VALUE)
        );
        PostPanelLayout.setVerticalGroup(
            PostPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 327, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(PostPanel);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Posts");

        admins.setIcon(new javax.swing.ImageIcon(getClass().getResource("/admins.png"))); // NOI18N
        admins.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                adminsMousePressed(evt);
            }
        });

        members.setIcon(new javax.swing.ImageIcon(getClass().getResource("/members.png"))); // NOI18N
        members.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                membersMousePressed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(description, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(AddPost, javax.swing.GroupLayout.PREFERRED_SIZE, 805, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addComponent(coverImageAvatar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(Name, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(members)
                                .addGap(27, 27, 27)
                                .addComponent(admins)
                                .addGap(103, 103, 103)))
                        .addContainerGap())))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(coverImageAvatar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Name, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(admins))
                    .addComponent(members))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(description, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AddPost, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cameraButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cameraButton2ActionPerformed
        // TODO add your handling code here:

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose a cover Photo");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image Files", "jpg", "png", "jpeg"));
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File chosenFile = fileChooser.getSelectedFile();
            String imagePath = chosenFile.getAbsolutePath();
            this.group.setPhoto(imagePath);
            this.coverImageAvatar.setImage(new ImageIcon(imagePath));
            this.coverImageAvatar.repaint();

        }
    }//GEN-LAST:event_cameraButton2ActionPerformed

    private void AddPostMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddPostMousePressed
             AddPostWindow a = new AddPostWindow (account,group);
    }//GEN-LAST:event_AddPostMousePressed

    private void jPopupMenu1ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPopupMenu1ComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_jPopupMenu1ComponentShown

    private void adminsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_adminsMousePressed
        AdminsWindow a = new AdminsWindow(account, group);
    }//GEN-LAST:event_adminsMousePressed

    private void membersMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_membersMousePressed
        MembersWindow a = new MembersWindow(account, group);
    }//GEN-LAST:event_membersMousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GroupWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GroupWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GroupWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GroupWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //  new GroupWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AddPost;
    private javax.swing.JLabel Name;
    private javax.swing.JPanel PostPanel;
    private javax.swing.JLabel admins;
    private javax.swing.JButton cameraButton2;
    private ProfileManagementFrontend.CoverImageAvatar coverImageAvatar;
    private javax.swing.JLabel description;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel members;
    // End of variables declaration//GEN-END:variables
}
