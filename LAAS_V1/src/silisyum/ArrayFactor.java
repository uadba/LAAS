package silisyum;

public class ArrayFactor {
	
	static double lambda = 1;
	static double beta = 2*Math.PI/lambda;
	static double[] a = {1.0, 0.9295, 1.2126, 1.4383, 1.5568, 1.5568, 1.4383, 1.2126, 0.9295, 1.0};
	static double[] d = {0.5*lambda, 0.5*lambda, 0.5*lambda, 0.5*lambda, 0.5*lambda, 0.5*lambda, 0.5*lambda, 0.5*lambda, 0.5*lambda, 0.5*lambda};
	static double[] alpha = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
	static double[] angle = new double[181];
	
//	clear; clc;
//	lambda = 1;
//	d = ones(1, 10)*lambda / 2;
//	beta = 2*pi/lambda;
//	a = [1.0, 0.9295, 1.2126, 1.4383, 1.5568, 1.5568, 1.4383, 1.2126, 0.9295, 1.0]; % cheby
//	alpha = zeros(1, 10);
//	af = zeros(1, 1801);
//	i=1;
//	for theta = 0:0.1:180;
//	af_real = 0;
//	af_img = 0;
//	  for eleman = 1:10
//	      %af(i) = af(i) + a(eleman)*1*exp(j*(beta*d(eleman)*cos((theta)/180*pi) + alpha(eleman))*(eleman-1));
//	      af_real = af_real + a(eleman)*1*cos((beta*d(eleman)*cos((theta)/180*pi) + alpha(eleman))*(eleman-1));
//	      af_img = af_img + a(eleman)*1*sin((beta*d(eleman)*cos((theta)/180*pi) + alpha(eleman))*(eleman-1));      
//	  end
//	  af(i) = af(i) + sqrt(af_real*af_real + af_img*af_img);
//	  i++; 
//	 end
//	af = af/max(af);
//	af_db = 20*log10(af);
//	plot(0:0.1:180, af_db, 'color', 'black');
//	axis([0 180 -100 0])
	
	public static double function(double theta)
	{
		double result = 0;
		double result_real = 0;
		double result_img = 0;
		for (int e = 0; e<10; e++)
		{
			result_real = a[e]*Math.exp((beta*d[e]*Math.cos((theta)/180*Math.PI) + alpha[e])*(e));
			result_img = a[e]*Math.exp((beta*d[e]*Math.cos((theta)/180*Math.PI) + alpha[e])*(e));			
		}
		result = Math.sqrt(result_real*result_real + result_img*result_img);
		
		return result;		
	}
	
	public static void pattern() {
		for (int i = 0; i < angle.length; i++) {
			
		}
	}
}
