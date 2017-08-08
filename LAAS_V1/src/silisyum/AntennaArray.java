package silisyum;

public class AntennaArray {
	
	double lambda = 1;
	double beta = 2*Math.PI/lambda;
	public int numberofElements;
	public double[] amplitude;
	public double[] position;
	public double[] phase;
	public double[] alpha;
	public double rod;
	public int numberofSamplePoints;
	public double[] angle;
	private double[] pattern;
	public double[] pattern_dB;
	public double[] angleForOptimization_ForOuters;
	public double[] patternForOptimization_ForOuters;
	public double[] patternForOptimization_dB_ForOuters;
	public double[] levels_ForOuters;
	public double[] weights_ForOuters;
	public double[] angleForOptimization_ForInners;
	public double[] patternForOptimization_ForInners;
	public double[] patternForOptimization_dB_ForInners;
	public double[] levels_ForInners;
	public double[] weights_ForInners;
	private Mask mask;
	public int numberOfSLLOuters;
	public int numberOfSLLInners;
	public double biggestOne;	
	
	public AntennaArray(int _numberofElements, int _numberofSamplePoints, Mask _mask) {
		
		numberofSamplePoints = _numberofSamplePoints;
		numberofElements = _numberofElements;
		mask = _mask;
		rod = DefaultConfiguration.delta;
		createArrays();
		initializeArrays();

	}
	

	public void createArrays() {
		amplitude = new double[numberofElements];
		position = new double[numberofElements];
		phase = new double[numberofElements];
		alpha = new double[numberofElements];
		createAnlgeAndPatternArrays();
	}
	
	private void createAnlgeAndPatternArrays() {
		angle = new double[numberofSamplePoints];
		pattern = new double[numberofSamplePoints];
		pattern_dB = new double[numberofSamplePoints];
	}

//	Wide null
//	double _gecici_alpha[] = { 104.04, 0.72, 40.68, 83.88, 100.44, 96.48, 88.56, 95.04, 75.96, 69.84, 79.56, 82.8, 72.72, 57.96, 61.2, 46.08, 23.04, 75.96, 106.2, 1.8};
	
//	Multiple nulls
	double _gecici_alpha[] = { 160.92, 104.76, 77.76, 103.32, 83.16, 73.8, 96.48, 101.52, 86.4, 86.04, 81.72, 88.92, 52.92, 73.44, 106.92, 110.52, 168.84, 90.0, 106.56, 79.2};

	double _gecici_amplitude[] = {0.32561, 0.28558, 0.39104, 0.50461, 0.62034, 0.73147, 0.83102, 0.91243, 0.97010, 1.00000, 1.00000, 0.97010, 0.91243, 0.83102, 0.73147, 0.62034, 0.50461, 0.39104, 0.28558, 0.32561};
	public void initializeArrays() {
		for (int i = 0; i < numberofElements; i++) {
			amplitude[i] = DefaultConfiguration.amplitudeValue;
			phase[i] = DefaultConfiguration.phaseValue;
			position[i] = i*DefaultConfiguration.positionValue*lambda;
			alpha[i] = DefaultConfiguration.alphaValue;
			
			alpha[i] = _gecici_alpha[i];
			amplitude[i] = _gecici_amplitude[i];
		}
	}

	public double patternFunction(double theta)
	{
		double result = 0;
		double result_real = 0;
		double result_img = 0;		
		for (int e = 0; e<numberofElements; e++)
		{
//			result_real = result_real + amplitude[e]*Math.cos(position[e]*beta*Math.cos((theta)/180*Math.PI) + ((phase[e])/180*Math.PI));
//			result_img = result_img + amplitude[e]*Math.sin(position[e]*beta*Math.cos((theta)/180*Math.PI) + ((phase[e])/180*Math.PI));

			alphayiKuantala(e);
			result_real = result_real + amplitude[e]*Math.cos(beta*(position[e]*Math.cos((theta)/180*Math.PI) + rod*Math.cos((alpha[e]-theta)/180*Math.PI)) + ((phase[e])/180*Math.PI));
			result_img = result_img + amplitude[e]*Math.sin(beta*(position[e]*Math.cos((theta)/180*Math.PI) + rod*Math.cos((alpha[e]-theta)/180*Math.PI)) + ((phase[e])/180*Math.PI));
		
		}
		result = Math.sqrt(result_real*result_real + result_img*result_img);
	
		return result;
	}
	
