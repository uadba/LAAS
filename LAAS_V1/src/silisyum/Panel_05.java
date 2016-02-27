package silisyum;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

class Panel_05 extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8717587918371971696L;
	
	private BufferedImage surface;
	private JLabel view;
	private Graphics gv;
	
	Panel_05() {
       
        surface = new BufferedImage(545, 275, BufferedImage.TYPE_INT_RGB);
        view = new JLabel(new ImageIcon(surface));
        gv = surface.getGraphics();
        this.add(view);
        
    }
  
    public void ciz()
    {    	
    	
    	for(int theta=0; theta<180; theta++)
    	{
    		gv.drawLine(0, 0, 545, 275);
    	}
    	view.repaint();
    }
    
    private double hesapla(double _theta)
    {
    	double result = Math.sin(Math.toRadians(_theta)); 
    	return result;
    }    
        
}