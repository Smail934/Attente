import java.io.*;
import java.util.*;

public class Save {
	public void loadSave(File loadPath) {
		try {
		Scanner loadScanner = new Scanner(loadPath); // scanner permet la saisie d'info
		
		while(loadScanner.hasNext()) {
			Ecran.killsPourGG = loadScanner.nextInt();
			for(int y=0;y<Ecran.room.block.length;y++) {
				for(int x=0;x<Ecran.room.block[0].length;x++) {
					Ecran.room.block[y][x].solID= loadScanner.nextInt();
				}
			}
			for(int y=0;y<Ecran.room.block.length;y++) {
				for(int x=0;x<Ecran.room.block[0].length;x++) {
					Ecran.room.block[y][x].airID= loadScanner.nextInt();
				}
			}
		}
		
		loadScanner.close();
		}catch(Exception e) { }
	}
}
