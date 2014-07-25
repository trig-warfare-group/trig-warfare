/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trig.game.entity;
import trig.utility.geometry.Polygon;
import trig.utility.geometry.Renderable;
import trig.utility.geometry.RenderableList;

import java.awt.*;

/**
 * Base class for combatants, such as the player, AI.
 * @author marcos
 */
public class Ship extends SMovable implements Visible, Collidable
{

    //vars
    protected String name;
    protected int hp;
    protected int maxHp;
    protected Color color;
    protected Polygon hitbox;

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

    public String getName()
    {
        return name;
    }

    public Color getColor()
    {
        return color;
    }

    public Ship(String name, Color color, int maxHp)
    {
        super(0,0);

        this.name = name;
        this.color = color;
        this.maxHp = maxHp;

        this.components = new RenderableList<Renderable>();

        /*
            Construct the hitbox, etc
         */

        hitbox = SEntity.constructGenericTriangle(50); //size: 50

        hitbox.translate(x, y);

        components.add(hitbox);

        Rectangle hitRect = hitbox.getBounds();

        Polygon tempA = SEntity.constructGenericTriangle((float) 50 / 3);
        tempA.translate(50/6, 50/6);
        components.add(tempA);
        float rotationAngle =  ( ( (float) 5 / 7 ) * (float) Math.PI);
        float centerX = (float) hitRect.getCenterX();
        float centerY = (float) hitRect.getCenterY();

        Polygon tempB = tempA.clone();
        tempB.rotateAbout(rotationAngle, centerX, centerY);

        Polygon tempC = tempA.clone();
        tempC.rotateAbout(-rotationAngle, centerX, centerY);

        components.add(tempA);
        components.add(tempB);
        components.add(tempC);
    }

    @Override
    public Polygon getHitbox()
    {
        return hitbox;
    }

    /**
     * Gives the entity a list of entities it has collided with, so that it can handle type-specific collision responses?
     *
     * @param colliders
     */
    @Override
    public void onCollision(Collidable[] colliders)
    {
        for(Collidable each : colliders)
        {
            if(each instanceof Bullet){
                damage(((Bullet) each).getDmg());
            }
        }
    }

    @Override
    public void render(Graphics2D g)
    {
        g.setColor(color);
        components.render(g);
    }

    /*
        Must override move() and setLocation() so the hitbox can be moved with the entity!
     */
    @Override
    public void move(int dX, int dY)
    {
        super.move(dX, dY);

        components.translate(dX, dY);
    }

    @Override
    public void setLocation(int x, int y)
    {
        super.setLocation(x, y);

        components.translate(
                x-this.x,
                y-this.y
        );
    }

    /**
     * for rotating the hitbox, etc
     * @param theta
     */
    public void rotate(float theta){
        Rectangle hitRect = hitbox.getBounds();
        components.rotateAbout(theta, (float) hitRect.getCenterX(), (float) hitRect.getCenterY());
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
     * Indicates that the engine should delete the entity from it's list.
     * Note: sort of forces the engine to do garbage collection, but it's a well-encapsulated way of doing it? Keep? Y/n?
     */
    @Override
    public boolean isTrash()
    {
        return hp > 0;
    }
}


//new DummyBullet(x+bulletSpawnOffset.getX(), y+bulletSpawnOffset.getY(), velocity.inPolar().angle)