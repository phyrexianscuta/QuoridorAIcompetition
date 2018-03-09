package Quoridor;

import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;
import java.util.Collections;

public class CommonMethods {
	
	static public String convertToString(ArrayList<int[]> edges) {
		String result = "[";
		for (int i=0; i<edges.size();i++) {
//			result = result +"[";
			result = result+ "[" + edges.get(i)[0]+","+edges.get(i)[1]+"],";			
		}
		result = result + "]";
		return result;
	}
	
    static public boolean contains(final int[] array, final int v) {

        for(int i : array){
            if(i == v){
                return true;
            }
        }

        return false;
    }
    
    static public boolean containsNode(ArrayList<Node> arrayOfNodes, Node node) {
    	for (int i =0; i< arrayOfNodes.size(); i++) {
    		if(arrayOfNodes.get(i).index == node.index && arrayOfNodes.get(i).distance == node.distance){
    			return true;
    		}
    	}
    	
    	return false;
    }

	static public int lookFor(int[] lookFor, ArrayList<int[]> array) {
		for (int k = 0; k < array.size(); k++) {
			if (lookFor[0] == array.get(k)[0] && lookFor[1] == array.get(k)[1]
					|| lookFor[0] == array.get(k)[1] && lookFor[1] == array.get(k)[0]) {
				return k;
			}
		}
		return -1;
	}

	static public int topLeftVertex(int n) {
		int top_Vertex = n + n / 8;
		return top_Vertex;
	}
	
	static public boolean reachedOppositeSide(char player, ArrayList<Node> visited){
		int[] oppositeSide = {};
		if(player=='b'){
			 oppositeSide = new int[]{0,1,2,3,4,5,6,7,8};
		}
		else if(player=='w'){
			 oppositeSide = new int[]{80,79,78,77,76,75,74,73,72};
		}
		for (int i=0; i <visited.size();i++){
			if( contains(oppositeSide, visited.get(i).index ) ){
				return true;
			}
		}
		return false;
	}
	
	static public char findOppositePlayer(char player) {
		if (player == 'b') {
			return 'w';
		} else {
			return 'b';
		}
	}

	static public ArrayList<Move> shuffle(ArrayList<Move> array){
  		int currentIndex = array.size(); 
  		Move temporaryValue; 
  		int	randomIndex;
  		while (0 != currentIndex) {
  			Random rand = new Random();
			randomIndex = (int) Math.floor(rand.nextFloat() * currentIndex);
			currentIndex -= 1;
			temporaryValue = array.get(currentIndex);
			array.set(currentIndex,array.get(randomIndex));
			array.set(randomIndex,temporaryValue);
		}
  	return array;
	}

	static public ArrayList<Move> availableMoves(char player,ArrayList<Object> vertices, ArrayList<int[]> edges, int walls_ai_2, int walls_ai_1, int[] vertical_tiles, int[] horizontal_tiles ){
		ArrayList<Move> avMoves = new ArrayList<Move>();
		int start = vertices.indexOf(player);
		int numberwalls = 0;
		if(player==AI_1.colour){
			numberwalls = walls_ai_1;
		}
		else if(player==AI_2.colour){
			numberwalls = walls_ai_2;
		}
		for(int i=0; i <81;i++){
			if(!invalidMove(player,start,i,vertices,edges) ){
				Move move = new Move();
				move.kind ="P";
				move.index= i;
				avMoves.add(move);
			}
		}
		for(int i=0; i< horizontal_tiles.length;i++){
			if(horizontal_tiles[i]==0  && numberwalls >0){
				ArrayList<int[]> edgesCopy = new ArrayList<int[]>(edges);

				int vertex_top_left = topLeftVertex(i);
				edgesCopy.remove( CommonMethods.lookFor(new int[ ] {vertex_top_left,vertex_top_left+9}, edgesCopy) );
				edgesCopy.remove( CommonMethods.lookFor(new int[ ] {vertex_top_left+1,vertex_top_left+10}, edgesCopy) );
								
				if(!invalidWall(vertices,edgesCopy) && !invalidWall(vertices,edgesCopy) ){
					Move move = new Move();
					move.kind="HW";
					move.index= i;
					avMoves.add(move);
				}
			}
		}
		for(int i=0; i< vertical_tiles.length;i++){
			if(vertical_tiles[i]==0 && numberwalls>0){
				ArrayList<int[]> edgesCopy = new ArrayList<int[]>(edges);
				
				int vertex_top_left = topLeftVertex(i);
				
				edgesCopy.remove( CommonMethods.lookFor(new int[ ] {vertex_top_left,vertex_top_left+1}, edgesCopy) );
				edgesCopy.remove( CommonMethods.lookFor(new int[ ] {vertex_top_left+9,vertex_top_left+10}, edgesCopy) );					
				
				if(!invalidWall(vertices,edgesCopy) &&  !invalidWall(vertices,edgesCopy) ){
					Move move = new Move();
					move.kind="VW";
					move.index= i;
					avMoves.add(move);
				}
			}
		}
		return avMoves;
	}
	
