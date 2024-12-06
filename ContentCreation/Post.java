package ContentCreation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class Post extends Content {

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS")  // ISO 8601 format for timestamp
    private LocalDateTime timestamp;

    // Default constructor for Jackson deserialization
    @JsonCreator
    public Post(@JsonProperty("content") String content,
                @JsonProperty("imagePath") String imagePath,
                @JsonProperty("timestamp") LocalDateTime timestamp,
                @JsonProperty("authorId") String authorId,
                @JsonProperty("expired") boolean expired) {
        super(content, imagePath);  // Calls the parent constructor
        this.timestamp = timestamp;
        this.authorId = authorId;
        
    }

    // Constructor for creating a new post
    public Post(String content, String imagePath) {
        super(content, imagePath);
        this.timestamp = LocalDateTime.now();  // Automatically set to current time
    }

    @Override
    public boolean isExpired() {
        return false;  // Post expiration logic
    }

    // Getter for timestamp
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // Setter for timestamp
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
