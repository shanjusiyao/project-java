package edu.zhengy7;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class Launch{
  public static GameUI2 gui; 
  public static chessUI ai;
  public static int ChooseMode(){
      String[] options = {"Play with AI", "Play with real person"};
      return JOptionPane.showOptionDialog(null, "Mode choose: ",
              "Choose Mode to start",
              JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
  }

  public static void main(String[] args) throws IOException{

      int choose = ChooseMode();
      if (choose == 0){
        ai = new chessUI();
        ai.create();
      }else if (choose == 1){
        gui = new GameUI2();
        gui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gui.setVisible(true);

      }
  }
}
