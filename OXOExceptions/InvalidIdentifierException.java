package OXOExceptions;

public class InvalidIdentifierException extends CellDoesNotExistException {

    public InvalidIdentifierException(int rowNumber, int columnNumber){
        super(rowNumber,columnNumber);
    }

    public String toString() {
        return "You have not entered a valid command";
    }
}
