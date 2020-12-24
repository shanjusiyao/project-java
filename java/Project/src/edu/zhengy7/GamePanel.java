package edu.zhengy7;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;



public class GamePanel extends JPanel{
	public static final int X = 50;
	public static final int Y = 40;
	public static final int LINE = 15;
	public static final int SIZE = 40;
	public static final int CHESS = 30;


	private int[][] data_info;

	public GamePanel(int[][] data_info){
		this.data_info = data_info;
	}

	public void paint(Graphics g){
		super.paint(g);
		for(int i = 0; i < LINE; i++){
			g.drawLine(X, Y+i*SIZE, (LINE-1)*SIZE+X, Y+i*SIZE);
			g.drawLine(X+i*SIZE, Y, X+i*SIZE, (LINE-1)*SIZE+Y);
		}
		paint_board(g);
	}

	public void paint_board(Graphics g){
		for(int i = 0;i < data_info.length;i++){
			for(int j = 0;j<data_info[i].length;j++){
				if(data_info[i][j] == 1){
					g.setColor(Color.WHITE);
					g.fillOval(i * SIZE + X - CHESS / 2, j * SIZE + Y - CHESS / 2, CHESS, CHESS);
				}else if(data_info[i][j] == -1){
					g.setColor(Color.BLACK);
					g.fillOval(i * SIZE + X - CHESS / 2, j * SIZE + Y - CHESS / 2, CHESS, CHESS);
				}
			}
		}
	}

}
