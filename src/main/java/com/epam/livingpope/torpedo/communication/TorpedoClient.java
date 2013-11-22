package com.epam.livingpope.torpedo.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.epam.livingpope.torpedo.Game;
import com.epam.livingpope.torpedo.shapes.GameBoard;
import com.epam.livingpope.torpedo.shapes.Point;
import com.epam.livingpope.torpedo.targeting.RandomTargetingSystem;

public class TorpedoClient extends DefaultMessages {
    public static final String SHIP_FILE_LOC = "D:/prj/_torpedo-contest/torpedo/resources/test.in";
    private static final int TABLE_SIZE = 20;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private final Game game = createGame();

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
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("I/O connection failed at " + hostName);
            System.exit(1);
        } finally {
            if (client != null) {
                client.closeSocket();
            }
        }
    }

    private static void checkParameters(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java TorpedoClient <host name> <port number>");
            System.exit(1);
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
        Point firstTarget = game.firstTarget();
        fire(firstTarget.x, firstTarget.y);
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
            game.onHit();
        } else if (input.equals(MISS)) {
            game.onMiss();
        } else if (input.equals(SUNK)) {
            game.onSunk();
        } else if (input.equals(WIN)) {
            System.err.println(GAME_WON);
            onEndOfGame();
        }
    }

    private void fireBack() {
        Point nextTarget = game.nextTarget();
        fire(nextTarget.x, nextTarget.y);
    }

    private void handleFire(String input) throws IOException {
        BulletStatus status = getStatusOnFire(input);
        if (status.equals(BulletStatus.HIT)) {
            hit();
        } else if (status.equals(BulletStatus.MISS)) {
            miss();
        } else if (status.equals(BulletStatus.SUNK)) {
            sunk();
            game.printTable();
        } else if (status.equals(BulletStatus.WIN)) {
            System.err.println(GAME_LOST);
            win();
            onEndOfGame();
        }
    }

    private BulletStatus getStatusOnFire(String input) {
        String[] split = input.split(SEPARATOR);
        int x = Integer.parseInt(split[1]);
        int y = Integer.parseInt(split[2]);
        BulletStatus status = game.getStatusOnFire(new Point(x, y));
        return status;
    }

    private void onEndOfGame() throws IOException {
        sendMessage(THANKS_FOR_THE_GAME);
        System.err.println(END_OF_THE_GAME);
        game.printResult();
        closeSocket();
    }

    private Game createGame() {
        return new Game(
                new RandomTargetingSystem(
                        new GameBoard.Builder(TABLE_SIZE, TABLE_SIZE)
                        .readShipsFromFile(SHIP_FILE_LOC)
                        .build()));
    }

    @Override
    public void sendMessage(String message) {
        out.println(message);
    }

}
