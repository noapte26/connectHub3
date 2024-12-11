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
    private ArrayList<UserAccount> admins;
    private ArrayList<UserAccount> member;
    private ArrayList<Post> content;
    private ArrayList<Post> requestPosts;
    private ArrayList<UserAccount> requests;
    private String photo;
    private String discription;
    private String name;
    private String groupId;

    public Group(UserAccount owner, String name) {
        this.owner = owner;
        this.name = name;
        admins = new ArrayList<>();
        member = new ArrayList<>();
        content = new ArrayList<>();
        requestPosts = new ArrayList<>();
        requests = new ArrayList<>();
        photo = Group.class.getResource("/group.jpg").toString().replace("file:", "");
        groupId = generategroupId();

    }

    private String generategroupId() {
        return UUID.randomUUID().toString();
    }

    public void setRequestPosts(ArrayList<Post> requestPosts) {
        this.requestPosts = requestPosts;
    }

    public ArrayList<Post> getRequestPosts() {
        return requestPosts;
    }

    public void setRequests(ArrayList<UserAccount> requests) {
        this.requests = requests;
    }

    public ArrayList<UserAccount> getRequests() {
        return requests;
    }

    public String getGroupId() {
        return groupId;
    }

    public UserAccount getOwner() {
        return owner;
    }

    public ArrayList<UserAccount> getAdmins() {
        return admins;
    }

    public ArrayList<UserAccount> getMember() {
        return member;
    }

    public ArrayList<Post> getContent() {
        return content;
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

    public void setAdmins(ArrayList<UserAccount> admins) {
        this.admins = admins;
    }

    public void setMember(ArrayList<UserAccount> member) {
        this.member = member;
    }

    public void setContent(ArrayList<Post> content) {
        this.content = content;
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
