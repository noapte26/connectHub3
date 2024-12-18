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
import java.io.File;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
        mainScreen.setViewportView(messagePanel);
        // Display all messages in the chat panel
        displayMessages();

        setTitle("Messenger");
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

        mainScreen = new javax.swing.JScrollPane();
        messagePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Input = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainScreen.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        mainScreen.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        javax.swing.GroupLayout messagePanelLayout = new javax.swing.GroupLayout(messagePanel);
        messagePanel.setLayout(messagePanelLayout);
        messagePanelLayout.setHorizontalGroup(
            messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 607, Short.MAX_VALUE)
        );
        messagePanelLayout.setVerticalGroup(
            messagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 463, Short.MAX_VALUE)
        );

        mainScreen.setViewportView(messagePanel);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/b..png"))); // NOI18N
        jLabel1.setText("AN Hub");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/right-arrow.png"))); // NOI18N
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel3MousePressed(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/upload.png"))); // NOI18N
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel4MousePressed(evt);
            }
        });

        Input.setColumns(20);
        Input.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Input.setLineWrap(true);
        Input.setRows(5);
        Input.setWrapStyleWord(true);
        Input.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jScrollPane1.setViewportView(Input);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainScreen)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(261, 261, 261)
                        .addComponent(jLabel1)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(mainScreen, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jLabel1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MousePressed
     String text = Input.getText();
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
    MessageFileManager messageFileManager = new MessageFileManager("Message_lists", "Message");
    ArrayList<Message> messages = messageFileManager.loadMessages(chatId);
   messages.add(message1);
   messageFileManager.saveMessage(me.getUser().getUserId(), messages);
       
      displayMessages(); 
    }//GEN-LAST:event_jLabel3MousePressed

    private void jLabel4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MousePressed
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

            // Optionally, display the file path in jLabel13
            jLabel3.setText("File Path: " + imagePath);

            // The image path is now saved in the 'imagePath' variable, ready for use
        }
    }//GEN-LAST:event_jLabel4MousePressed
private void displayMessages() {
        MessageFileManager messageFileManager = new MessageFileManager("Message_lists", "Message");
        ArrayList<Message> messages = messageFileManager.loadMessages(chatId);
        
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));  // Stack messages vertically
        
        // Set the message panel as the viewport view of the mainScreen
        
        
    
        
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));  // Layout for displaying messages vertically

        // Iterate through each message in the list
        for (Message message : messages) {
            JPanel msgContainer = new JPanel();  // This will hold the message content (text or image)

            // Check if the message contains text or an image
            String messageText = message.getMessageText();
            String messageImagePath = message.getImagePath();

            if (messageText != null && !messageText.isEmpty()) {
                // If it's a text message, display it in a label
                JLabel textLabel = new JLabel("<html>" + messageText + "</html>");  // Using HTML to allow line breaks
                msgContainer.add(textLabel);
            }

            if (messageImagePath != null && !messageImagePath.isEmpty()) {
                // If the message contains an image, display the image
                ImageIcon imageIcon = new ImageIcon(messageImagePath);
                JLabel imageLabel = new JLabel(imageIcon);
                msgContainer.add(imageLabel);
            }

            // Add this message panel to the main message panel
            messagePanel.add(msgContainer);
        }

        
    }
    /**
     * @param args the command line arguments
     */
    
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea Input;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane mainScreen;
    private javax.swing.JPanel messagePanel;
    // End of variables declaration//GEN-END:variables
}
