package Taxi1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Matrix {
	static int size = 80;
	//地图矩阵
	int[][] map = new int[size + 1][size + 1];
	// 邻接矩阵
	int[][] adjacency = new int[size * size][size * size];

	public int CreateMatrix() {
		try {

			File file = new File("d://map.txt");
			// 文件不存在
			if (!file.exists()) {
				System.out.println("No such file!");
				return -1;
			}
			// BufferedReader是可以按行读取文件
			FileInputStream inputStream = new FileInputStream(file);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

			String str = null;

			for (int i = 0; i < size ; i++) {

				str = bufferedReader.readLine();
				if (str == null) {
					System.out.println("invalid1:" + str);
					return -1;
				}

				String[] s = str.split(" ");
				if (s.length != size) {
					System.out.println("invalid2:" + str);
					System.out.println("s.length:" + s);
					return -1;
				}

				for (int j = 0; j < size; j++) {
					String str1 = "\\+?0*[0123]";
					Pattern pattern = Pattern.compile(str1);
					Matcher matcher = pattern.matcher(s[j]);
					if (!matcher.matches()) {
						System.out.println("invalid3:" + str);
						return -1;
					}
					map[i][j] = Integer.parseInt(s[j]);
				}
			}

			str = bufferedReader.readLine();
			if (str != null) {
				System.out.println("invalid:行数大于" + size);
				return -1;
			}
			
			
			
			
			

			return 0;

		} catch (Exception e) {
			System.out.println("invalid4");
		}
		return 0;

	}

	public void CreateAdjacency() {
		for (int i = 0; i < size * size; i++) {
			for (int j = 0; j < size * size; j++) {
				adjacency[i][j] = 99999;
			}
		}

		// 初始化邻接矩阵
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (map[i][j] == 1) {
					int point1 =i* size + j;
					int point2 =i* size + j+1;
					adjacency[point1][point2] = 1;
					adjacency[point2][point1] = 1;

				} else if (map[i][j] == 2) {
					int point1 =i* size + j;
					int point2 =(i+1)*size+j;
					adjacency[point1][point2] = 1;
					adjacency[point2][point1] = 1;
				} else if (map[i][j] == 3) {
					int point1 = i* size + j;
					int point2 =i* size + j+1;
					int point3 =(i+1)*size+j;
					adjacency[point1][point2] = 1;
					adjacency[point2][point1] = 1;
					adjacency[point1][point3] = 1;
					adjacency[point3][point1] = 1;
				} else {
					continue;
				}
			}
		}
	}

	public LinkedList <MyPoint> Dijkstra(int C[][], MyPoint p1,MyPoint p2) {
		int v=(p1.x)*80+p1.y+1;
		int end=(p2.x)*80+p2.y+1;
		LinkedList<MyPoint> list=new LinkedList<MyPoint>();
		// 求原点v到其余顶点的最短路径及其长度
		int[] D = new int[size*size];
		// 用来存储从起点到某一节点的最短距离
		int[] P = new int[size*size];
		int[] S = new int[size*size];
		// p[n]表示某一节点的父亲，s[n]相当于标志数组
		int i, j, k, v1, pre;
		k = 0;
		int min, max = 99999, inf = 9999999;
		v1 = v - 1;// 节点号和存储的数目差1

		for (i = 0; i < size*size; i++) {
			D[i] = C[v1][i]; // 置初始距离值
			if (D[i] != max) // 说明存在边
				P[i] = v;// 把父亲置为v
			else
				P[i] = 0;// 否则父亲置为0
		}

		for (i = 0; i < size*size; i++) {
			S[i] = 0;
			// 如果某点i被访问则把该点值置为1，否则为0
		}

		S[v1] = 1;// 已经被访问置为1
		D[v1] = 0; // 源点v送S

		// 扩充红点集
		for (i = 0; i < size*size - 1; i++) {

			min = inf;// 令inf>max，保证距离值为max的蓝点能扩充到S中
			for (j = 0; j < size*size; j++)// 在当前蓝点中选距离值最小的点k+1
			{
				if ((S[j] == 0) && (D[j] < min)) {
					min = D[j];// 找从起点开始的最小权值
					k = j;
				}
			}

			S[k] = 1; // 已经被访问，置为1

			for (j = 0; j < size*size; j++) {
				if ((S[j] == 0) && (D[j] > D[k] + C[k][j]))// 调整各蓝点的距离值
				{
					D[j] = D[k] + C[k][j]; // 修改蓝点j+1的距离
					P[j] = k + 1; // k+1是j+1的前趋
				}
			}

		} // 所有顶点均已扩充到S中

		/*for (i = 0; i < size; i++)// 输出结果和最短路径
		{
			System.out.println(v + "到" + (i + 1) + "的最短距离为");
			System.out.print(D[i] + "\r\n"); // 打印结果
			pre = P[i];
			System.out.print("路径：" + (i + 1));
			while (pre != 0) // 继续找前趋顶点
			{
				System.out.print("<——" + pre);
				pre = P[pre - 1];
			}
			System.out.println("\n");
		}*/
		//System.out.println(v + "到" + end + "的最短距离为");
		//System.out.print(D[end-1] + "\r\n"); // 打印结果
		pre = P[end-1];
		//System.out.print("路径：" + (end-1 + 1));
		MyPoint point=NumtoPoint(end);
		list.addFirst(point);
		
		while (pre != 0) // 继续找前趋顶点
		{	
			//System.out.print("<——" + pre);
			MyPoint point1=NumtoPoint(pre);
			list.addFirst(point1);
			pre = P[pre - 1];
		}
		//System.out.println("\n");
		return list;
		

	}
	
	
	public MyPoint NumtoPoint(int num){
		if(num%80==0){
			MyPoint point=new MyPoint((num-80)/80,79);
			return point;
		}
		else{
			MyPoint point=new MyPoint((num-(num%80))/80,num%80-1);
			return point;
		}
	}
	
	
	/*public static void main(String[] args) {
		Matrix matrix = new Matrix();
		if (matrix.CreateMatrix() == -1) {
			return;
		} else {
			for (int i = 0; i < matrix.size + 1; i++) {
				for (int j = 0; j < matrix.size + 1; j++) {
					System.out.print(matrix.map[i][j] + " ");
				}
				System.out.println("\r\n");
			}
		}
		matrix.CreateAdjacency();
		Point p1=new Point(1,1);
		Point p2=new Point(1,3);
		LinkedList<Point> list=matrix.Dijkstra(matrix.adjacency,p1,p2);
		System.out.println(list.size()-1);
		for(int i=0;i<list.size();i++){
			System.out.print("("+list.get(i).x+","+list.get(i).y+") ");
		}
	}*/
}
