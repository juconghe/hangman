package hangman;
/**
 * The Array implementation of the GameBoard interface.
 */
public class GameBoardArray implements GameBoard, HangManConstants {
	/** The number of characters (lower/upper). */
	@SuppressWarnings("unused")
	private static int ALPHABET_COUNT = 26*2;
	
	/** hung state */
	private int	state;
	private int NumberOfGuess;
	private String MyWord;
	private String HoldGuess="";
	private boolean[] HiddenWord;
	private char[] duplicate;
	/**
	 * Creates a new GameBoardArray object.
	 *  
	 *  guessWord the word to guess
	 */
	public GameBoardArray(String guessWord) {
		//System.out.println(" constructing");
		MyWord=guessWord;
		NumberOfGuess=0;
		HiddenWord=new boolean[guessWord.length()];
		duplicate=new char[guessWord.length()];
		for(int count1=0;count1<duplicate.length;count1++)
		{
			for(int count2=count1+1;count2<duplicate.length;count2++)
			{
				if(MyWord.charAt(count1)==MyWord.charAt(count2))
					duplicate[count1]=MyWord.charAt(count1);
				    duplicate[count2]=duplicate[count1];
			}
		}
		state = STARTING_STATE;
	}
		
	public boolean isPriorGuess(char guess) {
		for(int count=0;count<HoldGuess.length();count++)
		{
			if(HoldGuess.charAt(count)==guess)
			{
				return true;
			}			
		}		
		return false;
				
	}
	
	public int numberOfGuesses() {
		return NumberOfGuess;
	}
	
	public boolean isCorrectGuess(char guess) {
		NumberOfGuess+=1;
		for(int count=0;count<MyWord.length();count++)
		{	
			   if(MyWord.charAt(count)==guess)				   
			   {				 
				   if(isPriorGuess(guess))
				   {
					   return false;
				   }
				   else
				   {
					      HoldGuess+=guess;
						  return true;
				   }
					  }
			 }				   
			 
			return false;
	}
	
	public boolean doMove(char guess) {
		//System.out.println(" here");
		int dummy=0;
		if(isCorrectGuess(guess))
		{
			for(int count=0;count<MyWord.length();count++)
			{
				if(duplicate[count]==guess&&dummy==0)
					{
					duplicate[count]=0;
					dummy+=1;
					}
				if(MyWord.charAt(count)==guess)
			    HiddenWord[count]=true;
			}
			return true;
		}
		else
		{
			if(isPriorGuess(guess))
			{
				int temp=0;
				while(temp<duplicate.length)
				{
				  if(duplicate[temp]==guess)
				  {
					  duplicate[temp]=0;
					  return true;
				  }
				  temp+=1;
				} 
				return false;
			}
			else
			{
				HoldGuess+=guess;
				state+=1;
				return false;
			}
		}
		 
	}
 
	

	public boolean inWinningState() {
       for(int count=0;count<HiddenWord.length;count++)
       {
    	   if(!HiddenWord[count])
    		   return false;
       }
       return true;
	}

	public boolean inLosingState() {
		return state == NUMBER_OF_STATES;
	}
	
	public String toString() {
		String s="";
		for(int count=0;count<HiddenWord.length;count++)
		{
			if(HiddenWord[count])
			{
				if(count==HiddenWord.length-1)
				{					
			     s+=String.valueOf(MyWord.charAt(count));
				}
				else
				{
					 s+=String.valueOf(MyWord.charAt(count))+" ";
			    }
		}
			else
			{
				if(count==HiddenWord.length-1)
				s+="_";
				else
				{
					s+="_ ";
				}
			}
		}
		//System.out.println(s);
		return s;
	}

	public String previousGuessString() {
		String s = "[";
		 
		for(int count=0;count<HoldGuess.length();count++)
		{
			if(count==HoldGuess.length()-1)
			s+=HoldGuess.substring(count, count+1);
			else
				s+=HoldGuess.substring(count, count+1)+", ";
		}
		s+="]";
		//System.out.println(s);
		
		return s;
	}
	
	public int currentHungState() {
		System.out.println(state);
		return state;
	}

}
