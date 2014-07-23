package trig.utility.geometry;

import trig.utility.math.vector.Cartesian;

/**
 * Custom path collection class designed to use my Cartesian library.
 * It is essentially a list of paths that implements trig.utility.geometry.Renderable
 * @see trig.utility.geometry.Path
 * Created by marcos on 17/07/2014.
 */
public class PathList<T extends Path> extends RenderableList<T> implements Renderable
{
    /**
     * Produces a new, colourless version of the provided path list
     * @return a deep copy of the, without the colours
     */
    public static PathList<Path> stripColor(PathList<ColoredPath> colouredList){
        PathList<Path> plainList = new PathList<Path>();
        for(ColoredPath each : colouredList){
            plainList.add(each.stripColor());
        }
        return plainList;
    }
    /*
        Misc
     */
    @Override
    public PathList clone(){
        PathList newPathList = new PathList();
        for(Path each : this)
        {
            /*
                it says it may be an unchecked cast, but I'm not even sure why it even needs to cast
                says add(P) cannot be applied to Path<V>
                isn't P castable to Path<V>??
             */
            newPathList.add(each.clone());
        }
        return newPathList;
    }
}
