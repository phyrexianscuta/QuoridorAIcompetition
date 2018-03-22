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
	//All possible places where to locate an horizontal/vertical wall. A 0 represents the space is available, a 1 that is unavailable, but not that necessarily there was a wall placed there.
	static int[] horizontal_tiles = new int[64];
	static int[] vertical_tiles = new  int[64];
	//All possible places where to locate an horizontal /vertical wall, the difference is that an 1 indicates that a wall was located there. Thus, there are at most ten 1s in each array.
	//They are used only for graphing the board.
	static int[] horizontal_tiles_placed = new int[64];
	static int[] vertical_tiles_placed = new  int[64];
	
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
		Arrays.fill(horizontal_tiles_placed, 0);
		Arrays.fill(vertical_tiles_placed, 0);
		AI_1.walls = 10;
		AI_2.walls = 10;
	}
	
	public static void placeHW_DEBUGTOOL(int index){
		int indexBM = CommonMethods.topLeftVertex(index);

		horizontal_tiles[index] = 1;
		horizontal_tiles_placed[index] = 1;
		if(index+1<64 && index/8 == (index+1)/8){
			horizontal_tiles[index+1] = 1;
		}
		if(index-1>=0 && index/8 == (index-1)/8){
			horizontal_tiles[index-1] = 1;
		}
		vertical_tiles[index] = 1;

		edges.remove( CommonMethods.lookFor(new int[ ] {indexBM,indexBM+9}, edges ) );
		edges.remove( CommonMethods.lookFor(new int[ ] {indexBM+1,indexBM+10}, edges ) );
	}
	
	public static void placeVW_DEBUGTOOL(int index){
		int indexBM = CommonMethods.topLeftVertex(index);
		vertical_tiles[index] = 1;
		vertical_tiles_placed[index] = 1;
		if(index+8<64){
			vertical_tiles[index+8] = 1;
		}
		if(index-8>=0){
			vertical_tiles[index-8] = 1;
		}
		horizontal_tiles[index] = 1;		
		edges.remove( CommonMethods.lookFor(new int[ ] {indexBM,indexBM+1}, edges ) );
		edges.remove( CommonMethods.lookFor(new int[ ] {indexBM+9,indexBM+10}, edges ) );
		}
	
	static void logRecord(PrintWriter f,String currentPlayer) {
		String verticeString = vertices.stream().map(Object::toString)
                .collect(Collectors.joining(", "));
		f.println(currentPlayer);
		f.println(verticeString );
		String horizontalTilesPlacedString = Arrays.toString(horizontal_tiles_placed);
		String verticalTilesPlacedString = Arrays.toString(vertical_tiles_placed);

		f.println("horizontal Tiles:");
		f.println(horizontalTilesPlacedString);
		f.println("vertical Tiles:");
		f.println(verticalTilesPlacedString);

		f.println(" ");
	}
	
	public static void main(String[] args) throws IOException {
		int AI_1_win_counts  = 0;
		int AI_2_win_counts = 0;
		int starting_player_wins = 0;
		System.out.println("Iniciando");
		for(int j=0; j<SIMS; j++) {
			init();
			boolean playing = true;
			int turns = 0;

			PrintWriter f= new PrintWriter("log "+j+".txt");
			if (ThreadLocalRandom.current().nextInt(0, 2) == 0 ) {
				
				AI_1.colour = 'b';
				AI_2.colour = 'w';
				
	//			placeHW_DEBUGTOOL(1);
	//			placeHW_DEBUGTOOL(3);
	//			placeHW_DEBUGTOOL(5);
	//			placeHW_DEBUGTOOL(7);
	//			placeHW_DEBUGTOOL(41);
	//			placeHW_DEBUGTOOL(43);
	//			placeHW_DEBUGTOOL(45);
	//			placeHW_DEBUGTOOL(47);
				
	//			placeVW_DEBUGTOOL(0);
	//			placeVW_DEBUGTOOL(18);
	//			placeVW_DEBUGTOOL(32);
	//			placeVW_DEBUGTOOL(48);
	//			placeVW_DEBUGTOOL(51);
	//			placeVW_DEBUGTOOL(57);

	//			AI_1.walls = 5;
	//			AI_2.walls = 4;
				
	//			vertices.set(4,4);
	//			vertices.set(76,76);
				
	//			vertices.set(29, 'w');
	//			vertices.set(30, 'b');

				GraphPosition.main(vertices, edges,"000Inicial","Directory "+j, AI_1.colour, AI_2.colour, AI_1.walls, AI_2.walls,turns,horizontal_tiles_placed,vertical_tiles_placed);

				while(playing){
					AI_1.main(args,turns);
					int tuto = turns +1;
					f.println("Turno: "+tuto);
					logRecord(f,"AI_1 plays:");
					GraphPosition.main(vertices, edges,"position"+turns+"_a_ai_1","Directory "+j, AI_1.colour, AI_2.colour, AI_1.walls, AI_2.walls,turns,horizontal_tiles_placed,vertical_tiles_placed);
					if(AI_1.AI_1_Wins(vertices) ) {
						playing= false;
						AI_1_win_counts++;
						starting_player_wins++;
					}
					else if(playing) {
						AI_2.main(args,turns);
						logRecord(f,"AI_2 plays:");
						GraphPosition.main(vertices, edges,"position"+turns+"_b_ai_2","Directory "+j, AI_1.colour, AI_2.colour, AI_1.walls, AI_2.walls,turns,horizontal_tiles_placed,vertical_tiles_placed);
						turns++;
						if(AI_2.AI_2_Wins(vertices) ) {
							playing = false;
							AI_2_win_counts++;
						}
						f.println("--------------");
					}
					
					System.out.println(turns);
				}
				f.close();
			}
			
			else {
				
				AI_1.colour = 'w';
				AI_2.colour = 'b';
				
				GraphPosition.main(vertices, edges,"000Inicial","Directory "+j, AI_1.colour, AI_2.colour, AI_1.walls, AI_2.walls,turns,horizontal_tiles_placed,vertical_tiles_placed);
				
				while(playing){
					AI_2.main(args,turns);
					logRecord(f,"AI_2 plays:");
					GraphPosition.main(vertices, edges,"position"+turns+"_a_ai_2","Directory "+j, AI_1.colour, AI_2.colour, AI_1.walls, AI_2.walls,turns,horizontal_tiles_placed,vertical_tiles_placed);
					if(AI_2.AI_2_Wins(vertices) ) {
						playing= false;
						AI_2_win_counts++;
						starting_player_wins++;
					}
					else if(playing) {
						AI_1.main(args,turns);
						logRecord(f,"AI_1 plays:");
						GraphPosition.main(vertices, edges,"position"+turns+"_b_ai_1","Directory "+j, AI_1.colour, AI_2.colour, AI_1.walls, AI_2.walls,turns,horizontal_tiles_placed,vertical_tiles_placed);
						turns++;
						if(AI_1.AI_1_Wins(vertices) ) {
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
		System.out.println("The starting player wins "+starting_player_wins+" times.");
		
	}
}
