package xf.xfvrp.model;

public interface Event {

    String name();

    float[][] timeWindows();

    Location location();

    float serviceTime();
}
