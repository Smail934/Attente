import java.awt.*;


public class Block extends Rectangle{
	public Rectangle towerXY;
	public int towerXYsize = 130;
	public int solID;
	public int airID;
	public int hitMob =-1;
	public boolean tirer = false;
	public int loseTime=100,loseFrame=0;
	
	
	public Block(int x, int y,int width, int height,int solID, int airID) {
		setBounds(x,y,width,height);
		towerXY		= new Rectangle(x - (towerXYsize/2),y - (towerXYsize/2),width + (towerXYsize),height + (towerXYsize));
		this.solID	= solID;
		this.airID	= airID; 
	}
	
	public void draw(Graphics g) {
		g.drawImage(Ecran.tileset_sol[solID],x,y,width,height,null);
		if(airID != Value.airAir) {
			g.drawImage(Ecran.tileset_air[airID],x,y,width,height,null);
		}
	}
	
	public void physic() {
		if(hitMob!=-1 && towerXY.intersects(Ecran.mobs[hitMob])) {
			tirer=true;
			
		}else {
			tirer=false;
		}
		if(!tirer) {
			if(airID == Value.airTowerLaser) {
				for(int i=0;i<Ecran.mobs.length;i++) {
					if(Ecran.mobs[i].visible) {
						if(towerXY.intersects(Ecran.mobs[i])) {
							tirer=true;
							hitMob=i;
						}
					}
				}
			}
		}
		if(tirer) {
			if(loseFrame>= loseTime) {
				Ecran.mobs[hitMob].loseHP(1);;
				loseFrame=0;
			}else {
				loseFrame+=1;
			}
			
			if(Ecran.mobs[hitMob].estMort()) {
				tirer=false;
				hitMob=-1;
				Ecran.Win();
			}
		}
	}
	
	public void getMoney(int mobID) {
		Ecran.money +=Value.recompense[mobID];
	}
	
	public void attaque(Graphics g) { // dessine la range des tourelle 
		if(Ecran.isDebug) {
			if(airID == Value.airTowerLaser) {
				g.setColor(new Color(255,255,255,150));
				g.drawRect(towerXY.x, towerXY.y, towerXY.width, towerXY.height);
			}
		}
		
		if(tirer) {
			g.setColor(new Color(255,255,0) );
			g.drawLine(x+(width/2), y+(height/2),Ecran.mobs[hitMob].x +(Ecran.mobs[hitMob].width/2) ,Ecran.mobs[hitMob].y +(Ecran.mobs[hitMob].height/2));
		}	
		
	}
}
