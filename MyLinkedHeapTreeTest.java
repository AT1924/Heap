package heap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import net.datastructures.EmptyTreeException;
import net.datastructures.Position;

/**
 * This class should be used to test the functionality of your MyLinkedHeapTree
 * implementation. You will find a few examples to guide you through the syntax
 * of writing test cases. Each test case uses its own tree instance, to ensure
 * that the test cases are independent of each other. All of these given
 * examples should also pass once you've implemented your tree methods. The
 * annotation @Test before each test case is JUnit syntax, it basically lets the
 * compiler know that this is a unit test method. Use this annotation for every
 * test method. This class is also like any other java class, so should you need
 * to add private helper methods to use in your tests, you can do so, simply
 * without the annotations as you usually would write a method. The general
 * framework of a test case is: - Name the test method descriptively, mentioning
 * what is being tested (it is ok to have slightly verbose method names here) -
 * Set-up the program state (ex: instantiate a heap and insert K,V pairs into
 * it) - Use assertions to validate that the progam is in the state you expect
 * it to be
 */
public class MyLinkedHeapTreeTest {

	/**
	 * A simple example of checking that the add() method adds the first element
	 * to the tree.
	 */
	@Test
	// TODO pass this test
	public void testAddOneElement() {
		MyLinkedHeapTree<Integer> tree = new MyLinkedHeapTree<Integer>();
		tree.add(1);

		/*
		 * These are two ways of asserting the same thing Use whichever you find
		 * more convenient out of assertThat(actual, is(expected)) and
		 * assertTrue(boolean) Take a look at the JUnit docs for more assertions
		 * you might want to use.
		 */
		assertThat(tree.size(), is(1));
		assertTrue(tree.size() == 1);
	}

	// TODO -- add tests for adding/removing multiple elements

	/**
	 * This is an example of how to test whether an exception you expect to be
	 * thrown on a certain line of code is actually thrown. As shown, you'd
	 * simply add the expected exception right after the @Test annotation. This
	 * test will pass if the exception expected is thrown by the test and fail
	 * otherwise.
	 */
	@Test(expected = EmptyTreeException.class)
	public void testRemoveThrowsEmptyTreeException() {
		MyLinkedHeapTree<Integer> tree = new MyLinkedHeapTree<Integer>();
		tree.remove();
	}

	/**
	 * TODO: Write your own tests below! Think of edge cases for add/remove and
	 * try to test your helper methods (if applicable).
	 */
	@Test()
	public void testAddElement() {
		// setup
		MyLinkedHeapTree<MyHeapEntry> tree = new MyLinkedHeapTree<MyHeapEntry>();
	
		tree.set_comparator(new IntegerComparator());

		MyHeapEntry entry1 = new MyHeapEntry(1, "ABC");
		MyHeapEntry entry2 = new MyHeapEntry(2, "DEF");
		MyHeapEntry entry3 = new MyHeapEntry(10, "GHI");

		// put items in tree
		Position pos1 = tree.add(entry1);
		Position pos2 = tree.add(entry2);
		Position pos3 = tree.add(entry3);

		// check that positions contain correct entries
		assertEquals(pos1.element(), entry1);
		assertEquals(pos2.element(), entry2);
		assertEquals(pos3.element(), entry3);

		// check size
		assertThat(tree.size(), is(3));
	}

	/**
	 * create an entry, then test the getters and setters in the entry
	 */
	@Test()
	public void testMyHeapEntry() {
		//test
		MyHeapEntry ent = new MyHeapEntry(1, "the value");

		// test get/set
		assertEquals(ent.getKey(), 1);
		assertEquals(ent.getValue(), "the value");

		for (int i = 2; i < 100; i++) {
			ent.setKey(i);
			assertEquals(ent.getKey(), i);

			// test different values
			ent.setValue("another value");
			assertEquals("another value", ent.getValue());

			ent.setValue("a third value");
			assertEquals(ent.getValue(), "a third value");
		}

	}
	
	@Test()
	public void testReorder(){
		MyLinkedHeapTree tree = new MyLinkedHeapTree();
		tree.set_comparator( new IntegerComparator());
		
		// test that we are returning the appropriate position
		assertEquals(65, tree.add(65).element());
		assertEquals(55, tree.add(55).element());
		assertEquals(47, tree.add(47).element());
		assertEquals(45,tree.add(45).element());
		assertEquals(34,tree.add(34).element());
		
		// order of leaves 65, 55, 47, 34, 45
		int[] list = {47,65,55,45,34};
		for (int i = 0; i < list.length; i++){
			assertEquals(list[i], tree.remove());
		}
		
		
		
		
	}
	@Test()
	public void testRemove(){
		MyLinkedHeapTree tree = new MyLinkedHeapTree();
		tree.set_comparator(new IntegerComparator());
		
		tree.add(24);
		tree.add(16);
		assertEquals(24, tree.remove());
		assertEquals(16, tree.remove());
	}
	
	
	
}
