package silisyum;

public class Cost {
	
	private int problemDimension;
	private AntennaArray aA;
	
	public Cost(int _problemDimension, AntennaArray _aA) {
		problemDimension = _problemDimension;
		aA = _aA;
	}
	
	public double function(double[] theVector) {
		
		double result = 0;
		
		for (int d = 0; d < problemDimension; d++) {
			aA.a[d] = theVector[d]; 
		}
		
		aA.createPattern();
		
		for (int n = 0; n < aA.numberofSamplePoints; n++) {
			 
		}
		
		return result;
	}

}
