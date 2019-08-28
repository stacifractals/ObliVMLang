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
public class bit<t__T> implements IWritable<bit<t__T>, t__T> {
	public t__T v;

	public CompEnv<t__T> env;
	public IntegerLib<t__T> intLib;
	public FloatLib<t__T> floatLib;
	private NoClass<t__T> noclass;

	public bit(CompEnv<t__T> env) throws Exception {
		this.env = env;
		this.intLib = new IntegerLib<t__T>(env);
		this.floatLib = new FloatLib<t__T>(env, 24, 8);
		this.noclass = new NoClass<t__T>(env);
		this.v = env.inputOfAlice(false);
	}

	public int numBits() {
		return (0)+(1);
	}

	public t__T[] getBits() throws Exception {
		t__T[] ret = env.newTArray(this.numBits());
		t__T[] tmp_b;
		t__T tmp;
		int now = 0;
		tmp = v;
		ret[now] = tmp;
		now ++;
		return ret;
	}

	public bit<t__T> newObj(t__T[] data) throws Exception {
		if(data == null) {
			data = env.newTArray(this.numBits());
			for(int i=0; i<this.numBits(); ++i) { data[i] = intLib.SIGNAL_ZERO; }
		}
		if(data.length != this.numBits()) return null;
		bit<t__T> ret = new bit<t__T>(env);
		t__T[] tmp;
		int now = 0;
		ret.v = data[now];
		now ++;
		return ret;
	}

	public bit<t__T> fake() throws Exception {
		bit<t__T> ret = new bit<t__T>(env);
		ret.v = this.v;
		return ret;
	}

	public bit<t__T> muxFake(t__T __isDummy) throws Exception {
		bit<t__T> ret = new bit<t__T>(env);
		ret.v = this.v;
		return ret;
	}

}
