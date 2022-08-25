/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SignServer;

import static SignServer.MyCertUtil.getAlias;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;
import java.util.Collection;
import java.util.Iterator;
import javax.swing.JOptionPane;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.encoders.Base64;

/**
 *
 * @author Akshay
 */
public class SignVerification {

    private static PublicKey PubKey;

    public static int verifySign(String filename) throws UnsupportedEncodingException, CMSException, CertificateException, OperatorCreationException, Exception {

        try {

            //===============================Reading only Signature part===========================
            FileReader file = new FileReader("C:/Akshay/My Work/final year/DigitalSign/txt/" + filename);
            BufferedReader reader = new BufferedReader(file);

            // **** key is declared here in this block of code
            String key = "";
            String line = reader.readLine();;

            while (line != null) {
                if (line.equals("SIGNATURE:" + getAlias())) {
                    line = reader.readLine();
                    while (line != null) {

                        key = line;
                        line = reader.readLine();

                    }
                    break;
                }

                //key += line;
                line = reader.readLine();

            }
            //==========================================================================================

            byte[] digitalSignature = Base64.decode(key);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            //PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            PubKey = MyCertUtil.getCert().getPublicKey();
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(PubKey);

            //=========Reading file content excluding signatue for detecting any changes into file content======
            FileReader file2 = new FileReader("C:/Akshay/My Work/final year/DigitalSign/txt/" + filename);
            BufferedReader reader2 = new BufferedReader(file2);

            // **** key is declared here in this block of code
            String key2 = "";
            String line2 = reader2.readLine();;

            while (line2 != null) {
                if (line2.equals("SIGNATURE:"+getAlias())) {
                    key2 += line2;
                    break;
                }

                key2 += line2;
                line2 = reader2.readLine();

            }

            //===================================================================================================
            byte[] bytes = key2.getBytes();

            signature.update(bytes);

            boolean verified = signature.verify(digitalSignature);
            if (verified) {
                System.out.println("Data verified.");
                return 1;
            } else {
                System.out.println("Cannot verify data.");
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("always returning 0");

        return 0;

    }

}
