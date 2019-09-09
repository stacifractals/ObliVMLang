package com.oblivm.compiler.ast.stmt;



public interface ASTStatementVisitor<T> {

		public T visit(ASTAssignStatement stmt);
		
		public T visit(ASTBoundedWhileStatement stmt);
		
		public T visit(ASTDebugStatement stmt);
		
		public T visit(ASTFuncStatement stmt);
		
		public T visit(ASTIfStatement stmt);
		
		public T visit(ASTOnDummyStatement stmt);
		
		public T visit(ASTReturnStatement stmt);
		
		public T visit(ASTUsingStatement stmt);

		public T visit(ASTWhileStatement stmt);

		
	
	}
