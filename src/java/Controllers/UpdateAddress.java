/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAL.AccountDAO;
import Models.Address;
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
public class UpdateAddress extends HttpServlet {
    AccountDAO adao;

    public void init() {
        adao = new AccountDAO();
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
            out.println("<title>Servlet UpdateAddress</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateAddress at " + request.getContextPath() + "</h1>");
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
        User u = (User) request.getSession().getAttribute("account");
        if (u != null) {
            
            String deleteId = request.getParameter("deleteId");
            if(deleteId != null){
                adao.deleteA(Integer.parseInt(deleteId));
            }
            
            ArrayList<Address> adds = adao.loadAddress(u.getId());
            request.setAttribute("adds", adds);
            request.getRequestDispatcher("Views/Member/Address.jsp").forward(request, response);
        } else{
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
        User u = (User) request.getSession().getAttribute("account");
        boolean update = false;
        if (u != null) {
            Address a = new Address();
            String addressId = request.getParameter("addressId");
            if(addressId != null){
                a.setId(Integer.parseInt(request.getParameter("addressId")));
                update = true;
            }
            a.setName(request.getParameter("name"));
            a.setAdd(request.getParameter("address"));
            a.setPhone(request.getParameter("phone"));
            a.setUserId(u.getId());

            // insert new address
            if(update){
                adao.updateA(a);
            }
            else {
                adao.insertA(a);
            }
//            try(PrintWriter out = response.getWriter()){
//                out.println(adao.stat);
//            }
            response.sendRedirect("address");

        } else {
            response.sendRedirect("Login");
        }
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
