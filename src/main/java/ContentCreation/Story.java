package ContentCreation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class Story extends Content {

    private static final int EXPIRATION_HOURS = 24; // Stories expire after 24 hours

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS")  // ISO 8601 format for timestamp
    private LocalDateTime timestamp;

    // Default constructor for Jackson deserialization
    @JsonCreator
    public Story(@JsonProperty("content") String content,
                 @JsonProperty("imagePath") String imagePath,
                 @JsonProperty("timestamp") LocalDateTime timestamp,
                 @JsonProperty("authorId") String authorId,
                 @JsonProperty("expired") boolean expired,
                 @JsonProperty("contentId") String contentId) {
        super(content, imagePath);  // Calls the parent constructor
        this.timestamp = timestamp;
        this.authorId = authorId;
        this.contentId = contentId;
    }

    // Constructor for creating a new story
    public Story(String content, String imagePath) {
        super(content, imagePath);
        this.timestamp = LocalDateTime.now();  // Automatically set to current time
    }

    @Override
    public boolean isExpired() {
        // Stories expire after 24 hours
        LocalDateTime expirationTime = timestamp.plusHours(EXPIRATION_HOURS);
        return LocalDateTime.now().isAfter(expirationTime);
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
