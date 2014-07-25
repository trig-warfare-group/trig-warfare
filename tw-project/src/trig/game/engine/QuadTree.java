package trig.game.engine;

import trig.game.entity.Collidable;
import trig.game.entity.Entity;

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
 * Note on possible optimisations: returning the getLocation list slightly differently to allowing of knowing whether or not the overlap occurs
 */

public class QuadTree
{
    /*
        note that this QuadTree is mostly just a wrapper for the small amount of data and methods that not every node needs to have
        and some data-hiding
    */

    public static int MAX_OBJECTS = 3;//2;
    public static int MAX_DEPTH = 4;

    protected QTNode root;

    public QuadTree(float width, float height, float[] corner)
    {
        root = new QTNode(0, width, height, corner);
    }

    /**
     * processes a list of entities and returns the possible collisions for each of them, then clears itself
     */
    public ArrayList<ArrayList<Collidable>> processList(ArrayList<Collidable> guests){
        ArrayList<ArrayList<Collidable>> result = new ArrayList<ArrayList<Collidable>>(guests.size());

        ArrayList<Collidable> subResult;

        //first insert them all
        for(Collidable each : guests)
        {
            root.insert(each);
        }


        //now get the lists of objects nearby each
        for(Collidable each : guests)
        {
            subResult = root.neighbours(each);
            subResult.remove(each);
            result.add(subResult);
        }

        root.clear();
        return result;
    }

    public void render(Graphics2D g)
    {
        root.render(g);
    }

    /**
     * The real grunt of the QuadTree
     */
    protected static class QTNode
    {
        //enum
        private NodeType type = NodeType.LEAF;

        //current depth
        private int depth;

        //entities contained at this node
        ArrayList<Collidable> guests; //kind of want a more generic variable name

        //child nodes, size 4
        private QTNode[] children;

        /*
        using integers and rounding to avoid entities not indexing properly would probably be more costly?
        (is there actually a risk of them not indexing when using ints?)
        */
        private float width, height;

        //top-left-corner coord, size 2, //center coord, size 2
        private float[] corner, center;

        public QTNode(int depth, float width, float height, float[] corner)
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

            this.guests = new ArrayList<Collidable>();

            this.children = new QTNode[4];
        }

        /**
         * Deletes all child-nodes and objects
         */
        public void clear(){
            //java's garbage collector makes this easy
            guests.clear();
            children = new QTNode[4];

            //gotta reset the enum duh
            type = NodeType.LEAF;
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
            children[0] = new QTNode
                    (
                            newDepth, //depth
                            halfWidth, //width
                            halfHeight, //height
                            corner //corner
                    );

        /*
            top-right
         */
            children[1] = new QTNode
                    (
                            newDepth, //depth
                            halfWidth, //width
                            halfHeight, //height
                            new float[]{center[0], corner[1]} //corner
                    );

        /*
            bottom-left
         */
            children[2] = new QTNode
                    (
                            newDepth, //depth
                            halfWidth, //width
                            halfHeight, //height
                            new float[]{corner[0], center[1]} //corner
                    );

        /*
            bottom-right
         */
            children[3] = new QTNode
                    (
                            newDepth, //depth
                            halfWidth, //width
                            halfHeight, //height
                            new float[]{center[0], center[1]} //corner
                    );

            type = NodeType.BRANCH;
        }

        /**
         * Determines which child nodes/quadrants the provided entity is fully or partially inside of
         * @param subject an entity to determine the location of
         * @return an array integers, with up to 4 elements each of which are the index of a child node the entity is inside
         */
        public int[] getLocation(Collidable subject)
        {
            boolean left, right, above, below;

            Rectangle bounds = subject.getHitbox().getBounds();
            left = ( bounds.x < center[0] ); //some part is left of center
            right = ( bounds.x + bounds.width > center[0] ); //some part is right of center
            above = ( bounds.y < center[1] ); //some part is above center
            below = ( bounds.x + bounds.height > center[1] ); //some part is below center

            //determine the size of the return array,
            int sections = 1; //can be either 1 2 or 4 depending on the number of overlaps
            if(left && right)
            {
                sections = sections * 2;
            }
            if(above && below){
                sections = sections * 2;
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
         * Note: this modifies the provided locations list, adding the location index(s) to it
         * @return a set of int[] that can be used as a path to get all the guests a subject may collide with.
         */
        public void insert(Collidable subject)
        {
            if (type == NodeType.BRANCH)
            {
                int[] location = getLocation(subject);

                if(location.length == 1)
                {
                     children[location[0]].insert(subject);
                    return;
                }
            }
            //if it were added to a child already, this statement wouldn't be reached
            guests.add(subject);

            int guestCount = guests.size();//store the old size or else the loop won't got over all of them
            if (guestCount > QuadTree.MAX_OBJECTS && depth < QuadTree.MAX_DEPTH)
            {
                split();
                for(int i = 0; i < guestCount; i++) //can't have two enhanced for loops occuring at the same time or in a nested fashion
                {
                    Collidable each = guests.get(0); //when removing an entity, it resizes it!
                    int[] location = getLocation(each);
                    if (location.length == 1)
                    {
                        guests.remove(each);
                        children[location[0]].insert(each);
                    }
                }
                return; //debug point
            }
        }

        /**
         * Produces a list of all guests the subject entity might collide with
         * (note: the return list includes the subject)
         *
         * @return
         */
        public ArrayList<Collidable> neighbours(Collidable subject)
        {

            ArrayList<Collidable> result = new ArrayList<Collidable>();

            result.addAll(guests);
            int[] location = getLocation(subject);
            if (type == NodeType.BRANCH)
            {
                for (int each : location)
                {
                    //THERE IS A BUG HERE
                    result.addAll(children[each].neighbours(subject));
                }

                ///don't collide with self!
            }

            return result;
        }

        /**
         * Temporary debug rendering
         */
        public void render(Graphics2D g)
        {
            g.drawRect(Math.round(corner[0]), Math.round(corner[1]), Math.round(width), Math.round(height));

            if(type == NodeType.BRANCH)
            {
                for (QTNode each : children)
                {
                    each.render(g);
                }
            }
        }

        public static enum NodeType
        {
            BRANCH,
            LEAF
        }
    }
}