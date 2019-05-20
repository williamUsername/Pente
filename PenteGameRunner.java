import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class PenteGameRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int gWidth = 19*35;
		int gHeight = 19*35;
		int sbWidth = (int)(gWidth *0.50);
		int sbHeight = (int)(gHeight);
		
		JFrame theGame = new JFrame("Pente!!!");
		
		theGame.setLayout(new BorderLayout());
		theGame.setResizable(false);
		theGame.setSize(gWidth + sbWidth, gHeight +20);
		theGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		PenteScore sb = new PenteScore(sbWidth, sbHeight);
		sb.setPreferredSize(new Dimension(sbWidth, sbHeight));
		
		PenteGameBoard gb = new PenteGameBoard(gWidth, gHeight, sb);
		gb.setPreferredSize(new Dimension(gWidth, gHeight));
		
		sb.setGameBoard(gb);
		
		theGame.add(gb, BorderLayout.CENTER);
		theGame.add(sb,  BorderLayout.WEST);

		
		theGame.setVisible(true);
		gb.startGame(true);

	}

}
