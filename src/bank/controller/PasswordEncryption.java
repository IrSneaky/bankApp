package bank.controller;

import java.util.Base64;
import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordEncryption
{
	public static byte[] getSalt()
	{
		try
		{
			SecureRandom rand = SecureRandom.getInstance("SHA1PRNG"); //gets secured random
			byte[] salt = new byte[32]; //256 bit salt
			rand.nextBytes(salt);
			return salt;
		}
		catch(NoSuchAlgorithmException e)
		{
			System.err.println("Salt failed");
			e.printStackTrace();
		}
		return null;
	}	

	public static String hashThePass(String password, byte[] salt)
	{
		char[] pass = password.toCharArray(); //cast string to match keyspec constructor
		int iterations = 20000; //8ms goal for SHA-256 on lowend cpu
		int keyLength = 256; //sha-256 output a 256 bit or 32 btye hash
		String algo = "PBKDF2WithHmacSHA256";//Sha-256 prf for hash
		try
		{
			PBEKeySpec spec = new PBEKeySpec(pass, salt, iterations, keyLength);
			SecretKeyFactory hashMe = SecretKeyFactory.getInstance(algo);
			//generate the has with the generated key spec
			byte[] hashByte = hashMe.generateSecret(spec).getEncoded();
			//turn the byte hash into a string for storage
			String hashed = Base64.getEncoder().encodeToString(hashByte);
			spec.clearPassword(); //clears copy of password in KeySpec obj
			return hashed;
		}
		catch(NoSuchAlgorithmException | InvalidKeySpecException e)
		{
			System.err.println("Hash failed");
			e.printStackTrace();
		}
		return "";
	}

	public static boolean checkPass(String tempPass, String hashedPass, byte[] salt)
	{
		//put the attempted login pass into hash
		String tempHash = hashThePass(tempPass, salt);
		//returning results of check
		return (tempHash.equals(hashedPass));
	}
}
