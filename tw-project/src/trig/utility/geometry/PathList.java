package trig.utility.geometry;

/**
 * Custom path collection class designed to use my FloatCartesian library.
 * It is essentially a list of paths that implements trig.utility.geometry.Renderable
 * @see trig.utility.geometry.Path
 * Created by marcos on 17/07/2014.
 */
public class PathList<T extends Path> extends RenderableList<T>
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
            newPathList.add(each.clone());
        }
        return newPathList;
    }
}
