package com.oblivm.compiler.ast.stmt;

import com.oblivm.compiler.ast.stmt.ASTStatement;
import com.oblivm.compiler.ast.stmt.ASTAssignStatement;
import com.oblivm.compiler.ast.stmt.ASTBoundedWhileStatement;

import com.oblivm.compiler.ast.stmt.ASTDebugStatement;

import com.oblivm.compiler.ast.stmt.ASTFuncStatement;

import com.oblivm.compiler.ast.stmt.ASTIfStatement;

import com.oblivm.compiler.ast.stmt.ASTOnDummyStatement;

import com.oblivm.compiler.ast.stmt.ASTReturnStatement;

import com.oblivm.compiler.ast.stmt.ASTUsingStatement;

import com.oblivm.compiler.ast.stmt.ASTWhileStatement;

public abstract class ASTDefaultStatementVisitor<T> implements ASTStatementVisitor<T>  {

	public T visit(ASTStatement stmt) {
		if(stmt instanceof ASTStatement) {
			return visit((ASTStatement)stmt);
		} else if(stmt instanceof ASTAssignStatement) {
			return visit((ASTAssignStatement)stmt);
		} else if(stmt instanceof ASTBoundedWhileStatement) {
			return visit((ASTBoundedWhileStatement)stmt);
		} else if(stmt instanceof ASTDebugStatement) {
			return visit((ASTDebugStatement)stmt);
		} else if(stmt instanceof ASTFuncStatement) {
			return visit((ASTFuncStatement)stmt);
		} else if(stmt instanceof ASTIfStatement) {
			return visit((ASTIfStatement)stmt);
		} else if(stmt instanceof ASTOnDummyStatement) {
			return visit((ASTOnDummyStatement)stmt);
		} else if(stmt instanceof ASTReturnStatement) {
			return visit((ASTReturnStatement)stmt);
		} else if(stmt instanceof ASTUsingStatement) {
			return visit((ASTUsingStatement)stmt);
		} else if(stmt instanceof ASTWhileStatement) {
			return visit((ASTWhileStatement)stmt);
		} 
			throw new RuntimeException("Unknown stmt!");
	}


}