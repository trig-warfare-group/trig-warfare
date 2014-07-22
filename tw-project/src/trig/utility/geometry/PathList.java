package trig.utility.geometry;

import trig.utility.math.vector.Cartesian;

/**
 * Custom path collection class designed to use my Cartesian library.
 * It is essentially a list of paths that implements trig.utility.geometry.Renderable
 * @see trig.utility.geometry.Path
 * Created by marcos on 17/07/2014.
 */
public class PathList<P extends Path<V>, V extends Cartesian> extends RenderableList<P> implements Renderable
{
    /*
        Misc
     */
    @Override
    public PathList<P, V> clone(){
        PathList<P, V> newPathList = new PathList<P, V>();
        for(P each : this)
        {
            /*
                it says it may be an unchecked cast, but I'm not even sure why it even needs to cast
                says add(P) cannot be applied to Path<V>
                isn't P castable to Path<V>??
             */
            newPathList.add((P) each.clone());
        }
        return newPathList;
    }
}
