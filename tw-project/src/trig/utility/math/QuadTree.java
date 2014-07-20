package trig.utility.math;

import trig.game.entity.interfaces.Entity;

import java.awt.*;
import java.util.ArrayList;

/**
 * A node-tree class that can be used to estimate collisions, where each node is an instance of this class
 * Has the following data:
 *  maximum number of objects a node can contain before it needs to split (constant)
 *  maximum number of levels the tree is allowed to contain (constant)
 *  the depth-level of the node
 *  a list of objects the node contains
 *  information about the region the node represents:
 *      height
 *      width
 *      corner (a small array[2] for the top-left corner of the region, used for making sub nodes)
 *      center (a small array[2] for the center of the region, used for determining which subnode an object would belong to, and possibly for creating the subnodes)
 *
 *
 *
 * Involves 5 basic operations:
 *  splitting the node into subnodes
 *  adding objects to the tree (and determining where it belongs)
 *  finding out which direct subnodes of a particular node an object would be to based on it's coordinates:
 *      this will be an array of integers, in case the object overlaps multiple nodes.
 *  receiving the objects within the tree that are in the same node as a particular object and therefore likely to collide.
 *  clearing all objects from a node and its subnodes. (In Java this is redundant due to the garbage collector, we'll either just clear the top one or create a new one every frame
 *
 * For my implimentation I am thinking of (BUT HAVEN'T YET) doing the following customisations on the basic algorithm:
 *  using a temporary wrapper object to store each object's node location in the tree, rather than re-searching each time:
 *      including which sub-nodes the object overlaps if this occurs
 *      it could possibly be created by the tree and returned?
 * Created by marcos on 19/07/2014.
 */
public class QuadTree
{
    /**
     * Temporary debug rendering
     */
    public void render(Graphics2D g)
    {
        g.drawRect(Math.round(corner[0]), Math.round(corner[1]), Math.round(width), Math.round(height));
        if(children[0] != null)
        {
            for (QuadTree each : children)
            {
                each.render(g);
            }
        }
    }
    /*
        the value of these constants were semi-randomly chosen,
        we should make a real decision  on them at some point.
     */
    public static int MAX_OBJECTS = 1;
    public static int MAX_DEPTH = 4;

    //current depth
    private int depth;

    //entities contained at this node
    ArrayList<Entity> guests; //kind of want a more generic variable name

    //child nodes, size 4
    private QuadTree[] children;

    /*
    using integers and rounding to avoid entities not indexing properly would probably be more costly?
    (is there actually a risk of them not indexing when using ints?)
    */
    private float width, height;

    //top-left-corner coord, size 2, //center coord, size 2
    private float[] corner, center;

    public QuadTree(int depth, float width, float height, float[] corner)
    {
        this.depth = depth;

        this.width = width;
        this.height = height;
        this.corner = corner;

        this.center = new float[]
                {
                        (corner[0]+width/2),
                        (corner[1]+height/2)
                };

        this.guests = new ArrayList<Entity>();

        this.children = new QuadTree[4];
    }

    public QuadTree(float width, float height, float[] corner)
    {
        this(0, width, height, corner);
    }

    /**
     * Deletes all child-nodes and objects
     */
    public void clear(){
        //java's garbage collector makes this easy
        guests.clear();
        children = new QuadTree[4];
    }
    /**
     * Instantiates the child nodes.
     */
    public void split()
    {
        int newDepth = depth+1;
        float halfWidth = width/2;
        float halfHeight = height/2;

        /*
            top-left
         */
        children[0] = new QuadTree
        (
                newDepth, //depth
                halfWidth, //width
                halfHeight, //height
                corner //corner
        );

        /*
            top-right
         */
        children[1] = new QuadTree
        (
                newDepth, //depth
                halfWidth, //width
                halfHeight, //height
                new float[]{center[0], corner[1]} //corner
        );

        /*
            bottom-left
         */
        children[2] = new QuadTree
        (
                newDepth, //depth
                halfWidth, //width
                halfHeight, //height
                new float[]{corner[0], center[1]} //corner
        );

        /*
            bottom-right
         */
        children[3] = new QuadTree
        (
                newDepth, //depth
                halfWidth, //width
                halfHeight, //height
                new float[]{center[0], center[1]} //corner
        );
    }

    /**
     * Determines which child nodes/quadrants the provided entity is fully or partially inside of
     * @param subject an entity to determine the index of
     * @return an array integers, with up to 4 elements each of which are the index of a child node the entity is inside
     */
    public int[] getIndex(Entity subject)
    {
        boolean left, right, above, below;
        left = ((float) (subject.getX()) < center[0]); //some part is left of center
        right = ((float) (subject.getX()+subject.getHitSize()) > center[0]); //some part is right of center
        above = ((float) (subject.getY()) < center[1]); //some part is above center
        below = ((float) (subject.getY()+subject.getHitSize()) > center[1]); //some part is below center

        //determine the size of the return array,
        int sections = 1; //can be either 1 2 or 4 depending on the number of overlaps
        if(left && right)
        {
            sections = sections*2;
        }
        if(above && below){
            sections = sections*2;
        };

        int[] result = new int[sections];

        int i = 0; //track the last index used;
        if(above && left)
        {
            result[i++] = 0;
        }
        if(above && right)
        {
            result[i++] = 1;
        }
        if(below && left)
        {
            result[i++] = 2;
        }
        if(below && right)
        {
            result[i++] = 3;
        }

        return result;
    }


    /**
     * Places the object within the node, or it's sub nodes, depending on where it fits
     * @param subject an entity to place within the tree
     * Note: may make this return some data about where the entity is in the tree, rather than having to re-do the search, dunno yet
     */
    public void insert(Entity subject)
    {
        if (children[0] != null)
        {
            int[] index = getIndex(subject);
            if(index.length == 1)
            {
                children[index[0]].insert(subject);
                return;
            }
        }
        //if it were added to a child already, this statement wouldn't be reached
        guests.add(subject);
        if (guests.size() > QuadTree.MAX_OBJECTS && depth < QuadTree.MAX_DEPTH)
        {
            split();
            int[] index = getIndex(subject);
            if(index.length == 1)
            {
                guests.remove(subject);
                children[index[0]].insert(subject);
                return;
            }
        }
    }

    /**
     * Produces a list of all guests the subject entity might collide with
     * (note: the return list includes the subject)
     *
     * @return
     */
    public ArrayList<Entity> guestsNear(Entity subject)
    {

        ArrayList<Entity> result = new ArrayList<Entity>();
        int[] index = getIndex(subject);
        if(index.length > 1 || children[0] == null)
        {
            result.addAll(guests);
        }

        if (children[0] != null)
        {
            for (int each : index)
            {
                //THERE IS A BUG HERE
                result.addAll(children[each].guestsNear(subject));
            }

            ///don't collide with self!
        }

        return result;
    }
}