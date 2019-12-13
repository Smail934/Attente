import java.awt.*;

public class Magasin {
	public static int shopWidth=8;
	public static int buttonSize= 52;
	public static int cellSpace=2;
	public static int awayFromRoom =25;
	public static int itemIn=4;
	public static int[] buttonID = {Value.airTowerLaser,Value.airAir,Value.airAir,Value.airAir,Value.airAir,Value.airAir,Value.airAir,Value.airTrashCan};
	public static int heldID = -1;
	public static int[] buttonPrix = {10,0,0,0,0,0,0,0};
	public static int realID=-1;
	
	public Rectangle[] button=new Rectangle[shopWidth];
	public Rectangle VieJoueur;
	public Rectangle MoneyJoueur;
	public static int IconeSize=20;
	public static int IconeSpace = 3;
	public static int IconeTextY = 14;
	
	public boolean holdsItem = false;
	
	public Magasin() {
		define();
	}
	public void click(int mouseButton) {
		if(mouseButton == 1) {
			for(int i = 0 ; i<button.length;i++) {
				if(button[i].contains(Ecran.mse)) {
					if(buttonID[i] != Value.airAir)
						if(buttonID[i] == Value.airTrashCan) { // supprime l'item
							holdsItem=false;
						}else {
							heldID=buttonID[i];
							realID=i;
							holdsItem=true;
						}
				}
			}
			if(holdsItem) {
				if(Ecran.money >= buttonPrix[realID]) {
					for(int y=0;y<Ecran.room.block.length;y++) {
						for(int x=0;x<Ecran.room.block[0].length;x++) {
							if(Ecran.room.block[y][x].contains(Ecran.mse)) {
								if(Ecran.room.block[y][x].solID !=Value.chemin && Ecran.room.block[y][x].airID == Value.airAir) {
									Ecran.room.block[y][x].airID = heldID;
									Ecran.money -=buttonPrix[realID];
								}
							}
						}
					}
					
				}
			}
		}
		
	}
	public void define(){
		for(int i=0; i<button.length;i++) {
			button[i]= new Rectangle( (Ecran.myWidth/2) - ((shopWidth*(buttonSize+cellSpace))/2) + ((buttonSize+cellSpace)*i),(Ecran.room.block[Ecran.room.worldHeight-1][0].y) + Ecran.room.blockSize+awayFromRoom,buttonSize,buttonSize);
		}
		
		VieJoueur = new Rectangle(Ecran.room.block[0][0].x -1, button[0].y,IconeSize,IconeSize);
		MoneyJoueur = new Rectangle(Ecran.room.block[0][0].x -1, button[0].y + button[0].height -IconeSize,IconeSize,IconeSize);
	}
	
	public void draw (Graphics g) {
		for(int i=0; i<button.length;i++) {
			if(button[i].contains(Ecran.mse)) {
				g.setColor(new Color(255,255,255,150));
				g.fillRect(button[i].x, button[i].y, button[i].width, button[i].height);
			}
			g.drawImage(Ecran.tileset_cell[0],button[i].x, button[i].y, button[i].width, button[i].height,null);
			if(buttonID[i] != Value.airAir)g.drawImage(Ecran.tileset_air[buttonID[i]],button[i].x + itemIn, button[i].y+ itemIn, button[i].width - (itemIn*2), button[i].height- (itemIn*2),null);
			if(buttonPrix[i]>0) {
				
				g.setColor(new Color(255,255,255));
				g.setFont(new Font("Courier New",Font.BOLD,14));
				g.drawString(buttonPrix[i] + "",button[i].x + itemIn, button[i].y+ itemIn+10);
			}
		}
		
		g.drawImage(Ecran.tileset_cell[1],VieJoueur.x, VieJoueur.y, VieJoueur.width, VieJoueur.height,null);
		g.drawImage(Ecran.tileset_cell[2],MoneyJoueur.x, MoneyJoueur.y,MoneyJoueur.width, MoneyJoueur.height,null);
		g.setFont(new Font("Courier New",Font.BOLD,14)); // Police d'écriture
		g.setColor((new Color(255,255,255)));
		g.drawString("" + Ecran.vie ,VieJoueur.x + VieJoueur.width + IconeSpace,VieJoueur.y+IconeTextY);
		g.drawString("" + Ecran.money ,MoneyJoueur.x + MoneyJoueur.width + IconeSpace,MoneyJoueur.y+IconeTextY);
		
		if(holdsItem) {
			g.drawImage(Ecran.tileset_air[heldID], Ecran.mse.x - ((button[0].width - (itemIn*2) )/2) +itemIn, Ecran.mse.y - ((button[0].width - (itemIn*2))/2) + itemIn,button[0].width - (itemIn*2), button[0].height- (itemIn*2),null);
		}
	}
}
