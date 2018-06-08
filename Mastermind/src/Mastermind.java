import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Mastermind extends Canvas implements MouseListener, ActionListener {
	private static final int ROWS = 12;
	/* display width and height */
	private int dw, dh, currentColor, currentRow;

	private Color colors[] = { Color.red,  Color.green,   Color.blue,
                             Color.cyan, Color.magenta, Color.yellow };

	/* the players guesses */
	private Color guesses[][] = new Color[ROWS][4];
	/* the programs responses */
	private Color responses[][] = new Color[ROWS][4];

	private int[] input = new int[4], bw = new int[2];

	private MastermindModel mm = new MastermindModel();
	private boolean displayAnswer;//use later
	private View view;

	/**
	 * Constructor for Mastermind Controller
	 */
	public Mastermind() {
		reset();

		JFrame frame = new JFrame( "Mastermind" );
		/* exit if we close the window */
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setBackground( Color.white );

		/* just one button to submit each guess */
		JButton submit = new JButton( "Submit guess" );
		submit.addActionListener( this );
		/* we need to know mouse coords to place colored pegs */
		addMouseListener( this );

		frame.getContentPane().add( this );
		/* put the button on the bottom of the frame */
		frame.getContentPane().add( submit, BorderLayout.SOUTH );

		frame.setSize( new Dimension( 220, 450 ) );
		frame.setVisible( true );

		view = new View(mm, guesses, responses);
	}

	/**
	 * Called when the Submit Guess button is pressed.
	 */
	public void actionPerformed( ActionEvent e ) {
		if (e.getActionCommand().equals("Submit guess")) {
			/*
				implement win condition!!!
				implement lose condition!!!
			 */
			mm.guess(input, bw);
			
			if(bw[0]==4){
				System.out.println("You win!");
				reset();
			}
			
			//put colors into guesses
			for(int i=0; i<4; i++){
				guesses[currentRow][i]=colors[input[i]];
			}
			
			if(displayAnswer){
				//make into function this!!!
				int[] answer = mm.getAnswer();
				System.out.print("ANSWER-------->");
				for(int i = 0; i<4; i++){
					System.out.print(""+answer[i]);
				}
				System.out.println();
			}
			
			//put pegs in responses
			int pointer = 0;
			for(int i=0; i<bw[0]; i++){
				responses[currentRow][pointer]=Color.black;
				pointer++;
			}
			for(int i=0; i<bw[1]; i++){
				responses[currentRow][pointer]=Color.white;
				pointer++;
			}

			currentRow++;
			
			if(currentRow>11){
				System.out.println("You lose!");
				reset();
			}
		} else {
			((JButton)e.getSource()).setText( "Submit guess" );
			reset();
		}
		repaint();
	}

	public void reset() {
		for (int i=0; i < ROWS; i++) {
			for (int j=0; j < 4; j++) {
				guesses[i][j] = Color.white;
				responses[i][j] = Color.lightGray;
			}
		}
		currentRow = 0;
		currentColor = 0;
		displayAnswer = false;
	}

	/**
	 * Called when the mouse button is pressed to select colors
	 * and to indicate which square to add a peg to
	 */
	public void mousePressed( MouseEvent e ) {
		/* get the x & y coords of the mouse position */
		int x = e.getX(), y = e.getY();
		
		//select color
		if(dh*(ROWS+1) + (dh/2) < y && y < dh*(ROWS+2) + (dh/2)){
			for (int i=0; i < 6; i++) {
				if(i*dw+dw/2 < x && x < (i*dw+dw/2)+dw){
					currentColor=i;
					System.out.println("Color selected: " + i);
				}
			} 
		}
		
		//select column
		//display colors!!!
		if(dh < y && y < (ROWS+1)*dh){
			for (int i=0; i < 4; i++) {
				if((i+1)*dw < x && x < (i+2)*dw ){
					input[i]=currentColor;
					System.out.println("Column selected: " + i+1);
					repaint();
				}
			} 
		}
		
		//display answer
		if(y < dh){
			displayAnswer = true;
		}
	}

	public void mouseEntered(  MouseEvent e ) { }

	public void mouseExited(   MouseEvent e ) { }

	public void mouseClicked(  MouseEvent e ) { }

	public void mouseReleased( MouseEvent e ) { }

	/* the paint method we need 
	 * remember we're a ContentPane here
	 * Don't call paint, call repaint instead
	 */
	public void paint( Graphics g ) {
		dw = (int) getSize().width / 7;
		dh = (int) getSize().height / (ROWS+3);
		view.fillBoard( g, dw, dh, currentRow );
		view.drawBoard( g, dw, dh );
	}
	
	public static void main( String[] args ) {
		new Mastermind();	
	}
}
