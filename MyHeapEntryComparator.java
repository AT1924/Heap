package heap;

import java.util.Comparator;

/**
 * Implements the Comparator interface to compare two given MyHeapentries
 */
public class MyHeapEntryComparator implements Comparator<MyHeapEntry> {

	/**
	 * Returns a negative number, zero, or a positive number if the first
	 * argument is less than, equal to or greater than the second argument
	 * respectively.
	 */
	@Override
	public int compare(MyHeapEntry e1, MyHeapEntry e2) {
		return (int) e1.getKey() - (int) e2.getKey();
	}

}
