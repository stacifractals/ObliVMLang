/***
 * Copyright (C) 2015 by Chang Liu <liuchang@cs.umd.edu>
 */
package com.oblivm.compiler.ast.expr;

import java.util.ArrayList;
import java.util.List;

public class ASTTupleExpression extends ASTExpression {
	public List<ASTExpression> exps;
	public ASTCount cnt;
	//cnt needs to be equal along all sides of tuple?
	public ASTTupleExpression(ASTExpression... es) {
		exps = new ArrayList<ASTExpression>();
		for(int i=0; i<es.length; ++i) {
			exps.add(es[i]);
			//should be equal for all sides of tuple
		cnt=cnt.join(es[i].getCount());
		}
	}
	
	@Override
	public int level() {
		return -1;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<exps.size(); ++i) {
			if(i > 0)
				sb.append(", ");
			sb.append(exps.get(i).toString());
		}
		return sb.toString();
	}

	public ASTTupleExpression cloneInternal() {
		ASTTupleExpression ret = new ASTTupleExpression();
		for(ASTExpression e : exps) {
			ret.exps.add(e.clone());
		}
		return ret;
	}

	public ASTCount getCount() {
	return cnt;
	}
		/*	ASTCount cnt = ASTCount.Zero;
			for(int i=0; i<expr.size(); ++i)
				cnt = cnt.join(types.get(i).getCount());
			return cnt;
		}*/
}
