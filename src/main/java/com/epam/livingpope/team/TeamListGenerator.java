package com.epam.livingpope.team;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Responsible for generating a pairing of teams.
 *
 * @author Livia_Erdelyi
 */
public class TeamListGenerator {

    private static final int WEIGHT = 20;
    private static final int NUMBER_OF_TEAMS = 6;
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamListGenerator.class);
    private static List<Integer> numbers = new ArrayList<Integer>();
    private static List<Integer> numbers2 = new ArrayList<Integer>();

    public static void main(String[] args) {

        List<Team> teamList = new ArrayList<Team>();

        teamList.add(createTeam("Rabbit"));
        teamList.add(createTeam("LivingPope"));
        teamList.add(createTeam("Random"));
        teamList.add(createTeam("Bullsheep"));
        teamList.add(createTeam("O\""));
        teamList.add(createTeam("42"));

        LOGGER.info("{}", teamList);

    }

    public static Team createTeam(String name) {
        Team team = new Team();

        team.setName(name);
        team.setNumber1(generateRandomNumber(WEIGHT, numbers));
        team.setNumber2(generateRandomNumber(NUMBER_OF_TEAMS, numbers2) + 1);

        return team;
    }

    public static int generateRandomNumber(int max, List<Integer> nlist) {
        Random random = new Random();
        int number = random.nextInt(max);

        while (nlist.contains(number)) {
            number = random.nextInt(max);

        }
        nlist.add(number);
        return number;
    }
}
