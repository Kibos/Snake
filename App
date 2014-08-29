package mainApp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;



public class App {
	public static void autoClose() {
		System.out.println("1分钟自动关闭。");
		long startTime = System.currentTimeMillis();

		while (true) {
			long longtime = (System.currentTimeMillis() - startTime) / 1000;
			String time = String.format("%02d:%02d:%02d", longtime / 3600,
					longtime / 60, longtime % 60);
			System.out.println(time);
			if (time.equals("00:01:00")) {
				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");// 设置日期格式
				System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
				
				System.exit(0);
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	     MainFrame app=new MainFrame();
		
		//autoClose();
	}

	
}
