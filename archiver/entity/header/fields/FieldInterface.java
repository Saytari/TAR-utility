package archiver.entity.header.fields;

import java.io.File;

import archiver.exceptions.BadArchiveFormatException;

/**
 * 
 * @author Ahmad Alsaytari <saytari.dev@gmail.com>
 *
 */
public interface FieldInterface {

	/**
	 * Analyze and format information from entity header 
	 * 
	 * @param headerRecord
	 * 				Entity header record to analyze
	 */
	public void analyze(byte[] headerRecord) throws BadArchiveFormatException;
	
	/**
	 * Analyze and format information from regular file
	 * 
	 * @param fileToAnalyze
	 * 				File to analyze
	 */
	public void analyze(File fileToAnalyze);
	
	/**
	 * Fill array with field bytes in the field pre_
	 * calculated position
	 * 
	 * @param headerRecord
	 * 				Array to fill
	 */
	public void fill(byte[] headerRecord);
	
}
