/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trig.game.entity;
import trig.utility.geometry.*;
import trig.utility.geometry.Polygon;

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

    public int getMaxHp()
    {
        return maxHp;
    }

    public void setMaxHp(int maxHp)
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

        hitbox = SEntity.constructGenericTriangle(30); //size: 50
        //translate it to the correct position;
        Rectangle bounds = hitbox.getBounds();

        hitbox.translate(x-(int) bounds.getMinX(), y-(int) bounds.getMinY());

        components.add(hitbox);

        Rectangle hitRect = hitbox.getBounds();

        ColoredPolygon tempA = new ColoredPolygon(SEntity.constructGenericTriangle((float) 30 / 10) );

        float rotationAngle =  ( ( (float) 8/6) * (float) Math.PI);
        float centerX = (float) hitRect.getCenterX();
         float centerY = (float) hitRect.getCenterY();

        tempA.rotate((float) (24/18.0*Math.PI));

        tempA.translate(1, 20);

        ColoredPolygon tempB = tempA.clone();
        tempB.rotateAbout(rotationAngle, centerX, centerY);

        tempB.translate(-15, -9);

        ColoredPolygon tempC = tempA.clone();
        tempC.rotateAbout(-rotationAngle, centerX, centerY);

        tempC.translate(-7, 10);

        tempA.setColor(Color.CYAN);
        tempB.setColor(Color.ORANGE);
        tempC.setColor(Color.MAGENTA);



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

        g.drawString(name, x+12, y-28);

        g.drawString("HP: "+hp, x+3, y-12);
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

        components.translate(
                x-this.x,
                y-this.y
        );

        super.setLocation(x, y);

        return;
    }

    /**
     * for rotating the hitbox, etc
     * @param theta
     */
    public void rotate(float theta){
        Rectangle hitRect = hitbox.getBounds();
        components.rotateAbout( theta, (float) hitRect.getCenterX(), (float) hitRect.getCenterY() );

        //get the new corner from the hitbox
        hitRect = hitbox.getBounds();
        super.setLocation(hitRect.x, hitRect.y);
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
        return !(hp > 0);
    }
}