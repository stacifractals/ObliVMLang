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

	public t__T[] main(int n, SecureArray<t__T> x, t__T[] y) throws Exception {
		Stack<t__T> stack = new Stack<t__T>(env);
		for(int _j_2 = 0; _j_2 < 100; ++_j_2) {
			stack.elements.write(intLib.toSignals(_j_2), env.inputOfAlice(Utils.fromInt(0, 32)));
		}
		int i = 0;
		t__T[] res = env.inputOfAlice(Utils.fromInt(0, 32));
		t__T[] _t_state = env.inputOfAlice(Utils.fromInt(0, 32));
		t__T _t_state_new = env.inputOfAlice(false);
		t__T[] _t_count = env.inputOfAlice(Utils.fromInt(0, 32));
		stack.initialize();
		int __tmp19 = 0;
		i = __tmp19;
		boolean f_tmp_1 = i < n;
		boolean __tmp20 = f_tmp_1;
		while(__tmp20) {
			t__T[] f_tmp_3 = env.inputOfAlice(Utils.fromInt(i, 32));
			t__T[] f_tmp_2 = x.read(f_tmp_3);
			t__T[] __tmp21 = f_tmp_2;
			t__T[] f_tmp_4 = intLib.add(__tmp21,y);
			t__T[] __tmp22 = f_tmp_4;
			stack.push(__tmp22);
			int __tmp24 = 1;
			int f_tmp_6 = i + __tmp24;
			int __tmp25 = f_tmp_6;
			i = __tmp25;
			boolean f_tmp_7 = i < n;
			__tmp20 = f_tmp_7;
		}
		int __tmp26 = 0;
		res = env.inputOfAlice(Utils.fromInt(__tmp26, 32));
		int __tmp27 = 0;
		i = __tmp27;
		boolean f_tmp_10 = i < n;
		boolean __tmp28 = f_tmp_10;
		int __tmp29 = 4;
		boolean f_tmp_12 = i < __tmp29;
		boolean __tmp30 = f_tmp_12;
		boolean __tmp31 = __tmp28 & __tmp30;
		while(__tmp31) {
			t__T[] __tmp32 = stack.pop();
			t__T[] f_tmp_14 = intLib.add(res,__tmp32);
			t__T[] __tmp33 = f_tmp_14;
			res = __tmp33;
			int __tmp34 = 1;
			int f_tmp_16 = i + __tmp34;
			int __tmp35 = f_tmp_16;
			i = __tmp35;
			boolean f_tmp_17 = i < n;
			__tmp28 = f_tmp_17;
			__tmp29 = 4;
			boolean f_tmp_19 = i < __tmp29;
			__tmp30 = f_tmp_19;
			__tmp31 = __tmp28 & __tmp30;
		}
		return res;

	}
	public t__T[] main (int __n, int __m, t__T[] x, t__T[] y) throws Exception {
		int n = __n / (32);
		if ( (n) * (32) != __n ) {
			throw new RuntimeException("input size doesn't match");
		}
		return main(n, com.oblivm.backend.lang.inter.Util.intToSecArray(env, x, 32, n), y);
	}
}
