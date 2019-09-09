package com.oblivm.backend.generated.hamming;
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
public class NoClass<t__T> implements IWritable<NoClass<t__T>, t__T>, ISecureRunnable<t__T> {

	public CompEnv<t__T> env;
	public IntegerLib<t__T> intLib;
	public FloatLib<t__T> floatLib;
	public NoClass<t__T> noclass = this;

	public NoClass(CompEnv<t__T> env) throws Exception {
		this.env = env;
		this.intLib = new IntegerLib<t__T>(env);
		this.floatLib = new FloatLib<t__T>(env, 24, 8);
	}

	public int numBits() {
		return 0;
	}

	public t__T[] getBits() throws Exception {
		t__T[] ret = env.newTArray(this.numBits());
		t__T[] tmp_b;
		t__T tmp;
		int now = 0;
		return ret;
	}

	public NoClass<t__T> newObj(t__T[] data) throws Exception {
		if(data == null) {
			data = env.newTArray(this.numBits());
			for(int i=0; i<this.numBits(); ++i) { data[i] = intLib.SIGNAL_ZERO; }
		}
		if(data.length != this.numBits()) return null;
		NoClass<t__T> ret = new NoClass<t__T>(env);
		t__T[] tmp;
		int now = 0;
		return ret;
	}

	public NoClass<t__T> fake() throws Exception {
		NoClass<t__T> ret = new NoClass<t__T>(env);
		return ret;
	}

	public NoClass<t__T> muxFake(t__T __isDummy) throws Exception {
		NoClass<t__T> ret = new NoClass<t__T>(env);
		return ret;
	}

