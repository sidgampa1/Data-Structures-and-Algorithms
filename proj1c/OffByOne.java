
public class OffByOne implements CharacterComparator{

	@Override
	public boolean equalChars(char x, char y){
		int diff = y - x;
		if(Math.abs(diff) == 1){
			return true;
		}
		return false;
	}
}