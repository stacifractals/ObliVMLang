/***
 * Copyright (C) 2015 by Chang Liu <liuchang@cs.umd.edu>
 */
package com.oblivm.compiler.frontend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oblivm.compiler.ast.ASTFunction;
import com.oblivm.compiler.ast.ASTFunctionDef;
import com.oblivm.compiler.ast.ASTFunctionNative;
import com.oblivm.compiler.ast.ASTProgram;
import com.oblivm.compiler.ast.DefaultVisitor;
import com.oblivm.compiler.ast.expr.ASTAndPredicate;
import com.oblivm.compiler.ast.expr.ASTArrayExpression;
import com.oblivm.compiler.ast.expr.ASTBinaryExpression;
import com.oblivm.compiler.ast.expr.ASTBinaryExpression.BOP;
import com.oblivm.compiler.ast.expr.ASTBinaryPredicate;
import com.oblivm.compiler.ast.expr.ASTBinaryPredicate.REL_OP;
import com.oblivm.compiler.ast.expr.ASTConstantExpression;
import com.oblivm.compiler.ast.expr.ASTCount;
import com.oblivm.compiler.ast.expr.ASTExpression;
import com.oblivm.compiler.ast.expr.ASTFloatConstantExpression;
import com.oblivm.compiler.ast.expr.ASTFuncExpression;
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
import com.oblivm.compiler.ast.stmt.ASTAssignStatement;
import com.oblivm.compiler.ast.stmt.ASTBoundedWhileStatement;
import com.oblivm.compiler.ast.stmt.ASTDebugStatement;
import com.oblivm.compiler.ast.stmt.ASTFuncStatement;
import com.oblivm.compiler.ast.stmt.ASTIfStatement;
import com.oblivm.compiler.ast.stmt.ASTOnDummyStatement;
import com.oblivm.compiler.ast.stmt.ASTReturnStatement;
import com.oblivm.compiler.ast.stmt.ASTStatement;
import com.oblivm.compiler.ast.stmt.ASTUsingStatement;
import com.oblivm.compiler.ast.stmt.ASTWhileStatement;
import com.oblivm.compiler.ast.type.ASTArrayType;
import com.oblivm.compiler.ast.type.ASTDummyType;
import com.oblivm.compiler.ast.type.ASTFloatType;
import com.oblivm.compiler.ast.type.ASTFunctionType;
import com.oblivm.compiler.ast.type.ASTIntType;
import com.oblivm.compiler.ast.type.ASTNativeType;
import com.oblivm.compiler.ast.type.ASTNullType;
import com.oblivm.compiler.ast.type.ASTRecType;
import com.oblivm.compiler.ast.type.ASTRndType;
import com.oblivm.compiler.ast.type.ASTTupleType;
import com.oblivm.compiler.ast.type.ASTType;
import com.oblivm.compiler.ast.type.ASTVariableType;
import com.oblivm.compiler.ast.type.ASTVoidType;
import com.oblivm.compiler.backend.flexsc.Config;
import com.oblivm.compiler.frontend.bloop.ASTBranchStatement;
import com.oblivm.compiler.frontend.bloop.BoundedLoopRewritter;
import com.oblivm.compiler.frontend.nullable.ASTBoxNullableExpression;
import com.oblivm.compiler.frontend.nullable.ASTGetValueExpression;
import com.oblivm.compiler.frontend.nullable.NullableRewriter;
import com.oblivm.compiler.ir.ArrayAssign;
import com.oblivm.compiler.ir.ArrayExp;
import com.oblivm.compiler.ir.Assign;
import com.oblivm.compiler.ir.BopExp;
import com.oblivm.compiler.ir.BopExp.Op;
import com.oblivm.compiler.ir.BoxNullExp;
import com.oblivm.compiler.ir.CheckNullExp;
import com.oblivm.compiler.ir.ConstExp;
import com.oblivm.compiler.ir.Debug;
import com.oblivm.compiler.ir.EnforceBitExp;
import com.oblivm.compiler.ir.Expression;
import com.oblivm.compiler.ir.FuncCallExp;
import com.oblivm.compiler.ir.GetValueExp;
import com.oblivm.compiler.ir.IRCode;
import com.oblivm.compiler.ir.If;
import com.oblivm.compiler.ir.LocalFuncCallExp;
import com.oblivm.compiler.ir.LogExp;
import com.oblivm.compiler.ir.MuxExp;
import com.oblivm.compiler.ir.NewObjExp;
import com.oblivm.compiler.ir.NullExp;
import com.oblivm.compiler.ir.RangeAssign;
import com.oblivm.compiler.ir.RangeExp;
import com.oblivm.compiler.ir.RecExp;
import com.oblivm.compiler.ir.RecordAssign;
import com.oblivm.compiler.ir.Ret;
import com.oblivm.compiler.ir.ReverseRecordAssign;
import com.oblivm.compiler.ir.RopExp;
import com.oblivm.compiler.ir.Seq;
import com.oblivm.compiler.ir.SizeofExp;
import com.oblivm.compiler.ir.Skip;
import com.oblivm.compiler.ir.UnaryOpExp;
import com.oblivm.compiler.ir.UsingBlock;
import com.oblivm.compiler.ir.VarExp;
import com.oblivm.compiler.ir.Variable;
import com.oblivm.compiler.ir.While;
import com.oblivm.compiler.log.Bugs;
import com.oblivm.compiler.log.Info;
import com.oblivm.compiler.type.manage.ArrayType;
import com.oblivm.compiler.type.manage.BOPVariableConstant;
import com.oblivm.compiler.type.manage.BitVariable;
import com.oblivm.compiler.type.manage.Constant;
import com.oblivm.compiler.type.manage.Count;
import com.oblivm.compiler.type.manage.DummyType;
import com.oblivm.compiler.type.manage.FloatType;
import com.oblivm.compiler.type.manage.FunctionType;
import com.oblivm.compiler.type.manage.IntType;
import com.oblivm.compiler.type.manage.Label;
import com.oblivm.compiler.type.manage.LogVariable;
import com.oblivm.compiler.type.manage.Method;
import com.oblivm.compiler.type.manage.NativeType;
import com.oblivm.compiler.type.manage.NullType;
import com.oblivm.compiler.type.manage.RecordType;
import com.oblivm.compiler.type.manage.RndType;
import com.oblivm.compiler.type.manage.SizeofVariable;
import com.oblivm.compiler.type.manage.Type;
import com.oblivm.compiler.type.manage.TypeManager;
import com.oblivm.compiler.type.manage.VariableConstant;
import com.oblivm.compiler.type.manage.VariableType;
import com.oblivm.compiler.type.manage.VoidType;
import com.oblivm.compiler.type.source.TypeChecker;
import com.oblivm.compiler.util.Pair;

public class FrontendCompiler extends DefaultVisitor<IRCode, Pair<List<Variable>, IRCode>, Type> implements IFrontEndCompiler {

	private TypeChecker tc = new TypeChecker();
	private BitInferenceEngine bie = new BitInferenceEngine();

	private ASTProgram program;
	private ASTFunctionDef function;

	public Map<String, Variable> variableValues;

	public NullableRewriter nh = new NullableRewriter();

	private BoundedLoopRewritter blr = new BoundedLoopRewritter();

	public FrontendCompiler() {
	}

	public IRCode visit(ASTStatement statement) {
		//System.out.println("Statement Visitor");
		if(statement instanceof ASTBranchStatement) {
			return visit((ASTBranchStatement)statement);
		} else {
			return super.visit(statement);
		}
	}

	private int varNum = 0;

