package silisyum;

public class Cost {
	
	private int numberofElements;
	private AntennaArray aA;
	private Mask mask;
	
    private boolean amplitudeIsUsed;
    private boolean phaseIsUsed;
    private boolean positionIsUsed;
	
	public Cost(int _numberofElements, AntennaArray _aA, Mask _mask, boolean _amplitudeIsUsed, boolean _phaseIsUsed, boolean _positionIsUsed) {
		numberofElements = _numberofElements;
		aA = _aA;
		mask = _mask;
	    amplitudeIsUsed = _amplitudeIsUsed;
	    phaseIsUsed = _phaseIsUsed;
	    positionIsUsed = _positionIsUsed;
	
//		mask.addNewSLL_outer("SLL_01", 0, 20, 20, -24, 1);
//		mask.addNewSLL_outer("SLL_01", 20, 30, 10, -40, 1);
//		mask.addNewSLL_outer("SLL_01", 30, 79, 49, -20, 1);
//		mask.addNewSLL_outer("SLL_01", 79, 80, 5, -60, 1);
//		mask.addNewSLL_outer("SLL_01", 80, 100, 20, 0, 1);
//		mask.addNewSLL_outer("SLL_01", 100, 110, 10, -20, 1);
//		mask.addNewSLL_outer("SLL_01", 110, 115, 15, -40, 1);
//		mask.addNewSLL_outer("SLL_01", 115, 180, 65, -24, 1);
//						
//		mask.addNewSLL_inner("SLL_01", 0, 40, 3, -95, 1);
//		mask.addNewSLL_inner("SLL_01", 40, 60, 30, -30, 1);
//		mask.addNewSLL_inner("SLL_01", 60, 70, 20, -35, 1);
//		mask.addNewSLL_inner("SLL_01", 70, 150, 3, -95, 1);
//		mask.addNewSLL_inner("SLL_01", 150, 160, 10, -40, 1);
//		mask.addNewSLL_inner("SLL_01", 160, 180, 3, -95, 1);

		mask.addNewSLL_outer("SLL_01", 0, 60, 60, -25, 1);
		mask.addNewSLL_outer("SLL_01", 60, 65, 20, -70, 1);
		mask.addNewSLL_outer("SLL_01", 65, 80, 50, -25, 1);
		mask.addNewSLL_outer("SLL_01", 80, 100, 20, 0, 1);
		mask.addNewSLL_outer("SLL_01", 100, 110, 10, -25, 1);
		mask.addNewSLL_outer("SLL_01", 110, 115, 5, -25, 1);
		mask.addNewSLL_outer("SLL_01", 115, 180, 65, -25, 1);		


		
		aA.createLongArrays();
				
//		for (int i = 0; i < mask.SLL_outers.get(0).angles.length; i++) {
//			Mask.SidelobeLevel temp = mask.SLL_outers.get(0);
//			System.out.println(" " + temp.angles[i] + " " + temp.levels[i]);			
//		}
	}
	
	public double function(double[] theVector) {
		
		double result = 0;
		
		if (amplitudeIsUsed) {
			// this is for amplitudes	
			for (int index = 0; index < numberofElements; index++) {
				aA.a[index] = theVector[index];
			} 
		}
		
		if (phaseIsUsed) {
			// this is for phases
			for (int index = 0; index < numberofElements; index++) {
				aA.alpha[index] = theVector[index];
			} 
		}
		
		if (positionIsUsed) {
			// this is for positions. It starts with 1 instead of 0
			aA.d[0] = 0;
			for (int index = 1; index < numberofElements; index++) {
				aA.d[index] = aA.d[index - 1] + 0.5 + theVector[index];
			} 
		}
		
		aA.createPatternForOptimization();

		if (aA.numberOfSLLOuters > 0) {
			// ------------ for Outers ------------
			for (int z = 0; z < aA.angleForOptimization_ForOuters.length; z++) {
				aA.patternForOptimization_dB_ForOuters[z] = 20 * Math.log10(aA.patternForOptimization_ForOuters[z] / aA.biggestOne);
				if (aA.patternForOptimization_dB_ForOuters[z] > aA.levels_ForOuters[z])
					result += aA.weights_ForOuters[z] * (aA.patternForOptimization_dB_ForOuters[z] - aA.levels_ForOuters[z]);
			}
		}
		
		if (aA.numberOfSLLInners > 0) {
			// ------------ for Inners ------------
			for (int z = 0; z < aA.angleForOptimization_ForInners.length; z++) {
				aA.patternForOptimization_dB_ForInners[z] = 20 * Math.log10(aA.patternForOptimization_ForInners[z] / aA.biggestOne);
				if (aA.levels_ForInners[z] > aA.patternForOptimization_dB_ForInners[z]) {
					result += aA.weights_ForInners[z] * (aA.levels_ForInners[z] - aA.patternForOptimization_dB_ForInners[z]);
				}
			} 
		}
		
		return result;
	}

}
