package silisyum;

public class ArrayFactor {
	
	double[] a = {1.0, 0.9295, 1.2126, 1.4383, 1.5568, 1.5568, 1.4383, 1.2126, 0.9295, 1.0};
	double[] lambda = {0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5};
	double[] alpha = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	
//	theta = 0:0.1:180;
//	lambda = 1;
//	d = ones(1, 10)*lambda / 2;
//	beta = 2*pi/lambda;
//	a = [1.0, 0.9295, 1.2126, 1.4383, 1.5568, 1.5568, 1.4383, 1.2126, 0.9295, 1.0]; % cheby
//	alpha = zeros(1, 10);
//	af = 0;
//	for eleman = 1:10
//	    af = af + a(eleman)*1*exp(j*(beta*d(eleman)*cos(deg2rad(theta)) + alpha(eleman))*(eleman-1));
//	end
//	af = af/max(af);
//	af_db = 20*log10(abs(af));
	
	public double Function(double theta)
	{
		double result = 0;
		
		for (int element = 0; element<10; element++)
		{
			
		}
		
		return result;		
	}
}
