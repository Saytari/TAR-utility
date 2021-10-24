package archiver.entity.header.fields;

import java.io.File;

public class Pad extends Field{

	/**
	 * Create new instance
	 */
	public Pad(int length, int offset) {
		super(length, offset);
	}

	@Override
	public void analyze(File fileToAnalyze) {
		// do nothing
	}
}
