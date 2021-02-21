import java.util.HashMap;
import OXOExceptions.*;


class OXOController
{
    OXOModel gameModel;
    HashMap<Character, Integer> alphabetMap;
    
    public OXOController(OXOModel model)
    {
        gameModel = model;
        gameModel.setCurrentPlayer(model.getPlayerByNumber(0));
        alphabetMap = new HashMap<Character, Integer>();
        for(char i = 'a'; i < 'j'; i++) {
            alphabetMap.put(i, (int) i - 97);
        }
    }

    public void handleIncomingCommand(String command) throws OXOMoveException
    {   
        
        validateCell(command);
        gameModel.setCellOwner(y(command), x(command), gameModel.getCurrentPlayer());
        System.out.println(this.rightDiag(gameModel.getCurrentPlayer(), y(command), x(command))); 
        if(this.winCheck(gameModel.getCurrentPlayer(),y(command),x(command))) 
        {
            gameModel.setWinner(gameModel.getCurrentPlayer());
        }
        if(this.hasDrawn()) 
        {
        gameModel.setGameDrawn();;
        }
        
        this.nextTurn();
    }

    private void nextTurn() 
    {
        int currentIndex = gameModel.getIndexCurrentPlayer();
        int nextIndex = (currentIndex + 1) % gameModel.getNumberOfPlayers();
        gameModel.setCurrentPlayer(gameModel.getPlayerByNumber(nextIndex));
    }

    private int x(String command) 
    {
        char colVal = command.charAt(1);
        return Integer.parseInt(String.valueOf(colVal))-1;
    }

    private int y(String command) 
    {   
        char rowLetter = command.toLowerCase().charAt(0);
        return alphabetMap.get(rowLetter);
    }

    private void validateCell(String command) throws OXOMoveException
    {   
        char firstChar, secondChar, lowerFChar;
        int numRows, numCols, inputColNum;
        Object[] keyArray = alphabetMap.keySet().toArray();


        numRows = gameModel.getNumberOfRows();
        numCols = gameModel.getNumberOfColumns();

        if(command.length() != 2) {
            throw new InvalidIdentifierLengthException(numRows,numCols,command.length());
        }
        firstChar = command.charAt(0);
        secondChar = command.charAt(1);
        lowerFChar = Character.toLowerCase(firstChar);
        if(lowerFChar > 'z' || lowerFChar < 'a') 
        {
            throw new InvalidIdentifierCharacterException(numRows,numCols,firstChar,RowOrColumn.ROW);
        }
        if(secondChar > '9' || secondChar < '0') 
        {
            throw new InvalidIdentifierCharacterException(numRows,numCols,secondChar,RowOrColumn.COLUMN);
        }
        if(lowerFChar > (char) keyArray[numRows-1] || lowerFChar < (char) keyArray[0]) 
        {
            throw new OutsideCellRangeException(numRows,numCols,firstChar,RowOrColumn.ROW);
        }
        inputColNum = Integer.parseInt(String.valueOf(secondChar));
        if(inputColNum > numCols || inputColNum < 1) 
        {
            throw new OutsideCellRangeException(numRows,numCols,secondChar,RowOrColumn.COLUMN);
        }
        if(gameModel.getCellOwner(y(command), x(command))!=null)
        {
            throw new CellAlreadyTakenException(y(command), x(command));
        }

    }

    private boolean hasDrawn() {

        boolean isDraw = true;
        int numRows = gameModel.getNumberOfRows();
        int numCols = gameModel.getNumberOfColumns();

        for(int j=0; j<numRows; j++) {
            for(int i=0; i<numCols; i++) {
                if(gameModel.getCellOwner(j,i)==null){
                    isDraw = false;
                }
            }
        }
        return isDraw;
    }

    private boolean winCheck(OXOPlayer currentPlayer, int playerRowNum, int playerColNum) {
        
        boolean horizontal = horizontalWin(currentPlayer, playerRowNum),
                vertical= verticalWin(currentPlayer, playerColNum), 
                leftDiag = leftDiag(currentPlayer, playerRowNum, playerColNum),
                hasWon = false;

                if(horizontal||vertical||leftDiag) {hasWon=true;}

        return hasWon;
    }

