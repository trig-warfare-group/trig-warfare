package trig.game.entity;

/**
 * Temporary space (sorta for now, for identifying what data is needed for entities with a polygon hitbox) //BADNAME
 * Created by marcos on 12/07/2014.
 */
public interface PolygonHitbox
{
    //NOTE: entities do not explicitly all need a reserved variable for direction!!
    //Why? Because some entities may already have this data stored in a PolarVector, storing it twice would be redundant!
    abstract float getDirection(); //used for angle-correcting the hitbox
}
