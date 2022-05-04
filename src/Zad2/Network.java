package Zad2;

import java.util.*;

public class Network {
    private final int finishTime;

    private final List<Node> nodes = new ArrayList<>();
    private char nextNodeName = 'A';
    private final float probability;

    private final Signal[] cable;
    private final Signal[] oldCable;



    public Network(int size, float pb, int tm) {
        cable = new Signal[size];
        oldCable = new Signal[size];
        probability = pb;
        finishTime = tm;
        for(int i = 0; i < size; i ++) cable[i] = new Signal();
    }

    public void simulate() {
        int time = 0;
        System.out.println("Simulating: ");
        while(time < finishTime) {
            System.out.print("time " + time + ": ");
            if(time != 0) updateCable();
            for(Node n : nodes) {
                updateTime(n);
                randomTalkers(n);
                talkNode(n); // + checkCollision inside
            }
            System.out.print("\t" + Arrays.toString(cable));
            printLog();
            copyCable();
            time++;
        }
        System.out.println("\n SUMMARY:\n");
        for(Node n : nodes) n.printSummary();
    }

    public void addNode(int pos) {
        if(nextNodeName > 'Z') return;
        nodes.add(new Node(nextNodeName, pos));
        nextNodeName++;
    }

    private void talkNode(Node node) {
        if(node.jamming && node.signalsLeft > 0) {
            cable[node.position].add(node.send(), 0);
            node.signalsLeft--;
            if(node.signalsLeft == 0) { // koniec jammingu
                node.jamming=false;
                node.signalsLeft = 2*cable.length;
            }
        }
        else if((canStart(node) || isTalking(node)) && node.waitingTime == 0) {
            cable[node.position].add(node.send(), 0);
            node.signalsLeft--;
            checkCollision(node);
            if(node.signalsLeft == 0) { // udalo sie wyslac ramke
                node.collisions = 0;
                node.totalSuccesses++;
            }
        }
    }

    private boolean canStart(Node node) {
        return cable[node.position].isEmpty() && node.signalsLeft == 2*cable.length;
    }
    private boolean isTalking(Node node) {
        return node.signalsLeft > 0 && node.signalsLeft < 2*cable.length;
    }

    private void updateCable() {
        int n = cable.length;
        cable[0].clear();
        if(oldCable[1].direction == -1 || oldCable[1].direction == 0) cable[0].add(oldCable[1].read(), -1);

        cable[n-1].clear();
        if(oldCable[n-2].direction == 1 || oldCable[n-2].direction == 0) cable[n-1].add(oldCable[n-2].read(), 1);

        for(int i = 1; i < n - 1; i ++) {
            cable[i].clear();
            if(oldCable[i-1].direction == 1 || oldCable[i-1].direction == 0) cable[i].add(oldCable[i-1].read(), 1);
            if(oldCable[i+1].direction == -1 || oldCable[i+1].direction == 0) cable[i].add(oldCable[i+1].read(), -1);
        }
    }

    private void checkCollision(Node node) {
        if(!cable[node.position].read().equals(String.valueOf(node.name))) {
            node.totalCollisions++;
            node.collisions++;
            node.signalsLeft = cable.length*2;
            node.jamming = true;
            talkNode(node);
            int n = Math.min(10, node.collisions);
            node.waitingTime = (int)(Math.random() * (Math.pow(2, n))) * (2*cable.length);
        }
    }

    private void randomTalkers(Node n) {
        if(n.signalsLeft > 0) return;
        double p = Math.random();
        if(p < probability)  {
            n.signalsLeft = cable.length*2;
        }
    }

    private void updateTime(Node n) {
        if(!n.jamming && n.waitingTime > 0) {
            n.waitingTime--;
            if(n.waitingTime == 0) {
                n.signalsLeft = cable.length*2;
            }
        }
    }

    private void copyCable() {
        for(int i = 0; i < cable.length; i++) {
            oldCable[i] = new Signal(cable[i].read(), cable[i].direction);
        }
    }

    private void printLog() {
        System.out.print("\t");
        for(Node n : nodes) {
            if(n.jamming) System.out.print(" |" + n.name +": jamming [" +n.collisions+", " + n.waitingTime +"]");
            else if(isTalking(n)) System.out.print(" |" + n.name +": talking [" +n.collisions+"]");
            else if(n.signalsLeft == 2*cable.length && n.waitingTime == 0) System.out.print(" |" + n.name +": waiting to talk [" +n.collisions+"]");
        }
        System.out.println();
    }
}
