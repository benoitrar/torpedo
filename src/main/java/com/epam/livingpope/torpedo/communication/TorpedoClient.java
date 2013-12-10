package com.epam.livingpope.torpedo.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.livingpope.torpedo.shapes.FieldState;
import com.epam.livingpope.torpedo.shapes.GameBoard;
import com.epam.livingpope.torpedo.shapes.Point;
import com.epam.livingpope.torpedo.targeting.RandomTargetingSystem;
import com.epam.livingpope.torpedo.torpedo.CleverTorpedo;

/**
 * Class for...
 *
 * @author Livia_Erdelyi Benedek_Kiss
 */

public class TorpedoClient extends DefaultMessages {
    public static final String SHIP_FILE_LOC = "D:/prj/_torpedo-contest/torpedo/resources/test.in";
    private static final Logger LOGGER = LoggerFactory.getLogger(TorpedoClient.class);
    private static final int TABLE_SIZE = 20;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private CleverTorpedo torpedo;
    private RandomTargetingSystem targetingSystem;

    public TorpedoClient(Socket socket, PrintWriter out, BufferedReader in) {
        this.socket = socket;
        this.out = out;
        this.in = in;
    }

    public static void main(String[] args) throws IOException {

        checkParameters(args);

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        TorpedoClient client = null;
        try (
            Socket socket = createSocket(hostName, portNumber);
            PrintWriter out = createWriter(socket);
            BufferedReader in = createReader(socket);
        ) {
            client = new TorpedoClient(socket, out, in);
            client.playTheGame();
        } catch (UnknownHostException e) {
            LOGGER.error("Don't know about host {}", hostName);
        } catch (IOException e) {
            LOGGER.error("I/O connection failed at {}", hostName);
        } finally {
            if (client != null) {
                client.closeSocket();
            }
        }
    }

    private static void checkParameters(String[] args) {
        if (args.length != 2) {
            String errorMessage = "Usage: java TorpedoClient <host name> <port number>";
            LOGGER.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private static BufferedReader createReader(Socket socket) throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private static PrintWriter createWriter(Socket socket) throws IOException {
        return new PrintWriter(socket.getOutputStream(), true);
    }

    private static Socket createSocket(String hostName, int portNumber) throws UnknownHostException, IOException {
        return new Socket(hostName, portNumber);
    }

    private void closeSocket() throws IOException {
        socket.close();
    }

    private void playTheGame() throws IOException {
        startGame();
        handleInputs();
    }

    private void startGame() {
        greeting(TABLE_SIZE);
        torpedo = new CleverTorpedo.Builder(TABLE_SIZE, TABLE_SIZE).readShipsFromFile("d:/ships.txt").build();
        targetingSystem = new RandomTargetingSystem(new GameBoard(TABLE_SIZE, TABLE_SIZE, FieldState.UNHIT_EMPTY));
        Point firstTarget = targetingSystem.firstTarget();
        fire(firstTarget.getX(), firstTarget.getY());
    }

    private void handleInputs() throws IOException {
        String input;
        while ((input = in.readLine()) != null) {
            handleInput(input);
        }
    }

    private void handleInput(String input) throws IOException {
        if (input.startsWith(FIRE)) {
            handleFire(input);
            fireBack();
        } else if (input.equals(HIT)) {
            targetingSystem.onHit();
        } else if (input.equals(MISS)) {
            targetingSystem.onMiss();
        } else if (input.equals(SUNK)) {
            targetingSystem.onSunk();
        } else if (input.equals(WIN)) {
            LOGGER.error(GAME_WON);
            onEndOfGame();
        }
    }

    private void fireBack() {
        Point nextTarget = targetingSystem.nextTarget();
        fire(nextTarget.getX(), nextTarget.getY());
    }

    private void handleFire(String input) throws IOException {
        GameStatus status = getStatusOnFire(input);
        if (status.equals(GameStatus.HIT)) {
            hit();
        } else if (status.equals(GameStatus.MISS)) {
            miss();
        } else if (status.equals(GameStatus.SUNK)) {
            sunk();
            // TODO print
        } else if (status.equals(GameStatus.WIN)) {
            LOGGER.error(GAME_LOST);
            win();
            onEndOfGame();
        }
    }

    private GameStatus getStatusOnFire(String input) {
        String[] split = input.split(SEPARATOR);
        int x = Integer.parseInt(split[1]);
        int y = Integer.parseInt(split[2]);
        GameStatus status = torpedo.getFireResult(new Point(x, y));
        return status;
    }

    private void onEndOfGame() throws IOException {
        sendMessage(THANKS_FOR_THE_GAME);
        LOGGER.error(END_OF_THE_GAME);
        // TODO print result
        closeSocket();
    }

    @Override
    public void sendMessage(String message) {
        out.println(message);
    }

}