	double adimlar[] = {0, 0.36, 0.72, 1.08, 1.44, 1.8, 2.16, 2.52, 2.88, 3.24, 3.6, 3.96, 4.32, 4.68, 5.04, 5.4, 5.76, 6.12, 6.48, 6.84, 7.2, 7.56, 7.92, 8.28, 8.64, 9, 9.36, 9.72, 10.08, 10.44, 10.8, 11.16, 11.52, 11.88, 12.24, 12.6, 12.96, 13.32, 13.68, 14.04, 14.4, 14.76, 15.12, 15.48, 15.84, 16.2, 16.56, 16.92, 17.28, 17.64, 18, 18.36, 18.72, 19.08, 19.44, 19.8, 20.16, 20.52, 20.88, 21.24, 21.6, 21.96, 22.32, 22.68, 23.04, 23.4, 23.76, 24.12, 24.48, 24.84, 25.2, 25.56, 25.92, 26.28, 26.64, 27, 27.36, 27.72, 28.08, 28.44, 28.8, 29.16, 29.52, 29.88, 30.24, 30.6, 30.96, 31.32, 31.68, 32.04, 32.4, 32.76, 33.12, 33.48, 33.84, 34.2, 34.56, 34.92, 35.28, 35.64, 36, 36.36, 36.72, 37.08, 37.44, 37.8, 38.16, 38.52, 38.88, 39.24, 39.6, 39.96, 40.32, 40.68, 41.04, 41.4, 41.76, 42.12, 42.48, 42.84, 43.2, 43.56, 43.92, 44.28, 44.64, 45, 45.36, 45.72, 46.08, 46.44, 46.8, 47.16, 47.52, 47.88, 48.24, 48.6, 48.96, 49.32, 49.68, 50.04, 50.4, 50.76, 51.12, 51.48, 51.84, 52.2, 52.56, 52.92, 53.28, 53.64, 54, 54.36, 54.72, 55.08, 55.44, 55.8, 56.16, 56.52, 56.88, 57.24, 57.6, 57.96, 58.32, 58.68, 59.04, 59.4, 59.76, 60.12, 60.48, 60.84, 61.2, 61.56, 61.92, 62.28, 62.64, 63, 63.36, 63.72, 64.08, 64.44, 64.8, 65.16, 65.52, 65.88, 66.24, 66.6, 66.96, 67.32, 67.68, 68.04, 68.4, 68.76, 69.12, 69.48, 69.84, 70.2, 70.56, 70.92, 71.28, 71.64, 72, 72.36, 72.72, 73.08, 73.44, 73.8, 74.16, 74.52, 74.88, 75.24, 75.6, 75.96, 76.32, 76.68, 77.04, 77.4, 77.76, 78.12, 78.48, 78.84, 79.2, 79.56, 79.92, 80.28, 80.64, 81, 81.36, 81.72, 82.08, 82.44, 82.8, 83.16, 83.52, 83.88, 84.24, 84.6, 84.96, 85.32, 85.68, 86.04, 86.4, 86.76, 87.12, 87.48, 87.84, 88.2, 88.56, 88.92, 89.28, 89.64, 90, 90.36, 90.72, 91.08, 91.44, 91.8, 92.16, 92.52, 92.88, 93.24, 93.6, 93.96, 94.32, 94.68, 95.04, 95.4, 95.76, 96.12, 96.48, 96.84, 97.2, 97.56, 97.92, 98.28, 98.64, 99, 99.36, 99.72, 100.08, 100.44, 100.8, 101.16, 101.52, 101.88, 102.24, 102.6, 102.96, 103.32, 103.68, 104.04, 104.4, 104.76, 105.12, 105.48, 105.84, 106.2, 106.56, 106.92, 107.28, 107.64, 108, 108.36, 108.72, 109.08, 109.44, 109.8, 110.16, 110.52, 110.88, 111.24, 111.6, 111.96, 112.32, 112.68, 113.04, 113.4, 113.76, 114.12, 114.48, 114.84, 115.2, 115.56, 115.92, 116.28, 116.64, 117, 117.36, 117.72, 118.08, 118.44, 118.8, 119.16, 119.52, 119.88, 120.24, 120.6, 120.96, 121.32, 121.68, 122.04, 122.4, 122.76, 123.12, 123.48, 123.84, 124.2, 124.56, 124.92, 125.28, 125.64, 126, 126.36, 126.72, 127.08, 127.44, 127.8, 128.16, 128.52, 128.88, 129.24, 129.6, 129.96, 130.32, 130.68, 131.04, 131.4, 131.76, 132.12, 132.48, 132.84, 133.2, 133.56, 133.92, 134.28, 134.64, 135, 135.36, 135.72, 136.08, 136.44, 136.8, 137.16, 137.52, 137.88, 138.24, 138.6, 138.96, 139.32, 139.68, 140.04, 140.4, 140.76, 141.12, 141.48, 141.84, 142.2, 142.56, 142.92, 143.28, 143.64, 144, 144.36, 144.72, 145.08, 145.44, 145.8, 146.16, 146.52, 146.88, 147.24, 147.6, 147.96, 148.32, 148.68, 149.04, 149.4, 149.76, 150.12, 150.48, 150.84, 151.2, 151.56, 151.92, 152.28, 152.64, 153, 153.36, 153.72, 154.08, 154.44, 154.8, 155.16, 155.52, 155.88, 156.24, 156.6, 156.96, 157.32, 157.68, 158.04, 158.4, 158.76, 159.12, 159.48, 159.84, 160.2, 160.56, 160.92, 161.28, 161.64, 162, 162.36, 162.72, 163.08, 163.44, 163.8, 164.16, 164.52, 164.88, 165.24, 165.6, 165.96, 166.32, 166.68, 167.04, 167.4, 167.76, 168.12, 168.48, 168.84, 169.2, 169.56, 169.92, 170.28, 170.64, 171, 171.36, 171.72, 172.08, 172.44, 172.8, 173.16, 173.52, 173.88, 174.24, 174.6, 174.96, 175.32, 175.68, 176.04, 176.4, 176.76, 177.12, 177.48, 177.84, 178.2, 178.56, 178.92, 179.28, 179.64, 180};
	public void alphayiKuantala(int e)
	{
		//double ilk=alpha[e];
		double kusuratli = ((alpha[e]*100) / (180*100)) * 500;
		Double index_double = Math.floor(kusuratli);
		alpha[e] = adimlar[index_double.intValue()];
//		if (ilk != alpha[e])
//		{
//			System.out.println(ilk);
//			System.out.println(alpha[e]);
//			System.out.println("--------------------------");
//		}
	}
	
