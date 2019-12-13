import java.awt.event.*;
import java.awt.*;

public class KeyHandel implements MouseMotionListener,MouseListener{

	
	public void mouseClicked(MouseEvent e) {
		
	}

	
	public void mouseEntered(MouseEvent e) {
	
		
	}

	
	public void mouseExited(MouseEvent e) {
	
		
	}

	public void mousePressed(MouseEvent e) {
		Ecran.magasin.click(e.getButton());
	}

	public void mouseReleased(MouseEvent e) {
		
	}

	public void mouseDragged(MouseEvent e) {
		Ecran.mse=new Point((e.getX()) - ((Frame.size.width - Ecran.myWidth)/2), (e.getY()) - ((Frame.size.height - (Ecran.myHeight)) - (Frame.size.width- Ecran.myWidth)/2));
	}

	public void mouseMoved(MouseEvent e) {
		Ecran.mse=new Point((e.getX()) - ((Frame.size.width - Ecran.myWidth)/2), (e.getY()) - ((Frame.size.height - (Ecran.myHeight)) - (Frame.size.width- Ecran.myWidth)/2));
	}
	
}
