package mainApp;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class PlayPanel extends JPanel{
	//状态栏相关属性
	private JLabel jlScore = new JLabel("0");
	private JLabel jlThroughBody = new JLabel("0");
	private JLabel jlThroughWall = new JLabel("0");
	public static int score = 0;
	private int throughBody = 0;
	private int throughWall = 0;
	
	//定义游戏区相关属性
	private static final int ROWS = 30;
	private static final int COLS = 50;
	private JButton[][] playBlocks;
	
	//蛇身相关属性
	private int length = 3;
	private int[] rows = new int[ROWS*COLS];
	private int[] columes = new int[ROWS*COLS];
	public static final int UP = 1,LEFT = 2,DOWN = 3,RIGHT = 4;
	private int direction = RIGHT;
	private int lastdirection = RIGHT;
	private boolean lost = false;
	
	//创建蛇身
	public void createSnake(){
		length = 3;
		score = 0;
		throughBody = 0;
		throughWall = 0;
		lost=false;
		direction = RIGHT;
		lastdirection = RIGHT;
		
		for(int i = 0; i <length; i++){
			rows[i] = 0;
			columes[i] = length - i;
		}
		for(int i = 0; i < length; i++){
			playBlocks[rows[i]][columes[i]].setBackground(Color.green);
			playBlocks[rows[i]][columes[i]].setVisible(true);
		}
	}
	
	//	构造方法
	public PlayPanel(){
		//构造状态栏
		JPanel statusPane = new JPanel();
		statusPane.add(new JLabel("得分："));
		statusPane.add(jlScore);
		statusPane.add(new JLabel("穿身宝物："));
		statusPane.add(jlThroughBody);
		statusPane.add(new JLabel("穿墙宝物："));
		statusPane.add(jlThroughWall);
		
		//构造游戏区面板
		JPanel showPane = new JPanel();//显示蛇身运动的游戏区面板
		showPane.setLayout(new GridLayout(ROWS,COLS,0,0));
		//设置边框
		showPane.setBorder(BorderFactory.createEtchedBorder());
		playBlocks = new JButton[ROWS][COLS];
		for (int i = 0; i < ROWS; i++){
			for (int j = 0;j < COLS; j++){
				playBlocks[i][j] = new JButton();
				playBlocks[i][j].setBackground(Color.LIGHT_GRAY);
				playBlocks[i][j].setVisible(false);    //将来程序完成时改成false
				playBlocks[i][j].setEnabled(false);
				showPane.add(playBlocks[i][j]);
			}
		}
		this.setLayout(new BorderLayout());
		this.add(statusPane,BorderLayout.NORTH);
		this.add(showPane,BorderLayout.CENTER);
	}
	
	//在游戏区内随机放置食物
	public void createFood(){
		int x = 0;
		int y = 0;
		do{
			x = (int)(Math.random() * ROWS);
			y = (int)(Math.random() * COLS);
		}while(playBlocks[x][y].isVisible());
		int random = (int)(Math.random() * 10);
		if(random < 7){
			playBlocks[x][y].setBackground(Color.yellow);
		}
		else if (random < 9){
			playBlocks[x][y].setBackground(Color.blue);
		}
		else{
			playBlocks[x][y].setBackground(Color.red);
		}
		playBlocks[x][y].setVisible(true);
	}
	
	//移动蛇
	public void moveSnake(){
		//去掉蛇头
		playBlocks[rows[length]][columes[length]].setVisible(false);
		playBlocks[rows[length]][columes[length]].setBackground(Color.lightGray);
		//显示除蛇头外蛇身
		for(int i = 0; i < length; i++){
			playBlocks[rows[i]][columes[i]].setBackground(Color.green);
			playBlocks[rows[i]][columes[i]].setVisible(true);
		}
		for(int i = length; i > 0; i--){
			rows[i] = rows[i - 1];
			columes[i] = columes[i - 1];
		}
		//根据蛇身运动方向，决定蛇头位置
		switch (direction){
			case UP:{
				if(lastdirection == DOWN)
					rows[0] += 1;
				else{
					rows[0] -= 1;
					lastdirection = UP;
				}
				break;
			}
			case LEFT:{
				if(lastdirection == RIGHT)
					columes[0] += 1;
				else{
					columes[0] -= 1;
					lastdirection = LEFT;
				}
				break;
			}
			case DOWN:{
				if(lastdirection == UP)
					rows[0] -= 1;
				else{
					rows[0] += 1;
					lastdirection = DOWN;
				}
				break;
			}
			case RIGHT:{
				if(lastdirection == LEFT)
					columes[0] -= 1;
				else{
					columes[0] += 1;
					lastdirection = RIGHT;
				}
				break;
			}
		
		}
		
		//处理蛇头碰到墙壁时操作
		if(rows[0] >= ROWS || rows[0] < 0 || columes[0] >= COLS || columes[0] <0){
			if(throughWall != 0){
				throughWall--;
				jlThroughWall.setText(Integer.toString(throughWall));
				if(rows[0] >= ROWS){
					rows[0] = 0;
				}
				else if(rows[0] < 0){
					rows[0] = ROWS - 1;
				}
				else if(columes[0] >= COLS){
					columes[0] = 0;
				}
				else if(columes[0] < 0){
					columes[0] = COLS - 1;
				}
			}
			else{
				lost = true;
				return;
			}
		}
		
		if(playBlocks[rows[0]][columes[0]].getBackground().equals(Color.green)){
			if(throughBody != 0){
				throughBody--;
				jlThroughBody.setText(Integer.toString(throughBody));
			}
			else{
				lost = true;
				return;
			}
		}
		
		if(playBlocks[rows[0]][columes[0]].getBackground().equals(Color.yellow)
				||playBlocks[rows[0]][columes[0]].getBackground().equals(Color.blue)
				||playBlocks[rows[0]][columes[0]].getBackground().equals(Color.red)){
			length++;
			createFood();
			score +=100;
			jlScore.setText(Integer.toString(score));
			
			if(playBlocks[rows[0]][columes[0]].getBackground().equals(Color.blue)){
				throughBody++;
				jlThroughBody.setText(Integer.toString(throughBody));
			}
			if(playBlocks[rows[0]][columes[0]].getBackground().equals(Color.red)){
				throughWall++;
				jlThroughWall.setText(Integer.toString(throughWall));
			}
		}
		
		//显示蛇头
		playBlocks[rows[0]][columes[0]].setBackground(Color.green);
		playBlocks[rows[0]][columes[0]].setVisible(true);
	}
	
	//清空游戏区
	public void clear(){
		score = 0;
		throughBody = 0;
		throughWall = 0;
		jlScore.setText("0");
		jlThroughBody.setText("0");
		jlThroughWall.setText("0");
		MainFrame.speed=300;
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLS; j++){
				playBlocks[i][j].setBackground(Color.LIGHT_GRAY);
				playBlocks[i][j].setVisible(false);
			}
		}
	}
	
	
	//获取游戏状态
	public boolean isLost(){
		return lost;
	}
	
	//设置蛇的运行方向
	public void setSnakeDirection(int direction){
		this.direction = direction;
	}
	
}
