/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreamTravel.bookingDetail;

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
public class BookingDetailDAO implements Serializable{
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
    
    public int getAmountBooking(int tourId) throws NamingException, SQLException{
        try {
            con = DBUtilities.openConnection();
            if (con != null) {
                String sql = "SELECT TourID, SUM(Quantity) AS Amount "
                            + "FROM BookingDetail "
                            + "WHERE TourID = ? "
                            + "GROUP BY TourID ";
                pst = con.prepareStatement(sql);
                pst.setInt(1, tourId);
                rs = pst.executeQuery();
                if (rs.next()) {
                    return rs.getInt("Amount");
                }
            }
        } finally {
            closeConnection();
        }
        return 0;
    }
    
        public boolean insertBookingDetails(int tourId, int quantity, int bookingId) throws NamingException, SQLException{
        try {
            con = DBUtilities.openConnection();
            if (con != null) {
                String sql = "INSERT INTO BookingDetail(TourID, Quantity, BookingId) "
                            + "VALUES(?, ?, ?) ";
                pst = con.prepareStatement(sql);
                pst.setInt(1, tourId);
                pst.setInt(2, quantity);
                pst.setInt(3, bookingId);
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
