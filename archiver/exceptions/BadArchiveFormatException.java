package archiver.exceptions;

/**
 * Throws when an given file does not represent archive file
 * or if one of the entry header checksum are not correct
 * 
 * @author Ahmad Alsaytari <saytari.dev@gmail.com>
 *
 */
public class BadArchiveFormatException extends Exception {

	private static final long serialVersionUID = -5663366428117305785L;

	public BadArchiveFormatException() {
		
	}
	
	public BadArchiveFormatException(String s) {
		super(s);
	}
}
