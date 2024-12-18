/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GroupManagementFrontEnd;

import Account.UserAccount;
import GroupManagementBackEnd.Group;
import GroupManagementBackEnd.adminRole;
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
public class MembersWindow extends JFrame {

    ArrayList<UserAccount> members;
    private UserAccount Account;
    private Group group;
    MembersFileManager membersFileManager = new MembersFileManager("Members_lists","Member");
    MembersFileManager adminsFileManager = new MembersFileManager("Admins_lists","admin");

    public MembersWindow(UserAccount Account, Group group) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.group = group;
        members = membersFileManager.loadMembers(group.getGroupId());
        this.Account = Account;

        setTitle("Members List");
        setSize(400, 600);
        setLayout(new BorderLayout());
        // Admins List Section
        JLabel memberLabel = new JLabel("Members List");
        memberLabel.setFont(new Font("Arial", Font.BOLD, 16));
        memberLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel memberContainer = new JPanel();
        memberContainer.setLayout(new BoxLayout(memberContainer, BoxLayout.Y_AXIS));

        // Example Friends
        for (UserAccount user : members) {
            JPanel friendPanel = createMemberPanel(user);
            memberContainer.add(friendPanel);
        }

        JScrollPane memberScrollPane = new JScrollPane(memberContainer);
        memberScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        memberScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        memberScrollPane.setPreferredSize(new Dimension(400, 500));

        // Add sections to the frame
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(memberLabel, BorderLayout.NORTH);
        topPanel.add(memberScrollPane, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        setVisible(true);
    }

    private JPanel createMemberPanel(UserAccount user) {
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
            JMenuItem promoteItem = new JMenuItem("Promote");
            promoteItem.addActionListener(e -> {
                ownerRole a = new ownerRole();
                a.promoteMember(user, group);
            });
            popupMenu.add(promoteItem);
            JMenuItem removeItem = new JMenuItem("Remove");
            removeItem.addActionListener(e -> {
                adminRole a = new adminRole();
                a.removeMember(user, group);
            });
            popupMenu.add(removeItem);

        }
        ArrayList <String> adminsIds= new ArrayList<>();
        for (UserAccount admin: adminsFileManager.loadMembers(group.getGroupId()) )
        { adminsIds.add(admin.getUser().getUserId());
        }
        if (adminsIds.contains(user.getUser().getUserId()))
        {  JMenuItem removeItem = new JMenuItem("Remove");
            removeItem.addActionListener(e -> {
                adminRole a = new adminRole();
                a.removeMember(user, group);
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
