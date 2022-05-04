package Zad2;

public class Signal {
    private String message;
    public int direction; // -1 left, 1 right, 0 both, 2 empty

    public Signal() {
        message = "";
        direction = 2;
    }

    public Signal(String message, int direction) {
        this.message = message;
        this.direction = direction;
    }

    public void add(String s, int dir) {
        if (isEmpty()) direction = dir;
        if (s.equals("X") || message.equals("X")) {
            message = "X";
            if (direction == -dir) direction = 0;
            else direction = dir;
        } else if (!isEmpty() && (message.contains(s) || s.contains(message))) message = max(message, s);
        else message += s;
        if (direction == -dir) direction = 0;
    }

    public boolean isEmpty() {
        return message.equals("");
    }

    public void clear() {
        message = "";
        direction = 2;
    }

    public String read() {
        return message;
    }

    @Override
    public String toString() {
        if (!isEmpty())
            return printSignal();
        else return "_____";
    }

    private String space(int minLength) {
        if (message.length() < minLength) return " ";
        else return "";
    }

    private String printSignal() {
        String signal = space(3);
        signal += direction <= 0 ? "<" : " ";
        signal += this.message;
        signal += direction >= 0 ? ">" : " ";
        signal += space(2);
        return signal;
    }

    private String max(String a, String b) {
        if (a.contains(b)) return a;
        return b;
    }
}
