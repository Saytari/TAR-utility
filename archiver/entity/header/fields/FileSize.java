package archiver.entity.header.fields;

import java.io.File;

import archiver.helper.Formater;

public class FileSize extends Field {
	
	/**
	 * Create new instance
	 */
	public FileSize() {
		super(12, 124);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void analyze(File fileToAnalyze) {	
		// Gather file size
		int size = (int) (fileToAnalyze.isDirectory() ? 0 : fileToAnalyze.length());
		
		// Calculate octal string with terminal
		String field = Formater.toOctal(size) + TERMINAL;
		
		// Pad field value and store it
		buffer = Formater.toBytes(Formater.pad(field, LENGTH));
	}
	
	public int get() {
		byte[] size = new byte[buffer.length - 1];
		
		for (int byteSize = 0; byteSize < size.length; byteSize++)
			size[byteSize] = buffer[byteSize];

		return Integer.decode(new String(size));
	}
}