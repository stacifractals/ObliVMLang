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
public class Int<t__T> implements IWritable<Int<t__T>, t__T> {
	public t__T[] v;

	public CompEnv<t__T> env;
	public IntegerLib<t__T> intLib;
	public FloatLib<t__T> floatLib;
	private NoClass<t__T> noclass;
	private int n;

	public Int(CompEnv<t__T> env, int n) throws Exception {
		this.env = env;
		this.intLib = new IntegerLib<t__T>(env);
		this.floatLib = new FloatLib<t__T>(env, 24, 8);
		this.noclass = new NoClass<t__T>(env);
		this.n = n;
		this.v = env.inputOfAlice(Utils.fromInt(0, n));
	}

	public int numBits() {
		return (0)+(n);
	}

	public t__T[] getBits() throws Exception {
		t__T[] ret = env.newTArray(this.numBits());
		t__T[] tmp_b;
		t__T tmp;
		int now = 0;
		tmp_b = v;
		System.arraycopy(tmp_b, 0, ret, now, tmp_b.length);
		now += tmp_b.length;
		return ret;
	}

	public Int<t__T> newObj(t__T[] data) throws Exception {
		if(data == null) {
			data = env.newTArray(this.numBits());
			for(int i=0; i<this.numBits(); ++i) { data[i] = intLib.SIGNAL_ZERO; }
		}
		if(data.length != this.numBits()) return null;
		Int<t__T> ret = new Int<t__T>(env, n);
		t__T[] tmp;
		int now = 0;
		ret.v = env.newTArray(n);
		System.arraycopy(data, now, ret.v, 0, n);
		now += n;
		return ret;
	}

	public Int<t__T> fake() throws Exception {
		Int<t__T> ret = new Int<t__T>(env, n);
		ret.v = this.v;
		return ret;
	}

	public Int<t__T> muxFake(t__T __isDummy) throws Exception {
		Int<t__T> ret = new Int<t__T>(env, n);
		ret.v = this.v;
		return ret;
	}

}
