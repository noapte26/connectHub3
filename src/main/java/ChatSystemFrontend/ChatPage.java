/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ChatSystemFrontend;

import Account.UserAccount;
import ChatSystemBackend.ChatFileManager;
import ChatSystemBackend.Message;
import ChatSystemBackend.MessageFileManager;
import ChatSystemBackend.TheBuilder;
import groupDataBase.MembersFileManager;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author gg
 */
public class ChatPage extends javax.swing.JFrame {

    private String imagePath = "";
    private String chatId;  // Declare chatId as a member variable
    private UserAccount me;

    public ChatPage(UserAccount meSender, UserAccount theres, String chatId) {
        initComponents();
        this.me = meSender;  // Set the me instance variable
        this.chatId = chatId; // Set the chatId
        // Configure panel1 with BorderLayout
        panel1.setLayout(new BorderLayout());
        panel1.setBackground(Color.GRAY); // Dark background for better visibility

        // Profile Picture
        ImageIcon profileIcon = new ImageIcon(theres.getProfile().getProfileImageUrl());
        Image profileImage = profileIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Resize
        JLabel profilePicLabel = new JLabel(new ImageIcon(profileImage));
        profilePicLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Add padding
        panel1.add(profilePicLabel, BorderLayout.WEST);

        // User Details Panel
        JPanel userDetailsPanel = new JPanel();
        userDetailsPanel.setLayout(new BoxLayout(userDetailsPanel, BoxLayout.Y_AXIS));
        userDetailsPanel.setBackground(Color.GRAY);

        // Username Label
        JLabel usernameLabel = new JLabel(theres.getUser().getUserName());
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        usernameLabel.setForeground(Color.WHITE);
        userDetailsPanel.add(usernameLabel, BorderLayout.CENTER);
        // Add userDetailsPanel to the center of panel1
        panel1.add(userDetailsPanel, BorderLayout.CENTER);

        jScrollPane1.setViewportView(messagePanel);
        // Display all messages in the chat panel
        displayMessages();

        setTitle("Messenger");
        setVisible(true);
        this.setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        messagePanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        input = new javax.swing.JTextArea();
        upload = new javax.swing.JButton();
        send = new javax.swing.JButton();
        panel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout messagePanelLayout = new javax.swing.GroupLayout(messagePanel);
        messagePanel.setLayout(messagePanelLayout);
        messagePanelLayout.setHorizontalGroup(
            messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 405, Short.MAX_VALUE)
        );
        messagePanelLayout.setVerticalGroup(
            messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 436, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(messagePanel);

        input.setColumns(20);
        input.setLineWrap(true);
        input.setRows(5);
        input.setWrapStyleWord(true);
        jScrollPane2.setViewportView(input);

        upload.setBackground(new java.awt.Color(153, 153, 153));
        upload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/upload.png"))); // NOI18N
        upload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadActionPerformed(evt);
            }
        });

        send.setBackground(new java.awt.Color(153, 153, 153));
        send.setIcon(new javax.swing.ImageIcon(getClass().getResource("/right-arrow.png"))); // NOI18N
        send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendActionPerformed(evt);
            }
        });

        panel1.setBackground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 46, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(send)
                            .addComponent(upload)))
                    .addComponent(panel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(8, 8, 8))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(send)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(upload))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendActionPerformed
        String text = input.getText();
        if (text.isEmpty() && imagePath.isEmpty()) {
            // If both are empty, display a message
            JOptionPane.showMessageDialog(this, "Please enter a message or upload an image.");
            return;  // Stop the execution if both are empty
        }

        // Create the message object using the actual chatId (already assigned in constructor)
        TheBuilder builder = new TheBuilder(chatId, me);

        // Set the message text and image path in the builder
        builder.setMessageText(text).setImagePath(imagePath);
        Message message1 = builder.build();
        MessageFileManager messageFileManager = new MessageFileManager();
        ArrayList<Message> messages = messageFileManager.loadMessage(chatId);
        messages.add(message1);
        messageFileManager.saveMessage(chatId, messages);
        input.setText("");
        displayMessages();
    }//GEN-LAST:event_sendActionPerformed

    private void uploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadActionPerformed
        // Open a file chooser dialog to select a file (e.g., image)
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select an Image");

        // You can specify filters to select only specific types of files, like images
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "png", "gif"));

        // Show the dialog and wait for the user to select a file
        int returnValue = fileChooser.showOpenDialog(this);

        // If the user selects a file, returnValue will be APPROVE_OPTION
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            File selectedFile = fileChooser.getSelectedFile();

            // Get the file path and store it in the imagePath variable
            imagePath = selectedFile.getAbsolutePath();

            // The image path is now saved in the 'imagePath' variable, ready for use
        }

    }//GEN-LAST:event_uploadActionPerformed
    
       private void displayMessages() {
    // Load messages
    MessageFileManager messageFileManager = new MessageFileManager();
    ArrayList<Message> messages = messageFileManager.loadMessage(chatId);

    if (messages == null) {
        messages = new ArrayList<>();
    }

    // Debug: Check loaded messages
    System.out.println("Messages loaded: " + messages.size());

    // Clear old content
    messagePanel.removeAll();

    if (messages.isEmpty()) {
        // Configure layout and add "No messages" label
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        JLabel noMessagesLabel = new JLabel("No messages available.");
        noMessagesLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        noMessagesLabel.setForeground(Color.BLACK);
        noMessagesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messagePanel.add(noMessagesLabel);
    } else {
        // Configure layout for stacking messages vertically
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));

        // Add each message to the panel
        for (Message message : messages) {
            JPanel msgContainer = new JPanel();
            boolean isSender = message.getSender().getUser().getUserId().equals(me.getUser().getUserId());
            msgContainer.setLayout(new FlowLayout(isSender ? FlowLayout.RIGHT : FlowLayout.LEFT));

            // Profile picture
            ImageIcon profileIcon = new ImageIcon(message.getSender().getProfile().getProfileImageUrl());
            Image profileImage = profileIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            JLabel profilePicLabel = new JLabel(new ImageIcon(profileImage));

            // Handle text message
            if (message.getMessageText() != null && !message.getMessageText().isEmpty()) {
                JLabel textLabel = new JLabel("<html>" + message.getMessageText() + "</html>");
                textLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                if (isSender) {
                    msgContainer.add(textLabel);
                    msgContainer.add(profilePicLabel);
                } else {
                    msgContainer.add(profilePicLabel);
                    msgContainer.add(textLabel);
                }
            }

            // Handle image message
            if (message.getImagePath() != null && !message.getImagePath().isEmpty()) {
                File imgFile = new File(message.getImagePath());
                if (imgFile.exists()) {
                    ImageIcon imageIcon = new ImageIcon(message.getImagePath());
                    Image image = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                    JLabel imageLabel = new JLabel(new ImageIcon(image));
                    msgContainer.add(imageLabel);
                } else {
                    System.out.println("Image file not found: " + message.getImagePath());
                }
            }

            // Add spacing between messages
            messagePanel.add(msgContainer);
            messagePanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing
        }
    }

    // Debug: Check if components are added to the panel
    System.out.println("MessagePanel components count: " + messagePanel.getComponentCount());

    // Update UI
    messagePanel.revalidate();
    messagePanel.repaint();
    jScrollPane1.setViewportView(messagePanel); // Ensure the scroll pane updates
}


    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea input;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel messagePanel;
    private javax.swing.JPanel panel1;
    private javax.swing.JButton send;
    private javax.swing.JButton upload;
    // End of variables declaration//GEN-END:variables
}
