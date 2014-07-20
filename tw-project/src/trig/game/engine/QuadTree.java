package trig.game.engine;


import trig.game.entity.SEntity;
import trig.utility.SDimension;
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
    QTNode tl;
    QTNode tr;
    QTNode bl;
    QTNode br;
    SDimension space;
    ArrayList<SEntity> list;

    public QuadTree(Dimension space, ArrayList<SEntity> list)
    {
        this.space = (SDimension) space;
        this.list = list;
        init();
    }

    private void init()
    {
        tl = new QTNode(((SDimension) space).getHalvedDimension(), (SEntity []) list.toArray()); //Give top-left region.
        //Give top-right region.
    }


    /**
     *  Used to display the QuadTree dividing the space up into nodes.
     */
    public void displayTree(Graphics2D g)
    {

    }

}
