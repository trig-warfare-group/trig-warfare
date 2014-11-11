package trig.game.world;

import trig.utility.geometry.Polygon;
import trig.utility.math.vector.FloatCartesian;

import java.awt.*;

/**
 * Base class for in game entities, should we be directly implimenting some of the aspects of world here, or delete this class and do it repeatedly in others? IMO: here, less code
 * Created by marcos on 10/07/14.
 */
@Deprecated
public abstract class BasicWorldObject implements WorldObject
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

        return triangle;
    }

//    protected float x;
//    protected float y;

    private FloatCartesian location;

//    @Override
//    public float getX() {
//        return x;
//    }
//
//    @Override
//    public float getY() {
//        return y;
//    }

    @Override
    public FloatCartesian getLocation()
    {
        return location.clone();
    }

//    SWorldObject(float x, float y){
//        this.x = x;
//        this.y = y;
//    }


    protected void setLocation(FloatCartesian location)
    {
        this.location = location.clone();
    }

    BasicWorldObject(FloatCartesian location)
    {
        this.location = location.clone();
    }
}
