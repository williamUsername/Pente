import java.util.ArrayList;
import java.util.Collections;

public class ComputerMoveGenerator {

	public static final int OFFENSE = 1;
	public static final int DEFENSE = -1;
	
	public static final int ONE_DEF = 1;
	public static final int TWO_DEF = 4; 
	public static final int TWO_OPEN = 5;
	public static final int TWO_CAP = 6;
	public static final int THREE_DEF = 9;
	public static final int FOUR_DEF = 11;
	public static final int THREEE_DEF = 9;
	public static final int FOURE_DEF = 11;
	public static final int TUTU_DEF = 11;
	public static final int NOCAPS_DEF = 10;

	
	public static final int ONE_OFF = 2;
	public static final int TWO_OFF = 3;
	public static final int TWO_STOP = 7;
	public static final int THREE_OFF = 7;
	public static final int FOUR_OFF = 8;
	public static final int THREEE_OFF = 7;
	public static final int FOURE_OFF = 20;
	public static final int FIVE_OFF = 20;




	
	
	PenteGameBoard myGame;
	int myStone;
	
	ArrayList<CMObject> allMoves = new ArrayList<CMObject>();

	
	public ComputerMoveGenerator(PenteGameBoard gb, int stoneColor)
	{
		myStone = stoneColor;
		myGame = gb;
		
	System.out.println("computer is playing as player " + myStone);
	}
	
	public int[] getComputerMove()
	{
		
		int[] newMove = new int[2]; 
		
		newMove[0] = -1;
		newMove[1] = 0;
		
		allMoves.clear();
		
		
		moves();
		
		setPriorities();

		
		//System.out.println("First Def Move: " + dMoves.get(0));
		//System.out.println("Last Def Move: " + dMoves.get(dMoves.size() - 1));

		PrintPriorities();
		
		
		if(allMoves.size() > 0)
		{
			//int whichOne = (int)(Math.random() * dMoves.size());
			CMObject ourMove = allMoves.get(0);
			if(allMoves.get(0).getPriority() <= this.ONE_DEF)
			{
				ourMove = allMoves.get((int)(Math.random() * allMoves.size()));
			} else
			{
				ourMove = allMoves.get(0);
			}
			newMove[0] = ourMove.getRow();
			newMove[1] = ourMove.getCol();
			if(myGame.darkSquareProb(newMove[0], newMove[1]) == true)
			{
				System.out.println("there is a problem");
			}
			
		} else
		{
			if(myStone == PenteGameBoard.BLACK && myGame.getDarkStoneTanken2() == false)
			{
				int stoneProbRow = -1;
				int stoneProbCol = -1;
				int safe = PenteGameBoard.INNEREND - PenteGameBoard.INNERSTART + 1;
				
					while(myGame.getDarkStoneTanken2() == false)
					{
						stoneProbRow = (int) (Math.random() * (safe + 2)) + (safe + 1);
						stoneProbCol = (int) (Math.random() * (safe + 2)) + (safe + 1);
						myGame.darkSquareProb(stoneProbRow, stoneProbCol);
	
					}
					newMove[0]  = stoneProbRow;
					newMove[1]  = stoneProbCol;

			} else 
			{
			newMove = generateRandomMove();
			}
		}
		
	//	newMove = generateRandomMove();
		
		
		
		try {
			sleepForAMove();
		} catch (InterruptedException e) {
			
		}
		
		return newMove;
	
	}
	
	public boolean isOnBoard( int r, int c)
	{
		boolean isOn = false;
		
		if(r >= 0 && r < PenteGameBoard.SIDE_SQUARES)
		{
			if(c >= 0 && c < PenteGameBoard.SIDE_SQUARES)
			{
				isOn = true;
			}
		}
		
		return isOn;
	}
	
