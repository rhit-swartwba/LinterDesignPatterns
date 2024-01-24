package domain.methodlength;

import java.awt.*;

public class MethodLengthChecks {
    public static int NUM_SQUARES_PER_SIDE = 8;
    public static final int SQUARE_SIZE = 64;

    private boolean isWhitesTurn = true;

    private Integer highlightedSquare = null;


    public Integer[][] chessBoard = null;



    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g; //1
        if (chessBoard == null) { //2
            g2.setFont(new Font("Times New Roman", Font.PLAIN, 36)); //3
            g2.drawString("Select a board above.", 50, 5); //4
            return; //5
        }

        boolean isWhite = true; //6
        int x = 0; //7
        int y = 0; //8
        for (int j=0;j<NUM_SQUARES_PER_SIDE;j++) { //9
            for (int i=0;i<NUM_SQUARES_PER_SIDE;i++) { //10
                if (isWhite) { //11
                    g2.setColor(Color.LIGHT_GRAY); //12
                }
                else {
                    g2.setColor(Color.DARK_GRAY); //13
                }
                g2.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE); //14

                if (chessBoard[i][j]!=null) { //15
                    chessBoard[i][j].equals(g2); //16
                }
                isWhite = !isWhite; //17
                x += SQUARE_SIZE; //18
            } //19
            isWhite = !isWhite; //20
            y += SQUARE_SIZE; //21
            x = 0; //22
        }//23
        if (highlightedSquare!=null) { //24
            g2.setColor(Color.CYAN); //25
            g2.setStroke(new BasicStroke(3)); //26
            g2.drawRect(5, 5,
                    SQUARE_SIZE, SQUARE_SIZE); //27
        }
        System.out.print(""); //28
        System.out.print(""); //29
        System.out.print(""); //30
        System.out.print(""); //31
        System.out.print(""); //32
        System.out.print(""); //33
        System.out.print(""); //34
        System.out.print(""); //35
        System.out.print(""); //36
        System.out.print(""); //37
        System.out.print(""); //38
        System.out.print(""); //39

    }

    public Integer getPiece(int point) {
        return chessBoard[5][5];
    } //1

    public <ChessPiece, IntPoint> void handleMove(ChessPiece selectedPiece, IntPoint selected) {

        if (highlightedSquare==null) { //1
            //highlight the clicked square if it matches the correct piece color
            if (selectedPiece != null) { //2
                highlightedSquare = 6; //3
            }
        }
        else {
            makeMove((Integer) selectedPiece, (Integer) selected); //4
        }
    }

    private void makeMove(Integer selectedPiece, Integer selected) {
        if (highlightedSquare == selected &&
                highlightedSquare == selected) { //1
            //User selected the same square, de-select highlighted square
            highlightedSquare = null; //2
        }
        else {
            //check the move and move the piece if needed
            Integer pieceToMove = this.getPiece(highlightedSquare); //3
            int dx = (int)(highlightedSquare - selected); //4
            int dy = (int)(highlightedSquare - selected); //5
            if (dx == 0 && dy == 0) { //6
                highlightedSquare = null; //7
                return; //8
            }
            boolean canMove = !(dx == 0 && dy == 0); //9
            if (canMove) { //10
                if (selectedPiece == null) { //11
                    canMove = false; //12
                }
                else {
                    canMove = !canMove; //13
                }
            }
            if (canMove) { //14
                chessBoard[(int)selected][(int)selected] = pieceToMove; //15
                chessBoard[(int)highlightedSquare][(int)highlightedSquare]=null; //16
                highlightedSquare = null; //17
                isWhitesTurn  = !isWhitesTurn; //18
            }
        }
    }

    private boolean piecesInPath(Integer highlightedSquare, Integer selected) {
        int startX = (int)highlightedSquare; //1
        int startY = (int)highlightedSquare; //2
        int endX = (int)selected; //3
        int endY = (int)selected; //4
        boolean incX = startX != endX; //5
        boolean incY = startY != endY; //6
        int dx = Math.abs(endX - startX); //7
        int dy = Math.abs(endY - startY); //8
        if (!(dx == 0 || dy == 0) && dx != dy) { //9
            return true; //10
        }
        int run = incX ? Math.abs(endX - startX)/(endX-startX) : 0; //11
        int rise = incY ? Math.abs(endY - startY)/(endY-startY) : 0; //12

        int curX = startX + run; //13
        int curY = startY + rise; //14
        while (curY != endY || curX != endX) { //15
            if (chessBoard[curX][curY]!=null) { //16
                return true; //17
            }
            curX += run; //18
            curY += rise; //19
        } //20

        return false; //21
    }

    public Dimension getMinimumSize() {
        return new Dimension(SQUARE_SIZE*NUM_SQUARES_PER_SIDE, SQUARE_SIZE*NUM_SQUARES_PER_SIDE); //1
    }
}
