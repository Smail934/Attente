import java.awt.*;

public class Mob extends Rectangle{
	public int CoorX,CoorY;
	public int mobSize= 52;
	public int mobID = Value.mobAir;
	public boolean visible=false;
	public int walkFrame=0,speed=20;
	public int distanceParcourue=0;
	public int Haut=0,Bas=1,Droite=2,Gauche=3;
	public int direction=Droite;
	public boolean hasHAUT=false;
	public boolean hasBAS=false;
	public boolean hasGAUCHE=false;
	public boolean hasDROITE=false;
	public int HP;
	public int HPspace=3, HPhauteur=6;
	public Mob() {
		
	}
	
	public void spawnMob(int mobID) {
		for(int y = 0; y<Ecran.room.block.length;y++) {
			if(Ecran.room.block[y][0].solID == Value.chemin) {
				setBounds(Ecran.room.block[y][0].x,Ecran.room.block[y][0].y,mobSize,mobSize);
				CoorX=0;
				CoorY=y;
			}
		}
		
		this.mobID=mobID;
		this.HP = mobSize;
		visible=true;
	}
	
	
	public void deleteMob() {
		visible = false;
		direction=Droite;
		distanceParcourue=0;
		
		//Ecran.room.block[0][0].getMoney(mobID);
		if (HP <= 0) {
			Ecran.money += Value.recompense[mobID];
			Ecran.kill +=1;
		}
	}
	
	public void perteHP() {
		Ecran.vie -=1;
	}
	
	public void physic() {
		if(walkFrame>=speed) {
			if(direction == Droite ) {
				x+=1;
			}else if (direction == Haut) {
				y-=1;
			}else if ( direction == Bas) {
				y+=1;
			}else if ( direction == Gauche) {
				x-=1;
			}
			distanceParcourue+=1;
			
			if(distanceParcourue == Ecran.room.blockSize ) {
				if(direction == Droite ) {
					CoorX+=1;
					hasDROITE=true;
				}else if (direction == Haut) {
					CoorY-=1;
					hasHAUT=true;
				}else if ( direction == Bas) {
					CoorY+=1;
					hasBAS=true;
				}else if( direction == Gauche) {
					CoorX-=1;
					hasGAUCHE=true;
				}
				if(!hasHAUT) {
					try {
						if(Ecran.room.block[CoorY+1][CoorX].solID == Value.chemin ) {
							direction=Bas;
						}
					}catch(Exception e) { }
				}
				if(!hasBAS) {
					try {
						if(Ecran.room.block[CoorY-1][CoorX].solID == Value.chemin ) {
							direction=Haut;
						}
					}catch(Exception e) { }
				}
				if(!hasGAUCHE) {
					try {
						if(Ecran.room.block[CoorY][CoorX+1].solID == Value.chemin ) {
							direction=Droite;
						}
					}catch(Exception e) { }
				}
				if(!hasDROITE) {
					try {
						if(Ecran.room.block[CoorY][CoorX-1].solID == Value.chemin ) {
							direction=Gauche;
						}
					}catch(Exception e) { }
				}
				if(Ecran.room.block[CoorY][CoorX].airID == Value.airCave) {
					deleteMob();
					perteHP();
				}
				hasHAUT=false;
				hasBAS=false;
				hasDROITE=false;
				hasGAUCHE=false;
				distanceParcourue=0;
			}
			walkFrame=0;
		}else {
			walkFrame+=1;
		}
		
	}
	
	public void loseHP(int degat) {
		HP-=degat;
		checkMort();
	}
	
	public void checkMort() {
		if(HP == 0) {
			deleteMob();
		}
	}
	
	public boolean estMort() {
		if(visible) {
			return false;
		}else {
			return true;
		}
	}
	
	
	
	public void draw(Graphics g) {	
		g.drawImage(Ecran.tileset_mob[mobID],x,y,width,height,null);
		
		// Barre de vie 
		g.setColor(new Color(100,50,50)); 
		g.fillRect(x , y -(HPspace + HPhauteur),width , HPhauteur);
		
		g.setColor(new Color(50,100,50));
		g.fillRect(x , y -(HPspace + HPhauteur),HP , HPhauteur);
		
		g.setColor(new Color(0,0,0));
		g.drawRect(x , y -(HPspace + HPhauteur),HP-1 , HPhauteur-1);
		
	}
}
