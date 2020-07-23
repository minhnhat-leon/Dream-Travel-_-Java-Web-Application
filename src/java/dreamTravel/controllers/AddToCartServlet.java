/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreamTravel.controllers;

import dreamTravel.tour.ToursDAO;
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
@WebServlet(name = "AddToCartServlet", urlPatterns = {"/AddToCartServlet"})
public class AddToCartServlet extends HttpServlet {
    static Logger LOGGER = Logger.getLogger(AddToCartServlet.class);
    private final String SEARCH_PAGE = "SearchTouServlet";
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
        String url = SEARCH_PAGE;
        String tourIdString = request.getParameter("tourId");
        int tourId = Integer.parseInt(tourIdString);
        try {
            HttpSession session = request.getSession();
            HashMap<Integer, TourSearch> listTourCart = (HashMap<Integer, TourSearch>)session.getAttribute("listTourCart");
            if (listTourCart == null) {
                listTourCart = new HashMap<>();
                ToursDAO toursDAO = new ToursDAO();
                TourSearch tour = toursDAO.getTour(tourId);
                tour.setAmount(1);
                listTourCart.put(tourId, tour);
            } else {
                if (listTourCart.containsKey(tourId)) {
                    listTourCart.get(tourId).setAmount(listTourCart.get(tourId).getAmount() + 1);
                } else {
                    ToursDAO toursDAO = new ToursDAO();
                    TourSearch tour = toursDAO.getTour(tourId);
                    tour.setAmount(1);
                    listTourCart.put(tourId, tour);
                }
            }
            session.setAttribute("listTourCart", listTourCart);
            request.setAttribute("AddToCardMsg", "Add to Your Cart Success.");
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
