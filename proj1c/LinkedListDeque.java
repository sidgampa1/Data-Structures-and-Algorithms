public class LinkedListDeque<Item> implements Deque<Item>{

	private class Node {
 		public Item item;    
 		public Node next;
 		public Node prev; 
 
 		public Node(Item i, Node h, Node p) {
 			item = i;
 			next = h;	
 			prev = p;
 		}
 	} 
 	
	private Node sentinel;
	private int size;

	//constructor to create empty list
	public LinkedListDeque(){
		sentinel = new Node(null, null, null);
		sentinel.next = sentinel;
		sentinel.prev = sentinel;
		size = 0;
	}	

	//adds item to front of Deque
	@Override
	public void addFirst(Item i){
		Node newFirst = new Node(i, null, null);
		Node oldFirst = sentinel.next;
		newFirst.next = oldFirst;
		newFirst.prev = sentinel;
		oldFirst.prev = newFirst;
		sentinel.next = newFirst;
		size += 1;
	}

	//adds item to back of Deque
	@Override
	public void addLast(Item i){
		Node newLast = new Node(i, null, null);
		Node oldLast = sentinel.prev;
		newLast.prev = oldLast;
		newLast.next = sentinel;
		sentinel.prev = newLast;
		oldLast.next = newLast;
		size += 1;
	}

	//returns true if Deque is empty, false otherwise
	@Override
	public boolean isEmpty(){
		if (size == 0){
			return true;
		}
		return false;

	}

	//same as get, but uses recursion
	public Item getRecursive(int index){
		if (sentinel.next == null){
			return null;
		}
		Node temp = sentinel.next;
		return getHelper(temp, index);
	}

	private Item getHelper(Node temp, int index){
		if (index == 0){
			return temp.item;
		}
		else{
			return getHelper(temp.next, index - 1);
		}
	}

	//returns number of items in Deque
	@Override
	public int size(){
		return size;
	}

	//prints items in Deque separated by a space
	@Override
	public void printDeque(){
		Node temp = sentinel;
		for(int i = 0; i < size; i++){
			temp = temp.next;
			Item currentItem = temp.item;
			System.out.print(currentItem + " ");
		}
	}

	//removes and returns first item in Deque, or null
	@Override
	public Item removeFirst(){
		if (isEmpty()){
			return null;
		}
		Node oldFirst = sentinel.next;
		Node newFirst = oldFirst.next;
		sentinel.next = newFirst;
		newFirst.prev = sentinel;
		oldFirst.prev = null;
		oldFirst.next = null;
		size -= 1;
		return oldFirst.item;
	}

	//removes and returns last item in Deque, or null
	@Override
	public Item removeLast(){
		if (isEmpty()){
			return null;
		}
		Node oldLast = sentinel.prev;
		Node newLast = oldLast.prev;
		sentinel.prev = newLast;
		newLast.next = sentinel;
		oldLast.prev = null;
		oldLast.next = null;
		size -= 1;
		return oldLast.item;
	} 

	//returns item at given index (start = 0)
	@Override
	public Item get(int index){
		Node temp = sentinel;
		if (temp.next != null){
			temp = temp.next;
		}
		while(index != 0 && temp.next != null){
			temp = temp.next;
			index -= 1;
		}
		return temp.item;
	}
}