/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreamTravel.userHaveDiscount;

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
public class UserHaveDiscountDAO implements Serializable{
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
    
    public boolean checkUseDiscountCode(String discountCode, String userId) throws SQLException, NamingException{
        try {
            con = DBUtilities.openConnection();
            if (con != null) {
                String sql = "SELECT UserID, DiscountId "
                            + "FROM UserHaveDiscount "
                            + "WHERE DiscountId = ? AND UserID = ?";
                pst = con.prepareStatement(sql);
                pst.setString(1, discountCode);
                pst.setString(2, userId);
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
}
