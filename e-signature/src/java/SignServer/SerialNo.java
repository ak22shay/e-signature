/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SignServer;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
/**
 *
 * @author Akshay
 */
public class SerialNo {
    
    private  String tokenSNo = "123456789";
    
    public String getTokenSNo(){
        
        return tokenSNo;
    }
    public BigInteger generateCertSNo(int applicationSNo) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (DataOutputStream dos = new DataOutputStream(baos)) {
            dos.write("B".getBytes("UTF-8"));                           //1-1
            dos.writeInt(applicationSNo);                               //2-5
            dos.writeInt((int)(System.currentTimeMillis() / 1000));    //6-9
            byte[] randomBytes = new byte[5];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(randomBytes);
            secureRandom.nextBytes(randomBytes);
            dos.write(randomBytes);                                     //10-14
        }
        return new BigInteger(baos.toByteArray());
    }
    
}
