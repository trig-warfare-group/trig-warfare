/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trig.game.entity;

/**
 * A basic-combatant definition,
 * @author marcos
 * @author brody
 */
public abstract class BasicCombatant extends BasicEntity implements Living, Visible
{
    protected int hitPoints;
    protected boolean alive;

    public BasicCombatant(int x, int y, int hitPoints)
    {
        super(x, y);
        this.hitPoints = hitPoints;
        alive = true;
    }

    /**
     * A movement depending on what defines it.
     * @param x - cartesian x-location
     * @param y - cartesian y-location
     */
    public abstract void move(int x, int y);

    public boolean isAlive()
    {
        return alive;
    }

    public void setAlive(boolean alive)
    {
        this.alive = alive;
    }

    public int getHitPoints()
    {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints)
    {
        this.hitPoints = hitPoints;
    }

}