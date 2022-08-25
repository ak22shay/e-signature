/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SignServer;


import static SignServer.CreateCert.keyStoreFile;
import static SignServer.CreateCert.providerBC;
import static SignServer.CreateCert.providerKeyStore;
import static SignServer.CreateCert.userPrivKey;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.SignerInfoGenerator;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.x509.extension.AuthorityKeyIdentifierStructure;
import org.bouncycastle.x509.extension.SubjectKeyIdentifierStructure;
/**
 *
 * @author Akshay
 */
public class MyCertUtil {
    
    public static final  String BEGIN_CERTIFICATE = "-----BEGIN CERTIFICATE-----";
    public static final  String END_CERTIFICATE = "-----END CERTIFICATE-----";
    private static File rememberedDir = new File("c:/Akshay/My Work/final year/DigitalSign/certificates");
    public static String alias;
    private static KeyStore keyStore = null;
    private static String password = "password";
    
    
    public static void addAuthorityKeyIdentifier(
            X509v3CertificateBuilder x509v3CertificateBuilder,
            boolean critical,
            AuthorityKeyIdentifierStructure authorityKeyIdentifierStructure
    ) throws Exception {
        x509v3CertificateBuilder.addExtension(
            new ASN1ObjectIdentifier("2.5.29.35"), 
            critical, 
            authorityKeyIdentifierStructure
        );
    }
    public static void addSubjectKeyIdentifier(
            X509v3CertificateBuilder x509v3CertificateBuilder, 
            boolean critical, 
            SubjectKeyIdentifierStructure subjectKeyIdentifierStructure
    ) throws Exception {
        x509v3CertificateBuilder.addExtension(
            new ASN1ObjectIdentifier("2.5.29.14"), 
            critical, 
            subjectKeyIdentifierStructure
        );
        //TODO use following 
//        x509v3CertificateBuilder.addExtension(
//            X509Extension.subjectKeyIdentifier, 
//            critical, 
//            subjectKeyIdentifierStructure
//        );
        
    }
    
    public static void addBasicConstraints(
            X509v3CertificateBuilder x509v3CertificateBuilder, 
            boolean critical, 
            BasicConstraints basicConstraints
    ) throws Exception {
        x509v3CertificateBuilder.addExtension(
            new ASN1ObjectIdentifier("2.5.29.19"), 
            critical, 
            basicConstraints
        );
    }
    
    public static void addKeyUses(
            X509v3CertificateBuilder x509v3CertificateBuilder, 
            boolean critical, 
            KeyUsage keyUsage
    ) throws Exception {
        x509v3CertificateBuilder.addExtension(
            new ASN1ObjectIdentifier("2.5.29.15"), 
            critical, 
            keyUsage
        );
    }
    
    public static String getInfo(X509Certificate certificateX509) throws Exception {
        String sub = certificateX509.getSubjectDN().getName();
        sub = sub.substring(sub.indexOf("CN="));
        if (sub.indexOf(",") > 0) {
            sub = sub.substring(0, sub.indexOf(","));
        }
        //
        String issuer = certificateX509.getIssuerDN().getName();
        issuer = issuer.substring(issuer.indexOf("CN="));
        if (issuer.indexOf(",") > 0) {
            issuer = issuer.substring(0, issuer.indexOf(","));
        }
        return "Subject-" + sub + ", Issuer-" + issuer + ", validty=" + certificateX509.getNotAfter();
    }
    
