package trig.game.entity;

import trig.utility.geometry.Polygon;
import trig.utility.math.vector.FloatCartesian;
import trig.utility.math.vector.IntCartesian;

/**
 * Base class for in game entities, should we be directly implimenting some of the aspects of entity here, or delete this class and do it repeatedly in others? IMO: here, less code
 * Created by marcos on 10/07/14.
 */
public abstract class SEntity implements Entity
{
    /**
     * Possibly a temporary method, but both Bullets and Ships use it for now, so yeh.
     * @return a triangular Polygon.
     */
    public static Polygon constructGenericTriangle(float size){
        float halfSize = size/(float) 2.0;

        //absolute of the angle to rotate by against point A, to get points B and C
        //equilateral and isosceles triangle can be produced using +- this one angle, I think..
        //float rotationBase = (float)1/2* (float) Math.PI;
        float rotationAngle =  ( ( (float) 5 / 7 ) * (float) Math.PI);

        Polygon triangle = new Polygon();

        FloatCartesian vA = new FloatCartesian(size, 0);

        FloatCartesian vB = vA.clone();
        vB.rotate(rotationAngle);

        FloatCartesian vC = vA.clone();
        vC.rotate(-rotationAngle);

        triangle.add(vA);
        triangle.add(vB);
        triangle.add(vC);

        //translate it to the correct position;
        triangle.translate(halfSize, halfSize);

        return triangle;
    }

    protected int x;
    protected int y;


    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public IntCartesian getLocation()
    {
        return new IntCartesian(x,y);
    }

    SEntity(/*int id,*/ int x, int y){
        this.x = x;
        this.y = y;
    }
}
