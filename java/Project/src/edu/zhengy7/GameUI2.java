package edu.zhengy7;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.FileNotFoundException;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.net.*;

import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;





public class GameUI2 extends JFrame {
      private static final int X = 50;
    	private static final int Y = 40;
  	  private static final int LINE = 15;
  	  private static final int SIZE = 40;
  	  private static final int CHESS = 30;

      private int[][] data_infor = new int[LINE+2][LINE+2];
      private JPanel jp;
      private JScrollPane consolePane;
      private GamePanel gp;
      private JTextArea console;
      private Action saveAction;
      private Action show_hideAction;
      private boolean control_hidden = false; // Boolean value for hiden/show control panel
      private BufferedReader br;
      private BufferedWriter bw;
      private String userName;
      private int ready;
      private int op_ready;
      private int start; // 1 - start game already
      private int pick_first; // who go first ? serve go first
      private int net_connect; // check the Internet connected 0 - not connect
                               //                              1 - connected
      private int round_check; // get the information from the opponent

      private int x1, y1;
    	private int xx, yy;
      private int xx2, yy2;
    	//private int count = 0;
      private Graphics g;

      public GameUI2() {
        setTitle("vs Player");
    		setSize(1050, 700);
    		setDefaultCloseOperation(3);

    		setLocationRelativeTo(null);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        ready = 0;
        op_ready = 0;

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);



        JMenu fileMenu = new JMenu("Set");
        JMenu helpMenu = new JMenu("Help");
        JMenu s_hMenu = new JMenu("Dispose");





        //fileMenu
        fileMenu.setMnemonic('F');
        fileMenu.setToolTipText("Set parameter");

        JMenuItem openItem = fileMenu.add(new MenuAction("Set NickName"));
        openItem.setAccelerator(KeyStroke.getKeyStroke("ctrl A"));
        fileMenu.addSeparator();

        saveAction = new MenuAction("Change order");
        JMenuItem saveItem = fileMenu.add(saveAction);
        saveItem.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
        fileMenu.addSeparator();

        JMenuItem netItem = fileMenu.add(new MenuAction("Set network"));
        netItem.setAccelerator(KeyStroke.getKeyStroke("ctrl K"));
        fileMenu.addSeparator();

        fileMenu.add(
            new AbstractAction("Exit") {
              public void actionPerformed(ActionEvent event) {
                System.exit(0);
              }
            });

        //Help menu
        helpMenu.setMnemonic('H');

        Action aboutAction = new MenuAction("About");
        helpMenu.add(aboutAction);

        show_hideAction = new MenuAction("Show/Hide Panel");
        JMenuItem disItem = s_hMenu.add(show_hideAction);


        // add all together to menuBar
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        menuBar.add(s_hMenu);


        // Toolbar
        /*

        Action openAction = new ToolbarAction("New/Open");
        Action saveAction = new ToolbarAction("Save");
        Action exitAction =
            new AbstractAction("Exit") {
              public void actionPerformed(ActionEvent event) {
                System.exit(0);
              }
            };
        exitAction.putValue(Action.SHORT_DESCRIPTION, "Exit");
        Action showHideAction =
            new AbstractAction("Show/Hide Config Panel") {
              public void actionPerformed(ActionEvent event) {
                showHidePanel();
              }
            };

        // populate tool bar

        JToolBar bar = new JToolBar();
        bar.add(openAction);
        bar.add(saveAction);
        bar.addSeparator();
        bar.add(showHideAction);
        bar.addSeparator();
        bar.add(exitAction);
        add(bar, BorderLayout.NORTH);
          */

        net_connect = 0;
    		console = new JTextArea("Message:\n");
    		console.setEditable(false);
    		console.setWrapStyleWord(true);
    		console.setLineWrap(true);
        String m1 = "Welcome! ";
        showMsg(m1);
        m1 = "Default the player who host the room will start first, people can change it in set menu.";
        showMsg(m1);

    		consolePane = new JScrollPane(console);
    		consolePane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    		consolePane.setPreferredSize(new Dimension(280,10*40));
    		add(consolePane, BorderLayout.EAST);

    		gp = new GamePanel(data_infor);
    		gp.setBackground(new Color(139,139,122));
    		add(gp,BorderLayout.CENTER);
    		jp = new JPanel();
    		jp.setBackground(new Color(245,245,245));
    		add(jp,BorderLayout.WEST);
    		java.awt.FlowLayout fl=new java.awt.FlowLayout(5,5,60);
    		jp.setLayout(fl);
    		Dimension di=new Dimension(120,0);
    		jp.setPreferredSize(di);
    		javax.swing.JButton jReady=new javax.swing.JButton("Ready");
    		Dimension di1=new Dimension(100,60);
    		jReady.setPreferredSize(di1);
    		jp.add(jReady);
    		javax.swing.JButton jStart=new javax.swing.JButton("Start");
    		Dimension di2=new Dimension(100,60);
    		jStart.setPreferredSize(di2);
    		jp.add(jStart);

