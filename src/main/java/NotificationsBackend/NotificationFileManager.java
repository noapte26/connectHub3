/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NotificationsBackend;

/**
 *
 * @author hebai
 */

import Account.UserAccount;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class NotificationFileManager {
    private  String baseDirectory;
    private  ObjectMapper objectMapper;

    public NotificationFileManager() {
        this.baseDirectory = "notifications_lists";
        this.objectMapper = new ObjectMapper();
        createBaseDirectory(); // Ensure the base directory exists
    }

    // Create the base directory if it doesn't exist
    private void createBaseDirectory() {
        File dir = new File(baseDirectory);
        if (!dir.exists()) {
            dir.mkdirs(); // Create the directory and any necessary parent directories
        }
    }

    // Generate a JSON file path for a user's friend list using their user ID
    public String generateNotificationListFilePath(String userId) {
        return baseDirectory + File.separator + "user_" + userId + "_notifications.json";
    }
    // Create a JSON file for storing friend requests for a user, if it doesn't already exist
    public void createNotificationListFile(String userId) {
        String filePath = generateNotificationListFilePath(userId);
        File file = new File(filePath);

        try {
            if (!file.exists()) {
                boolean created = file.createNewFile();  // Create the file
                if (created) {
                    System.out.println("Notification List file created for user ID: " + userId);
                }
            } else {
                System.out.println("Notification List file already exists for user ID: " + userId);
            }
        } catch (IOException e) {
            System.err.println("Error creating notification List file for user ID: " + userId);
            e.printStackTrace();
        }
    }
    // Save a friend's list for a specific user
    public void saveNotificationList(String userId, ArrayList<Notification> notifications) {
        String filePath = generateNotificationListFilePath(userId);
         
        ObjectMapper objectMapper = new ObjectMapper();
   
        File file =new File(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))){
            
               
                try (PrintWriter pw = new PrintWriter(file)) {}
                catch (IOException e) {
                    e.printStackTrace();
                }

            
            for (Notification notification :notifications)
            {
                String notificationJson = objectMapper.writeValueAsString(notification);
                writer.write(notificationJson);
                writer.newLine(); // Add a newline after each JSON object
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        
       
    }

    // Load the Notification list for a specific user
   public ArrayList<Notification> loadNotificationList(String userId) {
    String filePath = generateNotificationListFilePath(userId);
    ArrayList<Notification> notifications = new ArrayList<>(); // Initialize the list
    File file = new File(filePath);

    if (file.exists() && file.length() > 0) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Notification notification = objectMapper.readValue(line, Notification.class);
                notifications.add(notification);
            }
        } catch (IOException e) {
            System.err.println("Error reading notifications from file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    return notifications;
}

    

    // Add a Notification to the user's Notification list
    public void addNotification(String userId, Notification notification) {
        ArrayList<Notification> notifications = loadNotificationList(userId);
        if(notifications == null){ // file is still empty
            ArrayList ns = new ArrayList();
            ns.add(notification);
            saveNotificationList(userId, ns);
        }
        else{
           notifications.add(notification);
           saveNotificationList(userId, notifications); 
        }
        
    }
}
