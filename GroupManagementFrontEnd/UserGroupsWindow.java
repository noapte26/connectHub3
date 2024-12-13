/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GroupManagementFrontEnd;

import Account.UpdateAccount;
import Account.UserAccount;
import GroupManagementBackEnd.Group;
import GroupManagementBackEnd.commonRole;
import GroupManagementBackEnd.ownerRole;
import groupDataBase.UserGroupsFileManager;
import groupDataBase.deleteGroup;
import groupDataBase.groupLoad;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 *
 * @author CONNECT
 */
public class UserGroupsWindow extends javax.swing.JFrame {

    UserAccount account;
    HashSet<Group> Groups;
    UserGroupsFileManager userGroupsFileManager=new UserGroupsFileManager();
    public UserGroupsWindow(UserAccount account) {
        initComponents();
        setVisible(true);

        groupLoad a = new groupLoad();
        Groups = a.loadGroups();
        this.account = account;
        UserGroupsPanel.setLayout(new BoxLayout(UserGroupsPanel, BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(UserGroupsPanel);
        SuggestedGroupsPanel.setLayout(new BoxLayout(SuggestedGroupsPanel, BoxLayout.Y_AXIS));
        jScrollPane2.setViewportView(SuggestedGroupsPanel);
       
      
            for (Group group : Groups) {
                if (group.getOwner().getUser().getUserId().equals(account.getUser().getUserId()))
                {  
                 creategroupsLabel(group);
                 UserGroupsPanel.add(Box.createVerticalStrut(10));
                 System.out.println("Group Id :" + group.getGroupId());
                }  
                else 
                { 
                createSuggestedgroupsLabel(group);
                SuggestedGroupsPanel.add(Box.createVerticalStrut(10));
             //   System.out.println("Group Id :" + group.getGroupId());
                }
            }
     
    }

    public void createSuggestedgroupsLabel(Group group) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.decode("#121212"));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // Profile Picture
        JLabel profilePictureLabel = new JLabel();
        ImageIcon profileIcon = new ImageIcon(group.getPhoto());
        Image profileImage = profileIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        profilePictureLabel.setIcon(new ImageIcon(profileImage));

        panel.add(profilePictureLabel, BorderLayout.WEST);

        // User details (Username and Mutual Friends)
        JPanel userDetailsPanel = new JPanel();
        userDetailsPanel.setBackground(Color.decode("#121212"));
        userDetailsPanel.setLayout(new BoxLayout(userDetailsPanel, BoxLayout.Y_AXIS));

        // Username
        JLabel usernameLabel = new JLabel(group.getName());
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameLabel.setForeground(Color.WHITE);

        userDetailsPanel.add(usernameLabel);

        // Ellipsis button (Options)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.decode("#121212"));
        JButton optionsButton = new JButton("..."); // Ellipsis for options
        optionsButton.setBackground(Color.decode("#121212"));
        optionsButton.setForeground(Color.WHITE);
        optionsButton.setPreferredSize(new Dimension(40, 30));
        optionsButton.setFocusPainted(false);

        //create the popup menu
        JPopupMenu popupMenu = new JPopupMenu();

        //Add options
        if (!account.getUser().getUserId().equals(group.getOwner().getUser().getUserId())) {
            JMenuItem LeaveItem = new JMenuItem("Join");
            LeaveItem.addActionListener(e -> {

                commonRole c = new commonRole();
                c.leaveGroup(account, group);
            });
            popupMenu.add(LeaveItem);
        }
        JMenuItem ViewItem = new JMenuItem("View Group");
        ViewItem.addActionListener(e -> {
            GroupWindow G = new GroupWindow(account, group);
        });
        popupMenu.add(ViewItem);

        optionsButton.addActionListener(e -> popupMenu.show(optionsButton, optionsButton.getWidth() / 2, optionsButton.getHeight() / 2));
        buttonPanel.add(optionsButton);
        panel.add(userDetailsPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.EAST);
        SuggestedGroupsPanel.add(panel);
    }

