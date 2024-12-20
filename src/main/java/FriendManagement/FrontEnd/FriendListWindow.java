package FriendManagement.FrontEnd;
//aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa i'm gonna kill my self

import Account.*;
import ChatSystemBackend.Chat;
import ChatSystemBackend.ChatFileManager;
import ChatSystemFrontend.ChatPage;
import FriendMangement.BackEnd.*;
import ProfileManagementFrontend.ProfileWindow;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FriendListWindow extends JFrame {

    ArrayList<UserAccount> accounts;
    private UserAccount Account;
    FriendListFileManager fileManager;

    public FriendListWindow(UserAccount Account) {
        accounts = new AccountLoad().loadAccounts();
        this.Account = Account;
        fileManager = new FriendListFileManager();
        ArrayList<UserAccount> Friends = (ArrayList<UserAccount>) fileManager.loadFriendList(Account.getUser().getUserId());
        setTitle("Friends List");
        setSize(400, 600);
        setLayout(new BorderLayout());
        // Friends List Section
        JLabel friendsLabel = new JLabel("Friends List");
        friendsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        friendsLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel friendsContainer = new JPanel();
        friendsContainer.setLayout(new BoxLayout(friendsContainer, BoxLayout.Y_AXIS));

        // Example Friends
        for (UserAccount user : Friends) {
            JPanel friendPanel = createFriendPanel(user);
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

    private JPanel createFriendPanel(UserAccount user) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.decode("#121212"));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // Profile Picture
        JLabel profilePic = new JLabel(new ImageIcon(user.getProfile().getProfileImageUrl()));
        ImageIcon profileIcon = new ImageIcon(user.getProfile().getProfileImageUrl());
        Image profileImage = profileIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Adjust profile image size
        profilePic.setIcon(new ImageIcon(profileImage));
        panel.add(profilePic,BorderLayout.WEST);

        // User details (Username and Mutual Friends)
        JPanel userDetailsPanel = new JPanel();
        userDetailsPanel.setBackground(Color.decode("#121212"));
        userDetailsPanel.setLayout(new BoxLayout(userDetailsPanel, BoxLayout.Y_AXIS));

        // Username
        JLabel usernameLabel = new JLabel(user.getUser().getUserName());
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameLabel.setForeground(Color.WHITE);

        userDetailsPanel.add(usernameLabel);
        for (UserAccount acc : accounts) //Statues
        {
            if (acc.getUser().getUserId().equals(user.getUser().getUserId())) {
                JLabel statues = new JLabel(acc.getUser().getStatus());
                statues.setFont(new Font("Arial", Font.ITALIC, 14));
                statues.setForeground(Color.LIGHT_GRAY);
                userDetailsPanel.add(statues);
            }
        }
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
        JMenuItem blockItem = new JMenuItem("Block");
        blockItem.addActionListener(e -> {
            fileManager.BlockFriend(Account, user);
            buttonPanel.removeAll();
            JLabel blocked = new JLabel(" You blocked this user ");
            blocked.setForeground(Color.LIGHT_GRAY);
            blocked.setFont(new Font("Arial", Font.ITALIC, 12));
            buttonPanel.add(blocked);
        });
        popupMenu.add(blockItem);

        JMenuItem removeItem = new JMenuItem("Remove");
        removeItem.addActionListener(e -> {
            fileManager.removeFriend(Account.getUser().getUserId(), user);
            buttonPanel.removeAll();
            JLabel removed = new JLabel(" You are no longer friends ");
            removed.setForeground(Color.LIGHT_GRAY);
            removed.setFont(new Font("Arial", Font.ITALIC, 12));
            buttonPanel.add(removed);
        });
        popupMenu.add(removeItem);

        JMenuItem viewItem = new JMenuItem("View profile");
        viewItem.addActionListener(e -> {
            ProfileWindow p=new ProfileWindow(user.getProfile(),user);
        });
        popupMenu.add(viewItem);

        JMenuItem chatItem = new JMenuItem("Send Message");
        chatItem.addActionListener(e -> {
            ChatFileManager chatFileManeger = new ChatFileManager ("Chats_List","Chat");
            ArrayList <Chat> chats = chatFileManeger.loadChats(Account.getUser().getUserId());
            int k = 0 ;
            for (Chat chat :chats)
            {
                if (chat.getUser1().getUser().getUserId().equals(user.getUser().getUserId())||chat.getUser2().getUser().getUserId().equals(user.getUser().getUserId()))
                {
                    ChatPage a = new ChatPage(Account,user,chat.getChatId());
                    k=1;
                }

            }
            if (k==0)
            {
                Chat chat = new Chat (Account,user);
                ChatPage a = new ChatPage(Account,user,chat.getChatId());
                ArrayList <Chat> accountChats = chatFileManeger.loadChats(Account.getUser().getUserId());
                ArrayList <Chat> userChats = chatFileManeger.loadChats(user.getUser().getUserId());
                accountChats.add(chat);
                userChats.add((chat));
                chatFileManeger.saveChats(Account.getUser().getUserId(),accountChats);
                chatFileManeger.saveChats(user.getUser().getUserId(),userChats);

            }




        });
        popupMenu.add(chatItem);



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
