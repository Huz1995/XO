package OXOExceptions;

public class CellAlreadyTakenException extends OXOMoveException{
    

    public CellAlreadyTakenException(int rowNumber, int columnNumber){
        super(rowNumber, columnNumber);
    }


    public String toString() {
        return "This cell has already been taken";
    }

}
