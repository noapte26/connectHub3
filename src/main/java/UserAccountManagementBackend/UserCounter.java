package UserAccountManagementBackend;

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

public class UserCounter {
    
    private static AtomicInteger userCounter = new AtomicInteger(0);

    static {
        File file = new File("userCounter.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            if (line != null) {
                userCounter.set(Integer.parseInt(line));
            }
        } catch (IOException e) {
            System.out.println("File not found or error reading the counter. Starting from 0.");
        }
    }

    public UserCounter() {
        userCounter.incrementAndGet();  // Thread-safe increment
        saveCounterToFile();
    }

    public static int getUserCounter() {
        return userCounter.get();  // Thread-safe access
    }

    private static void saveCounterToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("userCounter.txt"))) {
            bw.write(String.valueOf(userCounter.get()));
        } catch (IOException e) {
            System.out.println("Error saving counter: " + e.getMessage());
        }
    }
}
