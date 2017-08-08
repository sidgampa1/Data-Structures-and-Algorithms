import static org.junit.Assert.*;
import org.junit.Test;

public class TestPalindrome{

	@Test
	public void testPalindrome(){
		OffByOne compare = new OffByOne();
		assertEquals(true, Palindrome.isPalindrome("noon", compare));
		assertEquals(true, Palindrome.isPalindrome("racecar", compare));
		assertEquals(true, Palindrome.isPalindrome("aabbaabbaa", compare));
		assertEquals(false, Palindrome.isPalindrome("eeowfu", compare));
		assertEquals(true, Palindrome.isPalindrome("u", compare));
		assertEquals(false, Palindrome.isPalindrome("ui", compare));
		assertEquals(true, Palindrome.isPalindrome(""));
	}

	@Test
	public void testOffByOne(){
		OffByOne compare = new OffByOne();
		assertEquals(true, compare.equalChars('a', 'b'));
		assertEquals(true, compare.equalChars('a', 'a'));
		assertEquals(false, compare.equalChars('a', 'z'));
			
	}

	public static void main(String... args) {        
       jh61b.junit.TestRunner.runTests("all", TestPalindrome.class);
    }

}