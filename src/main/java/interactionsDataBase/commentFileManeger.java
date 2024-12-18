/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interactionsDataBase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import interactionsBackEnd.comments;

import java.io.*;
import java.util.ArrayList;

public class commentFileManeger {

    private String baseDirectory;
    private ObjectMapper objectMapper;
    private String type;

    public commentFileManeger(String baseDirectory) {
        this.type = "comments";
        this.baseDirectory = baseDirectory;
        this.objectMapper = new ObjectMapper();
        createBaseDirectory(); // Ensure the base directory exists    }

    }
       // Create the base directory if it doesn't exist
    private void createBaseDirectory() {
        File dir = new File(baseDirectory);
        if (!dir.exists()) {
            dir.mkdirs(); // Create the directory and any necessary parent directories
        }
    }
    // Generate a JSON file path for a user's friend list using their user ID
    public String generateContentLikesListFilePath(String contentId) {
        return baseDirectory + File.separator + "Content_" + contentId + "_"+this.type+".json";
    }
    public void saveComment(String contentId, ArrayList<comments> comments) {
        String filePath = generateContentLikesListFilePath(contentId);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        File file =new File(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))){
           
                // objectMapper.writeValue(file,friends);
                try (PrintWriter pw = new PrintWriter(file)) {}
                catch (IOException e) {
                    e.printStackTrace();
                }

            
            for (comments comment : comments)
            {
                String profileJson = objectMapper.writeValueAsString(comment);
                writer.write(profileJson);
                writer.newLine(); // Add a newline after each JSON object
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    // Load the friend list for a specific user
    public ArrayList<comments> loadComment(String conteentId) {

        String filePath = generateContentLikesListFilePath(conteentId);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        ArrayList<comments> comments = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                comments comment = objectMapper.readValue(line, comments.class);
                comments.add(comment);
            }
        } catch (IOException e) {
            System.err.println("Error reading accounts from file: " + e.getMessage());
            throw new RuntimeException("Failed to load accounts", e);
        }
        finally {
            return comments;
        }

    }

    
    
}