	public void createLongArrays() {
		numberOfSLLOuters = mask.outerMaskSegments.size(); 
		if (numberOfSLLOuters > 0) {
			Mask.MaskSegment SLL_outer = null;
			int numberOfAnglesForOuters = 0;
			for (int n = 0; n < numberOfSLLOuters; n++) {
				SLL_outer = mask.outerMaskSegments.get(n);
				numberOfAnglesForOuters += SLL_outer.angles.length;
			}
			angleForOptimization_ForOuters = new double[numberOfAnglesForOuters];
			patternForOptimization_ForOuters = new double[numberOfAnglesForOuters];
			patternForOptimization_dB_ForOuters = new double[numberOfAnglesForOuters];
			levels_ForOuters = new double[numberOfAnglesForOuters];
			weights_ForOuters = new double[numberOfAnglesForOuters];
		}
		
		numberOfSLLInners = mask.innerMaskSegments.size();		
		if (numberOfSLLInners > 0) {
			Mask.MaskSegment SLL_inner = null;
			int numberOfAnglesForInners = 0;
			for (int n = 0; n < numberOfSLLInners; n++) {
				SLL_inner = mask.innerMaskSegments.get(n);
				numberOfAnglesForInners += SLL_inner.angles.length;
			}
			angleForOptimization_ForInners = new double[numberOfAnglesForInners];
			patternForOptimization_ForInners = new double[numberOfAnglesForInners];
			patternForOptimization_dB_ForInners = new double[numberOfAnglesForInners];
			levels_ForInners = new double[numberOfAnglesForInners];
			weights_ForInners = new double[numberOfAnglesForInners];
		}
	}
	
