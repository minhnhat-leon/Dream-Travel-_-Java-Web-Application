/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreamTravel.controllers;

import dreamTravel.bookingDetail.BookingDetailDAO;
import dreamTravel.tour.TourSearch;
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
@WebServlet(name = "UpdateAmountTourServlet", urlPatterns = {"/UpdateAmountTourServlet"})
public class UpdateAmountTourServlet extends HttpServlet {
    static Logger LOGGER = Logger.getLogger(UpdateAmountTourServlet.class);
    private final String CART_PAGE = "viewCart.jsp";
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
        String tourIdString = request.getParameter("tourId");
        String amountString = request.getParameter("txtAmount");
        try {
            int tourId = Integer.parseInt(tourIdString);
            int amount = Integer.parseInt(amountString);
            HttpSession session = request.getSession();
            HashMap<Integer, TourSearch> listTourCart = (HashMap<Integer, TourSearch>)session.getAttribute("listTourCart");
            if (listTourCart != null && !listTourCart.isEmpty()) {
                TourSearch tour = listTourCart.get(tourId);
                BookingDetailDAO bookingDetailDAO = new BookingDetailDAO();
                int amountBooked = bookingDetailDAO.getAmountBooking(tourId);
                int quotaRemaining = tour.getQuota() - amountBooked;
                if (amount <= quotaRemaining) {
                    tour.setAmount(amount);
                    request.setAttribute("tourIdUpdate", tourId);
                    request.setAttribute("updateSuccess", "Update Amount Successfull.");
                } else {
                    request.setAttribute("tourIdUpdate", tourId);
                    request.setAttribute("errorAmountCart", quotaRemaining);
                }
                double total = 0;
                for (TourSearch tourInCart : listTourCart.values()) {
                    total = total + tourInCart.getAmount()*tour.getPrice();
                }
                request.setAttribute("total", total);
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
