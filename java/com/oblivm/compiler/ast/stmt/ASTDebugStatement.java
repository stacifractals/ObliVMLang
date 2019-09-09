package com.oblivm.compiler.ast.stmt;

import com.oblivm.compiler.ast.expr.ASTCount;
import com.oblivm.compiler.ast.expr.ASTExpression;

public class ASTDebugStatement extends ASTStatement {
	public ASTExpression exp;
	public ASTCount cnt;
	public ASTDebugStatement(ASTExpression exp) {
		this.exp = exp;
		this.cnt=exp.getCount();
	}
	
	@Override
	public String toString(int indent) {
		return indent(indent) + toString();
	}
	
	public String toString() {
		return "debug("+exp.toString()+")";
	}

	@Override
	public ASTCount getCount() {
		// TODO Auto-generated method stub
		return cnt;
	}
	
}
