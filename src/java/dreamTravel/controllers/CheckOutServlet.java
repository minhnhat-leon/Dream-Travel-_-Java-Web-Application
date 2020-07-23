/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreamTravel.controllers;

import dreamTravel.booking.BookingDAO;
import dreamTravel.bookingDetail.BookingDetailDAO;
import dreamTravel.tour.TourSearch;
import dreamTravel.users.UsersDTO;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author NhatBPM;
 */
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {
    static Logger LOGGER = Logger.getLogger(CheckOutServlet.class);
    private final String CART_PAGE = "viewCart.jsp";
    private final String HOME_PAGE = "SearchTouServlet";
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
        String url = CART_PAGE;
        String totalString = request.getParameter("txtTotal");
        
        try {
            double total = Double.parseDouble(totalString);
            HttpSession session = request.getSession();
            HashMap<Integer, TourSearch> listTourCart = (HashMap<Integer, TourSearch>)session.getAttribute("listTourCart");
            if (listTourCart != null && !listTourCart.isEmpty()) {
                BookingDetailDAO bookingDetailDAO = new BookingDetailDAO();
                int amountBooked;
                int quotaRemaining;
                for (TourSearch tour : listTourCart.values()) {
                    amountBooked = bookingDetailDAO.getAmountBooking(tour.getTourId());
                    quotaRemaining = tour.getQuota() - amountBooked;
                    if (tour.getAmount() > quotaRemaining) {
                        request.setAttribute("tourIdUpdate", tour.getTourId());
                        request.setAttribute("errorAmountCart", quotaRemaining);
                        total = 0;
                        for (TourSearch tourInCart : listTourCart.values()) {
                            total = total + tourInCart.getAmount() * tourInCart.getPrice();
                        }
                        request.setAttribute("total", total);
                        url = CART_PAGE;
                        return;
                    }
                } //check so luong con
                //insert DATABASE
                UsersDTO user = (UsersDTO)session.getAttribute("user");
                BookingDAO bookingDAO = new BookingDAO();
                String discountCode = (String)session.getAttribute("discountCode");
                int bookingId = bookingDAO.insertBookingReturnBookingId((float)total, user.getUserID(), discountCode);
                if (bookingId > 0) {
                    for (TourSearch tour : listTourCart.values()) {
                        bookingDetailDAO.insertBookingDetails(tour.getTourId(), tour.getAmount(), bookingId);
                    }
                }
                url = HOME_PAGE;
                session.removeAttribute("listTourCart");
                session.removeAttribute("discountCode");
                session.removeAttribute("discountPercent");
                request.setAttribute("AddToCardMsg", "Check Out Succesfull.");
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
