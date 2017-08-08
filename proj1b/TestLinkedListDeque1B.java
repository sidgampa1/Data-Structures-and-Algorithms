import static org.junit.Assert.*;
import org.junit.Test;

public class TestLinkedListDeque1B{

public LinkedListDequeSolution<Integer> correct = new LinkedListDequeSolution<Integer>();
public StudentLinkedListDeque<Integer> buggy = new StudentLinkedListDeque<Integer>();
public String errorMessage = "";
	
	@Test
	public void testRandom(){
		for(int i = 0; i < 10000000; i++){
			int random = StdRandom.uniform(7);
			if(random == 0){
				testAddFirst();
			}
			else if(random == 1){
				testAddLast();
			}
			else if(random == 3){
				testRemoveLast();
			}
			else if(random == 4){
				testRemoveFirst();
			}
			else if(random == 5){
				testSize();
			}
			else{
				testIsEmpty();
			}
		}

	}

	public void testAddFirst(){
		int random = StdRandom.uniform(1000);
		correct.addFirst(random);
		buggy.addFirst(random);
		errorMessage += "addFirst(" + random + ")\n";
		assertEquals(errorMessage, correct.get(0), buggy.get(0));
	}

	public void testAddLast(){
		int random = StdRandom.uniform(1000);
		correct.addLast(random);
		buggy.addLast(random);
		int lastIndex = correct.size() - 1;
		errorMessage += "addLast(" + random + ")\n";
		assertEquals(errorMessage, correct.get(lastIndex), buggy.get(lastIndex));
	}

	public void testRemoveLast(){
		if(correct.isEmpty()){
			return;
		}
		errorMessage += "removeLast()\n";
		assertEquals(errorMessage, correct.removeLast(), buggy.removeLast());
	}

	public void testRemoveFirst(){
		if(correct.isEmpty()){
			return;
		}
		errorMessage += "removeFirst()\n";
		assertEquals(errorMessage, correct.removeFirst(), buggy.removeFirst());
	}

	public void testSize(){
		errorMessage += "size()\n";
		assertEquals(errorMessage, correct.size(), buggy.size());
	}

	public void testIsEmpty(){
		errorMessage += "isEmpty()\n";
		assertEquals(errorMessage, correct.isEmpty(), buggy.isEmpty());
	}

	public static void main(String... args) {        
        jh61b.junit.TestRunner.runTests("all", TestLinkedListDeque1B.class);
    }

}