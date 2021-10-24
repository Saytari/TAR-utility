package archiver.entity.header.fields;

import java.io.File;

import archiver.helper.Formater;

public class OwnerID extends Field {
	
	/**
	 * Create new instance
	 */
	public OwnerID() {
		super(8, 108);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void analyze(File fileToAnalyze) {

		String field = Formater.pad("" + TERMINAL, LENGTH - 1);
		
		buffer = Formater.toBytes(field + SPACE);
	}
}