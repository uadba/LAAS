package silisyum;

public class Cost {
	
	private int numberofElements;
	private AntennaArray aA;
	
    private boolean amplitudeIsUsed;
    private boolean phaseIsUsed;
    private boolean positionIsUsed;
	
	public Cost(int _numberofElements, AntennaArray _aA, boolean _amplitudeIsUsed, boolean _phaseIsUsed, boolean _positionIsUsed) {
		numberofElements = _numberofElements;
		aA = _aA;
	    amplitudeIsUsed = _amplitudeIsUsed;
	    phaseIsUsed = _phaseIsUsed;
	    positionIsUsed = _positionIsUsed;
	
		aA.createLongArrays();
				
//		for (int i = 0; i < mask.SLL_outers.get(0).angles.length; i++) {
//			Mask.SidelobeLevel temp = mask.SLL_outers.get(0);
//			System.out.println(" " + temp.angles[i] + " " + temp.levels[i]);			
//		}
	}
	
	public double function(double[] theVector) {
		
		double result = 0;
		
		int delta = 0;
		if (amplitudeIsUsed) {
			// this is for amplitudes	
			for (int index = 0; index < numberofElements; index++) {
				aA.a[index] = theVector[index];
			}
			delta = numberofElements;
		}
		
		if (phaseIsUsed) {
			// this is for phases
			for (int index = 0; index < numberofElements; index++) {
				aA.alpha[index] = theVector[index + delta];
			}
			delta += numberofElements;
		}
		
		if (positionIsUsed) {
			// this is for positions. It starts with 1 instead of 0
			aA.d[0] = 0;
			for (int index = 1; index < numberofElements; index++) {
				aA.d[index] = aA.d[index - 1] + 0.5 + theVector[index + delta];
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
