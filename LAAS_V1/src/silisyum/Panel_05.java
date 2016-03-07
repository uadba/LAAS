package silisyum;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;

class Panel_05 extends JPanel implements ChartMouseListener {
	private double x = 0;
	private XYSeries seri;

    /**
	 * 
	 */
	private static final long serialVersionUID = 8717587918371971696L;
	
//	private BufferedImage surface;
//	private JLabel view;
//	private Graphics gv;
	
	private ChartPanel chartPanel;

    private Crosshair xCrosshair;

    private Crosshair yCrosshair;
	
	Panel_05() {
		
		seri = new XYSeries("Burası neresi1 Is it realy here, yes it is");
		XYSeriesCollection veri_seti = new XYSeriesCollection(seri);
		JFreeChart grafik = ChartFactory.createXYLineChart("Başlık", "Açı", "Patter (dB)", veri_seti);
				
        this.chartPanel = new ChartPanel(grafik);
        this.chartPanel.addChartMouseListener(this);
        CrosshairOverlay crosshairOverlay = new CrosshairOverlay();
        this.xCrosshair = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));
        this.xCrosshair.setLabelVisible(true);
        this.yCrosshair = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));
        this.yCrosshair.setLabelVisible(true);
        crosshairOverlay.addDomainCrosshair(xCrosshair);
        crosshairOverlay.addRangeCrosshair(yCrosshair);
        chartPanel.addOverlay(crosshairOverlay);
        
        this.add(this.chartPanel);
		
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

	@Override
	public void chartMouseClicked(ChartMouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void chartMouseMoved(ChartMouseEvent event) {
        Rectangle2D dataArea = this.chartPanel.getScreenDataArea();
        JFreeChart chart = event.getChart();
        XYPlot plot = (XYPlot) chart.getPlot();
        ValueAxis xAxis = plot.getDomainAxis();
        double x = xAxis.java2DToValue(event.getTrigger().getX(), dataArea, 
                RectangleEdge.BOTTOM);
        double y = DatasetUtilities.findYValue(plot.getDataset(), 0, x);
        this.xCrosshair.setValue(x);
        this.yCrosshair.setValue(y);
		System.out.println("Event works fine!..");
	}
    
//    private double hesapla(double _theta)
//    {
//    	double result = Math.sin(Math.toRadians(_theta)); 
//    	return result;
//    }    
        
}