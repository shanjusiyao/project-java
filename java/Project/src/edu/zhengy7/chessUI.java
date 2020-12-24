package edu.zhengy7;

import javax.swing.*;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.event.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;



public class chessUI {
	JFrame frame;
	Chess chess = new Chess();
	ChessPanel chessPanel;

	/**
	 * create window
	 */
	public void create() {
		frame = new JFrame("vs AI");
		chessPanel = new ChessPanel();
		frame.add(chessPanel);

		//start new control
		int yes=JOptionPane.showConfirmDialog(null,"Use the save data？","Use data",JOptionPane.YES_NO_OPTION );
		if(yes==JOptionPane.YES_OPTION) {
			readBoard();
		}

		JToolBar bar = new JToolBar();
		frame.add(bar, BorderLayout.SOUTH);
		bar.setBorderPainted(false);





		//tool of restart
		Icon resticon = new ImageIcon(chessUI.class.getResource("restart.png"));//Icon
		JButton restartAction = new JButton("restart", resticon);//Action
		restartAction.setToolTipText("restart");
		restartAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				restartBoard();
			}
		});

		//save part
		Icon saveicon = new ImageIcon(chessUI.class.getResource("save.png"));//Icon
		JButton saveAction = new JButton(" Save", saveicon);//Action
		saveAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					saveBoard();
			}
			public void saveBoard() {
				try {
			    	 FileOutputStream f1=new FileOutputStream("savedata.txt",false);
			    	 FileOutputStream f2=new FileOutputStream("list.txt",false);
			    	 ObjectOutputStream out1=new ObjectOutputStream(f1);
			    	 ObjectOutputStream out2=new ObjectOutputStream(f2);
			    	 out1.writeObject(chess);
			    	 out2.writeObject(chessPanel.list);
			    	 out1.close();
			    	 out2.close();
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		});


		//first hand tool
		Icon fistIcon = new ImageIcon(chessUI.class.getResource("shouxian.png"));
		final JButton firstAction = new JButton("first hand", fistIcon);
		firstAction.setBorderPainted(true);
		firstAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(firstAction.getText().equals("Player first")){
					firstAction.setText("AI first");
					Chess.first=Chess.AI;
				}else{
					firstAction.setText("Player first");
					Chess.first=Chess.PLAYER;
				}

			}
		});

		//add tool to bar
		bar.add(restartAction);
		bar.add(saveAction);
		bar.add(firstAction);

		//set the mouse event on board
		chessPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showChess(chessPanel, e);
			}
			public void showChess(ChessPanel chessPanel, MouseEvent e) {
				//get coor at the click point
				int x = e.getX();
				int y = e.getY();
				int row = (y - 5) / 30;
				int col = (x - 5) / 30;

				//check the players chess is able to place

				if (chess.playChess(row, col,Chess.PLAYER)) {
					chessPanel.Playchess(row, col, Chess.PLAYER);

					//check player winning
					if (chess.isWin(row, col, Chess.PLAYER)){
						JOptionPane.showMessageDialog(frame, "Player Win", "Player Win！",JOptionPane.WARNING_MESSAGE);
						restartBoard();//if play win, reset the board
						return;
					}

					//AI thinking part
					Chesspoint result = chess.Gettree();
					chess.playChess(result.getX(), result.getY(),Chess.AI);
					chessPanel.Playchess(result.getX(), result.getY(), Chess.AI);

					//check AI is winning or not
					if (chess.isWin(result.getX(), result.getY(),Chess.AI)){
						JOptionPane.showMessageDialog(frame, "AI Win", "You Lose！",JOptionPane.WARNING_MESSAGE);
						restartBoard();
						return;
					}

				} //else System.out.println("you can't place your chess here!");//for test;
			}

		});


		//set frame
		frame.setSize(470, 550);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
	}

	/**
	 * about restart
	 */
	public void restartBoard(){
		chess.Gamereset();
		chessPanel.clearBoard();
		if(Chess.first==Chess.AI){
			//check who is first hand
			Chesspoint location=chess.start();
			chess.playChess(location.getX(), location.getY(),Chess.AI);
			chessPanel.Playchess(location.getX(),location.getY(), Chess.AI);
		}
	}
	/**
	 * add unchecked to skip warning
	 */
	@SuppressWarnings("unchecked")
	public void readBoard() {
		try {
			FileInputStream f1=new FileInputStream("savedata.txt");
			FileInputStream f2=new FileInputStream("list.txt");
			ObjectInputStream in1=new ObjectInputStream(f1);
			ObjectInputStream in2=new ObjectInputStream(f2);
			chess=(Chess)in1.readObject();
			chessPanel.list=(List<Chesspoint>)in2.readObject();
			in1.close();
			in2.close();
			System.out.println("Save data has import");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
