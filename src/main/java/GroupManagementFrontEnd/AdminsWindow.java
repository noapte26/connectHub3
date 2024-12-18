/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GroupManagementFrontEnd;

import Account.UserAccount;
import GroupManagementBackEnd.Group;
import GroupManagementBackEnd.ownerRole;
import ProfileManagementFrontend.ProfileWindow;
import groupDataBase.MembersFileManager;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author CONNECT
 */
public class AdminsWindow extends JFrame {

    ArrayList<UserAccount> admins;
    private UserAccount Account;
    private Group group;
    MembersFileManager adminsFileManager = new MembersFileManager("Admins_lists","admin");

    public AdminsWindow(UserAccount Account, Group group) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.group = group;
        admins = adminsFileManager.loadMembers(group.getGroupId());
        this.Account = Account;

        setTitle("Admins List");
        setSize(400, 600);
        setLayout(new BorderLayout());
        // Admins List Section
        JLabel adminLabel = new JLabel("Admins List");
        adminLabel.setFont(new Font("Arial", Font.BOLD, 16));
        adminLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel adminsContainer = new JPanel();
        adminsContainer.setLayout(new BoxLayout(adminsContainer, BoxLayout.Y_AXIS));

        // Example Friends
        for (UserAccount user : admins) {
            JPanel friendPanel = createAdminPanel(user);
            adminsContainer.add(friendPanel);
        }

        JScrollPane adminsScrollPane = new JScrollPane(adminsContainer);
        adminsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        adminsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        adminsScrollPane.setPreferredSize(new Dimension(400, 500));

        // Add sections to the frame
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(adminLabel, BorderLayout.NORTH);
        topPanel.add(adminsScrollPane, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        setVisible(true);
    }

    private JPanel createAdminPanel(UserAccount user) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.decode("#121212"));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // Profile Picture
        JLabel profilePic = new JLabel(new ImageIcon(user.getProfile().getProfileImageUrl()));
        profilePic.setPreferredSize(new Dimension(75, 75));
        panel.add(profilePic, BorderLayout.WEST);

        // User details (Username and Mutual Friends)
        JPanel userDetailsPanel = new JPanel();
        userDetailsPanel.setBackground(Color.decode("#121212"));
        userDetailsPanel.setLayout(new BoxLayout(userDetailsPanel, BoxLayout.Y_AXIS));

        // Username
        JLabel usernameLabel = new JLabel(user.getUser().getUserName());
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

        if (Account.getUser().getUserId().equals(group.getOwner().getUser().getUserId())) {
            //Add options
            JMenuItem demoteItem = new JMenuItem("Demote");
            demoteItem.addActionListener(e -> {
                ownerRole a = new ownerRole();
                a.demoteAdmin(user, group);
            });
            popupMenu.add(demoteItem);
            JMenuItem removeItem = new JMenuItem("Remove");
            removeItem.addActionListener(e -> {
                ownerRole a = new ownerRole();
                a.removeAdmin(user, group);
            });
            popupMenu.add(removeItem);

        }
      
        JMenuItem viewItem = new JMenuItem("View Profile");
        viewItem.addActionListener(e -> {
           ProfileWindow a = new ProfileWindow(user.getProfile(),user);
        });
        popupMenu.add(viewItem);

        optionsButton.addActionListener(e -> popupMenu.show(optionsButton, optionsButton.getWidth() / 2, optionsButton.getHeight() / 2));
        buttonPanel.add(optionsButton);
        panel.add(userDetailsPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.EAST);

        return panel;
    }
    /* public static void main(String[] args) {
        SwingUtilities.invokeLater(FriendListWindow::new);
    }*/

}
