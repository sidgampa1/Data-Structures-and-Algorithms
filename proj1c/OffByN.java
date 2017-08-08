public class OffByN implements CharacterComparator{
	
	private int margin;

	public OffByN(int N){
		margin = N;
	}

	@Override
	public boolean equalChars(char x, char y){
		int diff = y - x;
		if(Math.abs(diff) == margin){
			return true;
		}
		return false;
	}
}