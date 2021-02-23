package OXOExceptions;

public class CellAlreadyTakenException extends OXOMoveException{
    

    public CellAlreadyTakenException(int rowNumber, int columnNumber){
        super(rowNumber, columnNumber);
    }


    public String toString() {
        return "The cell: " + (char) (getRow() + 97) + (getColumn()+1) + 
                " has already been taken";
    }

}
