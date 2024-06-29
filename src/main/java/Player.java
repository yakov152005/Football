import java.util.concurrent.atomic.AtomicInteger;

public class Player {
    private static AtomicInteger idCounter = new AtomicInteger();

    private final int id;
    private String firstName;
    private String lastName;
    private Team team;

    public Player(String firstName, String lastName,Team team) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.team = team;
        this.id = idCounter.incrementAndGet();
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Team getTeam() {
        return this.team;
    }

    public String toString(){
        return this.firstName + " " + this.lastName;
    }


}
