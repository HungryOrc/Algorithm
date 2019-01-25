/* Clone (Deep Copy) an undirected graph. Each node in the graph contains a label and a list of its neighbors.

First node is labeled as 0. Connect node 0 to both nodes 1 and 2.
Second node is labeled as 1. Connect node 1 to node 2.
Third node is labeled as 2. Connect node 2 to node 2 (itself), thus forming a self-cycle. 注意！这图里可能有loop！！
Visually, the graph looks like the following:
   1
  / \
 /   \
0 --- 2
     / \
     \_/
     
* Definition for undirected graph.
* class UndirectedGraphNode {
*     int label;
*     ArrayList<UndirectedGraphNode> neighbors;
*     UndirectedGraphNode(int x) { label = x; neighbors = new ArrayList<UndirectedGraphNode>(); }
* }; */


// 方法0：最简明有力的方法
// Since there might be cycles in the original graph, we cannot just loop along the graph, namely the list
// of nodes to copy the whole graph


public class Solution {
  
  public List<GraphNode> copy(List<GraphNode> graph) {
    List<GraphNode> graphCopy = new ArrayList<>();
    HashMap<GraphNode, GraphNode> map = new HashMap<>();
    
    for (int i = 0; i < graph.size(); i++)
      copy(graph.get(i), graphCopy, map);
    return graphCopy;
  }
  
  private GraphNode copy(GraphNode node, List<GraphNode> graphCopy,
    HashMap<GraphNode, GraphNode> map) {
    
    GraphNode nodeCopy = map.get(node);
    if (nodeCopy != null) {
      return nodeCopy;
    }
    
    nodeCopy = new GraphNode(node.key);
    map.put(node, nodeCopy);
    graphCopy.add(nodeCopy);
    
    for (GraphNode nei : node.neighbors) {
      nodeCopy.neighbors.add(copy(nei, graphCopy, map));
    }
    
    return nodeCopy;
  }
}


// 方法1: 三步
public class Solution {
    /* @param node: A undirected graph node
     * @return: A undirected graph node */
    
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        if (node == null) {
            return null;
        }
        
        // from the given node, get all the old nodes in the initial graph
        ArrayList<UndirectedGraphNode> oldNodes = getAllNodes(node);
        
        // map each old node with a new node, whose label is the same to the old one
        // 在这些一对一的mapping里，old nodes是key，new nodes是value
        HashMap<UndirectedGraphNode,UndirectedGraphNode> mappingFromOldNodesToNewNodes = new HashMap<>();
        for (UndirectedGraphNode oldNode : oldNodes) {
            // 到这里为止，old nodes都是带有neighbors关系的，而new nodes都还没有
            mappingFromOldNodesToNewNodes.put(oldNode, new UndirectedGraphNode(oldNode.label));
        }
        
        // 把neighbors关系也复制到new nodes里面去
        // 注意！neighbors关系原本是存在于old nodes之间的，复制之后，
        // 新的neighbors关系应该是存在于new nodes之间的
        for (UndirectedGraphNode oldNode : oldNodes) {
            UndirectedGraphNode correspondingNewNode = mappingFromOldNodesToNewNodes.get(oldNode);
            ArrayList<UndirectedGraphNode> oldNeighbors = oldNode.neighbors;
            
            for (UndirectedGraphNode oldNeighbor : oldNeighbors) {
                UndirectedGraphNode correspondingNewNeighbor = mappingFromOldNodesToNewNodes.get(oldNeighbor);
                correspondingNewNode.neighbors.add(correspondingNewNeighbor);
            }
        }
        return mappingFromOldNodesToNewNodes.get(node);
    }
    private ArrayList<UndirectedGraphNode> getAllNodes(UndirectedGraphNode node) {
        HashSet<UndirectedGraphNode> visitedNodes = new HashSet<>();
        Queue<UndirectedGraphNode> manageNodes = new LinkedList<>();
        visitedNodes.add(node);
        manageNodes.offer(node);
        
        while (!manageNodes.isEmpty()) {
            UndirectedGraphNode curNode = manageNodes.poll();
            for (UndirectedGraphNode neighbor : curNode.neighbors) {
                if (!visitedNodes.contains(neighbor)) {
                    visitedNodes.add(neighbor);
                    manageNodes.offer(neighbor);
                }
            }
        }
        
        // 注意 ArrayList 可以直接由 HashSet 来构造！
        return new ArrayList<UndirectedGraphNode>(visitedNodes);
    }
}
