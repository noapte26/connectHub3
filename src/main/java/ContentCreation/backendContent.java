package ContentCreation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.core.type.TypeReference;
import ContentCreation.Content;
import ContentCreation.Post;
import ContentCreation.Story;

import java.io.*;
import java.util.*;

public class backendContent {
    private Map<String, Post> postDatabase;
    private Map<String, Story> storyDatabase;

    private final ObjectMapper objectMapper;

    public backendContent(String postFilePath, String storyFilePath) {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());  // Register JavaTimeModule for LocalDateTime handling
        postDatabase = new HashMap<>();
        storyDatabase = new HashMap<>();
        loadPostDatabase(postFilePath);
        loadStoryDatabase(storyFilePath);
    }

    // Load Posts
    public void loadPostDatabase(String filePath) {
        try (Reader reader = new FileReader(filePath)) {
            postDatabase = objectMapper.readValue(reader, new TypeReference<Map<String, Post>>() {});
            System.out.println("Loaded " + postDatabase.size() + " posts."); // Debugging statement
            for (Map.Entry<String, Post> entry : postDatabase.entrySet()) {
                System.out.println("Post ID: " + entry.getKey() + ", Content: " + entry.getValue().getContent());
            }
        } catch (IOException e) {
            System.err.println("Error loading posts: " + e.getMessage());
            postDatabase = new HashMap<>();
        }
    }

    // Load Stories
    public void loadStoryDatabase(String filePath) {
        try (Reader reader = new FileReader(filePath)) {
            storyDatabase = objectMapper.readValue(reader, new TypeReference<Map<String, Story>>() {});
            System.out.println("Loaded " + storyDatabase.size() + " stories."); // Debugging message
        } catch (IOException e) {
            System.err.println("Error loading stories: " + e.getMessage());
            storyDatabase = new HashMap<>();
        }
    }

    // Save Posts
    public void savePostDatabase(String filePath) {
        try (Writer writer = new FileWriter(filePath)) {
            objectMapper.writeValue(writer, postDatabase);
            System.out.println("Posts saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving posts: " + e.getMessage());
        }
    }

    // Save Stories
    public void saveStoryDatabase(String filePath) {
        try (Writer writer = new FileWriter(filePath)) {
            objectMapper.writeValue(writer, storyDatabase);
            System.out.println("Stories saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving stories: " + e.getMessage());
        }
    }

    // Add Post
    public void addPost(Post post, String filePath) {
        if (post != null) {
            postDatabase.put(post.getContentId(), post);
            savePostDatabase(filePath);
        }
    }

    // Add Story
    public void addStory(Story story, String filePath) {
        if (story != null) {
            storyDatabase.put(story.getContentId(), story);
            saveStoryDatabase(filePath);
        }
    }

    // Get all Posts
    public List<Content> getAllPosts() {
        List<Content> posts = new ArrayList<>();
        for (Content content : postDatabase.values()) {
            posts.add(content);
        }
        return posts;
    }

    // Get all Stories
    public List<Content> getAllStories() {
        List<Content> stories = new ArrayList<>();
        for (Content content : storyDatabase.values()) {
            stories.add(content);
        }
        return stories;
    }

    // Get Active Stories
    public List<Story> getActiveStories(String authorId) {
        List<Story> stories = new ArrayList<>();
        for (Story story : storyDatabase.values()) {
            if (story.getAuthorId().equals(authorId) && !story.isExpired()) {
                stories.add(story);
            }
        }
        return stories;
    }
}
