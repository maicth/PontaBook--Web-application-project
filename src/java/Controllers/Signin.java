/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAL.*;
import Models.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author DELL
 */
public class Signin extends HttpServlet {

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
        request.getRequestDispatcher("Views/Signin.jsp").forward(request, response);
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
        init();
        String message;
        User u = new User();
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String pass = request.getParameter("pass");
        String repass = request.getParameter("re-pass");
        
        u.setEmail(email);
        u.setUsername(username);
        u.setPassword(pass);
      
        boolean check = true;
        if (pass.equals(repass)){
            //insert info          
            adao.loadAccount();
            for (User acc : adao.accounts) {
                if (acc.getUsername().equals(u.getUsername())) {
                    check = false;
                }
            }
            
            if(check){
                adao.insert(u);
                response.sendRedirect("Home");
            } else {
                request.setAttribute("msg", "Tên đăng nhập đã tồn tại");
                doGet(request, response);
            }           
        }
        else {
            doGet(request, response);
        }
    }
    
    AccountDAO adao;
    public void init(){
       adao = new AccountDAO();
    }
    
    
    private void doInsert(User u){
        adao.loadAccount();
        for (User acc : adao.accounts) {
            if(acc.getUsername().equals(u.getUsername())){
                
            }
        }
        
        adao.insert(u);
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
