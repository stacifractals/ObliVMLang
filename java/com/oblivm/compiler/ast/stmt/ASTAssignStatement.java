/***
 * Copyright (C) 2015 by Chang Liu <liuchang@cs.umd.edu>
 */
package com.oblivm.compiler.ast.stmt;

import com.oblivm.compiler.ast.expr.ASTCount;
import com.oblivm.compiler.ast.expr.ASTExpression;

/**
 * Defines an assignment statement of the form "var = expr;" 
 * where var is of type ASTVariable and expr is of type ASTExpression. 
 * @see ASTVariable
 * @see ASTExpression
 */
public class ASTAssignStatement extends ASTStatement {
	public ASTExpression var;
	public ASTExpression expr;
	public ASTCount cnt=ASTCount.One;
	public ASTAssignStatement(ASTExpression v, ASTExpression e) {
		this.var = v;
		this.expr = e;
		//as many counts as required for the expression
		this.cnt=e.getCount();
	}

	@Override
	public String toString(int indent) {
		StringBuffer sb = new StringBuffer();
		sb.append(this.indent(indent));
		sb.append(toString());
		sb.append("\n");
		return sb.toString();
	}
	
	public String toString() {
		return var+"="+expr.toString(0)+";";
	}

	@Override
	public ASTCount getCount() {
		// TODO Auto-generated method stub
		return cnt;
	}
}
