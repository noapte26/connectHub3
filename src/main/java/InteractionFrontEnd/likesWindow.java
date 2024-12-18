package InteractionFrontEnd;

import Account.AccountLoad;
import Account.UserAccount;
import ContentCreation.Post;
import FriendMangement.BackEnd.FriendListFileManager;
import ProfileManagementFrontend.ProfileWindow;
import interactionsBackEnd.like;
import interactionsDataBase.likeFileManeger;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class likesWindow extends JFrame {
    private Post post;
    public likesWindow(Post post)  {
        this.post=post;
        likeFileManeger likefilemanager=new likeFileManeger("Likes") ;
        ArrayList<like>likes=likefilemanager.loadLike(post.getContentId());
        setTitle("Likes");
        setSize(400, 600);
        setLayout(new BorderLayout());
        // Friends List Section
        JLabel friendsLabel = new JLabel(" Likes");
        friendsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        friendsLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel friendsContainer = new JPanel();
        friendsContainer.setLayout(new BoxLayout(friendsContainer, BoxLayout.Y_AXIS));

        // Example Friends
        for (like l : likes) {
            JPanel friendPanel = createlikesPanel(l);
            friendsContainer.add(friendPanel);
        }

        JScrollPane friendsScrollPane = new JScrollPane(friendsContainer);
        friendsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        friendsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        friendsScrollPane.setPreferredSize(new Dimension(400, 500));

        // Add sections to the frame
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(friendsLabel, BorderLayout.NORTH);
        topPanel.add(friendsScrollPane, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        setVisible(true);
    }
    private JPanel createlikesPanel(like l) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.decode("#121212"));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // Profile Picture
        JLabel profilePic = new JLabel(new ImageIcon(l.getAccount().getProfile().getProfileImageUrl()));
        ImageIcon profileIcon = new ImageIcon(l.getAccount().getProfile().getProfileImageUrl());
        Image profileImage = profileIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Adjust profile image size
        profilePic.setIcon(new ImageIcon(profileImage));
        panel.add(profilePic,BorderLayout.WEST);

        // User details (Username and Mutual Friends)
        JPanel userDetailsPanel = new JPanel();
        userDetailsPanel.setBackground(Color.decode("#121212"));
        userDetailsPanel.setLayout(new BoxLayout(userDetailsPanel, BoxLayout.Y_AXIS));

        // Username
        JLabel usernameLabel = new JLabel(l.getAccount().getUser().getUserName());
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

        JMenuItem viewItem = new JMenuItem("View profile");
        viewItem.addActionListener(e -> {
            ProfileWindow p=new ProfileWindow(l.getAccount().getProfile(),l.getAccount());
        });
        popupMenu.add(viewItem);

        optionsButton.addActionListener(e -> popupMenu.show(optionsButton, optionsButton.getWidth() / 2, optionsButton.getHeight() / 2));
        buttonPanel.add(optionsButton);
        panel.add(userDetailsPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.EAST);

        return panel;
    }
}
