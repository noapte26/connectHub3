package FriendManagement.FrontEnd;
import FriendMangement.BackEnd.*;
import Account.*;
import UserAccountManagementBackend.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FriendRequestWindow extends JFrame {
    FriendRequestFileManager fileManager;
    UserAccount Account;
    public FriendRequestWindow(UserAccount Account) {
       this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

         fileManager=new FriendRequestFileManager();
         this.Account=Account;
        ArrayList<UserAccount>requests= (ArrayList<UserAccount>) fileManager.loadFriendRequests(Account.getUser().getUserId());

        setTitle("Friend Request");
        setSize(400, 600);
        setLayout(new BorderLayout());

        // Friend Requests Section
        JLabel requestsLabel = new JLabel("Friend Requests");
        requestsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        requestsLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel requestsContainer = new JPanel();
        requestsContainer.setLayout(new BoxLayout(requestsContainer, BoxLayout.Y_AXIS));

        // Example Friend Requests
        requestsContainer.removeAll();
        requestsContainer.setLayout(new BoxLayout(requestsContainer, BoxLayout.Y_AXIS));
        requestsContainer.setBackground(Color.decode("#121212"));
        for (UserAccount user:requests) {
            JPanel requestPanel = createRequestPanel(user.getUser().getUserName());
            requestsContainer.add(requestPanel);
            requestsContainer.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        JScrollPane requestsScrollPane = new JScrollPane(requestsContainer);
        requestsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        requestsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        requestsScrollPane.setPreferredSize(new Dimension(400,500 ));

        // Add sections to the frame
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(requestsLabel, BorderLayout.NORTH);
        topPanel.add(requestsScrollPane, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        setVisible(true);
    }



    private JPanel createRequestPanel(String username) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.decode("#121212"));
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));

        // Profile Picture
        JLabel profilePic = new JLabel(new ImageIcon(Account.getProfile().getProfileImageUrl()));
        profilePic.setPreferredSize(new Dimension(75, 75));
        panel.add(profilePic,BorderLayout.WEST);

        //user datails and buttons
        JPanel detailsAndButtonsPanel = new JPanel(new BorderLayout());
        detailsAndButtonsPanel.setBackground(Color.decode("#1E1E1E"));

        // Username
        JLabel usernameLabel = new JLabel(username);
        usernameLabel.setForeground(Color.WHITE); // White text
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));

        detailsAndButtonsPanel.add(usernameLabel);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.decode("#1E1E1E"));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Small gap between buttons

        JButton confirmButton = new JButton("Confirm");
        JButton deleteButton = new JButton("Decline");


// Add an action listener for the Confirm button
        confirmButton.addActionListener(e -> {
           fileManager.confirmFriendRequest(Account.getUser().getUserId(),username);
            buttonPanel.removeAll();
            JLabel Accepted=new JLabel("You are now Friends");
            Accepted.setForeground(Color.LIGHT_GRAY);
            Accepted.setFont(new Font("Arial",Font.ITALIC,12));
            buttonPanel.add(Accepted);
        });
        deleteButton.addActionListener(e -> {
            fileManager.deleteFriendRequest(Account.getUser().getUserId(),username);
            buttonPanel.removeAll();
            JLabel Accepted=new JLabel(" Request removed ");
            Accepted.setForeground(Color.LIGHT_GRAY);
            Accepted.setFont(new Font("Arial",Font.ITALIC,12));
            buttonPanel.add(Accepted);
        });

        confirmButton.setBackground(Color.decode("#1877F2")); // Facebook-like blue
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setPreferredSize(new Dimension(100, 30));
        confirmButton.setFocusPainted(false);  // Text color

        deleteButton.setBackground(Color.GRAY);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setPreferredSize(new Dimension(100, 30));
        deleteButton.setFocusPainted(false);

        // Button Panel
        buttonPanel.add(confirmButton);
        buttonPanel.add(deleteButton);

        detailsAndButtonsPanel.add(buttonPanel,BorderLayout.SOUTH);
        // Add the buttons panel to the right of details
        panel.add(detailsAndButtonsPanel,BorderLayout.CENTER);


        return panel;
    }
    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(FriendRequestWindow::new);
    }*/

}
