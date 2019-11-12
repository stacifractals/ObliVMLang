package com.oblivm.backend.generated.stack;
import java.util.Arrays;
import java.util.Random;
import java.lang.reflect.Array;
import java.util.BitSet;
import java.security.SecureRandom;
import com.oblivm.backend.oram.SecureArray;
import com.oblivm.backend.oram.CircuitOram;
import com.oblivm.backend.flexsc.Mode;
import com.oblivm.backend.flexsc.Party;
import com.oblivm.backend.flexsc.CompEnv;
import com.oblivm.backend.circuits.arithmetic.IntegerLib;
import com.oblivm.backend.circuits.arithmetic.FloatLib;
import com.oblivm.backend.util.Utils;
import com.oblivm.backend.gc.regular.GCEva;
import com.oblivm.backend.gc.regular.GCGen;
import com.oblivm.backend.gc.GCSignal;
import com.oblivm.backend.flexsc.IWritable;
import com.oblivm.backend.lang.inter.*;
import com.oblivm.backend.flexsc.Comparator;
public class Stack<t__T> implements IWritable<Stack<t__T>, t__T> {
	public t__T[] top;
	public SecureArray<t__T> elements;

	public CompEnv<t__T> env;
	public IntegerLib<t__T> intLib;
	public FloatLib<t__T> floatLib;
	private NoClass<t__T> noclass;

	public Stack(CompEnv<t__T> env) throws Exception {
		this.env = env;
		this.intLib = new IntegerLib<t__T>(env);
		this.floatLib = new FloatLib<t__T>(env, 24, 8);
		this.noclass = new NoClass<t__T>(env);
		this.top = env.inputOfAlice(Utils.fromInt(0, 32));
		this.elements = new SecureArray<t__T>(env, 100, 32);
	}

	public int numBits() {
		return ((0)+(32))+((32)*(100));
	}

	public t__T[] getBits() throws Exception {
		t__T[] ret = env.newTArray(this.numBits());
		t__T[] tmp_b;
		t__T tmp;
		int now = 0;
		tmp_b = top;
		System.arraycopy(tmp_b, 0, ret, now, tmp_b.length);
		now += tmp_b.length;
		for(int i0=0; i0<100; ++i0)
		{
			tmp_b = elements.read(intLib.toSignals(i0));
			System.arraycopy(tmp_b, 0, ret, now, tmp_b.length);
			now += tmp_b.length;
		}
		return ret;
	}

	public Stack<t__T> newObj(t__T[] data) throws Exception {
		if(data == null) {
			data = env.newTArray(this.numBits());
			for(int i=0; i<this.numBits(); ++i) { data[i] = intLib.SIGNAL_ZERO; }
		}
		if(data.length != this.numBits()) return null;
		Stack<t__T> ret = new Stack<t__T>(env);
		t__T[] tmp;
		int now = 0;
		ret.top = env.newTArray(32);
		System.arraycopy(data, now, ret.top, 0, 32);
		now += 32;
		return ret;
	}

	public Stack<t__T> fake() throws Exception {
		Stack<t__T> ret = new Stack<t__T>(env);
		ret.top = this.top;
		return ret;
	}

	public Stack<t__T> muxFake(t__T __isDummy) throws Exception {
		Stack<t__T> ret = new Stack<t__T>(env);
		ret.top = this.top;
		return ret;
	}

	public void initialize() throws Exception {
		t__T[] _t_state = env.inputOfAlice(Utils.fromInt(0, 32));
		t__T _t_state_new = env.inputOfAlice(false);
		t__T[] _t_count = env.inputOfAlice(Utils.fromInt(0, 32));
		int __tmp0 = 0;
		top = env.inputOfAlice(Utils.fromInt(__tmp0, 32));

	}
	public t__T[] pop() throws Exception {
		t__T[] ret = env.inputOfAlice(Utils.fromInt(0, 32));
		t__T[] _t_state = env.inputOfAlice(Utils.fromInt(0, 32));
		t__T _t_state_new = env.inputOfAlice(false);
		t__T[] _t_count = env.inputOfAlice(Utils.fromInt(0, 32));
		int __tmp1 = 0;
		t__T[] f_tmp_24 = env.inputOfAlice(Utils.fromInt(__tmp1, 32));
		t__T f_tmp_23 = intLib.not(intLib.leq(top, f_tmp_24));
		t__T __tmp2 = f_tmp_23;
		int __tmp3 = 1;
		t__T[] f_tmp_27 = env.inputOfAlice(Utils.fromInt(__tmp3, 32));
		t__T[] f_tmp_26 = intLib.sub(top,f_tmp_27);
		t__T[] __tmp4 = f_tmp_26;
		t__T[] __tmp5 = intLib.mux(top, __tmp4,__tmp2);
		top = __tmp5;
		t__T[] f_tmp_29 = elements.read(top);
		t__T[] __tmp6 = f_tmp_29;
		t__T[] __tmp7 = intLib.mux(ret, __tmp6,__tmp2);
		ret = __tmp7;
		return ret;

	}
	public t__T[] push(t__T[] value) throws Exception {
		t__T[] succeed = env.inputOfAlice(Utils.fromInt(0, 32));
		t__T[] _t_state = env.inputOfAlice(Utils.fromInt(0, 32));
		t__T _t_state_new = env.inputOfAlice(false);
		t__T[] _t_count = env.inputOfAlice(Utils.fromInt(0, 32));
		int __tmp8 = 0;
		succeed = env.inputOfAlice(Utils.fromInt(__tmp8, 32));
		int __tmp9 = 100;
		t__T[] f_tmp_34 = env.inputOfAlice(Utils.fromInt(__tmp9, 32));
		t__T f_tmp_33 = intLib.not(intLib.geq(top, f_tmp_34));
		t__T __tmp10 = f_tmp_33;
		t__T[] f_tmp_35 = elements.read(top);
		t__T[] __tmp11 = f_tmp_35;
		t__T[] __tmp12 = intLib.mux(__tmp11, value,__tmp10);
		elements.write(top,__tmp12);
		int __tmp13 = 1;
		t__T[] f_tmp_39 = env.inputOfAlice(Utils.fromInt(__tmp13, 32));
		t__T[] f_tmp_38 = intLib.add(top,f_tmp_39);
		t__T[] __tmp14 = f_tmp_38;
		t__T[] __tmp15 = intLib.mux(top, __tmp14,__tmp10);
		top = __tmp15;
		int __tmp16 = 1;
		t__T[] f_tmp_43 = env.inputOfAlice(Utils.fromInt(__tmp16, 32));
		t__T[] __tmp17 = intLib.mux(succeed, f_tmp_43,__tmp10);
		succeed = __tmp17;
		return succeed;

	}
}
