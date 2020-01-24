package paillierPrototype;

import java.math.BigInteger;
import java.security.*;
import com.google.gson.annotations.Expose;


public class PaillierAlgorithm {

	@Expose(serialize = true, deserialize = true)
	public int STRENGTH = 0;
	@Expose(serialize = false, deserialize = false)
	public SecureRandom SECURE_RANDOM = null;
	@Expose(serialize = true, deserialize = true)
	public BigInteger N;
	@Expose(serialize = true, deserialize = true)
	public BigInteger G;
	@Expose(serialize = true, deserialize = true)
	public BigInteger R;
	@Expose(serialize = true, deserialize = true)
	public BigInteger P;
	@Expose(serialize = true, deserialize = true)
	public BigInteger Q;
	@Expose(serialize = true, deserialize = true)
	public BigInteger NSquare;
	@Expose(serialize = true, deserialize = true)
	public BigInteger MU;
	@Expose(serialize = true, deserialize = true)
	public BigInteger LAMBDA;
	@Expose(serialize = true, deserialize = true)
	public BigInteger C;

	public void initialize(int strength) {
		STRENGTH = strength;

	}

	public void generateKeyPair() {
		if (STRENGTH == 0) {
		//	STRENGTH = 512;
		}
		SECURE_RANDOM = new SecureRandom();
		BigInteger p = new BigInteger(STRENGTH, 16, SECURE_RANDOM);
		P = p;
		BigInteger q;
		do {
			q = new BigInteger(STRENGTH, 16, SECURE_RANDOM);
		} while (q.compareTo(p) == 0);

		Q = q;
		// lambda = lcm(p-1, q-1) = (p-1)*(q-1)/gcd(p-1, q-1)
		BigInteger lambda = (p.subtract(BigInteger.ONE).multiply(q
				.subtract(BigInteger.ONE))).divide(p.subtract(BigInteger.ONE)
				.gcd(q.subtract(BigInteger.ONE)));
		LAMBDA = lambda;

		BigInteger n = p.multiply(q); // n = p*q
		N = n;
		BigInteger nsquare = n.multiply(n); // nsquare = n*n
		NSquare = nsquare;
		BigInteger g;
		do {
			// generate g, a random integer in Z*_{n^2}
			do {
				g = new BigInteger(STRENGTH * 2, SECURE_RANDOM);
			} while (g.compareTo(nsquare) >= 0
					|| g.gcd(nsquare).intValue() != 1);
			G = g;

			// verify g, the following must hold: gcd(L(g^lambda mod n^2), n) =
			// 1,
			// where L(u) = (u-1)/n
		} while (g.modPow(lambda, nsquare).subtract(BigInteger.ONE).divide(n)
				.gcd(n).intValue() != 1);

		// mu = (L(g^lambda mod n^2))^{-1} mod n, where L(u) = (u-1)/n
		BigInteger mu = g.modPow(lambda, nsquare).subtract(BigInteger.ONE)
				.divide(n).modInverse(n);
		MU = mu;

	}

	/**
	 * c = g^m * r^n mod n^2
	 */
	public BigInteger encrypt(BigInteger m) throws Exception {
		BigInteger r;
		// generate r, a random integer in Z*_n
		do {
			r = new BigInteger(STRENGTH * 2, SECURE_RANDOM);
		} while (r.compareTo(N) >= 0 || r.gcd(N).intValue() != 1);
		R = r;
		if (m.compareTo(BigInteger.ZERO) < 0 || m.compareTo(N) >= 0) {
			throw new Exception(
					"encrypt(BigInteger m): plaintext m is not in Z_n");
		}
		// c = g^m*r^n mod n^2
		BigInteger c = (G.modPow(m, NSquare).multiply(R.modPow(N, NSquare)))
				.mod(NSquare);
		C = c;
		return c;
	}

	/**
	 * m = L(c^lambda mod n^2)mu mod n
	 */
	public BigInteger decrypt(BigInteger c) {

		BigInteger m = c.modPow(LAMBDA, NSquare).subtract(BigInteger.ONE)
				.divide(N).multiply(MU).mod(N);

		return m;
	}

	public BigInteger getN() {
		return N;
	}

	public BigInteger getG() {
		return G;
	}

	public BigInteger getR() {
		return R;
	}

	public BigInteger getP() {
		return P;
	}

	public BigInteger getQ() {
		return Q;
	}

	public BigInteger getNSquare() {
		return NSquare;
	}

	public BigInteger getMU() {
		return MU;
	}

	public BigInteger getLAMBDA() {
		return LAMBDA;
	}

	public BigInteger getC() {
		return C;
	}
}