package com.oblivm.compiler.ast.expr;

import com.oblivm.compiler.ast.expr.ASTAndPredicate;
import com.oblivm.compiler.ast.expr.ASTArrayExpression;
import com.oblivm.compiler.ast.expr.ASTBinaryExpression;
import com.oblivm.compiler.ast.expr.ASTBinaryPredicate;
import com.oblivm.compiler.ast.expr.ASTConstantExpression;
import com.oblivm.compiler.ast.expr.ASTFloatConstantExpression;
import com.oblivm.compiler.ast.expr.ASTFuncExpression;
import com.oblivm.compiler.ast.expr.ASTIsDummyPredicate;
import com.oblivm.compiler.ast.expr.ASTLogExpression;
import com.oblivm.compiler.ast.expr.ASTNewObjectExpression;
import com.oblivm.compiler.ast.expr.ASTNullExpression;
import com.oblivm.compiler.ast.expr.ASTOrPredicate;
import com.oblivm.compiler.ast.expr.ASTRangeExpression;
import com.oblivm.compiler.ast.expr.ASTRecExpression;
import com.oblivm.compiler.ast.expr.ASTRecTupleExpression;
import com.oblivm.compiler.ast.expr.ASTSizeExpression;
import com.oblivm.compiler.ast.expr.ASTTupleExpression;
import com.oblivm.compiler.ast.expr.ASTVariableExpression;


public interface ASTExpressionVisitor<T> {

		public T visit(ASTAndPredicate expr);
		
		public T visit(ASTArrayExpression expr);
		
		public T visit(ASTBinaryExpression expr);
		
		public T visit(ASTBinaryPredicate expr);
		
		public T visit(ASTConstantExpression expr);
		
		public T visit(ASTFloatConstantExpression expr);
		
		public T visit(ASTFuncExpression expr);
		
		public T visit(ASTIsDummyPredicate expr);

		public T visit(ASTLogExpression expr);

		public T visit(ASTNewObjectExpression expr);
		
		public T visit(ASTNullExpression expr);
		
		public T visit(ASTOrPredicate expr);
		
		public T visit(ASTRangeExpression expr);
		
		public T visit(ASTRecExpression expr);
		
		public T visit(ASTRecTupleExpression expr);
		
		public T visit(ASTSizeExpression expr);
		
		public T visit(ASTTupleExpression expr);
		
		public T visit(ASTVariableExpression expr);
	}
