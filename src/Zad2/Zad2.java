package Zad2;

public class Zad2 {
    public static void main(String[] args) { // java Zad2 [size] [time] [probability] [posA] [posB] [posC] ... itd
        if(args.length < 2 ) return;
        int size = Integer.parseInt(args[0]);
        int time = Integer.parseInt(args[1]);
        float pb = Float.parseFloat(args[2]);

        Network network = new Network(size, pb, time);

        int it = 3, pos;
        while(it < args.length) {
            pos = Integer.parseInt(args[it]) - 1;
            network.addNode(pos);
            it++;
        }

        network.simulate();
    }
}