	private String newTempVar() {
		return "__tmp"+(varNum++);
	}

	private Variable currentCond = null;

//	private boolean phantom = true;

	public TypeManager compile(ASTProgram program) {
		if(!tc.check(program)) {
			Bugs.LOG.log("The program does not type check.");
			//System.exit(1);
		}
		Info.LOG.log("The program type checks");
		//s("In Compiler FrontEnd \n");
		return translate(program);
	}

	public TypeManager translate(ASTProgram program) {
		TypeManager tm = new TypeManager();
		this.program = program;
		nh.reviseProgram(program);
		for(ASTFunction function : program.functionDef) {
			if(function instanceof ASTFunctionDef) {
				this.function = (ASTFunctionDef)function;
				blr.rewrite(this.function);
			}
		}
		this.bie.process(program);

		for(Pair<String, ASTType> i : program.typeDef) {
			if((i.right instanceof ASTRecType) 
					&& ((ASTRecType)i.right).name.equals(i.left)) {
				tm.put(i.left, visit(i.right));
			}
		}

		Map<String, Variable> func = new HashMap<String, Variable>();
		for(ASTFunction function : program.functionDef) {
			if(function.baseType == null) {
				Type ty = visit(function.getType());
				func.put(function.name, new Variable(ty, Label.Pub, Count.Zero, function.name));
			}
		}

		for(ASTFunction function : program.functionDef) {
			if(function instanceof ASTFunctionDef) {
				this.function = (ASTFunctionDef)function;
				Type baseType;
				if(this.function.baseType == null)
					baseType = null;
				else
					baseType = visit(this.function.baseType);
				List<Pair<Type, String>> inputs = new ArrayList<Pair<Type, String>>();
				for(Pair<ASTType, String> arg : this.function.inputVariables) {
					inputs.add(new Pair<Type, String>(visit(arg.left), arg.right));				
				}
				List<Pair<Type, String>> local = new ArrayList<Pair<Type, String>>();
				for(Pair<ASTType, String> arg : this.function.localVariables) {
					local.add(new Pair<Type, String>(visit(arg.left), arg.right));				
				}
				Method method = new Method(baseType, 
						visit(this.function.returnType),
						this.function.name,
						function.bitParameter,
						inputs, local);
				if(function.typeVariables != null) {
					for(int i=0; i<function.typeVariables.size(); ++i) {
						method.typeParameters.add(new VariableType(function.typeVariables.get(i).var));
					}
				}
				method.code = new Skip();
				this.variableValues = new HashMap<String, Variable>(func);
				if(baseType != null) {
					this.variableValues.put("this", new Variable(baseType, baseType.getLabel(), Count.Zero, "this"));
					if(baseType instanceof RecordType) {
						RecordType rt = (RecordType)baseType;
						for(VariableConstant vc : rt.bits) {
							String s = ((BitVariable)vc).var;
							this.variableValues.put(s, new Variable(new IntType(32, Label.Pub), Label.Pub, Count.Zero, s));
						}
						for(Map.Entry<String, Type> ent : rt.fields.entrySet()) {
							this.variableValues.put(ent.getKey(), new Variable(ent.getValue(), ent.getValue().getLabel(), Count.Zero, ent.getKey()));
						}
					}
				}
				for(String v : this.function.bitParameter) {
					this.variableValues.put(v, new Variable(new IntType(32, Label.Pub), Label.Pub, Count.Zero, v));
				}
				for(Pair<ASTType, String> arg : this.function.inputVariables) {
					Type type = visit(arg.left);
					this.variableValues.put(arg.right, new Variable(type, type.getLabel(), Count.Zero, arg.right));
				}
				for(Pair<ASTType, String> arg : this.function.localVariables) {
					Type type = visit(arg.left);
					this.variableValues.put(arg.right, new Variable(type, type.getLabel(), Count.Zero, arg.right));
				}
				//this.phantom = function.isDummy;
			//	method.isPhantom = phantom;
				if(this.function.isDummy) {
					// TODO
				}
				for(ASTStatement stmt : this.function.body)
					method.code = Seq.seq(method.code, visit(stmt));
				tm.addMethod(baseType, method);
			}
		}

		for(Pair<ASTFunctionType, ASTType> ent : program.functionTypeMapping) {
			tm.add((FunctionType)visit(ent.left), ent.right);
		}

		return tm;
	}

	public VariableConstant constructConstant(ASTExpression exp) {
		if(exp instanceof ASTLogExpression) {
			return new LogVariable(constructConstant(((ASTLogExpression)exp).exp));
		} else if(exp instanceof ASTSizeExpression) {
			return new SizeofVariable(visit(((ASTSizeExpression)exp).type));
		} else if(exp instanceof ASTConstantExpression) {
			return new Constant(((ASTConstantExpression)exp).value);
		} else if(exp instanceof ASTVariableExpression) {
			return new BitVariable(((ASTVariableExpression)exp).var);
		} else if(exp instanceof ASTBinaryExpression) {
			ASTBinaryExpression be = (ASTBinaryExpression)exp;
			VariableConstant left = constructConstant(be.left);
			VariableConstant right = constructConstant(be.right);
			Op op = null;
			if(left == null || right == null)
				return null;
			if(be.op == ASTBinaryExpression.BOP.ADD)
				op = Op.Add;
			else if(be.op == ASTBinaryExpression.BOP.SUB)
				op = Op.Sub;
			else if(be.op == ASTBinaryExpression.BOP.MUL)
				op = Op.Mul;
			else if(be.op == ASTBinaryExpression.BOP.DIV)
				op = Op.Div;
			else if(be.op == ASTBinaryExpression.BOP.MOD)
				op = Op.Mod;
			else if(be.op == ASTBinaryExpression.BOP.AND)
				op = Op.And;
			else if(be.op == ASTBinaryExpression.BOP.OR)
				op = Op.Or;
			else if(be.op == ASTBinaryExpression.BOP.XOR)
				op = Op.Xor;
			else if(be.op == ASTBinaryExpression.BOP.SHL)
				op = Op.Shl;
			else if(be.op == ASTBinaryExpression.BOP.SHR)
				op = Op.Shr;
			if(op == null)
				return null;
			return new BOPVariableConstant(left, op, right);
		}
		return null;
	}

	@Override
	public Type visit(ASTArrayType type) {
		//System.out.println("ArrayType");

		return new ArrayType(constructConstant(type.size), Label.get(type.lab), visit(type.type));
	}

	@Override
	public Type visit(ASTIntType type) {
		//System.out.println("IntType");

		return new IntType(constructConstant(type.getBits()), Label.get(type.getLabel()));
	}

	@Override
	public Type visit(ASTNativeType type) {
		//System.out.println("NativeType");

		// TODO raw name
		if(type.constructor == null) {
			return new NativeType(type.name, type.name, null);
		} else {
			List<VariableConstant> list = new ArrayList<VariableConstant>();
			for(int i=0; i<type.constructor.size(); ++i) {
				list.add(this.constructConstant(type.constructor.get(i)));
			}
			return new NativeType(type.name, type.name, list);
		}
	}

	@Override
	public Type visit(ASTRecType type) {
		//System.out.println("RecType");

		List<VariableConstant> vc = new ArrayList<VariableConstant>();
		for(int i = 0; i < type.bitVariables.size(); ++i)
			vc.add(this.constructConstant(type.bitVariables.get(i)));
		RecordType ret = new RecordType(type, vc);
		if(type.typeVariables != null) {
			for(ASTType ty : type.typeVariables) {
				ret.typeParameter.add(visit(ty));
			}
		}
		for(String s : type.fields) {
			ret.fields.put(s, visit(type.fieldsType.get(s)));
		}
		return ret;
	}

