/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SignServer;

import java.io.BufferedReader;
import java.io.FileReader;

/*
import static SignServer.MyCertUtil.signDoc;
import static SignServer.MyCertUtil.splitIntoMultLines;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.KeyStoreException;
import java.security.UnrecoverableKeyException;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.util.encoders.Base64;*/
/**
 *
 * @author Akshay
 */
public class test {
    
    public static void main(String arg[]) throws Exception{
        /*
        String str="rohit";
        System.out.println("step 1");
        byte[] data = Base64.decode(str.getBytes("UTF-8"));
        String s = signDoc(data);
        String signData = splitIntoMultLines(s);
        System.out.println(s);
        System.out.println("step 2");
        
        BufferedWriter wr = null;
        try{
            wr = new BufferedWriter(new FileWriter("C:/Akshay/My Work/BE project/test5/signed_doc/filename.txt"));
            wr.write(signData);
        }
        catch(IOException e){
            
        }
        finally{
            try{
                if(wr != null){
                    wr.close();
                }
            }
            catch(IOException e){               
            }
        }*/
    
        /*PrintWriter out = new PrintWriter("C:/Akshay/My Work/BE project/test5/signed_doc/filename.txt");
        out.println(str);*/
        //System.out.println("step 3");
        
        FileReader file = new FileReader("C:/Akshay/My Work/final year/DigitalSign/txt/ip.txt");
             BufferedReader reader = new BufferedReader(file);

            // **** key is declared here in this block of code
            String key = "";
            String line = reader.readLine();;

            /*while (line != null) {
                if(line.equals("SIGNATURE:24196")){
                    key += line;                    
                    break;
                }
                
                key += line;
                line = reader.readLine();
                
            }*/
            while (line != null) {
                if(line.equals("SIGNATURE:24196")){
                    line = reader.readLine();
                    while(line!=null){
                        
                        key=line;
                        line = reader.readLine();
                        
                    }                   
                    break;
                }
                
                //key += line;
                line = reader.readLine();
                
            }
            
            /*do{
                line = reader.readLine();
                key += line;
                System.out.println(line);
            }
            while(line != "SIGNATURE:24196");*/
        
            System.out.println(key);
        
    }
    
}
