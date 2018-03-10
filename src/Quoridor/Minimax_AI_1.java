package Quoridor;

import java.util.ArrayList;

public class Minimax_AI_1 {
	
	public static float evaluatingfunction(ArrayList<Object> vertices, ArrayList<int[]> edges ) {
		int distAI_1 = CommonMethods.Dijstrak(AI_1.colour,vertices, edges);
		int distAI_2 = CommonMethods.Dijstrak(AI_2.colour,vertices, edges);
//		int movesToNextRowAI = movesToNextRow("O",vertices, edges);
//		int movesToNextRowMe = movesToNextRow("M",vertices, edges);
		return (float) distAI_2 - distAI_1; // + (movesToNextRowMe - movesToNextRowAI)/4;
	
	}
	
	public static Move main(char player,ArrayList<Object> vertices,ArrayList<int[]> edges,int[] vertical_tiles,int[] horizontal_tiles,int depth,float alpha,float beta,int walls_ai_2,int walls_ai_1) {
		char oppositePlayer = CommonMethods.findOppositePlayer(player);

		if( AI_2.AI_2_Wins(vertices) ) {
			Move x = new Move();
			x.score = depth-100;
			return x;
		}
		else if( AI_1.AI_1_Wins(vertices) ){
			Move x = new Move();
			x.score = 100-depth;
			return x;
		}
		else if(depth==3){
			Move x = new Move();
			float ev = evaluatingfunction(vertices,QuoridorAIcompetition.edges );
			x.score = ev;
			return x;
		}
		ArrayList<Move> avMoves = new ArrayList<Move>();
		avMoves = CommonMethods.availableMoves(player,vertices,edges,walls_ai_2,walls_ai_1,vertical_tiles,horizontal_tiles);

//		avMoves = CommonMethods.shuffle(avMoves);
		Move bestMove = new Move();
		if(player == AI_2.colour){
			bestMove.score = 999f;
			for(int l=0; l < avMoves.size(); l++){
				Move move= new Move();
				ArrayList<int[]> edgesCopy = new ArrayList<>(edges);
				ArrayList<Object> verticesCopy = new ArrayList<Object>(vertices);
				int[] vertical_tiles_copy = new int[64];
				System.arraycopy( vertical_tiles, 0, vertical_tiles_copy, 0, vertical_tiles.length );
				int[] horizontal_tiles_copy = new int[64];
				System.arraycopy( horizontal_tiles, 0, horizontal_tiles_copy, 0, horizontal_tiles.length );
				int walls_ai_1_copy= walls_ai_1;
				int walls_ai_2_copy= walls_ai_2;
				if(avMoves.get(l).kind== "P"){
					move.kind="P";
					move.index = vertices.indexOf(avMoves.get(l).index);
					int start = verticesCopy.indexOf(player);
					verticesCopy.set(start, start);
					verticesCopy.set(move.index, player);
				}
				else if(avMoves.get(l).kind== "HW"){
					move.kind="HW";
					move.index = avMoves.get(l).index;
					horizontal_tiles_copy[move.index] = 1;
					walls_ai_2_copy -= 1;
					if(move.index+1<65 && move.index/8 == (move.index+1)/8 ){
						horizontal_tiles_copy[move.index+1] = 1;
					}
					if(move.index-1>=0 && move.index/8 == (move.index-1)/8 ){
						horizontal_tiles_copy[move.index-1] = 1;
					}
					vertical_tiles_copy[move.index] =1;
					int vertex_top_left = CommonMethods.topLeftVertex(move.index);
					
					edgesCopy.remove( CommonMethods.lookFor(new int[ ] {vertex_top_left,vertex_top_left+9}, edgesCopy) );
					edgesCopy.remove( CommonMethods.lookFor(new int[ ] {vertex_top_left+1,vertex_top_left+10}, edgesCopy) );
										
				}
				else if(avMoves.get(l).kind=="VW"){
					move.kind="VW";
					move.index = avMoves.get(l).index;
					vertical_tiles_copy[move.index] = 1;
					walls_ai_2_copy -= 1;
					if(move.index+8<64){
						vertical_tiles_copy[move.index+8] = 1;
					}
					if(move.index-8>=0){
						vertical_tiles_copy[move.index-8] = 1;
					}
					horizontal_tiles_copy[move.index] = 1;
					int vertex_top_left = CommonMethods.topLeftVertex(move.index);
					if( CommonMethods.lookFor(new int[] {vertex_top_left,vertex_top_left+1}, edgesCopy) <0) {
						System.out.println("error minimax ai_1");
					}
					edgesCopy.remove( CommonMethods.lookFor(new int[] {vertex_top_left,vertex_top_left+1}, edgesCopy) );
					if( CommonMethods.lookFor(new int[] {vertex_top_left+9,vertex_top_left+10}, edgesCopy) <0 ) {
						System.out.println("error minimax ai_1");
					}
					edgesCopy.remove( CommonMethods.lookFor(new int[] {vertex_top_left+9,vertex_top_left+10}, edgesCopy) );

				}
				Move result=Minimax_AI_1.main(oppositePlayer,verticesCopy,edgesCopy,vertical_tiles_copy,horizontal_tiles_copy,depth+1,alpha,beta,walls_ai_2_copy,walls_ai_1_copy);
				move.score = result.score;
				if(move.score < bestMove.score){
					bestMove.index = move.index;
					bestMove.score = move.score;
					bestMove.kind = move.kind;
				}
				beta = (float) Math.min(beta,bestMove.score);
				if(beta <= alpha){
					break;
				}
			}
			return bestMove;
		}
		else if(player==AI_1.colour){
			bestMove.score =-999f;
			for(int l=0;l< avMoves.size();l++){
				Move move= new Move();
				ArrayList<int[]> edgesCopy = new ArrayList<>(edges);
				ArrayList<Object> verticesCopy = new ArrayList<Object>(vertices);
				int[] vertical_tiles_copy = new int[64];
				System.arraycopy( vertical_tiles, 0, vertical_tiles_copy, 0, vertical_tiles.length );
				int[] horizontal_tiles_copy = new int[64];
				System.arraycopy( horizontal_tiles, 0, horizontal_tiles_copy, 0, horizontal_tiles.length );
				int walls_ai_1_copy= walls_ai_1;
				int walls_ai_2_copy= walls_ai_2;
				if(avMoves.get(l).kind== "P"){
					move.kind="P";
					move.index = vertices.indexOf(avMoves.get(l).index);
					int start = verticesCopy.indexOf(player);
					verticesCopy.set(start,start);
					verticesCopy.set(move.index, player);
				}
				
				else if(avMoves.get(l).kind== "HW"){
					move.kind="HW";
					move.index = avMoves.get(l).index;
					horizontal_tiles_copy[move.index] = 1;
					walls_ai_1_copy -= 1;
					if(move.index+1<65 && move.index/8 == (move.index+1)/8){
						horizontal_tiles_copy [move.index+1] = 1;
					}
					if(move.index-1>=0 && Math.floor(move.index/8) == Math.floor((move.index-1)/8)){
						horizontal_tiles_copy[move.index-1] = 1;
					}
					vertical_tiles_copy[move.index] =1;
					int vertex_top_left = CommonMethods.topLeftVertex(move.index);
					edgesCopy.remove( CommonMethods.lookFor(new int[ ] {vertex_top_left,vertex_top_left+9}, edgesCopy) );
					edgesCopy.remove( CommonMethods.lookFor(new int[] {vertex_top_left+1,vertex_top_left+10}, edgesCopy) );
				}
				else if(avMoves.get(l).kind=="VW"){
					move.kind="VW";
					move.index = avMoves.get(l).index;
					vertical_tiles_copy[move.index] = 1;
					walls_ai_1_copy -= 1;
					if(move.index+8<64){
						vertical_tiles_copy[move.index+8] = 1;
					}
					if(move.index-8>=0){
						vertical_tiles_copy[move.index-8] = 1;
					}
					horizontal_tiles_copy[move.index] = 1;
					int vertex_top_left = CommonMethods.topLeftVertex(move.index);
					edgesCopy.remove( CommonMethods.lookFor(new int[] {vertex_top_left,vertex_top_left+1}, edgesCopy) );
					edgesCopy.remove( CommonMethods.lookFor(new int[] {vertex_top_left+9,vertex_top_left+10}, edgesCopy) );
				}
				Move result= Minimax_AI_1.main(oppositePlayer,verticesCopy,edgesCopy,vertical_tiles_copy,horizontal_tiles_copy,depth+1,alpha,beta,walls_ai_2_copy,walls_ai_1_copy);
				move.score = result.score;
				if(move.score > bestMove.score){
					bestMove.index = move.index;
					bestMove.score = move.score;
					bestMove.kind = move.kind;
				}
				alpha = Math.max(alpha,bestMove.score);
				if(beta <= alpha){
					break;
				}
			}
			return bestMove;
		}
		Move movem = new Move();
		return movem;
	}
}