package FriendManagement.FrontEnd;
import Account.*;
import FriendMangement.BackEnd.*;
import UserAccountManagementBackend.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BlockedListWindow extends JFrame {
    private BlockingListFileManager fileManager;
    private UserAccount Account;
    public BlockedListWindow(UserAccount Account)
    {   this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.Account=Account;
        this.fileManager=new BlockingListFileManager();
        ArrayList<UserAccount> Blocked= (ArrayList<UserAccount>) fileManager.loadBlockingList(Account.getUser().getUserId());

        setTitle(" Blocking ");
        setSize(400, 600);
        setLayout(new BorderLayout());

        JLabel BlockingLabel = new JLabel("Blocking");
        BlockingLabel.setFont(new Font("Arial", Font.BOLD, 16));
        BlockingLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel BlockedContainer = new JPanel();
        BlockedContainer.setLayout(new BoxLayout(BlockedContainer, BoxLayout.Y_AXIS));

        BlockedContainer.removeAll();
        BlockedContainer.setLayout(new BoxLayout(BlockedContainer, BoxLayout.Y_AXIS));
        BlockedContainer.setBackground(Color.decode("#121212"));
        for (UserAccount user:Blocked) {
            JPanel requestPanel = createBlockedPanel(user);
            BlockedContainer.add(requestPanel);
            BlockedContainer.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        JScrollPane requestsScrollPane = new JScrollPane(BlockedContainer);
        requestsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        requestsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        requestsScrollPane.setPreferredSize(new Dimension(400,500 ));

        // Add sections to the frame
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(BlockingLabel, BorderLayout.NORTH);
        topPanel.add(requestsScrollPane, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        setVisible(true);
    }
    private JPanel createBlockedPanel(UserAccount user)
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.decode("#121212"));
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));

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
        usernameLabel.setForeground(Color.WHITE); // White text
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));

        detailsAndButtonsPanel.add(usernameLabel,BorderLayout.WEST);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.decode("#1E1E1E"));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Small gap between buttons

        JButton UnblockButton = new JButton("unblock");

        UnblockButton.addActionListener(e -> {
            fileManager.unblockUser(Account.getUser().getUserId(),user);
            buttonPanel.removeAll();
            JLabel unblocked=new JLabel(" You un blocked this user ");
            unblocked.setForeground(Color.LIGHT_GRAY);
            unblocked.setFont(new Font("Arial",Font.ITALIC,12));
            buttonPanel.add(unblocked);
        });

        UnblockButton.setBackground(Color.decode("#121212")); // Facebook-like blue
        UnblockButton.setForeground(Color.LIGHT_GRAY);
        UnblockButton.setPreferredSize(new Dimension(100, 30));
        UnblockButton.setFocusPainted(false);  // Text color

        // Button Panel
        buttonPanel.add(UnblockButton);
        detailsAndButtonsPanel.add(buttonPanel,BorderLayout.EAST);
        // Add the buttons panel to the right of details
        panel.add(detailsAndButtonsPanel,BorderLayout.CENTER);
        return panel;
    }
    /*public static void main(String[] args) {
        SwingUtilities.invokeLater(BlockedListWindow::new);
    }*/

}
