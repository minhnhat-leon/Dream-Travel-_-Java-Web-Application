/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreamTravel.users;

import dreamTravel.utilities.DBUtilities;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author NhatBPM;
 */
public class UsersDAO implements Serializable{
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    
    private void closeConnection() throws SQLException{
        if (rs != null) {
            rs.close();
        }
        if (pst != null) {
            pst.close();
        }
        if (con != null) {
            con.close();
        }
    }
    
    public boolean checkLogin(String userId, String password) throws SQLException, NamingException{
        try {
            con = DBUtilities.openConnection();
            if (con != null) {
                String sql = "SELECT UserID, Password, StatusId "
                            + "FROM Users "
                            + "WHERE UserID = ? AND Password = ? AND StatusID = 'activeAccount' ";
                pst = con.prepareStatement(sql);
                pst.setString(1, userId);
                pst.setString(2, password);
                rs = pst.executeQuery();
                if (rs.next()) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }
    
    public UsersDTO getUser(String userId) throws NamingException, SQLException{
        try {
            con = DBUtilities.openConnection();
            if (con != null) {
                String sql = "SELECT UserID, Name, FacebookLink, RoleId "
                            + "FROM Users "
                            + "WHERE UserID = ? ";
                pst = con.prepareStatement(sql);
                pst.setString(1, userId);
                rs = pst.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("Name");
                    String facebookLink = rs.getString("FacebookLink");
                    String role = rs.getString("RoleId");
                    UsersDTO usersDTO = new UsersDTO(userId, name, facebookLink, role);
                    return usersDTO;
                }
            }
        } finally {
            closeConnection();
        }
        return null;
    }
    
    public boolean checkLoginFacebook(String userId) throws NamingException, SQLException{
        try {
            con = DBUtilities.openConnection();
            if (con != null) {
                String sql = "SELECT UserID, StatusID "
                            + "FROM Users "
                            + "WHERE UserID = ? AND StatusID = 'activeAccount' ";
                pst = con.prepareStatement(sql);
                pst.setString(1, userId);
                rs = pst.executeQuery();
                if (rs.next()) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }
    
    public boolean insertUserFacebook(String userId, String name, String facebookLink, String role) throws NamingException, SQLException{
        try {
            con = DBUtilities.openConnection();
            if (con != null) {
                String sql = "INSERT Users(UserID, Password, Name, FacebookLink, RoleId, StatusID) "
                            + "VALUES(?, ?, ?, ?, ?, ?)";
                pst = con.prepareStatement(sql);
                pst.setString(1, userId);
                pst.setString(2, null);
                pst.setString(3, name);
                pst.setString(4, facebookLink);
                pst.setString(5, role);
                pst.setString(6, "activeAccount");
                int row = pst.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }
    
}
