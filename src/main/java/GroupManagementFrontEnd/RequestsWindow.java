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
import groupDataBase.UserGroupsFileManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

/**
 *
 * @author CONNECT
 */
public class RequestsWindow extends JFrame {
     ArrayList<UserAccount> members;
    private UserAccount Account;
    private Group group;
    MembersFileManager adminsFileManager = new MembersFileManager("Admins_lists","admin");
    MembersFileManager requestsFileManager = new MembersFileManager("requests_lists", "Requests");

    public RequestsWindow(UserAccount Account, Group group) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.group = group;
        this.Account = Account;

        setTitle("Requests List");
        setSize(400, 600);
        setLayout(new BorderLayout());
        // Admins List Section
        JLabel requestLabel = new JLabel("Requests List");
        requestLabel.setFont(new Font("Arial", Font.BOLD, 16));
        requestLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel requestContainer = new JPanel();
        requestContainer.setLayout(new BoxLayout(requestContainer, BoxLayout.Y_AXIS));

        // Example Friends
        for (UserAccount user : requestsFileManager.loadMembers(group.getGroupId())) {
            JPanel friendPanel = createRequestPanel(user);
            requestContainer.add(friendPanel);
        }

        JScrollPane requestScrollPane = new JScrollPane(requestContainer);
        requestScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        requestScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        requestScrollPane.setPreferredSize(new Dimension(400, 500));

        // Add sections to the frame
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(requestLabel, BorderLayout.NORTH);
        topPanel.add(requestScrollPane, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        setVisible(true);
    }

    private JPanel createRequestPanel(UserAccount user) {
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

            //Add options
            JMenuItem approveItem = new JMenuItem("Accept");
            approveItem.addActionListener(e -> {
                adminRole a = new adminRole();
                a.approveMember(user, group);
                UserGroupsFileManager g = new UserGroupsFileManager();
                ArrayList <Group> groups = g.loadGroups(user.getUser().getUserId());
                groups.add(group);
                g.saveGroups(user.getUser().getUserId(), groups);
            });
               popupMenu.add(approveItem);  
          JMenuItem declineItem = new JMenuItem("Decline");
            declineItem.addActionListener(e -> {
                adminRole a = new adminRole();
                a.declineMember(user, group);
            });
            popupMenu.add(declineItem);
        
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
