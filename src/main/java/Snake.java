import java.util.Arrays;

public class Snake {
    public static final double GROWTH_FACTOR = 1.5;

    private static Point[] body = {
            new Point(0, 4),
            new Point(0, 3),
            new Point(0, 2),
            new Point(0, 1),
            new Point(0, 0)
    };
    private static int length = body.length;
    private static Point[] mirrorBody = {
            new Point(9, 5),
            new Point(9, 6),
            new Point(9, 7),
            new Point(9, 8),
            new Point(9, 9)
    };
    private static int mirrorLength = mirrorBody.length;

    public static Point[] getBody() { return body; }
    public static int getLength() { return length; }
    public static Point[] getMirrorBody() { return mirrorBody; }
    public static int getMirrorLength() { return mirrorLength; }
    public static void move(String dir) {
        Point nextPoint = Game.getNextPoint(body[0], dir);
        if (nextPoint != null && Board.isSnake(nextPoint)) {
            System.out.println("u lost");
            System.exit(0);
            return;
        }
        if (nextPoint != null && Board.canMoveTo(nextPoint)) {
            handleFood(nextPoint);
            for (int i = length - 1; i >= 1; i--) {
                body[i] = body[i - 1];
            }
            body[0] = nextPoint;
            Game.drawSnakeOnBoard();
        }
    }

    public static void moveMirrorOpposite(String dir) {
        String opp;
        if ("w".equals(dir))      opp = "s";
        else if ("s".equals(dir)) opp = "w";
        else if ("a".equals(dir)) opp = "d";
        else if ("d".equals(dir)) opp = "a";
        else return;

        Point nextPoint = Game.getNextPoint(mirrorBody[0], opp);
        if (nextPoint != null && Board.isSnake(nextPoint)) {
            System.out.println("u lost");
            System.exit(0);
            return;
        }

        if (nextPoint != null && Board.isBlock(nextPoint) && Game.getRemoveCount() == 0) return;
        if (nextPoint != null && Board.isBlock(nextPoint) && Game.getRemoveCount() > 0) {
            Game.decreaseRemoveCount();
            Board.getCells()[nextPoint.getX()][nextPoint.getY()] = 0;
        }

        if (nextPoint != null && Board.canMoveTo(nextPoint)) {
            handleFoodMirror(nextPoint);
            for (int i = mirrorLength - 1; i >= 1; i--) {
                mirrorBody[i] = mirrorBody[i - 1];
            }
            mirrorBody[0] = nextPoint;
            Game.drawSnakeOnBoard();
        }
    }

    public static void reverse() {
        if (Game.getReverseCount() <= 0) return;
        for (int i = 0, j = length - 1; i < j; i++, j--) {
            Point t = body[i];
            body[i] = body[j];
            body[j] = t;
        }
        Game.decreaseReverseCount();
        Game.drawSnakeOnBoard();
    }
    public static void reverseMirror() {
        if (Game.getReverseCount() <= 0) return;
        for (int i = 0, j = mirrorLength - 1; i < j; i++, j--) {
            Point t = mirrorBody[i];
            mirrorBody[i] = mirrorBody[j];
            mirrorBody[j] = t;
        }
        Game.drawSnakeOnBoard();
    }
    private static void growSnake() {
        if (length == body.length) {
            body = Arrays.copyOf(body, (int)(body.length * GROWTH_FACTOR));
        }
        length++;
    }
    private static void growMirror() {
        if (mirrorLength == mirrorBody.length) {
            mirrorBody = Arrays.copyOf(mirrorBody, (int)(mirrorBody.length * GROWTH_FACTOR));
        }
        mirrorLength++;
    }
    private static void handleFood(Point nextPoint) {
        if (Board.isFood(nextPoint)) {
            if (Frogs.tryEscape(nextPoint)) return;
            Frogs.replaceFrog(nextPoint);
            growSnake();
            Game.increaseEaten();
        }
    }
    private static void handleFoodMirror(Point nextPoint) {
        if (Board.isFood(nextPoint)) {
            if (Frogs.tryEscape(nextPoint)) return;
            Frogs.replaceFrog(nextPoint);
            growMirror();
            Game.increaseEaten();
        }
    }
    public static boolean hasAnyValidMove() {
        Point head = body[0];
        String[] dirs = {"w", "a", "s", "d"};
        for (String d : dirs) {
            Point next = Game.getNextPoint(head, d);
            if (next == null) continue;
            if (!Board.isOutside(next) && !Board.isBlock(next) && !Board.isSnake(next)) {
                return true;
            }
        }
        return false;
    }
}
