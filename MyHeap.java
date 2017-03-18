package heap;

import java.util.Comparator;
import net.datastructures.CompleteBinaryTree;
import net.datastructures.DefaultComparator;
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
		_tree = new MyLinkedHeapTree();
		_tree.set_comparator(comparator);
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
	// TODO need to update comparator
	public void setComparator(Comparator<K> comparator) throws IllegalStateException, IllegalArgumentException {
		if (!isEmpty()) {
			throw new IllegalStateException("priority queue is not empty");
		}
		if (comparator == null) {
			throw new IllegalArgumentException("null comparator");
		}
		_tree.set_comparator(comparator);

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
		// does this run in O(1) time?
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
		// make sure to cache minimum and make sure to update and remove a node
		// keep an arrayList of entries
		if (isEmpty()) {
			throw new EmptyPriorityQueueException("Cannot get entry as heap is empty");
		}
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
		MyHeapEntry<K, V> entry = new MyHeapEntry<K, V>(key, value);
		entry.set_position(_tree.add(entry));
		reorderTree(entry.get_position());
		return entry;
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
		MyHeapEntry<K, V> minE = (MyHeapEntry<K, V>) min();
		return remove(minE);

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

		// swap with the last position
		Position<MyHeapEntry<K, V>> lastPosition = _tree.getLastPosition();
		Position<MyHeapEntry<K, V>> checkedPosition = checkedEntry.get_position();
		_tree.swapElements(lastPosition, checkedPosition);
		swapElementPositions(lastPosition, checkedPosition);

		// sort down
		downSort(checkedPosition);

		// up sort?
		reorderTree(checkedPosition);

		// delete from tree and return entry
		return _tree.remove();
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
		MyHeapEntry<K, V> checkedEntry = this.checkAndConvertEntry(entry);

		return null;
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
		MyHeapEntry<K, V> checkedEntry = this.checkAndConvertEntry(entry);

		// continue here ...

		return null;
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

	// TODO -- upsort as well
	/**
	 * Reorder a position with respect to it's children. Walk down the tree,
	 * swapping with any child node that has a smaller key.
	 * 
	 * @param position
	 *            a (hopefully) freshly swapped position
	 */
	public void downSort(Position<MyHeapEntry<K, V>> position) {
		Boolean possibleLargerChild = true;
		while (possibleLargerChild) {
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
				_tree.swapElements(position, smallerPosition);
				swapElementPositions(position, smallerPosition);
				// new position to check is old position of smaller child
				position = smallerPosition;
			} else {
				possibleLargerChild = false;
			}
		}
	}

	/*
	 * reorders tree when element is inserted
	 */
	public Position<MyHeapEntry<K, V>> reorderTree(Position<MyHeapEntry<K, V>> p) {
		// linearly updates the parents
		// if the node that has been added is greater than its parents swap with
		// parent
		// test
		Position<MyHeapEntry<K, V>> restingPosition = p;
		try {
			while (!_tree.isRoot(p)
					&& _comparator.compare(p.element().getKey(), _tree.parent(p).element().getKey()) < 0) {
				_tree.swapElements(p, _tree.parent(p));
				swapElementPositions(p, _tree.parent(p));
				p = _tree.parent(p);
				restingPosition = p;
			}
		} catch (BoundaryViolationException e) {

		}
		return restingPosition;
	}

	public void swapElementPositions(Position<MyHeapEntry<K, V>> firstPos, Position<MyHeapEntry<K, V>> secondPos) {
		 Position<MyHeapEntry<K, V>> swapped1 = firstPos;
		// firstPos.element().set_position(secondPos);
		// secondPos.element().set_position(swapped1);
		MyHeapEntry e1 = firstPos.element();
		MyHeapEntry e2 = secondPos.element();
		e1.set_position(secondPos);
		e2.set_position(firstPos);
		int x = 0;
	}
}
