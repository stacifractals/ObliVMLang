/***
 Created by Ana McTaggart 2019.
 */
package com.oblivm.compiler.ast.type;

public class ASTCount {
	public static final ASTCount Zero = new ASTCount("zero", 0);
	public static final ASTCount One = new ASTCount("one", 1);
	public static final ASTCount Two = new ASTCount("two", 2);
	public static final ASTCount Three = new ASTCount("three", 3);
	
	private String name;
	private int id;
	
	public static int getCountNumber() {
		return 6;
	}
	
	private ASTCount() {};
	private ASTCount(String name, int id) { this.name = name; this.id = id; };
	
	public String toString() {
		return name;
	}
	
	public boolean less(ASTCount cnt) {
		if(this == Zero || cnt == Zero || this == cnt)
			return true;
		return false;
	}
	public boolean equal(ASTCount cnt) {
		if(this==cnt) {
		return true;
		}
		else {
			return false;
		}
		
		
	}
	
	
	
	public int getId() {
		return id;
	}
	
	/***
	 * this cup cnt
	 * @param cnt
	 * @return
	 */
	public ASTCount meet(ASTCount cnt) {
		if(this == ASTCount.Zero)
			return cnt;
		if(cnt == ASTCount.Zero)
			return this;
		if(this == cnt)
			return this;
		return ASTCount.Three;
	}

	/***
	 * this cap cnt
	 * @param cntel
	 * @return
	 */
	public ASTCount join(ASTCount cnt) {
		if(this == ASTCount.Three)
			 return cnt;
		if(cnt == ASTCount.Three)
			return this;
		if(cnt == this)
			return this;
		return Zero;
	}
}
