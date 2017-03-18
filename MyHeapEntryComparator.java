package heap;

import java.util.Comparator;

/**
 * Implements the Comparator interface to compare two given MyHeapentries
 * 
 * @param <K>
 * @param <V>
 */
public class MyHeapEntryComparator<K> implements Comparator<K> {

	/**
	 * Returns a negative number, zero, or a positive number if the first
	 * argument is less than, equal to or greater than the second argument
	 * respectively.
	 */
	@Override
	public int compare(K o1, K o2) {
		return (int) o1 - (int) o2;
	}
}
