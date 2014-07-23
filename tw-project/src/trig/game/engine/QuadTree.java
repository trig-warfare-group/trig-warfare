package trig.game.engine;


import trig.game.entity.SEntity;
import trig.utility.Constants;
import trig.utility.SRectangle;

import java.awt.*;
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
    SRectangle space;
    ArrayList<SEntity> list;
    protected final static short MAX_NODE_DEPTH = 5;

    public QuadTree(SRectangle space, ArrayList<SEntity> list)
    {
        this.space = (SRectangle) space;
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


    /**
     *  Used to display the QuadTree dividing the space up into nodes.
     */
    public void displayTree(Graphics2D g)
    {
        //For each node, ask it to draw its self.
        Color colorMemory = g.getColor();
        SRectangle d;

        g.setStroke(new BasicStroke(0.1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
        g.setColor(Color.RED);

        for(SEntity e : list)
        {
            SRectangle col = e.getCollisionRegion();

            g.drawRect(col.x, col.y, col.width, col.height);

        }
        g.setStroke(new BasicStroke(0.1f));

        g.setColor(new Color(86, 43, 5));

        //Display all-nodes.
        root.displayNodes(g);

        g.setColor(colorMemory);

    }

    /**
     * Displays a structured-diagram of the QuadTree.
     */
    public void displayStructure(Graphics2D g)
    {
        Color colorMemory = g.getColor();
        g.setColor(Color.RED);
        //Get list of sub-nodes - create an array of NodeBox's each.
        ArrayList<QTNode> list = root.getAllSubNodes();
        QTNode [] treeStruct = list.toArray(new QTNode[list.size()]);
        NodeBox [] boxes = new NodeBox[treeStruct.length];

        for(int i = 0; i < treeStruct.length; i++)
        {
            boxes[i] = new NodeBox(treeStruct[i]);
            boxes[i].displayNodeBox(g);
        }

        g.setColor(colorMemory);
    }



    private class NodeBox
    {
        public QTNode n;
        final int width = 95;
        final int height = 50;

        public NodeBox(QTNode n){this.n = n;}

        public void displayNodeBox(Graphics2D g)
        {

            int offX = Constants.WINDOW_DIMENSION.width/2 + getOffX(n) * n.getDepth() + getOffX(n);
            int offY = 5 + (width * n.getDepth());

            g.drawRect(offX, offY, width, height);

            g.setColor(Color.CYAN);
            g.drawString("Region: " + n.getNodeRegion(), offX, offY + 15);
            g.drawString("Type: " + n.getNodeTypeName(), offX, offY + 28);
            g.drawString("Depth: " + n.getDepth(), offX,offY + 41);
            g.setColor(Color.RED);
        }

        private int getOffX(QTNode node)
        {
            switch (node.region)
            {
                case ROOT:
                    return 0;
                case TOP_LEFT:
                    return -50;
                case TOP_RIGHT:
                    return 0;
                case BOTTOM_LEFT:
                    return 50;
                case BOTTOM_RIGHT:
                    return 100;
            }
            return 0;
        }
    }


}
