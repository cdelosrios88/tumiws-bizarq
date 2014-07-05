package pe.com.tumi.common.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Clase encargada de codificar y codificar cadenas.
 * <p/>
 * @author enrique
 *
 */
public class Encriptador 
{
	public static Base64u base ;

	/**
	 * Metodo encriptador implementado con Blowfish y luego a Base64u	
	 * @param to_encrypt
	 * @param strkey
	 * @return cadena_encriptada
	 */
	public static String encryptBlowfish(String to_encrypt, String strkey) {
		String b64_enco = null;
		try {
			base = new Base64u();
		    SecretKeySpec key = new SecretKeySpec(strkey.getBytes(), "Blowfish");
		     Cipher cipher = Cipher.getInstance("Blowfish");
		     cipher.init(Cipher.ENCRYPT_MODE, key);
		     String encriptado = new String(cipher.doFinal(to_encrypt.getBytes()));
		     b64_enco =  base.encode(encriptado.getBytes());
		     return b64_enco;
		  } catch (Exception e) {
			  return null; }
		}
	/**
	 * Metodo desencriptador implementado con Base64u y luego a Blowfish	
	 * @param to_encrypt
	 * @param strkey
	 * @return cadena_desencriptada
	 */
	public static String decryptBlowfish(String to_decrypt, String strkey) {
		try {
			base = new Base64u();
			byte[] b64_dec =  base.decode(to_decrypt);
			String desEncriptado = new String(b64_dec); 
			SecretKeySpec key = new SecretKeySpec(strkey.getBytes(), "Blowfish");
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] decrypted = cipher.doFinal(desEncriptado.getBytes());
			return new String(decrypted);
	  } catch (Exception e){
		  return null; 
	  }
	}
	
	
	
}