    public void creategroupsLabel(Group group) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.decode("#121212"));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // Profile Picture
        JLabel profilePictureLabel = new JLabel();
        ImageIcon profileIcon = new ImageIcon(group.getPhoto());
        Image profileImage = profileIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        profilePictureLabel.setIcon(new ImageIcon(profileImage));

        panel.add(profilePictureLabel, BorderLayout.WEST);

        // User details (Username and Mutual Friends)
        JPanel userDetailsPanel = new JPanel();
        userDetailsPanel.setBackground(Color.decode("#121212"));
        userDetailsPanel.setLayout(new BoxLayout(userDetailsPanel, BoxLayout.Y_AXIS));

        // Username
        JLabel usernameLabel = new JLabel(group.getName());
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameLabel.setForeground(Color.WHITE);

        userDetailsPanel.add(usernameLabel);

        // Ellipsis button (Options)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.decode("#121212"));
        JButton optionsButton = new JButton("..."); // Ellipsis for options
        optionsButton.setBackground(Color.decode("#121212"));
        optionsButton.setForeground(Color.WHITE);
        optionsButton.setPreferredSize(new Dimension(40, 30));
        optionsButton.setFocusPainted(false);

        //create the popup menu
        JPopupMenu popupMenu = new JPopupMenu();

        //Add options
        if (!account.getUser().getUserId().equals(group.getOwner().getUser().getUserId())) {
            JMenuItem LeaveItem = new JMenuItem("Leave");
            LeaveItem.addActionListener(e -> {

                commonRole c = new commonRole();
                c.leaveGroup(account, group);
            });
            popupMenu.add(LeaveItem);
        } else {
            JMenuItem RemoveItem = new JMenuItem("Remove");
            RemoveItem.addActionListener(e -> {
                ArrayList<Group>groups=userGroupsFileManager.loadGroups(account.getUser().getUserId());
                groups.remove(group);
                userGroupsFileManager.saveGroups(account.getUser().getUserId(),groups);
                Groups.remove(group);
                new deleteGroup(group);
            });
            popupMenu.add(RemoveItem);
        }
        JMenuItem ViewItem = new JMenuItem("View Group");
        ViewItem.addActionListener(e -> {
            GroupWindow G = new GroupWindow(account, group);
        });
        popupMenu.add(ViewItem);

        optionsButton.addActionListener(e -> popupMenu.show(optionsButton, optionsButton.getWidth() / 2, optionsButton.getHeight() / 2));
        buttonPanel.add(optionsButton);
        panel.add(userDetailsPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.EAST);
        UserGroupsPanel.add(panel);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        createGroup = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        UserGroupsPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        SuggestedGroupsPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Groups");

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        createGroup.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        createGroup.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        createGroup.setText("Create your own group");
        createGroup.setOpaque(true);
        createGroup.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                createGroupMousePressed(evt);
            }
        });

        javax.swing.GroupLayout UserGroupsPanelLayout = new javax.swing.GroupLayout(UserGroupsPanel);
        UserGroupsPanel.setLayout(UserGroupsPanelLayout);
        UserGroupsPanelLayout.setHorizontalGroup(
            UserGroupsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 345, Short.MAX_VALUE)
        );
        UserGroupsPanelLayout.setVerticalGroup(
            UserGroupsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 399, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(UserGroupsPanel);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Your Groups");

        javax.swing.GroupLayout SuggestedGroupsPanelLayout = new javax.swing.GroupLayout(SuggestedGroupsPanel);
        SuggestedGroupsPanel.setLayout(SuggestedGroupsPanelLayout);
        SuggestedGroupsPanelLayout.setHorizontalGroup(
            SuggestedGroupsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 345, Short.MAX_VALUE)
        );
        SuggestedGroupsPanelLayout.setVerticalGroup(
            SuggestedGroupsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 229, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(SuggestedGroupsPanel);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Suggested Groups");
        jLabel2.setToolTipText("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(createGroup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(createGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(7, 7, 7)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(7, 7, 7)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
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
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void createGroupMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_createGroupMousePressed
        CreateGroupWindow a = new CreateGroupWindow(account);
        this.dispose();
    }//GEN-LAST:event_createGroupMousePressed

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
            java.util.logging.Logger.getLogger(UserGroupsWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserGroupsWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserGroupsWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserGroupsWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // new UserGroupsWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel SuggestedGroupsPanel;
    private javax.swing.JPanel UserGroupsPanel;
    private javax.swing.JLabel createGroup;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
