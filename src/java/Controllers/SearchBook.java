/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAL.*;
import Models.Book;
import Models.ControlPaging;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class SearchBook extends HttpServlet {
    CategoryDAO cdao;
    BookDAO bdao;
    AuthorDAO adao;
    PublisherDAO pdao;
    ArrayList<Book> booklist = null;  
    
    public void init(){
        cdao = new CategoryDAO();
        bdao = new BookDAO();
        adao = new AuthorDAO();
        pdao = new PublisherDAO();
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
        cdao.loadCategory();
        bdao.loadBook();
        cdao.loadBookCate();

        String type = request.getParameter("type");
        String query = request.getParameter("q");
        if (type != null && type.equals("0")) {
            String cid = request.getParameter("category");
            if (cid.trim().length() != 0) {
                booklist = searchByCate(request);
            }
        }

        if (type != null && type.equals("1")) {
            String authId = request.getParameter("authId");
            adao.loadAuthor();
            int aid = Integer.parseInt(authId);
            bdao.searchByAuthor(aid);
            booklist = bdao.booksByAuthor;
            request.setAttribute("search", adao.authorHm.get(aid).getName());
        }
        if (type != null && type.equals("2")) {
            String pid = request.getParameter("pub");
            pdao.loadPublisher();
            int pubid = Integer.parseInt(pid);
            bdao.searchByPub(pubid);
            booklist = bdao.booksByPub;
            request.setAttribute("search", pdao.publisherHm.get(pubid).getName());
        }

        if (type == null && query != null) {
            bdao.searchByName(query.trim());
            booklist = bdao.booksByName;
            request.setAttribute("search", "Kết quả tìm kiếm");
        }
        
        if(!booklist.isEmpty()){ 
            String sort = request.getParameter("sort");
            if(sort != null){
                if(sort.equals("asc")){
                    request.setAttribute("blist", bdao.sortByPriceAsc(booklist));
                }
                else if(sort.equals("desc")){
                    request.setAttribute("blist", bdao.sortByPriceDesc(booklist));
                }
            }
            else {
                request.setAttribute("blist", booklist);
            }
                
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
        init();
        processRequest(request, response);
        
        int nrpp = 5;
        int cp = 0;      
        
        int size = booklist.size();
        ControlPaging controlPage = new ControlPaging(nrpp, cp, size);
        controlPage.calc();
        request.setAttribute("CP", controlPage);
        request.getRequestDispatcher("Views/SearchBook.jsp").forward(request, response);

    }

    private ArrayList<Book> searchByCate(HttpServletRequest request){
        ArrayList<Book> booklist = new ArrayList<>();
        String id = request.getParameter("category");
        if(id.trim().length() != 0){
            int categoryId = Integer.parseInt(id);
            String cname = cdao.getCategoryById(categoryId).getName();
            request.setAttribute("search", cname);
            System.out.println(categoryId);
            ArrayList<Integer> bookIds = cdao.getBookByCate(categoryId);
            
            
            for (Integer bookId : bookIds) {
                booklist.add(bdao.search(bookId));
            }                    
        }
        
        return booklist;
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


        int size = booklist.size();
        int nrpp = 5;

        int cp = Integer.parseInt(request.getParameter("cp"));
        int np = Integer.parseInt(request.getParameter("np"));

        if (request.getParameter("home") != null) {
            cp = 0;
        }

        if (request.getParameter("end") != null) {
            cp = np - 1;
        }

        if (request.getParameter("pre") != null) {
            cp = cp - 1;
        }

        if (request.getParameter("next") != null) {
            cp = cp + 1;
        }

        for (int i = 0; i < np; i++) {
            if (request.getParameter("btn" + i) != null) {
                cp = i;
            }
        }

        ControlPaging controlPage = new ControlPaging(nrpp, cp, size);
        controlPage.calc();

        request.setAttribute("CP", controlPage);
        request.getRequestDispatcher("Views/SearchBook.jsp").forward(request, response);

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
