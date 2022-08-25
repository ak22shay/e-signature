/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SignServer;

import sign.security_providers.X509Util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateCrtKey;
import java.util.Calendar;
import java.util.Date;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERInteger;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DEROutputStream;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x509.TBSCertificateStructure;

import org.bouncycastle.asn1.x509.Time;
import org.bouncycastle.asn1.x509.V3TBSCertificateGenerator;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.bouncycastle.jce.PrincipalUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.KeyFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateCrtKeySpec;

import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.DERBMPString;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;

import org.bouncycastle.asn1.x509.X509CertificateStructure;

import org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.bouncycastle.jce.provider.X509CertificateObject;
import org.bouncycastle.x509.extension.SubjectKeyIdentifierStructure;


import org.bouncycastle.crypto.*;

/**
 *
 * @author Akshay
 */
public class CreateCert {
    
    SerialNo sn = new SerialNo();
    
    public static Provider providerBC;
    public static Provider providerKeyStore;//Sun or pkcs11
    private static KeyStore keyStore = null;
    private String password = "password";
    public PublicKey userPubKey;
    public static PrivateKey userPrivKey;
    public int validityDays = 180;
    public static File keyStoreFile;
     
    String  caFile = "C:/Akshay/My Work/final year/DigitalSign/keystore/ca/barc_ca.jks";
    String caAlias = "barc_ca";
    String caPassword = "password";
    private X509Certificate caCert;// This holds the certificate of the CA used to sign the new certificate.
    private RSAPrivateCrtKeyParameters caPrivateKey;//This holds the private key of the CA used to sign the new certificate.
    
