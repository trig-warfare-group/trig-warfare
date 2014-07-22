package trig.utility;

import java.awt.Dimension;

public class Constants 
{
	public static String GAME_TITLE = "TrigWarfare";
	public static String AUTHOR = "TrigWarfare Group"; //team name I guess, do we need a better one?


    //may be better to have a very high res world map, and the window dimension in the same aspect ratio, on some scale.

    //Window dimension may not stay a constant in the long run, if we allow different screen sizes, may want to move it out of constants soon in preparation?

    //16:9 is the current standard aspect ratio? map should also be in this ratio I guess? How big? could start with a medium size..

    public static Dimension WORLD_DIM = new Dimension(960,540);

    //The boundary of movement is a little smaller than the actual viewport, which will be based on WORLD_DIM
    //we only really need to know how far inwards it is?, since we'dimension need to shift our 0,0 by that amount if we wanted to go to the trouble of mapping ourself in this space anyway, it saves no time?
    //public static int WORLD_MOVE_PADDING = 2;

    //by allowing entities to move slightly beyond the point where edge collision would be detected,
    // we can ensure they don't go so far as to dodge the event
    //whilst also ensuring that the event gets raised successfully as-a-collision, OR alternatively, allowing entities to react to that special location on their own
    //QUESTION: WHICH IS BETTER?,
    //
    // OR should it be handled only by collision event, and ignore in the entity's move function? PROBABLY THIS

    public static int WORLD_COLLISION_PADDING = 4;

    //match world dim for now
	public static Dimension WINDOW_DIMENSION = WORLD_DIM;//old: public static Dimension WINDOW_DIMENSION = new Dimension(800, 640);


	
	public static int FPS = 2000/60;
}
