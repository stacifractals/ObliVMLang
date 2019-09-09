/***
 * Copyright (C) 2015 by Chang Liu <liuchang@cs.umd.edu>
 */
package com.oblivm.compiler.ast.stmt;

import com.oblivm.compiler.ast.expr.ASTCount;
import com.oblivm.compiler.ast.expr.ASTFuncExpression;

public class ASTFuncStatement extends ASTStatement {
	public ASTFuncExpression func;
	public ASTCount cnt;
	public ASTFuncStatement(ASTFuncExpression func) {
		this.func = func;
		//set to internal count?
		this.cnt=func.getCount();
	}
	
	@Override
	public String toString(int indent) {
		return this.indent(indent)+func.toString()+";\n";
	}

	public String toString() {
		return toString(0);
	}

	@Override
	public ASTCount getCount() {
		// TODO Auto-generated method stub
		return cnt;
	}
}
