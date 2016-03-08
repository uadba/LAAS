package silisyum;

import java.util.Random;

public class DifferentialEvolution {
	
	private int problemDimension;
	private int populationNumber;
	private double[][] members;
	private double[] memberFitness;
	private double[] Xtrial;
	private double[] temp;
	private int bestMember = -1;
	private double fitnessOfBestMember = 0;
	public int iterationNumber;
	private double F;
	private double Cr;
	private int R1, R2, R3;
	private Random r;
	private int iterationIndex = 0;
	private double L, H;
	private Cost c;
	private boolean iterationState = true;
	
	public DifferentialEvolution(int _problemDimension, int _populationNumber, int _iterationNumber, double _F, double _C, double _L, double _H) {
		
		problemDimension = _problemDimension;
		populationNumber = _populationNumber;
		iterationNumber = _iterationNumber;
		F = _F;
		Cr = _C;
		L = _L;
		H = _H;
		createArrays();
		initialize();
		c = new Cost(problemDimension);
		r = new Random();		
	}
	
	private void createArrays() {
		members = new double[problemDimension][populationNumber];
		memberFitness = new double[populationNumber];
		Xtrial = new double[problemDimension];
		temp = new double[problemDimension];
	}

	private void initialize() {
		Random r = new Random();
		for (int m = 0; m < populationNumber; m++) {
			for (int d = 0; d < problemDimension; d++) {
				members[d][m] = L + (H-L)*r.nextDouble();
				temp[d] = members[d][m];
			}			
			memberFitness[m] = c.function(temp);
			if(bestMember == -1) {
				bestMember = m;
				fitnessOfBestMember = memberFitness[m];
			}
			else if(memberFitness[m] < bestMember) {
				bestMember = m;
				fitnessOfBestMember = memberFitness[m];
			}
			
		}		
	}
	
	public boolean iterate() {
		
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
			for (int d = 0; d < problemDimension; d++) {
				if(r.nextDouble() < Cr || ri == d) {
					Xtrial[d] = members[d][R3] + F * (members[d][R2] - members[d][R1]);
				} else {
					Xtrial[d] = members[d][individual];
				}
			}
			
			// Check the boundary constraints for the the trial vector
			for (int d = 0; d < problemDimension; d++) {
				if(Xtrial[d]<L || Xtrial[d]>H)
				{
					Xtrial[d] = L + (H-L)*r.nextDouble();
				}
			}
			
			// Pick the best individual
			// between trial and current members
			double fitnessOfTrial = c.function(Xtrial);
			if(fitnessOfTrial < memberFitness[individual]) {
				// Replace the current with Xtrial
				// Because it is better than the current
				for (int d = 0; d < problemDimension; d++) {
					members[d][individual] = Xtrial[d];					
				}
				memberFitness[individual] = fitnessOfTrial;				
			}
			if(fitnessOfTrial < memberFitness[bestMember]) {
				bestMember = individual;
				fitnessOfBestMember = memberFitness[individual];
			}
		}
		
		iterationIndex++;
		
		if(iterationIndex == iterationNumber)
			iterationState = false;
		
		return iterationState;
	}
	
	public static void main(String[] args) {
		DifferentialEvolution name = new DifferentialEvolution(5, 70, 200, 0.7, 0.5, 0, 1);
		while (name.iterate()) {
			System.out.println(name.fitnessOfBestMember);
		}
	}
}
