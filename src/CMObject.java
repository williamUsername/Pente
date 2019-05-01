
public class CMObject {
	
	//Data
	
	private int priority = 0;
	private int row = -1, col = -1;
	private int moveType = 0;
	
	
	public void setPriority(int newP)
	{
		priority = newP;
	}
	
	public void setRow (int newR)
	{
		row = newR;
	}
	
	public void setCol(int newC)
	{
		col = newC;
	}
	
	public void setMoveType (int newT)
	{
		moveType = newT;
	}
	
	//SET ^^^ GET>>>
	
	
	public void getPriority(int newP)
	{
		priority = newP;
	}
	
	public void getRow (int newR)
	{
		row = newR;
	}
	
	public void getCol(int newC)
	{
		col = newC;
	}
	
	public void getMoveType (int newT)
	{
		moveType = newT;
	}
	

}
