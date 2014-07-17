package trig.game.entity.interfaces;

/**
 * Indicates that the entity can have it's coordinates manually manipulated?
 * Note: not sure where we will actually use this?
 * @author marcos
 */
public interface Movable //may not stay an interface, might though
{
    abstract public int setX(int x);
    abstract public int setY(int y);
}
