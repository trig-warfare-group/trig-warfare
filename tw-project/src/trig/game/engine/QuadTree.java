package trig.game.engine;


import trig.game.entity.Entity;
import trig.game.entity.SEntity;
import trig.utility.SDimension;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;

/**
 * A QuadTree used to divide up two-dimensional space, to allow for a more optimized approach to collision-detection.
 * The Tree its self does not handle the collision-detection, it simply organizes the Entities in given nodes,
 * so that it may be checked by the <b>super</b>.
 *
 * entitiyList = <Two-dimensional coordinate>
 * QuadTree qt = new QuadTree(entitiyList);
 *
 *
 * collisionList = qt.calculateCollision();
 *
 * Created by brody on 20/07/14.
 */
public class QuadTree
{
    QTNode root;
    SDimension space;
    ArrayList<SEntity> list;
    protected final static short MAX_NODE_DEPTH = 5;

    public QuadTree(SDimension space, ArrayList<SEntity> list)
    {
        this.space = (SDimension) space;
        this.list = list;
        root = new QTNode(space, list.toArray(new SEntity[list.size()]));
    }
    /**
     * Clear the tree, and prepare for re-growing it.
     */
    public void clear()
    {
        root = new QTNode(space, list.toArray(new SEntity[list.size()]));
    }

    /**
     * Takes the current settings, and creates a tree based on them.
     */
    public void plantSeed()
    {
        root.sprout();
    }

    //public void
    //SEntity [] getPossibleCollisions()
    //QTNode [] getLeaves();

    /**
     *  Used to display the QuadTree dividing the space up into nodes.
     */
    public void displayTree(Graphics2D g)
    {
        //For each node, ask it to draw its self.
        Color colorMemory = g.getColor();
        SDimension d;

        g.setColor(Color.YELLOW);

        for(SEntity e : list)
        {
            Point p;
            Dimension dim;
            d = e.getCollisionRegion();
            p = d.getPoint();
            dim = d.getDimension();

            g.drawRect(p.x, p.y, dim.width, dim.height);

        }
        g.setStroke(new BasicStroke(15));
        g.setColor(Color.DARK_GRAY);
        //Display all-nodes.
        root.displayNodes(g);



        g.setColor(colorMemory);

    }
    /*
       TODO - Create small boxes representing each node,

    */
    public void displayStructure(Graphics2D g)
    {

    }

    private class NodeBox
    {
        public QTNode n;

          //TODO Draw box, with the depth, and connection to sub-nodes.
        public void displayNodeBox(Graphics2D g)
        {

        }
    }
}
