/***
 * Copyright (C) 2015 by Chang Liu <liuchang@cs.umd.edu>
 */
package com.oblivm.compiler.ast.expr;

public class ASTNullExpression extends ASTExpression {

	@Override
	public int level() {
		return 100;
	}
	
	public String toString() {
		return "null";
	}

	@Override
	public ASTNullExpression cloneInternal() {
		return new ASTNullExpression();
	}

	@Override
	//zero count for null?
	public ASTCount getCount() {
		// TODO Auto-generated method stub
		return ASTCount.Zero;
	}
}
