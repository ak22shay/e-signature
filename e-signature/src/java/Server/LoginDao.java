/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/**
 *
 * @author Akshay
 */
public class LoginDao {
    
    String url = "jdbc:derby://localhost:1527/esign_user";
    //String url = "jdbc:derby://localhost:1527/EsignService_DB";
    String uname = "root";
    String pass = "root";
    
    public boolean check(String employee, String password){
        
        String sql = "select * from user_info where employee=? and password=?";
        //String sql = "select * from userinfo where employee=? and password=?";

        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url,uname,pass);
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, employee);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                return true;
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
            
        return false;
    }
    
    public String getName(String employee){
    
        String sql = "select fname from user_info where employee=?";
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url,uname,pass);
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, employee);
            ResultSet rs = st.executeQuery();
            rs.next();
            String name= rs.getString("fname");
            
            return name;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
    
}
