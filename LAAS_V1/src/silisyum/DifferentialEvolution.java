package silisyum;

import java.util.Random;

public class DifferentialEvolution {
	
	private int numberofElements;
	private int populationNumber;
	public double[][] members;
	private double[] memberFitness;
	private double[] Xtrial;
	private double[] temp;
	public int bestMember = -1;
	public double fitnessOfBestMember = 0;
	public int iterationNumber;
	private double F;
	private double Cr;
	private int R1, R2, R3;
	private Random r;
	public int iterationIndex = 0;
	private double[] L;
	private double[] H;
	private double[] Ls;
	private double[] Hs;
    private boolean amplitudeIsUsed;
    private boolean phaseIsUsed;
    private boolean positionIsUsed;
	private Cost c;
	private boolean iterationState = true;
	private int whichParameter;
	
	public DifferentialEvolution(int _numberofElements, int _populationNumber, int _iterationNumber, double _F, double _C, double[] _L, double[] _H, AntennaArray _aA, Mask _mask, boolean _amplitudeIsUsed, boolean _phaseIsUsed, boolean _positionIsUsed) {
		
		numberofElements = _numberofElements;
		populationNumber = _populationNumber;
		iterationNumber = _iterationNumber;
		F = _F;
		Cr = _C;
		L = _L;
		H = _H;
	    amplitudeIsUsed = _amplitudeIsUsed;
	    phaseIsUsed = _phaseIsUsed;
	    positionIsUsed = _positionIsUsed;
		c = new Cost(numberofElements, _aA, _mask, _amplitudeIsUsed, _phaseIsUsed, positionIsUsed);
		r = new Random();		
		createArrays();
		initialize();
		
		if(amplitudeIsUsed) whichParameter = 0;
		if(phaseIsUsed) whichParameter = 1;
		if(positionIsUsed) whichParameter = 2;
	}
	
	private void createArrays() {
		members = new double[numberofElements][populationNumber];
		memberFitness = new double[populationNumber];
		Xtrial = new double[numberofElements];
		temp = new double[numberofElements];
		Ls = new double[numberofElements];
		Hs = new double[numberofElements];
	}

	private void initialize() {
		for (int d = 0; d < numberofElements; d++) {
			if(amplitudeIsUsed) {
				Ls[d] = L[0];
				Hs[d] = H[0];
			}
			
			if(phaseIsUsed) {
				Ls[d] = L[1];
				Hs[d] = H[1];
			}
			
			if(positionIsUsed) {
				Ls[d] = L[2];
				Hs[d] = H[2];
			}
		}
		
		Random r = new Random();
		for (int m = 0; m < populationNumber; m++) {
			for (int d = 0; d < numberofElements; d++) {
				members[d][m] = Ls[d] + (Hs[d]-Ls[d])*r.nextDouble();
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
			for (int d = 0; d < numberofElements; d++) {
				if(r.nextDouble() < Cr || ri == d) {
					Xtrial[d] = members[d][R3] + F * (members[d][R2] - members[d][R1]);
				} else {
					Xtrial[d] = members[d][individual];
				}
			}
			
			// Check the boundary constraints for the the trial vector
			for (int d = 0; d < numberofElements; d++) {
				if(Xtrial[d]<Ls[d] || Xtrial[d]>Hs[d])
				{
					Xtrial[d] = Ls[d] + (Hs[d]-Ls[d])*r.nextDouble();
				}
			}
			
			// Pick the best individual
			// between trial and current members
			double fitnessOfTrial = c.function(Xtrial);
			if(fitnessOfTrial < memberFitness[individual]) {
				// Replace the current with Xtrial
				// Because it is better than the current
				for (int d = 0; d < numberofElements; d++) {
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
}
