package networkconnectivity;

import java.util.Scanner;

public class PathCompressionDemo {
	int[] networkID; //存放网络地址的网络号和其相连的结点标识符
	int componentCount; //记录当前网络中连通分量的数量
	
	//构造函数初始化
	PathCompressionDemo(int componentCount) {
		networkID = new int[componentCount];
		for(int i=1; i<componentCount; i++) {
			networkID[i] = -1; //初始化每一个网络都各自是一个分量
		}
		this.componentCount = componentCount; 
	}
		
	//路径压缩查找网络n所在的连通分量标识符(用其根结点标识这个网络)
	public int PathCompressionSearch(int networkAddress) {
		if(networkID[networkAddress] < 0) {
			return networkAddress;
		} else {
			networkID[networkAddress] = PathCompressionSearch(networkID[networkAddress]);
			return networkID[networkAddress];
		}
	}
	
	//判断两个网络分量的连接状态
	public boolean ConnectionState(int networkAddress1, int networkAddress2) {
		int network1_Root, network2_Root;
		network1_Root = PathCompressionSearch(networkAddress1);
		network2_Root = PathCompressionSearch(networkAddress2);
		if(network1_Root == network2_Root) {
			return true;
		} else {
			System.out.println("网络未连通,建立连接.");
			return false;
		}
	}
	
	//把两个未连接的网络分量连接(按照连通分量树的规模来归并)
	public boolean HeightRankMergeConnectToNetwork(int networkAddress1, int networkAddress2) {
		int network1_Root, network2_Root;
		network1_Root = PathCompressionSearch(networkAddress1);
		network2_Root = PathCompressionSearch(networkAddress2);
		//如果两个网络的根结点标识符不同,表明它们不在同一个连通分量,把它们连通
		if(network1_Root != network2_Root) {
			if(networkID[network2_Root] < networkID[network1_Root]) {
				networkID[network1_Root] = network2_Root; //把network2_Root接到network1_Root上
				networkID[network2_Root] += networkID[network1_Root]; //更新树的规模
			} else if (networkID[network2_Root] > networkID[network1_Root]) {
				networkID[network2_Root] = network1_Root;
				networkID[network1_Root] += networkID[network2_Root];
			} 
			componentCount--; //连接后,网络中的连通分量-1
			return true;
		} else {
			return false;
		}
	}
		
	//把两个未连接的网络分量连接(按照连通分量树的高度来归并)
	public boolean SizeRankMergeConnectToNetwork(int networkAddress1, int networkAddress2) {
		int network1_Root, network2_Root;
		network1_Root = PathCompressionSearch(networkAddress1);
		network2_Root = PathCompressionSearch(networkAddress2);
		//如果两个网络的根结点标识符不同,表明它们不在同一个连通分量,把它们连通
		if(network1_Root != network2_Root) {
			if(networkID[network2_Root] < networkID[network1_Root]) {
				networkID[network1_Root] = network2_Root; //把network1_Root接到network2_Root上
			} else if (networkID[network2_Root] > networkID[network1_Root]) {
				networkID[network2_Root] = network1_Root;
			} else {
				//两棵树等高
				networkID[network2_Root] = network1_Root;
				networkID[network1_Root]--; //树高增高一层
			}
			componentCount--; //连接后,网络中的连通分量-1
			return true;
		} else {
			return false;
		}
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int s1, s2;
		String networkAddress1, networkAddress2;
		PathCompressionDemo network = new PathCompressionDemo(223); 
		
		while(true) {
			networkAddress1 = in.nextLine();
			if(networkAddress1.equals("end")!=true) {
				s1 = Integer.parseInt(networkAddress1.substring(0, networkAddress1.indexOf(".")));
			} else {
				break;
			}
			networkAddress2 = in.nextLine();
			s2 = Integer.parseInt(networkAddress2.substring(0, networkAddress2.indexOf(".")));
			
			//判断两个网络分量是否已连通
			if(network.ConnectionState(s1, s2)) {
				System.out.println("网络已处于连通状态.");
				continue;
			} else {
				//未连通就把两个分量连通
				if(network.SizeRankMergeConnectToNetwork(s1, s2)) {
					System.out.println("连接成功.");
				} else {
					System.out.println("连接失败.");
				}
			}
		} 
		in.close();
		return;
	}

}
