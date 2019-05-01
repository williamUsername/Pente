import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class PenteScore extends JPanel implements ActionListener{

	private JLabel p1Name;
	private JLabel p2Name;
	private JTextField p1Captures;
	private JTextField p2Captures;
	private JTextField whoseTurnField;
	
	private JButton resetButton;
	
	private Color backColor;
	
	private int spWidth;
	private int spHeight;
	
	private Font myFont = new Font("Arial", Font.PLAIN, 24);
	private Color bBlack = new Color(5, 11, 91);
	private PenteGameBoard myBoard = null;
	
	private boolean firstGame = true;
	
	public PenteScore(int w, int h)
	{
		backColor = new Color(118, 168, 247);
		
		spWidth = w;
		spHeight = h;
		
		
		this.setSize(spWidth, spHeight);
		this.setBackground(backColor);
		
		this.setVisible(true);
		
		addInfo();
	}
	
	public void addInfo()
	{
		JPanel p1Panel = new JPanel();
		//p1Panel.setLayout(new BoxLayout(p1Panel, BoxLayout.PAGE_AXIS));
		
		p1Panel.setLayout(new BoxLayout(p1Panel, BoxLayout.Y_AXIS));
		
		
		p1Panel.setSize(spWidth, (int)(spHeight * 0.45));
		p1Panel.setBackground(new Color(137, 156, 245));
		//p1Panel.setOpaque(false);
		
		p1Name = new JLabel("Player 1");
		p1Name.setAlignmentX(Component.CENTER_ALIGNMENT);
		p1Name.setFont(myFont);
		p1Name.setForeground(bBlack);		
		p1Name.setBackground(Color.WHITE);
		p1Name.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		p1Captures = new JTextField("Player 1 Captures");
		p1Captures.setAlignmentX(Component.CENTER_ALIGNMENT);
		p1Captures.setFont(myFont);
		p1Captures.setForeground(Color.WHITE);
		p1Captures.setBackground(bBlack);
		p1Captures.setHorizontalAlignment(SwingConstants.CENTER);
		p1Captures.setFocusable(false);

		p1Panel.add(Box.createRigidArea(new Dimension(spWidth - 40,30)));
		p1Panel.add(p1Name);
		p1Panel.add(Box.createRigidArea(new Dimension(spWidth - 40,30)));
		p1Panel.add(p1Captures);
		p1Panel.add(Box.createRigidArea(new Dimension((spWidth - 40),30)));

		
		Border b1 = BorderFactory.createLineBorder(Color.BLUE, 4, true);
		
		p1Panel.setBorder(b1);
		
		this.add(Box.createRigidArea(new Dimension(spWidth - 40, 30)));
		this.add(p1Panel);
		this.add(Box.createRigidArea(new Dimension(spWidth - 40, 30)));

		resetButton = new JButton("New Game");
		resetButton.setFont(myFont);
		this.add(resetButton);
		resetButton.addActionListener(this);

		
		JPanel p2Panel = new JPanel();
		//p2Panel.setLayout(new BoxLayout(p2Panel, BoxLayout.PAGE_AXIS));
		
		p2Panel.setLayout(new BoxLayout(p2Panel, BoxLayout.Y_AXIS));
		
		
		p2Panel.setSize(spWidth, (int)(spHeight * 0.45));
		p2Panel.setOpaque(false);
		
		p2Name = new JLabel("Player 2 ");
		p2Name.setAlignmentX(Component.CENTER_ALIGNMENT);
		p2Name.setFont(myFont);
		p2Name.setForeground(Color.WHITE);
		p2Name.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		p2Captures = new JTextField("Player 2 Captures");
		p2Captures.setAlignmentX(Component.CENTER_ALIGNMENT);
		p2Captures.setFont(myFont);
		p2Captures.setForeground(bBlack);
		p2Captures.setHorizontalAlignment(SwingConstants.CENTER);
		p2Captures.setFocusable(false);


		p2Panel.add(Box.createRigidArea(new Dimension(spWidth - 40,30)));
		p2Panel.add(p2Name);
		p2Panel.add(Box.createRigidArea(new Dimension(spWidth - 40,30)));
		p2Panel.add(p2Captures);
		p2Panel.add(Box.createRigidArea(new Dimension((spWidth - 40),30)));

		
		Border b2 = BorderFactory.createLineBorder(Color.BLUE, 4, true);
		
		p2Panel.setBorder(b2);
		
		this.add(Box.createRigidArea(new Dimension(spWidth - 40, 40)));
		this.add(p2Panel);
		
		
		JPanel whoseTurn = new JPanel();
		whoseTurn.setLayout(new BoxLayout(whoseTurn, BoxLayout.Y_AXIS));
		whoseTurn.setSize(spWidth, (int)(spHeight * 0.45));
		whoseTurn.setOpaque(false);
		
		
		whoseTurnField = new JTextField("Its ???? Turn Now");
		//whoseTurnField.setLayout(new BoxLayout(whoseTurnField, BoxLayout.Y_AXIS));
		whoseTurnField.setAlignmentX(Component.CENTER_ALIGNMENT);
		whoseTurnField.setFont(myFont);
		whoseTurnField.setForeground(bBlack);
		whoseTurnField.setHorizontalAlignment(SwingConstants.CENTER);
		whoseTurnField.setFocusable(false);

							
		
		// ADD BORDER 
		whoseTurn.add(Box.createRigidArea(new Dimension(spWidth - 40, 30)));
		whoseTurn.add(whoseTurnField);
		whoseTurn.add(Box.createRigidArea(new Dimension(spWidth - 40, 30)));

		Border b3 = BorderFactory.createLineBorder(Color.BLUE, 4, true);
		
		whoseTurn.setBorder(b3);
		
		this.add(Box.createRigidArea(new Dimension(spWidth - 40, 30)));
		this.add(whoseTurn);
		
	}
	
	
	
	public void setName(String n, int whichPlayer)
	{
		
		if(whichPlayer == PenteGameBoard.BLACK)
		{
			p1Name.setText("Player 1: " + n);
		}else
		{
			p2Name.setText("Player 2: " + n);
		}
		
		repaint();
	}
	
	public void setCaptures(int c, int whichPlayer)
	{
		if(whichPlayer == PenteGameBoard.BLACK) 
		{
			p1Captures.setText( Integer.toString(c));
			Rectangle r = p1Captures.getVisibleRect();
			p1Captures.paintImmediately(r);
		}else
		{
			p2Captures.setText( Integer.toString(c));
			Rectangle r = p2Captures.getVisibleRect();
			p2Captures.paintImmediately(r);

		}
	}
	
	public void setPlayerTurn(int whichPlayer)
	{
		if(whichPlayer == PenteGameBoard.BLACK)
		{
			whoseTurnField.setBackground(bBlack);
			whoseTurnField.setForeground(Color.WHITE);
			//System.out.println(p1Name.getText());
			int cLoc = p1Name.getText().indexOf(";");
			String n  = p1Name.getText().substring(cLoc + 2, p1Name.getText().length());
			whoseTurnField.setText("it's " + n + "'s turn now");
			
		}else
		{
			whoseTurnField.setBackground(Color.WHITE);
			whoseTurnField.setForeground(bBlack);
			//System.out.println(p2Name.getText());
			int cLoc = p2Name.getText().indexOf(";");
			String n  = p2Name.getText().substring(cLoc + 2, p2Name.getText().length());
			whoseTurnField.setText("it's " + n + "'s turn now");		
		}
	
		
		if(firstGame)
		{
			whoseTurnField.repaint();
		} else
		{
			Rectangle r = whoseTurnField.getVisibleRect();
			whoseTurnField.paintImmediately(r);
		}
	
	}
	
	
	
	
	
	public void setGameBoard(PenteGameBoard gb)
	{
		myBoard =gb;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		JOptionPane.showMessageDialog(null, "Starting New Game!");
		firstGame = false;
		
		
		if(myBoard != null)
		{
			myBoard.startGame(false);
		}
		
	}
	
	
	
	
	
}
