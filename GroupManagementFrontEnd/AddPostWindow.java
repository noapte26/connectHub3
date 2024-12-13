package GroupManagementFrontEnd;

import Account.UserAccount;
import ContentCreation.Post;
import ContentCreation.backendContent;
import GroupManagementBackEnd.Group;
import GroupManagementBackEnd.commonRole;
import groupDataBase.ContentFileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.ArrayList;

public class AddPostWindow extends JFrame {

    private JTextArea textArea;
    private JLabel imagePreviewLabel;
    private String imagePath = null;
    private Group group;
    private UserAccount account;
    ContentFileManager contentFileManager=new ContentFileManager("posts_lists","Posts");
    public AddPostWindow(UserAccount account,Group group) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        this.group=group;
        this.account=account;
        setTitle("Post Uploader");
        setSize(900, 700);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(240, 240, 240));

        // Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(51, 153, 255));
        JLabel title = new JLabel("Create a New Post");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        topPanel.add(title);
        add(topPanel, BorderLayout.NORTH);

        // Center Panel
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(240, 240, 240));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Text Area
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBackground(new Color(240, 240, 240));
        JLabel textLabel = new JLabel("Enter Post Text:");
        textLabel.setFont(new Font("Arial", Font.BOLD, 16));
        textArea = new JTextArea(6, 30);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1));
        JScrollPane scrollPane = new JScrollPane(textArea);
        textPanel.add(textLabel, BorderLayout.NORTH);
        textPanel.add(scrollPane, BorderLayout.CENTER);

        centerPanel.add(textPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15))); // Spacer

        // Image Upload Section
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(new Color(240, 240, 240));
        imagePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY, 1), "Photo Preview", 0, 0, new Font("Arial", Font.BOLD, 14)));

        imagePreviewLabel = new JLabel("Drag & Drop Photo Here", SwingConstants.CENTER);
        imagePreviewLabel.setPreferredSize(new Dimension(500, 300));
        imagePreviewLabel.setBackground(Color.WHITE);
        imagePreviewLabel.setOpaque(true);
        imagePreviewLabel.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 2));
        enableDragAndDrop(imagePreviewLabel);

        JButton uploadButton = new JButton("Upload Photo");
        uploadButton.setFont(new Font("Arial", Font.BOLD, 14));
        uploadButton.setBackground(new Color(102, 204, 255));
        uploadButton.setForeground(Color.WHITE);
        uploadButton.addActionListener(e -> chooseImage());

        imagePanel.add(imagePreviewLabel, BorderLayout.CENTER);
        imagePanel.add(uploadButton, BorderLayout.SOUTH);

        centerPanel.add(imagePanel);
        add(centerPanel, BorderLayout.CENTER);

        // Submit Button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(240, 240, 240));
        JButton submitButton = new JButton("Submit Post");
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.setBackground(new Color(51, 153, 255));
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(e -> submitPost());

        bottomPanel.add(submitButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void submitPost() {
        String textContent = textArea.getText();

        if (textContent.isEmpty() && imagePath == null) {
            JOptionPane.showMessageDialog(this, "Please add text or an image.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Post newPost = new Post(textContent, imagePath);
        newPost.setAuthorId(this.account.getUser().getUserId()); // Replace with dynamic author ID if needed
        commonRole c=new commonRole();
        c.addPostRequest(newPost,group);
        JOptionPane.showMessageDialog(this, "Post added successfully!");
        clearForm();
        setVisible(false);
    }

    private void clearForm() {
        textArea.setText("");
        imagePreviewLabel.setIcon(null);
        imagePreviewLabel.setText("Drag & Drop Photo Here");
        imagePath = null;
    }

    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            imagePath = file.getAbsolutePath();
            setImagePreview(imagePath);
        }
    }

    private void setImagePreview(String path) {
        ImageIcon imageIcon = new ImageIcon(path);
        Image image = imageIcon.getImage().getScaledInstance(imagePreviewLabel.getWidth(), imagePreviewLabel.getHeight(), Image.SCALE_SMOOTH);
        imagePreviewLabel.setIcon(new ImageIcon(image));
        imagePreviewLabel.setText("");
    }

    private void enableDragAndDrop(JLabel label) {
        new DropTarget(label, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent dtde) {
                try {
                    dtde.acceptDrop(DnDConstants.ACTION_COPY);
                    java.util.List<File> droppedFiles = (java.util.List<File>) dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

                    if (!droppedFiles.isEmpty()) {
                        File file = droppedFiles.get(0);
                        imagePath = file.getAbsolutePath();
                        setImagePreview(imagePath);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(label, "Invalid file format or drag operation.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {

        // SwingUtilities.invokeLater(() -> new PostUploader(a.authorId).setVisible(true));
    }
}