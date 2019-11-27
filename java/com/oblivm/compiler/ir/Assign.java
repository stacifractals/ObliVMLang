/***
 * Copyright (C) 2015 by Chang Liu <liuchang@cs.umd.edu>
 */
package com.oblivm.compiler.ir;

import com.oblivm.compiler.type.manage.Count;
import com.oblivm.compiler.type.manage.Label;
public class Assign extends IRCode {

	public Label lab;
	public Variable name;
	public Expression exp;
	public boolean toDum;
	public Count cnt;
	
	public Assign(Label lab, Count cnt, Variable name, Expression exp, boolean toDum) {
		this.lab = lab;
		this.cnt=cnt;
		this.name = name;
		this.exp = exp;
		this.toDum = toDum;
	}
	
	public Assign(Label lab, Count cnt, Variable name, Expression exp) {
		this(lab, cnt, name, exp, false);
	}
	
	@Override
	public String toString(int indent) {
		StringBuffer sb = new StringBuffer();
		sb.append(this.indent(indent));
		if(name.toHaveType && withTypeDef)
			sb.append(name.type.toString()+" "+name.name+" = "+exp.toString()+";\n");
		else
			sb.append(name.name+" = "+exp.toString()+";\n");
		return sb.toString();
	}

	@Override
	public IRCode clone(boolean withTypeDef) {
		IRCode ret = new Assign(lab, cnt, name, exp);
		ret.withTypeDef = withTypeDef;
		return ret;
	}
	
	public String toString() {
		return toString(0);
	}
}
