/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreamTravel.controllers;

import dreamTravel.users.UsersDAO;
import dreamTravel.users.UsersDTO;
import dreamTravel.utilities.APIWrapper;
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
@WebServlet(name = "LoginFacebookServlet", urlPatterns = {"/LoginFacebookServlet"})
public class LoginFacebookServlet extends HttpServlet {
    static Logger LOGGER = Logger.getLogger(LoginFacebookServlet.class);
    private final String SEARCH_PAGE = "SearchTouServlet";
    private final String LOGIN_PAGE = "login.jsp";
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
        String code = request.getParameter("code");
        String error = request.getParameter("error");
        try {
            if (error == null) {
                APIWrapper wrapper = new APIWrapper();
                String accessToken = wrapper.getAccessToken(code);
                wrapper.setAccessToken(accessToken);
                UsersDTO user = wrapper.getUserDTOInfor();
                UsersDAO usersDAO = new UsersDAO();
                boolean checkInsert = usersDAO.checkLoginFacebook(user.getUserID());
                if (!checkInsert) {
                    usersDAO.insertUserFacebook(user.getUserID(), user.getName(), user.getFacebookLink(), user.getRole());
                }
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                url = SEARCH_PAGE;
            } else {
                url = LOGIN_PAGE;
                request.setAttribute("loginMsg", "Login with Facebook Unsuccess.");
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
