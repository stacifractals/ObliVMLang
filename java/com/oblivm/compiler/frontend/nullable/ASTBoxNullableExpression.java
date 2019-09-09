/***
 * Copyright (C) 2015 by Chang Liu <liuchang@cs.umd.edu>
 */
package com.oblivm.compiler.frontend.nullable;

import com.oblivm.compiler.ast.expr.ASTCount;
import com.oblivm.compiler.ast.expr.ASTExpression;

public class ASTBoxNullableExpression extends ASTExpression {
	public ASTExpression exp;
	public ASTCount cnt;
	public ASTBoxNullableExpression(ASTExpression exp) {
		this.exp = exp;
		this.cnt=exp.getCount();
	}

	public String toString() {
		return "Nullable("+exp+")";
	}
	
	@Override
	public int level() {
		return 100;
	}

	@Override
	public ASTBoxNullableExpression cloneInternal() {
		return new ASTBoxNullableExpression(exp.clone());
	}

	@Override
	public ASTCount getCount() {
		// TODO Auto-generated method stub
		return cnt;
	}
}
