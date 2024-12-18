/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GroupManagementBackEnd;

import Account.*;
import ContentCreation.Post;
import java.util.*;

/**
 *
 * @author CONNECT
 */
public class Group {

    private UserAccount owner;
    private String photo;
    private String discription;
    private String name;
    private String groupId;

    public Group() {
    }

    public Group(UserAccount owner, String name) {
        this.owner = owner;
        this.name = name;
        photo = Group.class.getResource("/group.jpg").toString().replace("file:", "");
        groupId = generategroupId();

    }

    private String generategroupId() {
        return UUID.randomUUID().toString();
    }



    public String getGroupId() {
        return groupId;
    }

    public UserAccount getOwner() {
        return owner;
    }

    public String getPhoto() {
        return photo;
    }

    public String getDiscription() {
        return discription;
    }

    public String getName() {
        return name;
    }

    public void setOwner(UserAccount owner) {
        this.owner = owner;
    }


    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public void setName(String name) {
        this.name = name;
    }

}
