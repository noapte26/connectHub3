
package groupDataBase;

import GroupManagementBackEnd.Group;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class groupSave {

    public static final String JSONFILE = "groups.json";

    public groupSave(Group account)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(JSONFILE, true))) {
            String profileJson = objectMapper.writeValueAsString(account);
            writer.write(profileJson);
            writer.newLine(); // Add a newline after each JSON object
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