	public void setMove(int r, int c, int p, int t)
	{
		if(myStone == PenteGameBoard.BLACK && myGame.getDarkStoneTanken2() == false)
		{
			if(myGame.darkSquareProbCompMoveList(r, c) == false)
			{
				CMObject newMove = new CMObject();
				newMove.setRow(r);
				newMove.setCol(c);
				newMove.setPriority(p);
				newMove.setMoveType(t);
				allMoves.add(newMove);
			} else
			{
				System.out.println();
			}
		}else
		{
			CMObject newMove = new CMObject();
			newMove.setRow(r);
			newMove.setCol(c);
			newMove.setPriority(p);
			newMove.setMoveType(t);
			allMoves.add(newMove);
			
		}
		
			
	}
	
	public void moves()
	{
		for(int row = 0; row < PenteGameBoard.SIDE_SQUARES; row++)
		{
			for(int col = 0; col < PenteGameBoard.SIDE_SQUARES; col++)
			{ 
				
				if(myGame.getBoard()[row][col].getState() == myStone * -1)
				{
					findOneDef(row, col);
					findTwoDef(row, col);
					findThreeDef(row, col);
					findFourDef(row, col);
					findThreeEDef(row, col);
					findFourEDef(row, col);
					findTuTuDef(row, col);
					protectCapsDef(row, col);
				}
				
				if(myGame.getBoard()[row][col].getState() == myStone)
				{
					findOneOff(row, col);
					findTwoOff(row, col);
					findThreeOff(row, col);
					findFourOff(row, col);
					findThreeEOff(row, col);
					findFourEOff(row, col);
				}
//				findThreeDef(row, col);
//				findFourDef(row, col);
			}
		}
	}
	
	public void findOneDef(int r, int c)
	{
		for(int rl = -1; rl <= 1; rl++)
		{
			for(int ud = -1; ud <= 1; ud++)
			{
				try 
				{
					if(myGame.getBoard()[r + rl][c + ud].getState() == PenteGameBoard.EMPTY)
					{
							setMove(r + rl, c + ud,ONE_DEF, DEFENSE);
					
						
					}
				} catch (ArrayIndexOutOfBoundsException e)
				{
					//System.out.println("off the board in findOneDef at [" + r + "][" + c + "]");
				}
			}
		}
	}

	public void findTwoDef(int r, int c)
	{
		for(int rl = -1; rl <= 1; rl++)
		{
			for(int ud = -1; ud <= 1; ud++)
			{
				try 
				{
					if(myGame.getBoard()[r + rl][c + ud].getState() == myStone * -1)
						{
							if(myGame.getBoard()[r + (rl * 2)][c + (ud * 2)].getState() == PenteGameBoard.EMPTY)
							{
															
								if(isOnBoard(r - rl, c - ud) == false)
								{
									setMove( r + (rl * 2), c + (ud * 2), TWO_DEF, DEFENSE);
								} else if(myGame.getBoard()[r - rl][c - ud].getState() == PenteGameBoard.EMPTY)
								{
									setMove(r + (rl * 2), c + (ud * 2), TWO_OPEN, DEFENSE);
								} else if(myGame.getBoard()[r - rl][c - ud].getState() == myStone)
								{
									setMove(r + (rl * 2), c + (ud * 2), TWO_CAP, DEFENSE);
								}
								
						
							}
						}
				} catch (ArrayIndexOutOfBoundsException e)
				{
					//System.out.println("off the board in findOneDef at [" + r + "][" + c + "]");
				}
			}
		}
	}
	
	public void findThreeDef(int r, int c)
	{
		for(int rl = -1; rl <= 1; rl++)
		{
			for(int ud = -1; ud <= 1; ud++)
			{
				try 
				{
					if(myGame.getBoard()[r + rl][c + ud].getState() == myStone * -1)
					{
						if(myGame.getBoard()[r + (rl*2)][c + (ud*2)].getState() == myStone * -1)
						{
	
							if(myGame.getBoard()[r + (rl* 3)][c + (ud* 3)].getState() == PenteGameBoard.EMPTY)
							{
								setMove(r + (rl * 3),c + (ud *3), THREE_DEF, DEFENSE);
							}
						}
					}
				} catch (ArrayIndexOutOfBoundsException e)
				{
					//System.out.println("off the board in findOneDef at [" + r + "][" + c + "]");
				}
			}
		}
	}
	