	public Pair<t__T, bit<t__T>, Int<t__T>> add(int n, t__T[] x, t__T[] y) throws Exception {
		bit<t__T> cin = new bit<t__T>(env);
		Int<t__T> ret = new Int<t__T>(env, n);
		bit<t__T> t1 = new bit<t__T>(env);
		bit<t__T> t2 = new bit<t__T>(env);
		t__T zzz = env.inputOfAlice(false);
		int i = 0;
		t__T[] _t_state = env.inputOfAlice(Utils.fromInt(0, 32));
		t__T _t_state_new = env.inputOfAlice(false);
		t__T[] _t_count = env.inputOfAlice(Utils.fromInt(0, 32));
		int __tmp0 = 0;
		i = __tmp0;
		boolean f_tmp_1 = i < n;
		boolean __tmp1 = f_tmp_1;
		while(__tmp1) {
			t__T __tmp2 = x[i];
			zzz = __tmp2;
			t__T f_tmp_2 = cin.v;
			t__T __tmp3 = f_tmp_2;
			t__T f_tmp_3 = intLib.xor(zzz,__tmp3);
			t__T __tmp4 = f_tmp_3;
			t1.v = __tmp4;
			t__T __tmp5 = y[i];
			t__T f_tmp_4 = cin.v;
			t__T __tmp6 = f_tmp_4;
			t__T f_tmp_5 = intLib.xor(__tmp5,__tmp6);
			t__T __tmp7 = f_tmp_5;
			t2.v = __tmp7;
			t__T __tmp8 = x[i];
			t__T f_tmp_6 = t2.v;
			t__T __tmp9 = f_tmp_6;
			t__T f_tmp_7 = intLib.xor(__tmp8,__tmp9);
			t__T __tmp10 = f_tmp_7;
			t__T[] f_tmp_8 = ret.v;
			t__T[] __tmp11 = f_tmp_8;
			__tmp11[i]=__tmp10;
			t__T f_tmp_9 = t1.v;
			t__T __tmp12 = f_tmp_9;
			t__T f_tmp_10 = t2.v;
			t__T __tmp13 = f_tmp_10;
			t__T f_tmp_11 = intLib.and(__tmp12,__tmp13);
			t__T __tmp14 = f_tmp_11;
			t1.v = __tmp14;
			t__T f_tmp_12 = cin.v;
			t__T __tmp15 = f_tmp_12;
			t__T f_tmp_13 = t1.v;
			t__T __tmp16 = f_tmp_13;
			t__T f_tmp_14 = intLib.xor(__tmp15,__tmp16);
			t__T __tmp17 = f_tmp_14;
			cin.v = __tmp17;
			int __tmp18 = 1;
			int f_tmp_16 = i + __tmp18;
			int __tmp19 = f_tmp_16;
			i = __tmp19;
			boolean f_tmp_17 = i < n;
			__tmp1 = f_tmp_17;
		}
		Pair<t__T, bit<t__T>, Int<t__T>> f_tmp_18 = new Pair<t__T, bit<t__T>, Int<t__T>>(env, new bit<t__T>(env), new Int<t__T>(env, n));
		f_tmp_18.left = cin;
		f_tmp_18.right = ret;
		Pair<t__T, bit<t__T>, Int<t__T>> __tmp20 = f_tmp_18;
		return __tmp20;

	}
	public t__T[] countOnes(int n, t__T[] x) throws Exception {
		t__T[] first = env.inputOfAlice(Utils.fromInt(0, Utils.logFloor(((n)/(2))+(1))));
		t__T[] second = env.inputOfAlice(Utils.fromInt(0, Utils.logFloor(((n)-((n)/(2)))+(1))));
		t__T[] r = env.inputOfAlice(Utils.fromInt(0, Utils.logFloor((n)+(1))));
		Pair<t__T, bit<t__T>, Int<t__T>> ret = new Pair<t__T, bit<t__T>, Int<t__T>>(env, new bit<t__T>(env), new Int<t__T>(env, Utils.logFloor(((n)/(2))+(1))));
		t__T[] _t_state = env.inputOfAlice(Utils.fromInt(0, 32));
		t__T _t_state_new = env.inputOfAlice(false);
		t__T[] _t_count = env.inputOfAlice(Utils.fromInt(0, 32));
		int __tmp21 = 1;
		boolean f_tmp_20 = n == __tmp21;
		boolean __tmp22 = f_tmp_20;
		if(__tmp22) {
			return x;
		} else {
		}
		t__T[] __tmp23 = Arrays.copyOfRange(x, 0, (n)/(2));
		t__T[] __tmp24 = intLib.enforceBits(__tmp23, (n)/(2));
		t__T[] __tmp25 = noclass.countOnes((n)/(2), __tmp24);
		t__T[] __tmp26 = intLib.enforceBits(__tmp25, Utils.logFloor(((n)/(2))+(1)));
		first = __tmp26;
		t__T[] __tmp27 = Arrays.copyOfRange(x, (n)/(2), n);
		t__T[] __tmp28 = noclass.countOnes((n)-((n)/(2)), __tmp27);
		t__T[] __tmp29 = intLib.enforceBits(__tmp28, Utils.logFloor(((n)-((n)/(2)))+(1)));
		second = __tmp29;
		int __tmp30 = 2;
		int f_tmp_22 = n / __tmp30;
		int __tmp31 = f_tmp_22;
		int __tmp32 = 1;
		int f_tmp_24 = __tmp31 + __tmp32;
		int __tmp33 = f_tmp_24;
		int __tmp34 = Utils.logFloor(__tmp33);
		int __tmp35 = 2;
		int f_tmp_26 = n / __tmp35;
		int __tmp36 = f_tmp_26;
		int f_tmp_27 = n - __tmp36;
		int __tmp37 = f_tmp_27;
		int __tmp38 = 1;
		int f_tmp_29 = __tmp37 + __tmp38;
		int __tmp39 = f_tmp_29;
		int __tmp40 = Utils.logFloor(__tmp39);
		boolean f_tmp_30 = __tmp34 == __tmp40;
		boolean __tmp41 = f_tmp_30;
		if(__tmp41) {
			t__T[] __tmp42 = intLib.enforceBits(first, Utils.logFloor(((n)/(2))+(1)));
			t__T[] __tmp43 = intLib.enforceBits(__tmp42, Utils.logFloor(((n)/(2))+(1)));
			t__T[] __tmp44 = intLib.enforceBits(second, Utils.logFloor(((n)-((n)/(2)))+(1)));
			t__T[] __tmp45 = intLib.enforceBits(__tmp44, Utils.logFloor(((n)/(2))+(1)));
			Pair<t__T, bit<t__T>, Int<t__T>> __tmp46 = noclass.add(Utils.logFloor(((n)/(2))+(1)), __tmp43, __tmp45);
			ret = __tmp46;
			Int<t__T> f_tmp_31 = ret.right;
			Int<t__T> __tmp47 = f_tmp_31;
			t__T[] f_tmp_32 = __tmp47.v;
			t__T[] __tmp48 = f_tmp_32;
			t__T[] __tmp49 = intLib.enforceBits(__tmp48, Utils.logFloor((n)+(1)));
			r = __tmp49;
			bit<t__T> f_tmp_33 = ret.left;
			bit<t__T> __tmp50 = f_tmp_33;
			t__T f_tmp_34 = __tmp50.v;
			t__T __tmp51 = f_tmp_34;
			r[(Utils.logFloor((n)+(1)))-(1)]=__tmp51;
		} else {
			t__T[] __tmp52 = intLib.enforceBits(first, Utils.logFloor(((n)/(2))+(1)));
			t__T[] __tmp53 = intLib.enforceBits(__tmp52, Utils.logFloor(((n)-((n)/(2)))+(1)));
			t__T[] __tmp54 = intLib.enforceBits(second, Utils.logFloor(((n)-((n)/(2)))+(1)));
			t__T[] __tmp55 = intLib.enforceBits(__tmp54, Utils.logFloor(((n)-((n)/(2)))+(1)));
			Pair<t__T, bit<t__T>, Int<t__T>> __tmp56 = noclass.add(Utils.logFloor(((n)-((n)/(2)))+(1)), __tmp53, __tmp55);
			ret = __tmp56;
			Int<t__T> f_tmp_35 = ret.right;
			Int<t__T> __tmp57 = f_tmp_35;
			t__T[] f_tmp_36 = __tmp57.v;
			t__T[] __tmp58 = f_tmp_36;
			t__T[] __tmp59 = intLib.enforceBits(__tmp58, Utils.logFloor((n)+(1)));
			r = __tmp59;
		}
		return r;

	}
	public t__T[] hamming(int n, t__T[] x, t__T[] y) throws Exception {
		t__T[] z = env.inputOfAlice(Utils.fromInt(0, n));
		t__T[] _t_state = env.inputOfAlice(Utils.fromInt(0, 32));
		t__T _t_state_new = env.inputOfAlice(false);
		t__T[] _t_count = env.inputOfAlice(Utils.fromInt(0, 32));
		t__T[] f_tmp_37 = intLib.xor(x,y);
		t__T[] __tmp60 = f_tmp_37;
		z = __tmp60;
		t__T[] __tmp61 = noclass.countOnes(n, z);
		return __tmp61;

	}
	public t__T[] main(int n, int m, t__T[] x, t__T[] y) throws Exception {
		t__T[] _t_state = env.inputOfAlice(Utils.fromInt(0, 32));
		t__T _t_state_new = env.inputOfAlice(false);
		t__T[] _t_count = env.inputOfAlice(Utils.fromInt(0, 32));
		t__T[] __tmp62 = intLib.enforceBits(y, n);
		t__T[] __tmp63 = noclass.hamming(n, x, __tmp62);
		return __tmp63;

	}
}
