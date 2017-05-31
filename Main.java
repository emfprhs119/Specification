import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

//����� �Ͼ���̰� layout�� null�� Container �ܰ��� draw
class ContainerRect extends Container {
	private static final long serialVersionUID = 6805380713240246261L;
	ContainerRect(){
		super();
		setLayout(null);
	}
	public void paint(Graphics g) {
		super.paint(g);
		g.drawRect(0, 0, 755,875);
	}
	
}

//����� �Ͼ���̰� layout�� null�� panel
class MyPanel extends JPanel {
	private static final long serialVersionUID = 5640668174921441140L;
	MyPanel(){
		setBackground(Color.WHITE);
		setLayout(null);
	}
}

public class Main {
	final static Color color = Color.getHSBColor((float) 0.160, (float) 0.42, (float) 0.98);
	static String font = "Serif"; 	//default font
	static int fontSize=17;			//default fontSize
	static int tableSize[]={235,95,85,85,50,95,95,45}; //default tableSize
	static boolean modify;			//���α׷� ��������(����� ���� ���� Ȯ��)
	static MainFrame mainFrame;
	public static void main(String[] args) {
		Scanner scan;
		modify=false;
		try {
			//initialize font,fontSize and tableSize data
			scan=new Scanner(new File("config.ini"));
			font=scan.next();
			fontSize=scan.nextInt();
			//ù�� �ѱ�
			scan.nextLine();	
			String str[]=scan.nextLine().split(":|,");
			for(int i=0;i<8;i++){
				//tableSize�� ���ܽ�Ű�� ���ڵ� ��Ī
				tableSize[i]=Integer.parseInt(str[i+1]);
			}
		} catch (FileNotFoundException ef) {
			try {
				//make config.ini file
				File file = new File("config.ini") ;
	            FileWriter fw = new FileWriter(file, true) ;
				fw.write(font+" "+fontSize+"\r\n");
				fw.write("tableSize:");
				for(int i=0;i<7;i++){
					fw.write(tableSize[i]+",");
				}
				fw.write(tableSize[7]+"");
	            fw.flush();
	            fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		mainFrame = new MainFrame();
	}
}
