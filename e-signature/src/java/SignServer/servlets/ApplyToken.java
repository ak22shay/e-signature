/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SignServer.servlets;

/*import SignServer.MyCertUtil;

import sign.mykeystores.*;*/
//import SignServer.CreateCert;
import SignServer.CreateCert;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Akshay
 */
public class ApplyToken extends HttpServlet {

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
        
        String eno = request.getParameter("eno");
       String xname = request.getParameter("xname");
        String xorgu = request.getParameter("xorgu");
        String xorg = request.getParameter("xorg");
        String xaddress = request.getParameter("xaddress");
        String xcity = request.getParameter("xcity");
        String xstate = request.getParameter("xstate");
        String xpin = request.getParameter("xpin");
        String xcountry = request.getParameter("xcountry");
        String xemail = request.getParameter("xemail");
        String xphone = request.getParameter("xphone");
        
        try{

                    // setting the alias name
                    /*MyCertUtil mcu = new MyCertUtil();
                    mcu.setAlias(eno);*/

                    int x;
                    CreateCert c = new CreateCert();
                    //x=c.CertStructure(eno);
                    x=c.CertStructure(xname,xorgu,xorg,xaddress,xcity,xstate,xpin,xcountry,xemail,xphone,eno);

                    if(x==1){
                        response.sendRedirect("SoftToken.jsp");
                        System.out.print("success");
                    }
                    else{
                        response.sendRedirect("SoftToken.jsp");
                        System.out.print("failure");
                    }

            }
            catch(Exception e){
                System.out.print(e);
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
