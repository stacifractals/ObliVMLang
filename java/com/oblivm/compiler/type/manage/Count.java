/***
 Created by Ana McTaggart 2019.
 */
package com.oblivm.compiler.type.manage;

import com.oblivm.compiler.ast.expr.ASTCount;

public class Count {
	public static final Count Zero = new Count("zero", 0);
	public static final Count One = new Count("one", 1);
	public static final Count Two = new Count("two", 2);
	public static final Count Three = new Count("three", 3);
	
	private String name;
	private int id;
	
	public static int getCountNumber() {
		return 6;
	}
	
	private Count() {};
	private Count(String name, int id) { this.name = name; this.id = id; };
	
	public String toString() {
		return name;
	}
	
	
	public boolean equal(Count cnt) {
		if(this==cnt) {
		return true;
		}
		else {
			return false;
		}
		
		
	}
	
	public static Count get(ASTCount cnt) {
	//	Count cnty;
		if(cnt.equal(ASTCount.Zero)) {
			return Count.Zero;
		}
		if(cnt.equal(ASTCount.One)) {
			return Count.One;
		}
		if(cnt.equal(ASTCount.Two)) {
			return Count.Two;
		}
		if(cnt.equal(ASTCount.Three)) {
			return Count.Three;
		}
		

		else
			throw new RuntimeException("Unknown label.");
	}
	
	
	public int getId() {
		return id;
	}
	
	/***
	 * this cup cnt
	 * @param cnt
	 * @return
	 */
	public Count join(Count cnt) {
		if(this == Count.Zero && cnt==Count.Zero)
			return cnt;
		if(cnt == Count.One && this == Count.Zero)
			return cnt;
		if(cnt==Count.Zero && this == Count.One)
			return this;
		if(cnt == Count.One && this == Count.One)
			return Count.Two;
		if(cnt==Count.Two && this == Count.Zero)
			return cnt;
		if(cnt == Count.Zero && this == Count.Two)
			return this;
		if(cnt==Count.One && this == Count.Two)
			return Count.Three;
		if(cnt == Count.Two && this == Count.One)
			return Count.Three;
		if(cnt==Count.Three && this == Count.Zero)
			return cnt;
		if(cnt == Count.Zero && this == Count.Three)
			return this;
		return null;
	}
public Count convert(ASTCount cnt) {
	Count cnty;
	if(cnt.equal(ASTCount.Zero)) {
		cnty=Count.Zero;
	}
	if(cnt.equal(ASTCount.One)) {
		cnty=Count.One;
	}
	if(cnt.equal(ASTCount.Two)) {
		cnty=Count.Two;
	}
	if(cnt.equal(ASTCount.Three)) {
		cnty=Count.Three;
	}
	else {
		cnty=Count.Zero;
	}
	return cnty;
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
