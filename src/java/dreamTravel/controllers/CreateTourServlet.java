/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dreamTravel.controllers;

import dreamTravel.tour.TourCreateError;
import dreamTravel.tour.ToursDAO;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

/**
 *
 * @author NhatBPM;
 */
@WebServlet(name = "CreateTourServlet", urlPatterns = {"/CreateTourServlet"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
       maxFileSize = 1024 * 1024 * 10, // 10MB
       maxRequestSize = 1024 * 1024 * 50) // 50MB
public class CreateTourServlet extends HttpServlet {
    static Logger LOGGER = Logger.getLogger(SearchTouServlet.class);
    private final String CREATE_TOUR_PAGE = "createTour.jsp";
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
        String url = CREATE_TOUR_PAGE;
        String name = null;
        String place = null;
        String dateFromString = null;
        String dateToString = null;
        String priceString = null;
        String quotaString = null;
        String imageLink = null;
        try {
            if (!ServletFileUpload.isMultipartContent(request)) {
                //Khong thuc hien 
                return;
            }
            TourCreateError error = new TourCreateError();
            boolean validate = true;
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = null;
            try {
                items = upload.parseRequest(new ServletRequestContext(request));
            } catch (FileUploadException e) {
                LOGGER.fatal(e.getMessage());
            }
            Iterator iter = items.iterator();
            Hashtable params = new Hashtable();
            String fileName = null;
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (item.isFormField()) {
                    params.put(item.getFieldName(), item.getString());
                } else {
                    try {
                        String itemName = item.getName();
                        fileName = itemName.substring(itemName.lastIndexOf("\\") + 1);
                        String realPath = getServletContext().getRealPath("/") 
                                + "images\\" + fileName;
                        File saveFile = new File(realPath);
                        item.write(saveFile);
                        imageLink = "images\\" + fileName;
                    } catch (Exception e) {
                        error.setImageError("input image.");
                        validate = false;
                        LOGGER.fatal(e.getMessage());
                    }
                }
            }
            //name
            name = (String)params.get("txtName");
            error.setName(name);
            if (name.isEmpty()) {
                error.setNameError("Input Name Tour");
                validate = false;
            }
            //dateFrom
            dateFromString = (String)params.get("txtDateFrom");
            error.setDateFrom(dateFromString);
            Date dateFrom = null;
            try {
                dateFrom = Date.valueOf(dateFromString.trim());
                if (dateFrom.getTime() - System.currentTimeMillis() <= 0) {
                    error.setDateFromError("Input DateFrom must greater than the current date.");
                    validate = false;
                }
            } catch (Exception e) {
                error.setDateFromError("Input DateFrom.");
                validate = false;
            }
            //dateTO
            dateToString = (String)params.get("txtDateTo");
            error.setDateTo(dateToString);
            Date dateTo = null;
            try {
                dateTo = Date.valueOf(dateToString.trim());
                if (dateFrom.after(dateTo)) {
                    error.setDateToError("Input DateTo must greater than the DateFrom.");
                    validate = false;
                }
            } catch (Exception e) {
                error.setDateToError("Input DateTo.");
                validate = false;
            }
            //price
            priceString = (String)params.get("txtPrice");
            error.setPrice(priceString);
            double price = 0;
            try {
                price = Double.parseDouble(priceString);
                if (price <= 0) {
                    error.setPriceError("Price must greater than 0 .");
                    validate = false;
                }
            } catch (Exception e) {
                error.setPriceError("Input Price is double.");
                validate = false;
            }
            //qouta
            quotaString = (String) params.get("txtQuota");
            error.setQouta(quotaString);
            int quota = 0;
            try {
                quota = Integer.parseInt(quotaString.trim());
                if (quota <= 0) {
                    error.setQoutaError("Input qouta must greater than 0.");
                    validate = false;
                }
            } catch (Exception e) {
                error.setQoutaError("Input qouta is number.");
                validate = false;
            }
            //place
            place = (String)params.get("txtPlace");
            error.setPlace(place);
            if (place.isEmpty()) {
                error.setPlaceError("Input Place.");
            }
            ToursDAO toursDAO = new ToursDAO();
            if (validate) {
                boolean result = toursDAO.insertTour(name, dateFrom, dateTo, (float)price, quota, place, imageLink);
                if (result) {
                    request.setAttribute("createMsg", "Create Tour Success.");
                } 
            } else {
                request.setAttribute("errorTour", error);
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
