/***
 * Copyright (C) 2015 by Chang Liu <liuchang@cs.umd.edu>
 */
package com.oblivm.compiler.ast.stmt;

import java.util.ArrayList;
import java.util.List;

import com.oblivm.compiler.ast.expr.ASTCount;
import com.oblivm.compiler.ast.expr.ASTExpression;
import com.oblivm.compiler.ast.expr.ASTPredicate;

public class ASTBoundedWhileStatement extends ASTStatement {
	public ASTStatement init;
	public ASTPredicate cond;
	public List<ASTStatement> body;
	public ASTExpression bound;
	public ASTCount cnt;
	//let cnt be the cnt of the bound? so that it's of the internal expressions?
	public ASTBoundedWhileStatement(ASTPredicate cond, ASTExpression bound) {
		this.cond = cond;
		this.bound = bound;
		this.cnt=bound.getCount();
		this.body = new ArrayList<ASTStatement>();
	}
	
	public String toString(int indent) {
		StringBuffer sb = new StringBuffer();
		if (!(init == null)) {
			sb.append(init.toString(indent));
		}
		sb.append(this.indent(indent));
		sb.append("bwhile ("+bound+") ("+cond.toString()+") {\n");
		for(int i=0; i<body.size(); ++i)
			sb.append(body.get(i).toString(indent+1));
		sb.append(this.indent(indent));
		sb.append("}\n");
		return sb.toString();
	}

	@Override
	public ASTCount getCount() {
		// TODO Auto-generated method stub
		return cnt;
	}
}