	public void findFourDef(int r, int c)
	{
		for(int rl = -1; rl <= 1; rl++)
		{
			for(int ud = -1; ud <= 1; ud++)
			{
				try 
				{
					if(myGame.getBoard()[r + rl][c + ud].getState() == myStone * -1)
					{
						if(myGame.getBoard()[r + (rl*2)][c + (ud*2)].getState() == myStone * -1)
						{
							if(myGame.getBoard()[r + (rl*3)][c + (ud*3)].getState() == myStone * -1)
							{
	
								if(myGame.getBoard()[r + (rl* 4)][c + (ud* 4)].getState() == PenteGameBoard.EMPTY)
								{
									setMove(r + (rl * 4),c + (ud *4), FOUR_DEF, DEFENSE);
								}
							}
						}
					}
				} catch (ArrayIndexOutOfBoundsException e)
				{
					//System.out.println("off the board in findOneDef at [" + r + "][" + c + "]");
				}
			}
		}
	}
	
	public void findThreeEDef(int r, int c)
	{
		for(int rl = -1; rl <= 1; rl++)
		{
			for(int ud = -1; ud <= 1; ud++)
			{
				try 
				{
					if(myGame.getBoard()[r + rl][c + ud].getState() == myStone * -1)
					{
						if(myGame.getBoard()[r + (rl*2)][c + (ud*2)].getState() == PenteGameBoard.EMPTY)
						{
	
							if(myGame.getBoard()[r + (rl* 3)][c + (ud* 3)].getState() ==  myStone * -1)
							{
								setMove(r + (rl * 2),c + (ud *2), THREEE_DEF, DEFENSE);
							}
						}
					}
				} catch (ArrayIndexOutOfBoundsException e)
				{
					//System.out.println("off the board in findOneDef at [" + r + "][" + c + "]");
				}
			}
		}
	}
	
	public void findFourEDef(int r, int c)
	{
		for(int rl = -1; rl <= 1; rl++)
		{
			for(int ud = -1; ud <= 1; ud++)
			{
				try 
				{
					if(myGame.getBoard()[r + rl][c + ud].getState() == myStone * -1)
					{
						if(myGame.getBoard()[r + (rl*2)][c + (ud*2)].getState() == myStone * -1)
						{
	
							if(myGame.getBoard()[r + (rl* 3)][c + (ud* 3)].getState() ==  PenteGameBoard.EMPTY)
							{
								if(myGame.getBoard()[r + (rl* 4)][c + (ud* 4)].getState() ==  myStone * -1)
								{
									setMove(r + (rl * 3),c + (ud *3), FOURE_DEF, DEFENSE);
								}
							}
						}
					}
				} catch (ArrayIndexOutOfBoundsException e)
				{
					//System.out.println("off the board in findOneDef at [" + r + "][" + c + "]");
				}
			}
		}
	}	
	
