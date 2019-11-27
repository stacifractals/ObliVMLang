package com.oblivm.backend.generated.test;
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
		SecureArray<t__T> alc = new SecureArray<t__T>(env, 32, 32);
		for(int _j_2 = 0; _j_2 < 32; ++_j_2) {
			alc.write(intLib.toSignals(_j_2), env.inputOfAlice(Utils.fromInt(0, 32)));
		}
		int N = 0;
		t__T[] flt = env.inputOfAlice(Utils.fromInt(0, 32));
		t__T[] _t_state = env.inputOfAlice(Utils.fromInt(0, 32));
		t__T _t_state_new = env.inputOfAlice(false);
		t__T[] _t_count = env.inputOfAlice(Utils.fromInt(0, 32));
		int __tmp0 = 32;
		int f_tmp_1 = n / __tmp0;
		int __tmp1 = f_tmp_1;
		N = __tmp1;
		int __tmp2 = 0;
		flt = env.inputOfAlice(Utils.fromInt(__tmp2, 32));
		int __tmp3 = 0;
		boolean f_tmp_4 = N > __tmp3;
		boolean __tmp4 = f_tmp_4;
		if(__tmp4) {
			int __tmp5 = 5;
			t__T[] f_tmp_6 = env.inputOfAlice(Utils.fromInt(n, 32));
			t__T[] f_tmp_7 = env.inputOfAlice(Utils.fromInt(__tmp5, 32));
			alc.write(f_tmp_6,f_tmp_7);
		} else {
			int __tmp6 = 0;
			int __tmp7 = 0;
			t__T[] f_tmp_10 = env.inputOfAlice(Utils.fromInt(__tmp7, 32));
			t__T[] f_tmp_11 = env.inputOfAlice(Utils.fromInt(__tmp6, 32));
			alc.write(f_tmp_10,f_tmp_11);
		}
		return flt;

	}
}
