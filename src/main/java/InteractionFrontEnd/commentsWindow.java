/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package InteractionFrontEnd;

import Account.GetAccount;
import Account.UserAccount;
import ContentCreation.Content;
import ContentCreation.Post;
import GroupManagementBackEnd.commonRole;
import GroupManagementFrontEnd.EditPostWindow;
import NotificationsBackend.Notification;
import NotificationsBackend.NotificationFileManager;
import interactionsBackEnd.comments;
import interactionsDataBase.commentFileManeger;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class commentsWindow extends javax.swing.JFrame {

    private Post post;
    private UserAccount account;
    commentFileManeger commentfileManager;
    public commentsWindow(Post post,UserAccount account) {
        initComponents();
        this.post=post;
        this.account=account;
        setVisible(true);
        commentsPanel=new JPanel();
        commentsPanel.setLayout(new BoxLayout(commentsPanel, BoxLayout.Y_AXIS));
        jScrollPane2.setViewportView(commentsPanel);
        commentfileManager=new commentFileManeger("comments");
        showcomments();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        commentsPanel = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        sendcomment = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Comments");

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Comments");

        javax.swing.GroupLayout commentsPanelLayout = new javax.swing.GroupLayout(commentsPanel);
        commentsPanel.setLayout(commentsPanelLayout);
        commentsPanelLayout.setHorizontalGroup(
            commentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 495, Short.MAX_VALUE)
        );
        commentsPanelLayout.setVerticalGroup(
            commentsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 469, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(commentsPanel);

        jTextField1.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(51, 51, 51));
        jTextField1.setText("Add comment");
        jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTextField1MousePressed(evt);
            }
        });
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        sendcomment.setBackground(new java.awt.Color(153, 153, 153));
        sendcomment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-sent-48.png"))); // NOI18N
        sendcomment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendcommentActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jTextField1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendcomment)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sendcomment)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public void showcomments() {
        // Clear the existing stories in the panel before adding new ones
        commentsPanel.removeAll();

        // Check if there are no stories
        if (commentfileManager.loadComment(post.getContentId()).isEmpty()){
            System.out.println("1111");
            JLabel noStoriesLabel = new JLabel("No comments available.");
            noStoriesLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            noStoriesLabel.setForeground(Color.GRAY);
            noStoriesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            commentsPanel.add(noStoriesLabel);
        } else {
            // Loop through the list of stories and add each one to the panel
            for (comments comment : commentfileManager.loadComment(post.getContentId())) {
                System.out.println(comment.getCommentText());
                    createcommentsPanel(comment);
                    commentsPanel.add(Box.createVerticalStrut(10));
            }
        }

        // Ensure the UI components are updated and the new components are visible
        commentsPanel.revalidate();
        commentsPanel.repaint();
    }
    public void createcommentsPanel(comments comment)
    {
        // Main panel for the post
        JPanel singlePostPanel = new JPanel(new BorderLayout(10, 10)); // Space between components
        singlePostPanel.setBackground(Color.WHITE);
        singlePostPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // Profile Picture
        JLabel profilePictureLabel = new JLabel();
        if (comment.getAccount().getProfile().getProfileImageUrl() != null) {
            ImageIcon profileIcon = new ImageIcon(comment.getAccount().getProfile().getProfileImageUrl() );
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
        String username = comment.getAccount().getUser().getUserName();
        JLabel usernameLabel = new JLabel(username);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        postautherdata.add(usernameLabel,BorderLayout.CENTER);
        // Post Content
        String content = (comment.getCommentText() != null ) ? comment.getCommentText() : "No content available";
        JLabel contentLabel = new JLabel(content);
        contentLabel.setFont(new Font("Arial", Font.PLAIN, 12));


        // Add components to textContentPanel
        textContentPanel.add(postautherdata, BorderLayout.NORTH); // Username at the top
        textContentPanel.add(contentLabel, BorderLayout.CENTER); // Content in the middle


        singlePostPanel.add(textContentPanel,BorderLayout.CENTER);
        // Add the post panel to the main post container (postPanel)
        commentsPanel.add(singlePostPanel);
        // Refresh the panel to make the new content visible
        commentsPanel.revalidate();
        commentsPanel.repaint();
    }
    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed

    }//GEN-LAST:event_jTextField1ActionPerformed

    private void sendcommentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendcommentActionPerformed
        String comment=jTextField1.getText();
        comments c=new comments(account,post.getContentId(),comment);
        ArrayList<comments>comments=commentfileManager.loadComment(post.getContentId());
        comments.add(c);
        commentfileManager.saveComment(post.getContentId(),comments);
        jTextField1.setText("");
        
        NotificationFileManager notificationFileManager = new NotificationFileManager();
        UserAccount postOwner = GetAccount.getAccount(post.getAuthorId());
        String type = "Post Comment";
        Notification notification = new Notification(type, postOwner.getUser().getUserId(), account.getUser().getUserId(), post, null);
        notificationFileManager.addNotification(postOwner.getUser().getUserId(), notification);
        
        showcomments();
    }//GEN-LAST:event_sendcommentActionPerformed

    private void jTextField1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MousePressed
        jTextField1.setText("");
    }//GEN-LAST:event_jTextField1MousePressed

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
            java.util.logging.Logger.getLogger(commentsWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(commentsWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(commentsWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(commentsWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
              //  new commentsWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel commentsPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton sendcomment;
    // End of variables declaration//GEN-END:variables
}
