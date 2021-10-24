package archiver.entity.contracts;

/**
 * Handle entity extraction
 * 
 * @author Ahmad Alsaytari <saytari.dev@gmail.com>
 *
 * @version 1.0.0
 */
public interface Extractable {
	/**
	 * Create new file and fill this instance chunk of data
	 * inside of it
	 * 
	 * @param FileAbsolutePath
	 * 			File absolute path include its name and extension
	 */
	public void extractTo(String FileAbsolutePath);
}
