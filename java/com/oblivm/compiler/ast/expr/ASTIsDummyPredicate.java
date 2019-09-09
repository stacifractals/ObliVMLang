/***
 * Copyright (C) 2015 by Chang Liu <liuchang@cs.umd.edu>
 */
package com.oblivm.compiler.ast.expr;

public class ASTIsDummyPredicate extends ASTPredicate {
	public boolean checkDummy = true;
	public ASTCount cnt;
	public ASTExpression exp;
	
	public ASTIsDummyPredicate cloneInternal() {
		return new ASTIsDummyPredicate(checkDummy, exp.clone());
	}
	
	public ASTIsDummyPredicate(boolean is, ASTExpression e) {
		this.checkDummy = is;
		this.exp = e;
		//dummy count is same as expression?
		this.cnt=e.getCount();
	}

	@Override
	public int level() {
		return 100;
	}
	
	public String toString() {
		if(checkDummy) {
			return "ISDUMMY("+exp.toString()+")";
		} else 
			return "!ISDUMMY("+exp.toString()+")";
	}

	@Override
	public ASTCount getCount() {
		// TODO Auto-generated method stub
		return null;
	}
}
