package hangman;

public class GameBoardLinkedList implements GameBoard,HangManConstants {

	@SuppressWarnings("unused")
	private static int ALPHABET_COUNT = 26*2;
	private int	state;
	private int NumberOfGuess;
	private String MyWord;
	private String StringLine="_";
	private char[] duplicate;
	private char Charline=StringLine.charAt(0);
	private LLCharacterNode LLline=new LLCharacterNode(Charline);
	private LLCharacterNode HoldGuessHead;
	private LLCharacterNode HiddenWordHead;
	
	public GameBoardLinkedList(String guess)
	{
		MyWord=guess;
		NumberOfGuess=0;
		duplicate=new char[MyWord.length()];
		findDuplicated(guess);
		for(int count1=0;count1<MyWord.length();count1++)
		{
		   if(HiddenWordHead==null)
		   {
			HiddenWordHead=LLline;
		   }
		       else
		      {
		    	   LLCharacterNode temp=HiddenWordHead;
		    	   while(temp.getLink()!=null)
		    	   {
		    		   temp=temp.getLink();
		    	   }
		    	   temp.setLink(new LLCharacterNode(Charline));
		      }
		}
		state = STARTING_STATE;
	}
	@Override
	public boolean isPriorGuess(char guess) {
		// TODO Auto-generated method stub
		if(HoldGuessHead==null)
		{
			return false;
		}
		else
		{
			LLCharacterNode temp=HoldGuessHead;
			while(temp!=null)
			{
			   if(temp.getInfo()==guess)
			   {
				return true;
			   }
			   temp=temp.getLink();
			}
		}
		return false;
	}

	@Override
	public int numberOfGuesses() {
		return NumberOfGuess;
	}

	@Override
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
					    LLCharacterNode LLGuess=new LLCharacterNode(guess);
					     if(HoldGuessHead==null)
					     {
					    	 HoldGuessHead=LLGuess;
					    	 return true;
					     }
					     else
					     {
					    	 LLCharacterNode temp=HoldGuessHead;
								while(temp.getLink()!=null)
								{
								   temp=temp.getLink();
					            }
								temp.setLink(LLGuess);
								return true;
				         }
					  }
			   }
		}	   		 
			return false;
	}

	@Override
	public boolean doMove(char guess) {
		LLCharacterNode temp=HiddenWordHead;
		int dummy=0;
		if(isCorrectGuess(guess))
		{
			int count=0;
			while(temp!=null)
			{
				if(duplicate[count]==guess&&dummy==0)
				{
				duplicate[count]=0;
				dummy+=1;
				}
				if(MyWord.charAt(count)==guess)
					temp.setInfo(guess);
				   temp=temp.getLink();
				   count+=1;
			}
			return true;
		}
		else
		{
			if(isPriorGuess(guess))
			{
				int temp2=0;
				while(temp2<duplicate.length)
				{
				  if(duplicate[temp2]==guess)
				  {
					  duplicate[temp2]=0;
					  return true;
				  }
				  temp2+=1;
				}
				return false;
			}
			else
			{
				LLCharacterNode LLGuess=new LLCharacterNode(guess);
			     if(HoldGuessHead==null)
			     {
			    	 HoldGuessHead=LLGuess;
			 		state+=1;
					return false;
			     }
			     else
			     {
			    	 LLCharacterNode temp3=HoldGuessHead;
						while(temp3.getLink()!=null)
						{
						   temp3=temp3.getLink();
			            }
						temp3.setLink(LLGuess);
						state+=1;
						return false;
			     }
			}
		}
	}

	@Override
	public boolean inWinningState() {
		LLCharacterNode temp=HiddenWordHead;
		int count=0;
		while(temp!=null)
		{
			if(!(temp.getInfo()==MyWord.charAt(count)))
			{
				return false;
			}
			temp=temp.getLink();
			count+=1;
		}
		return true;
	}

	@Override
	public boolean inLosingState() {
		return state == NUMBER_OF_STATES;
	}

	@Override
	public int currentHungState() {
		return state;
	}
    
	public String toString()
	{
		String s="";
		LLCharacterNode temp=HiddenWordHead;
		while(temp.getLink()!=null)
		{
			s+=temp.getInfo()+" ";
			temp=temp.getLink();
		}
		s+=temp.getInfo();
		return s;
	}
	@Override
	public String previousGuessString() {
		String s="[";
		LLCharacterNode temp=HoldGuessHead;
		  while(temp.getLink()!=null)
		  {
			  s+=temp.getInfo()+", ";
			  temp=temp.getLink();
		  }
		  s+=temp.getInfo()+"]";
		return s;
	}
	public void findDuplicated(String guess)
	{
		for(int count1=0;count1<duplicate.length;count1++)
		{
			for(int count2=count1+1;count2<duplicate.length;count2++)
			{
				if(guess.charAt(count1)==guess.charAt(count2))
					duplicate[count1]=guess.charAt(count1);
				    duplicate[count2]=duplicate[count1];
			}
		}
	}

}
