package Quoridor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;	


public class GraphPosition2 extends JPanel {
	static ArrayList<int[]> AllEdges = new ArrayList<int[]>();
	
	static void setEdges(){
		AllEdges.clear();
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				AllEdges.add( new int[] {9*i+j,9*i+j+1} );
				AllEdges.add( new int[] {9*i+j , 9*i+j+9} );
			}
		}
		for(int i=1;i<9;i++){
			AllEdges.add( new int[] {9*i-1,9*i+8});
			AllEdges.add( new int[] {71+i,72+i});
		}
	}
	
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
	
	static void initEdges(ArrayList<int[]> AllEdges, ArrayList<int[]> edges,Graphics2D g2d){
		for(int l =0; l<AllEdges.size(); l++){
			int[] edge = AllEdges.get(l);
			if(!contains(edges,edge)){
				if(edge[1] == edge[0]+1) {
					int fila = edge[0]/9;
					int columna = edge[0]%9;
					int topDisp = 37+15+fila*60;
					int leftDisp = 95+3 +20+ columna*60;
					
					g2d.setColor(new Color(230,152,76));
					g2d.fillRect(leftDisp, topDisp, 30, 7);
					
				}
				else {
					int fila = edge[0]/9;
					int columna = edge[0]%9;
					int topDisp = 55+20+5+fila*60;
					int leftDisp = 77 +15+columna*60;
					
					g2d.setColor(new Color(230,152,76));
					g2d.fillRect(leftDisp, topDisp, 7, 30);	
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
	
	static Graphics2D initBackground(BufferedImage img) {
		Graphics2D  g2d = img.createGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, 700, 700);

		return g2d;
	}
	
	public static void main(ArrayList<Object> vertices, ArrayList<int[]> edges, String filename ,String directory) throws IOException {		

		setEdges();
		BufferedImage Image = new BufferedImage(650, 600, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g2d = initBackground(Image);
		
		initVertices(edges,vertices,g2d);
		initEdges(AllEdges,edges,g2d);

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