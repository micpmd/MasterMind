import java.util.Arrays;
import java.util.Random;

public class MastermindModel {
	public static final int NUMBER_OF_POSITIONS = 4;
	public static final int MAXIMUM_VALUE = 5;

	private Random rn = new Random();
	private int[] answer = new int[NUMBER_OF_POSITIONS];
	private int answerSums[] = new int[MAXIMUM_VALUE+1];

	public MastermindModel() {
		/* create the random code for the user to guess */
		for (int i=0; i < answer.length; i++)
			answer[i] = rn.nextInt( answerSums.length );

		/* Initialize the answer housekeeping array */
		for (int i=0; i < answer.length; i++)
			answerSums[ answer[i] ]++;
	}

	/*
	 * this method evaluates the user's input and determines
	 * how many black & white pegs to place
	 */
	public void guess( int[] input, int[] bw ) {
		
		int blackPegs = 0;
		int whitePegs = 0;
		bw[0] = blackPegs;
		bw[1] = whitePegs;
		int temp1[]=Arrays.copyOf(input, input.length);
		int temp2[]=Arrays.copyOf(answer, answer.length);

		for(int i = 0; i<temp2.length; i++){
			if(temp2[i]==temp1[i]){//look for matching color and position first
				blackPegs++;
				bw[0] = blackPegs;
				//System.out.print("black peg for "+guess[i]+".");

				temp2[i]=98;//"delete"/check-out guessed color
				temp1[i]=99;//"delete"/check-out guessed color
			}
		}

		for(int i = 0; i<temp2.length; i++){//look for matching color
			for(int j=0; j<temp1.length; j++){
				if(temp2[i]==temp1[j]){//check colors
					whitePegs++;
					bw[1] = whitePegs;
					//System.out.print("white peg for "+guess[j]+".");

					temp1[j]=99;//"delete"/check-out guessed color

					break;
				}
			}
		}
	}

	public int[] getAnswer() {
		return answer;
	}
}
