package OXOExceptions;

abstract public class CellDoesNotExistException extends OXOMoveException {


    public CellDoesNotExistException(int rowNumber, int columnNumber){
        super(rowNumber, columnNumber);
    }



}

