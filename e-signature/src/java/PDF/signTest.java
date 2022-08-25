/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PDF;


import com.itextpdf.text.pdf.security.DigestAlgorithms;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;
import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.CertificateException;
/**
 *
 * @author Akshay
 */
public class signTest {
    
    private static String password = "password";
    private static KeyStore keyStore = null;
    public static Provider providerBC;
    public static Provider providerKeyStore;//Sun or pkcs11
    
    public static final String KEYSTORE = "C:/Akshay/My Work/final year/DigitalSign/keystore/user/24196.jks";
    public static final char[] PASSWORD = "password".toCharArray();
    public static final String SRC = "C:/Akshay/My Work/final year/DigitalSign/pdf/gate.pdf";
    public static final String DEST = "C:/Akshay/My Work/final year/DigitalSign/pdf/signed/output.pdf";
    
    public static void main(String arg[]) throws KeyStoreException, FileNotFoundException, IOException, NoSuchAlgorithmException, CertificateException, Exception{
    //public static void ss() throws KeyStoreException, FileNotFoundException, IOException, NoSuchAlgorithmException, CertificateException, Exception{    
        System.out.println("step 1");
        
        
       
        
        //BouncyCastleProvider provider = new BouncyCastleProvider();
       // Security.addProvider(provider);
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(new FileInputStream(KEYSTORE), PASSWORD);
        String alias = (String)ks.aliases().nextElement();
        PrivateKey pk = (PrivateKey) ks.getKey(alias, PASSWORD);
        java.security.cert.Certificate[] chain = ks.getCertificateChain(alias);
        SignHelloWorld app = new SignHelloWorld();
        
        System.out.println("step 2");
        
        app.sign(SRC, String.format(DEST, 1), chain, pk, DigestAlgorithms.SHA256,
        null, CryptoStandard.CMS, "for testing purpose", "mumbai");
        
        System.out.println("step 4");
    }
    
}
