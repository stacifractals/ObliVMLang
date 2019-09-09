/***
Created by Ana McTaggart 2019
 */
package com.oblivm.compiler.ast.stmt;

import java.util.Map;

import com.oblivm.compiler.ast.expr.ASTAndPredicate;
import com.oblivm.compiler.ast.expr.ASTCount;
import com.oblivm.compiler.ast.expr.ASTDefaultExpressionVisitor;
import com.oblivm.compiler.ast.type.ASTType;

public class ASTCountStatementComparator extends ASTDefaultStatementVisitor<Boolean> {
	private ASTCountStatementComparator() {}
	
	private static ASTCountStatementComparator inst = null;
	
	public static ASTCountStatementComparator get() {
		if(inst == null) {
			inst = new ASTCountStatementComparator();
		}
		return inst;
	}
	
	private ASTCount cnt;
	
	public boolean compare(ASTCount cnt, ASTStatement stmt) {
		this.cnt = cnt;
		return visit(stmt);
	}
	
	public Boolean visit(ASTAssignStatement stmt) {
		return true;
	}
	
	public Boolean  visit(ASTFuncStatement stmt){
		return true;
	}
	
	public Boolean visit(ASTIfStatement stmt){
		System.out.print("Visiting If"+stmt.tcnt.equal(stmt.fcnt)+"\n");

		return stmt.tcnt.equal(stmt.fcnt);

	}

	public Boolean visit(ASTReturnStatement stmt){
		return true;
	}

	public Boolean  visit(ASTStatement stmt){
		return true;
	}
	
	public Boolean visit(ASTWhileStatement stmt){
		return true;
	}
	
	public Boolean visit(ASTOnDummyStatement stmt){
		return true;
	}
	
	public Boolean visit(ASTBoundedWhileStatement stmt){
		return true;
	}
	
	public Boolean visit(ASTUsingStatement stmt){
		return true;
	}
	
	public Boolean visit(ASTDebugStatement stmt){
		return true;
	}
}