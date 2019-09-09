/***
 * Copyright (C) 2015 by Chang Liu <liuchang@cs.umd.edu>
 */
/**
 * 
 */
package com.oblivm.compiler.ast.expr;

/**
 * @author Chang Liu
 *
 */
public class ASTRangeExpression extends ASTExpression {

	public ASTExpression source, rangel, ranger;
	public ASTCount cnt;
	public ASTRangeExpression(ASTExpression source, ASTExpression rangel, ASTExpression ranger) {
		this.source = source;
		this.rangel = rangel;
		this.ranger = ranger;
		//set to source count. Not sure what range does?
		this.cnt=source.getCount();
	}
	
	public String toString() {
		return source.toString()+"$"+rangel.toString()+(ranger == null ? "$" : ".."+ranger.toString()+"$");
	}
	
	@Override
	public int level() {
		return 100;
	}

	@Override
	public ASTRangeExpression cloneInternal() {
		return new ASTRangeExpression(source.clone(), rangel.clone(), ranger.clone());
	}

	@Override
	public ASTCount getCount() {
		// TODO Auto-generated method stub
		return cnt;
	}

}
