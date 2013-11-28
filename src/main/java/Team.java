public class Team {
    String name;
    int numberOne;
    int numberTwo;

    public Team(String name, int number1, int number2) {
        this.name = name;
        this.numberOne = number1;
        this.numberTwo = number2;
    }

    public Team() {

    }

    public int getNumber2() {
        return numberTwo;
    }

    public void setNumber2(int number2) {
        this.numberTwo = number2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber1() {
        return numberOne;
    }

    public void setNumber1(int number1) {
        this.numberOne = number1;
    }

    @Override
    public String toString() {
        return "Team [name=" + name + ", numberOne=" + numberOne + ", numberTwo=" + numberTwo + "]\n";
    }

}