	static public boolean invalidWall(ArrayList<Object> vertices, ArrayList<int[]> edgesCopy){
		return (!checkAbleReachGoal('b',vertices,edgesCopy) || !checkAbleReachGoal('w',vertices,edgesCopy));
}
	
	static public boolean checkAbleReachGoal(char player, ArrayList<Object> vertices, ArrayList<int[]> edges){
		ArrayList<Integer> toDoSet = new ArrayList<Integer>();
		ArrayList<Integer> doneSet = new ArrayList<Integer>();
		ArrayList<Integer> goal = new ArrayList<Integer>();
		if(player=='w'){
			Integer[] obj = {72,73,74,75,76,77,78,79,80};
			goal.addAll(Arrays.asList(obj));
		}
		else if(player == 'b'){
			Integer[] obj = {0,1,2,3,4,5,6,7,8};
			goal.addAll(Arrays.asList(obj));
		}
		Integer source = (Integer) vertices.indexOf(player);
		toDoSet.add(source);
		while(toDoSet.size()>0){
			Integer currentNode=toDoSet.get(0);
			doneSet.add(currentNode);
			toDoSet.remove(0);
			ArrayList<Integer> neighbors = findAdjacentNodes(currentNode,edges);
			for(int j=0;j<neighbors.size();j++){
				if(goal.indexOf(neighbors.get(j) )>-1){
					return true;
				}
				if(doneSet.indexOf(neighbors.get(j) ) == -1 && toDoSet.indexOf(neighbors.get(j) )==-1 ){
					toDoSet.add(neighbors.get(j) );
				}
			}
		}
		return false;
	}
	
	static public ArrayList<Integer> findAdjacentNodes(Integer vertice,  ArrayList<int[]> edges){
		ArrayList<Integer> neighbors = new ArrayList<Integer>() ;
		for (int i=0;i< edges.size( );i++){
			if( edges.get(i)[0] == vertice) {
				neighbors.add(edges.get(i)[1]);
			}
			else if(edges.get(i)[1] ==vertice){
				neighbors.add(edges.get(i)[0]);
			}
		}
		return neighbors;
	}
	
	static public boolean invalidMove(char player,int start,int end, ArrayList<Object> vertices, ArrayList<int[]> edges){
		char opp;
		if(player=='b'){
			opp = 'w';
		}
		else{
			opp = 'b';
		}
		
	//	Object vertex = vertices.get(end);
		//vertices.get(end) == (char) vertices.get(end)  )  { || 
		if(  vertices.get(end).getClass().getName() != "java.lang.Integer") { //   (char) vertices.get(end) == 'w' || (char) vertices.get(end) =='b'){
			return true;
		}
		if(Math.abs(start-end)==1 || Math.abs(start-end)==9){
			if(isAristaAvailable(start,end,edges)){
				return false;
			}
		}
		if(Math.abs(start-end)== 2 || Math.abs(start-end)== 18){
			if( end== start+18 && vertices.get(start+9).getClass().getName()=="java.lang.Character" && (char) vertices.get(start+9)== opp && isAristaAvailable(start,start+9,edges) && isAristaAvailable(start+9,start+18,edges)){
				return false;
			}
			if( end== start-18 && vertices.get(start-9).getClass().getName()=="java.lang.Character" && (char) vertices.get(start-9)==opp && isAristaAvailable(start,start-9,edges) && isAristaAvailable(start-9,start-18,edges)){
				return false;
			}
			if( end== start+2 && vertices.get(start+1).getClass().getName()=="java.lang.Character" && (char) vertices.get(start+1)==opp && isAristaAvailable(start,start+1,edges) && isAristaAvailable(start+1,start+2,edges)){
				return false;
			}
			if( end== start-2 && vertices.get(start-1).getClass().getName()=="java.lang.Character" && (char) vertices.get(start-1)==opp && isAristaAvailable(start,start-1,edges) && isAristaAvailable(start-1,start-2,edges)){
				return false;
			}
		}
		if(end==start+10 && vertices.get(start+1).getClass().getName()=="java.lang.Character"&&(char) vertices.get(start+1)==opp && isAristaAvailable(start,start+1,edges) && isAristaAvailable(start+1,start+10,edges) && !isAristaAvailable(start+1,start+2,edges) ){
			return false;
		}
		if(end==start+10 && vertices.get(start+9).getClass().getName()=="java.lang.Character"&&(char) vertices.get(start+9)==opp && isAristaAvailable(start,start+9,edges) && isAristaAvailable(start+9,start+10,edges) && !isAristaAvailable(start+9,start+18,edges) ){
			return false;
		}
		if(end==start+8 && start%9 !=0 && vertices.get(start+9).getClass().getName()=="java.lang.Character"&&(char) vertices.get(start+9)==opp && isAristaAvailable(start,start+9,edges) && isAristaAvailable(start+8,start+9,edges) && !isAristaAvailable(start+9,start+18,edges) ){
			return false;
		}
		if(end==start+8 && start%9 !=0 && vertices.get(start-1).getClass().getName()=="java.lang.Character"&&(char) vertices.get(start-1)==opp && isAristaAvailable(start,start-1,edges) && isAristaAvailable(start-1,start+8,edges) && !isAristaAvailable(start-1,start-2,edges) ){
			return false;
		}
		if(end==start-10 && start%9 !=0 && vertices.get(start-1).getClass().getName()=="java.lang.Character" && (char) vertices.get(start-1)==opp && isAristaAvailable(start,start-1,edges) && isAristaAvailable(start-1,start-10,edges) && !isAristaAvailable(start-1,start-2,edges) ){
			return false;
		}
		if(end==start-10 &&start%9 !=0 && vertices.get(start-9).getClass().getName() =="java.lang.Character" && (char) vertices.get(start-9)==opp && isAristaAvailable(start,start-9,edges) && isAristaAvailable(start-9,start-10,edges) && !isAristaAvailable(start-9,start-18,edges) ){
			return false;
		}
		if(end==start-8 && start-9>=0 && vertices.get(start-9).getClass().getName() =="java.lang.Character" && (char) vertices.get(start-9)==opp && isAristaAvailable(start,start-9,edges) && isAristaAvailable(start-9,start-8,edges) && !isAristaAvailable(start-9,start-18,edges) ){
			return false;
		}
		if(end==start-8 && start+2 <81 && vertices.get(start+1).getClass().getName() =="java.lang.Character" && (char) vertices.get(start+1)==opp && isAristaAvailable(start,start+1,edges) && isAristaAvailable(start+1,start-8,edges) && !isAristaAvailable(start+1,start+2,edges) ){
			return false;
		}
		return true;
	}
	
