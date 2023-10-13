/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAL.AccountDAO;
import DAL.CartDAO;
import DAL.OrderDAO;
import Models.Address;
import Models.Item;
import Models.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class Checkout extends HttpServlet {
    AccountDAO adao;
    OrderDAO odao;
    CartDAO ctdao;
    
    public void init(){
        adao = new AccountDAO();
        odao = new OrderDAO();
        ctdao = new CartDAO();
    }
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
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Checkout</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Checkout at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
//        processRequest(request, response);
        init();
        adao.loadAccount();
        
        User u = (User) request.getSession().getAttribute("account");
        if(u != null){
            ArrayList<Address> adds = adao.loadAddress(u.getId());
            request.setAttribute("adds", adds);
            request.setAttribute("cart", ctdao.getItems(u.getId()));
            request.getRequestDispatcher("Views/Member/Checkout.jsp").forward(request, response);
        }
        else {
            response.sendRedirect("Login");
        }
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
//        processRequest(request, response);
        // address
        int addressId = Integer.parseInt(request.getParameter("buy-address"));
        User u = (User) request.getSession().getAttribute("account");
        if(u != null){
            ArrayList<Item> items = ctdao.getItems(u.getId());
            odao.insert(u.getId(), items, addressId);
            response.sendRedirect("orders");
        }
        else {
            response.sendRedirect("Login");
        }
        // user 
        // cart
        
        // insert to db
        // insert order
        // insert orderDetail
        // delete cart
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
