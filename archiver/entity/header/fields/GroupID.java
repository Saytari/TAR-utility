package archiver.entity.header.fields;

import java.io.File;

import archiver.helper.Formater;

public class GroupID extends Field {
	
	/**
	 * Create new group id  field
	 * 
	 */
	public GroupID() {
		super(8, 116);
	}

	@Override
	public void analyze(File fileToAnalyze) {
		
		String field = Formater.pad("" + TERMINAL, LENGTH - 1);
		
		buffer = Formater.toBytes(field + SPACE);
	}
}