	@Override
	public Type visit(ASTVariableType type) {
		//System.out.println("Variable Type");

		return new VariableType(type.var);
	}

	@Override
	public Type visit(ASTVoidType type) {
		//System.out.println("VoidType");

		return VoidType.get();
	}

	private static Variable phantomVariable = null;

	private static Variable getPhantomVariable() {
		//System.out.println("GetPhantomVariable");

		if(phantomVariable == null) {
		//	phantomVariable =
			//		new Variable(new IntType(1, Label.Secure), Label.Secure, Count.Zero, Config.phantomVariable);
		}
		return null;
		//return phantomVariable;
	}

	public IRCode translateAssign(ASTArrayExpression exp, Variable value) {
		//System.out.println("Translate Assign1");

		Pair<List<Variable>, IRCode> tmp = visit(exp.var);
		IRCode code = tmp.right;
		Variable v = tmp.left.get(0);
		tmp = visit(exp.indexExpr);
		code = Seq.seq(code, tmp.right);
		Variable i = tmp.left.get(0);
		ArrayType at = (ArrayType)v.type;
		if(this.currentCond == null) {
			code = Seq.seq(code,
					new ArrayAssign(at.indexLab.lab, v, i, value));
		} else {
			Type type = at.type;
			Variable dumb = new Variable(type, type.getLabel(), Count.Zero, newTempVar());
			code = Seq.seq(code, new Assign(dumb.lab, dumb.cnt, dumb, new ArrayExp(v, i)));
			Variable dumb1 = new Variable(type, Label.Secure, Count.Zero, newTempVar());
			code = Seq.seq(code, new Assign(Label.Secure, Count.Zero, dumb1, 
					new MuxExp(this.currentCond, value, dumb)));
			code = Seq.seq(code,
					new ArrayAssign(at.indexLab.lab, v, i, dumb1));
		}
		return code;
	}

	public IRCode translateAssign(ASTRangeExpression exp, Variable value) {
		//System.out.println("Translate Assign2");
		Pair<List<Variable>, IRCode> tmp = visit(exp.source);
		IRCode code = tmp.right;
		Variable v = tmp.left.get(0);
		VariableConstant ll = this.constructConstant(exp.rangel);
		VariableConstant rr = exp.ranger == null ? null : this.constructConstant(exp.ranger);
		if(this.currentCond == null ) {
			code = Seq.seq(code,
					new RangeAssign(v.lab, v, ll, rr, value));
		} else {
			Type type = new IntType(
					rr == null ? new Constant(1) : new BOPVariableConstant(rr, Op.Sub, ll), 
							v.lab);
			Variable dumb = new Variable(type, type.getLabel(), Count.Zero, newTempVar());
			code = Seq.seq(code, new Assign(dumb.lab, Count.Zero, dumb, new RangeExp(dumb.lab, v, ll, rr)));
			Variable dumb1 = new Variable(type, Label.Secure, Count.Zero, newTempVar());
			code = Seq.seq(code, new Assign(Label.Secure, Count.Zero, dumb1, new MuxExp( currentCond, value, dumb)));
	
		}
		return code;
	}

	private IRCode translateAssign(Variable base, String field, Variable value) {
		//System.out.println("Translate Assign3");
		if(this.currentCond == null) {
			return new RecordAssign(base.type.getLabel(), base, field, value);
		} else {
			Type type = ((RecordType)base.type).fields.get(field);
			Variable dumb = new Variable(type, type.getLabel(), Count.Zero, newTempVar());
			IRCode code = new Assign(dumb.lab, Count.Zero, dumb, new RecExp(type.getLabel(), base, field));
			Variable dumb1 = new Variable(type, Label.Secure, Count.Zero, newTempVar());
			code = Seq.seq(code, new Assign(Label.Secure, Count.Zero, dumb1, 
					new MuxExp( currentCond, value, dumb)));
			code = Seq.seq(code,
					new RecordAssign(base.type.getLabel(), base, field, dumb1));
			return code;
		}
	}

	public IRCode translateAssign(ASTRecExpression exp, Variable value) {
		//System.out.println("Translate Assign4");
		Pair<List<Variable>, IRCode> tmp = visit(exp.base);
		IRCode code = tmp.right;
		return Seq.seq(code, translateAssign(tmp.left.get(0), exp.field, value));
	}

	public IRCode translateAssign(ASTTupleExpression exp, List<Variable> values) {
		//System.out.println("Translate Assign5");
		IRCode code = new Skip();
		for(int i=0; i<exp.exps.size(); ++i) {
			Variable value = values.get(i);
			ASTExpression var = exp.exps.get(i);
			if(var instanceof ASTArrayExpression) {
				code = Seq.seq(code, translateAssign((ASTArrayExpression)var, value));
			} else if(var instanceof ASTRecExpression) {
				code = Seq.seq(code, translateAssign((ASTRecExpression)var, value));
			} else if(var instanceof ASTVariableExpression) {
				code = Seq.seq(code, translateAssign((ASTVariableExpression)var, value));
			} else
				throw new RuntimeException("Shouldn't reach here!");
		}
		return code;
	}

	public IRCode translateAssign(ASTRecTupleExpression exp, List<Variable> values) {
		//System.out.println("Translate Assign6");
		Pair<List<Variable>, IRCode> tmp = visit(exp);
		IRCode code = tmp.right;
		Variable v = tmp.left.get(0);
		for(int i=0; i<exp.exps.size(); ++i) {
			ASTVariableExpression var = (ASTVariableExpression)exp.exps.get(i);
			code = Seq.seq(code, translateAssign(v, var.var, values.get(i)));
		}
		return code;
	}

	public IRCode translateAssign(ASTVariableExpression exp, Variable value) {
		//System.out.println("Translate Assign7");
		Pair<List<Variable>, IRCode> tmp = visit(exp);
		IRCode code = tmp.right;
		Variable v = tmp.left.get(0);
		//rm not phantom
		if(this.currentCond == null) {
			code = Seq.seq(code,
					new Assign(v.type.getLabel(),v.cnt, v, new VarExp(value)));
		} else {
			Type type = v.type;
			Variable dumb = new Variable(type, Label.Secure, Count.Zero, newTempVar());
			code = Seq.seq(code, new Assign(Label.Secure, Count.Zero, dumb, 
					new MuxExp(currentCond, value, v)));
			code = Seq.seq(code,
					new Assign(v.type.getLabel(), v.cnt, v, new VarExp(dumb)));
		//	code = Seq.seq(code,
			//		new Assign(v.type.getLabel(),v.cnt, v, new VarExp(value)));
		}
		return code;
	}

