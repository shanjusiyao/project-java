package edu.zhengy7;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 * Panel use JPanal
 */
public class ChessPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public List<Chesspoint> list = new ArrayList<Chesspoint>();
	private Font font=new Font("TimesRoman",Font.PLAIN,13);
	//The row number
	int row = 30;
	int margin=20;
	int rowper=15;
	int chessRadius=13;

	Color bgColor=new Color(200,170,150);
	Color pointColor=new Color(120,90,50);

	@Override
	public void paint(Graphics temp) {
		super.paint(temp);
		Graphics2D g=(Graphics2D)temp;
		g.setFont(font);
		//make it looks good anti-aliasing
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING ,RenderingHints.VALUE_ANTIALIAS_ON);
		drawBoard(g);
		drawChess(g);
	}

	/**
	 * draw board
	 * @param g the Graphics2D
	 */
	public void drawBoard(Graphics2D g){
		//set background
		g.setColor(bgColor);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		g.setColor(Color.BLACK);
		for (int i = 0; i < rowper; i++) {
			g.drawLine(margin, margin + row * i, margin + row * 14, margin + row * i);
			g.drawLine(margin + row * i, margin, margin + row * i, margin + row * 14);
		}

		g.setColor(pointColor);

		g.fillOval(margin-5 + 7 * row, margin-5 + 7 * row, 10, 10);
		g.fillOval(margin-5 + 3 * row, margin-5 + 3 * row, 10, 10);
		g.fillOval(margin-5 + 3 * row, margin-5 + 11 * row, 10, 10);
		g.fillOval(margin-5 + 11 * row, margin-5 + 3 * row, 10, 10);
		g.fillOval(margin-5 + 11 * row, margin-5 + 11 * row, 10, 10);
	}

	/**
	 * draw chess on board
	 * @param g the Graphics2D
	 */
	public void drawChess(Graphics2D g){
		int i=1;
		//make the number in the mid
		FontMetrics metrics=g.getFontMetrics();
		int asc = metrics.getAscent();
		int desc = metrics.getDescent();
		//go through and draw chess
		for (Chesspoint location : list) {
			if (location.getPlayer() == Chess.first)g.setColor(Color.black);
			else g.setColor(Color.white);
			g.fillOval(margin-13 + location.getY() * row, margin-chessRadius + location.getX() * row, chessRadius*2, chessRadius*2);
			//draw the number part
			if(location.getPlayer()==Chess.first) g.setColor(Color.white);
			else g.setColor(Color.black);
			g.drawString(i+"",margin + location.getY() * row-metrics.stringWidth(i+"")/2,margin + location.getX() * row-(asc+desc)/2+asc);
			i++;
		}
	}

	/**
	 * set chess on the board
	 * @param row row of the chesspanel
	 * @param col col of the chesspanel
	 * @param player player(AI or PLAYER)
	 */
	public void Playchess(int row, int col, int player) {
		list.add(new Chesspoint(row, col, player));
		repaint();
	}

	/**
	 * clean the board
	 */
	public void clearBoard() {
		list.clear();
		repaint();
	}
}
