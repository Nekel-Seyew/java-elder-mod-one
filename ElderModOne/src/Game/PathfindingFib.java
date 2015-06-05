/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Game;

import Misc.FibonacciHeap;
import Misc.FibonacciHeap.Entry;
import Utilities.Vector2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 *
 * @author KyleSweeney
 */
public class PathfindingFib {
    public static Vector2[] getPath(Vector2 start, Vector2 end, Level level){
        
        if(level.isWall((int)end.getX(),(int)end.getY())){
            return null;
        }
        
        ArrayList<Vector2> path;
        Entry<Node>[][] neighbors = new Entry[level.walls[0].length][level.walls.length];
        FibonacciHeap<Node> Q = new FibonacciHeap<Node>();
        
//        ArrayList<Node> Q = new ArrayList<Node>();
        
        Node st = new Node((int)start.getX(),(int)start.getY(),0);
        st.setParent(null);
//        Q.add(st);
        Entry<Node> stn =Q.enqueue(st, 0.0);
        neighbors[(int)start.getX()][(int)start.getY()] = stn;
        for(int x =0; x<level.walls[0].length; x++){
            for(int y=0; y<level.walls.length; y++){
                if(!(y ==(int)start.getX() && x == (int)start.getY()) && !level.isWall(x, y)){
                    Node n = new Node(x,y,Integer.MAX_VALUE);
                    n.setParent(null);
//                    Q.add(n);
                    Entry<Node> en=Q.enqueue(n, Integer.MAX_VALUE);
                    neighbors[x][y] = en;
                }
            }
        }
        
        while(!Q.isEmpty()){
//            Node u = Q.remove(0);
            Entry<Node> u = Q.dequeueMin();
            
//            if(u.x == (int)end.getX() && u.y == (int)end.getY()){
//                path = u.parentPath();
//                break;
//            }
            
            for(Entry<Node> v : u.getValue().getNeighbors(neighbors, level)){
                int alt = u.getValue().distance + 1;//1 since distance to each node is 1, even in diagonall cases. This'll be interesting;
                if(alt < v.getValue().distance){
                    v.getValue().distance = alt;
                    v.getValue().setParent(u.getValue());
                    Q.decreaseKey(v, alt);
//                    Collections.sort(Q);
                }
            } 
        }
        
        Node endNode = neighbors[(int)end.getX()][(int)end.getY()].getValue();
        path = endNode.parentPath();
        
        //done finding, make array
        //could possibly construct this out of a stack....
        Vector2[] ret = new Vector2[path.size()];
        return path.toArray(ret);
    }
    
    private static class Node implements Comparable{
        int x,y;
        int distance;
        Node parent;
        
        public Node(int x,int y, int distance){
            this.x=x;
            this.y=y;
            this.distance = distance;
        }
        
        public int getDistance(){
            return distance;
        }
        public void setParent(Node n){
            this.parent=n;
        }
        
        public ArrayList<Entry<Node>> getNeighbors(Entry<Node>[][] neighbors, Level level){
            ArrayList<Entry<Node>> neigh = new ArrayList<Entry<Node>>();
            if(x == 0){
                neigh.add(neighbors[x+1][y]);
            }else if(y == neighbors.length-1){
                neigh.add(neighbors[x-1][y]);
            }else{
                neigh.add(neighbors[x+1][y]);
                neigh.add(neighbors[x-1][y]);
            }
            
            if(y==0){
                neigh.add(neighbors[x][y+1]);
            }else if(y == neighbors[0].length-1){
                neigh.add(neighbors[x][y-1]);
            }else{
                neigh.add(neighbors[x][y+1]);
                neigh.add(neighbors[x][y-1]);
            }
            
//            if(x == 0 && y==0){
//                neigh.add(neighbors[y+1][x+1]);
//            }else if(x == neighbors[0].length-1 && y == neighbors.length-1){
//                neigh.add(neighbors[y-1][x-1]);
//            }else if(x == 0 && y == neighbors.length-1){
//                neigh.add(neighbors[y-1][x+1]);
//            }else if(x == neighbors[0].length-1 && y==0){
//                neigh.add(neighbors[y+1][x-1]);
//            }else if(x == 0){
//                neigh.add(neighbors[y-1][x+1]);
//                neigh.add(neighbors[y+1][x+1]);
//            }else if(x == neighbors[0].length-1){
//                neigh.add(neighbors[y-1][x-1]);
//                neigh.add(neighbors[y+1][x-1]);
//            }else if(y==0){
//                neigh.add(neighbors[y+1][x-1]);
//                neigh.add(neighbors[y+1][x+1]);
//            }else if(y == neighbors.length-1){
//                neigh.add(neighbors[y-1][x-1]);
//                neigh.add(neighbors[y-1][x+1]);
//            }else{
//                neigh.add(neighbors[y-1][x-1]);
//                neigh.add(neighbors[y-1][x+1]);
//                neigh.add(neighbors[y+1][x-1]);
//                neigh.add(neighbors[y+1][x+1]);
//            }
            ArrayList<Entry<Node>> ret = new ArrayList<Entry<Node>>();
            try{
            for(int i=0; i<neigh.size(); i++){
                Entry<Node> n = neigh.get(i);
                if(n!=null){
                    ret.add(n);
                }
            }
            }catch(NullPointerException e){
                e.printStackTrace();
            }
            
            return ret;
        }

        @Override
        public int compareTo(Object o) {
            Node n = (Node)o;
            if(this.distance < n.distance){
                return -1;
            }else if(this.distance == n.distance){
                return 0;
            }else{
                return 1;
            }
        }
        
        //slow, inefficient, need better solution
        public ArrayList<Vector2> parentPath(){
            ArrayList<Vector2> ret = new ArrayList<Vector2>();
            Stack<Vector2> working = new Stack<Vector2>();
            
            Node next = this.parent;
            while(next!=null){
                working.add(new Vector2(next.x,next.y));//in reality, y stores the column number, and x stores the row number....
                next = next.parent;
            }
            while(!working.empty()){
                ret.add(working.pop());
            }
            return ret;
        }
        
    }
}
