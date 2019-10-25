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

	public t__T[] main(int n, t__T[] x, t__T[] y) throws Exception {
		t__T[] _t_state = env.inputOfAlice(Utils.fromInt(0, 32));
		t__T _t_state_new = env.inputOfAlice(false);
		t__T[] _t_count = env.inputOfAlice(Utils.fromInt(0, 32));
		t__T[] f_tmp_0 = intLib.xor(x,y);
		t__T[] __tmp0 = f_tmp_0;
		t__T[] __tmp1 = noclass.countOnes_opt2(n, __tmp0);
		return __tmp1;

	}
	public t__T[] countOnes(int n, t__T[] x) throws Exception {
		t__T[] ret = env.inputOfAlice(Utils.fromInt(0, 32));
		t__T[] tmp = env.inputOfAlice(Utils.fromInt(0, 32));
		int i = 0;
		t__T[] _t_state = env.inputOfAlice(Utils.fromInt(0, 32));
		t__T _t_state_new = env.inputOfAlice(false);
		t__T[] _t_count = env.inputOfAlice(Utils.fromInt(0, 32));
		int __tmp2 = 0;
		ret = env.inputOfAlice(Utils.fromInt(__tmp2, 32));
		int __tmp3 = 0;
		i = __tmp3;
		boolean f_tmp_3 = i < n;
		boolean __tmp4 = f_tmp_3;
		while(__tmp4) {
			t__T __tmp5 = x[i];
			t__T[] __tmp6 = intLib.enforceBits(__tmp5, 32);
			tmp = __tmp6;
			t__T[] f_tmp_4 = intLib.add(ret,tmp);
			t__T[] __tmp7 = f_tmp_4;
			ret = __tmp7;
			int __tmp8 = 1;
			int f_tmp_6 = i + __tmp8;
			int __tmp9 = f_tmp_6;
			i = __tmp9;
			boolean f_tmp_7 = i < n;
			__tmp4 = f_tmp_7;
		}
		return ret;

	}
	public t__T[] countOnes_opt1(int n, t__T[] x) throws Exception {
		t__T[] ret = env.inputOfAlice(Utils.fromInt(0, Utils.logFloor(n)));
		t__T[] tmp = env.inputOfAlice(Utils.fromInt(0, Utils.logFloor(n)));
		int i = 0;
		t__T[] _t_state = env.inputOfAlice(Utils.fromInt(0, 32));
		t__T _t_state_new = env.inputOfAlice(false);
		t__T[] _t_count = env.inputOfAlice(Utils.fromInt(0, 32));
		int __tmp10 = 0;
		ret = env.inputOfAlice(Utils.fromInt(__tmp10, Utils.logFloor(n)));
		int __tmp11 = 0;
		i = __tmp11;
		boolean f_tmp_10 = i < n;
		boolean __tmp12 = f_tmp_10;
		while(__tmp12) {
			t__T __tmp13 = x[i];
			t__T[] __tmp14 = intLib.enforceBits(__tmp13, Utils.logFloor(n));
			tmp = __tmp14;
			t__T[] __tmp15 = intLib.enforceBits(ret, Utils.logFloor(n));
			t__T[] __tmp16 = intLib.enforceBits(tmp, Utils.logFloor(n));
			t__T[] f_tmp_11 = intLib.add(__tmp15,__tmp16);
			t__T[] __tmp17 = f_tmp_11;
			ret = __tmp17;
			int __tmp18 = 1;
			int f_tmp_13 = i + __tmp18;
			int __tmp19 = f_tmp_13;
			i = __tmp19;
			boolean f_tmp_14 = i < n;
			__tmp12 = f_tmp_14;
		}
		return ret;

	}
	public t__T[] countOnes_opt2(int n, t__T[] x) throws Exception {
		t__T[] first = env.inputOfAlice(Utils.fromInt(0, Utils.logFloor(((n)/(2))+(1))));
		t__T[] second = env.inputOfAlice(Utils.fromInt(0, Utils.logFloor(((n)-((n)/(2)))+(1))));
		t__T[] r = env.inputOfAlice(Utils.fromInt(0, Utils.logFloor((n)+(1))));
		t__T[] _t_state = env.inputOfAlice(Utils.fromInt(0, 32));
		t__T _t_state_new = env.inputOfAlice(false);
		t__T[] _t_count = env.inputOfAlice(Utils.fromInt(0, 32));
		int __tmp20 = 1;
		boolean f_tmp_16 = n == __tmp20;
		boolean __tmp21 = f_tmp_16;
		if(__tmp21) {
			return x;
		} else {
		}
		t__T[] __tmp22 = Arrays.copyOfRange(x, 0, (n)/(2));
		t__T[] __tmp23 = intLib.enforceBits(__tmp22, (n)/(2));
		t__T[] __tmp24 = noclass.countOnes_opt2((n)/(2), __tmp23);
		t__T[] __tmp25 = intLib.enforceBits(__tmp24, Utils.logFloor(((n)/(2))+(1)));
		first = __tmp25;
		t__T[] __tmp26 = Arrays.copyOfRange(x, (n)/(2), n);
		t__T[] __tmp27 = noclass.countOnes_opt2((n)-((n)/(2)), __tmp26);
		t__T[] __tmp28 = intLib.enforceBits(__tmp27, Utils.logFloor(((n)-((n)/(2)))+(1)));
		second = __tmp28;
		t__T[] __tmp29 = intLib.enforceBits(first, Utils.logFloor((n)+(1)));
		r = __tmp29;
		t__T[] __tmp30 = intLib.enforceBits(r, Utils.logFloor((n)+(1)));
		t__T[] __tmp31 = intLib.enforceBits(second, Utils.logFloor((n)+(1)));
		t__T[] f_tmp_17 = intLib.add(__tmp30,__tmp31);
		t__T[] __tmp32 = f_tmp_17;
		r = __tmp32;
		return r;

	}
	public t__T[] main (int __n, int __m, t__T[] x, t__T[] y) throws Exception {
		int n = __n;
		return main(n, x, y);
	}
}
