package com.epam.livingpope.torpedo.communication;

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

    public void greeting(int size) {
        sendMessage(GREETING + SEPARATOR + size);
    }

    public void fire(int x, int y) {
        sendMessage(FIRE + SEPARATOR + x + SEPARATOR + y);
    }

    public void hit() {
        sendMessage(HIT);
    }

    public void miss() {
        sendMessage(MISS);
    }

    public void sunk() {
        System.err.println("me: sunk");
        sendMessage(SUNK);
    }

    public void win() {
        sendMessage(WIN);
    }

}
