package NotificationFrontend;

import Account.AccountLoad;
import Account.UserAccount;
import Account.GetAccount;
import NotificationsBackend.Notification;
import NotificationsBackend.NotificationFileManager;
import NotificationsBackend.NotificationExtension;
import FriendMangement.BackEnd.FriendRequestFileManager;
import GroupManagementBackEnd.Group;
import GroupManagementFrontEnd.GroupWindow;
import groupDataBase.groupLoad;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.*;
import InteractionFrontEnd.commentsWindow;
import ChatSystemFrontend.ChatPage;
import InteractionFrontEnd.likesWindow;

public class NotificationPanel extends JFrame {
    private NotificationFileManager notificationManager;
    private UserAccount currentUser;

    public NotificationPanel(UserAccount currentUser, NotificationFileManager notificationManager) {
        this.currentUser = currentUser;
        this.notificationManager = notificationManager;

        setTitle("Notifications");
        setSize(400, 600); // Set a fixed size or dynamic size depending on your design
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close the frame when done
        setLayout(new BorderLayout());

        // Notifications Section
        JLabel notificationsLabel = new JLabel("Notifications");
        notificationsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        notificationsLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel notificationsContainer = new JPanel();
        notificationsContainer.setLayout(new BoxLayout(notificationsContainer, BoxLayout.Y_AXIS));

        // Example Notifications
        notificationsContainer.removeAll();
        notificationsContainer.setLayout(new BoxLayout(notificationsContainer, BoxLayout.Y_AXIS));
        notificationsContainer.setBackground(Color.decode("#121212"));

        ArrayList<Notification> notifications;
        try {
            System.out.println("5555555555555");
            notifications = notificationManager.loadNotificationList(currentUser.getUser().getUserId());
            System.out.println("Nottt:    "+notifications);
            if (notifications == null ) {
                
                JLabel noNotificationsLabel = new JLabel("No new notifications.");
                noNotificationsLabel.setForeground(Color.WHITE);
                notificationsContainer.add(noNotificationsLabel);
            } else {
                for (Notification notification : notifications) {
                    System.out.println(notification);
                    JPanel notificationPanel = createNotificationPanel(notification);
                    notificationsContainer.add(notificationPanel);
                    notificationsContainer.add(Box.createRigidArea(new Dimension(0, 15)));
                }
            }
        } catch (Exception e) {
            JLabel errorLabel = new JLabel("Error loading notifications.");
            errorLabel.setForeground(Color.WHITE);
            notificationsContainer.add(errorLabel);
            e.printStackTrace(); // Optionally log the error for debugging
        }

        JScrollPane notificationsScrollPane = new JScrollPane(notificationsContainer);
        notificationsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        notificationsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        notificationsScrollPane.setPreferredSize(new Dimension(400, 500));

        // Add sections to the frame
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(notificationsLabel, BorderLayout.NORTH);
        topPanel.add(notificationsScrollPane, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        setLocationRelativeTo(null); // Center the JFrame on the screen
        setVisible(true); // Display the frame
    }

    private JPanel createNotificationPanel(Notification notification) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.decode("#1e1e1e"));
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Notification Message
        if (notification.getType().equals("Friend Request"))
        {UserAccount u= GetAccount.getAccount(notification.getActionId());
        JLabel messageLabel = new JLabel(notification.getType() + ": " + u.getUser().getUserName());
        messageLabel.setForeground(Color.WHITE);
        panel.add(messageLabel, BorderLayout.CENTER);
        }
        else if(notification.getType().equals("Joined Group"))
        {  groupLoad a = new groupLoad();
           HashSet <Group> groups = a.loadGroups();
           Group group = null;
             for (Group g : groups)
             {
             if (g.getGroupId().equals(notification.getActionId()))
             { group = g;
             break ;
             }
             }  
             if (group!=null)
             { JLabel messageLabel = new JLabel(notification.getType() + ": " + group.getName());
        messageLabel.setForeground(Color.WHITE);
        panel.add(messageLabel, BorderLayout.CENTER);
             }
        }
        // Action Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.decode("#1e1e1e"));

