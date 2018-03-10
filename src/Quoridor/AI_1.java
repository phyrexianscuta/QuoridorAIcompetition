package Quoridor;

import java.util.ArrayList;
import java.util.Arrays;

public class AI_1 {
	
	static char colour;

	static int walls = 10;
	
	static public boolean AI_1_Wins(ArrayList<Object> vertices) {
		if(AI_1.colour=='b'){
			int location = vertices.indexOf('b');
			int[] opposite = {0,1,2,3,4,5,6,7,8};
			if( CommonMethods.contains(opposite,location) ){
				return true;
			}
			return false;
		}
		else if(AI_1.colour=='w'){
			int location = vertices.indexOf('w');
			int[] opposite = {72,73,74,75,76,77,78,79,80};
			if( CommonMethods.contains(opposite,location) ){
				return true;
			}
			return false;
		}
	return true;
	}
	
	public static void main(String[] args) {
		System.out.println("hola AI_1");
		Move bestMove = new Move();
		bestMove = Minimax_AI_1.main(AI_1.colour, QuoridorAIcompetition.vertices,QuoridorAIcompetition.edges,QuoridorAIcompetition.vertical_tiles,QuoridorAIcompetition.horizontal_tiles,0,-99999f, 99999f,AI_2.walls,AI_1.walls);

		if(bestMove.kind.charAt(0) !='P') {
			int indexBM = CommonMethods.topLeftVertex(bestMove.index);
			walls --;
			if( bestMove.kind.charAt(0) =='H'){
				QuoridorAIcompetition.horizontal_tiles[bestMove.index] = 1;
				QuoridorAIcompetition.horizontal_tiles_placed[bestMove.index] = 1;
				
				if(bestMove.index+1<64 && bestMove.index/8 == (bestMove.index+1)/8){
					QuoridorAIcompetition.horizontal_tiles[bestMove.index+1] = 1;
				}
				if(bestMove.index-1>=0 && Math.floor(bestMove.index/8) == Math.floor((bestMove.index-1)/8)){
					QuoridorAIcompetition.horizontal_tiles[bestMove.index-1]=1;
				}
				QuoridorAIcompetition.vertical_tiles[bestMove.index]=1;
				QuoridorAIcompetition.edges.remove( CommonMethods.lookFor(new int[ ] {indexBM,indexBM+9}, QuoridorAIcompetition.edges ) );
				QuoridorAIcompetition.edges.remove( CommonMethods.lookFor(new int[ ] {indexBM+1,indexBM+10}, QuoridorAIcompetition.edges ) );
			}
			else {
				QuoridorAIcompetition.vertical_tiles[bestMove.index] = 1;
				QuoridorAIcompetition.vertical_tiles_placed[bestMove.index] = 1;
				if(bestMove.index+8<64){	
					QuoridorAIcompetition.vertical_tiles[bestMove.index+8] = 1;
				}
				if(bestMove.index-8>=0){
					QuoridorAIcompetition.vertical_tiles[bestMove.index-8] = 1;
				}	
				QuoridorAIcompetition.horizontal_tiles[bestMove.index] = 1;
				QuoridorAIcompetition.edges.remove( CommonMethods.lookFor(new int[ ] {indexBM,indexBM+1}, QuoridorAIcompetition.edges ) );
				QuoridorAIcompetition.edges.remove( CommonMethods.lookFor(new int[ ] {indexBM+9,indexBM+10}, QuoridorAIcompetition.edges ) );				
			}
		}
		else {
			int start = QuoridorAIcompetition.vertices.indexOf(colour);
			QuoridorAIcompetition.vertices.set(start, start);
			QuoridorAIcompetition.vertices.set(bestMove.index,colour); 
		}

	}

}
