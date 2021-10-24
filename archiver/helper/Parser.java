package archiver.helper;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import archiver.entity.Entity;
import archiver.entity.header.PPXHeader;
import archiver.exceptions.BadArchiveFormatException;
import archiver.entity.contracts.Combinable;
import archiver.entity.contracts.Extractable;

/**
 * Work as helper for TAR implementation, needed to parse TAR file
 * and extract entities from it or convert normal files to its entities
 * for building process
 * 
 * @author Ahmad Alsaytari <saytari.dev@gmail.com>
 * 
 * @version 1.0.0
 * 
 * @see archiver.entity.Entity
 * @see archiver.entity.header.PPXHeader
 * @see archiver.entity.contracts.Combinable
 * @see archiver.entity.contracts.Extractable
 */
public class Parser {

	/**
	 * Parse multiple files and prepare them for combination process
	 * 
	 * 
	 * @param files
	 * 			files that going to wrapped inside blocks
	 * @return
	 * 			objects that going to build the TAR File
	 */
	public static Combinable[] parse(File[] files) {
		
		ArrayList<Combinable> entities = new ArrayList<Combinable>();
		
		buildEntities(entities, files);
					
		return entities.toArray(new Combinable[entities.size()]);
	}
	
	/** 
	 * Convert provided files to entities and fill them inside
	 * 
	 * an empty list
	 * 
	 * @param list
	 * 			List to fill
	 * @param files
	 * 			Files to convert
	 * 
	 * @throws BadArchiveFormatException
	 */
	protected static void buildEntities(ArrayList<Combinable> list, File[] files) {
		for (int fileIndex = 0; fileIndex < files.length; ++fileIndex)
			if (files[fileIndex].isDirectory())
				list.addAll(gatherEntities(files[fileIndex], ""));
			else
				list.add(new Entity(new PPXHeader(files[fileIndex]), files[fileIndex]));
	}
	
	/**
	 * Spread inside an directory and convert every possible
	 * file inside of it to an entity, collect and return them
	 *  
	 * @param file
	 * 			File to Spread inside
	 * 
	 * @return
	 * 		All files entities
	 */
	protected static ArrayList<Combinable> gatherEntities(File file, String path) {
		ArrayList<Combinable> all = new ArrayList<Combinable>();
		
		Entity entity = createEntity(file, path);
		
		all.add(entity);
		
		if (file.isDirectory())
			for(File child : file.listFiles())
				all.addAll(gatherEntities(child, entity.getFileName()));
	
		return all;
	}
	
	/**
	 * Parse archive files and prepare it for extraction process
	 * 
	 * 
	 * @param archiveFile
	 * 			TAR File that going to be unarchived
	 * @return
	 * 			objects that represent files in archive
	 */
	public static Extractable[] parse(File archiveFile) throws BadArchiveFormatException {
		
		Extractable[] entities = parseArchive(archiveFile);
		
		return entities;
	}
	
	protected static Extractable[] parseArchive(File archiveFile) {
		// The offset of the first empty record in archive
		final long END_OF_RECORDS = archiveFile.length() - 1023;
		
		// List for extracted entities
		ArrayList<Extractable> entities = new ArrayList<Extractable>();
		
		// Fill entities list
		fillEntities(entities, archiveFile, END_OF_RECORDS);
		
		return entities.toArray(new Extractable[entities.size()]);
	}
	
	/**
	 * Extract entities from TAR file and fill them inside given list
	 * 
	 * @param entities
	 * 			List to fill
	 * @param archiveFile
	 * 			TAR file
	 * @param EOF
	 * 			The end of last Record
	 */
	protected static void fillEntities(
	ArrayList<Extractable> list,
	File archiveFile,
	final long EOF
	) {
		// A Record buffer
		byte[] buffer = new byte[512];
		
		try (RandomAccessFile archive = new RandomAccessFile(archiveFile, "r");) {
			while(archive.read(buffer) != -1 && archive.getFilePointer() < EOF) {
				Entity extractedEntity = createEntity(buffer, archiveFile, (int) archive.getFilePointer());
				list.add(extractedEntity);
				archive.seek(archive.getFilePointer() + extractedEntity.length());
			}	
		} catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
 	/**
	 * Factory method handles entity creation from normal file
	 * 
	 * @param file
	 * 			File to create entity from
	 * @param prefix
	 * 			Entity header file name prefix
	 * @return
	 */
	protected static Entity createEntity(File file, String prefix) {
		
		PPXHeader header = new PPXHeader(file);
		
		header.setDirectory(prefix);
		
		Entity entity = new Entity(header, file);
		
		return entity;
	}
	
	/**
	 * Factory method handles entity creation from archive file
	 * 
	 * @param file
	 * 			File to create entity from
	 * @param prefix
	 * 			Entity header file name prefix
	 * @return
	 */
	protected static Entity createEntity(byte[] headerBytes, File archiveFile, int entityDataOffset) throws BadArchiveFormatException {
		
		PPXHeader header = new PPXHeader(headerBytes);
		
		Entity entity = new Entity(header, archiveFile, entityDataOffset);
		
		return entity;
	}
}
