/***
 * Copyright (C) 2015 by Chang Liu <liuchang@cs.umd.edu>
 */
package com.oblivm.compiler.ast.expr;

public class ASTLogExpression extends ASTExpression {

	public ASTExpression exp;
	public ASTCount cnt;
	public ASTLogExpression cloneInternal() {
		return new ASTLogExpression(exp.clone());
	}
	
	public ASTLogExpression(ASTExpression exp) {
		this.exp = exp;
		this.cnt=exp.getCount();
	}
	
	public String toString() {
		return "log("+exp.toString()+")";
	}
	
	public boolean equals(Object obj) {
		if(!(obj instanceof ASTLogExpression))
			return false;
		return exp.equals(((ASTLogExpression)obj).exp); 
	}
	
	@Override
	public int level() {
		return 100;
	}

	@Override
	public ASTCount getCount() {
		// TODO Auto-generated method stub
		return cnt;
	}

}
