import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.*;
import java.util.concurrent.*;
public class javaTest {
    public static void main(String[] args) {
//        edge firstEdge=new edge(0,1,12);
//        edge secondEdge=new edge(1,2,10);
//        edge thirdEdge=new edge(2,3,3);
//        edge fourthEdge=new edge(3,4,4);
//        edge fifthEdge=new edge(4,5,2);
//        edge sixthEdge=new edge(5,6,9);
//        edge seventhEdge=new edge(0,6,14);
//        edge eigthEdge=new edge(0,5,16);
//        edge ninthEdge=new edge(1,5,7);
//        edge tenthEdge=new edge(2,5,6);
//        edge eleventhEdge=new edge(2,4,5);
//        edge twelfthEdge=new edge (4,6,8);
        graph g=new graph("D://Java projects//javaTest//graph.txt");

        System.out.println(g.findShortestPath("A","B"));

    }
}
class edge{
    int start;
    int end;
    int weight;
    public edge(int start,int end,int weight){
        this.start=start;
        this.end=end;
        this.weight=weight;
    }
}

class Vertex implements Comparable<Vertex>{
    String name;
    int distance;
    List<adjacentVertex> adj=new ArrayList<adjacentVertex>();
    public Vertex(String name){
        this.name=name;
    }
    public int compareTo(Vertex x){
        if(this.distance<=x.distance)
            return -1;
        return 1;
    }
}
class adjacentVertex{
    String name;
    int weight;
    public adjacentVertex(String name,int weight){
        this.name=name;
        this.weight=weight;
    }
}
class graph{
    List<Vertex> vertices=new ArrayList<Vertex>();
    Map<String,Vertex> recorder=new HashMap<String,Vertex>();
    public graph(String filePath){
        generateGraph(filePath);
    }
    public void generateGraph(String filePath){
        File f=new File(filePath);
        try {
            BufferedReader br=new BufferedReader(new FileReader(f));
            String line;
            while((line=br.readLine())!=null){
                Scanner scanner=new Scanner(line);
                String startVertex=scanner.next();
                String endVertex=scanner.next();
                int weight=scanner.nextInt();
                Vertex v=recorder.get(startVertex);
                if(v==null){
                    v=new Vertex(startVertex);
                    v.adj.add(new adjacentVertex(endVertex,weight));
                    recorder.put(startVertex,v);
                    vertices.add(v);
                }else{
                    v.adj.add(new adjacentVertex(endVertex,weight));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public int findShortestPath(String startPoint,String endPoint){
        Queue<Vertex> queue=new LinkedList<Vertex>();
        List<Vertex> done=new ArrayList<Vertex>();
        PriorityQueue<Vertex> ongoing=new PriorityQueue<Vertex>();
        for(Vertex v:vertices){
            if(v.name.equals(startPoint)){
                done.add(v);
                queue.offer(v);
            }else{
                ongoing.offer(v);
                v.distance=Integer.MAX_VALUE;
            }
        }
        while(!queue.isEmpty()){
            Vertex tmp=queue.poll();
            for(adjacentVertex adj:tmp.adj){
                Vertex adjacent=recorder.get(adj.name);
                if(tmp.distance+adjacent.distance<adjacent.distance)
                    adjacent.distance=tmp.distance+adjacent.distance;
            }
            Vertex nextStart=ongoing.poll();
            System.out.println(nextStart.name);
            queue.offer(nextStart);
            done.add(nextStart);
        }
        for(Vertex tmp:done){
            if(tmp.name.equals(endPoint))
                return tmp.distance;
        }
        return -1;
    }
}

