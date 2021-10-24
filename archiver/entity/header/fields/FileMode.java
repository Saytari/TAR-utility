package archiver.entity.header.fields;

import java.io.File;

import archiver.helper.Formater;

public class FileMode extends Field {
	
	/**
	 * Create new instance
	 */
	public FileMode() {
		super(8, 100);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void analyze(File fileToAnalyze) {
		// Gather file mode value
		String field = Formater.pad(getMode(fileToAnalyze), LENGTH - 2);
		
		// Save to buffer
		buffer = Formater.toBytes(field + TERMINAL + SPACE);
	}
	
	protected String getMode(File file) {
		if(file.isDirectory())
			return "777";
		return "666";
	}
	
}