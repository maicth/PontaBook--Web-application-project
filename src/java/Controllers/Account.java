/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAL.AccountDAO;
import Models.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Date;

/**
 *
 * @author DELL
 */
public class Account extends HttpServlet {
    AccountDAO adao;
    public void init(){
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
            out.println("<title>Servlet Account</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Account at " + request.getContextPath() + "</h1>");
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
        
        adao.loadAccount();
        User u = (User) request.getSession().getAttribute("account");
        if(u != null){
            request.getRequestDispatcher("Views/Member/Account.jsp").forward(request, response);
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
        
        User u = (User) request.getSession().getAttribute("account");
        if (u != null){
            u.setName(request.getParameter("name"));
            u.setEmail(request.getParameter("email"));
            Date dob;
            String db = request.getParameter("dob");
            if(db != null && db.trim().length() > 0 ){
                dob = Date.valueOf(request.getParameter("dob"));
                u.setDob(dob);
            }

            String pass = u.getPassword();

            u.setGender(request.getParameter("gender").equals("1"));


            if (request.getParameter("pass").trim().length() > 0){
                String oldpass = request.getParameter("pass");
                String newpass = request.getParameter("newpass");

                if(!u.getPassword().equals(oldpass)){
                    request.setAttribute("mess", "Mật khẩu hiện tại không đúng");
                    doGet(request, response);
                }
                else{
                    if(u.getPassword().equals(newpass)){
                        request.setAttribute("mess", "Không được đặt mật khẩu mới trùng mật khẩu cũ!");
                        doGet(request, response);
                    }
                    else {
                        pass = newpass;
                    }
                }
            }
            u.setPassword(pass);
            
            // doUpdate
            adao.update(u);
//            try(PrintWriter out = response.getWriter()){
//                out.println(adao.stat);
//            }
         
            request.setAttribute("mess", "Lưu thông tin thành công!");
            doGet(request, response);
        }      
        else {
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
