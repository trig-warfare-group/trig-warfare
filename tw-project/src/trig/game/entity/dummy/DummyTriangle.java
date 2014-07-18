package trig.game.entity.dummy;

//NOTE: probably better to have some class with draw methods corresponding to each object or something rather than these imports in lots of places? idk.
import trig.game.engine.GameEngine;
import trig.game.entity.*;
import trig.game.entity.interfaces.UpdateListener;
import trig.utility.Constants;
import trig.utility.geometry.ColorfulPath;
import trig.utility.geometry.ColorfulPathList;
import trig.utility.geometry.Path;
import trig.utility.geometry.Renderable;
import trig.utility.math.vector.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Random;

/**
 * (Dummy/test) temporary test implementation of Combatant, MAY CONTAIN UNWANTED HARD-CODED DATA/INEFFICIENCIES, REUSE WITH CAUTION
 * NOTE: MAKE CONTAIN A HELPFUL TEMPLATE FOR FIRING PROJECTILES.
 * Created by marcos on 11/07/2014.
 */
public final class DummyTriangle extends Combatant implements UpdateListener
{
    protected ColorfulPathList<ColorfulPath<Vector>, Vector> renderPath;

    protected void makeTriangle() //most java.awt functions use minimum floating-point accuracy, it seems?
    {
        //absolute of the angle to rotate by against point A, to get points B and C
        //equilateral and isosceles triangle can be produced using +- this one angle, I think..
        float rotationBase = Math.round(1/2*Math.PI);
        float rotationAngle = (float) ( ( (float) 5 / 7 ) * Math.PI);

        Vector vA = new PolarForm(hitSize, rotationBase);
        Vector vB = new PolarForm(
                hitSize,
                (float) (rotationBase + rotationAngle)
        );
        Vector vC = new PolarForm(
                hitSize,
                (float) (rotationBase - rotationAngle)
        );

        renderPath = new ColorfulPathList<ColorfulPath<Vector>, Vector>();
        ColorfulPath<Vector> pA = new ColorfulPath(Color.RED);
        pA.add(vA);
        pA.add(vB);

        ColorfulPath<Vector> pB = new ColorfulPath<Vector>(Color.GREEN);
        pB.add(vB);
        pB.add(vC);

        ColorfulPath<Vector> pC = new ColorfulPath<Vector>(Color.BLUE);
        pC.add(vC);
        pC.add(vA);

        renderPath.add(pA);
        renderPath.add(pB);
        renderPath.add(pC);

        //connect the last to the first
        renderPath.add(pA);
    }
    //getters
    @Override
    public boolean isVisible()
    {
        return isMapped();
    }

    //PROBABLY USE TIMERS INSTEAD ETC
    private int step = 0;
    private Random r = new Random();

    //random defaults
    //protected static final float ST_TURN = (float) (((float)1/18)*Math.PI);
    protected static final int ST_DIST = 1;
    public static final float DEF_SPEED = 1; //no multiplicative effect

    //private ArrayList<Projectile> projectiles = new ArrayList<Projectile>(); //is this a good or bad way to do this? DUNNO, only a demo..

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
        makeTriangle();
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
        setVelocity(new PolarForm(ST_DIST * speed, newDirection));

    }

    private void fireProjectile(Projectile munition, GameEngine engine) //bad name?
    {
        //POSSIBLY REDUCE AMMO STOCK OR CHECK SOME COOLDOWN IDK
        engine.addEntity(munition);

        /*
            NOTE/TODO: DOES NOT ADD TO THE ENTITY LIST, THIS IS IMPORTANT,
            also need way to limit the number of projectiles fired
            probably can't really other than by fire rate or A COOLDOWN MECHANISM!!
         */
    }

    /*
    @Override
    public void draw(Graphics2D g)
    {
        //get the color right
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
    */
    @Override
    public void update(GameEngine engine)
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
            setVelocity(new PolarForm(ST_DIST*speed, newDirection));
        }
        else
        {
            if(step % 10 == 0)
            {
                CartesianForm bulletSpawnOffset = PolarForm.toCartesian(hitSize, velocity.inPolar().angle);

                fireProjectile(new DummyBullet(x+bulletSpawnOffset.getX(), y+bulletSpawnOffset.getY(), velocity.inPolar().angle), engine);
            }
            move();
        }
        step++;
    }

    @Override
    public void render(Graphics2D g)
    {
        AffineTransform renderTransform = new AffineTransform();
        renderTransform.translate(x, y);
        renderTransform.rotate(velocity.inPolar().angle);
        renderPath.render(g, renderTransform);
    }
}
