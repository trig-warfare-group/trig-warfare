package trig.game.entity;

//NOTE: probably better to have some class with draw methods corresponding to each object or something rather than these imports in lots of places? idk.
import trig.utility.Constants;
import trig.utility.vector.*;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * (Dummy/test) temporary test implementation of Combatant, MAY CONTAIN UNWANTED HARD-CODED DATA/INEFFICIENCIES, REUSE WITH CAUTION
 * NOTE: MAKE CONTAIN A HELPFUL TEMPLATE FOR FIRING PROJECTILES.
 * Created by marcos on 11/07/2014.
 */
public final class DummyTriangle extends Combatant implements updateListener
{
    public static final Color DEF_COLOR = Color.WHITE;
    public static final int PROJECTILE_LIMIT = 100;
    private int step = 0;
    private Random r = new Random();

    private ArrayList<Projectile> projectiles = new ArrayList<Projectile>(); //is this a good or bad way to do this? DUNNO, only a demo..

    public DummyTriangle()
    {
        super(
                /*id, //id*/
                "", //name,
                25, //hitSize
                10, //hp
                Color.WHITE, //appease compiler by putting super() first
                1//speed
        );
        name = "Dummy_" + id;
        color = new Color(
                r.nextInt((250 - 75) + 1) + 75,
                r.nextInt((250 - 75) + 1) + 75,
                r.nextInt((250 - 75) + 1) + 75
        ); //random
        spawn();
    }

    @Override
    protected void move()
    {
        super.move();
        //rough, circle based edge-detection, temporary, handle in collisions later!!!
        if( x + hitSize > (Constants.WORLD_DIM.width - Constants.WORLD_COLLISION_PADDING - 1) ) //it's always -1 at the end, since the largest val is width-1, etc
        {
            //force-move to exact edge
            x = Constants.WORLD_DIM.width - Constants.WORLD_COLLISION_PADDING - Math.round(hitSize) - 1;
        }
        else if(x - hitSize < ( Constants.WORLD_COLLISION_PADDING - 1 ) )
        {
            x = Constants.WORLD_COLLISION_PADDING + Math.round(hitSize) - 1;
        }

        if( y + hitSize > (Constants.WORLD_DIM.height - Constants.WORLD_COLLISION_PADDING - 1) )
        {
            y = Constants.WORLD_DIM.height - Constants.WORLD_COLLISION_PADDING - Math.round(hitSize) - 1;
        }
        else if(y - hitSize < ( Constants.WORLD_COLLISION_PADDING - 1 ) )
        {
            y = Constants.WORLD_COLLISION_PADDING + Math.round(hitSize) - 1;
        }
    }

    @Override
    public void spawn()
    {
        //temporary boundary data for basic version


        //reset life-specific data
        hp = maxHp; //no point in entities having hp if its 0, one-hit-ko creatures should probably not even have
        speed = DEF_SPEED;

        int newX;
        int newY;
        float newDirection;
        do
        {
            newX = (int) (r.nextInt() * ( (Constants.WINDOW_DIMENSION.width-1 - hitSize) + 1) + hitSize); //little extra padding? //REPLACE THIS VAL WITH BOUDARY RELEVANT STUFF
            newY = (int) (r.nextInt() * ( (Constants.WINDOW_DIMENSION.width-1 - hitSize) + 1) + hitSize);

        }while(false); //!canSpawnAt(x, y) (pseudocode)
        newDirection = (float) ( ( (r.nextFloat() * 2) - 1 ) * Math.PI ); //between [-1,1], I think; //between [-1,1], I think

        //NOTE: FOR SAFETY WE REALLY NEED TO LIMIT THE NUMBER OF LOOPS SOMEHOW!

        //if the loop ends, we can spawn safely
        this.x = newX;
        this.y = newY;
        setVel(new Vector.PolarForm(ST_DIST*speed, newDirection));

    }

    private void fireProjectile(Projectile munition) //bad name?
    {
        projectiles.add(munition); //NOTE/TODO: DOES NOT ADD TO THE ENTITY LIST, THIS IS IMPORTANT, I THINK, ETC.
        if(projectiles.size() > PROJECTILE_LIMIT)
        {
            projectiles.remove(0); //remove last
        }
    }

    @Override
    public void draw(Graphics2D g)
    {
        //get the color right
        g.setColor(color);

        //direction-corrected isosceles triangle!, I hope..

        //terrible name for this, but I'm tired and it's just an example..
        //absolute of the angle to rotate by against point A, to get points B and C
        //equilateral and isosceles triangle can be produced using +- this one angle, I think..
        float rotationAngle = (float) ( ( (float) 5 / 7 ) * Math.PI);
        Vector.PolarForm frontPolar = new Vector.PolarForm(hitSize, polarVel.angle);

        //A,B,C points of the triangle, these are vector, and not real locations, as such they use the location of the entity as the origin, they must later be converted to locations.
        Vector.CartesianForm vA = frontPolar.toCartesian();
        Vector.CartesianForm vB = Vector.PolarForm.toCartesian(
                hitSize,
                (float) (polarVel.angle + rotationAngle)
        );
        Vector.CartesianForm vC = Vector.PolarForm.toCartesian(
                hitSize,
                (float) (polarVel.angle - rotationAngle)
        );

        //real coords of A,B,C, seems a bit inefficient to create new objects for them unnecessarily, but this is only a demo for now.
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
        float textBaseline = (float) (y-hitSize*1.2);

        g.drawString(x+", "+y, Math.round(x-(hitSize/1.2)), (float) (textBaseline));
        g.drawString(name, x-(hitSize), (float) (textBaseline-15));
        g.drawString("HP: "+hp, Math.round(x-(hitSize/1.5)), (float) (textBaseline-30));

        g.setColor(Color.RED);

        int approxHitDiameter = Math.round(hitSize*2);
        g.drawOval((int) (x-hitSize), (int) (y-hitSize), approxHitDiameter, approxHitDiameter); //inefficient re-do of math, only a demo, HITBOX

        //g.drawString(Float.toString(polarVel.angle), x-(hitSize/2)-10, (float) (y-hitSize*1.2)-10);

        //draw sub projectiles, shit way of doing it, ik.
        for(Entity e: projectiles)
        {
            if(e instanceof Visible && e.isMapped())
            {
                ((Visible) e).draw(g);
            }
        }
    }

    @Override
    public void onTick()
    {
        if(step == 2000)
        {
            //kill();
            //spawn();
            //reset
            step = 0;
        }
        else if(step % 500 == 0) //prime number, to prevent turning and moving, for now, I think it will anyway.
        {

            //randomise direction!
            float newDirection = (float) ( ( (r.nextFloat() * 2) - 1 ) * Math.PI ); //between [-1,1], I think
            setVel(new Vector.PolarForm(ST_DIST*speed, newDirection));
        }
        else
        {
            if(step % 50 == 0)
            {
                fireProjectile(new DummyBullet(Math.round(x+hitSize), Math.round(y+hitSize), polarVel.angle));
            }
            move();
            for(Entity e: projectiles)
            {
                if (e instanceof updateListener)
                {
                    ((updateListener) e).onTick();
                }
            }
        }
        step++;
    }
}
