import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PenteGameBoard  extends JPanel implements MouseListener{
	
	public static final int EMPTY = 0;
	public static final int BLACK = 1;
	public static final int WHITE = -1;
	public static final int SIDE_SQUARES = 19;
	public static final int INNERSTART = 7;
	public static final int INNEREND = 11;
	public static final int PLAYER1_TURN =1;
	public static final int PLAYER2_TURN =-1;
	public static final int MAX_CAPTURES = 10;
	public static final int SLEEP_TIME = 100;


	
	int bWidth, bHeight;

//	private PenteBoardSquare testSquare;
	private int squareW, squareH;
	private JFrame myFrame;
	
	private int playerTurn;
	private boolean player1IsComputer = false;
	private boolean player2IsComputer = false;
	private String p1Name, p2Name;
	private boolean darkStoneMove2Taken = false;
	
	private boolean gameOver = false;
	 

	
	//data structure for board
	private PenteBoardSquare [][] gameBoard;
	private PenteScore myScoreBoard;
	private int p1Captures, p2Captures;
	
	private ComputerMoveGenerator p1ComputerPlayer = null;
	private ComputerMoveGenerator p2ComputerPlayer = null;
	
	public PenteGameBoard(int w, int h, PenteScore sb)
	{
		bWidth = w;
		bHeight = h;
		myScoreBoard = sb;
		
		p1Captures = 0;
		p2Captures = 0;
		
		//myGame = g;
		
		this.setSize(w, h);
		this.setBackground(Color.CYAN);
		
		squareW = bWidth/this.SIDE_SQUARES;
		squareH = bHeight/this.SIDE_SQUARES;
		
	//	testSquare = new PenteBoardSquare(0, 0, squareW, squareH);
		gameBoard = new PenteBoardSquare[SIDE_SQUARES][SIDE_SQUARES];
		
		for(int row = 0; row < SIDE_SQUARES; row++)
		{
			for(int col = 0; col < SIDE_SQUARES; col++)
			{
				gameBoard[row][col] = new PenteBoardSquare(col*squareW, row *squareH, squareW, squareH);
				
				if( col >= INNERSTART && col <= INNEREND) 
				{
					if( row >= INNERSTART && row <= INNEREND)
					{
						gameBoard[row][col].setInner();
					}
				}
			}
		}
	
		
		
		this.initialBoard();
		
		repaint();
		
		addMouseListener(this);
		this.setFocusable(true);
		
	}
	
	public void paintComponent(Graphics g) {
		
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, bWidth, bHeight);
		
		for(int row = 0; row < SIDE_SQUARES; row++)
		{
			for(int col = 0; col < SIDE_SQUARES; col++)
			{
				gameBoard[row][col].drawMe(g);

			}
		}	
	}
	
	public void resetBoard()
	{
//		p1Captures = 0;
//		p1Captures = 0;
//		
		for(int row = 0; row < SIDE_SQUARES; row++)
		{
			for(int col = 0; col < SIDE_SQUARES; col++)
			{
				gameBoard[row][col].setState(EMPTY);
				gameBoard[row][col].setWinningSquare(false);
				gameOver = false;

			}
	
		}

		this.repaint();
	}
	
	public void startGame(boolean firstGame)
	{
		p1Captures = 0;
		p2Captures = 0;
		
		if(firstGame)
		{
			p1Name = JOptionPane.showInputDialog("Name of player 1 of type 'c' for computer");
			if(p1Name != null && (p1Name.toLowerCase().equals("c") || p1Name.toLowerCase().equals("computer") ||  p1Name.toLowerCase().equals("comp")))
			{
				player1IsComputer = true;
				p1ComputerPlayer = new ComputerMoveGenerator(this, BLACK);
			//	System.out.println("P1 is computer");

			} 
		}	
			
			this.myScoreBoard.setName(p1Name, BLACK);
			myScoreBoard.setCaptures(p1Captures, BLACK);
			
	if(firstGame)
	{
		
			p2Name = JOptionPane.showInputDialog("Name of player 2 of type 'c' for computer");
			if(p1Name != null && (p2Name.toLowerCase().equals("c") || p2Name.toLowerCase().equals("computer") ||  p2Name.toLowerCase().equals("comp")))
			{
				player2IsComputer = true;
				p2ComputerPlayer = new ComputerMoveGenerator(this, WHITE);
			//	System.out.println("P2 is computer");

			}
	}	
			this.myScoreBoard.setName(p2Name, WHITE);
			myScoreBoard.setCaptures(p2Captures, WHITE);
	
		
		resetBoard();

		 playerTurn = PLAYER1_TURN;
		 this.gameBoard[SIDE_SQUARES/2] [SIDE_SQUARES/2].setState(BLACK);;
		 this.darkStoneMove2Taken = false;
		 changePlayerTurn();
		 
		 
		 checkForComputerMove(playerTurn);
		 
		 this.repaint();
	}
	 
	
	public void changePlayerTurn()
	{
		playerTurn *= -1;
		myScoreBoard.setPlayerTurn(playerTurn);
		
	}
	
	public void updateSizes() 
	{
		if(myFrame.getWidth() != bWidth || myFrame.getHeight() != bHeight + 20)
		{
			bWidth = myFrame.getWidth();
			bHeight = myFrame. getHeight() - 20;
			
			squareW = bWidth/this.SIDE_SQUARES;
			squareH = bHeight/this.SIDE_SQUARES;
			
			resetSquares(squareW, squareH);
			

		}
	}
	
	
	
	
	public void resetSquares(int w, int h)
	{
		for(int row = 0; row < SIDE_SQUARES; row++)
		{
			for(int col = 0; col < SIDE_SQUARES; col++)
			{
				gameBoard[row][col].setXLoc(col * w);
				gameBoard[row][col].setYLoc(row * h);
				gameBoard[row][col].setWidth(w);
				gameBoard[row][col].setHeight(h);



			}
		}
	}
	public boolean fiveInARow(int whichPlayer)
	{
		boolean isFive = false;
		
		
		for(int row = 0; row < SIDE_SQUARES; row++)
		{
			for(int col = 0; col < SIDE_SQUARES; col++)
			{
				for(int rl = -1; rl <= 1; rl++)
				{
					for(int ud = -1; ud <= 1; ud++)
					{
						if(fiveCheck(row, col, whichPlayer, rl, ud))
						{
							isFive = true;
						}
		
					}
				}
			}
		}
		
		
		
		
		
		return isFive;
	}
	
	
	
	
	
	
	public boolean fiveCheck(int r, int c, int pt, int rightLeft, int upDown)
	{
		
		boolean win = false;
		try 
		{
		
			if(rightLeft != 0 || upDown != 0)
			{
				
				if(gameBoard[r][c].getState() == pt)
				{
			     
					if(gameBoard[r + upDown][c+ rightLeft].getState() == pt)
					{
						if(gameBoard[r + upDown*2][c+ rightLeft*2].getState() == pt)
						{
							if(gameBoard[r + upDown * 3][c+ rightLeft *3].getState() == pt)
							{
								if(gameBoard[r + upDown * 4][c+ rightLeft * 4].getState() == pt)
								{
									
									win = true;
									gameBoard[r + upDown][c+ rightLeft].setWinningSquare(true);
									gameBoard[r + upDown * 2][c+ rightLeft * 2].setWinningSquare(true);
									gameBoard[r + upDown * 3][c+ rightLeft * 3].setWinningSquare(true);
									gameBoard[r + upDown * 4][c+ rightLeft * 4].setWinningSquare(true);
									
								//	System.out.println("YOU WIN" + rightLeft + "," +upDown);
								}
							}
						}
					}
				}
			}
					/*
				if(p1Captures == 10 || p2Captures == 10)
				{
					win = true;
				}
				*/
				return win;
		}catch(ArrayIndexOutOfBoundsException e)
		{
			//System.out.println("you have an error " + e.getMessage());
			return false;
			
	}
				
			
		
	}
	
	public void checkForWin(int whichPlayer)
	{
		if(whichPlayer == this.PLAYER1_TURN)
		{
			if(this.p1Captures >= MAX_CAPTURES)
			{
				JOptionPane.showMessageDialog(null, "Congrats " + p1Name + " you won through captures.");
				gameOver = true;
			}else
			{
				if(fiveInARow(whichPlayer))
				{
					JOptionPane.showMessageDialog(null, "Congrats " + p1Name + " you won through five in a row.");
					gameOver = true;
				}
			}
		}
		
		if(whichPlayer == this.PLAYER2_TURN)
		{
			if(this.p2Captures >= MAX_CAPTURES)
			{
				JOptionPane.showMessageDialog(null, "Congrats " + p2Name + " you won through captures.");
				gameOver = true;
			}else
			{
				if(fiveInARow(whichPlayer))
				{
					JOptionPane.showMessageDialog(null, "Congrats " + p2Name + " you won through five in a row.");
					gameOver = true;
				}
			}
		}
	}
	
	
	
	
	public void checkClick(int clickX, int clickY)
	{
				
		if(!gameOver)
		{
			for(int row = 0; row < SIDE_SQUARES; row++)
			{
				for(int col = 0; col < SIDE_SQUARES; col++)
				{
					boolean squareClicked = gameBoard[row][col].isClicked(clickX, clickY);
						if(squareClicked)
						{
					//		System.out.println("You clicked the Square at [" + row + "," + col + "]");
								if(gameBoard[row][col].getState() == EMPTY)
								{
									 if(!darkSquareProb(row, col))
									 {
										 gameBoard[row][col].setState(playerTurn);
										 checkForCaptures(row, col, playerTurn);
										 //this.repaint();
										 this.paintImmediately(0, 0, this.bWidth, this.bHeight);
										 checkForWin(playerTurn);
										 this.changePlayerTurn();
										 checkForComputerMove(playerTurn);
	
									 }else
									 {		
											JOptionPane.showMessageDialog(null, "Second Dark move must be outside of the different color square");
									 }
								}else
								{
									JOptionPane.showMessageDialog(null, "this square is taken, click on another");
								}
						}
				}
		
			}
		}
	}
	
	public void checkForComputerMove(int whichPlayer)
	{
		
		if(whichPlayer == this.PLAYER1_TURN && this.player1IsComputer)
		{ 
			
			int[] nextMove = this.p1ComputerPlayer.getComputerMove();
			int newR = nextMove[0];
			int newC = nextMove[1];
			gameBoard[newR][newC].setState(playerTurn);
			repaint();
			checkForCaptures(newR, newC, playerTurn);
			this.paintImmediately(0, 0, bWidth, bHeight);
			//repaint();
			this.checkForWin(playerTurn);
			if(!gameOver)
			{
				this.changePlayerTurn();
				checkForComputerMove(playerTurn);
			}
		}else if(whichPlayer == this.PLAYER2_TURN && this.player2IsComputer)
		{
			int[] nextMove = this.p2ComputerPlayer.getComputerMove();
			int newR = nextMove[0];
			int newC = nextMove[1];
			gameBoard[newR][newC].setState(playerTurn);
			repaint();
			checkForCaptures(newR, newC, playerTurn);
			//this.repaint();
			this.paintImmediately(0, 0, bWidth, bHeight);
			this.checkForWin(playerTurn);
			if(!gameOver)
			{
				this.changePlayerTurn();
				checkForComputerMove(playerTurn);

			}
			
		}
	
		
		repaint();
		
	}
	
	
	public boolean darkSquareProb(int r, int c)
	{
		boolean dsp = false;
		
		if(!darkStoneMove2Taken &&
				playerTurn == BLACK)
		{
			if(r >= INNERSTART && r <= INNEREND &&
				c >= INNERSTART && c <= INNEREND)
					{
						dsp = true;
					}else
					{
						darkStoneMove2Taken = true;
					}
			
		}
		
		
		return dsp;
	}
	
	public void checkForCaptures(int r, int c, int pt)
	{
		boolean didCaptures;
		
		for(int rl = -1; rl <= 1; rl++)
		{
			for(int ud = -1; ud <= 1; ud++)
			{
				didCaptures = checkCapture(r, c, pt, ud, rl);

			}
		}
		
	}
	
		
	/*	 //col
		didCaptures = checkCapture(r, c, pt, 1, 0, "RIGHT");
		didCaptures = checkCapture(r, c, pt, -1, 0, "LEFT");
	//row
		didCaptures = checkCapture(r, c, pt, 0, 1, "UP");
		didCaptures = checkCapture(r, c, pt, 0, -1, "DOWN");
	//diaganal
		didCaptures = checkCapture(r, c, pt, -1, 1, "DIAGANAL NORTHEAST");
		didCaptures = checkCapture(r, c, pt, 1, -1, "DIAGANAL NORTHWEST");
		
		didCaptures = checkCapture(r, c, pt, -1, -1, "DIAGANAL NORTHEAST");
		didCaptures = checkCapture(r, c, pt, 1, 1, "DIAGANAL NORTHWEST");
	}*/
	
	public boolean checkCapture(int r, int c, int pt, int rightLeft, int upDown)
	{
		
		try 
		{
		boolean cap = false;
		
		if(gameBoard[r + upDown][c + rightLeft].getState() == pt* -1 && 
				gameBoard[r+ upDown*2][c + rightLeft*2].getState() == pt* -1 &&
				gameBoard[r + upDown*3][c + rightLeft*3].getState() == pt)
		{
		//	System.out.println("IT'S A CAPTURE");
			gameBoard[r + upDown][c + rightLeft].setState(EMPTY);
			gameBoard[r + upDown * 2][c + rightLeft*2].setState(EMPTY);
			cap = true;
				if(pt == this.PLAYER1_TURN)
				{
					p1Captures = p1Captures+2;
					this.myScoreBoard.setCaptures(p1Captures, playerTurn);
				}else
				{
					p2Captures = p2Captures+2;
					this.myScoreBoard.setCaptures(p2Captures, playerTurn);

				}
			
		}
		return cap;
	
		
		} catch(ArrayIndexOutOfBoundsException e)
		{
			return false;
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	//	System.out.println("You Clicked [" + e.getX() + "," + getY() + "]");
		this.checkClick(e.getX(), e.getY());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
		
		
	
	@Override
	
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
public void initialBoard() {
		
		this.gameBoard[3][9].setState(BLACK);
		this.gameBoard[15][9].setState(BLACK);
		this.gameBoard[9][15].setState(BLACK);
		this.gameBoard[9][3].setState(BLACK);
		this.gameBoard[10][4].setState(BLACK);
		this.gameBoard[11][4].setState(BLACK);
		this.gameBoard[8][4].setState(BLACK);
		this.gameBoard[7][4].setState(BLACK);
		this.gameBoard[4][8].setState(BLACK);
		this.gameBoard[4][7].setState(BLACK);
		this.gameBoard[4][10].setState(BLACK);
		this.gameBoard[4][11].setState(BLACK);
		this.gameBoard[8][14].setState(BLACK);
		this.gameBoard[7][14].setState(BLACK);
		this.gameBoard[10][14].setState(BLACK);
		this.gameBoard[11][14].setState(BLACK);
		this.gameBoard[14][11].setState(BLACK);
		this.gameBoard[14][10].setState(BLACK);
		this.gameBoard[14][8].setState(BLACK);
		this.gameBoard[14][7].setState(BLACK);
		this.gameBoard[13][6].setState(BLACK);
		this.gameBoard[12][5].setState(BLACK);
		this.gameBoard[6][5].setState(BLACK);
		this.gameBoard[5][6].setState(BLACK);
		this.gameBoard[5][12].setState(BLACK);
		this.gameBoard[6][13].setState(BLACK);
		this.gameBoard[12][13].setState(BLACK);
		this.gameBoard[13][12].setState(BLACK);
		this.gameBoard[7][7].setState(WHITE);
		this.gameBoard[11][6].setState(WHITE);
		this.gameBoard[12][7].setState(WHITE);
		this.gameBoard[12][8].setState(WHITE);
		this.gameBoard[12][9].setState(WHITE);
		this.gameBoard[12][10].setState(WHITE);
		this.gameBoard[12][11].setState(WHITE);
		this.gameBoard[11][12].setState(WHITE);
		this.gameBoard[12][9].setState(WHITE);
		this.gameBoard[14][8].setState(BLACK);
		this.gameBoard[14][11].setState(BLACK);
		this.gameBoard[12][13].setState(BLACK);
		this.gameBoard[12][13].setState(BLACK);
		this.gameBoard[9][9].setState(WHITE);
		this.gameBoard[10][14].setState(BLACK);
		this.gameBoard[7][11].setState(WHITE);
		this.gameBoard[8][14].setState(BLACK);
		this.gameBoard[9][15].setState(BLACK);
		this.gameBoard[5][12].setState(BLACK);
		this.gameBoard[4][10].setState(BLACK);
		this.gameBoard[3][9].setState(BLACK);
		this.gameBoard[4][8].setState(BLACK);
		this.gameBoard[4][8].setState(BLACK);
		this.gameBoard[6][5].setState(BLACK);
		this.gameBoard[8][4].setState(BLACK);
		this.gameBoard[10][4].setState(BLACK);

	}

	public PenteBoardSquare[][] getBoard()
		{
			return gameBoard;
		}
	}

