/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UserAccountManagementBackend;

/**
 *
 * @author hebai
 */
import java.time.LocalDate;
import com.fasterxml.jackson.databind.ObjectMapper;
public class User {
    private String userId;
    private String email;
    private String userName;
    private String password;
    private LocalDate birthDate;
    private String status;
    public User(){}
    public User(String userId, String email, String userName, String password, LocalDate birthDate, String status) {
        this.userId = userId;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.birthDate = birthDate;
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getStatus() {
        return status;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
