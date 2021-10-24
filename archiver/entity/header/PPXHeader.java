package archiver.entity.header;

import java.io.File;

import archiver.entity.header.fields.*;
import archiver.exceptions.BadArchiveFormatException;

/**
 * 
 * @author Ahmad Alsaytari <saytari.dev@gmail.com>
 *
 *
 */
public class PPXHeader extends Header {
		
	/**
	 * Create Pre_POSIX header record from file
	 * 
	 */
	public PPXHeader(File file) {
		super(file);
	}
	
	/**
	 * Create Pre_POSIX header record form bytes array
	 * 
	 */
	public PPXHeader(byte[] record) throws BadArchiveFormatException {
		super(record);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void fill() {
		fields.add(new FileName());
		fields.add(new FileMode());
		fields.add(new OwnerID());
		fields.add(new GroupID());
		fields.add(new FileSize());
		fields.add(new LastModify());
		fields.add(new CheckSum(fields));
		fields.add(new LinkIndictor());
		fields.add(new LinkFileName());
		fields.add(new Pad(254, SIZE - 254));
	}
}