	public IRCode visit(ASTBranchStatement stmt) {
		//System.out.println("Branch Stmt");
		if(stmt.pred == null) {
			Variable var = this.variableValues.get(stmt.stateVar+"_new");
			Variable tmp = new Variable(var.type, var.lab, var.cnt, newTempVar());
			IRCode code = new Assign(Label.Secure, Count.Zero, tmp, new ConstExp(stmt.goTrue.getId(), var.getBits())); 
		//rm not phantom
			if(this.currentCond == null && (var.lab == Label.Pub)) {
				code = Seq.seq(code,
						new Assign(var.type.getLabel(), var.cnt, var, new VarExp(tmp)));
			} else {
				Type type = var.type;
				Variable dumb = new Variable(type, Label.Secure,Count.Zero, newTempVar());
				code = Seq.seq(code, new Assign(Label.Secure, Count.Zero, dumb, 
						new MuxExp( currentCond, tmp, var)));
				code = Seq.seq(code,
						new Assign(var.type.getLabel(), var.cnt, var, new VarExp(dumb)));

			//	code = Seq.seq(code,
				//		new Assign(var.type.getLabel(), var.cnt, var, new VarExp(tmp)));
				
			}
			return code;
		} else {
			Variable var = this.variableValues.get(stmt.stateVar+"_new");
			Variable tmp = new Variable(var.type, var.lab, var.cnt, newTempVar());
			Pair<List<Variable>, IRCode> value = visit(stmt.pred);
			IRCode code = value.right;
			Variable tr = new Variable(var.type, var.lab, var.cnt, newTempVar());
			Variable fs = new Variable(var.type, var.lab, var.cnt, newTempVar());
			code = Seq.seq(code,
					new Assign(Label.Secure, Count.Zero, tr, new ConstExp(stmt.goTrue.getId(), var.getBits()))); 
			code = Seq.seq(code,
					new Assign(Label.Secure, Count.Zero, fs, new ConstExp(stmt.goFalse.getId(), var.getBits()))); 
			code = Seq.seq(code,
					new Assign(Label.Secure, Count.Zero, tmp, new MuxExp(value.left.get(0), tr, fs)));
			//rm not phantom
			if(this.currentCond == null && (var.lab == Label.Pub)) {
				code = Seq.seq(code,
						new Assign(var.type.getLabel(), var.cnt, var, new VarExp(tmp)));
			} else {
				Type type = var.type;
				Variable dumb = new Variable(type, Label.Secure, Count.Zero, newTempVar());
				code = Seq.seq(code, new Assign(Label.Secure,Count.Zero, dumb, 
						new MuxExp(currentCond, tmp, var)));
				code = Seq.seq(code,
						new Assign(var.type.getLabel(), var.cnt, var, new VarExp(dumb)));
			}
			return code;
		}
	}

	@Override
	public IRCode visit(ASTAssignStatement stmt) {
		//System.out.println("Assign Stmt");
		//Variable lv = left.left.get(0);

	//	Pair<List<Variable>, IRCode> right = visit(exp.right);
		//Variable rv = right.left.get(0);
		////System.out.println("Statement Assign Visitor");
		Pair<List<Variable>, IRCode> res = visit(stmt.expr);
		
		IRCode code = res.right;
		
		stmt.var.cnt=(res.left.get(0).cnt).join(Count.One);
	//	stmt.var.cnt = lv.cnt.join(rv.cnt);
		//System.out.println(stmt.var.cnt);
		if(res.left.size() == 1) {
			Variable value = res.left.get(0);
		
			if(stmt.var instanceof ASTArrayExpression) {
				return Seq.seq(code, translateAssign((ASTArrayExpression)stmt.var, value));
			} else if(stmt.var instanceof ASTRecExpression) {
				return Seq.seq(code, translateAssign((ASTRecExpression)stmt.var, value));
			} else if(stmt.var instanceof ASTVariableExpression) {
				return Seq.seq(code, translateAssign((ASTVariableExpression)stmt.var, value));
			} else if(stmt.var instanceof ASTRangeExpression) {
				return Seq.seq(code, translateAssign((ASTRangeExpression)stmt.var, value));
			} else
				throw new RuntimeException("Shouldn't reach here!");
		} else {
			if(stmt.var instanceof ASTRecTupleExpression) {
				return Seq.seq(code, translateAssign((ASTRecTupleExpression)stmt.var, res.left));
			} else if(stmt.var instanceof ASTTupleExpression) {
				return Seq.seq(code, translateAssign((ASTTupleExpression)stmt.var, res.left));
			} else
				throw new RuntimeException("Shouldn't reach here!");
		}
	}

	@Override
	public IRCode visit(ASTFuncStatement funcStatement) {
		//System.out.println("FuncStmt");
		//TODO this is ugly, need to change later
		Pair<List<Variable>, IRCode> value = visit(funcStatement.func);
		IRCode ret = value.right;
		if(ret instanceof Seq) {
			Assign tmp = (Assign)((Seq)ret).s2;
			tmp.toDum = true;
		} else {
			Assign tmp = (Assign)ret;
			tmp.toDum = true;
		}
		return ret;
	}

	@Override
	public IRCode visit(ASTIfStatement ifStatement) {
		//, lv.lab.meet(rv.lab)
		//System.out.println("Statement If Visitor");
		Pair<List<Variable>, IRCode> value = visit(ifStatement.cond);
		if(value.left.get(0).lab == Label.Secure || currentCond != null) {
			//System.out.println("First IF");
			IRCode ret = value.right;
			Variable old = this.currentCond;
			//rm phantom
		//if(currentCond == null)
				//this.currentCond = getPhantomVariable();
			if(currentCond == null) {
				//System.out.println("Null IF");
				this.currentCond = value.left.get(0);
			} else {
				//System.out.println("Else Null IF");
				Variable var = new Variable(new IntType(1, Label.Secure), Label.Secure, Count.One, newTempVar());
				ret = Seq.seq(ret, new Assign(Label.Secure, Count.One, var, new BopExp(this.currentCond, BopExp.Op.And, value.left.get(0))));
				this.currentCond = var;
				//System.out.println("Count in true" + this.currentCond.cnt);
			}
			IRCode tb = new Skip();
			for(int i=0; i<ifStatement.trueBranch.size(); ++i)
				tb = Seq.seq(tb, visit(ifStatement.trueBranch.get(i)));
			ret = Seq.seq(ret, tb);

			this.currentCond = old;

			if(ifStatement.falseBranch.size() > 0) {
				//System.out.println("False IF");
				//if(currentCond == null && this.phantom)
				//	this.currentCond = getPhantomVariable();
				Variable neg = new Variable(new IntType(1, Label.Secure), Label.Secure, Count.One, newTempVar());
				ret = Seq.seq(ret, new Assign(Label.Secure, Count.One, neg, new UnaryOpExp(UnaryOpExp.Op.Neg, value.left.get(0))));
				if(currentCond == null) {
					this.currentCond = neg;
				} else {
					Variable var = new Variable(new IntType(1, Label.Secure), Label.Secure, Count.One, newTempVar());
					ret = Seq.seq(ret, new Assign(Label.Secure, Count.One, var, new BopExp(this.currentCond, BopExp.Op.And, neg)));
					this.currentCond = var;
				}
				IRCode fb = new Skip();
				for(int i=0; i<ifStatement.falseBranch.size(); ++i)
					fb = Seq.seq(fb, visit(ifStatement.falseBranch.get(i)));
				ret = Seq.seq(ret, fb);
				this.currentCond = old;
			}
			return ret;
		} else {
			//System.out.println("Null FALSE IF");
			IRCode tb = new Skip();
		//	ASTCount temp=ASTCount.Zero;
			for(int i=0; i<ifStatement.trueBranch.size(); ++i) {
				tb = Seq.seq(tb, visit(ifStatement.trueBranch.get(i)));
				//should have count for assign.
	//	temp=temp.join(ifStatement.trueBranch.get(i).getCount());
				
				////System.out.println("Count" + ifStatement.trueBranch.get(i).getCount());
			//	if(i>0) {
				//ifStatement.trueBranch.get(i).getCount().join(ifStatement.trueBranch.get(i-1).getCount());
			//	}
				//System.out.println(i+"Count after join"+temp);
				

			}
			//ifStatement.tcnt=temp;
			IRCode fb = new Skip();
		//	ASTCount tempF=ASTCount.Zero;
			for(int i=0; i<ifStatement.falseBranch.size(); ++i) {
				fb = Seq.seq(fb, visit(ifStatement.falseBranch.get(i)));
				//System.out.println(i+"Count after join False" + ifStatement.falseBranch.get(i).getCount());
			//	tempF=tempF.join(ifStatement.falseBranch.get(i).getCount());
			}
		//	ifStatement.fcnt=tempF;
			return Seq.seq(value.right, 
					new If(value.left.get(0).lab, value.left.get(0), tb, fb));
		}
	}

