/***
 * Copyright (C) 2015 by Chang Liu <liuchang@cs.umd.edu>
 */
package com.oblivm.compiler.ast.type;

import com.oblivm.compiler.ast.expr.ASTConstantExpression;
import com.oblivm.compiler.ast.expr.ASTExpression;

public class ASTIntType extends ASTType {

	public ASTExpression bit;
	private ASTLabel lab;
	private ASTCount cnt;

	public ASTExpression getBits() {
		return bit;
	}

	public ASTLabel getLabel() {
		return lab;
	}

	public static ASTIntType get(int bit, ASTLabel lab, ASTCount cnt) {
		return ASTIntType.get(new ASTConstantExpression(bit), lab, cnt);
	}

	public static ASTIntType get(ASTExpression bit, ASTLabel lab, ASTCount cnt) {
		return new ASTIntType(bit, lab, cnt);
	}

	private ASTIntType(ASTExpression bit, ASTLabel lab, ASTCount cnt) {
		this.bit = bit;
		this.lab = lab;
		this.cnt=cnt;
	}

	public String toString(int indent) {
		return toString();
	}

	public String toString() {
		if(bit == null)
			return lab.toString() + " " + "int";
		else
			return lab.toString() + " " + "int"+(bit instanceof ASTConstantExpression ? bit.toString() : "@("+bit.toString()+")");
	}

	public boolean equals(Object obj) {
		if(!(obj instanceof ASTIntType))
			return false;
		ASTIntType other = (ASTIntType)obj;
		return bit.equals(other.bit) && lab == other.lab && cnt==other.cnt;
	}
//did not add in count for flow. 
	@Override
	public boolean canFlowTo(ASTType type) {
		if(type instanceof ASTDummyType)
			return canFlowTo(((ASTDummyType)type).type);
		if(type instanceof ASTRndType) {
			// Integer cannot flow to Random Type
			return false;
		} else if(type instanceof ASTIntType) {
			ASTIntType it = (ASTIntType)type;
			boolean labely=this.lab.less(it.lab);
			boolean county=this.cnt.equal(it.cnt);
			System.out.print(this.toString());
			if(labely=true&&county==true) {
				return true;
			}
			else {	return false;}
		}
		return false;
	}

	@Override
	public String shortName() {
		return toString();
	}

	public ASTCount getCount() {
		// TODO Auto-generated method stub
		return cnt;
	}



}
