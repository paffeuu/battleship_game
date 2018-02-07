package objects;

import javafx.geometry.Orientation;
import main.Game;

import java.util.ArrayList;

public class Ship
{
    private ArrayList<Field> fields;
    private ArrayList<Field> restrictedFields;
    private Orientation orientation;
    private int length;
    private int startX;
    private int startY;

    public Ship()
    {
        boolean properlyCreated;
        final Orientation[] orientations = {Orientation.HORIZONTAL, Orientation.VERTICAL};
        double factorLength = 5.0;
        do
        {
            properlyCreated = true;
            orientation = orientations[(int)(Math.random()*2.0)];

            length = (int)(Math.random()*factorLength) + 2;
    //        System.out.println("length = " + length);
            startX = (int)(Math.random()*10.0);
    //        System.out.println("startX = " + startX);
            startY = (int)(Math.random()*10.0);
    //        System.out.println("startY = " + startY);

            calculateFields();
            calculateRestrictedFields();

            if (fields.size() != length)
                properlyCreated = false;
            else
                for (Field field : fields)
                {
                    if (Game.occupiedFields.contains(field))
                    {
                        properlyCreated = false;
                        break;
                    }
                    if (Game.restrictedFields.contains(field))
                    {
                        properlyCreated = false;
                        break;
                    }
                }
            if(factorLength > 0.0)
                factorLength -= 0.1;
        } while ((!properlyCreated));
    }

    private void calculateFields()
    {
        fields = new ArrayList<>(length);
        if(orientation.equals(Orientation.HORIZONTAL))
            for (int x = startX; x < startX + length; x++)
            {
                Field field = Field.create(x, startY, this);
                if (field != null)
                    fields.add(field);
            }
        else if(orientation.equals(Orientation.VERTICAL))
            for (int y = startY; y < startY + length; y++)
            {
                Field field = Field.create(startX, y, this);
                if (field != null)
                    fields.add(field);
            }
    }

    private void calculateRestrictedFields() {
        restrictedFields = new ArrayList<>();
        ArrayList<Field> candidates = new ArrayList<>(4 * length);
        for (Field field : fields)
        {
            int fieldX = field.getX();
            int fieldY = field.getY();
            candidates.add(Field.create(fieldX, fieldY - 1, this));
            candidates.add(Field.create(fieldX, fieldY + 1, this ));
            candidates.add(Field.create(fieldX - 1, fieldY, this));
            candidates.add(Field.create(fieldX + 1, fieldY, this));
        }

        for (Field field : fields)
        {
            if (candidates.contains(field))
                candidates.remove(field);
        }

        for (Field candidate : candidates)
        {
            if (candidate != null)
                restrictedFields.add(candidate);
        }
    }

    public void getStruck(Field field)
    {
        fields.remove(field);
        if (fields.isEmpty())
            Game.shipsCounter--;
    }

    public ArrayList<Field> getFields() {
        return fields;
    }

    public ArrayList<Field> getRestrictedFields() {
        return restrictedFields;
    }
}
