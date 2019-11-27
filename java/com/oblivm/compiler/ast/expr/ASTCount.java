/***
 Created by Ana McTaggart 2019.
 */
package com.oblivm.compiler.ast.expr;

import com.oblivm.compiler.type.manage.Count;

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
	public ASTCount join(ASTCount cnt) {
		if(this == ASTCount.Zero && cnt==ASTCount.Zero)
			return ASTCount.Zero;
		if(cnt == ASTCount.One && this == ASTCount.Zero)
			return ASTCount.One;
		if(cnt==ASTCount.Zero && this == ASTCount.One)
			return ASTCount.One;
		if(cnt == ASTCount.One && this == ASTCount.One)
			return ASTCount.Two;
		if(cnt==ASTCount.Two && this == ASTCount.Zero)
			return ASTCount.Two;
		if(cnt == ASTCount.Zero && this == ASTCount.Two)
			return ASTCount.Two;
		if(cnt==ASTCount.One && this == ASTCount.Two)
			return ASTCount.Three;
		if(cnt == ASTCount.Two && this == ASTCount.One)
			return ASTCount.Three;
		if(cnt==ASTCount.Three && this == ASTCount.Zero)
			return ASTCount.Three;
		if(cnt == ASTCount.Zero && this == ASTCount.Three)
			return ASTCount.Three;
	return cnt;
	}
	

	/***
	 * this cap cnt
	 * @param cntel
	 * @return
	 */
	/*public ASTCount meet(ASTCount cnt) {
		if(this == ASTCount.Three)
			 return cnt;
		if(cnt == ASTCount.Three)
			return this;
		if(cnt == this)
			return this;
		return Zero;
	}*/
}
