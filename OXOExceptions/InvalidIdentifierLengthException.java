package OXOExceptions;

public class InvalidIdentifierLengthException extends InvalidIdentifierException {
    
    private int length;

    public InvalidIdentifierLengthException(int rowNumber, int columnNumber, int length) {
        super(rowNumber,columnNumber);
        this.length = length;
    }

    public String toString() {
        return "You entered a " + length + " character long command."
                + " Please just enter 2 characters";
    }


}
