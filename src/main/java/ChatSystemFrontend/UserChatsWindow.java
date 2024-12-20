/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChatSystemFrontend;

/**
 *
 * @author gg
 */
import Account.*;
import ChatSystemBackend.Chat;
import ChatSystemBackend.ChatFileManager;
import FriendMangement.BackEnd.*;
import ProfileManagementFrontend.ProfileWindow;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UserChatsWindow extends JFrame {

    
    private UserAccount Account;
    ChatFileManager fileManager;

    public UserChatsWindow(UserAccount Account) {
        setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.Account = Account;
        fileManager = new ChatFileManager("Chats_List","Chat");
        ArrayList<Chat> chats= fileManager.loadChats(Account.getUser().getUserId());
        setTitle("Chat List");
        setSize(400, 600);
        setLayout(new BorderLayout());
        // Friends List Section
        JLabel friendsLabel = new JLabel("Chat List");
        friendsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        friendsLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel friendsContainer = new JPanel();
        friendsContainer.setLayout(new BoxLayout(friendsContainer, BoxLayout.Y_AXIS));

        // Example Friends
        for (Chat chat : chats) {
            JPanel friendPanel = createChatPanel(chat);
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

   private JPanel createChatPanel(Chat chat) {
    JPanel panel = new JPanel(new BorderLayout());
    String chatId = chat.getChatId();
    panel.setBackground(Color.decode("#121212"));
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

    // to check what will be displayed in case -> had to be done like that to avoid lambda errors
    UserAccount user = chat.getUser1().getUser().getUserId().equals(Account.getUser().getUserId()) ? chat.getUser2() : chat.getUser1();

    // User details (Username and Mutual Friends)
    JPanel userDetailsPanel = new JPanel();
    userDetailsPanel.setBackground(Color.decode("#121212"));
    userDetailsPanel.setLayout(new BoxLayout(userDetailsPanel, BoxLayout.Y_AXIS));

    // Username label
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

    // Create the popup menu
    JPopupMenu popupMenu = new JPopupMenu();

    
    JMenuItem openChatItem = new JMenuItem("Open Chat");
    openChatItem.addActionListener(e -> {
        
        new ChatPage(Account, user, chatId).setVisible(true);
    });
    popupMenu.add(openChatItem);

    // Option: View Profile
    JMenuItem viewProfileItem = new JMenuItem("View Profile");
    viewProfileItem.addActionListener(e -> {
        // Open the profile window when clicked
        new ProfileWindow(user.getProfile(), user).setVisible(true);
    });
    popupMenu.add(viewProfileItem);

    // Show the popup menu when the button is pressed
    optionsButton.addActionListener(e -> popupMenu.show(optionsButton, optionsButton.getWidth() / 2, optionsButton.getHeight() / 2));
    buttonPanel.add(optionsButton);

    // Add panels to the main panel
    panel.add(userDetailsPanel, BorderLayout.CENTER);
    panel.add(buttonPanel, BorderLayout.EAST);

    return panel;
}

    /* public static void main(String[] args) {
        SwingUtilities.invokeLater(FriendListWindow::new);
    }*/

}
