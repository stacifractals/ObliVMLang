/***
 * Copyright (C) 2015 by Chang Liu <liuchang@cs.umd.edu>
 */
package com.oblivm.compiler.ast.expr;

import com.oblivm.compiler.type.manage.Count;

public abstract class ASTPredicate extends ASTExpression {
	public Count cnt;
	public String toString(int indent) {
		return toString();
	}
	
	public abstract ASTPredicate cloneInternal();
	
	public ASTPredicate clone() {
		ASTPredicate ret = cloneInternal();
		ret.setBeginPosition(this.beginPosition);
		ret.setEndPosition(this.endPosition);
		return ret;
	}
}
