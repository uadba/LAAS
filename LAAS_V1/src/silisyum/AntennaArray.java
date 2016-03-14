package silisyum;

public class AntennaArray {
	
	double lambda = 1;
	double beta = 2*Math.PI/lambda;
	//static double[] a = {1.0, 0.9295, 1.2126, 1.4383, 1.5568, 1.5568, 1.4383, 1.2126, 0.9295, 1.0};
	int numberofElements;
	public double[] a;
	private double[] d;
	private double[] alpha;
	public int numberofSamplePoints;
	public double[] angle = new double[numberofSamplePoints];
	private double[] pattern = new double[numberofSamplePoints];
	public double[] pattern_dB = new double[numberofSamplePoints];
	
	public AntennaArray(int _numberofElements, int _numberofSamplePoints) {
		
		numberofSamplePoints = _numberofSamplePoints;
		numberofElements = _numberofElements;
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
		for (int i = 0; i < numberofElements; i++)
		{
			a[i] = 1;
			d[i] = 0.5*lambda;
			alpha[i] = 0;
		}		
	}

	public double patternFunction(double theta)
	{
		double result = 0;
		double result_real = 0;
		double result_img = 0;
		for (int e = 0; e<numberofElements; e++)
		{
			result_real = result_real + a[e]*Math.cos((beta*d[e]*Math.cos((theta)/180*Math.PI) + alpha[e])*(e));
			result_img = result_img + a[e]*Math.sin((beta*d[e]*Math.cos((theta)/180*Math.PI) + alpha[e])*(e));			
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
}
