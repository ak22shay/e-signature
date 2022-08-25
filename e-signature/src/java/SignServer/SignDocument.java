/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SignServer;

import static SignServer.CreateCert.keyStoreFile;
import static SignServer.CreateCert.providerBC;
import static SignServer.CreateCert.providerKeyStore;
import static SignServer.MyCertUtil.getAlias;

import static SignServer.MyCertUtil.splitIntoMultLines;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.UnrecoverableKeyException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.util.encoders.Base64;

/**
 *
 * @author Akshay
 */
public class SignDocument {

    public static String alias;
    private static KeyStore keyStore = null;
    private static String password = "password";

    public void signTxt(String filename) throws Exception {

        /*
        System.out.println("step 1");
        byte[] data = Base64.decode(str.getBytes("UTF-8"));
        String s = signDoc(data);
        String signData = splitIntoMultLines(s);
        System.out.println("step 2");*/
        try {
            System.out.println(filename);
            System.out.println("step 1 new");
            keyStoreFile = new File("C:/Akshay/My Work/final year/DigitalSign/keystore/user/" + getAlias() + ".jks");

            providerBC = new BouncyCastleProvider();
            Security.addProvider(providerBC);
            providerKeyStore = providerBC;
            keyStore = KeyStore.getInstance("jks", Security.getProviders()[0]);//for reading jks key store
            Security.addProvider(providerKeyStore);
            keyStore.load(new FileInputStream(keyStoreFile), password.toCharArray());

            System.out.println("step 2");

            PrivateKey userPK;
            userPK = (PrivateKey) keyStore.getKey(getAlias(), password.toCharArray());

            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(userPK);

            // Supply the data to be signed to the Signature object
            // using the update() method and generate the digital
            // signature.
            //===============================Reading file content with "SIGNATURE" tag===========================
            FileReader file = new FileReader("C:/Akshay/My Work/final year/DigitalSign/txt/"+filename);
            BufferedReader reader = new BufferedReader(file);
            
            System.out.println("here");

            // **** key is declared here in this block of code
            String key = "";
            String line = reader.readLine();

            while (line != null) {
                key += line;
                line = reader.readLine();
            }

            String st = key + "SIGNATURE:" + getAlias();

            //==========================================================================================
            byte[] bytes = st.getBytes();  //converting file sttring into byte

            signature.update(bytes);
            byte[] digitalSignature = signature.sign();
            String base64Data = new String(Base64.encode(digitalSignature), "UTF-8");

            System.out.println("step 3");

            //===============================appending SIGNATURE tag at the end of file==================
            try (FileWriter fw = new FileWriter("C:/Akshay/My Work/final year/DigitalSign/txt/"+filename, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw)) {
                out.println("");
                out.println("SIGNATURE:" + getAlias());
                //more code
                out.println(base64Data);
                //more code
            } catch (IOException e) {
                //exception handling left as an exercise for the reader
            }
            //============================================================================================
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
