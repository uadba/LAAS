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
		
		for (int i = 0; i<90; i++) {
			double realAngle = 180*((double)i/(aA.numberofSamplePoints-1));
			if(realAngle >= 55 && realAngle <= 60)
			{
				double msld = -70;
				if(aA.pattern_dB[i] > msld) result += (aA.pattern_dB[i] - msld);
			}			 
		}
		
		return result;
	}

}
