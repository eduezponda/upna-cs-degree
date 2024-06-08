package anonymoussecureassociation;
/**
 *
 * @author MAZ
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.interfaces.DHKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
//
public final class AnonymousSecureAssociation {
  
  static private final String CLASS_NAME = AnonymousSecureAssociation.class.getName();
  static private final Logger LOGGER = Logger.getLogger(CLASS_NAME);
  
  private final MessageDigest md;
  private final SecureRandom rnd;
  
  private final KeyPairGenerator kpg;
  private final KeyFactory kf;
  private final KeyAgreement ka;
  
  private final DataInputStream is;
  private final DataOutputStream os;
  
  private Cipher encrypter;
  private Cipher decrypter;

  public AnonymousSecureAssociation (final InputStream is,
                                      final OutputStream os) throws NoSuchAlgorithmException {
    this.is = new DataInputStream(is);
    this.os = new DataOutputStream(os);
    
    SecureRandom _rnd;
    try {
      _rnd = SecureRandom.getInstance("SHA1PRNG"); //"NativePRNGNonBlocking");
    } catch (final NoSuchAlgorithmException ex) {
      _rnd = new SecureRandom();
    }
    this.rnd = _rnd;
    this.rnd.setSeed(this.rnd.generateSeed(64));
    
    try {
      this.md = MessageDigest.getInstance("SHA3-256");
    } catch (final  NoSuchAlgorithmException ex) {
      LOGGER.log(Level.WARNING, "algoritmo de resumen digital por la plataforma", ex);
      throw new NoSuchAlgorithmException();
    }    
    
    try {
      this.kpg = KeyPairGenerator.getInstance("DiffieHellman");
      this.kf  = KeyFactory.getInstance("DiffieHellman");
      this.ka  = KeyAgreement.getInstance("DiffieHellman");       
    } catch (final NoSuchAlgorithmException ex) {
      LOGGER.log(Level.WARNING, "algoritmo de acuerdo no provisto por la plataforma", ex);
      throw new NoSuchAlgorithmException();
    }

  }    
  
  public void init (final int bitLength) throws IOException, GeneralSecurityException {
    
    if ((bitLength % 64) != 0) {
      System.err.println("Longitud debe ser múltiplo de 64 bites");
      throw new IllegalArgumentException();
    } else if ((bitLength < 512) || (bitLength > 8192)) {
      System.err.println("Longitud debe estar entre 512 y 8192 bites");
      throw new IllegalArgumentException();      
    }  
    kpg.initialize(bitLength, rnd);    
    
    try { // Agente que toma la iniciativa
        
     
      LOGGER.log(Level.INFO, "0: arranca protocolo de acuerdo");
      
      // 1: generación del par criptográfico     
      final KeyPair dhKeyPar = kpg.generateKeyPair();

      LOGGER.log(Level.INFO, "1: par criptográfico creado");
      
      // 2: transmisión de la clave pública propia
      //extraer clave publica
      final PublicKey publickey = dhKeyPar.getPublic();
      //extraer codificacion de esa clave publica
      final byte[] bytes0 = publickey.getEncoded();
      //hay diferentes formas de enviar la informacion
      //codificar en base 64 y se convierte en secuencia de caracteres
      final String _publicKey = Base64.getEncoder().encodeToString(bytes0);
      os.writeUTF(_publicKey);
      os.flush();
      LOGGER.log(Level.INFO, "2: clave pública propia enviada");
      
      // 3: reconstrucción de la clave pública recibida      
      // nos envian la clave serializada
      final String _otherPublicKey = is.readUTF();
      //obtener secuencia de objetos que le corresponde
      final byte[] bytes1 = Base64.getDecoder().decode(_otherPublicKey);
      //a partir de la secuencia de objetos, crear un objeto de la clase X509EncodedKeySpec
      //a partir de esta especifiacion de la clave publica, se reconstruye
      final X509EncodedKeySpec x509spec = new X509EncodedKeySpec(bytes1);
      //reconstruir la clave publica
      final PublicKey otherPublicKey = kf.generatePublic(x509spec);
      LOGGER.log(Level.INFO, "3: clave pública recibida y reconstruida");
      
      // 4: generación de material secreto
      //se genera a partir de la clave publica y de la clave privada
      ka.init(dhKeyPar.getPrivate());
      ka.doPhase(otherPublicKey, true);
      //ya se puede generar el material secreto, que se hace con el objeto keyAgreement
      final byte[] secret = ka.generateSecret();
      LOGGER.log(Level.INFO, "4: material secreto generado");
      
      ciphersConfiguration(secret);
      
      LOGGER.log(Level.INFO, "5: cifradores instanciados y configurados");      

    } catch (final InvalidKeyException ex) {
      LOGGER.log(Level.SEVERE, "InvalidKeyException", ex);
      throw new GeneralSecurityException();
    } catch (final InvalidKeySpecException ex) {
      LOGGER.log(Level.SEVERE, "InvalidKeySpecException", ex);
      throw new GeneralSecurityException();
    }

  }

  public void accept () throws IOException, GeneralSecurityException {

    try { // Agente que acepta participar en el acuerdo
      
      LOGGER.log(Level.INFO, "0: arranca protocolo de acuerdo");
      
      // 1: reconstrucción de la clave publica recibida          

      LOGGER.log(Level.INFO, "1: clave pública recibida y reconstruida");
      
      // 2: generación del par criptográfico
      
      LOGGER.log(Level.INFO, "2: par criptografico generado");
      
      // 3: transmisión de la clave pública propia     
           
      LOGGER.log(Level.INFO, "3: clave publica propia enviada");
      
      // 4: generación de material secreto
      
      LOGGER.log(Level.INFO, "4: material secreto generado");
      
      ciphersConfiguration(secret);
      
      LOGGER.log(Level.INFO, "5: cifradores instanciados y configurados");

    } catch (final InvalidAlgorithmParameterException ex) {
      LOGGER.log(Level.SEVERE, "InvalidAlgorithmParameterException", ex);
      throw new GeneralSecurityException();
    } catch (final InvalidKeySpecException ex) {
      LOGGER.log(Level.SEVERE, "InvalidKeySpecException", ex);
      throw new GeneralSecurityException();
    } catch (final InvalidKeyException ex) {
      LOGGER.log(Level.SEVERE, "InvalidKeyException", ex);
      throw new GeneralSecurityException();
    }

  }

  private void ciphersConfiguration (final byte[] secret)
                                               throws GeneralSecurityException {
    
    try {

      // Generación del resumen digital del material secreto recibido
      final byte[] bytes = md.digest(secret);
      
      // Una vez usado, el material acordado se elimina de memoria RAM.
      Arrays.fill(secret, (byte) 0);

      // Preparación de clave simétrica
      final SecretKeySpec key = new SecretKeySpec(bytes, 0, 16, "AES");
      
      // Preparación del vector de inicialización
      final IvParameterSpec iv = new IvParameterSpec(bytes, 16, 16);

      // Instaciación y configuración del encriptador
      this.encrypter = Cipher.getInstance("AES/CFB/NoPadding");
      encrypter.init(Cipher.ENCRYPT_MODE, key, iv);
      
      // Instaciación y configuración del desencriptador      
      this.decrypter = Cipher.getInstance("AES/CFB/NoPadding");      
      decrypter.init(Cipher.DECRYPT_MODE, key, iv);

    } catch (final NoSuchAlgorithmException |
                   NoSuchPaddingException |
                   InvalidAlgorithmParameterException ex) {
      LOGGER.log(Level.WARNING, "", ex.getCause());
      throw new GeneralSecurityException();
    }

  }
  
  public Cipher getEncrypter () { return encrypter; }
  public Cipher getDecrypter () { return decrypter; }
  
}
