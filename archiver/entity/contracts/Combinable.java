package archiver.entity.contracts;

import java.io.File;
import java.io.IOException;

/**
 * Handle entity combination
 * 
 * @author Ahmad Alsaytari <saytari.dev@gmail.com>
 * 
 * @version 1.0.0
 * 
 */
public interface Combinable {
	/**
	 * Combine and fill this instance chunk of data to the 
	 * end of given file
	 * 
	 * @param file
	 * 			file to combine data with
	 * 
	 * @throws IOException
	 */
	public void combineWith(File file) throws IOException ;
}
