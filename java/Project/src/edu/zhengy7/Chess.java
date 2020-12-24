package edu.zhengy7;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Chess implements Serializable{
	/**
	 * the Chess of the data
	 */
	private static final long serialVersionUID = 1L;
	private int [][] chess;
	public static final int AI = 1;
	public static final int PLAYER = 2;
	public static int first = PLAYER;

	public Chess(){
		chess = new int[15][15];
		Gamereset();
	}

	//reset the board and make game set well
	public void Gamereset(){
		for(int i = 0; i < chess.length; i++){
			for(int j = 0; j< chess.length; j++){
				chess[i][j] = 0;
			}
		}
	}
	//alway start at the mid point
	public Chesspoint start(){
		chess[7][7] = first;
		return new Chesspoint(7,7,first);
	}

	public boolean playChess(int x, int y, int player ){
		if(x < 0 || x >= chess.length || y< 0 || y>= chess.length || chess[x][y] != 0){
			return false;
		}
		chess[x][y] = player;
		return true;
	}

	private void addChess(List<Chesspoint> locat, int x, int y){
		int sign = 0;
		for(Chesspoint loc: locat){
			if(loc.getX() == x && loc.getY() == y){
				sign = 1;
				break;
			}
		}
		if(sign == 0){
			locat.add(new Chesspoint(x,y,AI));
		}
	}

	/**
	 * Get tree of the maxpoint, Gettree()
	 * @return new Chesspoint of pos
	 */
	public Chesspoint Gettree(){
		int maxpoint = 0;
		List<Chesspoint> can = getAlllocleft();
		List<Chesspoint> max = new ArrayList<Chesspoint>();
		for(Chesspoint loc: can){
			int score = Calscore(loc.getX(), loc.getY());
			loc.setScore(score);
			if(score > maxpoint){
				maxpoint = score;
			}
			if(maxpoint != 0 && score == maxpoint){
				if(max.size() > 0){
					if(max.get(0).getScore() < maxpoint){
						max.clear();
					}
				}
				max.add(loc);
			}
			//System.out.println("score =" + score + " x=" + loc.getX() + " y=" + loc.getY() );
		}
		Chesspoint pos = max.get((int)(Math.random() * max.size()));
		//System.out.println("AI:row：" + (pos.getX() + 1) + " col:" + (pos.getY() + 1));
		//out put for test
		return new Chesspoint(pos.getX(), pos.getY(), AI);
	}


	private List<Chesspoint> getAlllocleft(){
		List<Chesspoint> loc = new ArrayList<Chesspoint>();

		for(int i = 0; i< chess.length; i++){
			for(int j = 0; j< chess.length; j++){
				if(chess[i][j] != 0){
					if (i != 0 && j != 0 && chess[i - 1][j - 1] == 0)
						addChess(loc, i - 1, j - 1);
					if (i != 0 && chess[i - 1][j] == 0)
						addChess(loc, i - 1, j);
					if (i != 0 && j != (chess.length - 1) && chess[i - 1][j + 1] == 0)
						addChess(loc, i - 1, j + 1);
					if (j != 0 && chess[i][j - 1] == 0)
						addChess(loc, i, j - 1);
					if (j != (chess.length - 1) && chess[i][j + 1] == 0)
						addChess(loc, i, j + 1);
					if (i != (chess.length - 1) && j != 0 && chess[i + 1][j - 1] == 0)
						addChess(loc, i + 1, j - 1);
					if (i != (chess.length - 1) && chess[i + 1][j] == 0)
						addChess(loc, i + 1, j);
					if (i != (chess.length - 1) && j != (chess.length - 1) && chess[i + 1][j + 1] == 0)
						addChess(loc, i + 1, j + 1);
				}
			}
		}
		return loc;
	}

	/*
	并不打算加入禁手not going to add，
	public boolean forbiddenstep{

	}
	*/

	 /**
	 * check winning program
	 * @param x the x coordinate of the chess
	 * @param y the y coordinate of the chess
	 * @param ply the plyer neither AI or Player
	 * @return check weather the player or AI is winning
	 */
	public boolean isWin(int x, int y, int ply){
		int count = 0;
		int i, j;
		for(i = 0; i< chess.length; i++){
			if(chess[x][i] == ply){
				count++;
				if(count>=5){
					return true;
				}
			}else{
				count = 0;
			}
		}
		count = 0;
		for(i = 0; i< chess.length; i++){

			if(chess[i][y] == ply){
				count++;
				if(count>=5){
					return true;
				}
			}else{
				count = 0;
			}
		}
		count = 0;
		for(i = 0; i< chess.length; i++){

			if(chess[i][y] == ply){
				count++;
				if(count>=5){
					return true;
				}
			}else{
				count = 0;
			}
		}
		count = 0;
		for (i = x, j = y; i < chess.length && j < chess.length; i++, j++) {
			if (chess[i][j] == ply)
				count++;
			else
				break;
		}
		for (i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
			if (chess[i][j] == ply)
				count++;
			else
				break;
		}
		if (count >= 5)
			return true;
		count = 0;

		for (i = x, j = y; i < chess.length && j >= 0; i++, j--) {
			if (chess[i][j] == ply)
				count++;
			else
				break;
		}
		for (i = x - 1, j = y + 1; i >= 0 && j < chess.length; i--, j++) {
			if (chess[i][j] == ply)
				count++;
			else
				break;
		}
		if (count >= 5)
			return true;

		return false;
	}

	/**
	 * Calculate the score, (global score difference between player and AI)
	 * @param x the x coor of chess
	 * @param y the y coor of chess
	 * @return get the score for the chess
	 */
	public int Calscore( int x, int y){
		int scoreXY = getXYScore(x, y, 1) + getXYScore(x, y ,2);
		int scoresl = getslashScore(x, y, 1) + getslashScore(x, y, 2);

		return scoreXY  + scoresl;
	}

	/**
	 * get the score of the chess
	 * @param count the count number
	 * @param left the left has oppo or not
	 * @param right the right has oppo or not
	 * @return get the score for the chess
	 */
	private int Scoreincase(int count, int left, int right) {
		int score = 0;

		//five chess in a line
		if (count >= 5) {
			score += 1000000;
		}else if (count == 4) {
			if (left + right == 2) //with
				score += 50000;
			if (left + right == 3)
				score += 3000;
			if (left + right == 4)
				score += 100;
		}else if (count == 3) {
			if (left + right == 2)
				score += 2000;
			if (left + right == 3)
				score += 1000;
			if (left + right == 4)
				score += 50;
		}else if (count == 2) {
			if (left + right == 2)
				score += 300;
			if (left + right == 3)
				score += 200;
			if (left + right == 4)
				score += 10;
		}else if (count == 1) {
			if (left + right == 2)
				score += 100;
			if (left + right == 3)
				score += 50;
			if (left + right == 4)
				score += 5;
		}

		return score;
	}

	/**
	 * Horizontal and vertical scan scores
	 * @param x the x coordinate of the chess
	 * @param y the y coordinate of the chess
	 * @param ply the plyer neither AI or Player
	 * @return get the score of the XYscore
	 */
	public int getXYScore(int x, int y, int ply) {

		chess[x][y] = ply;


		int leftStatus = 0;
		int rightStatus = 0;
		int top = 0;
		int bot = 0;
		int i,j,count = 0;
		int count2 = 0;

		for (i = x; i < chess.length; i++) {
			if (chess[i][y] == ply)
				count++;
			else {
				if (chess[i][y] == 0)
					bot = 1;
				if (chess[i][y] == 3 - ply)
					bot = 2;
				break;
			}
		}
		for (i = x - 1; i >= 0; i--) {
			if (chess[i][y] == ply)
				count++;
			else {
				if (chess[i][y] == 0)
					top = 1;
				if (chess[i][y] == 3 - ply)
					top = 2;
				break;
			}
		}

		for (j = y; j < chess.length; j++) {
			if (chess[x][j] == ply)
				count2++;
			else {
				if (chess[x][j] == 0)
					rightStatus = 1;
				if (chess[x][j] == 3 - ply)
					rightStatus = 2;
				break;
			}
		}
		for (j = y - 1; j >= 0; j--) {
			if (chess[x][j] == ply)
				count2++;
			else {
				if (chess[x][j] == 0)
					leftStatus = 1;
				if (chess[x][j] == 3 - ply)
					leftStatus = 2;
				break;
			}
		}

		chess[x][y] = 0;


		return Scoreincase(count, top, bot) + Scoreincase(count2, leftStatus, rightStatus);
	}
	
	/**
	 * Oblique scanning score
	 * @param x The x coordinate of the chess
	 * @param y The y coordinate of the chess
	 * @param ply the plyer neither AI or Player
	 * @return get the score of the Oblique score
	 */
	public int getslashScore(int x, int y, int ply) {
		int ret;
		chess[x][y] = ply;

		int top = 0;
		int bot = 0;
		int i, j,count = 0;


		for (i = x, j = y; i < chess.length && j >= 0; i++, j--) {
			if (chess[i][j] == ply)
				count++;
			else {
				if (chess[i][j] == 0)
					bot = 1;
				if (chess[i][j] == 3 - ply)
					bot = 2;
				break;
			}
		}

		for (i = x - 1, j = y + 1; i >= 0 && j < chess.length; i--, j++) {
			if (chess[i][j] == ply)
				count++;
			else {
				if (chess[i][j] == 0)
					top = 1;
				if (chess[i][j] == 3 - ply)
					top = 2;
				break;
			}
		}
		ret = Scoreincase(count, top, bot);
		top = 0;
		bot = 0;
		count = 0;

		for (i = x, j = y; i < chess.length && j < chess.length; i++, j++) {
			if (chess[i][j] == ply)
				count++;
			else {
				if (chess[i][j] == 0)
					bot = 1;
				if (chess[i][j] == 3 - ply)
					bot = 2;
				break;
			}
		}

		for (i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
			if (chess[i][j] == ply)
				count++;
			else {
				if (chess[i][j] == 0)
					top = 1;
				if (chess[i][j] == 3 - ply)
					top = 2;
				break;
			}
		}
		chess[x][y] = 0;

		return ret + Scoreincase(count, top, bot);
	}
}
