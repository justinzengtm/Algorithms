#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h> 

#define ElementType int

typedef struct networkConnectivity *networkList;
struct networkConnectivity {
	ElementType networkID[223]; //存放网络地址的网络号和其相连的结点标识符
	int count; //记录网络中连通分量的数量 
};

//函数声明
//初始化网络
networkList InitNetwork();
//路径压缩查找网络n所在的连通分量标识符
int PathCompressionSearch(networkList network, int networkAddress); 
//判断两个网络分量的连接状态
bool ConnectionState(networkList network, int networkAddress1, int networkAddress2); 
//连接两个连通分量(按照连通分量树的规模来归并)
bool HeightRankMergeConnectToNetwork(networkList network, int networkAddress1, int networkAddress2); 
//接两个连通分量(按照连通分量树的高度来归并)
bool SizeRankMergeConnectToNetwork(networkList network, int networkAddress1, int networkAddress2); 

int main(int argc, char const* argv[]) 
{
	int s1, s2;
	networkList network = InitNetwork(); //创建网络 

	while(true) {
		scanf("%d %d", &s1, &s2);
		if(s1 != -1 || s2 != -1) {
			//判断两个网络分量是否已连通
			if(ConnectionState(network, s1, s2)) {
				printf("网络已处于连通状态.\n");
				continue;
			} else {
				//未连通就把两个分量连通
				if(SizeRankMergeConnectToNetwork(network, s1, s2)) {
					printf("连接成功.\n");
				} else {
					printf("连接失败.\n");
				}
			}
		} else {
			break;
		}
	}
	
	return 0; 
}

//初始化网络
networkList InitNetwork() 
{
	int i;
	networkList network = (networkList)malloc(sizeof(struct networkConnectivity));
	
	for(i=0; i<223; i++) {
		network->networkID[i] = -1; //初始化每一个网络都各自是一个分量
	}
	network->count = 223;
	
	return network;
}

//路径压缩查找网络n所在的连通分量标识符
int PathCompressionSearch(networkList network, int networkAddress) 
{
	if(network->networkID[networkAddress] < 0) {
		return networkAddress;
	} else {
		return network->networkID[networkAddress] = PathCompressionSearch(network, network->networkID[networkAddress]);
	}
}

//判断两个网络分量的连接状态
bool ConnectionState(networkList network, int networkAddress1, int networkAddress2) 
{
	int network1_Root, network2_Root;
	
	network1_Root = PathCompressionSearch(network, networkAddress1);
	network2_Root = PathCompressionSearch(network, networkAddress2);
	if(network1_Root == network2_Root) {
		return true;
	} else {
		printf("网络未连通,建立连接.");
		return false;
	}
}

//连接两个连通分量(按照连通分量树的规模来归并)
bool HeightRankMergeConnectToNetwork(networkList network, int networkAddress1, int networkAddress2) 
{
	int network1_Root, network2_Root;
	
	network1_Root = PathCompressionSearch(network, networkAddress1);
	network2_Root = PathCompressionSearch(network, networkAddress2);
	//如果两个网络的根结点标识符不同,表明它们不在同一个连通分量,把它们连通
	if(network1_Root != network2_Root) {
		if(network->networkID[network2_Root] < network->networkID[network1_Root]) {
			network->networkID[network1_Root] = network2_Root; //把network2_Root接到network1_Root上
			network->networkID[network2_Root] += network->networkID[network1_Root]; //更新树的规模
		} else if (network->networkID[network2_Root] > network->networkID[network1_Root]) {
			network->networkID[network2_Root] = network1_Root;
			network->networkID[network1_Root] += network->networkID[network2_Root];
		} 
		network->count--; //连接后,网络中的连通分量-1
		return true;
	} else {
		return false;
	}
}

//接两个连通分量(按照连通分量树的高度来归并)
bool SizeRankMergeConnectToNetwork(networkList network, int networkAddress1, int networkAddress2) 
{
	int network1_Root, network2_Root;
	
	network1_Root = PathCompressionSearch(network, networkAddress1);
	network2_Root = PathCompressionSearch(network, networkAddress2);
	//如果两个网络的根结点标识符不同,表明它们不在同一个连通分量,把它们连通
	if(network1_Root != network2_Root) {
		if(network->networkID[network2_Root] < network->networkID[network1_Root]) {
			network->networkID[network1_Root] = network2_Root; //把network2_Root接到network1_Root上
		} else if (network->networkID[network2_Root] > network->networkID[network1_Root]) {
			network->networkID[network2_Root] = network1_Root;
		} else {
			//两棵树等高
			network->networkID[network2_Root] = network1_Root;
			network->networkID[network1_Root]--; //树高增高一层
		}
		network->count--; //连接后,网络中的连通分量-1
		return true;
	} else {
		return false;
	}
}
