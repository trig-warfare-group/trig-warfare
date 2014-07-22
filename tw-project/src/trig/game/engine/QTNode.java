package trig.game.engine;

import trig.game.entity.SEntity;
import trig.utility.SRectangle;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by brody on 20/07/14.
 */
public class QTNode
{
    QTNode [] nodes;
    NodeType type = NodeType.LEAF;
    NodeType region;
    private int depth;
    SRectangle space;
    SEntity[] list;

    public QTNode(SRectangle space, SEntity[] list)
    {
        this.space = space;
        this.list = list;
        this.depth = 0;
        type = NodeType.ROOT;
        region = NodeType.ROOT;
    }

    protected QTNode(SRectangle space, int depth)
    {
        this.space = space;
        this.depth = ++depth;
    }

    public void displayNodes(Graphics2D g)
    {
        Point p = space.getPoint();
        Dimension d = space.getDimension();

        g.drawRect(p.x, p.y, d.width, d.height);
        displaySubNodes(nodes, g);
    }

    private void displaySubNodes(QTNode [] nodes, Graphics2D g)
    {

        for(int i = 0; i < nodes.length; i++)
        {

            Point p = nodes[i].space.getPoint();
            Dimension d = nodes[i].space.getDimension();

            g.setStroke(new BasicStroke(0.1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));

            if(nodes[i].getNodeType() == NodeType.LEAF)
            {
                g.setColor(Color.GREEN);
                g.drawRect(p.x, p.y, d.width, d.height);
            }
            else //Must be Branch, draw it, and its sub-nodes
            {
                displaySubNodes(nodes[i].getNodes(), g);
                g.drawRect(p.x, p.y, d.width, d.height);
            }

        }

    }

    /**
     * Starts building the tree.
     */
    protected void sprout()
    {
        //If the tree, is too large, stop.
        if(this.depth > QuadTree.MAX_NODE_DEPTH)
            return;

        /*
            Otherwise, if the right requirements,
            create the nodes, and sprout them.
         */
        if(list.length > 2)
        {
            QTNode[] newNodes = this.createNewNodes();
            this.nodes = newNodes;
            type = NodeType.BRANCH;

            for (int i = 0; i < newNodes.length; i++)
                newNodes[i].sprout();
        }
    }

    /**
     * Obtains a list of entities that are within the bounds of a given node.
     * @return - the list of entities that intersect a node.
     */
    public SEntity [] intersectList(QTNode checkNode)
    {
        ArrayList<SEntity> collisions = new ArrayList<SEntity>();

        for(int i = 0; i < list.length; i++)
            if(checkNode.space.intersects(list[i].getCollisionRegion()))
                collisions.add(list[i]);

        return collisions.toArray(new SEntity[collisions.size()]);
    }

    private QTNode [] createNewNodes()
    {
        /*
                Create 4 new sub-nodes,
                inform their region.
                give them entities they need.
         */

        QTNode [] newNodes = new QTNode[4];

        for(int region = 0; region < newNodes.length; region++)
        {
            newNodes[region] = createNewNode(region);
            newNodes[region].list = intersectList(newNodes[region]);
        }

        return newNodes;
    }

    private QTNode createNewNode(int region)
    {
        QTNode newNode = null;
        SRectangle subSpace = space.getHalvedDimension();

        switch (region)
        {
            case 0: //Top-left
                    newNode = new QTNode(subSpace, depth);
                    newNode.region = NodeType.TOP_LEFT;
            break;
            case 1://Top-right
                subSpace.getPoint().x = subSpace.getDimension().width;
                newNode = new QTNode(subSpace, depth);
                newNode.region = NodeType.TOP_RIGHT;
            break;
            case 2://Bottom-left
                subSpace.getPoint().y = subSpace.getDimension().height;
                newNode = new QTNode(subSpace, depth);
                newNode.region = NodeType.BOTTOM_LEFT;
            break;
            case 3://Bottom-right
                subSpace.getPoint().x = subSpace.getDimension().width;
                subSpace.getPoint().y = subSpace.getDimension().height;
                newNode = new QTNode(subSpace, depth);
                newNode.region = NodeType.BOTTOM_RIGHT;
            break;
            default:
                new RuntimeException("QTNode.createNewNode() - Invalid option.");
        }

        return newNode;
    }

    public static enum NodeType
    {
        BRANCH, LEAF, ROOT,
        TOP_LEFT, TOP_RIGHT,
        BOTTOM_LEFT, BOTTOM_RIGHT
    }

    public QTNode getTLNode()
    {
        return nodes[0];
    }

    public QTNode getTRNode()
    {
        return nodes[1];
    }

    public QTNode getBLNode()
    {
        return nodes[2];
    }

    public QTNode getBRNode()
    {
        return nodes[3];
    }

    public QTNode [] getNodes()
    {
        return nodes;
    }

    public NodeType getNodeType()
    {
        return type;
    }

    public SRectangle getSpace()
    {
       return space;
    }

    public int getDepth()
    {
        return depth;
    }


    @Override
    public String toString()
    {
        Point p = space.getPoint();
        Dimension d = space.getDimension();
        return "X: "+ p.x + "\t\tY:" + p.y
                + "    \t\tWidth: " + d.width + "   \t\tHeight" + d.height
                + "\tType: " + type + "\tRegion: " + region;
    }

}
