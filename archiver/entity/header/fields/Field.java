package archiver.entity.header.fields;

import archiver.exceptions.BadArchiveFormatException;
/**
 * 
 * @author Ahmad Alsaytari <saytari.dev@gmail.com>
 *
 */
public abstract class Field implements FieldInterface {

	/**
	 * Field bytes array length
	 */
	protected final int LENGTH;
	
	/**
	 * Field position
	 */
	protected final int POSITION;
	
	/**
	 * Field bytes
	 */
	protected byte[] buffer;
	
	/**
	 * Terminal character
	 */
	protected final char TERMINAL = '\0';
	
	/**
	 * Space character
	 */
	protected final char SPACE = ' ';
	
	/**
	 * Directory separator character
	 */
	protected final char SEPARATOR = '/';
	
	/**
	 * Create new field instance
	 * 
	 * @param LENGTH
	 * 			Field length in header
	 * @param POSITION
	 * 			Field data position in bytes array
	 */
	public Field(final int LENGTH, final int POSITION) {
		this.LENGTH = LENGTH;
		this.POSITION = POSITION;
		this.buffer = new byte[LENGTH];
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void analyze(byte[] headerRecord) throws BadArchiveFormatException {
		for (int byteIndex = 0; byteIndex < LENGTH; byteIndex++)
			buffer[byteIndex] = headerRecord[byteIndex + POSITION];
		validate(buffer);
	}
	
	/**
	 * {@inheritDoc}
	 * @throws BadArchiveFormatException 
	 */
	@Override
	public void fill(byte[] headerRecord){
		for (int byteIndex = 0; byteIndex < LENGTH; byteIndex++)
			headerRecord[POSITION + byteIndex] = buffer[byteIndex];
		//validate(buffer);
	}
	
	protected void validate(byte[] bytes) throws BadArchiveFormatException {
		// Implementation is in the fields that should be validated
	};
}
