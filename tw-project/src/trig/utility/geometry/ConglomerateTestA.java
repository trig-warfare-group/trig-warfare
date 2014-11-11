//package trig.utility.geometry;
//
//import trig.utility.math.vector.FloatCartesian;
//
//import java.awt.*;
//import java.awt.geom.AffineTransform;
//import java.awt.geom.Path2D;
//
////TODO: TEST?
//
///**
// * A class for a conglomerate holding of miscellanious Renderables and treating the object as a container around the real boundaries of it's components
// * For example, entities with complex, non-connected shapes like explosion shrapnel or dust, which could be animated.
// * Note: The components that make up the conglomerate are new copies of those provided.
// * This is done to provide more efficient transformations and boundaryCalculation for most Renderables
// * by ensuring that the data remains unchanged between transformations called on this object.
// * probably/possibly useful for a static hitbox?
// * Note: whether or not it is actually efficient depends on how often functions are called:
// *  it makes individual calls to rotations (other than centered) and rendering slightly longer, but fixes the execution time of translations and all boundary checks such that they are approximately as long as a path with only 2 points?
// *  it's any way to treat a miscellaneous set of renderables like an image bitmap, at the least?
// *
// * Note: Would it be better to do the opposite, and update the bounds only on request for the data?
// *
// * One interesting observation I made whilst writing this is that the rectangle
// *
// * Note: We really should test that the way bounds is calculated in this class is consistent in accuracy and remains the same as the internal one, but we do not guarantee it.
// * Note: We should also test the actual efficiency, probably
// * Created by marcos on 22/07/2014.
// */
//public abstract class ConglomerateTestA implements Shape, Renderable
//{
//
//
//    protected FloatCartesian location;
//    protected FloatCartesian center;
//    protected int width;
//    protected int height;
//
//    protected Path boundsPath;
//
//    protected RenderableList<Renderable> components;
//
//    public ConglomerateTestA(RenderableList<Renderable> components){
//        this.components = components.clone();
//        Rectangle tempBounds = components.getBounds();
//        boundsPath = new Path();
//        boundsPath.add( new FloatCartesian( (float) tempBounds.getMinX(), (float) tempBounds.getMinY() ) );
//        boundsPath.add( new FloatCartesian( (float) tempBounds.getMaxX(), (float) tempBounds.getMaxY() ) );
//
//        //first, "crop": shift the components such that the origin is the center of the object.
//        //do not use this object's translate function here, we do not want to update the bounds.
//
//        float tempCenterX = (float) tempBounds.getCenterX();
//        float tempCenterY = (float) tempBounds.getCenterY();
//
//        center = new FloatCartesian((int) tempCenterX, (int) tempCenterY);
//
//        components.translate(-tempCenterX, -tempCenterY);
//
//
//
//        updateBounds(tempBounds);
//
//    }
//
//
//    /**
//     * Assumes that the components are pre-'cropped' and the bounds are up to date, internal use only!
//     * @param components
//     * @param boundsPath
//     */
//    protected ConglomerateTestA(RenderableList<Renderable> components, Path boundsPath){
//        this.components = components;
//        this.boundsPath = boundsPath;
//        updateBounds(boundsPath.getBounds());
//    }
//
//    /*
//        Helpers
//     */
//    protected void updateBounds(Rectangle bounds){
//        location = new FloatCartesian(bounds.x, bounds.y);
//        width = bounds.width;
//        height = bounds.height;
//    }
//    /**
//     * Updates the bounds
//     */
//    protected void updateBounds()
//    {
//        updateBounds(boundsPath.getBounds());
//    }
//
//
//    /*
//        Shape
//     */
//
//    @Override
//    public int getHeight()
//    {
//        return height;
//    }
//
//    @Override
//    public int getWidth()
//    {
//        return width;
//    }
//
//    @Override
//    public Dimension getSize()
//    {
//        return new Dimension(width, height);
//    }
//
//    @Override
//    public float getX()
//    {
//        return location.x;
//    }
//
//    @Override
//    public float getY()
//    {
//        return location.y;
//    }
//
//    @Override
//    public void setX(int x)
//    {
//        boundsPath.translate(location.x-x, 0);
//        updateBounds();
//
//        location.x = x;
//        center.x = location.x + width;
//
//    }
//
//    @Override
//    public void setY(int y)
//    {
//        boundsPath.translate(0, location.y-y);
//        updateBounds();
//
//        location.y = y;
//        center.y = location.y + height;
//    }
//
//    /**
//     * gets the coordinate of the top-left corner of the shape
//     *
//     * @return the coordinates of the top-left corner of the shape, in FloatCartesian vector form.
//     */
//    @Override
//    public FloatCartesian getLocation()
//    {
//        return location.clone();
//    }
//
//    /**
//     * Sets the coordinate of the location of the shape
//     *
//     * @param x the x-coordinate of the new top-left corner for the shape
//     * @param y the y-coordinate of the new top-left corner for the shape
//     */
//    @Override
//    public void setLocation(float x, float y)
//    {
//        boundsPath.translate(location.x-x, location.y-y);
//        updateBounds();
//
//        location.x = x;
//        location.y = y;
//
//        center.x = location.x + width;
//        center.y = location.y + height;
//    }
//
//    /**
//     * Sets the coordinate of the location of the shape
//     *
//     * @param TLCorner a vector in FloatCartesian form, representing the coordinates of the new top left corner of the shape
//     */
//    @Override
//    public void setLocation(FloatCartesian TLCorner)
//    {
//        setLocation(TLCorner.x, TLCorner.y);
//    }
//
//
//
//    /**
//     * Sets the coordinate of the location of the shape
//     *
//     * @param TLCorner a Point, representing the coordinates of the new top left corner of the shape
//     */
//    @Override
//    public void setLocation(Point TLCorner)
//    {
//        setLocation(TLCorner.x, TLCorner.y);
//    }
//
//    /**
//     * Rotates the object about it's own center, as defined by it's boundaries, rather than about the 0,0 default origin.
//     *
//     * @param theta the angle to rotate by, in radians.
//     */
//    @Override
//    public void centeredRotate(float theta)
//    {
//        components.rotate(theta);
//
//        boundsPath.rotate(theta);
//        updateBounds();
//    }
//
//    /*
//        Renderable
//     */
//
//    /**
//     * Returns a Path2D.Float representation of this object, which can be more easily drawn using Graphics2D
//     */
//    @Override
//    public Path2D getPath2D()
//    {
//        return components.getPath2D();
//    }
//
//    /**
//     * Gets a Rectangle representing a box that completely contains the shape rendered by the object.
//     * Note: in this case, "contains" just means that no portion of the shape lies outside of the box, there may be portions on the exact edge,
//     *  these portions may technically render beyond the container, depending on the style of stroke used to render them.
//     * Note: this function guarantees that the returned Rectangles contains the shape, but not that it is the smallest possible containing box.
//     * @return a Rectangle that completely contains the shape rendered by the object.
//     */
//    @Override
//    public Rectangle getBounds()
//    {
//        return new Rectangle((int)location.x, (int)location.y, width, height);
//    }
//
//    /**
//     * draws the object using 0,0 as the origin.
//     *
//     * @param g the canvas to draw on
//     */
//    @Override
//    public void render(Graphics2D g)
//    {
//        AffineTransform aT = new AffineTransform();
//        aT.translate(center.x, center.y);
//        components.render(g, aT);
//    }
//
//    /**
//     * Draws the object, with the rendered object transformed using the specified AffineTransform
//     *
//     * @param g  the canvas to draw on
//     * @param aT an affineTransform to apply to the rendered objected
//     * @see trig.utility.math.vector.FloatCartesian
//     */
//    @Override
//    public void render(Graphics2D g, AffineTransform aT)
//    {
//        aT.translate(center.x, center.y);
//        components.render(g, aT);
//    }
//
//    @Override
//    public void translate(FloatCartesian )
//    {
//        location.translate((int) tX, (int) tY);
//        center.translate((int) tX, (int) tY);
//
//        boundsPath.translate(tX, tY);
//        updateBounds();
//
//    }
//
//    @Override
//    public void translate(FloatCartesian tVector)
//    {
//        translate(tVector.x, tVector.y);
//    }
//
//    /**
//     * Rotates an object about the origin 0,0.
//     * Note: this is likely to move the position of the object.
//     * To render an object about its center, #rotateAbout(float, float, float) with the coordinates of the center of this object,
//     * or if using a class that implements trig.utility.geometry.Shape: trig.utility.geometry.Shape#centeredRotate(float)
//     * @see #rotateAbout(float, float, float)
//     * @see trig.utility.geometry.Shape#centeredRotate(float)
//     * @param theta an angle to rotate by, expressed in radians.
//     */
//    @Override
//    public void rotate(float theta)
//    {
//        rotateAbout(theta, (float) location.x, (float) location.y);
//    }
//
//    /**
//     * Rotates the object about the specified center, instead of it's normal origin
//     * Note: this is likely to move the position of the object.
//     * @param theta the angle to rotate by, in radians.
//     * @param cX    the x-coordinate of the center to rotate about.
//     * @param cY    the y-coordinate of the center to rotate about.
//     */
//    @Override
//    public void rotateAbout(float theta, float cX, float cY)
//    {
//        location.translate(cX, cY);
//        location.rotate(theta);
//        location.translate(-cX, -cY);
//
//        components.rotate(theta);
//
//        boundsPath.rotate(theta);
//        updateBounds();
//    }
//
//    /**
//     * Rotates the object about the specified center, instead of it's normal origin
//     *
//     * @param theta   the angle to rotate by, in radians.
//     * @param tVector the x and y coordinates of the rotation-center
//     */
//    @Override
//    public void rotateAbout(float theta, FloatCartesian tVector)
//    {
//        rotateAbout(theta, tVector.x, tVector.y);
//    }
//
//    /**
//     * Produces a deep-copy of the object.
//     *
//     * @return a deep-copy of the object and it's components.
//     */
//    @Override
//    public Renderable clone()
//    {
//        return null;
//        //new ConglomerateTestA(components, boundsPath);
//    }
//
//
//}
