/** Performs some basic linked list tests. */
import static org.junit.Assert.*;
import org.junit.Test;

public class ArrayDequeTest {

	public ArrayDeque createDeque(){
		ArrayDeque<Integer> random = new ArrayDeque<Integer>();
		for(int i = 8; i >= 0; i--){
			random.addFirst(i);
		}
		return random;
	}

	@Test
	public void testAddRemoveFirst(){
		ArrayDeque<Integer> random = createDeque();
		random.printDeque();
		//System.out.println(random.nextFirst + " " + random.nextLast);
		for(int i = 0; i <= 8; i++){
			int expected = i;
			int actual = random.removeFirst();
			assertEquals(expected, actual);
		}
		assertEquals(0, random.size());
		for(int i = 8; i >= 0; i--){
			random.addFirst(i);
		}
		assertEquals(9, random.size());


	// 	assertEquals(true, random.isEmpty());

	// 	for(int i = 8; i >= 0; i--){
	// 		random.addFirst(i);
	// 	}
	// 	assertEquals(8, random.size());
	}

	// @Test
	// public void testAddFirstLast(){
	// 	ArrayDeque<Integer> random = createDeque();
	// 	int expected = ;
	// 	int actual = random.removeLast();
	// 	assertEquals(actual, expected);

	// 	actual = random.removeFirst();
	// 	expected = 1;
	// 	assertEquals(actual, expected);
	// }

	// @Test
	// public void testAddRemoveFirstLast(){
	// 	ArrayDeque<Integer> random = createDequeTestAdd();
	// 	random.printDeque();

	// 	// remove items from queue
	// 	int counter = 0;
	// 	while (counter < 1000){
	// 		random.removeLast();
	// 		counter += 1;
	// 	}

	// 	assertEquals(0, random.size());


	// 	//readd items back to queue
	// 	counter = 0;
	// 	while(counter < 1000){
	// 		random.addLast(counter);
	// 		counter += 1;
	// 	}
	// 	assertEquals(1000, random.size());

	// 	System.out.println();
	// 	random.printDeque();

	// 	//System.out.println(random.nextFirst + " " + random.nextLast);
	// 	int actual = random.get(3);
	// 	int expected = 3;
	// 	assertEquals(expected, actual);
	// }

	 public static void main(String... args) {        
        jh61b.junit.TestRunner.runTests("all", ArrayDequeTest.class);
    }

}