package archiver.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;
import java.util.InputMismatchException;

import archiver.exceptions.BadArchiveFormatException;

/**
 * Input validation helper used TAR class to check on parameters
 * 
 * @author Joud_Alhumairy <joud.alhumairy@gmail.com>
 *
 * @version 1.0.0
 */
public class Validator {

	/**
	 * Check if files are exist
	 * 
	 * @param files
	 * 			files to check
	 * 
	 * @throws FileNotFoundException
	 */
	public static void exist(File[] files) throws FileNotFoundException {
		for (File file : files)
			exist(file);
	}
	
	/**
	 * Check if files are exist
	 * 
	 * @param files
	 * 			files to check
	 * 
	 * @throws FileNotFoundException
	 */
	public static void exist(File file) throws FileNotFoundException {
		if (file == null || ! file.exists())
			throw new FileNotFoundException();
	}
	/**
	 * Check if an file is directory
	 * 
	 * @param file
	 * 			file to check
	 * 
	 * @throws BadDirectoryException
	 */
	public static void directory(File file) throws NotDirectoryException {
		if (file == null || ! file.isDirectory())
			throw new NotDirectoryException("");
	}
	
	/**
	 * Check if an file is valid TAR file
	 * 
	 * @param file
	 * 			file to check
	 * 
	 * @throws BadArchiveFormatException
	 */
	public static void archiveFile(File file) throws BadArchiveFormatException {
		if (file == null || (file.length() % 512) != 0)
			throw new BadArchiveFormatException();
		
		String[] parts = file.getName().replace(".", " ").split(" ");
		
		if(! parts[parts.length - 1].equalsIgnoreCase("tar"))
			throw new BadArchiveFormatException();
	}
	
	/**
	 * Check the validation of file name
	 * 
	 * @param name
	 * 			name to check
	 * 
	 * @throws Exception
	 */
	public static void archiveName(String name) throws InputMismatchException {
		if(name.length() == 0 )
			throw new InputMismatchException("Invalid name");
	}
}
