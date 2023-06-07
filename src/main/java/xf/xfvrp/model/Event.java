package xf.xfvrp.model;

public interface Event {

    float[][] timeWindows();

    Location location();

    float serviceTime();
}
