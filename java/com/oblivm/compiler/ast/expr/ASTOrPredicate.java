/***
 * Copyright (C) 2015 by Chang Liu <liuchang@cs.umd.edu>
 */
package com.oblivm.compiler.ast.expr;

/**
 * Defines an "or" predicate of the form "(left predicate) OR (right predicate)". 
 * Left and right predicate are both stored as member variables of type ASTPredicate.
 */
public class ASTOrPredicate extends ASTPredicate {
	public ASTPredicate left;
	public ASTPredicate right;
	public ASTCount cnt;
	public ASTOrPredicate(ASTPredicate left, ASTPredicate right) {
		this.left = left;
		this.right = right;
		
		this.cnt=right.getCount();
	}
	
	public String toString() {
		return "("+left+") or ("+right+")";
	}

	@Override
	public int level() {
		return 0;
	}

	@Override
	public ASTOrPredicate cloneInternal() {
		return new ASTOrPredicate(left.clone(), right.clone());
	}

	@Override
	public ASTCount getCount() {
		// TODO Auto-generated method stub
		return cnt;
	}

}
