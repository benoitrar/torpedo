package com.epam.livingpope.torpedo.communication;

/**
 * Class for...
 *
 * @author Livia_Erdelyi Benedek_Kiss
 */

public interface Messages {

    /**
     * The general method for sending a message.
     *
     * @param message
     *            to be sent
     */
    void sendMessage(String message);

    /**
     * Send greeting message.
     *
     * @param size
     *            size of the game board
     */
    void greeting(int size);

    /**
     * Sends a fire message.
     *
     * @param x
     *            x coordinate of the fired point
     * @param y
     *            y coordinate of the fired point
     */
    void fire(int x, int y);

    /**
     * Sends a hit message.
     */
    void hit();

    /**
     * Sends a miss message.
     */
    void miss();

    /**
     * Sends a sunk message.
     */
    void sunk();

    /**
     * Sends a win message.
     */
    void win();
}
