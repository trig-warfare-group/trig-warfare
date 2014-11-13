/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trig.game.world;
import trig.utility.geometry.*;
import trig.utility.geometry.Polygon;
import trig.utility.math.vector.FloatCartesian;

import java.awt.*;

/**
 * Base class for an actor's (e.g. the KeyInputTracker's) vessel in the world. (the WorldObject that represents them)
 * @author marcos
 */
public class Vessel extends StandardWorldObject
{

    //vars
    protected String name;
    protected int hp;
    protected int maxHp;
    protected Color color;
    protected Weapon weapon;
    protected float facingDirection; //east is 0, like on a unit circle.
    FloatCartesian tempMid;
    public float getFacingDirection()
    {
        return facingDirection;
    }

    public Weapon getWeapon()
    {
        return weapon;
    }

    /**
     * Contains both the hitbox and any other renderable paths etc,
     * Just shortens the code a bit by automating transformation of all components
     */
    protected RenderableList<Renderable> components;


    //getters and setters
    public int getHp()
    {
        return hp;
    }

    public void setHp(int hp)
    {
        this.hp = hp;
        if(hp <= 0)
        {
            //die(); // protected abstract void die();
        }
    }

    public int getMaxHp()
    {
        return maxHp;
    }

    protected void setMaxHp(int maxHp)
    {
        this.maxHp = maxHp;
    }

    public String getName()
    {
        return name;
    }

    public Color getColor()
    {
        return color;
    }

    public Vessel(String name, Color color, int maxHp)
    {
        super(new FloatCartesian());

        this.name = name;
        this.color = color;
        this.maxHp = maxHp;

        this.components = new RenderableList<Renderable>();

        /*
            Construct the hitbox, etc
         */

        hitbox = BasicWorldObject.constructGenericTriangle(30); //size: 50
        //translate it to the correct position;

        components.add(hitbox);

        hitbox.translate(FloatCartesian.difference(location, hitbox.getFloatVectorBounds().getMin()));

        ColoredPolygon tempA = new ColoredPolygon(BasicWorldObject.constructGenericTriangle(5) );
        tempA.setColor(Color.ORANGE);
        FloatCartesian cannonLocation = FloatCartesian.sum(hitbox.get(0), new FloatCartesian(10, 0));
        tempA.translate(cannonLocation);

        components.add(tempA);
        weapon = new SCannonBETA(cannonLocation, new FloatCartesian(1, 0));
        facingDirection = 0;

        tempMid = hitbox.getFloatVectorBounds().getCenter();

    }

    @Override
    public Polygon getHitbox()
    {
        return hitbox;
    }

    /**
     * Gives the world a list of entities it has collided with, so that it can handle type-specific collision responses?
     *
     * @param colliders
     */
    @Override
    public void onCollision(Collidable[] colliders)
    {
        for(Collidable each : colliders)
        {
            if(each instanceof Bullet){
                Bullet bullet = ((Bullet) each);
                damage(bullet.getDmg());
                bullet.spend();

            }
        }
    }

    @Override
    public void render(Graphics2D g)
    {
        g.setColor(color);
        components.render(g);
        FloatCartesian tempLocation = getLocation();
        g.drawString(name, tempLocation.x+12, tempLocation.y-28);

        g.drawString("HP: " + hp, tempLocation.x + 3, tempLocation.y - 12);
    }

    /*
        Must override move() and setLocation() so the hitbox can be moved with the world!
     */
    @Override
    public void move(FloatCartesian shift)
    {
        super.move(shift);
        weapon.move(shift);
        components.translate(shift);
    }

    /**
     * for rotating the hitbox, etc
     * @param theta
     */
    public void rotate(float theta){
        FloatCartesian center = hitbox.getFloatVectorBounds().getCenter();
        components.rotateAbout(theta, center);
        tempMid = hitbox.getFloatVectorBounds().getCenter();
        weapon.rotateAbout(theta, center);
        location = hitbox.getFloatVectorBounds().getMin();
        facingDirection += theta;
    }

    public void setFacingDirection(float theta)
    {

        rotate(theta - facingDirection);
    }

    public void kill(){
        setHp(0);
    }

    public void damage(int points){
        int newHp = getHp()-points;
        if(newHp < 0)
        {
            newHp = 0;
        }
        setHp(newHp);
    }

    public void heal(int points){
        int newHp = getHp()+points;
        if(newHp > maxHp){
            newHp = maxHp;
        }
        setHp(newHp);
    }

    /**
     * Indicates that the engine should delete the world from it's list.
     * Note: sort of forces the engine to do garbage collection, but it's a well-encapsulated way of doing it? Keep? Y/n?
     */
    @Override
    public boolean isTrash()
    {
        return !(hp > 0);
    }

    //note, remember to refactor this to account for multiple weapons?
    //remember that this doesn't add the entity to the engine, that must be performed externally.
    public Projectile fireWeapon()
    {
        return weapon.generateProjectile();
    }
}