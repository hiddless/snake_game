
public class Frogs {

    public static final int FROG_COUNT = 3;

    private static Point[] positions = initializeFrogs();
    public static boolean tryEscape(Point aim) {
        if (!Board.isFood(aim)) return false;
        if (Game.rollChance(0.10)) {
            replaceFrog(aim);
            System.out.println("frog escaped");
            return true;
        }
        return false;
    }

    public static Point[] getPositions() {
        return positions;
    }

    public static void replaceFrog(Point frog) {
        for (int i = 0; i < positions.length; i++) {
            if (positions[i] != null && positions[i].equals(frog)) {
                positions[i] = Board.getRandomEmptyPoint();
                break;
            }
        }
        Game.drawFrogsOnBoard();
    }

    private static Point[] initializeFrogs() {
        Point[] positions = new Point[FROG_COUNT];
        for (int i = 0; i < FROG_COUNT; i++) {
            Point frog = Board.getRandomEmptyPoint();
            positions[i] = frog;
            Game.drawFrogOnBoard(frog);
        }
        return positions;
    }

    public static void tick() {
        for (int i = 0; i < positions.length; i++) {
            Point cur = positions[i];
            if (cur == null) continue;

            if (Game.rollChance(0.40)) {
                Point[] adj = new Point[] {
                        new Point(cur.getX() - 1, cur.getY()),
                        new Point(cur.getX() + 1, cur.getY()),
                        new Point(cur.getX(), cur.getY() - 1),
                        new Point(cur.getX(), cur.getY() + 1)
                };

                java.util.ArrayList<Point> opts = new java.util.ArrayList<>();
                for (Point p : adj) {
                    if (!Board.isOutside(p) && Board.isEmpty(p)) {
                        opts.add(p);
                    }
                }

                if (!opts.isEmpty()) {
                    positions[i] = opts.get(Game.rand.nextInt(opts.size()));
                }
            }
        }
        Game.drawFrogsOnBoard();
    }
}