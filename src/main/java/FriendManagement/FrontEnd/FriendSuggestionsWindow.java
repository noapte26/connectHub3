package FriendManagement.FrontEnd;

import FriendMangement.BackEnd.*;
import Account.*;
import NotificationsBackend.Notification;
import NotificationsBackend.NotificationFileManager;
import UserAccountManagementBackend.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;

public class FriendSuggestionsWindow extends JFrame {

    private UserAccount Account;
    private FriendSuggestionFileManager fileManager;

    public FriendSuggestionsWindow(UserAccount Account, ArrayList<UserAccount>Suggestons) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setVisible(true);
        this.Account = Account;
        this. fileManager = new FriendSuggestionFileManager();
        //ArrayList<UserAccount> Suggestions = (ArrayList<UserAccount>) fileManager.provideFriendSuggestions(Account.getUser().getUserId());

        setTitle("Friend Suggestions");
        setSize(400, 600);
        setLayout(new BorderLayout());

        // Set dark mode background color
        getContentPane().setBackground(Color.decode("#121212"));

        // Suggestions Section
        JLabel suggestionsLabel = new JLabel("Friend Suggestions");
        suggestionsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        suggestionsLabel.setHorizontalAlignment(SwingConstants.LEFT);
        suggestionsLabel.setForeground(Color.WHITE); // White text for label

        JPanel suggestionsContainer = new JPanel();
        suggestionsContainer.setLayout(new BoxLayout(suggestionsContainer, BoxLayout.Y_AXIS));
        suggestionsContainer.setBackground(Color.decode("#121212")); // Dark background for container

        // Example Friend Suggestions
        for (UserAccount user : Suggestons) {
            JPanel suggestionPanel = createSuggestionPanel(user);
            suggestionsContainer.add(suggestionPanel);
            suggestionsContainer.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        JScrollPane suggestionsScrollPane = new JScrollPane(suggestionsContainer);
        suggestionsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        suggestionsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        suggestionsScrollPane.setPreferredSize(new Dimension(400, 500));

        // Add components to the frame
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.decode("#121212")); // Dark background for top panel
        topPanel.add(suggestionsLabel, BorderLayout.NORTH);
        topPanel.add(suggestionsScrollPane, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        setVisible(true);
    }

    private JPanel createSuggestionPanel(UserAccount user) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.decode("#121212"));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // Profile Picture
        JLabel profilePic = new JLabel(new ImageIcon(user.getProfile().getProfileImageUrl()));
        ImageIcon profileIcon = new ImageIcon(user.getProfile().getProfileImageUrl());
        Image profileImage = profileIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Adjust profile image size
        profilePic.setIcon(new ImageIcon(profileImage));
        panel.add(profilePic,BorderLayout.WEST);

        //user datails and buttons
        JPanel detailsAndButtonsPanel = new JPanel(new BorderLayout());
        detailsAndButtonsPanel.setBackground(Color.decode("#1E1E1E"));

        // Username
        JLabel usernameLabel = new JLabel(user.getUser().getUserName());
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameLabel.setForeground(Color.WHITE); // White text for username

        detailsAndButtonsPanel.add(usernameLabel);

        // Button Panel with Add and Remove Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.decode("#121212")); // Dark background for button panel

        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");

        // Action for the Add button
        addButton.addActionListener(e -> {
            System.out.println("nada hates you");
            NotificationFileManager nManager = new NotificationFileManager();
            Notification n = new Notification("Friend Request", user.getUser().getUserId(), Account.getUser().getUserId());
            nManager.addNotification(user.getUser().getUserId(), n);
            fileManager.addFriend(Account, user);            
            buttonPanel.removeAll();
            JLabel Add = new JLabel("Request Sent");
            Add.setForeground(Color.LIGHT_GRAY);
            Add.setFont(new Font("Arial", Font.ITALIC, 12));
            buttonPanel.add(Add);
            JButton cancelButton = new JButton("Cancel");
            cancelButton.setBackground(Color.decode("#1877F2")); // Facebook-like blue
            cancelButton.setForeground(Color.WHITE); // White text on blue button
            cancelButton.setPreferredSize(new Dimension(100, 30));
            cancelButton.setFocusPainted(false);
            buttonPanel.add(cancelButton);
            cancelButton.addActionListener(e1 -> {
                buttonPanel.removeAll();
                buttonPanel.add(addButton);
                buttonPanel.add(removeButton);
            });
        });

        // Action for the Remove button
        removeButton.addActionListener(e -> {
            fileManager.removeFriendSuggestion(Account.getUser().getUserId(), user);
            buttonPanel.removeAll();
            JLabel remove = new JLabel("Suggestion removed");
            remove.setForeground(Color.LIGHT_GRAY);
            remove.setFont(new Font("Arial", Font.ITALIC, 12));
            buttonPanel.add(remove);
        });

        addButton.setBackground(Color.decode("#1877F2")); // Facebook-like blue
        addButton.setForeground(Color.WHITE); // White text on blue button
        addButton.setPreferredSize(new Dimension(100, 30));
        addButton.setFocusPainted(false);

        removeButton.setBackground(Color.GRAY); // Gray color for the remove button
        removeButton.setForeground(Color.WHITE); // White text
        removeButton.setPreferredSize(new Dimension(100, 30));
        removeButton.setFocusPainted(false);

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        detailsAndButtonsPanel.add(buttonPanel, BorderLayout.SOUTH);
        // Add the buttons panel to the right of details
        panel.add(detailsAndButtonsPanel, BorderLayout.CENTER);

        return panel;
    }
    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(FriendSuggestionsWindow::new);
    }*/
}
