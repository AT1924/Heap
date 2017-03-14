package heap;

import java.util.ArrayDeque;
import java.util.Comparator;

import net.datastructures.BoundaryViolationException;
import net.datastructures.CompleteBinaryTree;
import net.datastructures.Deque;
import net.datastructures.EmptyTreeException;
import net.datastructures.LinkedBinaryTree;
import net.datastructures.Position;

/**
 * An implementation of a complete binary tree by means of a linked structure
 * (LinkedBinaryTree). The LinkedBinaryTree class takes care of most of the
 * "mechanics" of modifying the tree (you should read through the NDS4
 * documentation in order to fully understand how this class works), but you
 * will need to think about how to implement a CompleteBinaryTree such that
 * additions and removals operate *only* on the last node (hint: think about
 * other useful data structures). You must also ensure that you do not violate
 * the assignment runtime requirements when deciding how you will track nodes
 * within the tree.
 * 
 * Feel free to add additional comments.
 */

// TODO readme: include justification for using linear updating while reordering
// the tree
public class MyLinkedHeapTree<E> extends LinkedBinaryTree<E> implements CompleteBinaryTree<E> {

	// instance variables
	private ArrayDeque<Position<E>> _positions;
	private Comparator _comparator;

	/**
	 * Default constructor. The tree begins empty.
	 */
	public MyLinkedHeapTree() {
		_positions = new ArrayDeque<Position<E>>();

	}

	/**
	 * Adds an element to the tree just after the last node. Returns the newly
	 * created position for the element.
	 *
	 * Note: You don't need to instantiate a new Postion<E> as a local variable.
	 * Look at the NDS4 documentation for LinkedBinaryTree for how to add a new
	 * Position<E> to the tree.
	 * 
	 * This method must run in constant O(1) worst-case time.
	 * 
	 * @param element
	 *            to be added to the tree as the new last node
	 * @return the Position of the newly inserted element
	 */

	// TODO for testing purposes
	// when adding an element you have to reorder the tree
	@Override
	public Position<E> add(E element) {

		// if tree is empty, create a root position and add element to position
		if (isEmpty()) {
			// create new position as root
			Position<E> root = addRoot(element);
			// add new position to deque
			_positions.add(root);
			// return position
			return root;
		}

		// else get position with opening add new position as that position's
		// child

		Position<E> leftMost = _positions.getFirst();
		if (!hasLeft(leftMost)) {
			// if the first element on deque does not have a left child
			Position<E> leftChild = insertLeft(leftMost, element);
			// add left position to deque
			_positions.add(leftChild);
			reorderTree(leftChild);
			return leftChild;

		} else {
			Position<E> rightChild = insertRight(leftMost, element);
			// add right position to deque
			_positions.add(rightChild);
			// Since now first element is full it should be removed from deque
			_positions.removeFirst();
			reorderTree(rightChild);
			return rightChild;

		}

	}

	/**
	 * Removes and returns the element stored in the last node of the tree.
	 * 
	 * This method must run in constant O(1) worst-case time.
	 * 
	 * @return the element formerly stored in the last node (prior to its
	 *         removal)
	 * @throws EmptyTreeException
	 *             if the tree is empty and no last node exists
	 */
	@Override
	public E remove() throws EmptyTreeException {

		if (isEmpty()) {
			throw new EmptyTreeException("You can not remove when the tree is empty");
		}

		Position<E> child = _positions.peekLast();
		if (isRoot(child)){
			_positions.removeLast();
			return remove(child);
			

		}
		Position<E> parent = parent(child);
		
		
		if (hasLeft(parent) && left(parent) == child) {
			_positions.removeLast();
			remove(child);

		}

		if (hasRight(parent) && right(parent) == child) {
			_positions.removeLast();
			_positions.addFirst(parent(child));
			remove(child);
		}
		return child.element();
	}

	public void set_comparator(Comparator _comparator) {
		this._comparator = _comparator;
	}

	/*
	 * Feel free to add helper methods here. Add helper methods here.
	 */
	// TODO write method that reorders tree

	public void reorderTree(Position<E> p) {
		// linearly updates the parents
		// if the node that has been added is greater than its parents swap with
		// parent

		try {
			while (parent(p) != null && _comparator.compare(p.element(), parent(p).element()) > 0) {
				swapElements(p, parent(p));
				p = parent(p);
			}
		} catch (BoundaryViolationException e) {
		}

	}

}
