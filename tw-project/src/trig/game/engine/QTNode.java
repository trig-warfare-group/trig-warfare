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
    final static int SPROUT_REQUIREMENT = 2;

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
        //g.drawRect(space.x, space.y, space.width, space.height);
        g.setColor(new Color(32, 105, 26));
        if(nodes != null)
            displaySubNodes(nodes, g);
    }

    /**
     * Recursively draws sub-nodes.
     */
    private void displaySubNodes(QTNode [] subNodes, Graphics2D g)
    {
        for(int i = 0; i < subNodes.length; i++)
        {
            SRectangle sb = subNodes[i].space;
            if(subNodes[i].type == NodeType.LEAF)
                g.drawRect(sb.x, sb.y, sb.width, sb.height);
            else
                subNodes[i].displaySubNodes(subNodes[i].nodes, g);
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
        if(list.length > SPROUT_REQUIREMENT)
        {
            QTNode[] newNodes = this.createNewNodes();
            this.nodes = newNodes;
            this.type = NodeType.BRANCH;

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
            if(list[i].getCollisionRegion().intersects(checkNode.space))
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
        SRectangle subSpace = new SRectangle();
        subSpace.x = space.x;
        subSpace.y = space.y;
        subSpace.width = space.width / 2;
        subSpace.height = space.height / 2;

        switch (region)
        {
            case 0: //Top-left
                newNode = new QTNode(subSpace, depth);
                newNode.region = NodeType.TOP_LEFT;
            break;
            case 1://Top-right
                subSpace.x += subSpace.width;
                newNode = new QTNode(subSpace, depth);
                newNode.region = NodeType.TOP_RIGHT;
            break;
            case 2://Bottom-left
                subSpace.y += subSpace.height;
                newNode = new QTNode(subSpace, depth);
                newNode.region = NodeType.BOTTOM_LEFT;
            break;
            case 3://Bottom-right
                subSpace.x += subSpace.width;
                subSpace.y += subSpace.height;
                newNode = new QTNode(subSpace, depth);
                newNode.region = NodeType.BOTTOM_RIGHT;
            break;
            default:
                new RuntimeException("QTNode.createNewNode() - Invalid option.");
        }

        //System.out.println(subSpace + "\t" + newNode.getNodeRegion() +"\t\t\t["  + newNode.depth + "]");
        return newNode;
    }

    /**
     * Returns a list of all nodes in the current tree.
     * @return - the list
     */
    public ArrayList<QTNode> getAllSubNodes()
    {
        ArrayList<QTNode> list = new ArrayList<QTNode>();
        list.add(this);

        for(int i = 0; i < nodes.length; i++)
        {
            list.add(nodes[i]);
            recursiveCollection(list, nodes[i]);
        }
        return list;
    }

    /**
     * Recursively tries to collect all the nodes from the tree.
     */
    private void recursiveCollection(ArrayList theList, QTNode theNode)
    {
        if(theNode.getNodeType() == QTNode.NodeType.LEAF)
        {
            theList.add(theNode);
        }
        else
        {
            theList.add(theNode);
            QTNode subNodes [] = theNode.nodes;
            for(int i = 0; i < subNodes.length; i++)
                recursiveCollection(theList, subNodes[i]);
        }
    }

    private ArrayList collectNode(NodeType typeOfNode, QTNode startNode)
    {
        ArrayList<QTNode> nodeList = new ArrayList<QTNode>();

        if(startNode.type == typeOfNode)
            nodeList.add(startNode);

        recursiveCollection(nodeList, startNode.nodes, typeOfNode);
        return nodeList;
    }

    private void recursiveCollection(ArrayList theList, QTNode [] subNodes, NodeType typeOfNode)
    {
        for(int i = 0; i < subNodes.length; i++)
        {
            if(subNodes[i].getNodeType() == typeOfNode)
                theList.add(subNodes[i]);
            if(subNodes[i].getNodeType() == NodeType.BRANCH)
                recursiveCollection(theList, subNodes[i].nodes, typeOfNode);
        }
    }

    public static enum NodeType
    {
        BRANCH, LEAF, ROOT,
        TOP_LEFT, TOP_RIGHT,
        BOTTOM_LEFT, BOTTOM_RIGHT
    }


    public String getNodeRegion()
    {
        switch (this.region)
        {
            case ROOT:
                return "ROOT";
            case TOP_LEFT:
                return "TOP_LEFT";
            case TOP_RIGHT:
                return "TOP_RIGHT";
            case BOTTOM_LEFT:
                return "BOTTOM_LEFT";
            case BOTTOM_RIGHT:
                return "BOTTOM_RIGHT";
        }
        return null;
    }


    public String getNodeTypeName()
    {
        switch (type)
        {
            case LEAF:
                return "LEAF";
            case BRANCH:
                return "BRANCH";
            case ROOT:
                return "ROOT";
        }
        return null;
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
        return "X: "+ space.x + "\t\tY:" + space.y
                + "    \t\tWidth: " + space.width + "   \t\tHeight" + space.height
                + "\tType: " + type + "\tRegion: " + region;
    }

}
