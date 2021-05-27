package ui.javaFX;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import sistema.Sistema;
import ui.javaFX.util.*;


public class Controlador {
	Sistema sistema;
    List<Data> list = new ArrayList<Data>();


    public Controlador(Sistema sistema) {
		this.sistema = sistema;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lblGastos;
    
    @FXML
    private Button btnCalcular;

    @FXML
    private Pane pnArea;

    @FXML
    void btnCalcularClick(ActionEvent event) {
        lblGastos.setText("Gastos realizados");
        loadList(0);
        dibujarArea();
    }

    @FXML
    void initialize() {
        assert btnCalcular != null : "fx:id=\"btnCalcular\" was not injected: check your FXML file 'miVista.fxml'.";
        assert pnArea != null : "fx:id=\"pnArea\" was not injected: check your FXML file 'miVista.fxml'.";

        // mi inicialización
        pnArea.setBorder(new Border(
                new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        pnArea.widthProperty().addListener(stageSizeListener);
        pnArea.heightProperty().addListener(stageSizeListener);

        loadList(0); //Se invoca a dibujar desde ChangeListener
    }

    private void loadList(int id){
        list.clear();
        for( String line: sistema.qryImportes(id)){
            //System.out.println(line);
            String[] s = line.split(";");
            list.add(new Data( Double.parseDouble(s[2].replace(",",".")), s[1], Integer.parseInt(s[0].trim()) ));
        };
    }

    private ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
        Double h = pnArea.getHeight();
        Double w = pnArea.getWidth();
        if (h < 100 || w < 100)
            return;
        dibujarArea();
        // -> System.out.println("Height: " + pnArea.getHeight() + " Width: " +
        // pnArea.getWidth());
    };

    void dibujarArea() {
        //List<Data> list = strToData("5 10 15 20 30");//txtValores.getText());
        Map<Area, Data> result = CalculadoraDeAreas
                .calcularAreas(new Area(0, 0, (int) pnArea.getWidth(), (int) pnArea.getHeight()), list);

        pnArea.getChildren().clear();
        for (Area a : result.keySet()) {
            // System.out.println(a + ":" + result.get(a));
            addControl(pnArea, a, result.get(a));
        }
    }

    private void addControl(Pane pn, Area area, Data data) {
        String line = String.format("%d", area.h * area.w / 100);
        line = String.format("%s\n%dx%d\n%s", line, area.h, area.w, data.txt);
        line = String.format("%4.2f\n%s", data.v, data.txt);
        Control ctrl = newControl(area.x, area.y, area.w, area.h, line, data);
        pn.getChildren().add(ctrl);
    }

    private Control newControl(double x, double y, double w, double h, String txt, Data data) {
        Button btn = new Button();
        Tooltip tooltip = new Tooltip();
        tooltip.setText(String.format("%s\n%dx%d",  data.txt.toUpperCase(),(int) w ,(int) h ));
        btn.setText(txt);
        btn.setTooltip(tooltip);
        btn.setUserData(data);
        btn.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Data d = (Data) btn.getUserData();
                if(d.id>9) return;
                lblGastos.setText(d.txt.toUpperCase());
                loadList(d.id);
                dibujarArea();

                //System.out.println(btn.getUserData());
            }
        });
        btn.setLayoutX(x);
        btn.setLayoutY(y);
        btn.setMinHeight(h);
        btn.setMaxHeight(h);
        btn.setMinWidth(w);
        btn.setMaxWidth(w);
        btn.setAlignment(Pos.CENTER);
        btn.setTextAlignment(TextAlignment.CENTER);
        return btn;
    }

    // private List<Data> strToData(String txt) {
    //     List<Data> list = new ArrayList<Data>();
    //     String[] ls = txtValores.getText().split("\\s+");
    //     for (String s : ls)
    //         list.add(new Data(Integer.parseInt(s), s));
    //     return list;
    // }
}
