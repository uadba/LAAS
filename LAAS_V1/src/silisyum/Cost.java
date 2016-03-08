package silisyum;

public class Cost {
	
	private int problemDimension;
	
	public Cost(int _problemDimension) {
		problemDimension = _problemDimension;
	}
	
	public double function(double[] theVector) {
		
		double result = 0;
		
		for (int d = 0; d < problemDimension; d++) {
			result = result + Math.abs(1-Math.pow(5, (theVector[d]- ((double)d)/10))); 
		}		
		
		return result;
	}

}
