import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TeamListGenerator {
    static List<Integer> numbers = new ArrayList<Integer>();
    static List<Integer> numbers2 = new ArrayList<Integer>();

    public static void main(String[] args) {

        List<Team> teamList = new ArrayList<Team>();

        teamList.add(createTeam("Rabbit"));
        teamList.add(createTeam("LivingPope"));
        teamList.add(createTeam("Random"));
        teamList.add(createTeam("Bullsheep"));
        teamList.add(createTeam("O\""));
        teamList.add(createTeam("42"));

        System.out.println(teamList);

    }

    public static Team createTeam(String name) {
        Team team = new Team();

        team.setName(name);
        team.setNumber1(generateRandomNumber(20, numbers));
        team.setNumber2(generateRandomNumber(6, numbers2) + 1);

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
