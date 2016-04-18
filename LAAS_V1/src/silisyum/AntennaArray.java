package silisyum;

public class AntennaArray {
	
	double lambda = 1;
	double beta = 2*Math.PI/lambda;
	//static double[] a = {1.0, 0.9295, 1.2126, 1.4383, 1.5568, 1.5568, 1.4383, 1.2126, 0.9295, 1.0};
	int numberofElements;
	public double[] a;
	private double[] d;
	public double[] alpha;
	public int numberofSamplePoints;
	public double[] angle = new double[numberofSamplePoints];	
	private double[] pattern = new double[numberofSamplePoints];
	public double[] pattern_dB = new double[numberofSamplePoints];
	public double[] angleForOptimization;
	public double[] patternForOptimization;
	public double[] patternForOptimization_dB;
	public double[] levels;
	public double[] weights;
	private Mask mask;	
	
	public AntennaArray(int _numberofElements, int _numberofSamplePoints, Mask _mask) {
		
		numberofSamplePoints = _numberofSamplePoints;
		numberofElements = _numberofElements;
		mask = _mask;
		createArrays();
		initializeArrays();

	}
	

	public void createArrays() {
		a = new double[numberofElements];
		d = new double[numberofElements];
		alpha = new double[numberofElements];		
		angle = new double[numberofSamplePoints];
		pattern = new double[numberofSamplePoints];
		pattern_dB = new double[numberofSamplePoints];		
	}
	
	public void initializeArrays() {
		// seeker example nulling at -10 degree
		double[] temp_a = {0.32561, 0.28558, 0.39104, 0.50461, 0.62034, 0.73147, 0.83102, 0.91243, 0.97010, 1.00000, 1.00000, 0.97010, 0.91243, 0.83102, 0.73147, 0.62034, 0.50461, 0.39104, 0.28558, 0.32561};
		//double[] temp_alpha = {-3.70782, -7.51205, -4.20985, -1.57898, 0.64605, 2.50538, 2.89041, 2.24520, 1.81287, 0.78319, -0.78319, -1.81287, -2.24520, -2.89041, -2.50538, -0.64605, +1.57898, +4.20985, +7.51205, +3.70782};
		
		// ismail th
		//double[] alpha_example = {14.3983, -26.1588, -9.93420, 10.2254, 1.93170, 5.18520, 1.53020, -1.48370, -2.37140, -2.97950, -0.997700, 3.23560, -1.12110, 0.775300, 4.14480, -7.55650, 1.79030, 6.73780, 24.7469, -15.6342};
		
		for (int i = 0; i < numberofElements; i++)
		{
			//a[i] = 1;
			a[i] = temp_a[i]; // seeker
			d[i] = 0.5*lambda;
			alpha[i] = 0;
			//alpha[i] = temp_alpha[i]; // seeker
			//alpha[i] = alpha_example[i]; // ismail th
		}		
	}

	public double patternFunction(double theta)
	{
		double result = 0;
		double result_real = 0;
		double result_img = 0;
		for (int e = 0; e<numberofElements; e++)
		{
			result_real = result_real + a[e]*Math.cos((beta*d[e]*Math.cos((theta)/180*Math.PI)*(e) + ((alpha[e])/180*Math.PI)));
			result_img = result_img + a[e]*Math.sin((beta*d[e]*Math.cos((theta)/180*Math.PI)*(e) + ((alpha[e])/180*Math.PI)));			
		}
		result = Math.sqrt(result_real*result_real + result_img*result_img);
					
		return result;
	}
	
	public void createPattern() {
		angle[0] = 0;
		double biggestOne = patternFunction(angle[0]);
		pattern[0] = patternFunction(angle[0]);
		for (int i = 1; i < numberofSamplePoints; i++) { // Attention please it starts from "1"
			angle[i] = 180*((double)i/(numberofSamplePoints-1));
			pattern[i] = patternFunction(angle[i]);
			if(pattern[i]>biggestOne) biggestOne = pattern[i];
		}
		
		for (int i = 0; i < numberofSamplePoints; i++) {
			pattern_dB[i] = 20*Math.log10(pattern[i] / biggestOne);
		}
	}
	
	public void createLongArrays() {
		int numberOfSLLOuters = mask.SLL_outers.size(); 
		Mask.SidelobeLevel SLL_outer = null;
		int numberOfAnglesForOuters = 0;
		for (int n = 0; n < numberOfSLLOuters; n++) {
			SLL_outer = mask.SLL_outers.get(n);
			numberOfAnglesForOuters += SLL_outer.angles.length;
		}
		angleForOptimization = new double[numberOfAnglesForOuters];
		patternForOptimization  = new double[numberOfAnglesForOuters];
		patternForOptimization_dB = new double[numberOfAnglesForOuters];
		levels = new double[numberOfAnglesForOuters];
		weights = new double[numberOfAnglesForOuters];
		
		int numberOfSLLInners = mask.SLL_inners.size();
		Mask.SidelobeLevel SLL_inner = null;
		int numberOfAnglesForInners = 0;
		for (int n = 0; n < numberOfSLLOuters; n++) {
			
		}
	}

	public double createPatternForOptimization() {
		double result = 0;
		
		// Create an array for the all mask values
		// For this purpose, we have to make a loop.
		// Then, we set angles into the elements of this array.
		
		int numberOfSLLOuters = mask.SLL_outers.size();
		Mask.SidelobeLevel SLL_outer = null;
		int i = 0;
		while (i < angleForOptimization.length) {
			for (int n = 0; n < numberOfSLLOuters; n++) {
				SLL_outer = mask.SLL_outers.get(n);
				for (int j = 0; j < SLL_outer.angles.length; j++) {
					angleForOptimization[i] = SLL_outer.angles[j];
					levels[i] = SLL_outer.levels[j];
					weights[i] = SLL_outer.weights[j];
					i++;
				}
			}
		}		

		angleForOptimization[0] = 0;
		double biggestOne = patternFunction(angleForOptimization[0]);
		patternForOptimization[0] = patternFunction(angleForOptimization[0]);
		for (int z = 1; z < angleForOptimization.length; z++) { // Attention please it starts from "1"
			patternForOptimization[z] = patternFunction(angleForOptimization[z]);
			if(patternForOptimization[z]>biggestOne) biggestOne = patternForOptimization[z];
		}
		
		for (int z = 0; z < angleForOptimization.length; z++) {
			patternForOptimization_dB[z] = 20*Math.log10(patternForOptimization[z] / biggestOne);
			if (patternForOptimization_dB[z] > levels[z])
				result += weights[z]*(patternForOptimization_dB[z] - levels[z]);
		}
		
		return result;
	}
}
