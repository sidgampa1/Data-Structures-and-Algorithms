package editor;

public class ArrayDeque<Item> {

	private Item[] items;
	private int size;
	private int nextFirst;
	private int nextLast;
	private final int RFACTOR = 2;

	//constructor to create list
	public ArrayDeque(){
		items = (Item []) new Object[1000];
		size = 0;
		nextFirst = 500;
		nextLast = 501;
	}

	//computes and returns usage ratio
	private double usageRatio(){
		return (double)(size) / items.length;
	}

	//resizes array to new capacity (only to use if array is filled)
	private void resize(int capacity){
		int front = plusOne(nextFirst);
		int counter = 0;
		Item[] a = (Item[]) new Object[capacity];
		while (items[front] != null && counter < size){
			a[counter] = items[front];
			front = plusOne(front);
			counter += 1;
		}
		items = a;
		nextFirst = items.length - 1;
		nextLast = size;
	}

	//adds item to front of Deque
	
	public void addFirst(Item i){
		if(size == items.length){
			resize(items.length * RFACTOR);
		}
		items[nextFirst] = i;
		size += 1;
		nextFirst = minusOne(nextFirst);
	}

	//adds item to back of Deque

	public void addLast(Item i){
		if(size == items.length){
			resize(items.length * RFACTOR);
		}
		items[nextLast] = i;
		size += 1;
		nextLast = plusOne(nextLast);
	}

	//returns true if Deque is empty, false otherwise

	public boolean isEmpty(){
		if (size == 0){
			return true;
		}
		return false;
	}

	//returns number of items in Deque

	public int size(){
	return size;
	}

	//prints items in Deque separated by a space
	//this will loop forever if size is full (fixed???)

	public void printDeque(){
		int index = plusOne(nextFirst);
		int counter = 0;
		while (items[index] != null && index < items.length && counter < size){
			System.out.print(items[index] + " ");
			index = plusOne(index);
			counter += 1;
		}
	}

	//removes and returns first item in Deque, or null

	public Item removeFirst(){
		if (size == 0){
			return null;
		}
		int front = plusOne(nextFirst);
		Item item = items[front];
		items[front] = null;
		size -= 1;
		nextFirst = plusOne(nextFirst);
		if (usageRatio() < .25 && items.length > 15){
			resize(items.length / RFACTOR);
		}
		return item;

	}

	private int plusOne(int index){
		index += 1;
		if(index == items.length){
			index = 0;
		}
		return index;
	}

	//removes and returns last item in Deque, or null

	public Item removeLast(){
		if (size == 0){
			return null;
		}
		int back = minusOne(nextLast);
		Item item = items[back];
		items[back] = null;
		size -= 1;
		nextLast = minusOne(nextLast);
		if (usageRatio() < .25 && items.length > 15){
			resize(items.length / RFACTOR);
		}
		return item;
	}

	private int minusOne(int index){
		index -= 1;
		if(index < 0){
			index = items.length - 1;
		}
		return index;
	}

	//returns item at given index (start = 0)

	public Item get(int index){
		if(size == 0){
			return null;
		}
		int front = plusOne(nextFirst);
		int actualIndex = front + index;
		if (actualIndex >= items.length){
			actualIndex -= items.length;
		}
		return items[actualIndex];
	}
}
