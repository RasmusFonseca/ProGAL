package ProGAL.proteins.beltaStructure.loop;

import ProGAL.geom3d.Point;
import ProGAL.math.Matrix;
import ProGAL.proteins.belta.SSType;
import ProGAL.proteins.belta.SecondaryStructure;
import ProGAL.proteins.belta.SecondaryStructure.SSSegment;
import ProGAL.proteins.beltaStructure.sheetLoop.PartialStructure;
import ProGAL.proteins.structure.AminoAcidChain;
import ProGAL.proteins.structure.Atom;

public class LoopStructure implements PartialStructure{
	public final SecondaryStructure secondaryStructure;
	public final SSSegment segment1, segment2;
	public Atom[] targetAtoms;
	private CTLoop chaintree;
	
	
	public LoopStructure(SecondaryStructure ss, int seg1, int seg2, Atom[] targetAtoms){
		this.secondaryStructure = ss;
		this.segment1 = ss.segments[Math.min(seg1, seg2)];
		this.segment2 = ss.segments[Math.max(seg1, seg2)];
		this.targetAtoms = targetAtoms;
		this.chaintree = new CTLoop(ss.primaryStructure, segment1.start, segment2.end);
		
		//Lock helices
		for(int s=seg1+1;s<seg2;s++){
			if(ss.segments[s].type==SSType.HELIX){
				for(int r=ss.segments[s].start+1;r<ss.segments[s].end-1;r++){
					chaintree.setLocked(r-segment1.start, 0);
					chaintree.setLocked(r-segment1.start, 1);
					chaintree.setLocked(r-segment1.start, 2);
					chaintree.setTorsionAngle(r-segment1.start, 0, -60*Math.PI/180);
					chaintree.setTorsionAngle(r-segment1.start, 1, -30*Math.PI/180);
				}
			}
		}
	}
	
	public LoopStructure(SecondaryStructure ss, int seg1, int seg2){
		this(ss,seg1,seg2, new Atom[]{});
	}

	public void setFirstTransform(Matrix m){
		chaintree.setFirstTransformation(m);
	}
	
	public String toString(){
		return String.format("LoopStructure[%d-%d]",segment1.start, segment2.end-1);
	}
	
	/** 
	 * Indicates if the loop is closed, ie. if the end of the loop matches up with the target atoms.
	 */
	public boolean isClosed(){
		int loopLength = segment2.end-segment1.start;
		double sqDistSum = 0;
		for(int i=0;i<targetAtoms.length;i++){
			sqDistSum += chaintree.getBackboneAtom(loopLength, i).distanceSquared(targetAtoms[i]);
		}
		double rms = Math.sqrt(sqDistSum/(targetAtoms.length>0?targetAtoms.length:1)); 
		return rms<0.4;
	}

	public void enforceClosureCCD(){
		chaintree.closeCCD(targetAtoms, 100);
	}
	
	public void enforceClosureAnalytically(){
		//TODO
	}
	
	public void enforceClosureJacobian(){
		//TODO
	}
	
	public void rebuildCCD(){
		//TODO
	}
	
	public void rebuildAnalytically(){
		//TODO
	}
	
	public void rebuildJacobian(){
		//TODO
	}
	
	public void rebuildACO(){
		//TODO
	}
	
	@Override
	public void updateAtoms(AminoAcidChain chain) {
		Point[] atomCoords = chaintree.getAllBackboneAtoms();
		int c = 0;
		for(int r=segment1.start;r<segment2.end;r++){
			for(int a=0;a<3;a++){
				chain.atom(r, a).set(atomCoords[c++]);
			}
		}
	}

	
	
}
