import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PenteGameBoard  extends JPanel{
	
	public static final int EMPTY = 0;
	public static final int BLACK = 1;
	public static final int WHITE = -1;
	public static final int SIDE_SQUARES = 19;
	public static final int INNERSTART = 7;
	public static final int INNEREND = 11;
	public static final int PLAYER1_TURN =1;
	public static final int PLAYER2_TURN =-1;

	
	int bWidth, bHeight;

	private PenteBoardSquare testSquare;
	private int squareW, squareH;
	private JFrame myFrame;
	
	int playerTurn;
	boolean player1IsComputer = false;
	boolean player2IsComputer = false;
	String p1Name, p2Name;
	 

	
	//data structure for board
	private PenteBoardSquare [][] gameBoard;
	
	public PenteGameBoard(int w, int h)
	{
		bWidth = w;
		bHeight = h;
		
		//myGame = g;
		
		this.setSize(w, h);
		this.setBackground(Color.CYAN);
		
		squareW = bWidth/this.SIDE_SQUARES;
		squareH = bHeight/this.SIDE_SQUARES;
		
	//	testSquare = new PenteBoardSquare(0, 0, squareW, squareH);
		gameBoard = new PenteBoardSquare[SIDE_SQUARES][SIDE_SQUARES];
		
		for(int j = 0; j < SIDE_SQUARES; j++)
		{
			for(int i = 0; i < SIDE_SQUARES; i++)
			{
				gameBoard[j][i] = new PenteBoardSquare(i*squareW, j*squareH, squareW, squareH);
				
				if( i >= INNERSTART && i <= INNEREND) 
				{
					if( j >= INNERSTART && j <= INNEREND)
					{
						gameBoard[j][i].setInner();
					}
				}
				
				
			if((j +i) % 2 == 0)
			{
				gameBoard[j][i].setState(BLACK);
			} else
			{
				gameBoard[j][i].setState(WHITE);
			}	
			}
		}
		
	}
	
	public void paintComponent(Graphics g) {
		
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, bWidth, bHeight);
		
		for(int j = 0; j < SIDE_SQUARES; j++)
		{
			for(int i = 0; i < SIDE_SQUARES; i++)
			{
				gameBoard[j][i].drawMe(g);

			}
		}	
	}
	
	public void resetBoard()
	{
		for(int j = 0; j < SIDE_SQUARES; j++)
		{
			for(int i = 0; i < SIDE_SQUARES; i++)
			{
				gameBoard[j][i].setState(EMPTY);;

			}
		
		}

	}
	
	public void startGame()
	{
		resetBoard();
		
		p1Name = JOptionPane.showInputDialog("Name of player 1(od type 'c' for computer");
		if( p1Name.equals('c') || p1Name.equals("computer") ||  p1Name.equals("comp"))
		{
			player1IsComputer = true;
		}
		p2Name = JOptionPane.showInputDialog("Name of player 2(od type 'c' for computer");
		if( p2Name.equals('c') || p2Name.equals("computer") ||  p2Name.equals("comp"))
		{
			player2IsComputer = true;
		}
	
		 playerTurn = this.PLAYER1_TURN;
		 this.gameBoard[SIDE_SQUARES/2] [SIDE_SQUARES/2].setState(BLACK);;
		
		 
		 changePlayerTurn();
		 
		 
		 this.repaint();
	}
	 
	
	public void changePlayerTurn()
	{
		playerTurn *= -1;
	}
/*	
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
		for(int j = 0; j < SIDE_SQUARES; j++)
		{
			for(int i = 0; i < SIDE_SQUARES; i++)
			{
				gameBoard[j][i].setXLoc(i * w);
				gameBoard[j][i].setYLoc(j * h);
				gameBoard[j][i].setWidth(w);
				gameBoard[j][i].setHeight(h);



			}
		}
	}

*/
}
