package com.oblivm.backend.generated.euclidean;
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

	public t__T[] main(int n, int m, t__T[] x, t__T[] y) throws Exception {
		SecureArray<t__T> alc = new SecureArray<t__T>(env, (n)/(32), 32);
		for(int _j_2 = 0; _j_2 < (n)/(32); ++_j_2) {
			alc.write(intLib.toSignals(_j_2), env.inputOfAlice(Utils.fromInt(0, 32)));
		}
		SecureArray<t__T> bb = new SecureArray<t__T>(env, (n)/(32), 32);
		for(int _j_2 = 0; _j_2 < (n)/(32); ++_j_2) {
			bb.write(intLib.toSignals(_j_2), env.inputOfAlice(Utils.fromInt(0, 32)));
		}
		int N = 0;
		int i = 0;
		t__T[] flt = env.inputOfAlice(Utils.fromInt(0, 32));
		t__T[] _t_state = env.inputOfAlice(Utils.fromInt(0, 32));
		t__T _t_state_new = env.inputOfAlice(false);
		t__T[] _t_count = env.inputOfAlice(Utils.fromInt(0, 32));
		int __tmp0 = 32;
		int f_tmp_1 = n / __tmp0;
		int __tmp1 = f_tmp_1;
		N = __tmp1;
		int __tmp2 = 0;
		i = __tmp2;
		boolean f_tmp_3 = i < N;
		boolean __tmp3 = f_tmp_3;
		while(__tmp3) {
			t__T[] __tmp4 = Arrays.copyOfRange(x, (32)*(i), ((32)*(i))+(32));
			t__T[] __tmp5 = intLib.enforceBits(__tmp4, 32);
			t__T[] f_tmp_4 = env.inputOfAlice(Utils.fromInt(i, 32));
			alc.write(f_tmp_4,__tmp5);
			t__T[] __tmp6 = Arrays.copyOfRange(y, (32)*(i), ((32)*(i))+(32));
			t__T[] __tmp7 = intLib.enforceBits(__tmp6, 32);
			t__T[] f_tmp_5 = env.inputOfAlice(Utils.fromInt(i, 32));
			bb.write(f_tmp_5,__tmp7);
			int __tmp8 = 1;
			int f_tmp_7 = i + __tmp8;
			int __tmp9 = f_tmp_7;
			i = __tmp9;
			boolean f_tmp_8 = i < N;
			__tmp3 = f_tmp_8;
		}
		int __tmp10 = 0;
		flt = env.inputOfAlice(Utils.fromInt(__tmp10, 32));
		int __tmp11 = 0;
		i = __tmp11;
		boolean f_tmp_11 = i < N;
		boolean __tmp12 = f_tmp_11;
		while(__tmp12) {
			t__T[] f_tmp_13 = env.inputOfAlice(Utils.fromInt(i, 32));
			t__T[] f_tmp_12 = alc.read(f_tmp_13);
			t__T[] __tmp13 = f_tmp_12;
			t__T[] f_tmp_15 = env.inputOfAlice(Utils.fromInt(i, 32));
			t__T[] f_tmp_14 = bb.read(f_tmp_15);
			t__T[] __tmp14 = f_tmp_14;
			t__T[] f_tmp_16 = intLib.sub(__tmp13,__tmp14);
			t__T[] __tmp15 = f_tmp_16;
			t__T[] f_tmp_18 = env.inputOfAlice(Utils.fromInt(i, 32));
			t__T[] f_tmp_17 = alc.read(f_tmp_18);
			t__T[] __tmp16 = f_tmp_17;
			t__T[] f_tmp_20 = env.inputOfAlice(Utils.fromInt(i, 32));
			t__T[] f_tmp_19 = bb.read(f_tmp_20);
			t__T[] __tmp17 = f_tmp_19;
			t__T[] f_tmp_21 = intLib.sub(__tmp16,__tmp17);
			t__T[] __tmp18 = f_tmp_21;
			t__T[] f_tmp_22 = intLib.multiply(__tmp15,__tmp18);
			t__T[] __tmp19 = f_tmp_22;
			t__T[] f_tmp_23 = intLib.add(flt,__tmp19);
			t__T[] __tmp20 = f_tmp_23;
			flt = __tmp20;
			int __tmp21 = 1;
			int f_tmp_25 = i + __tmp21;
			int __tmp22 = f_tmp_25;
			i = __tmp22;
			boolean f_tmp_26 = i < N;
			__tmp12 = f_tmp_26;
		}
		t__T[] __tmp23 = intLib.enforceBits(flt, n);
		t__T[] __tmp24 = noclass.intLib.sqrt(__tmp23);
		return __tmp24;

	}
}
