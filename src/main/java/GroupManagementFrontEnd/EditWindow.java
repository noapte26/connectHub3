package GroupManagementFrontEnd;

import GroupManagementBackEnd.Group;
import groupDataBase.Updategroup;

import javax.swing.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class EditWindow extends JFrame {
    private JTextField groupNameField;
    private JTextArea groupDescriptionField;
    private JLabel groupPhotoLabel;
    private File selectedPhotoFile;
    private Group group;
    public EditWindow(Group group) {
       this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.group=group;
        setTitle("Edit Group");
        setSize(400, 500);
        setLayout(new BorderLayout());

        // Top Panel for group photo
        JPanel photoPanel = new JPanel();
        photoPanel.setLayout(new BorderLayout());
        photoPanel.setBackground(Color.decode("#121212"));
        groupPhotoLabel = new JLabel();
        groupPhotoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        groupPhotoLabel.setIcon(resizeImage(group.getPhoto(), 200, 200));

        JButton changePhotoButton = new JButton("Change Photo");
        changePhotoButton.setBackground(Color.decode("#121212"));
        changePhotoButton.setForeground(Color.LIGHT_GRAY);
        changePhotoButton.addActionListener(e -> choosePhoto());

        photoPanel.add(groupPhotoLabel, BorderLayout.CENTER);
        photoPanel.add(changePhotoButton, BorderLayout.SOUTH);

        // Middle Panel for group details
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(6, 1, 10, 10));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        detailsPanel.setBackground(Color.DARK_GRAY);
        JLabel groupNameLabel = new JLabel("Group Name:");
        groupNameField = new JTextField(group.getName());
        groupNameLabel.setForeground(Color.WHITE);
        JLabel groupDescriptionLabel = new JLabel("Group Description:");
        groupDescriptionLabel.setForeground(Color.WHITE);
        groupDescriptionField = new JTextArea(group.getDiscription());
        groupDescriptionField.setLineWrap(true);
        groupDescriptionField.setWrapStyleWord(true);
        JScrollPane descriptionScrollPane = new JScrollPane(groupDescriptionField);

        detailsPanel.add(groupNameLabel);
        detailsPanel.add(groupNameField);
        detailsPanel.add(groupDescriptionLabel);
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
        String newGroupName = groupNameField.getText();
        String newGroupDescription = groupDescriptionField.getText();

        if (newGroupName.isEmpty() || newGroupDescription.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Group name and description cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Simulate saving changes (you can integrate backend saving logic here)
        this.group.setName(newGroupName);
        this.group.setDiscription(newGroupDescription);
        if (selectedPhotoFile != null) {
           this.group.setPhoto(selectedPhotoFile.getAbsolutePath());
        } else {
            System.out.println("Photo remains unchanged.");
        }
        new Updategroup(group);
        JOptionPane.showMessageDialog(this, "Changes saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

   /* public static void main(String[] args) {
        // Example current group data
        String currentGroupName = "Developers Group";
        String currentDescription = "A group for software developers.";
        String currentPhotoPath = "default-group-photo.png"; // Replace with an actual image path

       // new EditWindow(currentGroupName, currentDescription, currentPhotoPath);
    }*/
}

