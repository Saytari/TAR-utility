package archiver.entity.header.fields;

import java.io.File;

import archiver.helper.Formater;

public class LastModify extends Field {
	
	/**
	 * Create new instance
	 */
	public LastModify() {
		super(12, 136);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void analyze(File fileToAnalyze) {

		String field = Formater.toOctal((int) fileToAnalyze.lastModified());
		buffer = Formater.toBytes(Formater.pad(field + TERMINAL, LENGTH));
	}
}