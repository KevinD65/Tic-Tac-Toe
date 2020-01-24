package TicTacToe;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.lang.IndexOutOfBoundsException;
public class TicTacToe{
	String letter;
	public TicTacToe(String a) {
		this.letter = a;
	}
	public static void main(String[]args) {
		TicTacToe X = new TicTacToe("X");
		TicTacToe O = new TicTacToe("O");
		Scanner input = new Scanner(System.in);
		String[][]board = new String[5][5];
/*
 * ------------------------------------------------------------------------------------------------------------------------------------------------------
 * For loop with conditionals to display the proper borders of the Tic-Tac-Toe board
 * ------------------------------------------------------------------------------------------------------------------------------------------------------
 */
		for(int a = 0; a < 5; a++) {
			if(a % 2 == 0) {
				for(int i = 0; i < 5; i++) {
					if(i % 2 == 0) {
						board[a][i] = " ";
					}
					else {
						board[a][i] = "|";
					}
				}
			}
			else {
				for(int j = 0; j < 5; j++) {
					if(j % 2 == 0) {
						board[a][j] = "-";
					}
					else {
						board[a][j] = "+";
					}
				}
			}
		}
/*
 * -----------------------------------------------------------------------------------------------------------------------------------------------------
 * Prompts user(s) to place 'X' and 'O' at location on board
 * Catches exceptions such as IndexOutOfBoundsException and InputMismatchException
 * 	Allows user to try again if input causes a caught exception and if input is in an occupied location
 * -----------------------------------------------------------------------------------------------------------------------------------------------------
 */
		boolean repeat = false; boolean flag = true; int row; int column; boolean end = false;
		do {
			do {
				try {
					System.out.println("Place an X (Row:0-2; Column:0-2): ");
					row = input.nextInt(); column = input.nextInt();
					repeat = X.place(board, row, column);
				}
				catch(InputMismatchException ime) {
					System.out.println("Please choose a numerical location on the board.");
					System.out.println();
					input.nextLine();
					repeat = true;
				}
				catch(IndexOutOfBoundsException iobe){
					System.out.println("Please choose a location on the board.");
					System.out.println();
					input.nextLine();
					repeat = true;
				}
				finally {
					if(X.checkWin(board) == true) {
						System.out.println("X Wins!");
						flag = false;
						end = true;
					}
					else if(draw(board) == true) {
						System.out.println("Draw!");
						flag = false;
						end = true;
					}
				}
			}while(repeat == true);
			repeat = false;
			do {
				try {
					if(end != true) {
						System.out.println("Place an O (Row:0-2; Column:0-2): ");
						row = input.nextInt(); column = input.nextInt();
						repeat = O.place(board, row, column);
					}
				}
				catch(InputMismatchException ime) {
					System.out.println("Please choose a numerical location on the board.");
					System.out.println();
					input.nextLine();
					repeat = true;
				}
				catch(IndexOutOfBoundsException iobe){
					System.out.println("Please choose a location on the board.");
					System.out.println();
					input.nextLine();
					repeat = true;
				}
				finally {
					if(O.checkWin(board) == true){
						System.out.println("O Wins!");
						flag = false;
					}
				}
			}while(repeat == true);
		}while(flag == true);
		input.close();
	}
/*
 * ------------------------------------------------------------------------------------------------------------------------------------------------------
 * place() method which takes 3 parameters: a 2D String array, and 2 int variables; returns a boolean value
 * Places a piece (based on the caller object) at the specified location
 * Notifies the user if attempts to place piece in occupied location
 * ------------------------------------------------------------------------------------------------------------------------------------------------------
 * 
 */
	public boolean place(String[][]a, int b, int c)
	{
		int column = c * 2; 
		int row = b * 2;
		if(a[row][column] != " " && a[row][column] != null) {
			System.out.println("Please choose an unoccupied location.");
			System.out.println();
			return(true);
		}
		else {
			a[row][column] = this.letter;
			displayBoard(a);
			return(false);
		}
	}
/*
 * -----------------------------------------------------------------------------------------------------------------------------------------------------
 * checkWin() method which accepts a 2D String array parameter and returns a boolean value
 * Checks verticals, horizontals, and both diagonals for 3-in-a-row
 * If a win is detected, returns true; otherwise, false
 * -----------------------------------------------------------------------------------------------------------------------------------------------------
 */
	public boolean checkWin(String[][]a) {
		int counter = 0;
		for(int i = 0; i < a.length; i+=2) {
			for(int j = 0; j < a[i].length; j+=2) {
				if(a[i][j] == this.letter) {
					counter++;
					if(counter == 3) {
						return(true);
					}
				}
				else {
					counter = 0;
					continue;
				}
			}
			counter = 0;
		}
		counter = 0; int columnTracker = 0;
		for(int c = 0; c < a.length; c+=2) {
			for(int d = 0; d < a[c].length; d+=2) {
				if(a[d][c] == this.letter) {
					counter++;
					if(counter == 3) {
						return(true);
					}
				}
				else {
					counter = 0;
					continue;
				}
			}
			counter = 0;
		}
		for(int y = 0; y < 5; y+=2) {
			if(a[y][columnTracker] == this.letter) {
				counter++;
				columnTracker+=2;
				if(counter == 3) {
					return(true);
				}
			}
			else {
				counter = 0;
				continue;
			}
		}
		counter = 0; columnTracker = 4;
		for(int z = 0; z < 5; z+=2) {
			if(a[z][columnTracker] == this.letter) {
				counter++;
				columnTracker-=2;
				if(counter == 3) {
					return(true);
				}
			}
			else {
				counter = 0;
				columnTracker = 4;
				continue;
			}
		}
		return(false);
	}
/*
 * -----------------------------------------------------------------------------------------------------------------------------------------------------
 * draw() static method which accepts a 2D String array parameter and returns a boolean value
 * checks to see if the board is full (and no wins), meaning a draw
 * -----------------------------------------------------------------------------------------------------------------------------------------------------
 */	
	public static boolean draw(String[][]a) {
		for(int i = 0; i < 5; i+=2) {
			for(int j = 0; j < 5; j+=2) {
				if(a[i][j] == " ") {
					return(false);
				}
			}
		}
		return(true);
	}
/*
 * -----------------------------------------------------------------------------------------------------------------------------------------------------
 * displayBoard() static method which accepts a 2D String array parameter and returns nothing
 * Displays the current status of the board
 * -----------------------------------------------------------------------------------------------------------------------------------------------------
 */
	public static void displayBoard(String[][]a){
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < 5; j++){
				System.out.print(a[i][j]);
			}
			System.out.println();
		}
	}
}