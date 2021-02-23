package OXOExceptions;

abstract public class InvalidIdentifierException extends CellDoesNotExistException {

    public InvalidIdentifierException(int rowNumber, int columnNumber){
        super(rowNumber,columnNumber);
    }

 
}
