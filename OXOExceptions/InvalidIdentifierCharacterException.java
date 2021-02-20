package OXOExceptions;

public class InvalidIdentifierCharacterException extends InvalidIdentifierException {

    private char character;
    private RowOrColumn type;

    public InvalidIdentifierCharacterException(int rowNumber, int columnNumber,
                                                        char character, RowOrColumn type)
    {
        super(rowNumber,columnNumber);
        this.character = character;
        this.type = type;
    }

    
    public String toString() {
        if(type == RowOrColumn.ROW) {
            return "You entered an invalid first character: " + "'" + character + "'" + 
                    " for the row, it needs to be a valid" + 
                    " letter in the English alphabet (can be UPPERCASE)" ;
        }
        return "You entered an invalid second character: " + "'" + character + "'" + " for the column, " + 
                "please enter a valid single digit number " ;
    }
}
