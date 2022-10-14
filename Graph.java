
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Deque;
import java.util.Collection;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;


public class Graph<V> { 
   
    // Keep an index from node labels to nodes in the map
    protected Map<V, Vertex<V>> vertices; 

    /**
     * Construct an empty Graph.
     */
    public Graph() {
       vertices = new HashMap<V, Vertex<V>>();
    }

    /**
     * Retrieve a collection of vertices. 
     */  
    public Collection<Vertex<V>> getVertices() {
        return vertices.values();
    }

    public void addVertex(V u) {
        addVertex(new Vertex<>(u));
    }
    
    public void addVertex(Vertex<V> v) {
        if (vertices.containsKey(v.name)) 
            throw new IllegalArgumentException("Cannot create new vertex with existing name.");
        vertices.put(v.name, v);
    }

    /**
     * Add a new edge from u to v.
     * Create new nodes if these nodes don't exist yet. 
     * @param u unique name of the first vertex.
     * @param w unique name of the second vertex.
     * @param cost cost of this edge. 
     */
    public void addEdge(V u, V w, Double cost) {
        if (!vertices.containsKey(u))
            addVertex(u);
        if (!vertices.containsKey(w))
            addVertex(w);

        vertices.get(u).addEdge(
            new Edge<>(vertices.get(u), vertices.get(w), cost)); 

    }

    public void addEdge(V u, V w) {
        addEdge(u,w,1.0);
    }

    public void printAdjacencyList() {
        for (V u : vertices.keySet()) {
            StringBuilder sb = new StringBuilder();
            sb.append(u.toString());
            sb.append(" -> [ ");
            for (Edge e : vertices.get(u).getEdges()){
                sb.append(e.target.name);
                sb.append(" ");
            }
            sb.append("]");
            System.out.println(sb.toString());
        }
    }    
  
   /**
    * Add a bidirectional edge between u and v. Create new nodes if these nodes don't exist
    * yet. This method permits adding multiple edges between the same nodes.
    *
    * @param u  
    *          the name of the source vertex.
    * @param v 
    *          the name of the target vertex.
    * @param cost
    *          the cost of this edge
    */
    public void addUndirectedEdge(V u, V v, Double cost) {
        addEdge(u,v, cost);
        addEdge(v,u, cost);
    }

    /****************************
     * Your code follows here.  *
     ****************************/ 
    
    //Computes cost or distances through cities
    public void computeAllEuclideanDistances() {
        //Calculate distance from cities
        for (V name : vertices.keySet()){
            Vertex<V> city = vertices.get(name);
            List<Edge<V>> edges = city.getEdges();
            for (Edge<V> edge : edges){
                //Calculates distance from positions.
                double xdist = Math.pow((edge.source.posX - edge.target.posX), 2);
                double ydist = Math.pow((edge.source.posY - edge.target.posY), 2);
                double dist = sqrt(xdist+ydist);
                edge.distance = dist;
            }
        }
    }

    public void doDijkstra(V s) {
      //Instantiates all verteces
      for (Vertex<V> vertex : getVertices()){
          vertex.cost = Integer.MAX_VALUE;
          vertex.visited = false;
          vertex.backpointer = null;
      }
      Vertex<V> startPoint = vertices.get(s);
      BinaryHeap<Vertex<V>> vertexList = new BinaryHeap<Vertex<V>>(getVertices().size());
      vertexList.insert(startPoint);
      startPoint.cost = 0;

      //If list is empty dijkstra's algorithm stops
      while (vertexList.isEmpty() == false){

          Vertex<V> current = vertexList.deleteMin();
          current.visited = true;

          for (Edge<V> edge : current.getEdges()){
              Vertex<V> next = edge.target;
              if (next.visited == false){
                  double cost = current.cost + edge.distance;
                  if (cost < next.cost){
                      next.cost = cost;
                      next.backpointer = current;
                      vertexList.insert(next);
                  }
              }
          }
      }
    }

    // Part 3
    public List<Edge<V>> getDijkstraPath(V s, V t) {
      doDijkstra(s);
      LinkedList<Edge<V>> path = new LinkedList<>();
      //Prints edges going from last city through backpointers.
      for (Vertex<V> v = vertices.get(t); v.backpointer != null; v = v.backpointer){
          List<Edge<V>> edges = v.getEdges();
          for (Edge<V> edge : edges){
              if (edge.target.name == v.backpointer.name){
                  path.add(edge);
                  break;
              }
          }
      }
      //Returns path
      return path;
    }  
    
}
