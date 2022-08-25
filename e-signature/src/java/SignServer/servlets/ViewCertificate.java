/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SignServer.servlets;

import SignServer.Certificate;

import static SignServer.MyCertUtil.exportCert;
import static SignServer.MyCertUtil.getAlias;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Akshay
 */
public class ViewCertificate extends HttpServlet {

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
        try{
            
            File f = new File("C:/Akshay/My Work/final year/DigitalSign/keystore/user/"+getAlias()+".jks");
            if(f.exists()){
            //============   export a certificate
            System.out.println("calling export fn ");
            exportCert();
            System.out.println("end of export()");
            
            
            //============  display the certificate
            Certificate c = new Certificate();
            c.viewCert();
            response.sendRedirect("ViewCertificate.jsp");
           /* System.out.println("done");
            Certificate c = new Certificate();
            c.viewCert();
            response.sendRedirect("Work.jsp");*/
            }
            else{
                
                PrintWriter out = response.getWriter();  
                response.setContentType("text/html");  
                out.println("<script type=\"text/javascript\">");  
                out.println("alert('Certificate does not exist....  Apply for Soft Token');");  
                out.println("window.open('ViewCertificate.jsp','_self');");
                out.println("</script>");
                //response.sendRedirect("viewCertFail.jsp");
                //response.sendRedirect("ViewCertificate.jsp");
            }
            
        }
        catch(Exception e){
            System.out.println(e);
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
