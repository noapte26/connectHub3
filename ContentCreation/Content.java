package ContentCreation;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Content {
    public static final String contentDatabaseName = "content_database.json";
    protected String contentId;
    protected String authorId;
    protected String content;
    protected String imagePath; // Save the image path instead of base64 data

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS")  // ISO 8601 format
    protected LocalDateTime timestamp;

    public Content(String content, String imagePath) {
        this.contentId = generateContentId();
        this.timestamp = LocalDateTime.now();  // Automatically set to current time
        this.content = content;
        this.imagePath = imagePath;  // Assign the image path
    }

    private String generateContentId() {
        return UUID.randomUUID().toString();
    }

    // Getters and setters
    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public abstract boolean isExpired();
}
