package directeddfs;

import java.util.Scanner;

public class GraphDemo {
	private int V; //图中的顶点数
	private int E; //边数
	private Bag<Integer>[] Graph; 
	
	//构造函数初始化
	public GraphDemo(int V) {
		this.V = V;
		this.E = 0; 
		Graph = (Bag<Integer>[]) new Bag[V]; //邻接表存储结构有向图
		
		for(int i = 0; i<V; i++) {
			Graph[i] = new Bag<Integer>(); //背包中每一个链表结点都是一个链表
		}
	}
	
	//初始化有向图
	public void InitialGrph() {
		Scanner in = new Scanner(System.in);
		int v1, v2;
		
		while(true) {
			v1 = in.nextInt();
			v2 = in.nextInt();
			if(v1!=-1 && v2!=-1) {
				addEdge(v1, v2);
			} else {
				break;
			}
		}
		in.close();
	}
	
	//增加一条有向边
	public void addEdge(int v1, int v2) {
		Graph[v1].add(v2);
		E++; //边数++
	}
	
	//输出有向图
	public void printGraph() {
		for(int i=0; i<V; i++) {
			System.out.print("Verter-"+i+": ");
			for(int w : Graph[i]) {
				System.out.print(w+" ");
			}
			System.out.println();
		}
	}
	
	//返回图中的顶点数
	public int returnV() {
		return V;
	}
	
	//返回有向图
	public Iterable<Integer> returnGraph(int v) {
        return Graph[v];
    }

	public static void main(String[] args) throws InterruptedException {
		GraphDemo Graph = new GraphDemo(13);
		Graph.InitialGrph(); //创建有向图
		Graph.printGraph(); //输出有向图
System.out.println(Graph.E);
		Bag<Integer> graph = new Bag<Integer>();
		graph.add(4);
		graph.add(6);
		graph.add(9);
		graph.add(12);

		dfsSearchDemo searchArrival = new dfsSearchDemo(Graph, graph); //在图中查找这些顶点的可达顶点
		
		System.out.println("集合中顶点,在图中可达的所有顶点有:");
		for(int v=0; v<Graph.V; v++) {
			if(searchArrival.MarkTest(v)) {
				System.out.print(v+" ");
			}
		}
		
		System.out.println("\r\n回收空闲块:");
		for(int v=0; v<Graph.V; v++) {
			if(!searchArrival.MarkTest(v)) {
				System.out.print(v+" ");
			}
		}
	}

}
