package GroupManagementFrontEnd;

import ContentCreation.Post;
import GroupManagementBackEnd.Group;
import GroupManagementBackEnd.commonRole;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class EditPostWindow extends JFrame {
    private JTextArea postcontent;
    private JLabel groupPhotoLabel;
    private File selectedPhotoFile;
    private Group group;
    private Post post;
    public EditPostWindow(Post post, Group group) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.group=group;
        this.post=post;
        setTitle("Edit Post");
        setSize(400, 500);
        setLayout(new BorderLayout());

        // Top Panel for Post photo
        JPanel photoPanel = new JPanel();
        photoPanel.setLayout(new BorderLayout());
        photoPanel.setBackground(Color.decode("#121212"));
        groupPhotoLabel = new JLabel();
        groupPhotoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        groupPhotoLabel.setIcon(resizeImage(post.getImagePath(), 200, 200));

        JButton changePhotoButton = new JButton("Edit Photo");
        changePhotoButton.setBackground(Color.decode("#121212"));
        changePhotoButton.setForeground(Color.LIGHT_GRAY);
        changePhotoButton.addActionListener(e -> choosePhoto());

        photoPanel.add(groupPhotoLabel, BorderLayout.CENTER);
        photoPanel.add(changePhotoButton, BorderLayout.SOUTH);

        // Middle Panel for Post details
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(6, 1, 10, 10));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        detailsPanel.setBackground(Color.DARK_GRAY);

        JLabel content = new JLabel("Text Area");
        content.setForeground(Color.WHITE);
        postcontent = new JTextArea(post.getContent());
        postcontent.setLineWrap(true);
        postcontent.setWrapStyleWord(true);
        JScrollPane descriptionScrollPane = new JScrollPane(postcontent);

        detailsPanel.add(content);
        detailsPanel.add(descriptionScrollPane);

        // Bottom Panel for Save Button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.DARK_GRAY);
        JButton saveButton = new JButton("Save Changes");
        saveButton.setBackground(Color.decode("#121212"));
        saveButton.setForeground(Color.LIGHT_GRAY);
        saveButton.addActionListener(e -> saveChanges());

        buttonPanel.add(saveButton);

        // Add components to frame
        add(photoPanel, BorderLayout.NORTH);
        add(detailsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Choose a new photo
    private void choosePhoto() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedPhotoFile = fileChooser.getSelectedFile();
            groupPhotoLabel.setIcon(resizeImage(selectedPhotoFile.getAbsolutePath(), 200, 200));
        }
    }

    // Resize the image for JLabel
    private ImageIcon resizeImage(String imagePath, int width, int height) {
        if (imagePath == null || imagePath.isEmpty()) {
            return new ImageIcon();
        }
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    // Save the changes made by the user
    private void saveChanges() {
        String newGroupDescription = postcontent.getText();

        if ( newGroupDescription.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Group name and description cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Simulate saving changes (you can integrate backend saving logic here)
        commonRole c=new commonRole();
        String photoPath = null;
        if (selectedPhotoFile!=null)
        { photoPath=selectedPhotoFile.getAbsolutePath();}
        c.editPost(post,newGroupDescription,photoPath,group);
        JOptionPane.showMessageDialog(this, "Changes saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
    }

   /* public static void main(String[] args) {
        // Example current group data
        String currentGroupName = "Developers Group";
        String currentDescription = "A group for software developers.";
        String currentPhotoPath = "default-group-photo.png"; // Replace with an actual image path

       // new EditWindow(currentGroupName, currentDescription, currentPhotoPath);
    }*/
}
