package archiver.entity;

import java.io.File;
import java.io.IOException;

import archiver.entity.contracts.Combinable;
import archiver.entity.contracts.Extractable;
import archiver.entity.contracts.Infoable;
import archiver.entity.header.Header;

/**
 * Represents file header and data blocks inside TAR file 
 * 
 * 
 * @author Ahmad Alsaytari <saytari.dev@gmail.com>
 *
 * @version 1.0.0
 */
public class Entity implements Combinable, Extractable, Infoable {

	/**
	 * Entity meta data
	 * 
	 */
	protected Header header;
	
	/**
	 * Entity file data
	 * 
	 */
	protected Data[] data;
	
	/**
	 * Create new entity instance and create its data record
	 * 
	 * @param header
	 * 			Entity header
	 * @param file
	 * 			File to create data records from
	 * @param dataStart
	 * 			Data start offset inside file
	 * @param dataEnd
	 * 			Data end offset inside file
	 */
	protected Entity(Header header, File file, int dataStart, int dataEnd) {
		this.header = header;
		if(dataStart != dataEnd)
			this.data = splitFile(file, dataStart, dataEnd);
	}
	
	/**
	 * Create new entity from regular file
	 * 
	 * @param archiveFile
	 * 				file to seek entity from
	 * @param offset
	 * 				entity offset inside archive file
	 */
	public Entity(Header header, File regularFile) {
		this(header, regularFile, 0, (int) (regularFile.length() - 1));
	}
	
	/**
	 * Create new entity from archive
	 * 
	 * @param archiveFile
	 * 				file to seek entity from
	 * @param offset
	 * 				entity offset inside archive file
	 */
	public Entity(Header header, File archiveFile, int offset)
	{
		this(header, archiveFile, offset, offset + (int) header.getFileSize());
	}

	/**
	 * Split file content to bunch of records
	 * 
	 * @param fileToSplite
	 * 				File that going to be split
	 * @param start
	 * 				File data start offset
	 * @param end
	 * 				File data end offset
	 */
	protected Data[] splitFile(File fileToSplit, int start, int end) {
		// Calculate records count
		int recordsCount = (int) Math.ceil((end - start + 1) / (float) 512);
		// Initialize data records count
		Data[] dataRecords = new Data[recordsCount];
		
		// Create records instance
		for (int recordIndex = 0; recordIndex < recordsCount; recordIndex++)
			dataRecords[recordIndex] = new Data(fileToSplit, (recordIndex * 512) + start);
		
		return dataRecords;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void combineWith(File archiveFile) throws IOException
	{
		header.combineWith(archiveFile);
		for (Data record : data)
			record.combineWith(archiveFile);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void extractTo(String path) {
		if(getFileSize() != 0) {
			try {
				buildFile(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			createDirectory(path);
		}
			
		
	}
	
	/**
	 * Create the file represented by this entity & fill it
	 * with its original content
	 * 
	 * @param filePath
	 * 			File absolute path
	 * 
	 * @throws IOException
	 */
	protected void buildFile(String filePath) throws IOException {
		File file = new File(filePath + "\\" + getFileName());
		file.createNewFile();
		fillFile(file);
	}
	
	/**
	 * Fill file by the data of this entity
	 * 
	 * @param file
	 * 			File to fill
	 * 
	 * @throws IOException
	 */
	protected void fillFile(File file) throws IOException {
		// Fill all data records file
		for (int recordIndex = 0; recordIndex < data.length - 1; recordIndex++)
			data[recordIndex].combineWith(file);
		
		// Last record may not be combine totally
		data[data.length - 1].combineWith(file,(int) getFileSize() % 512);
	}
	
	/**
	 * Create directory represented by this entity
	 * 
	 * @param filePath
	 * 			directory absolute path
	 * 
	 * @throws IOException
	 */
	protected void createDirectory(String directoryPath) {
		File file = new File(directoryPath + "\\" + getFileName());
		file.mkdir();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getFileName() {
		return header.getFileName();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getFileSize() {
		return header.getFileSize();
	}
	
	/**
	 * Calculate total data records size
	 * 
	 * @return 
	 * 			Data size
	 */
	public int length() {
		return data != null ? 512 * data.length : 0;
	}
}

