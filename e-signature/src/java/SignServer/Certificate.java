/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SignServer;

import static SignServer.MyCertUtil.getAlias;

import java.io.File;
import java.io.IOException;
/**
 *
 * @author Akshay
 */
public class Certificate {
    
    public static void viewCert() throws IOException{
        System.out.println(" reached inside viewCert method");
        File file = new File("c:/Akshay/My Work/final year/DigitalSign/certificates/"+getAlias()+".cer");
         System.out.println("file loaded");
        DesktopAPI desktop = new DesktopAPI();
       
        if(file.exists()){
            
            desktop.open(file);
        }
        System.out.println(" reached end of viewCert method");
    }
    
}
