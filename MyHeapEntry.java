package heap;

import net.datastructures.Entry;
import net.datastructures.Position;

/**
 * Represents a key/value pair to be stored in a data structure, such as a heap.
 * Entry<K,V> is a very limited accessing interface, so you may wish to add
 * additional methods. In particular, think about the relationship of the
 * Entry<K,V> with its location in the heap's binary tree. All methods must run
 * in O(1) time.
 *
 * Feel free to add additional comments.
 * 
 * The only change to this class was the addition of helper method that get and set a MyHeapEntry's position.
 */

public class MyHeapEntry<K, V> implements Entry<K, V> {

	K key;
	V value;
	Position<MyHeapEntry<K, V>> _position;

	

	/**
	 * Default constructor. You may wish to modify the parameters.
	 */
	public MyHeapEntry(K key, V value) {
		this.key = key;
		this.value = value;
		
	}

	/**
	 * Sets key of entry
	 * 
	 * @param key
	 *            the key to set
	 */
	public void setKey(K key) {
		this.key = key;
	}

	/**
	 * Set value of entry
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setValue(V value) {
		this.value = value;
	}

	/**
	 * @return the key stored in this entry
	 */
	public K getKey() {
		return key;
	}

	/**
	 * @return the value stored in this entry
	 */
	public V getValue() {
		return value;
	}
	
	/*
	 * These helper methods allow the a MyHeapEntry to be position aware which is the basis for runtime
	 * of the methods in the MyHeap class
	 */
	
	public Position<MyHeapEntry<K,V>> get_position() {
		return _position;
	}
	
	public void set_position(Position<MyHeapEntry<K, V>> position) {
		this._position = position;
	}
}
