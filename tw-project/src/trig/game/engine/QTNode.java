package trig.game.engine;

import trig.game.entity.SEntity;
import trig.utility.SDimension;

import java.awt.*;

/**
 * Created by brody on 20/07/14.
 */
public class QTNode
{
    QTNode tl;
    QTNode tr;
    QTNode bl;
    QTNode br;
    NodeType type = NodeType.LEAF;
    Dimension space;
    SEntity[] list;

    public QTNode(Dimension space, SEntity[] list)
    {
        this.space = space;
        this.list = list;
    }

    public void displayNode(Graphics2D g)
    {
        g.draw3DRect(
                ((SDimension) space).x, ((SDimension) space).y,
                ((SDimension) space).width, ((SDimension) space).height,
                true);
    }

    public QTNode getTLNode()
    {
        return tl;
    }

    public QTNode getTRNode()
    {
        return tr;
    }

    public QTNode getBLNode()
    {
        return bl;
    }

    public QTNode getBRNode()
    {
        return br;
    }

    public NodeType getNodeType()
    {
        return type;
    }

    private enum NodeType
    {
        BRANCH, LEAF
    }


}
