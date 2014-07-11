package trig.game.entity;

//NOTE: probably better to have some class with draw methods corresponding to each object or something rather than these imports in lots of places? idk.
import trig.utility.Constants;
import trig.utility.vector.*;

import java.awt.*;
import java.awt.geom.*;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Dummy/test class
 * Created by marcos on 11/07/2014.
 */
public class DummyTriangle extends Combatant implements TickActor
{
    public static final Color DEF_COLOR = Color.WHITE;
    private int step = 0;

    public DummyTriangle(int id)
    {
        super(
                id, //id
                "Dummy_" + id //name
        );
        Random r = new Random();
        color = new Color(
                r.nextInt((250 - 75) + 1) + 75,
                r.nextInt((250 - 75) + 1) + 75,
                r.nextInt((250 - 75) + 1) + 75
        ); //random
        spawn();
    }

    @Override
    public void spawn()
    {
        //temporary boundary data for basic version


        //reset life-specific data
        HP = MAX_HP; //no point in entities having HP if its 0, one-hit-ko creatures should probably not even have
        speed = DEF_SPEED;

        int newX;
        int newY;
        float newDirection;
        do
        {
            newX = (int) (Math.random() * Constants.WINDOW_DIMENSION.width-hitRadius-21); //little extra padding? //REPLACE THIS VAL WITH BOUDARY RELEVANT STUFF
            newY = (int) (Math.random() * Constants.WINDOW_DIMENSION.height-hitRadius-21);

        }while(false); //!canSpawnAt(x, y) (pseudocode)
        newDirection = (float) Math.random() * 2 - 1; //between [-1,1], I think

        //NOTE: FOR SAFETY WE REALLY NEED TO LIMIT THE NUMBER OF LOOPS SOMEHOW!

        //if the loop ends, we can spawn safely
        this.x = newX;
        this.y = newY;
        setVel(new PolarVector(ST_DIST*speed, newDirection));

    }

    @Override
    public void draw(Graphics2D g)
    {
        //get the color right
        g.setColor(color);

        //temporary boundary data for basic version
        int mapSize = 400;

        //direction-corrected isosceles triangle!, I hope..

        //terrible name for this, but I'm tired and it's just an example..
        //absolute of the angle to rotate by against point A, to get points B and C
        //equilateral and isosceles triangle can be produced using +- this one angle, I think..
        float rotationAngle = (float) ( ( (float) 3 / 5 ) * Math.PI);
        PolarVector frontPolar = new PolarVector(hitRadius, polarVel.angle);

        //A,B,C points of the triangle, these are vector, and not real locations, as such they use the location of the entity as the origin, they must later be converted to locations.
        CartesianVector vA = frontPolar.toCartesian();
        CartesianVector vB = PolarVector.toCartesian(
                hitRadius,
                (float) ( polarVel.angle + rotationAngle)
        );
        CartesianVector vC = PolarVector.toCartesian(
                hitRadius,
                (float) ( polarVel.angle - rotationAngle)
        );

        //real coords of A,B,C
        Location lA = new Location(
                x+vA.x,
                y+vA.y
        );
        Location lB = new Location(
                x+vB.x,
                y+vB.y
        );
        Location lC = new Location(
                x+vC.x,
                y+vC.y
        );

        //TODO: MAKE THE GAME ENGINE OR WHATEVER WIPE THE SCREEN TO BLANK/DEFAULT COLOUR ON EACH FRAME!

        //TODO: DETERMINE IN THE HITBOX AND DRAWBOX MATCH UP!
        //draw on the actual screen..
        //there may be some shape class or something for this but atm who cares?
        g.draw(new Line2D.Float(lA.x, lA.y, lB.x, lB.y));
        g.draw(new Line2D.Float(lA.x, lA.y, lC.x, lC.y));
        g.draw(new Line2D.Float(lB.x, lB.y, lC.x, lC.y));
        //draw the name of the triangle above it
        g.drawString(name, x-(hitRadius/2), (float) (y-hitRadius*1.2));
    }

    @Override
    public void onTick()
    {
        if(step == 200)
        {
            kill();
            spawn();
            //reset
            step = 0;
        }
        else if(step % 40 == 0) //prime number, to prevent turning and moving, for now, I think it will anyway.
        {
            //randomise direction!
            float newDirection = (float) Math.random() * 2 - 1; //between [-1,1], I think
            setVel(new PolarVector(ST_DIST*speed, newDirection));
        }
        else
        {
            move();
        }
        step++;
    }
}
