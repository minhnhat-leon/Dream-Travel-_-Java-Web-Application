/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreamTravel.controllers;

import dreamTravel.bookingDetail.BookingDetailDAO;
import dreamTravel.tour.ToursDAO;
import dreamTravel.tour.TourSearch;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author NhatBPM;
 */
@WebServlet(name = "SearchTouServlet", urlPatterns = {"/SearchTouServlet"})
public class SearchTouServlet extends HttpServlet {
    static Logger LOGGER = Logger.getLogger(SearchTouServlet.class);
    private final String HOME_PAGE = "home.jsp";
    private final int SIZE_PAGE = 20;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = HOME_PAGE;
        String address = request.getParameter("txtAddress");
        String dateFromString = request.getParameter("txtDateFrom");
        String dateToString = request.getParameter("txtDateTo");
        String priceFromString = request.getParameter("txtPriceFrom");
        String priceToString = request.getParameter("txtPriceTo");
        String indexPageString = request.getParameter("indexPage");
        int indexPage;
        if (indexPageString == null || indexPageString.isEmpty()) {
            indexPage = 1;
        } else {
            indexPage = Integer.parseInt(indexPageString);
        }
        if (address == null) {
            address = "";
            dateFromString = "";
            dateToString = "";
            priceFromString = "";
            priceToString = "";
        }
        try {
            Date dateFrom;
            Date dateTo;
            float priceFrom;
            float priceTo;
            boolean validate = true;
            if (!dateFromString.isEmpty() && !dateToString.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                dateFrom = new Date(sdf.parse(dateFromString).getTime());
                dateTo = new Date(sdf.parse(dateToString).getTime());
                Date dateNow = new Date(System.currentTimeMillis() - 24*60*60*1000);
                if (!dateFrom.after(dateNow)) {
                    validate = false;
                }
                if (!dateTo.after(dateFrom)) {
                    validate = false;
                }
            } else {
                dateFrom = null;
                dateTo = null;
            }
            if (!priceFromString.isEmpty() && !priceToString.isEmpty()) {
                priceFrom = Float.parseFloat(priceFromString);
                priceTo = Float.parseFloat(priceToString);
                if (priceFrom >= priceTo || priceFrom < 0) {
                    validate = false;
                }
            } else {
                priceFrom = 0;
                priceTo = 0;
            }
            if (validate) {
                ToursDAO tourDAO = new ToursDAO();
                HashMap<Integer, TourSearch> listTourSearch = tourDAO.getListTourSearch(address, dateFrom, dateTo, priceFrom, priceTo, indexPage, SIZE_PAGE);
                BookingDetailDAO bookingDetailDAO = new BookingDetailDAO();
                for (Map.Entry<Integer, TourSearch> entry : listTourSearch.entrySet()) {
                    int amount = bookingDetailDAO.getAmountBooking(entry.getKey());
                    entry.getValue().setQuotaRemaining(entry.getValue().getQuota() - amount);
                }
                int lastPage = tourDAO.getLastPageTourSearch(address, dateFrom, dateTo, priceFrom, priceTo, SIZE_PAGE);
                request.setAttribute("lastPage", lastPage);
                request.setAttribute("listTourSearch", listTourSearch);
            }
        } catch (Exception e) {
            LOGGER.fatal(e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