	public void createPattern() {
		
		if(numberofSamplePoints != angle.length) {
			createAnlgeAndPatternArrays();
		}			
		
		angle[0] = 0;
		double biggestOne = patternFunction(angle[0]);
		pattern[0] = patternFunction(angle[0]);
		for (int i = 1; i < numberofSamplePoints; i++) {
			angle[i] = 180*((double)i/(numberofSamplePoints-1));
			pattern[i] = patternFunction(angle[i]);
			if(pattern[i]>biggestOne) biggestOne = pattern[i];
		}
		
		for (int i = 0; i < numberofSamplePoints; i++) {
			pattern_dB[i] = 20*Math.log10(pattern[i] / biggestOne);
		}
	}

	public void createPatternForOptimization() {	
		// Create an array for the all mask values
		// For this purpose, we have to make a loop.
		// Then, we set angles into the elements of this array.
		
		int i;
		biggestOne = 0;
		
		if (numberOfSLLOuters > 0) {
			// ------------ for Outers ------------
			int numberOfSLLOuters = mask.outerMaskSegments.size();
			Mask.MaskSegment SLL_outer = null;
			i = 0;
			while (i < angleForOptimization_ForOuters.length) {
				for (int n = 0; n < numberOfSLLOuters; n++) {
					SLL_outer = mask.outerMaskSegments.get(n);
					for (int j = 0; j < SLL_outer.angles.length; j++) {
						angleForOptimization_ForOuters[i] = SLL_outer.angles[j];
						levels_ForOuters[i] = SLL_outer.levels[j];
						weights_ForOuters[i] = SLL_outer.weights[j];
						i++;
					}
				}
			}

			biggestOne = patternFunction(angleForOptimization_ForOuters[0]);
			patternForOptimization_ForOuters[0] = patternFunction(angleForOptimization_ForOuters[0]);
			for (int z = 1; z < angleForOptimization_ForOuters.length; z++) { // Attention please it starts from "1"
				patternForOptimization_ForOuters[z] = patternFunction(angleForOptimization_ForOuters[z]);
				if (patternForOptimization_ForOuters[z] > biggestOne)
					biggestOne = patternForOptimization_ForOuters[z];
			}
		}
		
		if (numberOfSLLInners > 0) {
			// ------------ for Inners ------------
			numberOfSLLInners = mask.innerMaskSegments.size();
			Mask.MaskSegment SLL_inner = null;
			i = 0;
			while (i < angleForOptimization_ForInners.length) {
				for (int n = 0; n < numberOfSLLInners; n++) {
					SLL_inner = mask.innerMaskSegments.get(n);
					for (int j = 0; j < SLL_inner.angles.length; j++) {
						angleForOptimization_ForInners[i] = SLL_inner.angles[j];
						levels_ForInners[i] = SLL_inner.levels[j];
						weights_ForInners[i] = SLL_inner.weights[j];
						i++;
					}
				}
			}

			if (numberOfSLLOuters < 1) biggestOne = patternFunction(angleForOptimization_ForInners[0]);
			patternForOptimization_ForInners[0] = patternFunction(angleForOptimization_ForInners[0]);
			for (int z = 1; z < angleForOptimization_ForInners.length; z++) { // Attention please it starts from "1"
				patternForOptimization_ForInners[z] = patternFunction(angleForOptimization_ForInners[z]);
				if(patternForOptimization_ForInners[z]>biggestOne) biggestOne = patternForOptimization_ForInners[z];
			}
		}
		
		if (numberOfSLLOuters > 0) {
			for (int z = 0; z < angleForOptimization_ForOuters.length; z++) {
				patternForOptimization_dB_ForOuters[z] = 20 * Math.log10(patternForOptimization_ForOuters[z] / biggestOne);
			}
		}			
		
		if (numberOfSLLInners > 0) {
			for (int z = 0; z < angleForOptimization_ForInners.length; z++) {
				patternForOptimization_dB_ForInners[z] = 20 * Math.log10(patternForOptimization_ForInners[z] / biggestOne);
			}
		}
	}
}
