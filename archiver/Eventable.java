package archiver;

import java.util.function.Function;
import java.util.function.BiFunction;

import archiver.enumerations.State;

/**
 * Every archive strategy should implement this interface
 * it can be use for ZIP, RAR & other archive implementations
 * 
 * @author Ahmad Alsaytari <saytari.dev@gmail.com>
 * 
 * @version 1.0.0
 * 
 * Lambda expressions
 * @see java.util.function.Function
 * @see java.util.function.BiFunction
 */
public interface Eventable {

	/**
	 * Register callback for progress event
	 * 
	 * @param callBack
	 * 				lambda expression calls inside process event
	 */
	public void onProgress(BiFunction<String, Float, Void> callBack);
	
	/**
	 * Register callback for done event
	 * 
	 * @param callBack
	 * 				lambda expression calls when process gets done
	 */
	public void onDone(Function<State, Void> callBack);
}
