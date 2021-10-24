package archiver.entity.header;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

import archiver.entity.Record;
import archiver.entity.contracts.Infoable;
import archiver.entity.header.fields.*;
import archiver.entity.header.fields.FieldInterface;
import archiver.exceptions.BadArchiveFormatException;

/**
 * An entity header record utility
 * 
 * 
 * @author Ahmad Alsaytari <saytari.dev@gmail.com>
 *
 */
public abstract class Header extends Record implements Infoable {
	
	/**
	 * Header record fields
	 * 
	 */
	protected List<FieldInterface> fields;
	
	/**
	 * Create new header
	 * 
	 */
	public Header() {
		fields = new ArrayList<FieldInterface>();
		fill();
	}
	
	public Header(File file) {
		this();
		analyzeFile(file);
	}
	
	public Header(byte[] record) throws BadArchiveFormatException {
		this();
		analyzeBytes(record);
	}
	
	/**
	 * Resolve file metadata inside header fields objects
	 * Include checksum field
	 * 
	 */
	 protected void analyzeFile(File file) {
		 // Tell each field to fill it's content
		 for (FieldInterface field : fields)
			 field.analyze(file);
	 }
	 
	/**
	 * Resolve file metadata inside header fields objects
	 * Include checksum field
	 * 
	 */
	 protected void analyzeBytes(byte[] record) throws BadArchiveFormatException {
		 // Tell each field to fill it's content
		 for (FieldInterface field : fields)
			 field.analyze(record);
		// Validate checksum
		 for (FieldInterface field : fields)
			 if(field instanceof CheckSum)
				 ((CheckSum) field).validate();
	 }
	 
	 /**
	  * Prepare checksum header field 
	  */
	 protected void prepareChecksum(List<FieldInterface> fields) {
		// find checksum field and calculate its value
		 for (FieldInterface field : fields)
			 if(field instanceof CheckSum)
				 ((CheckSum) field).calculate();
	 }
	 
	/**
	 * Return header bytes 
	 * 
	 * 
	 * @return
	 * 		Byte array represent header
	 */
	 protected void generate() {		
		 // Calculate and fill checksum field
		 prepareChecksum(fields);
		 
		 // Fill record with header fields
		 fillArrayWithFields(buffer);
	 }
	 
	 /**
	  * 
	  * @param array
	  */
	 protected void fillArrayWithFields(byte[] array) {
		 for (FieldInterface field : fields)
			 field.fill(array);
	 }
	 
	 /**
	  * 
	  * @param directory
	  * @throws BadArchiveFormatException
	  */
	 public void setDirectory(String directory) {
		 for (FieldInterface field : fields)
			 if(field instanceof FileName) 
				 ((FileName) field).prepend(directory);
	 }
	 
	 /**
	  * {@inheritDoc}
	  */
	 @Override
	 public String getFileName() {
		 for (FieldInterface field : fields)
			 if (field instanceof FileName)
				 return ((FileName) field).get();
		 return null;
	 }
	 
	 /**
	  * {@inheritDoc}
	  */
	 @Override
	 public long getFileSize() {
		 
		 for (FieldInterface field : fields)
			 if(field instanceof FileSize)
				 return ((FileSize) field).get();
		 return 0;
	 }
	 /**
	  * {@inheritDoc}
	  */
	 @Override
     public void combineWith(File archiveFile) throws IOException {
		 generate();
		 super.combineWith(archiveFile);
	 }
	 
	 /**
	  * Fill header fields list
	  */
	 abstract protected void fill();
}