package archiver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.InputMismatchException;
import java.util.function.BiFunction;
import java.util.function.Function;

import archiver.entity.Record;
import archiver.entity.contracts.Combinable;
import archiver.entity.contracts.Extractable;
import archiver.entity.contracts.Infoable;
import archiver.enumerations.State;
import archiver.exceptions.BadArchiveFormatException;
import archiver.helper.Parser;
import archiver.helper.Validator;

/**
 * TAR archive implementation 
 * 
 * 
 * @author Joudy_Alhumairy <Joudy.Alhumairy@gmail.com>
 *
 * @version 1.0.0
 * 
 * {@link} www.wikipedia.com/tar-format
 * 
 * @see archiver.Archiveable
 * @see archiver.Eventable
 */
public class TAR implements Archiveable, Eventable {
	
	/**
	 * Archive file extension
	 */
	protected final static String EXTENSION = ".tar";
	
	/**
	 * Progress event callback
	 */
	protected BiFunction<String, Float, Void> progressCallback;
	
	/**
	 * Done event callback
	 */
	protected Function<State, Void> doneCallback;
	
	/**
	 * {@inheritDoc}
	 * @throws BadArchiveFormatException 
	 */
	@Override
	public void archive(File[] files, File directory, String archiveName) throws FileNotFoundException, NotDirectoryException, InputMismatchException {
		// Validate parameters
		Validator.exist(files);
		Validator.directory(directory);
		Validator.archiveName(archiveName);
		
		// Convert files to records
		Combinable[] entities = Parser.parse(files);
					
		try {
			// Create empty archive file
			File archiveFile = createNewArchiveFile(directory, archiveName);
			
			// Combine every file block with the created archive file
			startCombineProcess(entities, archiveFile);
			
			// Done callback
			done(State.Success);
		} catch(Exception exception) {
			exception.printStackTrace();
			// Done callback
			done(State.Failed);
		}
	}

	/**
	 * Create new empty TAR file, called when new archive
	 * process takes place
	 * 
	 * @param directory
	 * 				Creation directory
	 * @param fileName
	 * 				TAR file name
	 * @return
	 * 		        Created TAR
	 * 
	 * @throws IOException
	 */
	protected File createNewArchiveFile(File directory, String fileName) throws IOException {
		// 
		File dumpFile = new File(directory.getAbsolutePath() + "\\" + fileName + EXTENSION);
		
		dumpFile.createNewFile();
		
		return dumpFile;
	}
	
	/**
	 * Combine records array with TAR file and call progress
	 * callback through combining
	 * 
	 * @param records
	 * 			records to combine
	 * @param archiveFile
	 * 			TAR file
	 * 
	 * @throws IOException
	 */
	protected void startCombineProcess(Combinable[] entities, File archiveFile) throws IOException {
		// Merge every created entity with TAR file
		for (Combinable entity : entities) {
			// Progress event takes place
			// progress event
			progress(((Infoable) entity).getFileName(), 1);
			
			// Actual combination
			entity.combineWith(archiveFile);
		}
		
		// Add TAR empty records trailer
		// first empty record
		(new Record()).combineWith(archiveFile);
		// second empty record
		(new Record()).combineWith(archiveFile);
	}
	
	/**
	 * {@inheritDoc}
	 */	
	@Override
	public void unarchive(File archiveFile, File directory) throws FileNotFoundException, NotDirectoryException, BadArchiveFormatException {
		// Validate parameters
		Validator.exist(archiveFile);
		Validator.exist(directory);
		Validator.archiveFile(archiveFile);
		Validator.directory(directory);
		
		// Extract files archived entities from TAR
		Extractable[] entities = Parser.parse(archiveFile);
		
		try {
			// Convert every entity to its original file
			startExtractProcess(entities, directory);
			
			// Done callback
			done(State.Success);
		} catch(Exception exception) {
			exception.printStackTrace();
			// Done callback
			done(State.Failed);
		}
	}

	/**
	 * Loop through entities and create & build its original files
	 * into an directory and call progress callback through extracting
	 * 
	 * @param entities
	 * 			entities to build from
	 * @param directory
	 * 			an destination directory
	 * 
	 * @throws IOException
	 */
	protected void startExtractProcess(Extractable[] entities, File directory) throws IOException {
		// Convert every resolve entity to its original file
		for (Extractable entity : entities) {
			// progress event
			progress(((Infoable) entity).getFileName(), 1);
			
			// Actual extraction
			entity.extractTo(directory.getAbsolutePath());
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @throws BadArchiveFormatException 
	 */
	@Override
	public Infoable[] scan(File archiveFile) throws FileNotFoundException, BadArchiveFormatException {
		// Validation layer
		Validator.exist(archiveFile);
		Validator.archiveFile(archiveFile);
		
		// Gather TAR files archived info
		Extractable[] entities = Parser.parse(archiveFile);
		
		Infoable[] infos = new Infoable[entities.length];
		
		for (int entityIndex = 0; entityIndex < entities.length; entityIndex++)
			infos[entityIndex] = (Infoable) entities[entityIndex];
		
		return infos;
	}
	
	/**
	 * {@inheritDoc} 
	 * @throws NotDirectoryException 
	 * @throws FileNotFoundException 
	 */
	@Override
	public void extract(Infoable fileInfo, File directory) throws NotDirectoryException, FileNotFoundException {
		Validator.exist(directory);
		Validator.directory(directory);
		((Extractable) fileInfo).extractTo(directory.getAbsolutePath());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onProgress(BiFunction<String, Float, Void> callBack) {
		progressCallback = callBack;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onDone(Function<State, Void> callBack) {
		doneCallback = callBack;
	}
	
	/**
	 * Encapsulate progress callback invoke
	 * 
	 * @param fileName
	 * 			Current processing file
	 * @param percent
	 * 			Processing progress in percentage
	 */
	protected void progress(String fileName, float percent) {
		if(progressCallback != null)
			progressCallback.apply(fileName, percent);
	}
	
	/**
	 * Encapsulate done callback invoke
	 * 
	 * @param state
	 * 			Process end state
	 */
	protected void done(State state) {
		if(doneCallback != null)
			doneCallback.apply(state);
	}
}
