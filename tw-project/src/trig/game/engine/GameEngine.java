package trig.game.engine;

import trig.game.entity.*;
import trig.utility.Constants;
import trig.utility.SRectangle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by marcos on 14/07/2014.
 * @author brody
 */
public class GameEngine
{
   // private ArrayList<Entity> entities = new ArrayList<Entity>();
    private Font bigFont = new Font(Font.SANS_SERIF, Font.BOLD, 45);
    private Font lilFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
    ArrayList<SEntity> entities;
    int collisions = 0;

    QuadTree qt;

    /**
     * Initialisation of the engine
     */
    public GameEngine()
    {
        entities = new ArrayList<SEntity>();
        Random r = new Random();

        for(int i = 0; i < 6; i ++)
            entities.add(new Combatant(
                    r.nextInt(Constants.WINDOW_DIMENSION.width),
                    r.nextInt(Constants.WINDOW_DIMENSION.height),
                    10));


       qt = new QuadTree(new SRectangle(Constants.WINDOW_DIMENSION),
                entities);

    }

    /**
     * Update the game-engine
     */
    public void update()
    {
        qt.clear();
        for(SEntity e : entities)
            e.update();
        qt.plantSeed();
    }


    public void render(Graphics2D g)
    {
        g.setFont(bigFont);
        g.setColor(Color.DARK_GRAY);

        g.drawString("Game-Engine", Constants.WINDOW_DIMENSION.width / 2 - 175, 325);

        g.setColor(new Color(74, 198, 36));
        g.setFont(lilFont);

        long start;
        long end;

        start = System.nanoTime();

        for(SEntity e : entities)
            e.render(g);

        qt.displayTree(g);
        //qt.displayStructure(g);

        g.setColor(new Color(74, 198, 36));
        end = System.nanoTime();
        g.drawString("Render time: " + Long.toString((end - start) / 1000) + "µs" + "|   Collision: " + Integer.toString(collisions),
                5,
                15);

    }

    public void addEntity(SEntity e)
    {
       entities.add(e);
    }

    public void removeEntity(SEntity e)
    {
        entities.remove(e);
    }
}
