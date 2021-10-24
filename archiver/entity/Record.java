package archiver.entity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import archiver.entity.contracts.Combinable;

/**
 * TAR archive record
 * 
 * 
 * @author Ahmad Alsaytari <saytari.dev@gmail.com>
 *
 * @version 1.0.0
 * 
 */
public class Record implements Combinable {
	
	/**
	 * TAR standard record size
	 */
	protected final static int SIZE = 512;
	
	/**
	 * Contents in bytes
	 */
	protected byte[] buffer;
	
	/**
	 * Offset inside TAR file
	 */
	protected int offset;
	
	/**
	 * Create new record instance
	 */
	public Record() {
		buffer = new byte[SIZE];
	}

	/**
	 * Combine bytes with an file
	 * 
	 * @param archiveFile
	 * 			TAR file to combine record with
	 * @param count
	 * 			Bytes count to write
	 * 
	 * @throws IOException
	 */
	public void combineWith(File archiveFile, int count) throws IOException {
		// Copy bytes to write form original bytes array
		byte[] bufferToWrite = Arrays.copyOfRange(buffer, 0, count);
		
		// fill it inside output file
		try (FileOutputStream output = new FileOutputStream(archiveFile, true)) {
			output.write(bufferToWrite);			
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void combineWith(File archiveFile) throws IOException {
		combineWith(archiveFile, SIZE);
	}
}
