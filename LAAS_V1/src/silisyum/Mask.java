package silisyum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mask {
	List<SidelobeLevel> SLL_outers = new ArrayList<SidelobeLevel>();
	List<SidelobeLevel> SLL_inners = new ArrayList<SidelobeLevel>();

	public void addNewSLL_outer(String _name, double _startAngle, double _stopAngle, int _numberOfPoints, double _level, double _weight)
	{
		SLL_outers.add(new SidelobeLevel(_name, _startAngle, _stopAngle, _numberOfPoints, _level, _weight));
		Collections.sort(SLL_outers);
	}
	
	public void deleteSLL_outer(int index)
	{
		SLL_outers.remove(index);
	}
	
	public void addNewSLL_inner(String _name, double _startAngle, double _stopAngle, int _numberOfPoints, double _level, double _weight)
	{
		SLL_inners.add(new SidelobeLevel(_name, _startAngle, _stopAngle, _numberOfPoints, _level, _weight));
		Collections.sort(SLL_inners);
	}
	
	public class SidelobeLevel implements Comparable<SidelobeLevel>{
		String name;
		Double startAngle;
		double stopAngle;
		double[] angles;
		double[] levels;
		double[] weights;
		
		public SidelobeLevel(String _name, double _startAngle, double _stopAngle, int _numberOfPoints, double _level, double _weight) {
			name = _name;
			startAngle = Double.valueOf(_startAngle);
			stopAngle = _stopAngle;
			angles = new double[_numberOfPoints];
			levels = new double[_numberOfPoints];
			weights = new double[_numberOfPoints];
			
			double resolution = (_stopAngle - _startAngle)/(_numberOfPoints - 1); 
			
			angles[0] = _startAngle;
			levels[0] = _level;
			weights[0] = _weight;
			for (int i = 1; i < _numberOfPoints-1; i++) {
				angles[i] = angles[i-1] + resolution;
				levels[i] = _level;
				weights[i] = _weight;
			}
			angles[_numberOfPoints-1] = _stopAngle;			
			levels[_numberOfPoints-1] = _level;
			weights[_numberOfPoints-1] = _weight;
		}

		@Override
		public int compareTo(SidelobeLevel o) {			
			return startAngle.compareTo(o.startAngle);
		}
	}
}

