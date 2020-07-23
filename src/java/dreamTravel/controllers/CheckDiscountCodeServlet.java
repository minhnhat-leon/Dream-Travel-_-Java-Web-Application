/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreamTravel.controllers;

import dreamTravel.discountCode.DiscountCodeDAO;
import dreamTravel.userHaveDiscount.UserHaveDiscountDAO;
import dreamTravel.users.UsersDTO;
import java.io.IOException;
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
@WebServlet(name = "CheckDiscountCodeServlet", urlPatterns = {"/CheckDiscountCodeServlet"})
public class CheckDiscountCodeServlet extends HttpServlet {
    static Logger LOGGER = Logger.getLogger(CheckDiscountCodeServlet.class);
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
        String totalString = request.getParameter("txtTotal");
        String discountCode = request.getParameter("txtDiscountCode");
        try {
            double total = Double.parseDouble(totalString);
            request.setAttribute("total", total);
            HttpSession session = request.getSession();
            session.removeAttribute("discountPercent");
            session.removeAttribute("discountCode");
            UsersDTO user = (UsersDTO)session.getAttribute("user");
            DiscountCodeDAO discountCodeDAO = new DiscountCodeDAO();
            UserHaveDiscountDAO haveDiscountDAO = new UserHaveDiscountDAO();
            if (discountCodeDAO.checkDiscountCode(discountCode)) {
                if (!haveDiscountDAO.checkUseDiscountCode(discountCode, user.getUserID())) {
                    int discountPercent = discountCodeDAO.getPercentDiscountCode(discountCode);
                    session.setAttribute("discountPercent", discountPercent);
                    session.setAttribute("discountCode", discountCode);
                } else {
                    request.setAttribute("errorDiscout", "Code already in use.");
                }
            } else {
                request.setAttribute("errorDiscout", "Code does not exist or expires.");
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
