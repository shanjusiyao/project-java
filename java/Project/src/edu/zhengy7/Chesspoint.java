package edu.zhengy7;

import java.io.Serializable;

/**
 * basic function for chess_point
 * with x,y coord and player and score
 */
public class Chesspoint implements Serializable{

	private static final long serialVersionUID = 1L;

	private int x;
	private int y;

	private int player;
	private int score;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getPlayer() {
		return player;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Chesspoint(int x, int y, int player) {
		super();
		this.x = x;
		this.y = y;
		this.player = player;
	}
}