    public static void exportCert() throws FileNotFoundException, IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException, Exception{
        
        System.out.println("inside the export()");
        //File dir = MyCertUtil.chooseDir();
        File dir = new File("c:/Akshay/My Work/final year/DigitalSign/certificates");
        if(dir == null){
            //return "certificate not exported!";
            System.out.println("certificate not exported!");
        }
        File certFile = new File(dir, getAlias()+".cer");
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(certFile))) {
            pw.println(BEGIN_CERTIFICATE);
            pw.println(toBase64Str(getCert()));
            pw.println(END_CERTIFICATE);
            pw.flush();
        }
        System.out.println("export() end");
    }
    
    public static File chooseDir() {
        System.out.println("inside the choosedir()");
        
        JFileChooser chooser = new JFileChooser(rememberedDir);
        chooser.setDialogTitle("Select keystores directory");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setDialogType(JFileChooser.OPEN_DIALOG);
        int returnVal = chooser.showOpenDialog(new JFrame());
        if(returnVal != JFileChooser.APPROVE_OPTION){
            return null;
        }
        rememberedDir = chooser.getSelectedFile();
        System.out.println("choosedir end");
        return rememberedDir;
    }
    
    public static X509Certificate getCert() throws FileNotFoundException, IOException, NoSuchAlgorithmException, CertificateException, KeyStoreException{
        
        File keyStoreFile = new File("C:/Akshay/My Work/final year/DigitalSign/keystore/user/"+getAlias()+".jks");
        //File KeyStoreFile = new File("C:/Akshay/My Work/final year/DigitalSign/keystore/user/24196.jks");
        keyStore = KeyStore.getInstance("jks", Security.getProviders()[0]);//for reading jks key store
        keyStore.load(new FileInputStream(keyStoreFile), password.toCharArray());
        X509Certificate cert = (X509Certificate) keyStore.getCertificate(getAlias());
        //X509Certificate cert = (X509Certificate) keyStore.getCertificate("24196");
        return cert;
    }
    
    
    
    
    
    
    public static void setAlias(String eno){
        alias = eno;
        
    }
    
    
    public static String getAlias(){
        
        return alias;
    }
    
    
    public static String toBase64Str(X509Certificate x509Certificate) throws Exception {
        return splitIntoMultLines(new String(Base64.encode(x509Certificate.getEncoded()), "UTF-8"));
    }
    
    
    public static String splitIntoMultLines(String input){
        if(input==null){
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int start = 0; int lineSize = 120;
        while(input.length() > start+lineSize){
            sb.append(input.substring(start, start+lineSize)).append("\n");
            start+=lineSize;
        }
        sb.append(input.substring(start));
        return sb.toString();
    }
    
    
    public  int block() throws KeyStoreException, FileNotFoundException, IOException, NoSuchAlgorithmException, CertificateException{
        
        File StoreFile = new File("C:/Akshay/My Work/final year/DigitalSign/keystore/user/"+getAlias()+".jks");
        if(StoreFile.exists()){
            
            StoreFile.delete();
            return 1;
        }
        else{
            return 0;
        }
        
        
    }
    
    /*public static String signDoc(byte[] data) throws OperatorCreationException, KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException, IOException, FileNotFoundException, CertificateException, CMSException{
        
        
        keyStoreFile = new File("C:/Akshay/My Work/final year/DigitalSign/keystore/user/"+getAlias()+".jks");
        
        providerBC = new BouncyCastleProvider();
        Security.addProvider(providerBC);
        providerKeyStore = providerBC;
        keyStore = KeyStore.getInstance("jks", Security.getProviders()[0]);//for reading jks key store
        Security.addProvider(providerKeyStore);
        keyStore.load(new FileInputStream(keyStoreFile), password.toCharArray());
        
        
        PrivateKey userPK;
        userPK = (PrivateKey) keyStore.getKey(getAlias(), password.toCharArray());
        
        //Build CMS   pkcs7
        //1 contentSigner
        ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256WithRSA").setProvider(providerKeyStore).build(userPK);
        //2 digestCalculatorProvider
        JcaDigestCalculatorProviderBuilder digestCalculatorProviderBuilder = new JcaDigestCalculatorProviderBuilder().setProvider(providerBC);
        DigestCalculatorProvider digestCalculatorProvider = digestCalculatorProviderBuilder.build();
        //3 signerInfoGenerator
        JcaSignerInfoGeneratorBuilder signerInfoGeneratorBuilder = new JcaSignerInfoGeneratorBuilder(digestCalculatorProvider);
        SignerInfoGenerator signerInfoGenerator = signerInfoGeneratorBuilder.build(contentSigner, getCert());
        //4 certStore
        List<X509Certificate> certList = new ArrayList<>();
        certList.add(getCert());
        Store certStore = new JcaCertStore(certList);
        //5 signedDataGenerator
        CMSSignedDataGenerator signedDataGenerator = new CMSSignedDataGenerator();
        signedDataGenerator.addSignerInfoGenerator(signerInfoGenerator);
        signedDataGenerator.addCertificates(certStore);
        //6 signedData
        CMSSignedData signedData = signedDataGenerator.generate(new CMSProcessableByteArray(data), true);//true include data
        return new String(Base64.encode(signedData.getEncoded()), "UTF-8");
        
        
    }*/
    
}
