/***
 * Copyright (C) 2015 by Chang Liu <liuchang@cs.umd.edu>
 */
package com.oblivm.compiler.ast.expr;


public interface ExpressionVisitor<T> {

	public  T visitNull();
	
	public  T visit(ASTAndPredicate andPredicate);

	public  T visit(ASTArrayExpression arrayExpression);

	public  T visit(ASTBinaryExpression binaryExpression);
	
	public  T visit(ASTBinaryPredicate binaryPredicate);
	
	public  T visit(ASTConstantExpression constantExpression);

	public  T visit(ASTFloatConstantExpression constantExpression);

	public  T visit(ASTExpression expression);

	public  T visit(ASTFuncExpression funcExpression);

	public  T visit(ASTNewObjectExpression exp);

	public  T visit(ASTOrPredicate orPredicate);
	
	public  T visit(ASTPredicate predicate);

	public  T visit(ASTRecExpression rec);
	
	public  T visit(ASTRecTupleExpression tuple);

	public  T visit(ASTTupleExpression tuple);

	public  T visit(ASTLogExpression logExp);

	public  T visit(ASTRangeExpression rangeExp);

	public  T visit(ASTVariableExpression variableExpression);
	
	public  T visit(ASTNullExpression exp);
	
	public  T visit(ASTSizeExpression exp);
}
