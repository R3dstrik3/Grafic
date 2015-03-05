package editor;

import java.awt.Color;
import java.awt.Point;

import grafic.Block;

public class Curs extends Block{
	int index = 0;
	
	public Curs(){
		p = new Point(0,0);
	}
	
	public void load(String str){
		index++;
		
		if (index >20){
			index =1;
		}
		int a = index*10;
		image = new Color[a][a];
		for (int i=0; i<a ;i++){
			for (int j=0; j<a ; j++){
				double b = Math.pow(i, 2)+Math.pow(j, 2);
				if ((b>index*50 && b<index*70) || i<3 && j<3){
					image[i][j] = Color.orange;
				}else{
					image[i][j] = null;
				}
			}
		}
	}
}
