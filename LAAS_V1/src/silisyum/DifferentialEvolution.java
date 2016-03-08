package silisyum;

import java.util.Random;

public class DifferentialEvolution {
	
	private int problemDimension;
	private int populationNumber;
	private double[][] members;
	private double[] memberFitness;
	private double[] Xtrial;
	public int iterationNumber;
	private double F;
	private double Cr;
	private int R1, R2, R3;
	private Random r;
	private int iterationIndex = 0;
	
	public DifferentialEvolution(int _problemDimension, int _populationNumber, int _iterationNumber, double _F, double _C) {
		
		problemDimension = _problemDimension;
		populationNumber = _populationNumber;
		iterationNumber = _iterationNumber;
		F = _F;
		Cr = _C;
		createArrays();
		initialize();
		r = new Random();
	}
	
	private void createArrays() {
		members = new double[problemDimension][populationNumber];
		memberFitness = new double[populationNumber];
		Xtrial = new double[problemDimension];
	}

	private void initialize() {
		Random r = new Random();
		for (int m = 0; m < populationNumber; m++) {
			for (int d = 0; d < problemDimension; d++) {
				members[d][m] = r.nextDouble();
			}
		}		
	}
	
	public int iterate() {
		
		for (int individual = 0; individual < populationNumber; individual++) {
			// You need to modify the following codes so that different
			// random numbers can be selected.
			R1 = r.nextInt(populationNumber);
			R2 = r.nextInt(populationNumber);
			R3 = r.nextInt(populationNumber);
			
			// Set a new random index
			// in order to guarantee that at least one parameter will be changed
			int ri = r.nextInt(populationNumber);
			
			// Construct trial vector
			for (int i = 0; i < problemDimension; i++) {
				if(Math.random() < Cr || ri == i) {
					Xtrial[i] = members[i][R3] + F * (members[i][R2] - members[i][R1]);
				} else {
					Xtrial[i] = members[i][iterationIndex];
				}
			}
			
			
		}
		
		iterationIndex++;
		
		return 0;
	}
	
	public static void main(String[] args) {
		DifferentialEvolution name = new DifferentialEvolution(5, 70, 200, 0.7, 0.5);
		for (int i = 0; i < name.iterationNumber; i++) {
			name.iterate();
		}
	}
}
