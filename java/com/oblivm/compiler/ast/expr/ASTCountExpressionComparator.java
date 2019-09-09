/***
Created by Ana McTaggart 2019
 */
package com.oblivm.compiler.ast.expr;

import java.util.Map;

import com.oblivm.compiler.ast.expr.ASTDefaultExpressionVisitor;
import com.oblivm.compiler.ast.type.ASTType;

public class ASTCountExpressionComparator extends ASTDefaultExpressionVisitor<Boolean> {
	private ASTCountExpressionComparator() {}
	
	private static ASTCountExpressionComparator inst = null;
	
	public static ASTCountExpressionComparator get() {
		if(inst == null) {
			inst = new ASTCountExpressionComparator();
		}
		return inst;
	}
	
	private ASTCount cnt;
	
	public boolean compare(ASTCount cnt, ASTExpression expr) {
		this.cnt = cnt;
		return visit(expr);
	}

	@Override
	public Boolean visit(ASTAndPredicate expr) {
		// TODO Auto-generated method stub
		System.out.print("Visiting And"+ expr.left.cnt.equal(expr.right.cnt)+"\n");
		return expr.left.cnt.equal(expr.right.cnt);

	}

	@Override
	public Boolean visit(ASTArrayExpression expr) {
		// TODO Auto-generated method stub
		//using index expression
		System.out.print("Visiting Array"+visit(expr.indexExpr)+"\n");

		return visit(expr.indexExpr);
	}

	@Override
	public Boolean visit(ASTBinaryExpression expr) {
		// TODO Auto-generated method stub
		return expr.left.cnt.equal(expr.right.cnt);
	}

	@Override
	public Boolean visit(ASTBinaryPredicate expr) {
		return expr.left.cnt.equal(expr.right.cnt);

	}

	@Override
	public Boolean visit(ASTConstantExpression expr) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Boolean visit(ASTFloatConstantExpression expr) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Boolean visit(ASTFuncExpression expr) {
		// TODO Auto-generated method stub
		return cnt.equal(expr.getCount());
	}

	@Override
	public Boolean visit(ASTIsDummyPredicate expr) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Boolean visit(ASTLogExpression expr) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Boolean visit(ASTNewObjectExpression expr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean visit(ASTNullExpression expr) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Boolean visit(ASTOrPredicate expr) {
		// TODO Auto-generated method stub
		return expr.left.cnt.equal(expr.right.cnt);
	}

	@Override
	public Boolean visit(ASTRangeExpression expr) {
		// TODO Auto-generated method stub
		return visit(expr);
	}

	@Override
	public Boolean visit(ASTRecExpression expr) {
		// TODO Auto-generated method stub
		return visit(expr);
	}

	@Override
	public Boolean visit(ASTRecTupleExpression expr) {
		// TODO Auto-generated method stub
		return visit(expr);
	}

	@Override
	public Boolean visit(ASTSizeExpression expr) {
		// TODO Auto-generated method stub
		return visit(expr);
		}

	@Override
	public Boolean visit(ASTTupleExpression expr) {
		// TODO Auto-generated method stub
		return  cnt.equal(expr.getCount());
	}

	@Override
	public Boolean visit(ASTVariableExpression expr) {
		// TODO Auto-generated method stub
		return visit(expr);
	}


}
