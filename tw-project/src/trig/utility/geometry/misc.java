package trig.utility.geometry;

/**
 * Created by marcos on 17/07/2014.
 */
public class misc
{

    /*Each world that can be drawn will pass forward an object containing data needed to draw it, including:
    *          a CustomPath as the basic polygon/image asset of the object
    *          The x and y coordinates to which to translate the RenderPaths
    *          The angle, in radians within the domain [-pi,pi] by which to rotate the points in the RenderPaths
    *          Possibly more data in the future:
    *              Possible a stat-rendering object, for things like text (e.g. name), something on the health or sheild, etc.
    *      Note that the path used for rendering etc is not explicitly the same as the data used for collisions, that will be a separate object
    * @see trig.utility.CustomPath
    */
    /*
            notes on custompath:
            needs a better name
            have a color object!
            Support subpaths (for colour object and ther data inheritance purposes)
            use Vectors for nodes/points (makes transforms such as rotation and translation easy?)
            May not be enclosed:
                (this could however be achieved: by referencing the first FloatCartesian as the last FloatCartesian, for example)
         */

        /*
            Note on stat-rendering:
                health etc should be a bar
         */
}