	@Override
	public IRCode visit(ASTReturnStatement returnStatement) {
		//System.out.println("REturn Statement");
		Pair<List<Variable>, IRCode> t = visit(returnStatement.exp);
		return Seq.seq(t.right, new Ret(t.left.get(0)));
	}

	@Override
	public IRCode visit(ASTWhileStatement whileStatement) {
		//System.out.println("While Stmt");
		Pair<List<Variable>, IRCode> value = visit(whileStatement.cond);
		if(value.left.get(0).lab == Label.Secure || currentCond != null) {
			throw new RuntimeException("while loop cannot appear within a secure context.");
		} else {
			IRCode tb = new Skip();
			for(int i=0; i<whileStatement.body.size(); ++i)
				tb = Seq.seq(tb, visit(whileStatement.body.get(i)));
			return Seq.seq(value.right, 
					new While(value.left.get(0).lab, value.left.get(0), 
							Seq.seq(tb, value.right.clone(false))));
		}
	}

	public IRCode visit(ASTBoundedWhileStatement stmt) {
		//System.out.println("Bounded While");
		throw new RuntimeException("Should have eliminated all bounded loop before this.");
	}

	public List<Variable> one(Variable var) {
		List<Variable> ret = new ArrayList<Variable>();
		ret.add(var);
		return ret;
	}

	public Pair<List<Variable>, IRCode> visit(ASTExpression exp) {

		if (exp instanceof ASTBoxNullableExpression) {
			ASTBoxNullableExpression as = (ASTBoxNullableExpression)exp;
			return visit(as);
		} else if (exp instanceof ASTGetValueExpression) {
			ASTGetValueExpression as = (ASTGetValueExpression)exp;
			return visit(as);
		}

		Pair<List<Variable>, IRCode> pair = super.visit(exp);
		if(exp.targetBits == null 
				|| this.constructConstant(exp.targetBits).equals(pair.left.get(0).getBits())
				|| pair.left.size() > 1
				|| !(pair.left.get(0).type instanceof IntType)) {
			return pair;
		} else {
			Variable v = pair.left.get(0);
			Variable var = new Variable(
					new IntType(this.constructConstant(exp.targetBits), v.lab), 
					v.lab, 
					v.cnt, newTempVar());
			return new Pair<List<Variable>, IRCode>(one(var),
					Seq.seq(pair.right,
							new Assign(var.lab, v.cnt, var, new EnforceBitExp(v, var.getBits()))
							));
		}
	}

	@Override
	public Pair<List<Variable>, IRCode> visit(ASTAndPredicate andPredicate) {
		Pair<List<Variable>, IRCode> left = visit(andPredicate.left);
		Pair<List<Variable>, IRCode> right = visit(andPredicate.right);
		Variable lv = left.left.get(0);
		Variable rv = right.left.get(0);
		Variable var = new Variable(lv.type, lv.lab.meet(rv.lab), lv.cnt.join(rv.cnt), newTempVar());

		return new Pair<List<Variable>, IRCode>(one(var),
				Seq.seq(Seq.seq(left.right, right.right),
						new Assign(var.lab, var.cnt, var, new BopExp(lv, BopExp.Op.And, rv))
						));
	}

	@Override
	public Pair<List<Variable>, IRCode> visit(ASTArrayExpression exp) {
		Pair<List<Variable>, IRCode> array = visit(exp.var);
		Pair<List<Variable>, IRCode> idx = visit(exp.indexExpr);
		Variable av = array.left.get(0);
		Variable iv = idx.left.get(0);
		Variable var = new Variable(((ArrayType)av.type).type, ((ArrayType)av.type).indexLab.lab.meet(((ArrayType)av.type).getLabel()), (av.cnt).join((av).cnt), newTempVar());
		return new Pair<List<Variable>, IRCode>(one(var),
				Seq.seq(Seq.seq(array.right, idx.right),
						new Assign(var.lab, var.cnt, var, new ArrayExp(av, iv))
						));
	}

	@Override
	public Pair<List<Variable>, IRCode> visit(ASTBinaryExpression exp) {
		Pair<List<Variable>, IRCode> left = visit(exp.left);
		Variable lv = left.left.get(0);
		if(exp.op == BOP.SHL) {
			Pair<List<Variable>, IRCode> right = visit(exp.right);
			Variable var = new Variable(lv.type, lv.lab, lv.cnt, newTempVar());
			return new Pair<List<Variable>, IRCode>(one(var), 
					Seq.seq(Seq.seq(left.right, right.right),
							new Assign(var.lab, var.cnt, var, new BopExp(lv, BopExp.Op.Shl, right.left.get(0)))
							));
		} else if(exp.op == BOP.SHR) {
			Pair<List<Variable>, IRCode> right = visit(exp.right);
			Variable var = new Variable(lv.type, lv.lab, lv.cnt, newTempVar());
			return new Pair<List<Variable>, IRCode>(one(var), 
					Seq.seq(Seq.seq(left.right, right.right),
							new Assign(var.lab, var.cnt, var, new BopExp(lv, BopExp.Op.Shr, right.left.get(0)))
							));
		}

		Pair<List<Variable>, IRCode> right = visit(exp.right);
		Variable rv = right.left.get(0), var;;
		if(lv.type instanceof IntType) {
			IntType it = (IntType)lv.type;
			var = new Variable(new IntType(it.bit, lv.lab.meet(rv.lab)), lv.lab.meet(rv.lab), lv.cnt.join(rv.cnt), newTempVar());
		} else if(lv.type instanceof FloatType) {
			FloatType ft = (FloatType)lv.type;
			var = new Variable(new FloatType(ft.bit, lv.lab.meet(rv.lab)), lv.lab.meet(rv.lab), lv.cnt.join(rv.cnt), newTempVar());
		} else
			throw new RuntimeException("Binary expression can be operated between only int values or float values.");

		IRCode assign;
		if(exp.op == BOP.ADD) {
			assign = new Assign(var.lab, var.cnt, var, new BopExp(lv, BopExp.Op.Add, rv));
		} else if(exp.op == BOP.SUB) {
			assign = new Assign(var.lab,var.cnt, var, new BopExp(lv, BopExp.Op.Sub, rv));
		} else if(exp.op == BOP.MUL) {
			assign = new Assign(var.lab,var.cnt, var, new BopExp(lv, BopExp.Op.Mul, rv));
		} else if(exp.op == BOP.DIV) {
			assign = new Assign(var.lab,var.cnt, var, new BopExp(lv, BopExp.Op.Div, rv));
		} else if(exp.op == BOP.MOD) {
			assign = new Assign(var.lab, var.cnt,var, new BopExp(lv, BopExp.Op.Mod, rv));
		} else if(exp.op == BOP.AND) {
			assign = new Assign(var.lab,var.cnt, var, new BopExp(lv, BopExp.Op.And, rv));
		} else if(exp.op == BOP.OR) {
			assign = new Assign(var.lab,var.cnt, var, new BopExp(lv, BopExp.Op.Or, rv));
		} else if(exp.op == BOP.XOR) {
			assign = new Assign(var.lab,var.cnt, var, new BopExp(lv, BopExp.Op.Xor, rv));
		} else
			throw new RuntimeException("Unsupported binary operation! "+exp.toString());

		return new Pair<List<Variable>, IRCode>(one(var),
				Seq.seq(Seq.seq(left.right, right.right), assign));
	}

