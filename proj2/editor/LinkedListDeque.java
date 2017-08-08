package editor;
import java.util.Iterator;

public class LinkedListDeque<Item> implements Iterable<Item>{

	public class Node {
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
	public Node currentNode;

	//constructor to create empty list
	public LinkedListDeque(){
		sentinel = new Node(null, null, null);
		sentinel.next = sentinel;
		sentinel.prev = sentinel;
		currentNode = sentinel;
		size = 0;
	}

	public Item currentNode() {
		return currentNode.item;
	}

	public int indexOf(Item i) {
		int index = 0;
		Node temp = sentinel;
		while (temp.next != sentinel) {
			temp = temp.next;
			if (temp.item == i) {
				return index;
			}
			index += 1;
		}
		return -1;

	}

	public Item toLeft() {
		if (currentNode.item != null) {
				currentNode = currentNode.prev;
				//System.out.println("moved left");
		}
		return currentNode.item;
	}

	public Item toRight(){
		if (currentNode.item != null) {
				currentNode = currentNode.next;
				//System.out.println("moved right");
		}
		return currentNode.item;
	}

	//adds item to front of Deque

	public void addFirst(Item i){
		Node newFirst = new Node(i, null, null);
		Node oldFirst = sentinel.next;
		newFirst.next = oldFirst;
		newFirst.prev = sentinel;
		oldFirst.prev = newFirst;
		sentinel.next = newFirst;
		currentNode = newFirst;
		size += 1;
	}

	//adds item to back of Deque

	public void addLast(Item i){
		Node newLast = new Node(i, null, null);
		Node oldLast = sentinel.prev;
		newLast.prev = oldLast;
		newLast.next = sentinel;
		sentinel.prev = newLast;
		oldLast.next = newLast;
		currentNode = newLast;
		size += 1;
	}

	public void add(Item i){
		Node newItem = new Node(i, null, null);
		newItem.next = currentNode.next;
		newItem.next.prev = newItem;
		newItem.prev = currentNode;
		currentNode.next = newItem;
		currentNode = newItem;
		size += 1;
	}

	public Item remove(){
		Node toRemove = currentNode;
		//System.out.println("to remove:" + toRemove.item);
		toRemove.prev.next = toRemove.next;
		toRemove.next.prev = toRemove.prev;
		currentNode = toRemove.prev;
		toRemove.next = null;
		toRemove.prev = null;
		size -= 1;
		//System.out.println("current:" + currentNode.item);
		return toRemove.item;
	}

	//returns true if Deque is empty, false otherwise

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

	public int size(){
		return size;
	}

	//removes and returns first item in Deque, or null

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
		currentNode = newFirst;
		size -= 1;
		return oldFirst.item;
	}

	//removes and returns last item in Deque, or null

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
		currentNode = newLast;
		size -= 1;
		return oldLast.item;
	}

	//returns item at given index (start = 0)

	public Item get(int index) {
		Node temp = sentinel;
		if (temp.next != null) {
			temp = temp.next;
		}
		while (index != 0 && temp.next != null) {
			temp = temp.next;
			index -= 1;
		}
		return temp.item;
	}

	public Item getLast() {
		return sentinel.prev.item;
	}

	public Node getFirst() {
		return sentinel.next;
	}

	public Item getNext() {
		return currentNode.next.item;
	}


	public Iterator<Item> iterator(){
		return new ListIterator();
	}

	public class ListIterator implements Iterator<Item>{
		private Node current;

        public ListIterator() {
			current = getFirst();
        }

        @Override
        public boolean hasNext() {
            return (!current.equals(sentinel));
        }

        @Override
        public Item next() {
            Item value = current.item;
            current = current.next;
            return value;
        }

	}


}
