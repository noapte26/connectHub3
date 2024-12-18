/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package NewsFeedFrontEnd;

import java.awt.*;
import javax.swing.ImageIcon;
import Account.AccountLoad;
import Account.UpdateAccount;
import Account.UserAccount;
import InteractionFrontEnd.commentsWindow;
import ContentCreation.Content;
import ContentCreation.Post;
import ContentCreation.Story;
import ContentCreation.backendContent;
import FriendManagement.FrontEnd.BlockedListWindow;
import FriendManagement.FrontEnd.FriendListWindow;
import FriendManagement.FrontEnd.FriendRequestWindow;
import FriendManagement.FrontEnd.FriendSuggestionsWindow;
import FriendMangement.BackEnd.BlockingListFileManager;
import FriendMangement.BackEnd.FriendSuggestionFileManager;
import FrontendCreation.PostUploader;
import FrontendCreation.StoryUploader;
import GroupManagementBackEnd.commonRole;
import GroupManagementFrontEnd.EditPostWindow;
import GroupManagementFrontEnd.UserGroupsWindow;
import InteractionFrontEnd.likesWindow;
import NotificationFrontend.NotificationPanel;
import NotificationsBackend.Notification;
import NotificationsBackend.NotificationFileManager;
import ProfileManagementFrontend.ProfileWindow;
import SearchEngineFrontEnd.SearchEngineFrontend;
import TailoredNewsFeedBackend.TailoredPosts;
import UserAccountManagementBackend.User;
import static UserAccountManagementBackend.getUser.getUser;
import UserAccountManagementFrontend.LoginWindpw;
import interactionsBackEnd.like;
import interactionsDataBase.likeFileManeger;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;
import java.util.HashSet;
import java.util.List;

public class NewsFeedWindow extends javax.swing.JFrame {

    ArrayList<UserAccount> accounts;
    private JPanel storyPanel;
    private JPanel postPanel;
    public String authorId;
    private List<Content> stories;
    private ArrayList<Post> posts;

