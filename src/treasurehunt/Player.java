package treasurehunt;

import java.util.Scanner;
import java.util.Random;

/**
 *
 * @author genel
 */
public class Player {
    
    private String name;
    private String surname;
    private int age;
    private int digPoints;
    private int piratePoints;

    public Player(String name, String surname, int age) {
        //here the players are gonna be created and assigned digpoints and piratepoints.
      

        this.name = name;
        this.surname = surname;
        this.age = age;
        Random rnd = new Random();
        this.digPoints = rnd.nextInt() % (7 - 4) + 4;
        this.piratePoints = 100 - digPoints * 5;
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getDigPoints() {
        return digPoints;
    }

    public void setDigPoints(int digPoints) {
        this.digPoints = digPoints;
    }

    public void decreaseDigPoint() {
        this.digPoints--;
    }

    public int getPiratePoints() {
        return piratePoints;
    }

    public void setPiratePoints(int piratePoints) {
        this.piratePoints = piratePoints;
    }

    public void addPiratePoint() {

        this.piratePoints += 20;

    }
}