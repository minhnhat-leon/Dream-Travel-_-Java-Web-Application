/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreamTravel.tour;

import dreamTravel.utilities.DBUtilities;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javax.naming.NamingException;

/**
 *
 * @author NhatBPM;
 */
public class ToursDAO implements Serializable{
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
    
    public HashMap<Integer, TourSearch> getListTourSearch(String address, Date dateFromSearch, Date dateToSearch, float priceFrom, float priceTo, int indexPage, int sizePage) 
            throws NamingException, SQLException{
        try {
            con = DBUtilities.openConnection();
            if (con != null) {
                String sql = "SELECT RowNum, TourID, TourName, FromDate, ToDate, Price, Place, Quota, ImageLink "
                            + "FROM (SELECT ROW_NUMBER() OVER (ORDER BY TourID) AS RowNum, TourID, TourName, FromDate, ToDate, Price, Place, Quota, ImageLink "
                                    + "FROM Tours "
                                    + "WHERE StatusId = 'activeTour' AND Place LIKE ? ";
                boolean dateInput = false;
                if (dateFromSearch != null && dateToSearch != null) {
                    sql = sql + "AND ( FromDate >= ? AND ToDate <= ? ) ";
                    dateInput = true;
                }
                boolean priceInput = false;
                if (priceFrom >= 0 && priceTo > 0) {
                    sql = sql + "AND ( Price BETWEEN ? AND ? ) ";
                    priceInput = true;
                }
                sql = sql + ") AS TourSearch " + "WHERE TourSearch.RowNum BETWEEN ? AND ? ";
                pst = con.prepareStatement(sql);
                pst.setString(1, "%" + address + "%");
                if (dateInput) {
                    pst.setDate(2, dateFromSearch);
                    pst.setDate(3, dateToSearch);
                    if (priceInput) {
                        pst.setFloat(4, priceFrom);
                        pst.setFloat(5, priceTo);
                        pst.setInt(6, sizePage*(indexPage - 1));
                        pst.setInt(7, sizePage*indexPage + 1);
                    } else {
                        pst.setInt(4, sizePage*(indexPage - 1));
                        pst.setInt(5, sizePage*indexPage + 1);
                    }
                } else {
                    if (priceInput) {
                        pst.setFloat(2, priceFrom);
                        pst.setFloat(3, priceTo);
                        pst.setInt(4, sizePage*(indexPage - 1));
                        pst.setInt(5, sizePage*indexPage + 1);
                    } else {
                        pst.setInt(2, sizePage*(indexPage - 1));
                        pst.setInt(3, sizePage*indexPage + 1);
                    }
                }
                rs = pst.executeQuery();
                HashMap<Integer, TourSearch> listTourSearch = new HashMap<>();
                TourSearch tourSearch;
                while (rs.next()) {
                    int tourId = rs.getInt("TourID");
                    String name = rs.getString("TourName");
                    Date dateFrom = rs.getDate("FromDate");
                    Date dateTo = rs.getDate("ToDate");
                    int price = rs.getInt("Price");
                    String place = rs.getString("Place");
                    int quota = rs.getInt("Quota");
                    String imageLink = rs.getString("ImageLink");
                    tourSearch = new TourSearch(tourId, name, dateFrom, dateTo, price, place, quota, imageLink);
                    listTourSearch.put(tourId, tourSearch);
                }
                return listTourSearch;
            }
        } finally {
            closeConnection();
        }
        return null;
    }
    
    public int getLastPageTourSearch(String address, Date dateFromSearch, Date dateToSearch, float priceFrom, float priceTo,  int sizePage) 
            throws NamingException, SQLException{
        try {
            con = DBUtilities.openConnection();
            if (con != null) {
                String sql = "SELECT COUNT(*) AS countRow "
                            + "FROM Tours "
                            + "WHERE StatusId = 'activeTour' AND Place LIKE ? ";
                boolean dateInput = false;
                if (dateFromSearch != null && dateToSearch != null) {
                    sql = sql + "AND ( FromDate >= ? AND ToDate <= ? ) ";
                    dateInput = true;
                }
                boolean priceInput = false;
                if (priceFrom >= 0 && priceTo > 0) {
                    sql = sql + "AND ( Price BETWEEN ? AND ? ) ";
                    priceInput = true;
                }
                pst = con.prepareStatement(sql);
                pst.setString(1, "%" + address + "%");
                if (dateInput) {
                    pst.setDate(2, dateFromSearch);
                    pst.setDate(3, dateToSearch);
                    if (priceInput) {
                        pst.setFloat(4, priceFrom);
                        pst.setFloat(5, priceTo);
                    } 
                } else {
                    if (priceInput) {
                        pst.setFloat(2, priceFrom);
                        pst.setFloat(3, priceTo);
                    }
                }
                rs = pst.executeQuery();
                if (rs.next()) {
                    int row = rs.getInt("countRow");
                    return (int)Math.ceil((double)row / sizePage);
                }
            }
        } finally {
            closeConnection();
        }
        return 1;
    }
    
    public TourSearch getTour(int tourId) throws NamingException, SQLException{
        try {
            con = DBUtilities.openConnection();
            if (con != null) {
                String sql = "SELECT TourID, TourName, FromDate, ToDate, Price, Place, Quota, ImageLink "
                            + "FROM Tours "
                            + "WHERE TourID = ? ";
                pst = con.prepareStatement(sql);
                pst.setInt(1, tourId);
                rs = pst.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("TourName");
                    Date dateFrom = rs.getDate("FromDate");
                    Date dateTo = rs.getDate("ToDate");
                    int price = rs.getInt("Price");
                    String place = rs.getString("Place");
                    int quota = rs.getInt("Quota");
                    String imageLink = rs.getString("ImageLink");
                    TourSearch tourSearch = new TourSearch(tourId, name, dateFrom, dateTo, price, place, quota, imageLink);
                    return tourSearch;
                }
            }
        } finally {
            closeConnection();
        }
        return null;
    }
    
    public boolean insertTour(String name, Date dateFrom, Date dateTo, float price, int quota, String place, String imageLink) throws NamingException, SQLException{
        try {
            con = DBUtilities.openConnection();
            if (con != null) {
                String sql = "INSERT INTO  Tours(TourName, FromDate, ToDate, DateImport, Price, Place, Quota, ImageLink, StatusId) "
                            + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
                pst = con.prepareStatement(sql);
                pst.setString(1, name);
                pst.setDate(2, dateFrom);
                pst.setDate(3, dateTo);
                pst.setDate(4, new Date(System.currentTimeMillis()));
                pst.setFloat(5, price);
                pst.setString(6, place);
                pst.setInt(7, quota);
                pst.setString(8, imageLink);
                pst.setString(9, "activeTour");
                int row = pst.executeUpdate();
                if (row > 0 ) {
                    return true;
                }
            }
        } finally {
            closeConnection();
        }
        return false;
    }
    
}