    public NewsFeedWindow(String authorId) {
        initComponents();
        this.authorId = authorId;
        setVisible(true);
        this.setLocationRelativeTo(null);
        accounts = new AccountLoad().loadAccounts();
        backendContent backend = new backendContent("post_database.json", "story_database.json");
        TailoredPosts tailoredPosts = new TailoredPosts("post_database.json", this.authorId);
        stories = backend.getAllStories();  // Load stories correctly
        posts = tailoredPosts.getAllPosts();

        // Initialize storyPanel with BoxLayout
        storyPanel = new JPanel();
        storyPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Set horizontal alignment
        jScrollPane1.setViewportView(storyPanel);

        postPanel = new JPanel();
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));
        jScrollPane2.setViewportView(postPanel);

        // Show stories
        showStories();
        showPosts();

    }

    public String getname(String authorId) {
        String name;
        User theContentCreator = getUser(authorId);
        if (theContentCreator != null) {

            return theContentCreator.getUserName();
        } else {
            return "Not Found";
        }
    }

    public UserAccount getAcc(String id) {
        for (UserAccount user : accounts) {
            if (user.getUser().getUserId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public void showStories() {
        // Clear the existing stories in the panel before adding new ones
        storyPanel.removeAll();

        // Check if there are no stories
        if (stories.isEmpty()) {
            JLabel noStoriesLabel = new JLabel("No stories available.");
            noStoriesLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            noStoriesLabel.setForeground(Color.GRAY);
            noStoriesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            storyPanel.add(noStoriesLabel);
        } else {
            // Loop through the list of stories and add each one to the panel
            for (Content content : stories) {
                if (content instanceof Story) {
                    Story story = (Story) content;
                    createStoryPanel(story);

                }
            }
        }

        // Ensure the UI components are updated and the new components are visible
        storyPanel.revalidate();
        storyPanel.repaint();
    }

    public void showPosts() {
        // Clear the existing stories in the panel before adding new ones
        postPanel.removeAll();

        // Check if there are no stories
        if (posts.isEmpty()) {
            JLabel noStoriesLabel = new JLabel("No Posts available.");
            noStoriesLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            noStoriesLabel.setForeground(Color.GRAY);
            noStoriesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            postPanel.add(noStoriesLabel);
        } else {
            // Loop through the list of stories and add each one to the panel
            for (Content content : posts) {
                if (content instanceof Post) {
                    Post post = (Post) content;
                    createPostPanel(post);
                    postPanel.add(Box.createVerticalStrut(10));

                }
            }
        }

        // Ensure the UI components are updated and the new components are visible
        postPanel.revalidate();
        postPanel.repaint();
    }

    private void createStoryPanel(Story story) {
        // Create a single story container
        JPanel singleStoryPanel = new JPanel(new BorderLayout(1, 1)); // Margin between elements
        singleStoryPanel.setBackground(Color.LIGHT_GRAY);
        singleStoryPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1)); // Padding

        // Text content
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.LIGHT_GRAY);

        // Username
        String username = getname(story.getAuthorId());
        JLabel usernameLabel = new JLabel(username);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameLabel.setForeground(Color.BLACK);

        // Story content
        String content = (story.getContent() != null && !story.getContent().isEmpty()) ? story.getContent() : "";
        JLabel contentLabel = new JLabel(content);
        contentLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        contentLabel.setForeground(Color.BLACK);

        textPanel.add(usernameLabel);
        textPanel.add(Box.createVerticalStrut(5)); // Add spacing between username and content
        textPanel.add(contentLabel);

        singleStoryPanel.add(textPanel, BorderLayout.CENTER);

        // Image (if available)
        if (story.getImagePath() != null) {
            ImageIcon storyImageIcon = new ImageIcon(story.getImagePath());
            // Scale the image to a smaller size to fit within the panel
            int scaledWidth = 100;  // Set desired width
            int scaledHeight = 50; // Set desired height
            Image scaledImage = storyImageIcon.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
            JLabel storyImageLabel = new JLabel(new ImageIcon(scaledImage));
            storyImageLabel.setHorizontalAlignment(SwingConstants.CENTER);

            // Add the scaled image to the bottom of the single story panel
            singleStoryPanel.add(storyImageLabel, BorderLayout.SOUTH);
        }

        // Add the single story panel to the storyPanel container
        storyPanel.add(singleStoryPanel);

        // Refresh to display the new story immediately
        storyPanel.revalidate();
        storyPanel.repaint();
    }

    private void createPostPanel(Post post) {
        // Main panel for the post
        JPanel singlePostPanel = new JPanel(new BorderLayout(10, 10)); // Space between components
        singlePostPanel.setBackground(Color.WHITE);
        singlePostPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // Profile Picture
        JLabel profilePictureLabel = new JLabel();
        if (getAcc(post.getAuthorId()) != null) {
            ImageIcon profileIcon = new ImageIcon(getAcc(post.getAuthorId()).getProfile().getProfileImageUrl());
            Image profileImage = profileIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Adjust profile image size
            profilePictureLabel.setIcon(new ImageIcon(profileImage));
        } else {
            profilePictureLabel.setText("No Image"); // Placeholder text if no profile image
            profilePictureLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
        JPanel postautherdata = new JPanel(new BorderLayout(10, 10));
        postautherdata.add(profilePictureLabel,BorderLayout.WEST);
        postautherdata.setBackground(Color.WHITE);
        // Text Content Panel (Username + Content + Buttons)
        JPanel textContentPanel = new JPanel(new BorderLayout(10, 10)); // Space between sections
        textContentPanel.setBackground(Color.WHITE);

        // Username
        String username = getAcc(post.getAuthorId()).getUser().getUserName();
        JLabel usernameLabel = new JLabel(username);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        postautherdata.add(usernameLabel,BorderLayout.CENTER);
        // Post Content
        String content = (post.getContent() != null && !post.getContent().isEmpty()) ? post.getContent() : "No content available";
        JLabel contentLabel = new JLabel(content);
        contentLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        // Like and Comment Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Small gap between buttons
        //buttonPanel.setBackground(Color.decode("#1E1E1E"));
        JLabel likes1=new JLabel("likes");
        likes1.setFont(new Font("Arial", Font.BOLD, 14));
        buttonPanel.add(likes1,BorderLayout.NORTH);
        JButton likeButton = new JButton("Like");
        JButton commentButton = new JButton("Comment");
        likeFileManeger l=new likeFileManeger("Likes");
        ArrayList<like>likes=l.loadLike(post.getContentId());
        JPanel buttonPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        like li=null;
        for(like l1:likes)
        {
            if(l1.getAccount().getUser().getUserId().equals(getAcc(authorId).getUser().getUserId()))
            {
                li=l1;
            }
        }
        if(li!=null)
        {
            likeButton.setBackground(Color.decode("#1877F2"));
        }
        else {
            likeButton.setBackground(Color.decode("#1E1E1E"));
        }
        likeButton.setForeground(Color.WHITE);
        likeButton.setPreferredSize(new Dimension(400, 30));
        likeButton.setFocusPainted(false);

        commentButton.setBackground(Color.decode("#1E1E1E"));
        commentButton.setForeground(Color.WHITE);
        commentButton.setPreferredSize(new Dimension(400, 30));
        commentButton.setFocusPainted(false);
        likes1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        likes1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                likesWindow lw=new likesWindow(post);
            }
        });
        likeButton.addActionListener(e -> {
            like ll = new like(getAcc(authorId), post.getContentId());
            if(likeButton.getBackground().equals(Color.decode("#1E1E1E"))) {
                likes.add(ll);
                l.saveLike(post.getContentId(), likes);
            }
            else{
                like lll=null;
                for(like l2:likes)
                {
                    if(l2.getAccount().getUser().getUserId().equals(getAcc(authorId).getUser().getUserId()))
                    {
                        lll=l2;
                    }
                }
                likes.remove(lll);
                l.saveLike(post.getContentId(),likes);
            }
            showPosts();
        });
        commentButton.addActionListener(e -> {
            commentsWindow c=new commentsWindow(post,getAcc(authorId));
        });

        buttonPanel2.add(likeButton);
        JLabel label2=new JLabel("      ");
        buttonPanel2.add(label2);
        buttonPanel2.add(commentButton);
        buttonPanel.add(buttonPanel2,BorderLayout.SOUTH);
        // Add components to textContentPanel
        textContentPanel.add(postautherdata, BorderLayout.NORTH); // Username at the top
        textContentPanel.add(contentLabel, BorderLayout.CENTER); // Content in the middle


        // Image Section (optional, displayed below the text content)
        if (post.getImagePath() != null) {
            JLabel postImageLabel = new JLabel();
            ImageIcon postImageIcon = new ImageIcon(post.getImagePath());
            Image postImage = postImageIcon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH); // Adjust the size as needed
            postImageLabel.setIcon(new ImageIcon(postImage));
            postImageLabel.setHorizontalAlignment(SwingConstants.CENTER);

            // Add the image to the bottom of the singlePostPanel
            textContentPanel.add(postImageLabel, BorderLayout.SOUTH);
        }
        singlePostPanel.add(textContentPanel,BorderLayout.CENTER);
        singlePostPanel.add(buttonPanel,BorderLayout.SOUTH);
        // Add the post panel to the main post container (postPanel)
        postPanel.add(singlePostPanel);
        // Refresh the panel to make the new content visible
        postPanel.revalidate();
        postPanel.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        Friends = new javax.swing.JLabel();
        Request = new javax.swing.JLabel();
        suggests = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        block = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        counter = new javax.swing.JLabel();
        Story = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jButton3 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jButton1 = new javax.swing.JButton();
        refresh = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("AN HUB");

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        Friends.setIcon(new javax.swing.ImageIcon(getClass().getResource("/friends.png"))); // NOI18N
        Friends.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                FriendsMousePressed(evt);
            }
        });

        Request.setIcon(new javax.swing.ImageIcon(getClass().getResource("/request.png"))); // NOI18N
        Request.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                RequestMousePressed(evt);
            }
        });

        suggests.setIcon(new javax.swing.ImageIcon(getClass().getResource("/suggest.png"))); // NOI18N
        suggests.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                suggestsMousePressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setLabelFor(Friends);
        jLabel1.setText("Friends");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setLabelFor(Request);
        jLabel3.setText("Friend Request");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setLabelFor(Request);
        jLabel4.setText("Friend Suggestion");

        block.setIcon(new javax.swing.ImageIcon(getClass().getResource("/block.png"))); // NOI18N
        block.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                blockMousePressed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Block List");

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/user.png"))); // NOI18N
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel10MousePressed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel11.setText("Profile");

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/groups.png"))); // NOI18N
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel12MousePressed(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Groups");

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/search.png"))); // NOI18N
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel14MousePressed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel15.setText("Search");

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/bell.png"))); // NOI18N
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                NotificationsButton(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel17.setText("Notifications");

        counter.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        counter.setForeground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 351, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(counter, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel17))
                .addGap(66, 66, 66)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addGap(12, 12, 12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(suggests)
                        .addGap(63, 63, 63)
                        .addComponent(block)
                        .addGap(19, 19, 19)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(Request)
                        .addGap(51, 51, 51))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(Friends))
                .addGap(21, 21, 21))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(suggests)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(Request, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(Friends))
                                    .addComponent(block))
                                .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(jLabel16)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(counter, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4)
                        .addComponent(jLabel2))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(jLabel15)
                        .addComponent(jLabel13))
                    .addComponent(jLabel17))
                .addGap(150, 150, 150))
        );

        Story.setBackground(new java.awt.Color(153, 153, 153));
        Story.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        Story.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Story.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons8-stories-50.png"))); // NOI18N
        Story.setOpaque(true);

        jButton2.setBackground(new java.awt.Color(102, 102, 102));
        jButton2.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jButton2.setText("ADD STORY");
        jButton2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane1.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                jScrollPane1ComponentAdded(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(102, 102, 102));
        jButton3.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jButton3.setText("ADD POST");
        jButton3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("What is in Your Mind");
        jLabel5.setOpaque(true);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel6.setText("STORIES");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel7.setText("POSTS");

        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jButton1.setBackground(new java.awt.Color(153, 153, 153));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/logout.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        refresh.setBackground(new java.awt.Color(153, 153, 153));
        refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/refresh.png"))); // NOI18N
        refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel8.setText("Refresh");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel9.setText("LogOut");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(Story, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(refresh))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jButton1)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel7))
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(7, 7, 7))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Story, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 24, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(refresh, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jScrollPane1ComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_jScrollPane1ComponentAdded

    }//GEN-LAST:event_jScrollPane1ComponentAdded

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        PostUploader a1 = new PostUploader(authorId);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        StoryUploader a1 = new StoryUploader(authorId);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        for (UserAccount acc : accounts) {
            if (acc.getUser().getUserId().equals(authorId)) {
                acc.getUser().setStatus("Offline");
                System.out.println(acc.getUser().getStatus());
                UpdateAccount a = new UpdateAccount();
                a.save(acc);

            }
        }
        LoginWindpw windows = new LoginWindpw();
        dispose();
        windows.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void suggestsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_suggestsMousePressed
        for (UserAccount acc : accounts) {
            System.out.println(authorId);
            if (acc.getUser().getUserId().equals(authorId)) {

                FriendSuggestionFileManager a = new FriendSuggestionFileManager();
                FriendSuggestionsWindow a1 = new FriendSuggestionsWindow(acc, a.provideFriendSuggestions(acc));

                a1.setVisible(true);
            }
        }
    }//GEN-LAST:event_suggestsMousePressed

    private void refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshActionPerformed

        this.dispose();
        backendContent backend = new backendContent("post_database.json", "story_database.json");
        stories = backend.getAllStories();  // Load stories correctly
        TailoredPosts tailoredPosts = new TailoredPosts("post_database.json", this.authorId);
        posts = tailoredPosts.getAllPosts();
        showStories();
        showPosts();
        setVisible(true);
        NotificationFileManager notification = new NotificationFileManager();
        ArrayList<Notification> notifications = notification.loadNotificationList(authorId);

