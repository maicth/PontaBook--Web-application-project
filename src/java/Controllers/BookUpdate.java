/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import DAL.*;
import Models.Book;
import Models.Category;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 *
 * @author DELL
 */
@MultipartConfig
public class BookUpdate extends HttpServlet {
    
    
    BookDAO bdao;
    PublisherDAO pdao;
    CategoryDAO cdao;
    public void init(){
        bdao = new BookDAO();
        pdao = new PublisherDAO();
        cdao = new CategoryDAO();
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
        bdao.loadBook();
        pdao.loadPublisher();
        cdao.loadBookCate();
        cdao.loadCategory();
        String type = request.getParameter("type");
        String id = request.getParameter("deleteId");
        
        if(type != null && type.equals("0")){
            if(id != null && id.trim().length() != 0){
                int deleteId = Integer.parseInt(id);
                bdao.delete(deleteId);
//                try(PrintWriter out = response.getWriter()){
//                    out.println(bdao.stat);
//                }
            }
        }
        
        if(type != null && type.equals("1")){
            String id2 = request.getParameter("updateId");
            if(id2 != null && id2.trim().length() != 0){
                Book b = bdao.search(Integer.parseInt(id2));
                request.setAttribute("bookUpdate", b);
                request.setAttribute("CDAO", cdao);
                request.setAttribute("bCate", cdao.getCateByBook(Integer.parseInt(id2)));
            }
        }
        request.getRequestDispatcher("book-list").forward(request, response);
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
        OutputStream ops = null;
            /* TODO output your page here. You may use following sample code. */        
        String oldImage = request.getParameter("oldImage");
        String id = request.getParameter("id");
        
        
        String image = oldImage;

        Part part = request.getPart("newImage");

        String fileName = part.getSubmittedFileName();

        if(fileName.trim().length() != 0){
            ServletContext servletContext = getServletContext();
            String path = servletContext.getRealPath("/images");
            InputStream is = part.getInputStream();

            ops = new FileOutputStream(new File(path + File.separator + fileName));

            int read = 0;
            final byte[] bytes = new byte[1024];

            while ((read = is.read(bytes)) != -1) {
                ops.write(bytes, 0, read);
            }     
            image = "images/"+fileName;
        }  
        
        doUpdate(request, image);
//        try(PrintWriter out = response.getWriter()){
//            out.println(bdao.stat);
//        }
        response.sendRedirect("book-list");
    }

    private void doUpdate(HttpServletRequest request, String img){
        
        Book b = new Book();
        b.setId(Integer.parseInt(request.getParameter("id")));
        b.setTitle(request.getParameter("title"));
        b.setAuthorId(Integer.parseInt(request.getParameter("authorId")));
        b.setPublishDate(java.sql.Date.valueOf(request.getParameter("publishDate")));
        b.setImage(img+"");
        b.setPrice(Integer.parseInt(request.getParameter("price")));
        b.setPublisherId(Integer.parseInt(request.getParameter("publisherId")));
        b.setDescription(request.getParameter("description")+"");
        b.setDiscount(Float.parseFloat(request.getParameter("discount"))/100);
        b.setStatus(request.getParameter("status").equals("1"));
        b.setSize(request.getParameter("size")+"");
        b.setQuantity(Integer.parseInt(request.getParameter("quantity")));

        String[] cids = request.getParameterValues("category");
        if (cids != null) {
            cdao.loadCategory();
            for (String cid : cids) {
                Category c = cdao.getCategoryById(Integer.parseInt(cid));
                b.getCates().add(c);
            }
        }
        bdao.update(b);
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
