import java.util.ArrayList;

public class ComputerMoveGenerator {

	public static final int OFFENCE = 1;
	public static final int DEFENCE = -1;
	
	
	PenteGameBoard myGame;
	int myStone;
	
	//ArrayList<CMObject> oMoves = new ArrayList<CMObjects>();
	//ArrayList<CMObject> dMoves = new ArrayList<CMObjects>();

	
	public ComputerMoveGenerator(PenteGameBoard gb, int stoneColor)
	{
		myStone = stoneColor;
		myGame = gb;
		
	//	System.out.println("computer is playing as player " + myStone);
	}
	
	public int[] getComputerMove()
	{
		int[] newMove; 
		
		defMoves();
		offMoves();
		
		
		newMove = generateRandomMove();
		
		
		
		try {
			sleepForAMove();
		} catch (InterruptedException e) {
		}
		
		return newMove;
	}
	
	public void offMoves()
	{
//		findOneDef();
//		findTwoDef();
//		findThreeDef();
//		finFourDef();

		
	}
	
	
	
	public void findOneDef()
	{
		
	}
	
	public void defMoves()
	{
		
	}
	

	private int[] generateRandomMove() 
	{
		int[] move = new int[2];
		
		boolean done = false;
		
		int newR, newC;
		
		do
		{
			newR = (int)(Math.random() * PenteGameBoard.SIDE_SQUARES);
			newC = (int)(Math.random() * PenteGameBoard.SIDE_SQUARES);
			
			if(myGame.getBoard()[newR][newC].getState() == PenteGameBoard.EMPTY)
			{
				done = true;
				move[0] = newR;
				move[1] = newC;
			}
		}while(!done);
		
		return move;
	}
	
	public void sleepForAMove() throws InterruptedException
	{
		Thread currThred = Thread.currentThread();
		
		Thread.sleep(PenteGameBoard.SLEEP_TIME);
	}
	
}
