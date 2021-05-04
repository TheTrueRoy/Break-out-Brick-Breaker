package tsg.ttt.main.utils;

public class GoodPlayer {
	
	public static int[] makeAPlay(int[][] boardLayout) { //Finds all possible plays and chooses the one with the highest win percentage float returned
		int[] bPlay = new int[2];
		int[][] possiblePlays = new int[0][2];
		for(int i = 0; i < 3; i++) {
			for(int I = 0; I < 3; I++) {
				if (boardLayout[I][i] == 0)
					possiblePlays = combineArray(possiblePlays, new int[]{I,i});
			}
		}
		float bP = 0f;
		for(int i = 0; i < possiblePlays.length; i++) {
			if (bP < winPercentage(boardLayout,possiblePlays[i],2,1)) {
				bP = winPercentage(boardLayout,possiblePlays[i],2,1);
				bPlay = possiblePlays[i];
			} else if (bP <= winPercentage(boardLayout,possiblePlays[i],2,1)) {
				if (Math.random() < 0.5) {
					bP = winPercentage(boardLayout,possiblePlays[i],2,1);
					bPlay = possiblePlays[i];
				}
			}
		}
		
		return bPlay;
	}
	
	public static float winPercentage(int[][] boardLayout, int[] move, int CurrentPlayer, int layersDown) { 
		/*Updates board with the move it was told to make, then checks if the game is done, if not it	*
		 *finds all possible moves and checks their win percentage before finding the average of all 	*
		 *possible moves and returning said average. If it does end it returns 1f for a computer victory*
		 *0.5f for a tie and 0f for a loss. It assumes the human player always takes the best possible	*
		 *move																							*/
		float winP = 0f;
		int[][] newBoard = new int[3][3];
		System.arraycopy(boardLayout[0], 0, newBoard[0], 0, boardLayout[0].length);
		System.arraycopy(boardLayout[1], 0, newBoard[1], 0, boardLayout[1].length);
		System.arraycopy(boardLayout[2], 0, newBoard[2], 0, boardLayout[2].length);
		newBoard[move[0]][move[1]] = CurrentPlayer;
		if (doesWin(newBoard).equals("continue")) {
			int[][] possiblePlays = new int[0][2];
			if (CurrentPlayer == 1) {
				possiblePlays = combineArray(possiblePlays, makeAPlay(newBoard));
			} else {
				for(int i = 0; i < 3; i++) {
					for(int I = 0; I < 3; I++) {
						if (newBoard[I][i] == 0)
							possiblePlays = combineArray(possiblePlays, new int[]{I,i});
					}
				}
			}
			for(int i = 0; i < possiblePlays.length; i++) {
				winP+=winPercentage(newBoard,possiblePlays[i],-CurrentPlayer+3,layersDown+1)/possiblePlays.length;
			}
		} else if (doesWin(newBoard).equals("win")) {
			winP = 1f;
		} else if (doesWin(newBoard).equals("loss")) {
			winP = 0f;
		} else if (doesWin(newBoard).equals("tie")) {
			winP = 0.5f;
		}
		return winP;
	}
	
	public static int[][] combineArray(int[][] arr1, int[] arr2) { //Concatenates arrays
		int[][] newArr = new int[arr1.length + 1][2];
		System.arraycopy(arr1, 0, newArr, 0, arr1.length); 
		newArr[arr1.length] = arr2;
		return newArr;
	}
	
	public static String doesWin(int[][] boardLayout) { //Checks if the game is over and returns a String containing the result for the AI
		for(int i = 0; i < 3; i++) {
			if(boardLayout[i][0] == boardLayout[i][1] && boardLayout[i][0] == boardLayout[i][2] && boardLayout[i][0] != 0) {
				if (boardLayout[i][0] == 2)
					return "win";
				if (boardLayout[i][0] == 1)
					return "loss";
			}
			if(boardLayout[0][i] == boardLayout[1][i] && boardLayout[0][i] == boardLayout[2][i] && boardLayout[0][i] != 0) {
				if (boardLayout[0][i] == 2)
					return "win";
				if (boardLayout[0][i] == 1)
					return "loss";
			}
		}
		if(boardLayout[0][0] == boardLayout[1][1] && boardLayout[0][0] == boardLayout[2][2] && boardLayout[0][0] != 0) {
			if (boardLayout[0][0] == 2)
				return "win";
			if (boardLayout[0][0] == 1)
				return "loss";
		}
		if(boardLayout[0][2] == boardLayout[1][1] && boardLayout[0][2] == boardLayout[2][0] && boardLayout[0][2] != 0) {
			if (boardLayout[0][2] == 2)
				return "win";
			if (boardLayout[0][2] == 1)
				return "loss";
		}
		if (boardLayout[0][0] != 0 && boardLayout[1][0] != 0 && boardLayout[2][0] != 0 && boardLayout[0][1] != 0 && boardLayout[1][1] != 0 && boardLayout[2][1] != 0 && boardLayout[0][2] != 0 && boardLayout[1][2] != 0 && boardLayout[2][2] != 0) {
			return "tie";
		}
		return "continue";
	}
	
}
