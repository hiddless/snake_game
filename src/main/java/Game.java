import java.util.Random;

public class Game {

    public static final Random rand = new Random();

    private static int eaten = 0;
    private static int reverseCount = 0;
    private static int removeCount = 0;
    public static boolean win = false;
    public static boolean lose = false;
    public static boolean digitSikin = false;
    public static void toggleSkin() {
        digitSikin = !digitSikin;
        drawSnakeOnBoard();
    }

    public static int getEaten() {
        return eaten;
    }

    public static int getReverseCount() {
        return reverseCount;
    }

    public static int getRemoveCount() {
        return removeCount;
    }
    
    public static void decreaseReverseCount() {
        if (reverseCount > 0) {
            reverseCount--;
        }
    }

    public static void decreaseRemoveCount() {
        if (removeCount > 0) {
            removeCount--;
        }
    }

    public static void increaseEaten() {
        eaten++;
        if (eaten > 0 && eaten % 10 == 0) {
            Point p = Board.getRandomEmptyPoint();
            if (p != null) {
                Board.getCells()[p.getX()][p.getY()] = Board.BLOCK_CHAR;
            }
        }
    }
    public static boolean rollChance(double probability) {
        return rand.nextDouble() <= probability;
    }

    public static Point getNextPoint(Point head, String dir) {
        if (dir.equals("w")) {
            return new Point(head.getX() - 1, head.getY());
        } else if (dir.equals("a")) {
            return new Point(head.getX(), head.getY() - 1);
        } else if (dir.equals("s")) {
            return new Point(head.getX() + 1, head.getY());
        } else if (dir.equals("d")) {
            return new Point(head.getX(), head.getY() + 1);
        } else {
            return null;
        }
    }

    public static void drawSnakeOnBoard() {
        char[][] cells = Board.getCells();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j] == Board.SNAKE_BODY_CHAR ||
                        cells[i][j] == Board.SNAKE_HEAD_CHAR ||
                        Character.isDigit(cells[i][j])) {
                    cells[i][j] = 0;
                }
            }
        }
        Point[] a = Snake.getBody();
        for (int i = 0; i < Snake.getLength(); i++) {
            Point p = a[i];
            if (i == 0) {
                cells[p.getX()][p.getY()] = Board.SNAKE_HEAD_CHAR;
            } else {
                if (digitSikin) {
                    int n = ((i - 1) % 9) + 1;
                    cells[p.getX()][p.getY()] = (char)('0' + n);
                } else {
                    cells[p.getX()][p.getY()] = Board.SNAKE_BODY_CHAR;
                }
            }
        }
        Point[] b = Snake.getMirrorBody();
        for (int i = 0; i < Snake.getMirrorLength(); i++) {
            Point p = b[i];
            if (i == 0) {
                cells[p.getX()][p.getY()] = Board.SNAKE_HEAD_CHAR;
            } else {
                if (digitSikin) {
                    int n = ((i - 1) % 9) + 1;
                    cells[p.getX()][p.getY()] = (char)('0' + n);
                } else {
                    cells[p.getX()][p.getY()] = Board.SNAKE_BODY_CHAR;
                }
            }
        }
    }

    public static void drawFrogOnBoard(Point frog) {
        if (frog != null) {
            char[][] cells = Board.getCells();
            cells[frog.getX()][frog.getY()] = Board.FROG_CHAR;
        }
    }

    public static void drawFrogsOnBoard() {
        char[][] cells = Board.getCells();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j] == Board.FROG_CHAR) {
                    cells[i][j] = 0;
                }
            }
        }
        Point[] positions = Frogs.getPositions();
        for (int i = 0; i < positions.length; i++) {
            drawFrogOnBoard(positions[i]);
        }
    }
    public static boolean isWin() {
        return win;
    }
    public static boolean isLose() {
        return lose;
    }
    public static void checkWinLose() {
        if (!Board.hasEmptyCell()){
            win = true;
            return;
        }
        if (!Snake.hasAnyValidMove()){
            lose = true;
        }
    }
    
}