    public int CertStructure(String xname,String xorgu,
                    String xorg,String xaddress,
                    String xcity, String xstate,
                    String xpin, String xcountry,
                    String xemail, String xphone,
                    String alias) throws KeyStoreException, FileNotFoundException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, InvalidKeyException, NoSuchProviderException, SignatureException, InvalidKeySpecException, InvalidCipherTextException, Exception{
        
        
     //=======================================================================================
        //===============================       Load CA       =====================================
        //=======================================================================================
        // load the key entry from the keystore 
        KeyStore caKs = KeyStore.getInstance("jks");
        caKs.load(new FileInputStream(new File(caFile)), caPassword.toCharArray());
        System.out.println("CA loaded succesfully");
        Key key = caKs.getKey(caAlias, caPassword.toCharArray());
	if (key == null) {
            System.out.println("Got null key from keystore!");
            throw new RuntimeException("Got null key from keystore!"); 
	}
        RSAPrivateCrtKey privKey = (RSAPrivateCrtKey) key;
	caPrivateKey = new RSAPrivateCrtKeyParameters(privKey.getModulus(), privKey.getPublicExponent(), privKey.getPrivateExponent(),
                            privKey.getPrimeP(), privKey.getPrimeQ(), privKey.getPrimeExponentP(), privKey.getPrimeExponentQ(), privKey.getCrtCoefficient());
        System.out.println("ca private key loaded");
        
        // and get the certificate
        
        caCert = (X509Certificate) caKs.getCertificate(caAlias);
	if (caCert == null) {
            System.out.println("Got null cert from keystore!");
            throw new RuntimeException("Got null cert from keystore!"); 
	}
        System.out.println("ca certificate loaded");
        //caCert.verify(caCert.getPublicKey());//verified CA certificate with its own public key
        //System.out.println("verified CA certificate with its own public key");






        //=======================================================================================
        //=====================      Load JKS or create NEW JKS (Client)   ======================
        //=======================================================================================
        
        keyStoreFile = new File("C:/Akshay/My Work/final year/DigitalSign/keystore/user/"+alias+".jks");
        int ret;
        providerBC = new BouncyCastleProvider();
        Security.addProvider(providerBC);
        providerKeyStore = providerBC;
        keyStore = KeyStore.getInstance("jks", Security.getProviders()[0]);//for reading jks key store
        Security.addProvider(providerKeyStore);
        if(keyStoreFile.exists()){
            keyStore.load(new FileInputStream(keyStoreFile), password.toCharArray());
            System.out.println("success");
            ret=1;
        }else{
            keyStore.load(null, null);
            
            System.out.println("failed....so created new one");
            ret=2;
        
       
        
        
        
        
       
        
                    //=======================================================================================
                    //====================== generate the keypair for the new User certificate=================
                    //=======================================================================================
                    SecureRandom sr = new SecureRandom();
                    // this is the JSSE way of key generation
                    KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
                    keyGen.initialize(1024, sr);
                    KeyPair keypair = keyGen.generateKeyPair();
                    //privKey = (RSAPrivateCrtKey) keypair.getPrivate();
                    userPrivKey = keypair.getPrivate();
                    userPubKey = keypair.getPublic();
                    
                    /*
                    RSAKeyPairGenerator gen = new RSAKeyPairGenerator();
                                    gen.init(new RSAKeyGenerationParameters(BigInteger.valueOf(3), sr, 1024, 80));
                                    AsymmetricCipherKeyPair keypair = gen.generateKeyPair();
                                    //logger.debug("Generated keypair, extracting components and creating public structure for certificate");
                                    RSAKeyParameters publicKey = (RSAKeyParameters) keypair.getPublic();
                                    RSAPrivateCrtKeyParameters privateKey = (RSAPrivateCrtKeyParameters) keypair.getPrivate();
                                    // used to get proper encoding for the certificate
                                    RSAPublicKeyStructure pkStruct = new RSAPublicKeyStructure(publicKey.getModulus(), publicKey.getExponent());
                                    //logger.debug("New public key is '" + new String(Hex.encodeHex(pkStruct.getEncoded())) + 
                                                    //", exponent=" + publicKey.getExponent() + ", modulus=" + publicKey.getModulus());
                                    // JCE format needed for the certificate - because getEncoded() is necessary...
                            userPubKey = KeyFactory.getInstance("RSA").generatePublic(
                                            new RSAPublicKeySpec(publicKey.getModulus(), publicKey.getExponent()));
                            // and this one for the KeyStore
                            userPrivKey = KeyFactory.getInstance("RSA").generatePrivate(
                                            new RSAPrivateCrtKeySpec(publicKey.getModulus(), publicKey.getExponent(),
                                                            privateKey.getExponent(), privateKey.getP(), privateKey.getQ(), 
                                                            privateKey.getDP(), privateKey.getDQ(), privateKey.getQInv()));

                    System.out.println("new key pair has been created line-101 "+ userPrivKey+"and"+userPubKey);
                    */

                    //=======================================================================================
                    //======================Create certificate structure  =================
                    //=======================================================================================

                    Calendar expiry = Calendar.getInstance();
                    expiry.add(Calendar.DAY_OF_YEAR, validityDays);

                    org.bouncycastle.asn1.x500.X500Name subjectX500Name =  //<editor-fold defaultstate="collapsed" desc="subjectDN;//distinguished name">
                    new X500NameBuilder(BCStyle.INSTANCE)
                            .addRDN(BCStyle.CN, xname)//name
                            .addRDN(BCStyle.OU,xorgu )//BARC
                            .addRDN(BCStyle.O, xorg)//DAE
                            .addRDN(BCStyle.POSTAL_ADDRESS, xaddress)//address
                            .addRDN(BCStyle.L, xcity)//city
                            .addRDN(BCStyle.ST, xstate)//state
                            .addRDN(BCStyle.POSTAL_CODE, xpin)//pin
                            .addRDN(BCStyle.C, xcountry)//country initial
                            .addRDN(BCStyle.E, xemail)//email
                            .addRDN(BCStyle.TELEPHONE_NUMBER, xphone)//phone
                            .addRDN(BCStyle.SERIALNUMBER, String.valueOf(sn.getTokenSNo())
                    ).build();

                    // generate a certificate signing request
                    V3TBSCertificateGenerator certGen = new V3TBSCertificateGenerator();
                    certGen.setSerialNumber(new DERInteger(BigInteger.valueOf(System.currentTimeMillis())));
                    certGen.setIssuer(PrincipalUtil.getSubjectX509Principal(caCert));  //  issued by
                    certGen.setSubject(subjectX500Name);  // issued to

                    DERObjectIdentifier sigOID = X509Util.getAlgorithmOID("SHA1WithRSAEncryption");
                    AlgorithmIdentifier sigAlgId = new AlgorithmIdentifier(sigOID, new DERNull());
                    certGen.setSignature(sigAlgId);
                    certGen.setSubjectPublicKeyInfo(new SubjectPublicKeyInfo((ASN1Sequence)new ASN1InputStream(
                            new ByteArrayInputStream(userPubKey.getEncoded())).readObject()));
                            certGen.setStartDate(new Time(new Date(System.currentTimeMillis())));
                            certGen.setEndDate(new Time(expiry.getTime()));

                     System.out.println("Certificate structure generated, creating SHA1 digest");

                    // attention: hard coded to be SHA1+RSA!
                            SHA1Digest digester = new SHA1Digest();
                            AsymmetricBlockCipher rsa = new PKCS1Encoding(new RSAEngine());
                            TBSCertificateStructure tbsCert = certGen.generateTBSCertificate();

                            ByteArrayOutputStream   bOut = new ByteArrayOutputStream();
                            DEROutputStream         dOut = new DEROutputStream(bOut);
                            dOut.writeObject(tbsCert);

                    // and now sign = the JCE way
                    byte[] signature;
                    PrivateKey caPrivKey = KeyFactory.getInstance("RSA").generatePrivate(
                                            new RSAPrivateCrtKeySpec(caPrivateKey.getModulus(), caPrivateKey.getPublicExponent(),
                                                            caPrivateKey.getExponent(), caPrivateKey.getP(), caPrivateKey.getQ(), 
                                                            caPrivateKey.getDP(), caPrivateKey.getDQ(), caPrivateKey.getQInv()));

                            Signature sig = Signature.getInstance(sigOID.getId());
                            sig.initSign(caPrivKey, sr);
                            sig.update(bOut.toByteArray());
                            signature = sig.sign();

                    /*
                    byte[] certBlock = bOut.toByteArray();
                                    // first create digest
                                    //logger.debug("Block to sign is '" + new String(Hex.encodeHex(certBlock)) + "'");		
                                    digester.update(certBlock, 0, certBlock.length);
                                    byte[] hash = new byte[digester.getDigestSize()];
                                    digester.doFinal(hash, 0);
                                    // and sign that
                                    rsa.init(true, caPrivateKey);
                                    DigestInfo dInfo = new DigestInfo( new AlgorithmIdentifier(X509ObjectIdentifiers.id_SHA1, null), hash);
                                    byte[] digest = dInfo.getEncoded(ASN1Encodable.DER);
                                    signature = rsa.processBlock(digest, 0, digest.length);*/

                    System.out.println("CA signature updated");

                    // and finally construct the certificate structure
                    ASN1EncodableVector  v = new ASN1EncodableVector();

                    v.add(tbsCert);
                    v.add(sigAlgId);
                    v.add(new DERBitString(signature));

                    X509CertificateObject clientCert = new X509CertificateObject(new X509CertificateStructure(new DERSequence(v))); 
                    //clientCert.verify(caCert.getPublicKey());
                    //System.out.println("Verifying certificate for correct signature with CA public key");
                    System.out.println("client certificate created ");



                    PKCS12BagAttributeCarrier bagCert = clientCert;
                    bagCert.setBagAttribute(PKCSObjectIdentifiers.pkcs_9_at_friendlyName,
                                    new DERBMPString("Certificate for IPSec WLAN access"));
                    bagCert.setBagAttribute(
                            PKCSObjectIdentifiers.pkcs_9_at_localKeyId,
                            new SubjectKeyIdentifierStructure(userPubKey));

                    X509Certificate[] chain = new X509Certificate[2];
                    // first the client, then the CA certificate
                    chain[0] = clientCert;
                    chain[1] = caCert;
                    System.out.println("chain created");


                    //==========================  store certificate in jks======================

                    keyStore.setKeyEntry(alias, userPrivKey, password.toCharArray(), chain);

                    if(!keyStoreFile.exists()){
                        keyStore.store(new FileOutputStream(keyStoreFile), password.toCharArray());
                    }




                    userPrivKey = (PrivateKey) keyStore.getKey(alias, password.toCharArray());
                    if(userPrivKey == null){
                         System.out.println("private key entry not found! ");
                    }
                    System.out.println("private key loaded");

                    X509Certificate certificateX509 = (X509Certificate) clientCert;
                    String certInfo = MyCertUtil.getInfo(certificateX509);
                    System.out.println(certInfo);
                    //============================================================================
                    System.out.println("client certificate stored in jks");

        
        }   
        
        return ret;
    }
    
}
