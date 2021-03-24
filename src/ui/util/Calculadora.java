package ui.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Calculadora {

    public static Map<Area, Data> calcularAreas(Area area, List<Data> list) {
        Map<Area, Data> result = new HashMap<Area, Data>();
        ordenaInverso(list);
        calcularAreaResult(area, list, result);
        return result;
    }

    private static void calcularAreaResult(Area area, List<Data> list, Map<Area, Data> result) {
        // Manejar el fin de la recursividad
        if (list.size() < 1)
            return;
        if (list.size() == 1) {
            result.put(area, list.get(0));
            return;
        }
        // Crear dos listas
        List<Data> list1 = new ArrayList<Data>();
        List<Data> list2 = new ArrayList<Data>();
        dividirLista(list, list1, list2);
        // Pintar los dos paneles
        Double sum = sum(list);
        Double sum1 = sum(list1);
        // Horizontal o vertical
        int w = area.w;
        int h = area.h;
        int h1, h2, w1, w2;
        Area area1;
        Area area2;
        if (w < h) {
            // Vertical
            h1 = (int) (h * sum1 / sum);
            h2 = h - h1;
            area1 = new Area(area.x, area.y, w, h1);
            area2 = new Area(area.x, area.y + h1, w, h2);
        } else {
            // Horizontal
            w1 = (int) (w * sum1 / sum);
            w2 = w - w1;
            area1 = new Area(area.x, area.y, w1, h);
            area2 = new Area(area.x + w1, area.y, w2, h);
        }
        calcularAreaResult(area1, list1, result);
        calcularAreaResult(area2, list2, result);
    }

    private static void dividirLista(List<Data> list, List<Data> list1, List<Data> list2) {
        Double sum1 = 0.0;
        Double mit = sum(list) / 2;
        int i = 0;
        do {
            list1.add(list.get(i));
            sum1 += list.get(i).v;
            i++;
        } while (sum1 + list.get(i).v < mit);
        for (; i < list.size(); i++)
            list2.add(list.get(i));
    }

    private static Double sum(List<Data> l) {
        return l.stream().mapToDouble(d -> d.v).reduce(0, (a, b) -> a + b);
    }

    private static void ordenaInverso(List<Data> l) {
        l.sort((d1, d2) -> Double.compare(d2.v, d1.v));
    }
}
