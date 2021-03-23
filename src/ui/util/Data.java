package ui.util;

public class Data {
    public double v;
    public int id;
    public String txt;

    public Data(double valor, String titulo, int id) {
        this.v = valor;
        this.id = id;
        this.txt = titulo;
    }


    @Override
    public String toString(){
        return "Data ("+txt+","+v+")";
    }
}