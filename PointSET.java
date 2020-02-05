import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {

	private TreeSet<Point2D> set;

	public PointSET() {
		set = new TreeSet<>();

	}

	public boolean isEmpty() {
		return set.isEmpty();
	}

	public int size() {
		return set.size();
	}

	public void insert(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}
		set.add(p);
	}

	public boolean contains(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}
		return set.contains(p);
	}

	public void draw() {
		for (Point2D point : set) {
			point.draw();
		}
		
	}

	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null) {
			throw new IllegalArgumentException();
		}
		
		List<Point2D> list = new LinkedList<>();

		for (Point2D point : set) {
			if (rect.contains(point)) {
				list.add(point);
			}
			
		}
		return new Iterable<Point2D>() {
			
			@Override
			public Iterator<Point2D> iterator() {
				return list.iterator();
			}
		};
	}

	public Point2D nearest(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}
		if (set.isEmpty()) {
			return null;
		}
		Point2D champion = set.first();
		for (Point2D point : set) {
			if (point.distanceTo(p) <  champion.distanceSquaredTo(p)) {
				champion = point;
			}
			
		}
		return champion;
	}

	public static void main(String[] args) {
		
	}
}