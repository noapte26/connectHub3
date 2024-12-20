/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NotificationsBackend;

/**
 *
 * @author hebai
 */
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class NotificationThread extends Thread {
    private boolean running = true;
    private NotificationFileManager notificationManager;
    private String userId;

    public NotificationThread(NotificationFileManager manager, String userId) {
        this.notificationManager = manager;
        this.userId = userId;
    }

    @Override
    public void run() {
        int lastNotificationCount = 0;

        while (running) {
            try {
                ArrayList<Notification> notifications = notificationManager.loadNotificationList(userId);
                int currentCount = (notifications != null) ? notifications.size() : 0;

                if (currentCount > lastNotificationCount) {
                    // Display new notifications
                    Notification newNotification = notifications.get(currentCount - 1);
                    SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(null, "New notification: " + newNotification, "Notification", JOptionPane.INFORMATION_MESSAGE);
                    });
                }

                lastNotificationCount = currentCount;
                Thread.sleep(5000); // Poll every 5 seconds
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void stopThread() {
        running = false;
    }
    
}
