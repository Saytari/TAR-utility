package archiver.entity.header.fields;

import java.io.File;

import archiver.exceptions.BadArchiveFormatException;
import archiver.helper.Formater;

/**
 * 
 * @author Ahmad Alsaytari <saytari.dev@gmail.com>
 *
 */
public class FileName extends Field {
	
	/**
	 * Create new instance
	 */
	public FileName() {
		super(100, 0);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void analyze(File fileToAnalyze) {
		// Gather file name
		String fileName = fileToAnalyze.getName();
		
		// If the file is directory add separator
		if(fileToAnalyze.isDirectory())
			fileName += SEPARATOR;
		
		// Convert string to bytes
		buffer = Formater.toBytes(Formater.rightPad(fileName, LENGTH, TERMINAL));
	}

	/**
	 * Validate entity file name
	 * 
	 * {@inheritDoc}
	 */
	@Override
	protected void validate(byte[] bytes) throws BadArchiveFormatException {
		String nameToTest = Formater.toString(bytes);
		
		if(nameToTest.length() == 0)
			throw new BadArchiveFormatException("File name are missed");
	}
	
	/**
	 * 
	 * @return
	 */
	public String get() {
		return Formater.toString(buffer);
	}
	
	/**
	 * 
	 * @param path
	 */
	public void prepend(String path) {
		// Gather file name
		String fileName = path + get();
		// Convert string to bytes
		buffer = Formater.toBytes(Formater.rightPad(fileName, LENGTH, TERMINAL));
	}
}