import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

public class Path implements Serializable{
	private static final long serialVersionUID = 4159944502660335033L;
	int stroke;
	Color color;
	ArrayList<Point> points = new ArrayList<Point>();
	Path () {
		stroke = Painter.defaultStroke;
		color = Painter.defaultColor;
	}
}