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
		boolean f_tmp_4 = N >= __tmp3;
		boolean __tmp4 = f_tmp_4;
		if(__tmp4) {
			int __tmp5 = 5;
			t__T[] f_tmp_6 = env.inputOfAlice(Utils.fromInt(n, 32));
			t__T[] f_tmp_7 = env.inputOfAlice(Utils.fromInt(__tmp5, 32));
			alc.write(f_tmp_6,f_tmp_7);
			int __tmp6 = 1;
			boolean f_tmp_9 = N > __tmp6;
			boolean __tmp7 = f_tmp_9;
			if(__tmp7) {
				int __tmp8 = 5;
				int __tmp9 = 0;
				t__T[] f_tmp_12 = env.inputOfAlice(Utils.fromInt(__tmp9, 32));
				t__T[] f_tmp_13 = env.inputOfAlice(Utils.fromInt(__tmp8, 32));
				alc.write(f_tmp_12,f_tmp_13);
				int __tmp10 = 2;
				boolean f_tmp_15 = N > __tmp10;
				boolean __tmp11 = f_tmp_15;
				if(__tmp11) {
					int __tmp12 = 5;
					int __tmp13 = 0;
					t__T[] f_tmp_18 = env.inputOfAlice(Utils.fromInt(__tmp13, 32));
					t__T[] f_tmp_19 = env.inputOfAlice(Utils.fromInt(__tmp12, 32));
					alc.write(f_tmp_18,f_tmp_19);
				} else {
					int __tmp14 = 7;
					int __tmp15 = 1;
					t__T[] f_tmp_22 = env.inputOfAlice(Utils.fromInt(__tmp15, 32));
					t__T[] f_tmp_23 = env.inputOfAlice(Utils.fromInt(__tmp14, 32));
					alc.write(f_tmp_22,f_tmp_23);
				}
			} else {
				int __tmp16 = 7;
				int __tmp17 = 1;
				t__T[] f_tmp_26 = env.inputOfAlice(Utils.fromInt(__tmp17, 32));
				t__T[] f_tmp_27 = env.inputOfAlice(Utils.fromInt(__tmp16, 32));
				alc.write(f_tmp_26,f_tmp_27);
				int __tmp18 = 0;
				boolean f_tmp_29 = N == __tmp18;
				boolean __tmp19 = f_tmp_29;
				if(__tmp19) {
					int __tmp20 = 5;
					int __tmp21 = 0;
					t__T[] f_tmp_32 = env.inputOfAlice(Utils.fromInt(__tmp21, 32));
					t__T[] f_tmp_33 = env.inputOfAlice(Utils.fromInt(__tmp20, 32));
					alc.write(f_tmp_32,f_tmp_33);
				} else {
					int __tmp22 = 7;
					int __tmp23 = 1;
					t__T[] f_tmp_36 = env.inputOfAlice(Utils.fromInt(__tmp23, 32));
					t__T[] f_tmp_37 = env.inputOfAlice(Utils.fromInt(__tmp22, 32));
					alc.write(f_tmp_36,f_tmp_37);
				}
			}
		} else {
			int __tmp24 = 0;
			int __tmp25 = 0;
			t__T[] f_tmp_40 = env.inputOfAlice(Utils.fromInt(__tmp25, 32));
			t__T[] f_tmp_41 = env.inputOfAlice(Utils.fromInt(__tmp24, 32));
			alc.write(f_tmp_40,f_tmp_41);
			int __tmp26 = 1;
			boolean f_tmp_43 = N < __tmp26;
			boolean __tmp27 = f_tmp_43;
			if(__tmp27) {
				int __tmp28 = 2;
				boolean f_tmp_45 = N < __tmp28;
				boolean __tmp29 = f_tmp_45;
				if(__tmp29) {
					int __tmp30 = 5;
					int __tmp31 = 0;
					t__T[] f_tmp_48 = env.inputOfAlice(Utils.fromInt(__tmp31, 32));
					t__T[] f_tmp_49 = env.inputOfAlice(Utils.fromInt(__tmp30, 32));
					alc.write(f_tmp_48,f_tmp_49);
				} else {
					int __tmp32 = 7;
					int __tmp33 = 1;
					t__T[] f_tmp_52 = env.inputOfAlice(Utils.fromInt(__tmp33, 32));
					t__T[] f_tmp_53 = env.inputOfAlice(Utils.fromInt(__tmp32, 32));
					alc.write(f_tmp_52,f_tmp_53);
				}
				int __tmp34 = 5;
				int __tmp35 = 0;
				t__T[] f_tmp_56 = env.inputOfAlice(Utils.fromInt(__tmp35, 32));
				t__T[] f_tmp_57 = env.inputOfAlice(Utils.fromInt(__tmp34, 32));
				alc.write(f_tmp_56,f_tmp_57);
			} else {
				int __tmp36 = 7;
				t__T[] f_tmp_59 = env.inputOfAlice(Utils.fromInt(n, 32));
				t__T[] f_tmp_60 = env.inputOfAlice(Utils.fromInt(__tmp36, 32));
				alc.write(f_tmp_59,f_tmp_60);
				int __tmp37 = 1;
				boolean f_tmp_62 = N > __tmp37;
				boolean __tmp38 = f_tmp_62;
				if(__tmp38) {
					int __tmp39 = 5;
					int __tmp40 = 0;
					t__T[] f_tmp_65 = env.inputOfAlice(Utils.fromInt(__tmp40, 32));
					t__T[] f_tmp_66 = env.inputOfAlice(Utils.fromInt(__tmp39, 32));
					alc.write(f_tmp_65,f_tmp_66);
				} else {
					int __tmp41 = 7;
					int __tmp42 = 1;
					t__T[] f_tmp_69 = env.inputOfAlice(Utils.fromInt(__tmp42, 32));
					t__T[] f_tmp_70 = env.inputOfAlice(Utils.fromInt(__tmp41, 32));
					alc.write(f_tmp_69,f_tmp_70);
				}
			}
		}
		return flt;

	}
}
