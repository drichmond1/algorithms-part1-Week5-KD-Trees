import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
	private TwoDTree tree;
	private Point2D nearest;

	public KdTree() {
		tree = new TwoDTree();
	}

	public boolean isEmpty() {
		return tree.size == 0;
	}

	public int size() {
		return tree.size;
	}

	public void insert(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}
		tree.insert(p);
	}

	public boolean contains(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}
		return tree.containes(p);
	}

	public void draw() {
		Iterator<Node> iter = tree.iterator();
		while(iter.hasNext()) {
			Node node = iter.next();
			if(node.partitionVertical) {
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.point(node.getValue().x(), node.getValue().y());
				StdDraw.setPenColor(StdDraw.RED);
				StdDraw.line(node.getValue().x(), node.minY, node.getValue().x(), node.maxY);
			}
			else {
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.point(node.getValue().x(), node.getValue().y());
				StdDraw.setPenColor(StdDraw.BLUE);
				StdDraw.line(node.minX, node.getValue().y(), node.maxX, node.getValue().y());
			}
			
		}
	}

	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null) {
			throw new IllegalArgumentException();
		}
		//rect.draw();
		List<Point2D> list = new LinkedList<>();
		
		if(tree.root != null) {
		range(tree.root, list, rect);
		}
		
		return new Iterable<Point2D>() {
			
			@Override
			public Iterator<Point2D> iterator() {
				return list.iterator();
			}
		};
	}

	private void range(Node node, List<Point2D> list, RectHV rect) {
	 	if(rect.contains(node.value)) {
			list.add(node.value);
		}
		
		if(node.left != null) {
			
			RectHV leftRect = new RectHV(node.left.minX, node.left.minY, node.left.maxX, node.left.maxY);
			
		
			if(leftRect.intersects(rect)) {
				range(node.left, list, rect);
			}
		}
		
		if(node.right != null) {
			RectHV rightRect = new RectHV(node.right.minX, node.right.minY, node.right.maxX, node.right.maxY);
			if(rightRect.intersects(rect)) {
				range(node.right, list, rect);
			}
		}
		
	}

	public Point2D nearest(Point2D p) {
		if (p == null) {
			throw new IllegalArgumentException();
		}
		
		if(this.isEmpty()) {
			return null;
		}
		nearest = tree.root.getValue();
		nearest(p, tree.root);
		return nearest;
		
	}

	private void nearest(Point2D p, Node node) {
		//System.out.println("node " + node.getValue());
		if(node.getValue().distanceTo(p) < nearest.distanceTo(p)) {

			nearest = node.getValue();
		}
		double leftNodedist = getRectDistance(node.left, p);
		double rightNodedist = getRectDistance(node.right, p);
		
		if ( leftNodedist < rightNodedist){
			
			if (node.left != null) {
				RectHV leftRect = new RectHV(node.left.minX, node.left.minY, node.left.maxX, node.left.maxY);
		        
				if(leftRect.distanceTo(p) <= nearest.distanceTo(p)) {
		        	
		        	nearest(p, node.left);
		        }
			}
			
			if (node.right != null) {
				RectHV rightRect = new RectHV(node.right.minX, node.right.minY, node.right.maxX, node.right.maxY);
				
				if(rightRect.distanceTo(p) <= nearest.distanceTo(p)) {
			
		        	nearest(p, node.right);
		        }
			}

		}
		
		else {
			if (node.right != null) {
				RectHV rightRect = new RectHV(node.right.minX, node.right.minY, node.right.maxX, node.right.maxY);
				
				if(rightRect.distanceTo(p) <= nearest.distanceTo(p)) {
			
		        	nearest(p, node.right);
		        }
			}
			
			if (node.left != null) {
				RectHV leftRect = new RectHV(node.left.minX, node.left.minY, node.left.maxX, node.left.maxY);
		        
				if(leftRect.distanceTo(p) <= nearest.distanceTo(p)) {
		        	
		        	nearest(p, node.left);
		        }
			}
			
		}
		
	}

	private double getRectDistance(Node node, Point2D queryPoint) {
		if(node == null) {
			return Double.POSITIVE_INFINITY;
		}
		RectHV rect = new RectHV(node.minX, node.minY, node.maxX, node.maxY);
		return rect.distanceSquaredTo(queryPoint);
	}

	public static void main(String[] args) {
	KdTree kdt = new KdTree();
	
	kdt.insert(new Point2D(0.0, 1.0));
	kdt.insert(new Point2D(0.0, 0.75));
	kdt.insert(new Point2D(0.75, 0.5));
	kdt.insert(new Point2D(0.25, 0.25));
	kdt.insert(new Point2D(0.5, 0.75));
	kdt.insert(new Point2D(0.0, 1.0));
	kdt.insert(new Point2D(0.0, 0.75));
	kdt.insert(new Point2D(0.0, 0.0));
	kdt.insert(new Point2D(0.75, 1.0));
	kdt.insert(new Point2D(0.25, 0.25));
	
//	kdt.insert(new Point2D(0.9375, 0.8125));
//	kdt.insert(new Point2D(0.0625, 0.75));
//	kdt.insert(new Point2D(0.625, 0.5));
//	kdt.insert(new Point2D(0.6875, 0.375));
//	kdt.insert(new Point2D(0.0625, 0.5625));
//	kdt.insert(new Point2D(0.9375, 0.5));
//	kdt.insert(new Point2D(0.8125, 0.9375));
//	kdt.insert(new Point2D(0.125, 0.6875));
//	kdt.insert(new Point2D(0.4375, 0.9375));
//	kdt.insert(new Point2D(0.875, 0.5625));
	
//		kdt.insert(new Point2D(0.5 , 0.5));
//		kdt.insert(new Point2D(0.1 , 0.1));
//		kdt.insert(new Point2D(0.2 , 0.3));
//		kdt.insert(new Point2D(0.9 , 0.9));
//		kdt.insert(new Point2D(0.9 , 0.6));
		
		System.out.println(kdt.nearest(new Point2D(0.0, 0.5)));
		
//		System.out.println(kdt.size());
//		System.out.println(kdt.tree.root.getValue());
//		kdt.draw();
//		System.out.println(new RectHV(0, 1, 1, 1).contains(new Point2D(0.7, 0.2)));

//		System.out.println(kdt.nearest(new Point2D(1.0, 0.25)));
//	    Iterator<Point2D> iter = kdt.range(new RectHV(0, 0, 0.7, 0.4)).iterator();
		//Iterator<Point2D> iter = kdt.range(new RectHV(0.8, 0, 1, 1)).iterator();
//		 Iterator<Point2D> iter = kdt.tree.iterator();
//		while(iter.hasNext()) {
//			System.out.println(iter.next());
//		}
		
	}

	private class TwoDTree implements Iterable<Node> {
		private Node root;
		private int size;

		public TwoDTree() {

		}

		public void insert(Point2D point) {
			if(!containes(point)) {
			root = insert(root, point, true, 0, 1, 0, 1);
			size++;
			}
		}

		private Node insert(Node node, Point2D point, boolean partitionVertical, double xMin, double xMax, double yMin, double yMax) {
			if (node == null) {
				return new Node(point, null, null, partitionVertical, xMin, xMax, yMin, yMax);
			}

			else if (partitionVertical) {
				if (point.x() < node.getValue().x()) {
					node.left = insert(node.left, point, false, node.minX, node.getValue().x(), node.minY, node.maxY);
					return node;
				} else {
					node.right = insert(node.right, point, false, node.getValue().x(), node.getMaxX(), node.minY, node.maxY);
					return node;
				}
			}

			else {
				if (point.y() < node.getValue().y()) {
					node.left = insert(node.left, point, true, node.minX, node.maxX, node.minY, node.getValue().y());
					return node;
				} else {
					node.right = insert(node.right, point, true, node.minX, node.maxX, node.getValue().y(), node.maxY);
					return node;
				}
			}
		}

//		public void remove(Point2D point) {
//			
//		}

		public boolean containes(Point2D point) {
			return containes(root, point, true);
		}

		private boolean containes(Node node, Point2D point, boolean partitionVertical) {
			if (node == null) {
				return false;
			}

			else if (node.getValue().equals(point)) {
				return true;
			}

			else if (partitionVertical) {
				if (point.x() < node.getValue().x()) {
					return containes(node.left, point, false);
				} else {
					return containes(node.right, point, false);
				}
			}

			else {
				if (point.y() < node.getValue().y()) {
					return containes(node.left, point, true);
				} else {
					return containes(node.right, point, true);
				}
			}
		}

		@Override
		public Iterator<Node> iterator() {
			List<Node> list = new LinkedList<>();
			traverse(list, root);
			return list.iterator();
		}

		private void traverse(List<Node> list, Node node) {
			if (node == null) {
				return;
			}
			list.add(node);
			traverse(list, node.left);
			traverse(list, node.right);	
		}
	}

	private class Node {
		private Point2D value;
		private Node left;
		private Node right;
		private boolean partitionVertical;
		private double minX;
		private double maxX;
		private double minY;
		private double maxY;
	

		public Node(Point2D value, Node left, Node right, boolean partitionVertical, double minX, double maxX,
				double minY, double maxY) {
			super();
			this.value = value;
			this.left = left;
			this.right = right;
			this.partitionVertical = partitionVertical;
			this.minX = minX;
			this.maxX = maxX;
			this.minY = minY;
			this.maxY = maxY;
		}

		public double getMinX() {
			return minX;
		}

		public void setMinX(double minX) {
			this.minX = minX;
		}

		public double getMaxX() {
			return maxX;
		}

		public void setMaxX(double maxX) {
			this.maxX = maxX;
		}

		public double getMinY() {
			return minY;
		}

		public void setMinY(double minY) {
			this.minY = minY;
		}

		public double getMaxY() {
			return maxY;
		}

		public void setMaxY(double maxY) {
			this.maxY = maxY;
		}

		public Point2D getValue() {
			return value;
		}

		public void setValue(Point2D value) {
			this.value = value;
		}

		public boolean isPartitionVertical() {
			return partitionVertical;
		}

		public void setPartitionVertical(boolean partitionVertical) {
			this.partitionVertical = partitionVertical;
		}

		public Node getLeft() {
			return left;
		}

		public void setLeft(Node left) {
			this.left = left;
		}

		public Node getRight() {
			return right;
		}

		public void setRight(Node right) {
			this.right = right;
		}
	}
}
