package common;

public interface BoardGame {
    
    void setupBoard(int rows, int columns, int extras);
    int getRows();
    int getColumns();
    String getCell(int row, int col);
}
