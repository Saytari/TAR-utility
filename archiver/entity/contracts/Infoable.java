package archiver.entity.contracts;

/**
 * Handle information messages about an entry inside TAR file
 * 
 * @author Ahmad Alsaytari <saytari.dev@gmail.com>
 *
 * @version 1.0.0
 */
public interface Infoable {
	/**
	 * Return the file name represented by this entity instance
	 * 
	 * @return
	 * 		File name include extension
	 */
	public String getFileName();
	
	/**
	 * Return the file size represented by this entity instance
	 *  
	 * @return
	 * 		File size in bytes
	 */
	public long getFileSize();
}
