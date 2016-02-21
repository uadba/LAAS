package silisyum;

public class DifferentialEvolution {
	
	public int problemDimension = 10;
	public int populationNumber = 70;
	public double[][] members;
	public double[] memberFitness = new double[populationNumber];
	public int iterationNumber = 200;
	public double F = 0.7;
	public double C = 0.5;
	
	public DifferentialEvolution(int _problemDimension, int _populationNumber, int _iterationNumber, double _F, double _C) {
		
		problemDimension = _problemDimension;
		populationNumber = _populationNumber;
		members = new double[populationNumber][problemDimension];
		memberFitness = new double[populationNumber];
		iterationNumber = _iterationNumber;
		F = _F;
		C = _C;
	
	}
	
	

}
