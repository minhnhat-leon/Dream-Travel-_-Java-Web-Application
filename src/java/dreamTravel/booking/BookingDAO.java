/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreamTravel.booking;

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
public class BookingDAO implements Serializable{
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
    
    public int insertBookingReturnBookingId(float total, String userId, String discountId) throws NamingException, SQLException{
        try {
            con = DBUtilities.openConnection();
            if (con != null) {
                String sql = "INSERT INTO Booking(BookingDate, TotalMoney, StatusId, UserID, DiscountId) "
                            + "OUTPUT inserted.BookingId "
                            + "VALUES(?, ?, ?, ?, ?) ";
                pst = con.prepareStatement(sql);
                pst.setDate(1, new Date(System.currentTimeMillis()));
                pst.setFloat(2, total);
                pst.setString(3, "checkOutBooking");
                pst.setString(4, userId);
                pst.setString(5, discountId);
                rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getInt("BookingId");
                }
            }
        } finally {
            closeConnection();
        }
        return 0;
    }
}
