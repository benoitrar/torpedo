package com.epam.livingpope.torpedo.communication;

/**
 * Class for...
 *
 * @author Livia_Erdelyi Benedek_Kiss
 */

public abstract class DefaultMessages implements Messages {

    public static final String SEPARATOR = " ";
    public static final String GREETING = "greeting";
    public static final String FIRE = "fire";
    public static final String HIT = "hit";
    public static final String MISS = "miss";
    public static final String SUNK = "sunk";
    public static final String WIN = "win";
    public static final String THANKS_FOR_THE_GAME = "Thanks for the game";
    public static final String END_OF_THE_GAME = "END OF THE GAME";
    public static final String GAME_LOST = "I LOST THE GAME";
    public static final String GAME_WON = "I WON THE GAME";
    static final String CLIENT_SHIP_SUNK = "client: sunk";
    static final String CLIENT_SHIP_HIT = "client: hit";

    @Override
    public void greeting(int size) {
        sendMessage(GREETING + SEPARATOR + size);
    }

    @Override
    public void fire(int x, int y) {
        sendMessage(FIRE + SEPARATOR + x + SEPARATOR + y);
    }

    @Override
    public void hit() {
        sendMessage(HIT);
    }

    @Override
    public void miss() {
        sendMessage(MISS);
    }

    @Override
    public void sunk() {
        sendMessage(SUNK);
    }

    @Override
    public void win() {
        sendMessage(WIN);
    }

}