    private boolean horizontalWin(OXOPlayer currentPlayer,int playerRowNum) {

        int winThresh = gameModel.getWinThreshold();
        int numCols = gameModel.getNumberOfColumns();
        boolean horizWin = false;

        for(int i = 0; i <= numCols-winThresh; i++) {
            if(gameModel.getCellOwner(playerRowNum, i)==currentPlayer) {
                int counter = 1;
                int streak = 1;
                int cellColNum = i+1;
                while(counter < winThresh) {
                    if(gameModel.getCellOwner(playerRowNum, cellColNum)==currentPlayer) {
                        streak++;
                    }
                    cellColNum++;
                    counter++;
                }
                if(streak==winThresh) {horizWin = true;}
            }
        }
        return horizWin;
    }


    private boolean verticalWin(OXOPlayer currentPlayer,int playerColNum) {

        int winThresh = gameModel.getWinThreshold();
        int numRows = gameModel.getNumberOfRows();
        boolean verticalWin = false;

        for(int j = 0; j <= numRows-winThresh; j++) {
            if(gameModel.getCellOwner(j,playerColNum)==currentPlayer) {
                int counter = 1;
                int streak = 1;
                int cellRowNum = j+1;
                while(counter < winThresh) {
                    if(gameModel.getCellOwner(cellRowNum, playerColNum)==currentPlayer) {
                        streak++;
                    }
                    cellRowNum++;
                    counter++;
                }
                if(streak==winThresh) {verticalWin = true;}
            }
        }
        return verticalWin;
    }

    private boolean leftDiag(OXOPlayer currentPlayer, int playerRowNum, int playerColNum) {

        int startingX = playerColNum, startingY = playerRowNum;
        int numRows = gameModel.getNumberOfRows(), numCols = gameModel.getNumberOfColumns();
        int winThresh = gameModel.getWinThreshold();
        boolean leftDiagWin = false;
        

        while(startingX > 0 && startingY > 0) {
            startingX = startingX - 1;
            startingY = startingY - 1;
        }
        
        for(int j = startingY,i =startingX; j<=numRows-winThresh && i<=numCols-winThresh; j++,i++) {
            if(gameModel.getCellOwner(j, i)==currentPlayer) {
                int counter = 1;
                int streak = 1;
                int cellRowNum = j+1, cellColNum = i+1;
                while(counter < winThresh) {
                    if(gameModel.getCellOwner(cellRowNum, cellColNum)==currentPlayer) {
                        streak++;
                    }
                    cellRowNum++;
                    cellColNum++;
                    counter++;
                }
                if(streak==winThresh) {leftDiagWin = true;}
            }
        }
        return leftDiagWin;
    }

    private boolean rightDiag(OXOPlayer currentPlayer, int playerRowNum, int playerColNum) {

        int startingX = playerColNum, startingY = playerRowNum;
        int numRows = gameModel.getNumberOfRows(), numCols = gameModel.getNumberOfColumns();
        int winThresh = gameModel.getWinThreshold();
        boolean rightDiagWin = false;
        

        while(startingX < numRows-1 && startingY > 0) {
            startingX = startingX + 1;
            startingY = startingY - 1;
        }
        
        for(int j = startingY,i =startingX; j<=numRows-winThresh && i<=numCols-winThresh; j++,i--) {
            if(gameModel.getCellOwner(j, i)==currentPlayer) {
                int counter = 1;
                int streak = 1;
                int cellRowNum = j+1, cellColNum = i+1;
                while(counter < winThresh) {
                    if(gameModel.getCellOwner(cellRowNum, cellColNum)==currentPlayer) {
                        streak++;
                    }
                    cellRowNum++;
                    cellColNum--;
                    counter++;
                }
                if(streak==winThresh) {rightDiagWin = true;}
            }
        }
        return rightDiagWin;
    }
}