	@Override
	public Pair<List<Variable>, IRCode> visit(ASTBinaryPredicate exp) {
		Pair<List<Variable>, IRCode> left = visit(exp.left);
		Variable lv = left.left.get(0);

		Pair<List<Variable>, IRCode> right = visit(exp.right);
		Variable rv = right.left.get(0);
		Label lab = lv.lab.meet(rv.lab);
		Count cnt = lv.cnt.join(rv.cnt);

		if(lv.type instanceof IntType) {
			IntType lty = (IntType)lv.type;
			IntType rty = (IntType)rv.type;

			if(lty.bit == null) lty.bit = rty.bit;
			if(rty.bit == null) rty.bit = lty.bit;

			Variable var = new Variable(new IntType(1, lab), lab, cnt, newTempVar());

			IRCode assign;
			if(exp.op == REL_OP.EQ) {
				if(lv.getBits().isConstant(1) && exp.right instanceof ASTConstantExpression) {
					right.right = new Skip();
					ASTConstantExpression ce = (ASTConstantExpression)exp.right;
					int cn = ce.value;
					if(cn != 0) {
						assign = new Assign(var.lab, var.cnt, var, new VarExp(lv));
					} else {
						assign = new Assign(var.lab, var.cnt, var, new UnaryOpExp(UnaryOpExp.Op.Neg, lv));
					}
				} else
					assign = new Assign(var.lab, var.cnt, var, new RopExp(lv, RopExp.Op.Eq, rv));
			} else if(exp.op == REL_OP.NEQ) {
				if(lv.getBits().isConstant(1) && exp.right instanceof ASTConstantExpression) {
					right.right = new Skip();
					ASTConstantExpression ce = (ASTConstantExpression)exp.right;
					int cn = ce.value;
					if(cn == 0) {
						assign = new Assign(var.lab, var.cnt, var, new VarExp(lv));
					} else {
						assign = new Assign(var.lab,var.cnt, var, new UnaryOpExp(UnaryOpExp.Op.Neg, lv));
					}
				} else
					assign = new Assign(var.lab, var.cnt, var, new RopExp(lv, RopExp.Op.Ne, rv));
			} else if(exp.op == REL_OP.GT) {
				assign = new Assign(var.lab, var.cnt, var, new RopExp(lv, RopExp.Op.Gt, rv));
			} else if(exp.op == REL_OP.GET) {
				assign = new Assign(var.lab, var.cnt, var, new RopExp(lv, RopExp.Op.Ge, rv));
			} else if(exp.op == REL_OP.LT) {
				assign = new Assign(var.lab, var.cnt, var, new RopExp(lv, RopExp.Op.Lt, rv));
			} else if(exp.op == REL_OP.LET) {
				assign = new Assign(var.lab, var.cnt, var, new RopExp(lv, RopExp.Op.Le, rv));
			} else
				throw new RuntimeException("Unsupported binary comparison! "+exp.toString());

			return new Pair<List<Variable>, IRCode>(one(var),
					Seq.seq(Seq.seq(left.right, right.right), assign));
		} else {
			// assert it is nullable
			Variable var = new Variable(new IntType(1, lab), lab,  cnt, newTempVar());

			IRCode assign;
			if(exp.op == REL_OP.EQ) {
				if(rv.type instanceof NullType) {
					if(lv.type instanceof NullType) {
						assign = new Assign(var.lab, var.cnt, var, new ConstExp(1, new Constant(1)));
					} else {
						assign = new Assign(var.lab, var.cnt, var, new CheckNullExp(true, lv));
					}
				} else 
					assign = new Assign(var.lab, var.cnt, var, new RopExp(lv, RopExp.Op.Eq, rv));
			} else if(exp.op == REL_OP.NEQ) {
				if(rv.type instanceof NullType) {
					if(lv.type instanceof NullType) {
						assign = new Assign(var.lab, var.cnt, var, new ConstExp(1, new Constant(0)));
					} else {
						assign = new Assign(var.lab, var.cnt, var, new CheckNullExp(false, lv));
					}
				} else 
					assign = new Assign(var.lab, var.cnt, var, new RopExp(lv, RopExp.Op.Ne, rv));
			} else
				throw new RuntimeException("Unsupported binary comparison! "+exp.toString());

			return new Pair<List<Variable>, IRCode>(one(var),
					Seq.seq(Seq.seq(left.right, right.right), assign));
		}
	}

	@Override
	public Pair<List<Variable>, IRCode> visit(ASTConstantExpression exp) {
		VariableConstant bits = this.constructConstant(exp.bitSize);
		if(bits == null) {
			bits = new Constant(32);
		}
		Variable var = new Variable(new IntType(bits, Label.Pub), Label.Pub, Count.Zero, newTempVar());
		return new Pair<List<Variable>, IRCode>(one(var),
				new Assign(Label.Pub, Count.Zero, var, new ConstExp(exp.value, bits)));
	}

