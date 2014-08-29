package mainApp;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

//import Snake.PlayPanel;

public class MainFrame extends JFrame implements ActionListener, KeyListener {

	
	public static final int BEGINNER = 1, MIDDLE = 2, EXPERT = 3;
	
	private JToolBar jtb = new JToolBar();
	
	private JButton jb1 = new JButton("开始"), jb2 = new JButton("暂停"),
			jb3 = new JButton("结束"), jb4 = new JButton("帮助");

	private JButton jbl = new JButton(("  难度     ")), jbl1 = new JButton("初级"),
			jbl2 = new JButton("中级"), jbl3 = new JButton("高级"),
			jbl4 = new JButton("                                                    ");



	private Help help;

	private boolean isPause = false, isEnd = true;

	private int level = 1;

	private PlayPanel playPanel = new PlayPanel();

	public static int speed = 300;

	private SnakeThread thread = new SnakeThread();
	
	

	public MainFrame() {
		
		
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());
		jtb.add(jb1);
		jtb.add(jb2);
		jtb.add(jb3);
		jtb.add(jb4);
		
		jbl.setEnabled(false);
		jbl4.setEnabled(false);
		
		jtb.add(jbl4);

		jtb.add(jbl);
		jtb.add(jbl1);
		jtb.add(jbl2);
		jtb.add(jbl3);

		jb2.setEnabled(false);
		jb3.setEnabled(false);

		

		// 设置按钮不可获得焦点
		jb1.setFocusable(false);
		jb2.setFocusable(false);
		jb3.setFocusable(false);
		jb4.setFocusable(false);
		jbl1.setFocusable(false);
		jbl2.setFocusable(false);
		jbl3.setFocusable(false);

		jbl1.setForeground(Color.blue);

		cp.add(jtb, BorderLayout.NORTH);

		//cp.add(jp1, BorderLayout.CENTER);
		
		cp.add(playPanel, BorderLayout.CENTER);
		
		
		this.setTitle("贪吃蛇");
		this.setVisible(true);
		this.setSize(820, 650);
		this.setDefaultCloseOperation(3);
		this.setLocationRelativeTo(null);

		jb1.addActionListener(this);
		jb2.addActionListener(this);
		jb3.addActionListener(this);
		jb4.addActionListener(this);

		jbl1.addActionListener(this);
		jbl2.addActionListener(this);
		jbl3.addActionListener(this);
		
		this.addKeyListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == jb1) {
			isEnd = false;
			isPause = false;
			playPanel.createSnake();
			playPanel.createFood();
			try {
			 thread.start();
			} catch (Exception e1) {
			}
			jb1.setEnabled(false);
			jb2.setEnabled(true);
			jb3.setEnabled(true);
		} else if (e.getSource() == jb2) {
			if (!isPause) {
				jb2.setText("继续");

			} else {
				jb2.setText("暂停");
			}
			isPause = !isPause;
		} else if (e.getSource() == jb3) {
			isEnd = true;
			isPause = false;
			playPanel.clear();
			jb1.setEnabled(true);
			jb2.setText("暂停");
			jb2.setEnabled(false);
			jb3.setEnabled(false);
		} else if (e.getSource() == jb4) {
			help = new Help(this);
		} else if (e.getSource() == jbl1) {
			jbl1.setForeground(Color.blue);
			jbl2.setForeground(Color.black);
			jbl3.setForeground(Color.black);
			speed = 300;

		} else if (e.getSource() == jbl2) {
			jbl2.setForeground(Color.blue);
			jbl1.setForeground(Color.black);
			jbl3.setForeground(Color.black);
			speed = 200;

		} else if (e.getSource() == jbl3) {
			jbl3.setForeground(Color.blue);
			jbl1.setForeground(Color.black);
			jbl2.setForeground(Color.black);
			speed = 100;

		}
	}


	class SnakeThread extends Thread {
		public void run() {
			while (true) {
				try {
					Thread.sleep(speed);
					if (!isEnd && !isPause) {
						playPanel.moveSnake();
						
						if (playPanel.isLost()) {
							isEnd = true;
							
							JOptionPane.showMessageDialog(null, "    GAME OVER!\nTotal Score Is "+PlayPanel.score);
							isPause = false;
							playPanel.clear();
							jb1.setEnabled(true);
							jb2.setText("暂停");
							jb2.setEnabled(false);
							jb3.setEnabled(false);
						}
					}
				} catch (Exception e) {
				}
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		// 判断游戏状态
		if (!isEnd && !isPause) {
			//判断游戏状态
			if(! isEnd && ! isPause){
				//根据用户按键，设置蛇运动方向
				if(e.getKeyCode() == KeyEvent.VK_UP){
					playPanel.setSnakeDirection(PlayPanel.UP);
				}
				if(e.getKeyCode() == KeyEvent.VK_DOWN){
					playPanel.setSnakeDirection(PlayPanel.DOWN);
				}
				if(e.getKeyCode() == KeyEvent.VK_LEFT){
					playPanel.setSnakeDirection(PlayPanel.LEFT);
				}
				if(e.getKeyCode() == KeyEvent.VK_RIGHT){
					playPanel.setSnakeDirection(PlayPanel.RIGHT);
				}
			}
		}
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	
	 class Help extends JDialog {
			private JTextArea help;
			public Help(JFrame owner) {
				super(owner, "help");
				this.setSize(300, 250);
				Container cp=this.getContentPane();
				help=new JTextArea("游戏说明：                                                \n1:ff\n2:gg\n3:fff");
				help.setBackground(Color.green);
				cp.add(help);
				
				this.setLocationRelativeTo(null);
				this.setResizable(false);
				this.setModal(true);
				this.setVisible(true);
			}

		}

}
