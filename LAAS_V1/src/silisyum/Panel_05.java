package silisyum;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

class Panel_05 extends JPanel {
	private double x = 0;
	private XYSeries seri;

    /**
	 * 
	 */
	private static final long serialVersionUID = 8717587918371971696L;
	
//	private BufferedImage surface;
//	private JLabel view;
//	private Graphics gv;
	
	Panel_05() {
		
		seri = new XYSeries("Burası neresi1");
		XYSeriesCollection veri_seti = new XYSeriesCollection(seri);
		JFreeChart grafik = ChartFactory.createXYLineChart("Başlık", "Açı", "Patter (dB)", veri_seti);
		ChartPanel grafikPaneli = new ChartPanel(grafik);
		this.add(grafikPaneli);
		
//        surface = new BufferedImage(545, 275, BufferedImage.TYPE_INT_RGB);
//        view = new JLabel(new ImageIcon(surface));
//        gv = surface.getGraphics();
//        this.add(view);
        
    }
  
    public void drawPlot(double rastgele)
    {
    	seri.add(x, rastgele);
    	x = x + 0.1;
    	
  	
//    	for(int theta=0; theta<180; theta++)
//    	{
//    		gv.drawLine(0, 0, 545, 275);
//    	}
//    	view.repaint();
    }
    
//    private double hesapla(double _theta)
//    {
//    	double result = Math.sin(Math.toRadians(_theta)); 
//    	return result;
//    }    
        
}