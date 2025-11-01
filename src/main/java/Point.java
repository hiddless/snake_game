
public class Point {

    private int x, y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int distance(Point other) {
        return distance(other.getX(), other.getY());
    }

    public int distance(int x, int y) {
        return Math.max(Math.abs(this.x - x), Math.abs(this.y - y));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Point other = (Point) obj;
        return this.x == other.getX() && this.y == other.getY();
    }

    @Override
    public int hashCode() {
        return this.x ^ this.y;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", this.x, this.y);
    }

    @Override
    public Point clone() {
        return new Point(this.x, this.y);
    }

}