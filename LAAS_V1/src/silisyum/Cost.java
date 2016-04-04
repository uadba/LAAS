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
			aA.alpha[d] = theVector[d]; 
		}
		
		aA.createPattern();
		
		for (int n = 0; n < aA.numberofSamplePoints; n++) {
			 
		}
		
		for (int i = 0; i<181; i++) {
			double realAngle = 180*((double)i/(aA.numberofSamplePoints-1));
			if(realAngle <= 80)
			{
				double msld = -25;
				if(aA.pattern_dB[i] > msld) result += 1*(aA.pattern_dB[i] - msld);
				if(realAngle >= 45 && realAngle <= 49)
				{
					double ndld = -0;
					if(aA.pattern_dB[i] > ndld) result += (aA.pattern_dB[i] - ndld);
				}
			}
			if(realAngle >= 110)
			{
				double msld = -25;
				if(aA.pattern_dB[i] > msld) result += 1*(aA.pattern_dB[i] - msld);
				if(realAngle >= 45 && realAngle <= 49)
				{
					double ndld = -0;
					if(aA.pattern_dB[i] > ndld) result += (aA.pattern_dB[i] - ndld);
				}
			}
		}
		
		return result;
	}

}