	static public boolean isAristaAvailable(int start, int end, ArrayList<int[]> edges){
		for ( int l=0;l<edges.size();l++){
			int[] edgesPart = edges.get(l);
			if( contains(edgesPart,start) && contains(edgesPart,end) ) { // edgesPart.contains(start) && edgesPart.contains(end)){
				return true;
			}
		}
		return false;
	}

	static public Node leastDistance(ArrayList<Node> nodes, ArrayList<Node> visited){
		int minDistance = 999;
		Node minNode = new Node();
		for (int i=0; i<nodes.size();i++){
			if(  !containsNode(visited,nodes.get(i)) && nodes.get(i).distance < minDistance ){
				minDistance = nodes.get(i).distance;
				minNode = nodes.get(i);
			}
		}
		return minNode;
	}
	
	static public ArrayList<Node> nonVisitedNeighbors(Node currentNode,ArrayList<Node> visited, ArrayList<Node> Nodes, ArrayList<int[]> edges){
		ArrayList<Node> nonVisitedNeighbors = new ArrayList<Node>();
		ArrayList<Integer> neighbors = findAdjacentNodes(currentNode.index,edges);
		for(int l=0; l<neighbors.size(); l++){
			if( !containsNode(visited, Nodes.get(neighbors.get(l))) ){
				nonVisitedNeighbors.add(Nodes.get(neighbors.get(l)));
			}
		}
		return nonVisitedNeighbors;
	}
	
	
	static public int Dijstrak(char player, ArrayList<Object> vertices, ArrayList<int[]> edges){
		ArrayList<Node> Nodes = new ArrayList<Node>();
		int source = vertices.indexOf(player);
		for (int i=0; i<81; i++){
			Node node = new Node();
			node.index = i;
			if(i==source){
				node.distance = 0;
			}
			else{
				if( isAristaAvailable(node.index, source , edges) ){
					node.distance = 1;
				}
				else{
					node.distance=999;
				}
			}
			Nodes.add(node);
		}
		ArrayList<Node> visited = new ArrayList<Node>();
		visited.add(Nodes.get(source));
		while( visited.size() < vertices.size() && !reachedOppositeSide(player,visited) ){
			Node currentNode = leastDistance(Nodes,visited);
			visited.add(currentNode);
			ArrayList<Node> nodesVisit = nonVisitedNeighbors(currentNode,visited,Nodes,edges);
			for (int i=0;i<nodesVisit.size();i++ ){
				nodesVisit.get(i).distance = Math.min(nodesVisit.get(i).distance, currentNode.distance + 1);
			}
		}
		if(player=='b'){
			Integer[] objective = {Nodes.get(0).distance,Nodes.get(1).distance,Nodes.get(2).distance,Nodes.get(3).distance,Nodes.get(4).distance,Nodes.get(5).distance,Nodes.get(6).distance,Nodes.get(7).distance,Nodes.get(8).distance};
			return Collections.min(Arrays.asList(objective));
		//	return Math.min();
		}
		else if(player=='w'){
			Integer[] objective = {Nodes.get(72).distance,Nodes.get(73).distance,Nodes.get(74).distance,Nodes.get(75).distance,Nodes.get(76).distance,Nodes.get(77).distance,Nodes.get(78).distance,Nodes.get(79).distance,Nodes.get(80).distance};
			return Collections.min(Arrays.asList(objective));
			//return Math.min(objective);
		}
		return -1000;
	}
	
}