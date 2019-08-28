/***
Created by Ana McTaggart 2019
 */
package com.oblivm.compiler.ast.type;

import java.util.Map;

public class ASTCountTypeComparator extends ASTDefaultTypeVisitor<Boolean> {
	private ASTCountTypeComparator() {}
	
	private static ASTCountTypeComparator inst = null;
	
	public static ASTCountTypeComparator get() {
		if(inst == null) {
			inst = new ASTCountTypeComparator();
		}
		return inst;
	}
	
	private ASTCount cnt;
	
	public boolean compare(ASTCount cnt, ASTType type) {
		this.cnt = cnt;
		return visit(type);
	}
	
	@Override
	public Boolean visit(ASTArrayType type) {
		return visit(type.type);
	}

	@Override
	public Boolean visit(ASTIntType type) {
		return cnt.less(type.getCount());
	}

	@Override
	public Boolean visit(ASTFloatType type) {
		return cnt.less(type.getCount());
	}

	@Override
	public Boolean visit(ASTRndType type) {
		return cnt.less(type.getCount());
	}

	@Override
	public Boolean visit(ASTNativeType type) {
		return false;
	}

	@Override
	public Boolean visit(ASTRecType type) {
		for(Map.Entry<String, ASTType> ent : type.fieldsType.entrySet()) {
			if(!visit(ent.getValue()))
				return false;
		}
		return true;
	}

	@Override
	public Boolean visit(ASTVariableType type) {
		// TODO variable type is by default secure; relax this notion
		return true;
	}

	@Override
	public Boolean visit(ASTVoidType type) {
		return true;
	}

	@Override
	public Boolean visit(ASTFunctionType type) {
		return true;
	}

	@Override
	public Boolean visit(ASTNullType type) {
		return true;
	}

	@Override
	public Boolean visit(ASTDummyType type) {
		// TODO dummy type is by default secure; need to change this later.
		return visit(type.type);
	}

	@Override
	public Boolean visit(ASTTupleType type) {
		for(ASTType ty : type.types)
			if(!visit(ty))
				return false;
		
		return true;
	}

}
