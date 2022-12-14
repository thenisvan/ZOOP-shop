package Model;

public interface Abilities {
    default void printInfo(){
        System.out.println(
                """
                ---------------------------------
                | Name:
                | Username: %s
                ----------------------------------
                """);
    }
    int getPoints();
     void printAbilities(Person p);

}
