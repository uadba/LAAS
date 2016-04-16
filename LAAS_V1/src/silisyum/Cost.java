package silisyum;

public class Cost {
	
	private int problemDimension;
	private AntennaArray aA;
	private Mask mask;
	
	public Cost(int _problemDimension, AntennaArray _aA, Mask _mask) {
		problemDimension = _problemDimension;
		aA = _aA;
		mask = _mask;
	
		mask.addNewSLL_outer("SLL_01", 0, 20, 20, -24, 1);
		mask.addNewSLL_outer("SLL_01", 20, 30, 10, -40, 1);
		mask.addNewSLL_outer("SLL_01", 30, 79, 49, -24, 1);
		mask.addNewSLL_outer("SLL_01", 79, 80, 5, -70, 1);
		mask.addNewSLL_outer("SLL_01", 80, 100, 20, 0, 1);
		mask.addNewSLL_outer("SLL_01", 100, 110, 10, -20, 1);
		mask.addNewSLL_outer("SLL_01", 110, 115, 15, -40, 1);
		mask.addNewSLL_outer("SLL_01", 115, 180, 65, -24, 1);
		
		aA.createLongArrays();
				
//		mask.addNewSLL_inner("SLL_01", 0, 55, 80, -95);
//		mask.addNewSLL_inner("SLL_01", 55, 75, 30, -40);
//		mask.addNewSLL_inner("SLL_01", 75, 180, 70, -90);
		
		
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

		
		result = aA.createPatternForOptimization();
	
		
		return result;
	}

}
