/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Game;

import Utilities.Vector2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 *
 * @author KyleSweeney
 */
public class Pathfinding {
    public static Vector2[] getPath(Vector2 start, Vector2 end, Level level){
        
        if(level.isWall((int)end.getX(),(int)end.getY())){
            return null;
        }
        
        ArrayList<Vector2> path;
        Node[][] neighbors = new Node[level.walls[0].length][level.walls.length];
        
        ArrayList<Node> Q = new ArrayList<Node>();
        
        Node st = new Node((int)start.getX(),(int)start.getY(),0);
        st.setParent(null);
        Q.add(st);
        //Q.enqueue(st, 0.0);
        neighbors[(int)start.getX()][(int)start.getY()] = st;
        for(int x =0; x<level.walls.length; x++){
            for(int y=0; y<level.walls[x].length; y++){
                if(!(y ==(int)start.getX() && x == (int)start.getY()) && !level.isWall(x, y)){
                    Node n = new Node(x,y,Integer.MAX_VALUE);
                    n.setParent(null);
                    Q.add(n);
                    neighbors[x][y] = n;
                }
            }
        }
        
        while(!Q.isEmpty()){
            Node u = Q.remove(0);
            
            for(Node v : u.getNeighbors(neighbors, level)){
                int alt = u.distance + 1;//1 since distance to each node is 1, even in diagonall cases. This'll be interesting;
                if(alt < v.distance){
                    v.distance = alt;
                    v.setParent(u);
                    Collections.sort(Q);
                }
            } 
        }
        
        Node endNode = neighbors[(int)end.getX()][(int)end.getY()];
        path = endNode.parentPath();
        
        //done finding, make array
        //could possibly construct this out of a stack....
        Vector2[] ret = new Vector2[path.size()];
        return path.toArray(ret);
    }
    
    public static ArrayList<Vector2[]> getPathMulti(Vector2 start, ArrayList<Vector2> end, Level level){
        
        ArrayList<Vector2[]> path = new ArrayList<Vector2[]>();
        Node[][] neighbors = new Node[level.walls[0].length][level.walls.length];
        
        ArrayList<Node> Q = new ArrayList<Node>();
        
        Node st = new Node((int)start.getX(),(int)start.getY(),0);
        st.setParent(null);
        Q.add(st);
        //Q.enqueue(st, 0.0);
        neighbors[(int)start.getX()][(int)start.getY()] = st;
        for(int x =0; x<level.walls.length; x++){
            for(int y=0; y<level.walls[x].length; y++){
                if(!(y ==(int)start.getX() && x == (int)start.getY()) && !level.isWall(x, y)){
                    Node n = new Node(x,y,Integer.MAX_VALUE);
                    n.setParent(null);
                    Q.add(n);
                    neighbors[x][y] = n;
                }
            }
        }
        
        while(!Q.isEmpty()){
            Node u = Q.remove(0);
            
            for(Node v : u.getNeighbors(neighbors, level)){
                int alt = u.distance + 1;//1 since distance to each node is 1, even in diagonall cases. This'll be interesting;
                if(alt < v.distance){
                    v.distance = alt;
                    v.setParent(u);
                    Collections.sort(Q);
                }
            } 
        }
       
        
        //done finding, make array
        //could possibly construct this out of a stack....
        
        for(Vector2 e : end){
            if(!level.isWall((int)e.getX(), (int)e.getY())){
                path.add(null);
            }
            ArrayList<Vector2> ePath = neighbors[(int)e.getX()][(int)e.getY()].parentPath();
            Vector2[] retArray = new Vector2[ePath.size()];
            retArray = ePath.toArray(retArray);
            path.add(retArray);
        }
        
        return path;
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
        
        public ArrayList<Node> getNeighbors(Node[][] neighbors, Level level){
            ArrayList<Node> neigh = new ArrayList<Node>();
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
            ArrayList<Node> ret = new ArrayList<Node>();
            for(int i=0; i<neigh.size(); i++){
                Node n = neigh.get(i);
                if(n!=null){
                    ret.add(n);
                }
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
