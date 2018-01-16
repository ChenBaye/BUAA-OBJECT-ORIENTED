package Taxi3;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Light extends Thread {
	//表示对象:static int size;int[][] light;int time;TaxiGUI gui;
	//
	//抽象函数：AF(c)=(gui)where gui==c.gui
	//
	//不变式:gui!=null
	static int size = 80;
	int[][] light = new int[size * size][size * size];
	int time;
	TaxiGUI gui;

	
	public boolean repOK(){
		/*@Effects: \result==invariant(this).
		*/
		if(gui==null){
			return false;
		}
		return true;
	}
	
	
	public Light(TaxiGUI gui) {
		/*@REQUIRES:gui!=null
		 @
		 @
		 @MODIFIES:this
		 @
		 @EFFECTS:这是Light类的构造方法，用于初始化Light对象
		 @
		 */
		try {
			this.gui = gui;
			File file = new File("d://light.txt");
			// 文件不存在
			if (!file.exists()) {
				System.out.println("No such file!");
			}
			// BufferedReader是可以按行读取文件
			FileInputStream inputStream = null;

			inputStream = new FileInputStream(file);

			BufferedReader bufferedReader = null;

			bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

			String str = null;

			for (int i = 0; i < size; i++) {

				str = bufferedReader.readLine();

				if (str == null) {
					System.out.println("invalid:" + str);
				}

				String[] s = str.split("");
				if (s.length != size) {
					System.out.println("invalid:" + str);
					System.out.println("s.length:" + s);
				}

				for (int j = 0; j < size; j++) {
					String str1 = "\\+?0*[01]";
					Pattern pattern = Pattern.compile(str1);
					Matcher matcher = pattern.matcher(s[j]);
					if (!matcher.matches()) {
						System.out.println("invalid:" + str);
					}
					light[i][j] = Integer.parseInt(s[j]);
				}
			}

			str = bufferedReader.readLine();

			if (str != null) {
				System.out.println("invalid:行数大于" + size);
			}

			// 设置红绿灯变化时间
			time = (int) (Math.random() * 50 + 50);
			//System.out.println("time=" + time);

			for (int p = 0; p < size; p++) {
				for (int q = 0; q < size; q++) {
					if (light[p][q] == 0) {
						gui.SetLightStatus(new Point(p, q), 0);
					} else if (light[p][q] == 1) {
						if (Math.random() > 0.5) {
							light[p][q] = 1;
							gui.SetLightStatus(new Point(p, q), 1);
						} else {
							light[p][q] = 2;
							gui.SetLightStatus(new Point(p, q), 2);
						}
					} else {
						System.out.println("invalid light");
					}
				}
			}

		} catch (Exception e) {
			System.out.println("invalid light");
		}

	}

	public void run() {
		/*@REQUIRES:none
		 @
		 @
		 @MODIFIES:this
		 @
		 @EFFECTS:红绿灯随时间变化
		 @
		 */
		try {
			while (true) {
				try {
					sleep(time);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (int i = 0; i < size; i++) {
					for (int j = 0; j < size; j++) {
						if (light[i][j] == 1) {
							light[i][j] = 2;
							gui.SetLightStatus(new Point(i, j), 2);
						} else if (light[i][j] == 2) {
							light[i][j] = 1;
							gui.SetLightStatus(new Point(i, j), 1);
						} else {
							gui.SetLightStatus(new Point(i, j), 0);
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("");
		}
	}

}