	public void findTuTuDef(int r, int c)
	{
		for(int rl = -1; rl <= 1; rl++)
		{
			for(int ud = -1; ud <= 1; ud++)
			{
				try 
				{
					if(myGame.getBoard()[r + rl][c + ud].getState() == myStone)
					{
						if(myGame.getBoard()[r + (rl*2)][c + (ud*2)].getState() == PenteGameBoard.EMPTY)
						{
							if(myGame.getBoard()[r + (rl*3)][c + (ud*3)].getState() == myStone)
							{
								if(myGame.getBoard()[r + (rl*4)][c + (ud*4)].getState() == myStone)
								{
									setMove(r + (rl * 2),c + (ud *2), TUTU_DEF, DEFENSE);
								}
							}
						}
					}
				} catch (ArrayIndexOutOfBoundsException e)
				{
					//System.out.println("off the board in findOneDef at [" + r + "][" + c + "]");
				}
			}
		}
	}	

	
	public void findTwoOffDef(int r, int c)
	{
		for(int rl = -1; rl <= 1; rl++)
		{
			for(int ud = -1; ud <= 1; ud++)
			{
				try 
				{
					if(myGame.getBoard()[r + rl][c + ud].getState() == myStone)
						{
							if(myGame.getBoard()[r + (rl * 2)][c + (ud * 2)].getState() == PenteGameBoard.EMPTY)
							{								
								if(isOnBoard(r - rl, c - ud) == false)
								{
									setMove( r + (rl * 2), c + (ud * 2), TWO_STOP, OFFENSE);	
								}
							}
						}
				} catch (ArrayIndexOutOfBoundsException e)
				{
					//System.out.println("off the board in findOneDef at [" + r + "][" + c + "]");
				}
			}
		}
	}
	
	
	public void findOneOff(int r, int c)

	{
		for(int rl = -1; rl <= 1; rl++)
		{
			for(int ud = -1; ud <= 1; ud++)
			{
				try 
				{
					if(myGame.getBoard()[r + rl][c + ud].getState() == PenteGameBoard.EMPTY)
					{
						
						setMove(r + rl, c + ud,ONE_OFF, OFFENSE);
						
					}
				} catch (ArrayIndexOutOfBoundsException e)
				{
					//System.out.println("off the board in findOneDef at [" + r + "][" + c + "]");
				}
			}
		}
	}
	
	public void protectCapsDef(int r, int c)
	{
		for(int rl = -1; rl <= 1; rl++)
		{
			for(int ud = -1; ud <= 1; ud++)
			{
				try 
				{
					if(myGame.getBoard()[r + rl][c + ud].getState() == myStone)
						{
							if(myGame.getBoard()[r + (rl * 2)][c + (ud * 2)].getState() == myStone)
							{
								if(myGame.getBoard()[r + (rl * 3)][c + (ud * 3)].getState() == PenteGameBoard.EMPTY)
								{
									setMove( r + (rl * 3), c + (ud * 3), NOCAPS_DEF, DEFENSE);	
								}
							}
						}
				} catch (ArrayIndexOutOfBoundsException e)
				{
					//System.out.println("off the board in findOneDef at [" + r + "][" + c + "]");
				}
			}
		}
	}
	public void findTwoOff(int r, int c)
	{
		for(int rl = -1; rl <= 1; rl++)
		{
			for(int ud = -1; ud <= 1; ud++)
			{
				try 
				{
					if(myGame.getBoard()[r + rl][c + ud].getState() == myStone)
						{
							if(myGame.getBoard()[r + (rl * 2)][c + (ud * 2)].getState() == PenteGameBoard.EMPTY)
							{								
								if(myGame.getBoard()[r + (rl * 2)][c + (ud * 2)].getState() == PenteGameBoard.EMPTY)
								{
									if(myGame.getBoard()[r + (rl * 2)][c + (ud * 2)].getState() == PenteGameBoard.EMPTY)
									{
										if(isOnBoard(r - rl, c - ud) == true)
										{
											setMove(r + (rl * 2), c + (ud * 2), TWO_OFF, OFFENSE);
										}
									}
								}
								
							}
						}
				} catch (ArrayIndexOutOfBoundsException e)
				{
					//System.out.println("off the board in findOneDef at [" + r + "][" + c + "]");
				}
			}
		}
	}

