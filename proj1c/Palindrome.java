public class Palindrome{

	public static Deque<Character> wordToDeque(String word){
		Deque<Character> characters = new LinkedListDeque<Character>();
		for(int i = 0; i < word.length(); i++){
			char character = word.charAt(i);
			characters.addLast(character);
		}
		return characters;
	}

	public static boolean isPalindrome(String word){
		if(word.length() == 0 || word.length() == 1){
			return true;
		}
		int last = word.length() - 1;
		char start = word.charAt(0);
		char end = word.charAt(last);
		if (start == end){
			return isPalindrome(word.substring(1, last));
		}
		return false;
	}

	public static boolean isPalindrome(String word, CharacterComparator cc){
		if(word.length() == 0 || word.length() == 1){
			return true;
		}
		int last = word.length() - 1;
		char start = word.charAt(0);
		char end = word.charAt(last);
		if (cc.equalChars(start, end)){
			return isPalindrome(word.substring(1, last), cc);
		}
		return false;
	}
}