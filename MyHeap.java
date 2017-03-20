package heap;

import java.util.Comparator;
import net.datastructures.CompleteBinaryTree;
import net.datastructures.EmptyPriorityQueueException;
import net.datastructures.Entry;
import net.datastructures.InvalidEntryException;
import net.datastructures.InvalidKeyException;
import net.datastructures.Position;
import net.datastructures.AdaptablePriorityQueue;
import net.datastructures.BoundaryViolationException;
import support.heap.HeapWrapper;

/**
 * An implementation of an adaptable priority queue by means of a heap. Be
 * certain that your running times match those specified in the program
 * documentation, and remember that the running time of a "called" method sets
 * the minimum running time of the "calling" method. Feel free to add additional
 * comments.
 * 
 * This class implements all of the stubbed methods using helper methods that provide 
 * easy access to entries keys and values. The helper methods also allow for sorting in order to
 * maintain the min heap structure where each parent's key is smaller than that of it's child
 */

public class MyHeap<K, V> implements HeapWrapper<K, V>, AdaptablePriorityQueue<K, V> {

	// This the underlying data structure of your heap
	private MyLinkedHeapTree<MyHeapEntry<K, V>> _tree;
	private Comparator<K> _comparator;

	/**
	 * Creates an empty heap with the given comparator.
	 * 
	 * @param the
	 *            comparator to be used for heap keys
	 */
	public MyHeap(Comparator<K> comparator) {
		_tree = new MyLinkedHeapTree<MyHeapEntry<K, V>>();
		_comparator = comparator;
	}

	/**
	 * Sets the comparator used for comparing items in the heap to the
	 * comparator passed in.
	 * 
	 * @param comparator
	 *            the comparator to be used for heap keys
	 * @throws IllegalStateException
	 *             if priority queue is not empty
	 * @throws IllegalArgumentException
	 *             if null comparator is passed in
	 */
	public void setComparator(Comparator<K> comparator) throws IllegalStateException, IllegalArgumentException {
		if (!isEmpty()) {
			throw new IllegalStateException("priority queue is not empty");
		}
		if (comparator == null) {
			throw new IllegalArgumentException("null comparator");
		}
	}

	/**
	 * Returns a CompleteBinaryTree that will allow the visualizer access to
	 * private members, shattering encapsulation, but allowing visualization of
	 * the heap. This is the only method needed to satisfy HeapWrapper interface
	 * implementation.
	 *
	 * Do not modify or call this method. It is solely necessary for the
	 * visualizer to work properly.
	 * 
	 * @return the underlying binary tree on which the heap is based
	 */
	public CompleteBinaryTree<MyHeapEntry<K, V>> getTree() {
		return _tree;
	}

	/**
	 * Returns the size of the heap. This method must run in O(1) time.
	 *
	 * @return an int representing the number of entries stored
	 */
	public int size() {
		// as size of the heap is equal to the size of the tree
		// return the size of the tree
		return _tree.size();

	}

