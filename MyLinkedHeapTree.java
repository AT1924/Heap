package heap;

import java.util.ArrayDeque;

import net.datastructures.CompleteBinaryTree;
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
 * 
 * This class implemented an ArrayDeque of Positions <E> in order to keep track of the positions of the 
 * LinkedBinaryTree
 */


public class MyLinkedHeapTree<E> extends LinkedBinaryTree<E> implements CompleteBinaryTree<E> {

	
	private ArrayDeque<Position<E>> _positions;

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


	@Override
	public Position<E> add(E element) {

		// if tree is empty, create a root position and add element to position
		if (isEmpty()) {
			// create new position as root
			// add new position to deque
			_positions.add(addRoot(element));
			return _positions.peekLast();
		}

		// else get position with opening add new position as that position's
		// child

		Position<E> leftMost = _positions.getFirst();
		if (!hasLeft(leftMost)) {
			// if the first element on deque does not have a left child

			// add left position to deque
			_positions.add(insertLeft(leftMost, element));
			return _positions.peekLast();

		} else {
			// add right position to deque
			_positions.add(insertRight(leftMost, element));
			// Since now first element is full it should be removed from deque
			_positions.removeFirst();
			return _positions.peekLast();

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
		// get the last node of the tree 
		Position<E> child = _positions.peekLast();
		
		// check the edge case in that the tree only has one node
		if (isRoot(child)) {
			_positions.removeLast();
			return remove(child);

		}
		Position<E> parent = parent(child);

		// checking whether the last node (child) is a right or left child
		
		if (hasLeft(parent) && left(parent) == child) {
			_positions.removeLast();
			remove(child);

		}
		// if it is a right child remove it and add the parent back onto the deque
		if (hasRight(parent) && right(parent) == child) {
			_positions.removeLast();
			_positions.addFirst(parent(child));
			remove(child);
		}
		return child.element();
	}

	/*
	 * Feel free to add helper methods here. Add helper methods here.
	 */
	
	/*
	 *  getter of the last position of the tree, for use in MyHeap class
	 */
	public Position<E> getLastPosition() {
		return _positions.peekLast();
	}

}
