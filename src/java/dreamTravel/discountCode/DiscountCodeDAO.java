/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreamTravel.discountCode;

import dreamTravel.utilities.DBUtilities;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author NhatBPM;
 */
public class DiscountCodeDAO implements Serializable{
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
    
    public boolean checkDiscountCode(String discountCode) throws SQLException, NamingException{
        try {
            con = DBUtilities.openConnection();
            if (con != null) {
                String sql = "SELECT DiscountId, ExpiryDate "
                            + "FROM DiscountCode "
                            + "WHERE DiscountId = ? ";
                pst = con.prepareStatement(sql);
                pst.setString(1, discountCode);
                rs = pst.executeQuery();
                if (rs.next()) {
                    Date dateNow = new Date(System.currentTimeMillis());
                    Date expiryDate = rs.getDate("ExpiryDate");
                    if (dateNow.before(expiryDate)) {
                        return true;
                    }
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }
    
    public int getPercentDiscountCode(String discountCode) throws SQLException, NamingException{
        try {
            con = DBUtilities.openConnection();
            if (con != null) {
                String sql = "SELECT DiscountId, PercentDiscount "
                            + "FROM DiscountCode "
                            + "WHERE DiscountId = ? ";
                pst = con.prepareStatement(sql);
                pst.setString(1, discountCode);
                rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getInt("PercentDiscount");
                }
            }
        } finally {
            closeConnection();
        }
        return 0;
    }
}
