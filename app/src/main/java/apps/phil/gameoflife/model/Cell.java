package apps.phil.gameoflife.model;

public class Cell {

    private int x;
    private int y;
    private boolean isAlive;
    private int size;

    public Cell(int x, int y, boolean isAlive, int size) {
        this.x = x;
        this.y = y;
        this.isAlive = isAlive;
        this.size = size;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public boolean equals(Object obj) {
        // self check
        if(this == obj) {
            return true;
        }
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof Cell)) {
            return false;
        }
        Cell otherCell = (Cell) obj;
        return otherCell.getSize() == this.getSize()
                && otherCell.isAlive() == this.isAlive()
                && otherCell.getX() == this.getX()
                && otherCell.getY() == this.getY();
    }

    @Override
    public int hashCode() {
        int result = 2;
        int hashValue = 0;
        hashValue += y;
        hashValue += x;
        hashValue += size;
        hashValue += (isAlive ? 0 : 1);
        return result * 37 + hashValue;
    }
}