package trig.utility.vector;

import trig.utility.Methods;

/**
 * A vector in the form of Radius, Angle, explicitly starting at the origin
 * Created by marcos on 11/07/2014.
 */
public abstract class PolarForm implements VectorForm{
    public PolarForm toPolar()
    {
        return this;
    }
    public static class Float extends PolarForm
    {
        public final float radius;
        public final float angle;

        /**
         * @param radius the distance from origin
         * @param angle  expressed in radians, between [-PI, -PI]
         */
        public Float(float radius, float angle)
        {
            this.radius = radius;
            this.angle = Methods.normalise(angle);
        }

        /**
         * Produces a CartesianForm equivalent of the given data
         * @param radius the distance from origin
         * @param angle  expressed in radians, between [-PI, -PI]
         * @return a vector from given data, in cartesian form
         */
        public static CartesianForm.Float toCartesian(float radius, float angle)
        {
            //make sure to normalise
            angle = Methods.normalise(angle);
            return new CartesianForm.Float
                    (
                            (float) (radius * Math.cos(angle)), //x
                            (float) (radius * Math.sin(angle)) //y
                    );
        }

        public CartesianForm.Float toCartesian()
        {
            return toCartesian(radius, angle);
        }
    }

    public static class Int extends PolarForm
    {
        public final float radius;
        public final float angle;

        /**
         * @param radius the distance from origin
         * @param angle  expressed in radians, between [-PI, -PI]
         */
        public Int(float radius, float angle)
        {
            this.radius = radius;
            this.angle = Methods.normalise(angle);
        }

        /**
         * Produces a CartesianForm equivalent of the given data
         * @param radius the distance from origin
         * @param angle  expressed in radians, between [-PI, -PI]
         * @return a vector from given data, in cartesian form
         */
        public static CartesianForm.Int toCartesian(float radius, float angle)
        {
            //make sure to normalise
            angle = Methods.normalise(angle);
            return new CartesianForm.Int
                    (
                            (int) Math.round( radius * Math.cos(angle) ) , //x
                            (int) Math.round( radius * Math.sin(angle) )//y
                    );
        }

        public CartesianForm.Int toCartesian()
        {
            return toCartesian(radius, angle);
        }
    }
}