	@Override
	public Pair<List<Variable>, IRCode> visit(ASTFuncExpression funcExpression) {
		IRCode code = new Skip();
		List<Variable> input = new ArrayList<Variable>();
		int j = 0;
		for(int i=0; i<funcExpression.inputs.size(); ++i) {
			Pair<List<Variable>, IRCode> tmp = visit(funcExpression.inputs.get(i).right);

//			input.addAll(tmp.left);
			code = Seq.seq(code, tmp.right);
			for(Variable v : tmp.left) {
				ASTType type = funcExpression.inputTypes.get(j);
				ASTExpression typeBits = null;
				if(type instanceof ASTIntType) {
					typeBits = ((ASTIntType)type).getBits();
				} else if(type instanceof ASTRndType) {
					typeBits = ((ASTRndType)type).getBits();
				}
				if(funcExpression.inputTypes.get(j) == null
						|| !(v.type instanceof IntType)
						|| !(v.lab == Label.Secure)
						|| constructConstant(typeBits).equals(v.getBits())
						) {
					input.add(v);
				} else {
					VariableConstant bits = constructConstant(((ASTIntType)funcExpression.inputTypes.get(j)).getBits());
					Variable nv = new Variable(new IntType(bits, Label.Secure), Label.Secure, Count.Zero, newTempVar());
					code = Seq.seq(code,
							new Assign(nv.lab,nv.cnt, nv, new EnforceBitExp(v, bits))
							);
					input.add(nv);
				}
				j++;
			}
		}

		ASTExpression obj = funcExpression.obj;
		if(obj instanceof ASTVariableExpression) {
			String name = ((ASTVariableExpression)obj).var;
			for(Pair<ASTType, String> inputs : this.function.inputVariables) {
				if((inputs.left instanceof ASTFunctionType) && inputs.right.equals(name)) {
					ASTFunctionType fty = (ASTFunctionType)inputs.left;

					// TODO handle dummy
					if(this.currentCond != null)
						throw new RuntimeException("Function Pointers can be called only in low context.");

					Type ty = visit(fty.returnType);
					Expression exp =  new LocalFuncCallExp(ty, name, input);
					Variable var = new Variable(ty, ty.getLabel(), Count.Zero, newTempVar());
					return new Pair<List<Variable>, IRCode>(one(var),
							Seq.seq(code, new Assign(var.lab, var.cnt, var, exp)));
				}
			}
			for(ASTFunction func : program.functionDef) {
				if(func.name.equals(name) && func.baseType == null) {
					Type ty = visit(func.returnType);
					if(func.isDummy) {
						if(this.currentCond == null) {
							Variable var = new Variable(new IntType(1, Label.Secure), Label.Secure, Count.Zero, newTempVar());
							code = Seq.seq(code, new Assign(var.lab, var.cnt, var, new ConstExp(1, new Constant(1))));
							input.add(var);
						} else {
							input.add(currentCond);
						}
					}
					Expression exp;
					List<VariableConstant> bits = new ArrayList<VariableConstant>();
					for(int i=0; i<funcExpression.bitParameters.size(); ++i)
						bits.add(this.constructConstant(funcExpression.bitParameters.get(i)));
					if(func instanceof ASTFunctionNative) {
						FunctionType rawFty = (FunctionType)visit(program.getFunctionType(name));
						FunctionType fty = (FunctionType)visit(funcExpression.obj.type);
						exp = new FuncCallExp(rawFty,
								fty, 
								ty.getLabel(), 
								ty, 
								((ASTFunctionNative)func).nativeName, 
								bits, 
								input, 
								true);
					} else {
						FunctionType rawFty = (FunctionType)visit(program.getFunctionType(name));
						FunctionType fty = (FunctionType)visit(funcExpression.obj.type);
						exp = new FuncCallExp(rawFty,
								fty, 
								ty.getLabel(), 
								ty, 
								name, 
								bits, 
								input, 
								false);
					}
					Variable var = new Variable(ty, ty.getLabel(), Count.Zero, newTempVar());
					return new Pair<List<Variable>, IRCode>(one(var),
							Seq.seq(code, new Assign(var.lab,var.cnt, var, exp)));
				}
			}
			throw new RuntimeException("Shouldn't reach here!");
		} else if(obj instanceof ASTRecExpression) {
			ASTRecExpression recObj = (ASTRecExpression)obj;
			Pair<List<Variable>, IRCode> base = visit(recObj.base);
			String name = recObj.field;
			tc.setContext(program, this.function);
			ASTType baseType = tc.visit(recObj.base).get(0);
			if(baseType == null)
				return null;
			for(ASTFunction func : program.functionDef) {
				if(func.name.equals(name) && func.baseType.instance(baseType)) {
				
					Type ty = visit(funcExpression.type);
					if(func.isDummy) {
						if(this.currentCond == null) {
							Variable var = new Variable(new IntType(1, Label.Secure), Label.Secure, Count.Zero, newTempVar());
							code = Seq.seq(code, new Assign(var.lab,var.cnt, var, new ConstExp(1, new Constant(1))));
							input.add(var);
						} else {
							input.add(currentCond);
						}
					}

					List<VariableConstant> bits = new ArrayList<VariableConstant>();
					for(int i=0; i<funcExpression.bitParameters.size(); ++i)
						bits.add(this.constructConstant(funcExpression.bitParameters.get(i)));

					FuncCallExp exp;
					if(func instanceof ASTFunctionNative) {
						FunctionType rawFty = (FunctionType)visit(program.getFunctionType(name));
						FunctionType fty = (FunctionType)visit(funcExpression.obj.type);
						exp = new FuncCallExp(rawFty,
								fty, 
								ty.getLabel(), 
								ty, 
								base.left.get(0), 
								((ASTFunctionNative)func).nativeName, 
								bits, 
								input, 
								true);
					} else {
						FunctionType rawFty = (FunctionType)visit(program.getFunctionType(name));
						FunctionType fty = (FunctionType)visit(funcExpression.obj.type);
						exp = new FuncCallExp(rawFty,
								fty, 
								ty.getLabel(), 
								ty, 
								base.left.get(0), 
								name, 
								bits, 
								input, 
								false);
					}
					Variable var = new Variable(ty, ty.getLabel(), Count.Zero, newTempVar());
					return new Pair<List<Variable>, IRCode>(one(var),
							Seq.seq(Seq.seq(code, base.right),
									new Assign(var.lab, var.cnt, var, exp)));
				}
			}
			throw new RuntimeException("Shouldn't reach here!");
		} else {
			throw new RuntimeException("Shouldn't reach here!");
		}
	}

	@Override
	public Pair<List<Variable>, IRCode> visit(ASTNewObjectExpression exp) {
		RecordType type = (RecordType)visit(exp.type);
		Map<String, Variable> init = new HashMap<String, Variable>();
		IRCode code = new Skip();
		for(Map.Entry<String, ASTExpression> ent : exp.valueMapping.entrySet()) {
			Pair<List<Variable>, IRCode> tmp = visit(ent.getValue());
			init.put(ent.getKey(), tmp.left.get(0));
			code = Seq.seq(code, tmp.right);
		}
		NewObjExp nexp = new NewObjExp(type, type.lab, init);
		Variable var = new Variable(type, type.getLabel(), Count.Zero, newTempVar());
		return new Pair<List<Variable>, IRCode>(one(var),
				Seq.seq(code, new Assign(var.lab, var.cnt, var, nexp)));
	}

	@Override
	public Pair<List<Variable>, IRCode> visit(ASTOrPredicate orPredicate) {
		Pair<List<Variable>, IRCode> left = visit(orPredicate.left);
		Pair<List<Variable>, IRCode> right = visit(orPredicate.right);
		Variable lv = left.left.get(0);
		Variable rv = right.left.get(0);
		Variable var = new Variable(lv.type, lv.lab.meet(rv.lab), lv.cnt.join(rv.cnt), newTempVar());

		return new Pair<List<Variable>, IRCode>(one(var),
				Seq.seq(Seq.seq(left.right, right.right),
						new Assign(var.lab, var.cnt, var, new BopExp(lv, BopExp.Op.Or, rv))
						));
	}

	@Override
	public Pair<List<Variable>, IRCode> visit(ASTRecExpression rec) {
		Pair<List<Variable>, IRCode> code = visit(rec.base);
		RecordType ty = (RecordType)code.left.get(0).type;
		RecExp exp = new RecExp(ty.lab, code.left.get(0), rec.field);
		Variable var = new Variable(ty.fields.get(rec.field), 
				ty.fields.get(rec.field).getLabel(), 
				Count.Zero, newTempVar());

		return new Pair<List<Variable>, IRCode>(one(var),
				Seq.seq(code.right, new Assign(var.lab,  var.cnt, var, exp)));
	}

	@Override
	public Pair<List<Variable>, IRCode> visit(ASTRecTupleExpression tuple) {
		Pair<List<Variable>, IRCode> base = visit(tuple.base);
		Variable bv = base.left.get(0);
		Variable[] var = new Variable[((RecordType)bv.type).fields.size()];
		String[] fields = new String[((RecordType)bv.type).fields.size()];
		int j = 0;
		for(Map.Entry<String, Type> i : ((RecordType)bv.type).fields.entrySet()) {
			var[j] = new Variable(i.getValue(), i.getValue().getLabel(), Count.Zero, newTempVar());
			fields[j] = i.getKey();
			j++;
		}
		IRCode code = Seq.seq(base.right, new ReverseRecordAssign(bv, var, fields));
		List<Variable> ret = new ArrayList<Variable>();
		Map<String, Variable> old = new HashMap<String, Variable>(this.variableValues);
		for(int i=0; i<fields.length; ++i)
			this.variableValues.put(fields[i], var[i]);
		for(int i=0; i<tuple.exps.size(); ++i) {
			Pair<List<Variable>, IRCode> tmp = visit(tuple.exps.get(i));
			ret.add(tmp.left.get(0));
			code = Seq.seq(code, tmp.right);
		}
		this.variableValues = old;
		return new Pair<List<Variable>, IRCode>(ret, code);
	}

