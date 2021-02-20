package OXOExceptions;

public class CellDoesNotExistException extends OXOMoveException {


    public CellDoesNotExistException(int rowNumber, int columnNumber){
        super(rowNumber, columnNumber);
    }

    public String toString() {
        return "Row letter and Column number are out of bounds, please try again";
    }

}

