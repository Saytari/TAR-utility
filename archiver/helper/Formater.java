package archiver.helper;

import java.io.UnsupportedEncodingException;

/**
 * Format utility used in many place specially in fields package
 * 
 * @author Joud_Alhumairy <joud.alhumairy@gmail.com>
 *
 * @version 1.0.0
 */
public class Formater {

	/**
	 * Convert string into array of bytes, string characters
	 * Treated as ASCII table char
	 * 
	 * @param string
	 * 			String to convert
	 * @return
	 * 			Bytes represent string
	 */
	public static byte[] toBytes(String string) {
		try {
			return string.getBytes("US-ASCII");
		} catch (UnsupportedEncodingException e) {
			// do nothing
		}
		return null;
	}
	
	/**
	 * Convert bytes array to its matched string, assume array
	 * are US-ASCII char set
	 * 
	 * @param bytes
	 * 			Array to convert
	 * @return
	 * 			String represent array
	 */
	public static String toString(byte[] bytes) {
		String string = "";
		for (byte letter : bytes)
			if (letter != '\0')
				string += (char) letter;
		return string;
	}
	
	/**
	 * Padding left an string to length with zero character
	 * 
	 * @param string
	 * 			String to pad
	 * @param length
	 * 			padding length
	 * @return
	 * 			String after padding
	 */
	public static String pad(String string, int length) {
		return String.format("%1$" + length + "s", string).replace(' ', '0');
	}
	
	/**
	 * Padding right an string to length with and character
	 * 
	 * @param string
	 * 			String to pad
	 * @param length
	 * 			Padding length
	 * @param padChar
	 * 			Padding character
	 * @return
	 */
	public static String rightPad(String string, int length, char padChar) {
		String extra = String.format("%1$" + (length - string.length()) + "s", "").replace(' ', padChar);
		return string + extra;
		
	}
	
	/**
	 * Convert integer to string represents its octal value
	 * 
	 * @param value
	 * 			Integer to convert
	 * @return
	 * 			Octal representation string
	 */
	public static String toOctal(int value) {
		return Integer.toOctalString(value);
	}
}
