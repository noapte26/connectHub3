package GroupManagementFrontEnd;

import Account.AccountLoad;
import Account.UserAccount;
import ContentCreation.Post;
import FriendMangement.BackEnd.BlockingListFileManager;
import FriendMangement.BackEnd.FriendListFileManager;
import GroupManagementBackEnd.Group;
import GroupManagementBackEnd.adminRole;
import groupDataBase.ContentFileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PostRequestsWindow extends JFrame {
    private UserAccount Account;
    private Group group;
    ContentFileManager contentFileManager = new ContentFileManager("requests_lists", "Requests");

    public PostRequestsWindow(UserAccount Account, Group group) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.Account = Account;
        this.group = group;
        ArrayList<Post> reqposts = contentFileManager.loadPosts(group.getGroupId());
        setTitle(" Post Requests ");
        setSize(400, 600);
        setLayout(new BorderLayout());

        // Friends List Section
        JLabel reqLabel = new JLabel(" Post Requests ");
        reqLabel.setFont(new Font("Arial", Font.BOLD, 16));
        reqLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel reqContainer = new JPanel();
        reqContainer.setLayout(new BoxLayout(reqContainer, BoxLayout.Y_AXIS));

        // Example Friends
        for (Post user : reqposts) {
            JPanel requestPanel = createFriendPanel(user);
            reqContainer.add(requestPanel);
        }

        JScrollPane friendsScrollPane = new JScrollPane(reqContainer);
        friendsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        friendsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        friendsScrollPane.setPreferredSize(new Dimension(400, 500));

        // Add sections to the frame
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(reqLabel, BorderLayout.NORTH);
        topPanel.add(friendsScrollPane, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        setVisible(true);
    }

    private JPanel createFriendPanel(Post post) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.decode("#121212"));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // Profile Picture
        JLabel profilePic = new JLabel(new ImageIcon(post.getImagePath()));
        ImageIcon profileIcon = new ImageIcon(post.getImagePath());
        Image profileImage = profileIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Adjust profile image size
        profilePic.setIcon(new ImageIcon(profileImage));
        panel.add(profilePic, BorderLayout.WEST);

        // User details (Username and Mutual Friends)
        JPanel userDetailsPanel = new JPanel();
        userDetailsPanel.setBackground(Color.decode("#121212"));
        userDetailsPanel.setLayout(new BoxLayout(userDetailsPanel, BoxLayout.Y_AXIS));

        // Username
        JLabel usernameLabel = new JLabel(post.getContent());
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
        JMenuItem AcceptItem = new JMenuItem("Accept");
        AcceptItem.addActionListener(e -> {
            adminRole a = new adminRole();
            a.appovePost(post, group);
        });
        popupMenu.add(AcceptItem);

        JMenuItem declineItem = new JMenuItem("Decline");
        declineItem.addActionListener(e -> {
            adminRole a = new adminRole();
            a.declinePost(post, group);
        });
        popupMenu.add(declineItem);

        optionsButton.addActionListener(e -> popupMenu.show(optionsButton, optionsButton.getWidth() / 2, optionsButton.getHeight() / 2));
        buttonPanel.add(optionsButton);
        panel.add(userDetailsPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.EAST);

        return panel;
    }
}
