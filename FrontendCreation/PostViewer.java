package FrontendCreation;

import ContentCreation.Post;
import ContentCreation.backendContent;
import ContentCreation.Content;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PostViewer extends JFrame {

    public PostViewer() {
        setTitle("Post Viewer");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top Panel (Title)
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(51, 153, 255));
        JLabel titleLabel = new JLabel("View All Posts");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);

        // Main Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); // Stack posts vertically
        contentPanel.setBackground(new Color(240, 240, 240));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // Load Posts using backendContent
        backendContent backend = new backendContent("post_database.json", "story_database.json");
        List<Content> posts = backend.getAllPosts();

        System.out.println("Number of posts: " + posts.size()); // Debugging message

        if (posts.isEmpty()) {
            JLabel noPostsLabel = new JLabel("No posts available.");
            noPostsLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            noPostsLabel.setForeground(Color.GRAY);
            noPostsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPanel.add(noPostsLabel);
        } else {
            for (Content content : posts) {
                if (content instanceof Post) {
                    Post post = (Post) content;
                    JPanel postPanel = createPostPanel(post);
                    contentPanel.add(postPanel);
                    contentPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing between posts
                }
            }
        }
    }

    // Create a Panel for a Single Post
    private JPanel createPostPanel(Post post) {
        JPanel postPanel = new JPanel();
        postPanel.setLayout(new BorderLayout());
        postPanel.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1));
        postPanel.setBackground(Color.WHITE);

        // Post Text Content
        JTextArea textArea = new JTextArea(post.getContent());
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setBackground(Color.WHITE);
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane textScrollPane = new JScrollPane(textArea);
        postPanel.add(textScrollPane, BorderLayout.CENTER); // Text in the center

        // Image Preview
        if (post.getImagePath() != null) {
            JLabel imageLabel = new JLabel();
            ImageIcon imageIcon = new ImageIcon(post.getImagePath());
            Image image = imageIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH); // Larger image
            imageLabel.setIcon(new ImageIcon(image));
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            postPanel.add(imageLabel, BorderLayout.SOUTH); // Image below text
        }

        return postPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PostViewer viewer = new PostViewer();
            viewer.setVisible(true);
        });
    }
}
