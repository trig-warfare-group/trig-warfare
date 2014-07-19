package trig.game.engine;

import trig.game.entity.Combatant;

/**
 * Just a quick utility -- will be most likely scrapped / upgraded
 * Created by brody on 17/07/14.
 */
public class CollisionCheck
{
       public Combatant c;
       public boolean collided;

        public CollisionCheck(Combatant c)
        {
            this.c = c;
            collided = false;
        }

}