        switch (notification.getType()) {
            case "Friend Request":
                JButton acceptButton = new JButton("Accept");
                JButton declineButton = new JButton("Decline");

               acceptButton.addActionListener(e -> {
                    UserAccount u1 = GetAccount.getAccount(notification.getActionId());
                    new FriendRequestFileManager().confirmFriendRequest(currentUser, u1);
                    JOptionPane.showMessageDialog(this, "Friend request accepted!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose(); // Close the current panel and refresh notifications if needed
                   // new NotificationPanel(currentUser, notificationManager);
                });

                declineButton.addActionListener(e -> {
                    UserAccount u1 = GetAccount.getAccount(notification.getActionId());
                    new FriendRequestFileManager().deleteFriendRequest(currentUser.getUser().getUserId(), u1);
                    JOptionPane.showMessageDialog(this, "Friend request declined.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    dispose(); // Close the current panel and refresh notifications if needed
                    //new NotificationPanel(currentUser, notificationManager);
                });

                buttonPanel.add(acceptButton);
                buttonPanel.add(declineButton);
                break;

            case "New Group Post":
            case "Joined Group":
                JButton viewGroupButton = new JButton("View Group");
                viewGroupButton.addActionListener(e -> {
                    groupLoad groupl = new groupLoad();
                    HashSet<Group> groups = groupl.loadGroups();
                    Group groupToView = null;
                    for (Group group : groups) {
                        if (group.getGroupId().equals(notification.getActionId())) {
                            groupToView = group;
                            break;
                        }
                    }

                    if (groupToView != null) {
                        GroupWindow groupWindow = new GroupWindow(currentUser, groupToView);
                        groupWindow.setLocationRelativeTo(null); // Center the window on the screen
                    } else {
                        JOptionPane.showMessageDialog(this, "Group not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });
                buttonPanel.add(viewGroupButton);
                break;
            
            case "Chat":
                UserAccount messageSender = GetAccount.getAccount(notification.getActionId());
                JLabel messageLabel2 = new JLabel(notification.getType() + ": " +messageSender.getUser().getUserName());
                messageLabel2.setForeground(Color.WHITE);
                panel.add(messageLabel2, BorderLayout.CENTER);
                JButton viewMessageButton = new JButton("View Message");
                viewMessageButton.addActionListener(e -> {
                    // Open chat window 
                   UserAccount messageReciever = GetAccount.getAccount(notification.getRecipientId());
                   if(messageReciever != null){
                       ChatPage c = new ChatPage(messageReciever, messageSender, notification.getCharId());
                   }
                   
                });
                buttonPanel.add(viewMessageButton);
                break;
            case "Post Comment":
              
                        System.out.println("Iam in the post notification now");
                        UserAccount commentOwner = GetAccount.getAccount(notification.getActionId());
                        JLabel messageLabel = new JLabel(notification.getType() + ": " +commentOwner.getUser().getUserName());
                        messageLabel.setForeground(Color.WHITE);
                        panel.add(messageLabel, BorderLayout.CENTER);
                        JButton viewPostButton = new JButton("View Post");
                        viewPostButton.addActionListener(e -> {
                        // Open post view logic

                         UserAccount userAccount = GetAccount.getAccount(notification.getRecipientId());
                         if(userAccount != null){
                         commentsWindow cm = new commentsWindow(notification.getPost(), userAccount);
                        
                     }
                   
                });
                         buttonPanel.add(viewPostButton);
                        
                
                break;
                case "Post Like":
              
                        System.out.println("Iam in the post notification now");
                        UserAccount likeOwner = GetAccount.getAccount(notification.getActionId());
                        JLabel messageLabel1 = new JLabel(notification.getType() + ": " +likeOwner.getUser().getUserName());
                        messageLabel1.setForeground(Color.WHITE);
                        panel.add(messageLabel1, BorderLayout.CENTER);
                        JButton viewPostButton1 = new JButton("View Post");
                        viewPostButton1.addActionListener(e -> {
                        // Open post view logic

                         UserAccount userAccount = GetAccount.getAccount(notification.getRecipientId());
                         if(userAccount != null){
                         likesWindow cm = new likesWindow(notification.getPost());
                        
                     }
                   
                });
                         buttonPanel.add(viewPostButton1);
                        
                
                break;

            default:
                JLabel infoLabel = new JLabel("No actions available.");
                infoLabel.setForeground(Color.LIGHT_GRAY);
                buttonPanel.add(infoLabel);
                break;
        }

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    public UserAccount getAcc(String id) {
        ArrayList<UserAccount> accounts = new AccountLoad().loadAccounts();
        for (UserAccount user : accounts) {
            if (user.getUser().getUserId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public static void showNotificationPanel(UserAccount currentUser, NotificationFileManager notificationManager) {
        new NotificationPanel(currentUser, notificationManager); // Instantiate and show NotificationPanel as a JFrame
    }

    public static void main(String[] args) {
        // Create an example UserAccount and NotificationFileManager to test the frame
       // UserAccount account = new UserAccount(); // This should be your real account object
        //NotificationFileManager manager = new NotificationFileManager();
        //SwingUtilities.invokeLater(() -> showNotificationPanel(account, manager));
    }
}
