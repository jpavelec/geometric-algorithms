package cz.muni.fi.pa093;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Josef Pavelec, jospavelec@gmail.com
 */
public class Main extends Application {
    
    private final List<Point> points = new ArrayList<>();
    private List<Line> lines = new ArrayList<>();
    
    private Algorithm algorithm = Algorithm.none;
    
    private final Canvas canvas = new Canvas(800,600);
    
    private static final int POINT_RADIUS = 4;
    private static final Color POINT_COLOR = Color.BLUE;
    private static final Color CANVAS_COLOR = Color.WHITE;
    private static final Color LINE_GW_COLOR = Color.BLACK;
    private static final Color LINE_GRAHAM_COLOR = Color.BLACK;
    private static final Color LINE_TRIAN = Color.BLACK;
    private static final Color LINE_KD_HORIZONTAL_COLOR = Color.CORNFLOWERBLUE;
    private static final Color LINE_KD_VERTICAL_COLOR = Color.LAWNGREEN;
    private static final Color LINE_DELAUNAY_COLOR = Color.MAGENTA;
    private static final Color LINE_VERONOI_COLOR = Color.RED;
    
    
    @Override
    public void start(Stage stage) {
        
        HBox root = new HBox();
        root.setSpacing(5);
        root.setPadding(new Insets(10));
        
        Scene scene = new Scene(root, 960, 620);
        stage.setScene(scene);
        
        Group group = new Group(canvas);
        ToggleGroup radioButtonsAddDelete = new ToggleGroup();
                
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        VBox vbox = new VBox();
        vbox.setMinWidth(140);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        final RadioButton radioButtonAdd = new RadioButton("Add point");
        radioButtonAdd.setTooltip(new Tooltip("When is selected you may create " +
                                              "new point by clicking on canvas"));
        radioButtonAdd.setSelected(true);
        radioButtonAdd.setToggleGroup(radioButtonsAddDelete);
        final RadioButton radioButtonDelete = new RadioButton("Delete");
        radioButtonDelete.setTooltip(new Tooltip("When is selected you may delete "+
                                                 "existing point by clicking on it"));
        radioButtonDelete.setToggleGroup(radioButtonsAddDelete);
        Button buttonClear = new Button("Clear");
        buttonClear.setTooltip(new Tooltip("Clear canvas"));
        Button buttonGenerate = new Button("Generate 5\nrandom points");
        buttonClear.setMinWidth(120);
        buttonGenerate.setMinWidth(120);
        
        ToggleGroup radioButtonsAlgorithms = new ToggleGroup();
        RadioButton radioButtonGift = new RadioButton("Gift wrapping");
        radioButtonGift.setToggleGroup(radioButtonsAlgorithms);
        RadioButton radioButtonGraham = new RadioButton("Graham scan");
        radioButtonGraham.setToggleGroup(radioButtonsAlgorithms);
        RadioButton radioButtonTrianPol = new RadioButton("Trian polygon");
        radioButtonTrianPol.setToggleGroup(radioButtonsAlgorithms);
        RadioButton radioButtonTrianHull = new RadioButton("Trian convex hull");
        radioButtonTrianHull.setToggleGroup(radioButtonsAlgorithms);
        RadioButton radioButtonKD = new RadioButton("K-D tree");
        radioButtonKD.setToggleGroup(radioButtonsAlgorithms);
        RadioButton radioButtonDelaunay = new RadioButton("Delaunay");
        radioButtonDelaunay.setToggleGroup(radioButtonsAlgorithms);
        RadioButton radioButtonVeronoi = new RadioButton("Veronoi");
        radioButtonVeronoi.setToggleGroup(radioButtonsAlgorithms);
        
        
        
        
        vbox.getChildren().addAll(
                radioButtonAdd,
                radioButtonDelete,
                new Separator(),
                buttonClear,
                buttonGenerate,
                new Separator(),
                radioButtonGift,
                radioButtonGraham,
                radioButtonTrianPol,
                radioButtonTrianHull,
                radioButtonKD,
                radioButtonDelaunay,
                radioButtonVeronoi);
        
        
        buttonClear.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                points.clear();
                clearCanvas();
            }
            
        });
        
        buttonGenerate.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                generateRandomPoints(5);
                refresh();
            }
            
        });
        
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if (radioButtonAdd.isSelected()) {
                    points.add(new Point((float)event.getX(), (float)event.getY()));
                    refresh();
                }
                if (radioButtonDelete.isSelected()) {
                    Iterator<Point> it = points.iterator();
                    while (it.hasNext()) {
                        Point p = it.next();
                        if (event.getX()>p.getX()-POINT_RADIUS && event.getX()<p.getX()+POINT_RADIUS &&
                            event.getY()>p.getY()-POINT_RADIUS && event.getY()<p.getY()+POINT_RADIUS) {
                            points.remove(p);
                            refresh();
                            break;
                        }
                    }
                    
                }
            }
        });
        
        
        radioButtonGift.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                algorithm = Algorithm.gift;
                refresh();
            }
            
        });
        
        radioButtonGraham.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                algorithm = Algorithm.graham;
                refresh();
            }
            
        });
        
        radioButtonTrianPol.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                algorithm = Algorithm.trian;
                refresh();
            }
            
        });
        
        radioButtonTrianHull.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                algorithm = Algorithm.trianConvex;
                refresh();
            }
            
        });
        
        radioButtonKD.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                algorithm = Algorithm.kd;
                refresh();
            }
            
        });
        
        radioButtonDelaunay.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                algorithm = Algorithm.delaunay;
                refresh();
            }
            
        });
        
        radioButtonVeronoi.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                algorithm = Algorithm.veronoi;
                refresh();
            }
            
        });
        
        scene.widthProperty().addListener(new ChangeListener<Number>() {
        @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
            canvas.setWidth((double) newSceneWidth - 180);
            refresh();
            }
        });
        
        scene.heightProperty().addListener(new ChangeListener<Number>() {
        @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
            canvas.setHeight((double) newSceneHeight - 20);
            refresh();
            }
        });
        
        generateRandomPoints(15);
        drawPoints();
        
        root.getChildren().add(vbox);
        root.getChildren().add(group);
        
        
        
        
        stage.show();
        
    }
    
    
    
    private void refresh() {
        drawPoints();
        switch (algorithm) {
            case gift: lines = Line.getPolygonFromPoints(ConvexHull.giftWrapping(points));
                      drawLines(1, LINE_GW_COLOR);
                      break;
            case graham: lines = Line.getPolygonFromPoints(ConvexHull.grahamScan(points));
                         drawLines(1,LINE_GRAHAM_COLOR);
                         break;
            case trian:  lines = Line.getPolygonFromPoints(points);
                        drawLines(3, LINE_TRIAN);
                        lines = Triangulation.sweepLine(points);
                        drawLines(1, LINE_TRIAN);
                        break;
            case trianConvex: lines = Line.getPolygonFromPoints(ConvexHull.grahamScan(points));
                             drawLines(3, LINE_TRIAN);
                             lines = Triangulation.sweepLine(ConvexHull.grahamScan(points));
                             drawLines(1, LINE_TRIAN);
                             break;
            case kd: KdNode root = KdTree.buildKdTree(points, 0);
                     lines = KdTree.getLines(root, 0, 0, (float) canvas.getWidth(), (float) canvas.getHeight());
                     drawKdTree(1, LINE_KD_HORIZONTAL_COLOR, LINE_KD_VERTICAL_COLOR);
                     break;
            case delaunay: notSupported();
                          break;
            case veronoi: notSupported();
                         break;
            default: break;
        }
        
        
    }
    
    private static void notSupported() {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Not Supported");
        alert.setHeaderText(null);
        alert.setContentText("Sorry, this operation is not supported yet");

        alert.showAndWait();
    }
    
    private void drawKdTree(int width, Color horizontalColor, Color verticalColor) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(width);
        for (Line l : lines) {
            if (isHorizontal(l)) {
                gc.setStroke(horizontalColor);
            } else {
                gc.setStroke(verticalColor);
            }
            gc.strokeLine(l.getStartPoint().getX(), l.getStartPoint().getY(),
                          l.getEndPoint().getX(), l.getEndPoint().getY());
        }
    }
    
    private boolean isHorizontal(Line line) {
        return Float.compare(line.getStartPoint().getX(), line.getEndPoint().getX()) == 0;
    }
    
    private void drawPoints() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        clearCanvas();
        gc.setFill(POINT_COLOR);
        for (Point p : points) {
            gc.fillOval(p.getX()-POINT_RADIUS, p.getY()-POINT_RADIUS, 2*POINT_RADIUS, 2*POINT_RADIUS);
    //        gc.setLineWidth(1);
    //        gc.strokeText(p.toString(), p.getX()+10, p.getY());
        }
    }
    
    private void drawLines(int lineWidth, Color lineColor) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(lineColor);
        gc.setLineWidth(lineWidth);
        for (Line l : lines) {
            gc.strokeLine(l.getStartPoint().getX(), l.getStartPoint().getY(),
                          l.getEndPoint().getX(), l.getEndPoint().getY());
        }
    }
    
    
    private void clearCanvas() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(CANVAS_COLOR);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
    
    private void generateRandomPoints(int countOfPoints) {
        Random random = new Random(System.currentTimeMillis());
        for (int i=0;i<countOfPoints;i++) {
            points.add(new Point(50+random.nextFloat()*(float)(canvas.getWidth()-100),
                                 50+random.nextFloat()*(float)(canvas.getHeight()-100)));
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
