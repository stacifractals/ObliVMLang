/***
 * Copyright (C) 2015 by Chang Liu <liuchang@cs.umd.edu>
 */
package com.oblivm.compiler.frontend.bloop;

import com.oblivm.compiler.ast.expr.ASTCount;
import com.oblivm.compiler.ast.expr.ASTExpression;
import com.oblivm.compiler.ast.stmt.ASTStatement;
import com.oblivm.compiler.type.manage.Count;

public class ASTBranchStatement extends ASTStatement {
	public ASTExpression pred;
	public Label goTrue, goFalse;
	public Count TrueCnt, FalseCnt;
	public String stateVar = null;
	
	// assert pred == null <=> goFalse == null
	
	public ASTBranchStatement(Label label, Count cnt) {
		pred = null;
		goFalse = null;
		goTrue = label;
		FalseCnt=null;
		TrueCnt=cnt;
	}

	public ASTBranchStatement() {
		pred = null;
		goTrue = goFalse = null;
		FalseCnt=TrueCnt=null;
	}
	
	@Override
	public String toString(int indent) {
		if(pred == null)
			return indent(indent)+"jmp -> "+goTrue+";\n";
		else
			return indent(indent)+"br "+pred+" | T -> "+goTrue+" | F -> "+goFalse+";\n";
	}

	@Override
	public ASTCount getCount() {
		// TODO Auto-generated method stub
		return convert(TrueCnt);
	}

	public ASTCount convert(Count cnt) {
		ASTCount cnty;
		if(cnt.equal(Count.Zero)) {
			cnty=ASTCount.Zero;
		}
		if(cnt.equal(Count.One)) {
			cnty=ASTCount.One;
		}
		if(cnt.equal(Count.Two)) {
			cnty=ASTCount.Two;
		}
		if(cnt.equal(Count.Three)) {
			cnty=ASTCount.Three;
		}
		else {
			cnty=ASTCount.Zero;
		}
		return cnty;
	}

}
