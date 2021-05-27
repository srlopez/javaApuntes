package ui.javaFX.util;

public class Area {
    public int x;
    public int y;
    public int w;
    public int h;

    public Area(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    };

    @Override
    public String toString() {
        return "Area [" + x + "," + y + "," + w + "," + h + "]";
    }
}
