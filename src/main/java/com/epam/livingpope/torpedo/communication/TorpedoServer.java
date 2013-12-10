package com.epam.livingpope.torpedo.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

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

public class TorpedoServer extends DefaultMessages {
    private static final Logger LOGGER = LoggerFactory.getLogger(TorpedoServer.class);
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private CleverTorpedo torpedo;
    private RandomTargetingSystem targetingSystem;

    public TorpedoServer(ServerSocket serverSocket, Socket clientSocket, PrintWriter out, BufferedReader in) {
        this.serverSocket = serverSocket;
        this.out = out;
        this.in = in;
    }

    public static void main(String[] args) throws IOException {

        checkParameters(args);

        int portNumber = Integer.parseInt(args[0]);

        TorpedoServer torpedoServer = null;
        try (
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
            torpedoServer = new TorpedoServer(serverSocket, clientSocket, out, in);
            torpedoServer.playTheGame();
        } catch (IOException e) {
            LOGGER.error("Exception caught when trying to listen on port {} or listening for a connection", portNumber);
            LOGGER.error(e.getMessage());
        } finally {
            if (torpedoServer != null) {
                torpedoServer.closeSockets();
            }
        }
    }

    private static void checkParameters(String[] args) {
        if (args.length != 1) {
            String errorMessage = "Usage: java EchoServer <port number>";
            LOGGER.error(errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private void closeSockets() throws IOException {
        if (clientSocket != null) {
            clientSocket.close();
        }
        if (serverSocket != null) {
            serverSocket.close();
        }
    }

    private void playTheGame() throws IOException {
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            handleInput(inputLine);
        }
    }

    private void handleInput(String input) throws IOException {
        if (input.startsWith(GREETING)) {
            createGame(input);
        } else if (input.startsWith(FIRE)) {
            handleFire(input);
            fireBack();
        } else if (input.equals(HIT)) {
            LOGGER.error(DefaultMessages.CLIENT_SHIP_HIT);
            targetingSystem.onHit();
        } else if (input.equals(MISS)) {
            targetingSystem.onMiss();
        } else if (input.equals(SUNK)) {
            LOGGER.error(DefaultMessages.CLIENT_SHIP_SUNK);
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

    private void createGame(String input) {
        String[] split = input.split(SEPARATOR);
        int tableSize = Integer.parseInt(split[1]);
        torpedo = new CleverTorpedo.Builder(tableSize, tableSize).readShipsFromFile("d:/ships.txt").build();
        targetingSystem = new RandomTargetingSystem(new GameBoard(tableSize, tableSize, FieldState.UNSPECIFIED));
    }

    private void onEndOfGame() throws IOException {
        LOGGER.error(END_OF_THE_GAME);
        sendMessage(THANKS_FOR_THE_GAME);
        closeSockets();
    }

    private void handleFire(String input) throws IOException {
        GameStatus status = getStatusOnFire(input);
        if (status.equals(GameStatus.HIT)) {
            hit();
        } else if (status.equals(GameStatus.MISS)) {
            miss();
        } else if (status.equals(GameStatus.SUNK)) {
            sunk();
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

    @Override
    public void sendMessage(String message) {
        out.println(message);
    }

}
