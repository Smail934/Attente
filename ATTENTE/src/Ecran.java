import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;


public class Ecran extends JPanel implements Runnable{
	
	public Thread thread = new Thread(this);
	public static boolean isFirst=true;
	public static int myWidth,myHeight;
	public static Room room;
	public static int money =100,vie=25;
	public static boolean isDebug = false;
	public static int kill=0, killsPourGG = 0,level=1,levelMax=3;
	public static boolean aGagner=false;
	public static int winTime =4000,winFrame=0;
	
	public static Image[] tileset_sol = new Image[100];
	public static Image[] tileset_air = new Image[100];
	public static Image[] tileset_cell = new Image[100];
	public static Image[] tileset_mob = new Image[100];
	
	public static Save save;
	
	public static Point mse= new Point(0,0);
	
	public static Magasin magasin;
	
	public static Mob[] mobs= new Mob[100];
	
	
	public Ecran(Frame frame) {
		
		frame.addMouseListener(new KeyHandel());
		frame.addMouseMotionListener(new KeyHandel());
		
		thread.start();
	}
	
	public void define() {
		room	= new Room();
		save	= new Save();
		magasin = new Magasin();
		money=100;
		vie=25;
		
		for(int i=0;i<tileset_sol.length;i++) {
			tileset_sol[i] = new ImageIcon("res/test.png").getImage();
			tileset_sol[i] = createImage(new FilteredImageSource(tileset_sol[i].getSource(),new CropImageFilter(0,26*i,26,26)));
		}
		for(int i=0;i<tileset_air.length;i++) {
			tileset_air[i] = new ImageIcon("res/tileset_air.png").getImage();
			tileset_air[i] = createImage(new FilteredImageSource(tileset_air[i].getSource(),new CropImageFilter(0,26*i,26,26)));
		}
		tileset_cell[0]= new ImageIcon("res/cell.png").getImage();
		tileset_cell[1]= new ImageIcon("res/Vie.png").getImage();
		tileset_cell[2]= new ImageIcon("res/Coin.png").getImage();
		tileset_mob[0] = new ImageIcon("res/gobelin.png").getImage();
		
		save.loadSave(new File("save/mission"+ level +".groupe"));
		for(int i=0;i<mobs.length;i++) {
			mobs[i] = new Mob();
		}
	}
	
	public void paintComponent(Graphics g) {
		if(isFirst) {
			myWidth=getWidth();
			myHeight=getHeight();
			define();
			
			isFirst=false;
		}
		g.setColor(new Color(50,50,50)); // Nouvelle couleur pour le fond 
		g.fillRect(0,0, getWidth(), getHeight());
		g.setColor(new Color(0,0,0)); // Nouvelle couleur pour les lignes sur le côte
		g.drawLine(room.block[0][0].x-1, 0, room.block[0][0].x-1,room.block[room.worldHeight-1][0].y + room.blockSize +1 ); // la ligne gauche
		g.drawLine(room.block[0][room.worldWidth-1].x+room.blockSize, 0, room.block[0][room.worldWidth-1].x+room.blockSize,room.block[room.worldHeight-1][0].y + room.blockSize +1 ); // la ligne droite
		g.drawLine(room.block[0][0].x,room.block[room.worldHeight-1][0].y+room.blockSize, room.block[0][room.worldWidth-1].x + room.blockSize,room.block[room.worldHeight-1][0].y+room.blockSize); // la ligne du bas
		
		room.draw(g); //Dessine la room
		
		for(int i = 0 ; i<mobs.length;i++) {
			if(mobs[i].visible) {
				mobs[i].draw(g);
			}
		}
		
		magasin.draw(g); // Dessine le magasin
		
		if(vie<1) {
			g.setColor(new Color(240,20,20));
			g.fillRect(0, 0, myWidth, myHeight);
			g.setColor(new Color(255,255,255));
			g.setFont(new Font("Courier New",Font.BOLD,14));
			g.drawString("Game Over!", 10, 10);
			
		}
		if(aGagner) {
			g.setColor(new Color(255,255,255));
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(new Color(0,0,0));
			g.setFont(new Font("Courier New",Font.BOLD,14));
			if(level==levelMax) {
				g.drawString("Tu viens de gagner, félicitations!", 10, 10);
			}else {
				g.drawString("Tu viens de gagner, félicitations! Place au level suivant :D", 10, 10);
			}
		}
	}
	
	public int spawnTime=2000,spawnFrame=0;
	
	public void mobSpawner() {
		if(spawnFrame>=spawnTime) {
			for(int i=0;i<mobs.length;i++) {
				if(!mobs[i].visible) {
					mobs[i].spawnMob(Value.mobCour);
					break;
				}
			}
			
			spawnFrame=0;
		}else {
			spawnFrame+=1;
		}
	}
	
	public static void Win() {
		if(kill==killsPourGG) {
			aGagner=true;
			kill=0;
			money=0;
		}
	}
	
	public void run() {
		while(true) {
			if(!isFirst && vie>0 && !aGagner) {
				room.physic();
				mobSpawner();
				for(int i=0;i<mobs.length;i++) {
					if(mobs[i].visible) {
						mobs[i].physic();
					}
				}
			}else {
				if(aGagner) {
					if(winFrame>= winTime) {
						if(level == levelMax) {
							System.exit(0);
						}else {
							level+=1;
							define();
							aGagner=false;
							winFrame=0;
						}
					}else {
						winFrame+=1;
					}
				}
			}
			repaint();
			
			try {
				Thread.sleep(1);
			}catch(Exception e) { }
		}
	}
}