        javax.swing.JButton jdec=new javax.swing.JButton("Receive");
    		Dimension di3=new Dimension(100,60);
    		jdec.setPreferredSize(di3);
    		jp.add(jdec);

    		javax.swing.JButton jSurrender=new javax.swing.JButton("Surrender");
    		Dimension di5=new Dimension(100,60);
    		jSurrender.setPreferredSize(di5);
    		jp.add(jSurrender);
    		setVisible(true);
    		g = gp.getGraphics();
        jStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jStartActionPerformed(evt);
            }
        });
        jdec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jdecActionPerformed(evt);
            }
        });
    		//jSurrender.addActionListener(mouse);
        jReady.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jReadyActionPerformed(evt);
            }
        });
        jSurrender.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSurrenderActionPerformed(evt);
            }
        });

      }

      private class MenuAction extends AbstractAction {
        /**
         * Class constructor
         * @param name: name of the action
         */
        public MenuAction(String name) {
          super(name);
        }

        /**
         * modify the operation for the Action, add the function and menu here
         *
         * @param event: the button event that has taken place
         */
        public void actionPerformed(ActionEvent event) {
          if (getValue(Action.NAME).equals("Show/Hide Panel")) {
            showHidePanel();

          }else if (getValue(Action.NAME).equals("Set NickName")) {
            userName = JOptionPane.showInputDialog("Set your username: ");
            String m1 = "Welcome "+ userName;
            showMsg(m1);

          }else if (getValue(Action.NAME).equals("Change order")) {
            if (pick_first == 1) {
              pick_first = 2;
              showMsg("You go second now.");
            }else{
              pick_first = 1;
              showMsg("You go first now.");
            }
          }else if (getValue(Action.NAME).equals("Set network")) {
            if (net_connect == 0) {
              Object[] options = {"Find the player to start", "Wait other player to join"};
              int n =
                  JOptionPane.showOptionDialog(
                      null,
                      "Do you want to connect with other player?",
                      "Internect Connect",
                      JOptionPane.YES_NO_OPTION,
                      JOptionPane.QUESTION_MESSAGE,
                      null,
                      options,
                      options[1]);
              if (n == JOptionPane.YES_OPTION) {
                connect_client();
              }else{
                connect_serve();
              }

            }else{
              JOptionPane.showMessageDialog(null, "Internet already connected");
              showMsg("Internet already connected");
            }

          } else System.out.println(getValue(Action.NAME) + " selected.");
        }
      }

      private void showMsg(String message) {
        console.append(message);
        console.append("\n");
      }
      private void showHidePanel() {
        control_hidden = !control_hidden;
        //set_panel.hide();

        if (!control_hidden) {
          gp.setVisible(false);
          consolePane.setVisible(true);
          jp.setVisible(true);
          gp.setVisible(true);
        } else {
          consolePane.setVisible(false);
          jp.setVisible(false);
        }

      }

      /** The first part window which player must to choose to connect to network */
      private void formWindowOpened(java.awt.event.WindowEvent evt) {
        Object[] options = {"Find the player to start", "Wait other player to join", "Cancel"};
        int n =
            JOptionPane.showOptionDialog(
                this,
                "Do you want to connect with other player?",
                "Internect Connect",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2]);
        if (n == JOptionPane.YES_OPTION) {
          connect_client();
        }else if (n == JOptionPane.NO_OPTION){
          connect_serve();
        }else{
          showMsg("Network need to set, go to set Menu");
          JOptionPane.showMessageDialog(null, "Network need to set, go to set Menu");
        }
      }

      private void connect_serve(){
        try
        {
           // establish server socket
           ServerSocket s = new ServerSocket(8133);

           // wait for client connection
           Socket incoming = s.accept();
           JOptionPane.showMessageDialog(null, "Success Connect");
           String ip=(((InetSocketAddress) incoming.getRemoteSocketAddress()).getAddress()).toString().replace("/","");
           showMsg("Success Connect");
           //showMsg("Client ip address: ");
           ip = "Client ip address: " +ip;
           showMsg(ip);
           pick_first = 1;
           net_connect = 1;
           try
           {
             br = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
             bw = new BufferedWriter(new OutputStreamWriter(incoming.getOutputStream()));

           }
           finally
           {
             /*
              incoming.close();
              */
           }
        }
        catch (IOException e)
        {
           e.printStackTrace();
        }

      }
      /** Connect the network as the client  */
      private void connect_client(){
        try
        {
        	 String server, input;
        	 int port;
        	 //Scanner cons = new Scanner(System.in);
           server = JOptionPane.showInputDialog("Enter server name/address: ","localhost");
           input = JOptionPane.showInputDialog("Enter server port: (Try 8133)","8133");
        	 port = Integer.valueOf(input);
           Socket s = new Socket(server, port);
           //s.setSoTimeout(3000);
           JOptionPane.showMessageDialog(null, "Success Connect!");
           showMsg("Success Connect");
           pick_first = 2;
           net_connect = 1;
           try
           {
             br = new BufferedReader(new InputStreamReader(s.getInputStream()));
             bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));

           }
           catch (NoSuchElementException e) {
           	 System.out.println("The remote server appears to have closed the connection");
           }

        }
        catch (IOException e)
        {
           e.printStackTrace();
        }
      }

      private void jReadyActionPerformed(java.awt.event.ActionEvent evt) {
        if (net_connect == 0) {
          showMsg("Network need to set, go to set Menu");
          JOptionPane.showMessageDialog(null, "Network need to set, go to set Menu");
          return;
        }else{
          if (start == 1) {
            JOptionPane.showMessageDialog(null, "Game Start");
            return;
          }else{
            if (ready == 1) {
              ready = 0;
              showMsg("Cancel ready state");
            }else{
              ready = 1;
              showMsg("You are ready to start game");
              showMsg("Wait for the opponent");
              try{
                bw.write(ready+"\n");
                bw.flush();
              }
              catch (IOException e)
              {
                 e.printStackTrace();
              }
              String str = null;
              while(op_ready == 0){
                // only come up once
                try{
                  String mess = br.readLine();
                  if (mess != null) {
                    //System.out.println("br!!");
                    //System.out.println(mess);
                    if (mess.equals("1")) {
                      op_ready = 1;

                    }

                  }

                }
                catch (IOException e)
                {
                }
              }
              // add the username later!!!!


              showMsg("Both ready to start");
              if (pick_first == 1) {
                showMsg("You go first");
              }else{
                showMsg("You go second");
              }
              showMsg("Click Start to start the game");
              start = 1;
            }
          }
        }
      }


      private void jStartActionPerformed(java.awt.event.ActionEvent evt) {
        if (start != 1) {
          JOptionPane.showMessageDialog(null, "Both player need to ready first");

        }else{
            gp.addMouseListener(new MouseAdapter(){
              public void mouseClicked(MouseEvent e) {
                x1 = e.getX();
            		y1 = e.getY();
            		rr();
            }
            });


        }
      }

      private void jSurrenderActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("Restart");
  			u_lose();
        try{
          String tmp = "a@b";

          bw.write(tmp+"\n");
          bw.flush();
        }
        catch (IOException e)
        {
           e.printStackTrace();
        }
  			restart();
      }

      private int checkrow(int x, int y){
        int num_1 = 0;
        for (int i = x + 1; i < 15; i++) {
          if (data_infor[Math.abs(i)][y] == data_infor[x][y]) {
            num_1++;
            //System.out.println(num_1);
          } else {
            break;
          }
        }
        for (int i = x; i >= 0; i--) {
          if (data_infor[Math.abs(i)][y] == data_infor[x][y]) {
            num_1++;
          } else {
            break;
          }
        }
        return num_1;

      }


    	private int checkcolumn(int x, int y) {
    		int num_1 = 0;
    		for (int i = y + 1; i < 15; i++) {
    			if (data_infor[x][Math.abs(i)] == data_infor[x][y]) {
    				num_1++;
    			} else {
    				break;
    			}
    		}
    		for (int i = y; i >= 0; i--) {
    			if (data_infor[x][Math.abs(i)] == data_infor[x][y]) {
    				num_1++;
    			} else {
    				break;
    			}
    		}

    		return num_1;
    	}

    	// б
    	private int checkinclined1(int x, int y) {
    		int num_1 = 0;
    		for (int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
    			if (data_infor[i][j] == data_infor[x][y]) {
    				num_1++;
    				//System.out.println(num_1);
    			} else {
    				break;
    			}
    		}
    		for (int i = x + 1, j = y + 1; i < LINE && j < LINE; i++, j++) {
    			if (data_infor[i][j] == data_infor[x][y]) {
    				num_1++;
    			} else {
    				break;
    			}
    		}
    		return num_1;

    	}

    	private int checkinclined2(int x, int y) {
    		int num_1 = 0;
    		for (int i = x - 1, j = y + 1; i >= 0 && j < LINE; i--, j++) {
    			if (data_infor[i][j] == data_infor[x][y]) {
    				num_1++;
    			} else {
    				break;
    			}
    		}
    		for (int i = x + 1, j = y - 1; i < LINE && j >= 0; i++, j--) {
    			if (data_infor[i][j] == data_infor[x][y]) {
    				num_1++;
    			} else {
    				break;
    			}
    		}
    		return num_1;
    	}
    	private void gameWin(int x,int y){
    		if(checkinclined1(x, y)>=4||checkinclined2(x,y)>=4||checkcolumn(x,y)>=5||checkrow(x,y)>=5){

          if (pick_first == 1) {
            if(data_infor[x][y] == -1){
              u_lose();
            }else if(data_infor[x][y] == 1){
              u_win();
            }

          }else{
            if(data_infor[x][y] == 1){
              u_lose();
            }else if(data_infor[x][y] == -1){
              u_win();
            }

          }
    		}
    	}
      private void jdecActionPerformed(java.awt.event.ActionEvent evt) {
        if (ready == 0) {
          JOptionPane.showMessageDialog(null, "Ready first");
          restart();
          return;
        }
        try{
          String mess = br.readLine();
          /*
          if (mess.equals("a@b") ||mess.equals("b@a") )
          {
            if (mess.equals("b@a")) {
              u_lose();

            }else{
              u_win();
            }

          }else
          {
          */
            String[] s12 = mess.split("@", 2);
            //System.out.println(s12[0]);
            //System.out.println(s12[1]);
            xx2 = Integer.valueOf(s12[0]);
            yy2 = Integer.valueOf(s12[1]);
            rr2(xx2,yy2);

          //}
        }
        catch (IOException e)
        {

        }

      }

      private void u_lose() {
    		JOptionPane.showMessageDialog(null, "You lose 2333, Trt it again");
        restart();
    	}


    	private void u_win() {
    		JOptionPane.showMessageDialog(null, "You win!!!");
        restart();
    	}

      private void rr(){
        if ((x1 - X) % SIZE > SIZE / 2) {
          xx = (x1 - X) / SIZE + 1;
        } else {
          xx = (x1 - X) / SIZE;
        }
        if ((y1 - Y) % SIZE > SIZE / 2) {
          yy = (y1 - Y) / SIZE + 1;
        } else {
          yy = (y1 - Y) / SIZE;
        }

        if (data_infor[xx][yy] == 0) {
          if (pick_first == 1) {  // first go
            g.setColor(Color.WHITE);
            data_infor[xx][yy] = 1;
            String str= "White take the [" + String.valueOf(xx) + "][" +String.valueOf(yy)+ "]";
            showMsg(str);
          } else {
            g.setColor(Color.BLACK);
            data_infor[xx][yy] = -1;
            String str= "Black take the [" + String.valueOf(xx) + "][" +String.valueOf(yy)+ "]";
            showMsg(str);
          }
          g.fillOval(xx * SIZE + X - CHESS / 2, yy * SIZE + Y - CHESS / 2,CHESS, CHESS);
          gameWin(xx, yy);

        }

        try{
          String tmp = String.valueOf(xx) + "@"+ String.valueOf(yy);
          System.out.println(tmp);
          bw.write(tmp+"\n");
          bw.flush();
        }
        catch (IOException e)
        {
           e.printStackTrace();
        }

      }

      private void rr2(int m, int n){
        if (data_infor[m][n] == 0) {
          if (pick_first == 2) {  //
            g.setColor(Color.WHITE);
            data_infor[m][n] = 1;
            String str= "White take the [" + String.valueOf(m) + "][" +String.valueOf(n)+ "]";
            showMsg(str);
          } else {
            g.setColor(Color.BLACK);
            data_infor[m][n] = -1;
            String str= "Black take the [" + String.valueOf(m) + "][" +String.valueOf(n)+ "]";
            showMsg(str);
          }
          g.fillOval(m * SIZE + X - CHESS / 2, n * SIZE + Y - CHESS / 2,CHESS, CHESS);
          gameWin(m, n);
        }

      }

      private void restart(){
        for (int i = 0; i < LINE; i++) {
  				for (int j = 0; j < LINE; j++) {
  					data_infor[i][j] = 0;
  				}
  			}
  			gp.repaint();
        ready = 0;
        op_ready = 0;
        start = 0;

      }





}
