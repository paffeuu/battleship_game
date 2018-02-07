package objects;

public class Field
{
    final public static int stageSize = 10;

    private int x;
    private int y;
    private Ship ship;

    private Field()
    {
        x = -1;
        y = -1;
    }

    public static Field create(int x, int y, Ship ship)
    {
        Field field = new Field();
        field.setX(x);
        field.setY(y);
        if (ship != null)
            field.ship = ship;
        if( field.x < 0 || field.y < 0)
            return null;
        else
            return field;
    }

    private void setX(int x) {
        if(x < stageSize)
            this.x = x;
    }

    private void setY(int y) {
        if (y < stageSize)
            this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Ship getShip() {
        return ship;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Field)
        {
            if(((Field) obj).getX() == x && ((Field) obj).getY() == y)
                return true;
        }
        return false;
    }
}