	@Override
	public Pair<List<Variable>, IRCode> visit(ASTTupleExpression tuple) {
		IRCode code = new Skip();
		List<Variable> res = new ArrayList<Variable>();
		for(int i=0; i<tuple.exps.size(); ++i) {
			Pair<List<Variable>, IRCode> tmp = visit(tuple.exps.get(i));
			res.add(tmp.left.get(0));
			code = Seq.seq(code, tmp.right);
		}
		return new Pair<List<Variable>, IRCode>(res, code);
	}

	@Override
	public Pair<List<Variable>, IRCode> visit(ASTVariableExpression variableExpression) {
		return new Pair<List<Variable>, IRCode>(
				one(this.variableValues.get(variableExpression.var)), new Skip());
	}

	@Override
	public Type visit(ASTFloatType type) {
		return new FloatType(constructConstant(type.getBits()), Label.get(type.getLabel()));
	}

	@Override
	public Type visit(ASTRndType type) {
		return new RndType(constructConstant(type.getBits()), Label.get(type.getLabel()));
	}

	@Override
	public Type visit(ASTFunctionType type) {
		Type ret = visit(type.returnType);
		List<Type> inputs = new ArrayList<Type>();
		for(int i=0; i<type.inputTypes.size(); ++i)
			inputs.add(visit(type.inputTypes.get(i)));
		List<Type> tp = new ArrayList<Type>();
		if(type.typeParameter != null) {
			for(int i=0; i<type.typeParameter.size(); ++i)
				tp.add(visit(type.typeParameter.get(i)));
		}

		return new FunctionType(ret, type.name, inputs, type.bitParameter, tp, type.global);
	}

	@Override
	public Pair<List<Variable>, IRCode> visit(
			ASTFloatConstantExpression exp) {
		VariableConstant bits = this.constructConstant(exp.bitSize);
		Variable var = new Variable(new FloatType(bits, Label.Pub), Label.Pub, Count.Zero, newTempVar());
		return new Pair<List<Variable>, IRCode>(one(var),
				new Assign(Label.Pub, Count.Zero, var, new ConstExp(exp.value, bits)));
	}

	@Override
	public Pair<List<Variable>, IRCode> visit(ASTLogExpression tuple) {
		Pair<List<Variable>, IRCode> expCode = visit(tuple.exp);
		Variable var = new Variable(new IntType(32, Label.Pub), Label.Pub, Count.Zero, newTempVar());
		return new Pair<List<Variable>, IRCode>(one(var),
				Seq.seq(expCode.right,
						new Assign(Label.Pub, Count.Zero, var, new LogExp(Label.Pub, expCode.left.get(0)))));
	}

	@Override
	public Pair<List<Variable>, IRCode> visit(ASTRangeExpression tuple) {
		Pair<List<Variable>, IRCode> expCode = visit(tuple.source);
		VariableConstant ll = this.constructConstant(tuple.rangel);
		IRCode code = expCode.right;
		Label lab = expCode.left.get(0).lab;
		Count cnt = expCode.left.get(0).cnt;
		if(tuple.ranger == null) {
			Variable var = new Variable(
					new IntType(1, lab), 
					lab, 
					cnt, newTempVar());
			return new Pair<List<Variable>, IRCode>(one(var),
					Seq.seq(code,
							new Assign(lab, cnt, var, 
									new RangeExp(lab, expCode.left.get(0), ll, null)))); 
		} else {
			VariableConstant rr = this.constructConstant(tuple.ranger);
			Variable var = new Variable(
					new IntType(new BOPVariableConstant(rr, Op.Sub, ll), lab), 
					lab, 
					cnt, newTempVar());
			return new Pair<List<Variable>, IRCode>(one(var),
					Seq.seq(code,
							new Assign(lab, cnt, var, 
									new RangeExp(lab, expCode.left.get(0), ll, rr)))); 
		}
	}

	public Pair<List<Variable>, IRCode> visit(ASTBoxNullableExpression exp) {
		Pair<List<Variable>, IRCode> tmp = visit(exp.exp);
		Variable var = new Variable(new DummyType(tmp.left.get(0).type), tmp.left.get(0).lab, tmp.left.get(0).cnt, newTempVar());
		IRCode code = Seq.seq(tmp.right, 
				new Assign(var.lab,var.cnt, var, new BoxNullExp(tmp.left.get(0))));
		return new Pair<List<Variable>, IRCode>(one(var), code);
	}

	public Pair<List<Variable>, IRCode> visit(ASTGetValueExpression exp) {
		Pair<List<Variable>, IRCode> tmp = visit(exp.exp);
		Variable var = new Variable(((DummyType)tmp.left.get(0).type).type, tmp.left.get(0).lab, tmp.left.get(0).cnt, newTempVar());
		IRCode code = Seq.seq(tmp.right, 
				new Assign(var.lab, var.cnt, var, new GetValueExp(exp.way, tmp.left.get(0))));
		return new Pair<List<Variable>, IRCode>(one(var), code);
	}

	@Override
	public Type visit(ASTNullType type) {
		return NullType.inst;
	}

	@Deprecated
	@Override
	public IRCode visit(ASTOnDummyStatement stmt) {
		return null;
	}

	@Override
	public Pair<List<Variable>, IRCode> visit(ASTNullExpression exp) {
		Variable var = new Variable(NullType.inst, Label.Pub, Count.Zero, newTempVar());
		IRCode code = new Assign(Label.Pub, Count.Zero, var, NullExp.inst);
		return new Pair<List<Variable>, IRCode>(one(var), code);
	}

	@Override
	public Type visit(ASTDummyType type) {
		return new DummyType(visit(type.type));
	}

	@Override
	public Type visit(ASTTupleType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IRCode visit(ASTUsingStatement stmt) {
		Map<String, Variable> old = this.variableValues;
		this.variableValues = new HashMap<String, Variable>(old);
		IRCode code = new Skip();
		for(Pair<String, ASTExpression> ent : stmt.use) {
			Pair<List<Variable>, IRCode> res = visit(ent.right);
			Type ty = visit(ent.right.type);
			Variable var = new Variable(ty, ty.getLabel(), Count.Zero, ent.left, true);
			this.variableValues.put(ent.left, new Variable(ty, ty.getLabel(), Count.Zero, ent.left, false));
			code = Seq.seq(code, res.right);
			code = Seq.seq(code, new Assign(var.lab,var.cnt, var, new VarExp(res.left.get(0))));
		}
		for(ASTStatement st : stmt.body) {
			code = Seq.seq(code, visit(st));
		}
		for(Pair<String, ASTExpression> ent : stmt.use) {
			ASTAssignStatement as = new ASTAssignStatement(ent.right, new ASTVariableExpression(ent.left));
			code = Seq.seq(code, visit(as));
		}
		this.variableValues = old;
		return new UsingBlock(code);
	}

	@Override
	public Pair<List<Variable>, IRCode> visit(ASTSizeExpression exp) {
		Type type = visit(exp.type);
		Variable var = new Variable(new IntType(32, Label.Pub), Label.Pub, Count.Zero, newTempVar());
		IRCode code = new Assign(Label.Pub, Count.Zero, var, new SizeofExp(type));
		return new Pair<List<Variable>, IRCode>(one(var), code);
	}

	@Override
	public IRCode visit(ASTDebugStatement stmt) {
		Pair<List<Variable>, IRCode> code = visit(stmt.exp);
		return new Debug(code.right, code.left.get(0), this.currentCond, stmt.beginPosition);
	}

}
