package Quoridor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class QuoridorAIcompetition {
	
	static ArrayList<Object> vertices = new ArrayList<Object>();
	static ArrayList<int[]> edges = new ArrayList<int[]>();
	
	static int[] horizontal_tiles = new int[64];
	static int[] vertical_tiles = new  int[64];
	
	static int SIMS = 20;
	
	static void setVertices() {
		vertices.clear();
		for(int i=0;i<81;i++){
			vertices.add(i);
		}
		vertices.set(4, 'w');
		vertices.set(76, 'b');
	}
	
	static void setEdges(){
		edges.clear();
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				edges.add( new int[] {9*i+j,9*i+j+1} );
				edges.add( new int[] {9*i+j , 9*i+j+9} );
			}
		}
		for(int i=1;i<9;i++){
			edges.add( new int[] {9*i-1,9*i+8});
			edges.add( new int[] {71+i,72+i});
		}
	}
	
	static void init() {
		QuoridorAIcompetition.setVertices();
		QuoridorAIcompetition.setEdges();
		Arrays.fill(horizontal_tiles, 0);
		Arrays.fill(vertical_tiles, 0);
		AI_1.walls = 10;
		AI_2.walls = 10;
	}
	
	static void logRecord(PrintWriter f,String currentPlayer) {
		String verticeString = vertices.stream().map(Object::toString)
                .collect(Collectors.joining(", "));
		f.println(currentPlayer);
		f.println(verticeString );
		String edgeString = CommonMethods.convertToString(edges);
		f.println(edgeString);
		f.println(" ");
	}
	
	public static void main(String[] args) throws IOException {
		int AI_1_win_counts  = 0;
		int AI_2_win_counts = 0;
		System.out.println("Iniciando");
		for(int j=0; j<SIMS; j++) {
			init();
			boolean playing = true;
			int turns = 0;
			int starting_player_wins = 0;
			PrintWriter f= new PrintWriter("record"+j+".txt");
			if (ThreadLocalRandom.current().nextInt(0, 2) == 0 ) {
				
				AI_1.colour = 'b';
				AI_2.colour = 'w';

				while(playing){
					AI_1.main(args);
					logRecord(f,"AI_1 plays:");
					GraphPosition.main(vertices, edges,"position"+turns+"_a_ai_1","Directory"+j, AI_1.colour, AI_2.colour, AI_1.walls, AI_2.walls,turns);
					if(AI_1.AI_1_Wins() ) {
						playing= false;
						AI_1_win_counts++;
						starting_player_wins++;
					}
					else if(playing) {
						AI_2.main(args);
						logRecord(f,"AI_2 plays:");
						GraphPosition.main(vertices, edges,"position"+turns+"_b_ai_2","Directory"+j, AI_1.colour, AI_2.colour, AI_1.walls, AI_2.walls,turns);
						turns++;
						if(AI_2.AI_2_Wins() ) {
							playing = false;
							AI_2_win_counts++;
						}
					}
					
					System.out.println(turns);
				}
				f.close();
			}
			
			else {
				
				AI_1.colour = 'w';
				AI_2.colour = 'b';
				
				while(playing){
					AI_2.main(args);
					logRecord(f,"AI_2 plays:");
					GraphPosition.main(vertices, edges,"position"+turns+"_a_ai_2","Directory"+j, AI_1.colour, AI_2.colour, AI_1.walls, AI_2.walls,turns);
					if(AI_2.AI_2_Wins() ) {
						playing= false;
						AI_2_win_counts++;
						starting_player_wins++;
					}
					else if(playing) {
						AI_1.main(args);
						logRecord(f,"AI_1 plays:");
						GraphPosition.main(vertices, edges,"position"+turns+"_b_ai_1","Directory"+j, AI_1.colour, AI_2.colour, AI_1.walls, AI_2.walls,turns);
						turns++;
						if(AI_1.AI_1_Wins() ) {
							playing = false;
							AI_1_win_counts++;
						}
					}
					System.out.println(turns);
				}
			}
			
		}
		
		System.out.println("AI_1 wins: "+AI_1_win_counts);
		System.out.println("AI_2 wins: "+AI_2_win_counts);
		
	}
}
