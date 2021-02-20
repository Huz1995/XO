package OXOExceptions;

public class OutsideCellRangeException extends CellDoesNotExistException {
    private int position;
    private RowOrColumn type;

    public OutsideCellRangeException(int rowNumber, int columnNumber, char position,
                                        RowOrColumn type){
        super(rowNumber,columnNumber);
        this.position = position;
        this.type = type;
    }

    public String toString() {
        if(type == RowOrColumn.ROW) {
            return "The row label: " + "'" + (char) position + "'" + 
                    " is out of range. Please enter a letter that" + 
                    " is displayed on the y-axis in the board " + 
                    " (can be UPPERCASE)" ;
        }
        return "The column number: " + "'" + position + "'" + 
                " is out of range. Please enter a value that" + 
                " is displayed on the x-axis int the board" ;
    }

}