package FrontendCreation;

import ContentCreation.Story;
import ContentCreation.backendContent;
import ContentCreation.Content;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StoryViewer extends JFrame {

    public StoryViewer() {
        setTitle("Story Viewer");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top Panel (Title)
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(51, 153, 255));
        JLabel titleLabel = new JLabel("View All Stories");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);

        // Main Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); // Stack stories vertically
        contentPanel.setBackground(new Color(240, 240, 240));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // Load Stories using backendContent
        backendContent backend = new backendContent("post_database.json", "story_database.json");
        List<Content> stories = backend.getAllStories();  // Load stories correctly

        System.out.println("Number of stories: " + stories.size()); // Debugging message

        if (stories.isEmpty()) {
            JLabel noStoriesLabel = new JLabel("No stories available.");
            noStoriesLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            noStoriesLabel.setForeground(Color.GRAY);
            noStoriesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPanel.add(noStoriesLabel);
        } else {
            for (Content content : stories) {
                if (content instanceof Story) {
                    Story story = (Story) content;
                    JPanel storyPanel = createStoryPanel(story);
                    contentPanel.add(storyPanel);
                    contentPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing between stories
                }
            }
        }
    }

    // Create a Panel for a Single Story
    private JPanel createStoryPanel(Story story) {
        JPanel storyPanel = new JPanel();
        storyPanel.setLayout(new BorderLayout());
        storyPanel.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1));
        storyPanel.setBackground(Color.WHITE);

        // Story Text Content
        JTextArea textArea = new JTextArea(story.getContent());
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setBackground(Color.WHITE);
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane textScrollPane = new JScrollPane(textArea);
        storyPanel.add(textScrollPane, BorderLayout.CENTER); // Text in the center

        // Image Preview
        if (story.getImagePath() != null) {
            JLabel imageLabel = new JLabel();
            ImageIcon imageIcon = new ImageIcon(story.getImagePath());
            Image image = imageIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH); // Larger image
            imageLabel.setIcon(new ImageIcon(image));
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            storyPanel.add(imageLabel, BorderLayout.SOUTH); // Image below text
        }

        return storyPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StoryViewer viewer = new StoryViewer();
            viewer.setVisible(true);
        });
    }
}
