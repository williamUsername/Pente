import javax.swing.JFrame;

public class PenteGameRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int gWidth = 19*35;
		int gHeight = 19*35;
		
		JFrame theGame = new JFrame("Pente!!!");
		theGame.setSize(gWidth, gHeight +20);
		theGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		PenteGameBoard gb = new PenteGameBoard(gWidth, gHeight);
		theGame.add(gb);
		
		theGame.setVisible(true);
		gb.startGame();

	}

}
