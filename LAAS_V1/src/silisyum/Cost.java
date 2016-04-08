package silisyum;

public class Cost {
	
	private int problemDimension;
	private AntennaArray aA;
	private Mask mask;
	
	public Cost(int _problemDimension, AntennaArray _aA, Mask _mask) {
		problemDimension = _problemDimension;
		aA = _aA;
		mask = _mask;
		mask.addNewSLL_outer("SLL_01", 30, 45, 23, -30);
		
//		for (int i = 0; i < 23; i++) {
//			Mask.SidelobeLevel temp = mask.SLL_outers.get(0);
//			System.out.println(" " + temp.angles[i] + " " + temp.levels[i]);			
//		}
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
			if(realAngle <= 75)
			{
				double msld = -0;
				if(aA.pattern_dB[i] > msld) result += 10*(aA.pattern_dB[i] - msld);
				if(realAngle >= 45 && realAngle <= 49)
				{
					double ndld = -100;
					if(aA.pattern_dB[i] > ndld) result += 1*(aA.pattern_dB[i] - ndld);
				}
			}
//			if(realAngle >= 105)
//			{
//				double msld = -27;
//				if(aA.pattern_dB[i] > msld) result += 10*(aA.pattern_dB[i] - msld);
//				if(realAngle >= 47 && realAngle <= 49)
//				{
//					double ndld = -0;
//					if(aA.pattern_dB[i] > ndld) result += 1*(aA.pattern_dB[i] - ndld);
//				}
//			}
		}
		
		return result;
	}

}
