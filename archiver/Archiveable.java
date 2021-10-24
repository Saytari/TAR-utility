package archiver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.InputMismatchException;

import archiver.entity.contracts.Infoable;
import archiver.exceptions.BadArchiveFormatException;

/**
 * Every archive strategy should implement this interface
 * it can be use for ZIP, RAR & other archive implementations
 * 
 * @author Ahmad Alsaytari <saytari.dev@gmail.com>
 * 
 * @version 1.0.0
 */
public interface Archiveable {

	/**
	 * Archive multiple files in one TAR file
	 * inside specific directory
	 * 
	 * @param files 
	 * 				client files to archive
	 * @param directory
	 * 				an directory to place in the generated TAR file
	 * @param archiveName
	 * 				the name of TAR file
	 * 
	 * @throws IOException
	 * @throws BadArchiveFormatException 
	 * @throws Exception 
	 */
	public void archive(File[] files, File directory, String archiveName) throws FileNotFoundException, NotDirectoryException, InputMismatchException;
	
	/**
	 * Extract archived files from TAR file
	 * to specific directory
	 * 
	 * @param archiveFile
	 * 				TAR file to extract files from
	 * @param directory
	 * 				an directory to place extracted files in
	 * 
	 * @throws IOException
	 * @throws BadArchiveFormatException
	 */
	public void unarchive(File archiveFile, File directory) throws FileNotFoundException, NotDirectoryException, BadArchiveFormatException;
	
	/**
	 * Scan all files info inside a TAR file
	 * 
	 * @param archiveFile
	 * 				TAR file to scan 
	 * 
	 * @return array of file archived info inside an TAR file
	 * @throws IOException
	 * @throws BadArchiveFormatException 
	 */
	public Infoable[] scan(File archiveFile) throws FileNotFoundException, BadArchiveFormatException;
	
	/**
	 * Extract specific file archived from TAR file
	 * 
	 * @param archiveFile
	 * 				TAR file to extract from 
	 * @throws FileNotFoundException 
	 * @throws NotDirectoryException 
	 */
	public void extract(Infoable fileInfo,  File directory) throws NotDirectoryException, FileNotFoundException;
	
}
