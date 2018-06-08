import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class View {
	private static final int ROWS = 12;
	private int dw, dh, currentColor, currentRow;

	private Color colors[] = { Color.red,  Color.green,   Color.blue,
			Color.cyan, Color.magenta, Color.yellow };

	private Color guesses[][];
	private Color responses[][];

	private MastermindModel mm;
	private boolean displayAnswer;

	/**
	 * Constructor for Mastermind View
	 * we need the Model to talk to.
	 * we need the reference to guesses and responses to
	 * paint the correct colors
	 */
	public View(MastermindModel m, Color guesses[][], Color responses[][])
	{
		this.mm = m;
		this.guesses = guesses;
		this.responses = responses;
	}

	/*
	 * this fills the grid with the correct colored pegs
	 * and fills the little 2 x 2 grid with the correct
	 * black and white pegs. Empty grid squares here are gray.
	 */
	public void fillBoard( Graphics g, int dw, int dh, int curRow ) {
		this.currentRow = curRow;

		for(int j=0; j<currentRow; j++){	
			for (int i=0; i < 4; i++) {

				//player's guesses
				g.setColor(guesses[j][i]);
				g.fillRect( (i+1)*dw, dh*(j+1), dw, dh );

				//black & white pegs
				//cells look a bit off
				g.setColor(responses[j][i]);
				if(i<2){
					g.fillRect(5*dw + i*(dw/2) , dh*(j+1), dw/2, dh/2);
				}else{
					g.fillRect(4*dw + i*(dw/2) , dh*(j+1)+dh/2, (dw/2), (dh/2));
				}
			}
		}
		//displayAnswer=true;
		if(displayAnswer){
			int[] answer = mm.getAnswer();
			
			for (int i=0; i < 4; i++) {
				g.setColor(colors[answer[i]]);
				g.fillRect( (i+1)*dw, 0, dw, dh );
			}
		}
	}

	/*
	 * draw the grids
	 */
	public void drawBoard( Graphics g, int dw, int dh ) {
		g.setColor( Color.black );
		// draw the rows
		for (int i=0; i < ROWS+1; i++) {
			g.drawLine( dw, dh*(i+1), 6*dw, dh*(i+1) );
		}
		// draw the columns
		for (int i=0; i <  6; i++) {
			g.drawLine( dw*(i+1), dh, dw*(i+1), (ROWS+1)*dh );
		}
		// draw the black-white horizontal dividers
		for (int i=0; i < ROWS; i++) {
			g.drawLine( dw*5, (i+1)*dh+dh/2, dw*6, (i+1)*dh+dh/2 );  
		}
		// draw the black-white vertical divider
		g.drawLine( dw*5+dw/2, dh, dw*5+dw/2, dh*(ROWS+1) );  

		// draw the color palette
		for (int i=0; i < 6; i++) {
			g.setColor( colors[i] );
			g.fillRect( i*dw+dw/2, dh*(ROWS+1)+dh/2, dw, dh );  
		}
	}
}
