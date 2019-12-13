import javax.swing.*;
import java.awt.*;
public class Frame extends JFrame {
	
	public static String title= "TowerDefence By Fallou&Smaïl Production !";
	public static Dimension size = new Dimension(700,550);
	
	public Frame() {
			setTitle(title);
			setSize(size);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			init();
	}
	
	public void init() {
		//setLayout pour supp  le Grid, et le Grid pour définir un quadrillage
		setLayout(new GridLayout(1,1,0,0));
		
		Ecran ecran = new Ecran(this);
		add(ecran);
		
		setVisible(true);
		
	}
	
	public static void main(String args[]) {
		Frame frame = new Frame();
	}
	
}
