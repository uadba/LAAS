package silisyum;

public class BestValues {
	public int bestID;
	public double bestCostValue;
	public double[] bestAmplitudes;
	
	public BestValues(int _bestID, double _bestCostValue, double[] _bestAmplitudes) {
		bestID = _bestID;
		bestCostValue = _bestCostValue;
		bestAmplitudes = new double[_bestAmplitudes.length];
		for (int i = 0; i < _bestAmplitudes.length; i++) {
			bestAmplitudes[i] = _bestAmplitudes[i];
		}
	}
}
