/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreamTravel.users;

import java.io.Serializable;

/**
 *
 * @author NhatBPM;
 */
public class UsersDTO implements Serializable{
    private String userID;
    private String name;
    private String facebookLink;
    private String role;

    public UsersDTO(String userID, String name, String facebookLink, String role) {
        this.userID = userID;
        this.name = name;
        this.facebookLink = facebookLink;
        this.role = role;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFacebookLink() {
        return facebookLink;
    }

    public void setFacebookLink(String facebookLink) {
        this.facebookLink = facebookLink;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    
    
}
