package ProGAL.geom3d.complex.alphaComplex;

import java.awt.Color;
import java.util.LinkedList;

import ProGAL.datastructures.viewer.InteractiveBinaryTree;
import ProGAL.geom3d.complex.CTetrahedron;

public class Node implements InteractiveBinaryTree {
	public Node left = null;
	public Node right = null;
	private double alpha;
	private double death;
	private LinkedList<CTetrahedron> tetra;
	
	public Node(double a, LinkedList<CTetrahedron> t){
		alpha = a;
		death = -1;
		tetra = t;
	}
	
	public double getAlpha(){
		return alpha;
	}
	
	public void changeAlpha(double newA){
		alpha = newA;
	}
	
	public double getDeath(){
		return death;
	}
	
	public void changeDeath(double newD){
		death = newD;
	}
	
	public LinkedList<CTetrahedron> getTetra(){
		return tetra;
	}
	
	public void setChild(Node n, int index){
		if (index == 0){
			left = n;
		} else {
			right = n;
		}
	}

	
	
	public Node left() {return left;}
	public Node right() {return right;}
	public Color leftLegColor() {return Color.BLACK;}
	public Color rightLegColor() {return Color.BLACK;}
	public Color nodeColor() {
		if(tetra.size()==0){
			return Color.RED;
		}
		return Color.BLACK;}
	public String label() { return "";
		//if(tetra.size()==0) return "Rest";
		//return String.format("a: %.2f, tets: %d",alpha, tetra.size());
	}
	public void click() {
		System.out.println(String.format("a: %.2f, tets: %d",alpha, tetra.size()));
	}
}