// Ensure notifications is not null before accessing size
        if (notifications == null) {
            notifications = new ArrayList<>(); // Initialize with an empty list if null
        }

        String number = String.valueOf(notifications.size());
        counter.setText(number);



    }//GEN-LAST:event_refreshActionPerformed

    private void RequestMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_RequestMousePressed
        for (UserAccount acc : accounts) {
            System.out.println(authorId);
            System.out.println(acc.getUser().getUserId());

            if (acc.getUser().getUserId().equals(authorId)) {

                FriendRequestWindow a1 = new FriendRequestWindow(acc);

                a1.setVisible(true);
            }
        }    }//GEN-LAST:event_RequestMousePressed

    private void FriendsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FriendsMousePressed
        for (UserAccount acc : accounts) {
            System.out.println(authorId);
            System.out.println(acc.getUser().getUserId());

            if (acc.getUser().getUserId().equals(authorId)) {

                FriendListWindow a1 = new FriendListWindow(acc);

                a1.setVisible(true);
            }
        }
    }//GEN-LAST:event_FriendsMousePressed

    private void blockMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_blockMousePressed
        for (UserAccount acc : accounts) {
            System.out.println(authorId);
            System.out.println(acc.getUser().getUserId());

            if (acc.getUser().getUserId().equals(authorId)) {

                BlockedListWindow a1 = new BlockedListWindow(acc);

                a1.setVisible(true);
            }
        }


    }//GEN-LAST:event_blockMousePressed

    private void jLabel10MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MousePressed

        for (UserAccount acc : accounts) {
            System.out.println(authorId);
            System.out.println(acc.getUser().getUserId());

            if (acc.getUser().getUserId().equals(authorId)) {

                ProfileWindow a1 = new ProfileWindow(acc.getProfile(), acc);

                a1.setVisible(true);
            }
        }
    }//GEN-LAST:event_jLabel10MousePressed

    private void jLabel12MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MousePressed
        UserGroupsWindow a = new UserGroupsWindow(getAcc(authorId));
    }//GEN-LAST:event_jLabel12MousePressed

    private void jLabel14MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MousePressed
        SearchEngineFrontend engine = new SearchEngineFrontend(authorId);
        engine.setVisible(true);
    }//GEN-LAST:event_jLabel14MousePressed

    private void NotificationsButton(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NotificationsButton
      UserAccount account = getAcc(authorId);
       NotificationFileManager manager = new NotificationFileManager();
       NotificationPanel panel=new NotificationPanel(account,manager);
       panel.setVisible(true);
       ArrayList<Notification> notifications = new ArrayList<>();
       manager.saveNotificationList(authorId, notifications);
    }//GEN-LAST:event_NotificationsButton

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewsFeedWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewsFeedWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewsFeedWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewsFeedWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // new NewsFeedWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Friends;
    private javax.swing.JLabel Request;
    private javax.swing.JLabel Story;
    private javax.swing.JLabel block;
    private javax.swing.JLabel counter;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton refresh;
    private javax.swing.JLabel suggests;
    // End of variables declaration//GEN-END:variables
}