	/**
	 * Returns whether the heap is empty. This method must run in O(1) time.
	 * 
	 * @return true if the heap is empty; false otherwise
	 */
	public boolean isEmpty() {
		if (_tree.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns but does not remove an entry with minimum key. This method must
	 * run in O(1) time.
	 * 
	 * @return the entry with the minimum key in the heap
	 * @throws EmptyPriorityQueueException
	 *             if the heap is empty
	 */
	public Entry<K, V> min() throws EmptyPriorityQueueException {
		
		if (isEmpty()) {
			throw new EmptyPriorityQueueException("heap is empty");
		}
		// due to min heap structure the minimum value will always be the root
		return _tree.root().element();
	}

	/**
	 * Inserts a key-value pair and returns the entry created. This method must
	 * run in O(log n) time.
	 *
	 * @param key
	 *            to be used as the key the heap is sorting with
	 * @param value
	 *            stored with the associated key in the heap
	 * @return the entry created using the key/value parameters
	 * @throws InvalidKeyException
	 *             if the key is not suitable for this heap
	 */
	public Entry<K, V> insert(K key, V value) throws InvalidKeyException {
		if ((int) key < 1 || (int) key > 99) {
			throw new InvalidKeyException("key out of appropriate range");
		}

		MyHeapEntry<K, V> entry = new MyHeapEntry<K, V>(key, value);
		// in order to allow for O(logn) runtime manipulation of the tree set entry's position
		entry.set_position(_tree.add(entry));
		// check to see if the inserted entry has child whose key is larger than its own, if so swap
		return upSort(entry.get_position()).element();
		
	}

	/**
	 * Removes and returns an entry with minimum key. This method must run in
	 * O(log n) time.
	 * 
	 * @return the entry with the with the minimum key, now removed
	 * @throws EmptyPriorityQueueException
	 *             if the heap is empty
	 */
	public Entry<K, V> removeMin() throws EmptyPriorityQueueException {
		// do not have to throw exception because calling min() checks for an
		// empty heap and throws it
		return remove(min());
	}

	/**
	 * Removes and returns the given entry from the heap. This method must run
	 * in O(log n) time.
	 *
	 * @param entry
	 *            to be removed from the heap
	 * @return the entry specified for removal by the parameter
	 * @throws InvalidEntryException
	 *             if the entry cannot be removed from this heap
	 */
	public Entry<K, V> remove(Entry<K, V> entry) throws InvalidEntryException {
		MyHeapEntry<K, V> checkedEntry = this.checkAndConvertEntry(entry);

		if (isEmpty() || checkedEntry.get_position() == null) {
			throw new InvalidEntryException("entry not in tree");
		}

		// prune last node of tree
		MyHeapEntry<K, V> last = _tree.remove();

		if (_tree.size() > 0) {
			swapValuesandKeys(checkedEntry, last);
			downSort(checkedEntry.get_position());
			upSort(checkedEntry.get_position());
		}

		return last;
	}

	/**
	 * Replaces the key of the given entry. This method must run in O(log n)
	 * time.
	 *
	 * @param entry
	 *            within which the key will be replaced
	 * @param key
	 *            to replace the existing key in the entry
	 * @return the old key formerly associated with the entry
	 * @throws InvalidEntryException
	 *             if the entry is invalid
	 * @throws InvalidKeyException
	 *             if the key is invalid
	 */
	public K replaceKey(Entry<K, V> entry, K key) throws InvalidEntryException, InvalidKeyException {
		// throws InvalidEntryException when necessary
		MyHeapEntry<K, V> checkedEntry = this.checkAndConvertEntry(entry);
		if ((int) key < 1 || (int) key > 99) {
			throw new InvalidKeyException("key is not in appropriate range");
		}

		K oldKey = checkedEntry.getKey();
		checkedEntry.setKey(key);

		// sort if new key is larger or smaller than child
		downSort(checkedEntry.get_position());
		upSort(checkedEntry.get_position());

		return oldKey;
	}

	/**
	 * Replaces the value of the given entry. This method must run in O(1) time.
	 *
	 * @param entry
	 *            within which the value will be replaced
	 * @param value
	 *            to replace the existing value in the entry
	 * @return the old value formerly associated with the entry
	 * @throws InvalidEntryException
	 *             if the entry cannot have its value replaced
	 */
	public V replaceValue(Entry<K, V> entry, V value) throws InvalidEntryException {
		// throws InvalidEntryException if needed
		MyHeapEntry<K, V> checkedEntry = this.checkAndConvertEntry(entry);

		V oldVal = checkedEntry.getValue();
		checkedEntry.setValue(value);

		return oldVal;
	}

	/**
	 * Determines whether a given entry is valid and converts it to a
	 * MyHeapEntry. Don't change this method.
	 *
	 * @param entry
	 *            to be checked for validity with respect to the heap
	 * @return the entry cast as a MyHeapEntry if considered valid
	 *
	 * @throws InvalidEntryException
	 *             if the entry is not of the proper class
	 */
	public MyHeapEntry<K, V> checkAndConvertEntry(Entry<K, V> entry) throws InvalidEntryException {
		if (entry == null || !(entry instanceof MyHeapEntry)) {
			throw new InvalidEntryException("Invalid entry");
		}
		return (MyHeapEntry<K, V>) entry;
	}

	/*
	 * You may find it useful to add some helper methods here. Think about
	 * actions that may be executed often in the rest of your code. For example,
	 * checking key validity, upheaping and downheaping, swapping or replacing
	 * elements, etc. Writing helper methods instead of copying and pasting
	 * helps segment your code, makes it easier to understand, and avoids
	 * problems in keeping each occurrence "up-to-date."
	 */

	/**
	 * Reorder a position with respect to it's children. Walk down the tree,
	 * swapping with any child node that has a smaller key.
	 * 
	 * @param position
	 *            a (hopefully) freshly swapped position
	 */
	public void downSort(Position<MyHeapEntry<K, V>> position) {
		Boolean possibleSmallerChild = true;
		while (possibleSmallerChild) {
			// no children left
			if (!_tree.hasLeft(position)) {
				break;
			}

			// has children
			Position<MyHeapEntry<K, V>> smallerPosition;
			if (_tree.hasRight(position) && (_comparator.compare(_tree.right(position).element().getKey(),
					_tree.left(position).element().getKey()) < 0)) {
				smallerPosition = _tree.right(position);
			} else {
				smallerPosition = _tree.left(position);
			}

			// attempt to swap child
			if (_comparator.compare(position.element().getKey(), smallerPosition.element().getKey()) > 0) {
				// swap elements
				swapValuesandKeys(position.element(), smallerPosition.element());
				// new position to check is old position of smaller child
				position = smallerPosition;
			} else {
				possibleSmallerChild = false;
			}
		}
	}

	/**
	 * linearly updates the parents if the node that has been added with a key
	 * smaller than its parent's swap with parent
	 */
	public Position<MyHeapEntry<K, V>> upSort(Position<MyHeapEntry<K, V>> p) {

		Position<MyHeapEntry<K, V>> restingPosition = p;
		try {
			while (!_tree.isRoot(p)
					&& _comparator.compare(p.element().getKey(), _tree.parent(p).element().getKey()) < 0) {
				swapValuesandKeys(p.element(), _tree.parent(p).element());
				p = _tree.parent(p);
				restingPosition = p;
			}
		} catch (BoundaryViolationException e) {

		}
		return restingPosition;
	}

	/**
	 * swamps elements and keys of two given entries without sorting
	 */
	public void swapValuesandKeys(MyHeapEntry<K, V> first, MyHeapEntry<K, V> second) {
		K oldKey = first.getKey();
		V oldVal = first.getValue();

		first.setKey(second.getKey());
		first.setValue(second.getValue());

		second.setKey(oldKey);
		second.setValue(oldVal);
	}
}
