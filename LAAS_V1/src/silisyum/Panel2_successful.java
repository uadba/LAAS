package silisyum;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

class Panel2_successful extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8717587918371971696L;
	
	BufferedImage surface;
	JLabel view;
	Graphics gv;
	Random r = new Random();
	
	Panel2_successful() {
       
        surface = new BufferedImage(470, 240, BufferedImage.TYPE_INT_RGB);
        view = new JLabel(new ImageIcon(surface));
        gv = surface.getGraphics();
        this.add(view);
        
    }
  
    public void ciz()
    {
    	//gv.drawLine(0, 0, r.nextInt(480), r.nextInt(251));
    	gv.drawLine(0, 0, 470, 240);
    	view.repaint();
    }
    
}