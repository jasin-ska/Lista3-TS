package Zad2;

public class Node {
    public char name;
    public int position;
    public int waitingTime=0;
    public int signalsLeft = 0;
    public int collisions = 0; // liczba kolizji przy wysylaniu aktualnej ramki
    public boolean jamming = false;

    public int totalSuccesses = 0;
    public int totalCollisions = 0;


    public Node(char name, int position) {
        this.name = name;
        this.position = position;
    }

    public String send() {
        if(!jamming) return String.valueOf(name);
        else return "X";
    }

    @Override
    public String toString() {
        return String.valueOf(this.name);
    }

    public void printSummary() {
        System.out.println("Node " + name);
        System.out.println("\tTotal successes: " + totalSuccesses);
        System.out.println("\tTotal collisions: " + totalCollisions);
    }
}
