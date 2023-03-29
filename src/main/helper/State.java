package com.app.helper;

public class State {
    /**
     * the state enum for the possible gameStates
     */
    public static enum gameState {
        /**the state the triggers the events for gameplay to occur */
        Game,
        /**the Menu state TODO add different menu states for each menu type*/
        Menu,
        /**the pause menu state */
        PauseMenu
    }
    /**
     * the initialized gameState enum for use and modification/checking
     */
    public gameState state = gameState.Menu;
    //
    /**
     * creates a general purpose state Manager to facilitate changes in the game such
     *  as menu to 1st level to pause menu, yep its pretty simple
     */
    public State() {
        //
    }
    //
}
