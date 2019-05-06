package directeddfs;

public class dfsSearchDemo {
	private boolean[] mark; //标识结点是否已经被访问过
	
	//在图中查找顶点tag可到达的所有顶点
	public dfsSearchDemo(GraphDemo Graph, int tag) {
		mark = new boolean[Graph.returnV()];
		DFSSearch(Graph, tag); //在图中查找tag顶点可达的所有顶点
	}
	
	//在图中查找集合graph中的多个顶点可到达的所有顶点
	public dfsSearchDemo(GraphDemo Graph, Iterable<Integer> graph) {
		mark = new boolean[Graph.returnV()];
		for(int v : graph) {
			if(mark[v] != true) {
				DFSSearch(Graph, v);
			}
		}
	}
	
	//深度优先搜索
		public void DFSSearch(GraphDemo Graph, int tag) {
			mark[tag] = true; //从顶点tag开始
			for(int v : Graph.returnGraph(tag)) {
				if(mark[v] != true) {
					DFSSearch(Graph, v);
				}
			}
		}
	public boolean MarkTest(int v) {
		return mark[v];
	}
}