	public void findThreeOff(int r, int c)
	{
		for(int rl = -1; rl <= 1; rl++)
		{
			for(int ud = -1; ud <= 1; ud++)
			{
				try 
				{
					if(myGame.getBoard()[r + rl][c + ud].getState() == myStone)
					{
						if(myGame.getBoard()[r + (rl*2)][c + (ud*2)].getState() == myStone)
						{
	
							if(myGame.getBoard()[r + (rl* 3)][c + (ud* 3)].getState() == PenteGameBoard.EMPTY)
							{
								if(myGame.getBoard()[r + (rl* 4)][c + (ud* 4)].getState() == PenteGameBoard.EMPTY)
								{
									setMove(r + (rl * 3),c + (ud *3), THREE_OFF, OFFENSE);
								}
							}
						}
					}
				} catch (ArrayIndexOutOfBoundsException e)
				{
					//System.out.println("off the board in findOneDef at [" + r + "][" + c + "]");
				}
			}
		}
	}
	
	public void findFourOff(int r, int c)
	{
		for(int rl = -1; rl <= 1; rl++)
		{
			for(int ud = -1; ud <= 1; ud++)
			{
				try 
				{
					if(myGame.getBoard()[r + rl][c + ud].getState() == myStone)
					{
						if(myGame.getBoard()[r + (rl*2)][c + (ud*2)].getState() == myStone)
						{
							if(myGame.getBoard()[r + (rl*3)][c + (ud*3)].getState() == myStone)
							{
	
								if(myGame.getBoard()[r + (rl* 4)][c + (ud* 4)].getState() == PenteGameBoard.EMPTY)
								{
									setMove(r + (rl * 4),c + (ud *4), FOUR_OFF, OFFENSE);
								}
							}
						}
					}
				} catch (ArrayIndexOutOfBoundsException e)
				{
					//System.out.println("off the board in findOneDef at [" + r + "][" + c + "]");
				}
			}
		}
	}
	
	public void findThreeEOff(int r, int c)
	{
		for(int rl = -1; rl <= 1; rl++)
		{
			for(int ud = -1; ud <= 1; ud++)
			{
				try 
				{
					if(myGame.getBoard()[r + rl][c + ud].getState() == myStone)
					{
						if(myGame.getBoard()[r + (rl*2)][c + (ud*2)].getState() == PenteGameBoard.EMPTY)
						{
	
							if(myGame.getBoard()[r + (rl* 3)][c + (ud* 3)].getState() ==  myStone)
							{
								setMove(r + (rl * 2),c + (ud *2), THREEE_OFF, OFFENSE);
							}
						}
					}
				} catch (ArrayIndexOutOfBoundsException e)
				{
					//System.out.println("off the board in findOneDef at [" + r + "][" + c + "]");
				}
			}
		}
	}
	
	public void findFourEOff(int r, int c)
	{
		for(int rl = -1; rl <= 1; rl++)
		{
			for(int ud = -1; ud <= 1; ud++)
			{
				try 
				{
					if(myGame.getBoard()[r + rl][c + ud].getState() == myStone)
					{
						if(myGame.getBoard()[r + (rl*2)][c + (ud*2)].getState() == myStone)
						{
	
							if(myGame.getBoard()[r + (rl* 3)][c + (ud* 3)].getState() ==  PenteGameBoard.EMPTY)
							{
								if(myGame.getBoard()[r + (rl*4)][c + (ud*4)].getState() == myStone)
								{
									setMove(r + (rl * 3),c + (ud *3), FOURE_OFF, OFFENSE);
								}
							}
						}
					}
				} catch (ArrayIndexOutOfBoundsException e)
				{
					//System.out.println("off the board in findOneDef at [" + r + "][" + c + "]");
				}
			}
		}
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
	
	public void setPriorities()
	{
		Collections.sort(allMoves);
		int range = 0;
		while(range + 1 < allMoves.size() && allMoves.get(range).getPriority() == allMoves.get(range + 1).getPriority())
			range++;
		
	}
	
	public void PrintPriorities()
	{
		for(CMObject m: allMoves)
		{
			System.out.println(m);
		}
		
	}
	
}
