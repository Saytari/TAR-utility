package archiver.entity.header.fields;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import archiver.exceptions.BadArchiveFormatException;
import archiver.helper.Formater;

public class CheckSum extends Field {
	
	/**
	 * The other fields in the same header
	 */
	List<FieldInterface> fields;
	
	/**
	 * Create new instance
	 */
	public CheckSum(List<FieldInterface> fields) {
		super(8, 148);
		this.fields = fields;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void analyze(File fileToAnalyze) {
		// do nothing
	}
	
	/**
	 * 
	 * @throws BadArchiveFormatException
	 */
	public void validate() throws BadArchiveFormatException {
		String before = Formater.toString(buffer);
		
		calculate();
		
		String after = Formater.toString(buffer);

		if(!before.equals(after))
			throw new BadArchiveFormatException("File are damaged");
	}
	
	/**
	 * Calculate checksum field
	 * 
	 * @param fields
	 * 			Fields incorporate in sum
	 */
	public void calculate() {
		// initialize checksum buffer with space character
		Arrays.fill(buffer, (byte) SPACE);
		
		byte[] fieldsBytes = new byte[512];
		
		for (FieldInterface field : fields)
			 field.fill(fieldsBytes);
		
		int sum = getSum(fieldsBytes);
		
		String field = Formater.toOctal(sum);
		
		buffer = Formater.toBytes(Formater.pad(field + TERMINAL, LENGTH - 1) + SPACE);
	}
	
	/**
	 * Sum and return bytes
	 * 
	 * @param fields
	 * 			Fields to sum
	 * @return
	 * 			Total bytes sum
	 */
	protected int getSum(byte[] bytes) {
		int bytesSum = 0;
		for (byte oneByte : bytes)
			bytesSum += oneByte;
		return bytesSum;
	}
}