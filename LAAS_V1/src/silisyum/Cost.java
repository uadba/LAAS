package silisyum;

public class Cost {
	
	private int problemDimension;
	private AntennaArray aA;
	private Mask mask;
	
	public Cost(int _problemDimension, AntennaArray _aA, Mask _mask) {
		problemDimension = _problemDimension;
		aA = _aA;
		mask = _mask;
	
		mask.addNewSLL_outer("SLL_01", 0, 40, 40, -24);
		mask.addNewSLL_outer("SLL_01", 40, 50, 10, -45);
		mask.addNewSLL_outer("SLL_01", 50, 79, 20, -24);
		mask.addNewSLL_outer("SLL_01", 79, 80, 5, -70);
		mask.addNewSLL_outer("SLL_01", 80, 100, 20, 0);
		mask.addNewSLL_outer("SLL_01", 100, 110, 80, -20);
		mask.addNewSLL_outer("SLL_01", 110, 115, 80, -40);
		mask.addNewSLL_outer("SLL_01", 115, 180, 80, -24);
		
		mask.addNewSLL_inner("SLL_01", 0, 55, 80, -95);
		mask.addNewSLL_inner("SLL_01", 55, 75, 30, -40);
		mask.addNewSLL_inner("SLL_01", 75, 180, 70, -90);
		
		
//		for (int i = 0; i < mask.SLL_outers.get(0).angles.length; i++) {
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
//			if(realAngle <= 75)
//			{
//				double msld = -0;
//				if(aA.pattern_dB[i] > msld) result += 10*(aA.pattern_dB[i] - msld);
//				if(realAngle >= 47 && realAngle <= 49)
//				{
//					double ndld = -100;
//					if(aA.pattern_dB[i] > ndld) result += 1*(aA.pattern_dB[i] - ndld);
//				}
//			}

			
			
			int numberOfSLLOuters = mask.SLL_outers.size();
			Mask.SidelobeLevel SLL_outer;
			for (int n = 0; n < numberOfSLLOuters; n++) {
				SLL_outer = mask.SLL_outers.get(n);
				if(realAngle >= SLL_outer.angles[0] && realAngle <= SLL_outer.angles[SLL_outer.angles.length-1])
				{
					double maximumsld = SLL_outer.levels[0];
					if(aA.pattern_dB[i] > maximumsld) result += 1*(aA.pattern_dB[i] - maximumsld);
				}			
			}
			
			int numberOfSLLInners = mask.SLL_inners.size();
			Mask.SidelobeLevel SLL_inner;
			for (int n = 0; n < numberOfSLLInners; n++) {
				SLL_inner = mask.SLL_inners.get(n);
				if(realAngle >= SLL_inner.angles[0] && realAngle <= SLL_inner.angles[SLL_inner.angles.length-1])
				{
					double minimumsld = SLL_inner.levels[0];
					if(minimumsld > aA.pattern_dB[i]) result += 10*(minimumsld - aA.pattern_dB[i]);
				}			
			}
		}
		
		return result;
	}

}
