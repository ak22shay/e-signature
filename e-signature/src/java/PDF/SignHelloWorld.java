/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PDF;


import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.BouncyCastleDigest;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.ExternalSignature;
import com.itextpdf.text.pdf.security.MakeSignature;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;
import com.itextpdf.text.pdf.security.PrivateKeySignature;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
/**
 *
 * @author Akshay
 */
public class SignHelloWorld {
    
    public void sign(String src, String dest,
                    java.security.cert.Certificate[] chain, PrivateKey pk, String digestAlgorithm, String provider,
                    CryptoStandard subfilter, String reason, String location)
                    throws GeneralSecurityException, IOException, DocumentException {
    // Creating the reader and the stamper
    System.out.println("step 3");
    PdfReader reader = new PdfReader(src);
    FileOutputStream os = new FileOutputStream(dest);
    PdfStamper stamper = PdfStamper.createSignature(reader, os, '\0');
    // Creating the appearance
    PdfSignatureAppearance appearance = stamper.getSignatureAppearance();
    appearance.setReason(reason);
    appearance.setLocation(location);
    appearance.setVisibleSignature(new Rectangle(36, 748, 144, 780), 1, "sig");
    // Creating the signature
    ExternalDigest digest = new BouncyCastleDigest();
    ExternalSignature signature =
    new PrivateKeySignature(pk, digestAlgorithm, provider);
    MakeSignature.signDetached(appearance, digest, signature, chain,
    null, null, null, 0, subfilter);
    }
    
}
