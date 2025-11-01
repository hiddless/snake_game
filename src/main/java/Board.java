
public class Board {

    public static final int BOARD_SIZE = 10;
    public static final char BLOCK_CHAR = '#';
    public static final char VISION_CHAR = '.';
    public static final char FROG_CHAR = 'a';
    public static final char SNAKE_HEAD_CHAR = '0';
    public static final char SNAKE_BODY_CHAR = '*';
    public static final char EMPTY_CHAR = 32;

    private static char[][] cells = new char[BOARD_SIZE][BOARD_SIZE];

    public static char[][] getCells() {
        return cells;
    }
    private static boolean inVision(int i, int j) {
        Point head = Snake.getBody()[0];
        int dx = Math.abs(i - head.getX());
        int dy = Math.abs(j - head.getY());
        return dx <= 2 && dy <= 2;
    }

    private static char getDisplayChar(int i, int j) {
        if (isOutside(i, j)) return BLOCK_CHAR;
        char raw = cells[i][j];
        if (raw == BLOCK_CHAR) return BLOCK_CHAR;
        if (!inVision(i, j)) return EMPTY_CHAR;
        if (raw == 0) return VISION_CHAR;
        return raw;
    }

    public static void print(int reverseCount, int removeCount, int eaten) {
        String header = String.format(" R: %-2d B: %-8d E: %2d", reverseCount, removeCount, eaten);
        System.out.println(header);
        for (int i = -1; i <= cells.length; i++) {
            for (int j = -1; j <= cells[0].length; j++) {
                System.out.printf("%2c", getDisplayChar(i, j));
            }
            System.out.println();
        }
    }

    public static boolean isEmpty(int x, int y) {
        return cells[x][y] == 0;
    }

    public static boolean isEmpty(Point p) {
        return isEmpty(p.getX(), p.getY());
    }

    public static boolean isFood(int x, int y) {
        return cells[x][y] == FROG_CHAR;
    }

    public static boolean isFood(Point p) {
        return isFood(p.getX(), p.getY());
    }

    public static boolean isBlock(int x, int y) {
        return cells[x][y] == BLOCK_CHAR;
    }

    public static boolean isBlock(Point p) {
        return isBlock(p.getX(), p.getY());
    }

    public static boolean isSnake(int x, int y) {
        return cells[x][y] == SNAKE_BODY_CHAR || Character.isDigit(cells[x][y]);
    }

    public static boolean isSnake(Point p) {
        return isSnake(p.getX(), p.getY());
    }

    public static boolean isOutside(int x, int y) {
        return x < 0 || x >= BOARD_SIZE || y < 0 || y >= BOARD_SIZE;
    }

    public static boolean isOutside(Point p) {
        return isOutside(p.getX(), p.getY());
    }

    public static boolean canMoveTo(int x, int y) {
        if (isOutside(x, y)) return false;
        if (isSnake(x, y)) return false;
        if (isBlock(x, y)) return Game.getRemoveCount() > 0; // blok varsa hakkın olmalı
        return true;    }

    public static boolean canMoveTo(Point p) {
        return canMoveTo(p.getX(), p.getY());
    }

    public static boolean hasEmptyCell() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (isEmpty(i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Point getRandomEmptyPoint() {
        java.util.ArrayList<Point> empties = new java.util.ArrayList<>();
        char[][] cells = getCells();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j] == 0) {
                    empties.add(new Point(i, j));
                }
            }
        }
        if (empties.isEmpty()) return null;
        return empties.get(Game.rand.nextInt(empties.size()));
    }
    public static boolean isFrog(int x, int y) {
        return !isOutside(x, y) && isFood(x, y);
    }

    public static boolean isFoodNearby(Point p) {
        return isFrog(p.getX() - 1, p.getY())
          || isFrog(p.getX(), p.getY() - 1) 
          || isFrog(p.getX() + 1, p.getY())
          || isFrog(p.getX(), p.getY() + 1);
    }

}
