package com.epam.livingpope.torpedo.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.epam.livingpope.torpedo.Game;
import com.epam.livingpope.torpedo.shapes.Point;
import com.epam.livingpope.torpedo.shapes.Table;
import com.epam.livingpope.torpedo.targeting.RandomTargetingSystem;

public class TorpedoServer extends DefaultMessages {
    private static final String CLIENT_SHIP_SUNK = "client: sunk";
    private static final String CLIENT_SHIP_HIT = "client: hit";
    private ServerSocket serverSocket;
    private static Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Game game;
    private int firecounter = 0;

    public TorpedoServer(ServerSocket serverSocket, PrintWriter out, BufferedReader in) {
        this.serverSocket = serverSocket;
        this.out = out;
        this.in = in;
    }

    public static void main(String[] args) throws IOException {

        checkParameters(args);

        int portNumber = Integer.parseInt(args[0]);

        TorpedoServer torpedoServer = null;
        try {
            torpedoServer = createTorpedoServer(portNumber);
            torpedoServer.playTheGame();
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        } finally {
            if (torpedoServer != null) {
                torpedoServer.closeSockets();
            }
        }
    }

    private static void checkParameters(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java EchoServer <port number>");
            System.exit(1);
        }
    }

    private void closeSockets() throws IOException {
        clientSocket.close();
        serverSocket.close();
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
            nextFire();
        } else if (input.equals(HIT)) {
            System.out.println(CLIENT_SHIP_HIT);
            game.onHit();
        } else if (input.equals(MISS)) {
            game.onMiss();
        } else if (input.equals(SUNK)) {
            System.out.println(CLIENT_SHIP_SUNK);
            game.onSunk();
        } else if (input.equals(WIN)) {
            System.err.println(GAME_WON);
            onEndOfGame();
        }
    }

    private void nextFire() {
        Point nextTarget = game.nextTarget();
        fire(nextTarget.x, nextTarget.y);
    }

    private void createGame(String input) {
        String[] split = input.split(SEPARATOR);
        int tableSize = Integer.parseInt(split[1]);
        game = new Game(new RandomTargetingSystem(new Table(tableSize, tableSize)));
    }

    private void onEndOfGame() throws IOException {
        System.err.println(END_OF_THE_GAME);
        sendMessage(THANKS_FOR_THE_GAME);
        game.printResult();
        closeSockets();
    }

    private void handleFire(String input) throws IOException {
        Status status = getStatusOnFire(input);
        if (status.equals(Status.HIT)) {
            hit();
        } else if (status.equals(Status.MISS)) {
            miss();
        } else if (status.equals(Status.SUNK)) {
            sunk();
            game.printTable();
        } else if (status.equals(Status.WIN)) {
            System.err.println(GAME_LOST);
            win();
            onEndOfGame();
        }
    }

    private Status getStatusOnFire(String input) {
        String[] split = input.split(SEPARATOR);
        int x = Integer.parseInt(split[1]);
        int y = Integer.parseInt(split[2]);
        Status status = game.fire(new Point(x, y));
        return status;
    }

    private static TorpedoServer createTorpedoServer(int portNumber) throws IOException {
        ServerSocket serverSocket = new ServerSocket(portNumber);
        clientSocket = serverSocket.accept();
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        return new TorpedoServer(serverSocket, out, in);
    }

    @Override
    public void sendMessage(String message) {
        out.println(message);
    }

}
