package com.epam.livingpope.torpedo;

import com.epam.livingpope.torpedo.shapes.Table;
import com.epam.livingpope.torpedo.targeting.RandomTargetingSystem;

public class Main {

    public static void main(String[] args) {

        Table table = new Table(20, 20);
        RandomTargetingSystem targetingSystem = new RandomTargetingSystem(table);
        Game game = new Game(targetingSystem);
        game.startGame();
    }

}
