package trig.utility.geometry;

import trig.utility.math.vector.Cartesian;

/**
 * Similar to PathList, but contains ColorfulPaths
 * Note: does not have it's own colour
 * Created by marcos on 18/07/2014.
 * @see PathList
 * @see trig.utility.geometry.ColorfulPath
 */
public class ColorfulPathList<P extends ColorfulPath<V>, V extends Cartesian> extends PathList<P, V> implements Colorful
{
    /*
        Misc
     */
    @Override
    public ColorfulPathList<P, V> clone(){
        ColorfulPathList<P, V> newPathList = new ColorfulPathList<P, V>();
        newPathList.addAll(super.clone());
        return newPathList;
    }
    /**
     * Produces a new, colourless version of the pathLIST
     * @return similar to a deep copy of this object, without the colour
     */
    @Override
    public PathList<Path<V>, V> stripColor()
    {
        PathList<Path<V>, V> newPathList = new PathList<Path<V>, V>();
        for(P each : this)
        {
           newPathList.add(each.stripColor());
        }
        return newPathList;

    }
}
