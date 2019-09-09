/***
 * Copyright (C) 2015 by Chang Liu <liuchang@cs.umd.edu>
 */
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
import com.oblivm.compiler.ast.expr.ASTTupleExpression;
import com.oblivm.compiler.ast.expr.ASTVariableExpression;

public abstract class ASTDefaultExpressionVisitor<T> implements ASTExpressionVisitor<T> {
	public T visit(ASTExpression expr) {
		if(expr instanceof ASTAndPredicate) {
			return visit((ASTAndPredicate)expr);
		} else if(expr instanceof ASTArrayExpression) {
			return visit((ASTArrayExpression)expr);
		} else if(expr instanceof ASTBinaryExpression) {
			return visit((ASTBinaryExpression)expr);
		} else if(expr instanceof ASTBinaryPredicate) {
			return visit((ASTBinaryPredicate)expr);
		} else if(expr instanceof ASTConstantExpression) {
			return visit((ASTConstantExpression)expr);
		} else if(expr instanceof ASTFloatConstantExpression) {
			return visit((ASTFloatConstantExpression)expr);
		} else if(expr instanceof ASTFuncExpression) {
			return visit((ASTFuncExpression)expr);
		} else if(expr instanceof ASTIsDummyPredicate) {
			return visit((ASTIsDummyPredicate)expr);
		} else if(expr instanceof ASTLogExpression) {
			return visit((ASTLogExpression)expr);
		} else if(expr instanceof ASTNewObjectExpression) {
			return visit((ASTNewObjectExpression)expr);
		} else if(expr instanceof ASTNullExpression) {
			return visit((ASTNullExpression)expr);
		} else if(expr instanceof ASTOrPredicate) {
			return visit((ASTOrPredicate)expr);
		} else if(expr instanceof ASTRangeExpression) {
			return visit((ASTRangeExpression)expr);
		}else if(expr instanceof ASTRecExpression) {
			return visit((ASTRecExpression)expr);
		}else if(expr instanceof ASTRecTupleExpression) {
			return visit((ASTRecTupleExpression)expr);
		}else if(expr instanceof ASTSizeExpression) {
			return visit((ASTSizeExpression)expr);
		}else if(expr instanceof ASTTupleExpression) {
			return visit((ASTTupleExpression)expr);
		}else if(expr instanceof ASTVariableExpression) {
			return visit((ASTVariableExpression)expr);
		}
			throw new RuntimeException("Unknown expr!");
	}


}
