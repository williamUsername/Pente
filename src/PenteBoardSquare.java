import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class PenteBoardSquare {
	
	private int xLoc, yLoc;
	private int sWidth, sHeight;
	
	private int sState; //whitch plaer/ no player
	
	private Color sColor; //square color
	private Color lColor; //Line color
	private Color bColor; //boarder color
	private Color iColor; //inner squares
	private Color darkStone;
	private Color shadowGrey; 
	private Color whiteStone;
	private Color whiteStoneBright;
	private Color darkStoneBright;


	private boolean isInner = false;
	
	//constructure
	
	public PenteBoardSquare(int x, int y, int w, int h)
	{
		xLoc = x;
		yLoc = y;
		sWidth = w;
		sHeight = h;
		
		sColor = new Color(249, 218, 124);
		lColor = new Color(255, 171, 102);
		bColor = new Color(137, 79, 31);
		iColor = new Color(255, 218, 188);
		darkStone = new Color(18, 18, 7);
		whiteStone = new Color(250, 243, 229);
		shadowGrey = new Color(107, 98, 83);
		darkStoneBright = new Color(35, 29, 18);
		whiteStoneBright = new Color(255, 248, 234);


		sState = PenteGameBoard.EMPTY;

	}
	
	public void setInner()
	{
		isInner = true;
	}
	
	
	public void drawMe(Graphics g)
	{
		if(isInner)
		{
			g.setColor(iColor);
		}else {
			g.setColor(sColor);
		}
		g.fillRect(xLoc, yLoc, sWidth, sHeight);
		
		
		//boarder color
		
		g.setColor(bColor);
		g.drawRect(xLoc, yLoc, sWidth, sHeight);
		
		
		if(sState != PenteGameBoard.EMPTY)
		{
			g.setColor(shadowGrey);
			g.fillOval(xLoc, yLoc + 6, sWidth - 8, sHeight - 8);
		}
		
		g.setColor(lColor);
		g.drawLine(xLoc, yLoc + sHeight/2, xLoc + sHeight, yLoc + sHeight/2);
		
		
		g.drawLine(xLoc + sWidth/2, yLoc, xLoc + sWidth/2, yLoc + sWidth);
		
		
		if(sState == PenteGameBoard.BLACK)
		{
			g.setColor(darkStone);
			g.fillOval(xLoc + 3, yLoc + 3, sWidth - 6, sHeight - 6);
			g.setColor(darkStoneBright);
			g.fillOval(xLoc + 8, yLoc + 3, sWidth - 12, sHeight - 10);
			
		//	Graphics2D g2 = (Graphics2D) g;
		//	g2.setStroke(new BasicStroke(3));
		//	
		//	g2.setColor(darkStoneBright);
		//	
		//	g2.setStroke(new BasicStroke(1));
		//	
		//	g2.drawArc(xLoc + (int)(sWidth * 0.45),
		//			yLoc + 10,
		//			(int) (sWidth * .3),
		//			(int)(sHeight * .35),
		//			0,
		//			90
		//			);
			

		} 
		
		if(sState == PenteGameBoard.WHITE)
		{
			g.setColor(whiteStone);
			g.fillOval(xLoc + 3, yLoc + 3, sWidth - 6, sHeight - 6);
			g.setColor(whiteStoneBright);
			g.fillOval(xLoc + 8, yLoc + 3, sWidth - 12, sHeight - 10);
		}
		
	}
	
	public void setState(int newState)
	{
		if(newState < -1 || newState > 1) 
		{
			System.out.println( newState + " is illigal");
		} else
		{
			sState = newState;
		}
	}
	
	
}
