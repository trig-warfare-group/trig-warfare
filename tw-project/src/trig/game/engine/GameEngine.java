package trig.game.engine;

/**
 * Dummy/Demo/Draft engine for some testing, etc etc?
 * Created by marcos on 14/07/2014.
 */
public class GameEngine //may extend some GameState interface I think, not an exact name, but it's for the states that relate to rendering and updates and stuff.
{
    /**
     * Initialisation of the state
     */
    public void GameEngine()
    {

    }

    public void update()
    {

    }

    public void render() //we don't need to call render, as the client will call it automatically. through the FSM stuff, etc.
    {

    }

    /*
        NOTE/TODO: SINCE THE GAME WILL BE ONLINE, IT WILL NEED A SUB-STATE OR SOMETHING FOR PAUSED,
        SINCE THE GAME'S SCREEN WILL NEED TO RENDER IN THE BACKGROUND, BUT EVENT BEHAVIOUR WILL CHANGE!
        MAYBE THIS COULD BE HANDLED BY HAVING A GAMEINPUTEVENTS AND PAUSEINPUTEVENTS FUNCTION OR SOMETHING?

    */
}

/**
 * Class for holding game data that needs to be
 */
class GameData
{

}