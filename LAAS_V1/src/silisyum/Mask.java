package silisyum;

import java.util.ArrayList;
import java.util.List;

public class Mask {
	List<SidelobeLevel> SLL_outers = new ArrayList<SidelobeLevel>();
	List<SidelobeLevel> SLL_inners = new ArrayList<SidelobeLevel>();

	public void addNewSLL_outer(String _name, double _startAngle, double _stopAngle, int _numberOfPoints, double _level)
	{
		SLL_outers.add(new SidelobeLevel(_name, _startAngle, _stopAngle, _numberOfPoints, _level));
	}
	
	public void addNewSLL_inner(String _name, double _startAngle, double _stopAngle, int _numberOfPoints, double _level)
	{
		SLL_inners.add(new SidelobeLevel(_name, _startAngle, _stopAngle, _numberOfPoints, _level));
	}
	
	public class SidelobeLevel {
		String name;
		double[] angles;
		double[] levels;
		public SidelobeLevel(String _name, double _startAngle, double _stopAngle, int _numberOfPoints, double _level) {
			name = _name;
			angles = new double[_numberOfPoints];
			levels = new double[_numberOfPoints];
			
			double resolution = (_stopAngle - _startAngle)/(_numberOfPoints - 1); 
			
			angles[0] = _startAngle;
			levels[0] = _level;
			for (int i = 1; i < _numberOfPoints-1; i++) {
				angles[i] = angles[i-1] + resolution;
				levels[i] = _level;
			}
			angles[_numberOfPoints-1] = _stopAngle;			
			levels[_numberOfPoints-1] = _level;
		}
	}
}

