package Quoridor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;	


public class GraphPosition extends JPanel {
	
	static boolean contains(ArrayList<int[]> edges, int[] edge) {
		for(int i=0; i<edges.size(); i++) {
			if(edge[0]== edges.get(i)[0] && edge[1]== edges.get(i)[1]) {
				return true;
			}
		}
		return false;
	}
	
	static void initVertices(ArrayList<int[]> edges,ArrayList<Object> vertices, Graphics2D g2d) {
		int black = vertices.indexOf('b');
		int white = vertices.indexOf('w');
		for(int  y=0;y<9;y++){
			for(int x=0;x<9;x++){
				int leftDisp =70+x*60;
				int topDisp = 30+y*60;
				g2d.setColor(new Color(106,75, 53));
				g2d.fillRect(leftDisp, topDisp, 45, 45);
				if(x+y*9 == black){
					g2d.setColor(Color.BLACK);
					g2d.fillOval(leftDisp+7, topDisp+7, 30, 30);
				}
				else if(x+y*9 == white ){
					g2d.setColor(new Color(240,240,240));
					g2d.fillOval(leftDisp+7, topDisp+7, 30, 30);

				}
			}
		}
	}
		
	static void initEdges2(ArrayList<int[]> edges,Graphics2D g2d){
		for(int l =0; l<edges.size(); l++){
			if(edges.get(l)[1]==edges.get(l)[0]+1){
				int fila = edges.get(l)[0]/9;
				int columna = edges.get(l)[0]%9;
				int topDisp = 15+ 15+fila*60;
				int leftDisp = 95 +20+ columna*60;		
				g2d.setColor(Color.WHITE);
				g2d.fillRect(leftDisp, topDisp, 20, 60);
			}
			if(edges.get(l)[1]==edges.get(l)[0]+9){
				int fila = edges.get(l)[0]/9;
				int columna = edges.get(l)[0]%9;
				int topDisp = 55+20+fila*60;
				int leftDisp = 55 +15+columna*60;		
				g2d.setColor(Color.WHITE);
				g2d.fillRect(leftDisp, topDisp, 45, 15);

			}
		}	
	}
	
	static void initEdges(int[] horizontal_tiles_placed, int[] vertical_tiles_placed,Graphics2D g2d) {
		for (int i=0; i< 64; i++) {
			if(vertical_tiles_placed[i]==1) {
				int leftDisp = 115 +(i%8)*60;
				int topDisp = 30+ (i/8)*60;
				g2d.setColor(new Color(230,152,76));
				g2d.fillRect(leftDisp, topDisp, 15,105);
//				g2d.setColor(Color.RED);
//		        g2d.setFont(new Font("Arial Black", Font.BOLD, 20));
//				g2d.drawString(" "+i, 610,+10*i);
			}
		}
		for (int i=0; i< 64; i++) {
			if(horizontal_tiles_placed[i]==1) {
				int leftDisp = 130-60 +(i%8)*60;
				int topDisp = 75+ (i/8)*60;
				g2d.setColor(new Color(230,152,76));
				g2d.fillRect(leftDisp, topDisp, 105,15);
//				g2d.setColor(Color.RED);
//		        g2d.setFont(new Font("Arial Black", Font.BOLD, 20));
//				g2d.drawString(" "+i, 10*i,580);
			}
		}
	}
	
	static void initData(char AI_1_colour,char AI_2_colour,int AI_1_walls,int AI_2_walls, int turns, Graphics2D g2d) {
        turns = turns +1;
		g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial Black", Font.BOLD, 20));
		g2d.drawString("TURN #"+turns, 650, 60);
		g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial Black", Font.BOLD, 20));
		g2d.drawString("AI_1 colour: "+AI_1_colour, 610, 100);
	    g2d.setColor(Color.BLACK);
	    g2d.setFont(new Font("Arial Black", Font.BOLD, 20));
		g2d.drawString("AI_2 colour: "+AI_2_colour, 610, 130);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial Black", Font.BOLD, 20));
		g2d.drawString("AI_1 walls: "+AI_1_walls, 610, 160);
	    g2d.setColor(Color.BLACK);
	    g2d.setFont(new Font("Arial Black", Font.BOLD, 20));
		g2d.drawString("AI_2 walls: "+AI_2_walls, 610, 190);

	}
	
	static Graphics2D initBackground(BufferedImage img) {
		Graphics2D  g2d = img.createGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, 800, 600);
//		g2d.setColor(new Color(230,152,76));
//		g2d.fillRect(70, 30, 525, 525);
		return g2d;
	}
	
	public static void main(ArrayList<Object> vertices, ArrayList<int[]> edges, String filename ,String directory, char AI_1_colour, char AI_2_colour, int AI_1_walls, int AI_2_walls, int turns,int[] horizontal_tiles_placed , int[] vertical_tiles_placed) throws IOException {		

		BufferedImage Image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g2d = initBackground(Image);
		initData(AI_1_colour,AI_2_colour,AI_1_walls,AI_2_walls,turns,g2d);
		initVertices(edges,vertices,g2d);
		initEdges(horizontal_tiles_placed, vertical_tiles_placed, g2d);
		g2d.dispose();
		
        File f = new File(directory);
        if (!f.exists()) {
        	if (f.mkdir()) {
        		System.out.println("Directory is created!");
        	} else {
                System.out.println("Failed to create directory!");
            }
        }
		
		File file= new File(directory+"\\"+filename+".png");
		ImageIO.write(Image, "PNG", file);
		
	}

}
