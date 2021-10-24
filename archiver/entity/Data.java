package archiver.entity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * An entity data record utility
 * 
 * 
 * @author Ahmad Alsaytari <saytari.dev@gmail.com>
 *
 */
public class Data extends Record {
	
	/**
	 * File to seek data record from
	 */
	protected File file;
	
	/**
	 * Record offset inside archive file
	 */
	protected int offset;
	
	/**
	 * Create new Data instance from normal file
	 */
	public Data(File file, int offset) {
		this.file = file;
		try {
			buffer = getDataRecord(file, offset);
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Get record data content from archive file with specific offset
	 * 
	 * @param archiveFile
	 * 			Archive file to scan on
	 * @param offset
	 * 			Record offset inside file
	 * @return
	 * 			Record as bytes
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	protected byte[] getDataRecord(File archiveFile, int offset) throws FileNotFoundException, IOException {
		byte[] readBytes = new byte[SIZE];
 		try (RandomAccessFile input = new RandomAccessFile(archiveFile, "r")) {
			input.seek(offset);
			input.read(readBytes);
			return readBytes;
		} 
	}
	
}
