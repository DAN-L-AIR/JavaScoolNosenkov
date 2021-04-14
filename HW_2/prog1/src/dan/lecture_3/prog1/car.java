package dan.lecture_3.prog1;

public class car implements Comparable<car> {
    String model;
    String type;

    public car(String model, String type) {
        this.model = model;
        this.type = type;
    }

    @Override
    public String toString() {
        return "<" + model + ":" + type + "> ";
    }

    @Override
    public int compareTo(car o) {
        int r = type.hashCode() - o.type.hashCode();
        if (r < 0) {
            return -1;
        } else if (r > 0) {
            return 1;
        } else {
            return 0;
        }
    }
}
