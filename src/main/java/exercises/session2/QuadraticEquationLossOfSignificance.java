package exercises.session2;

/**
 * Demonstrates the loss of significance (catastrophic cancellation) that
 * affects the classical formula for the smallest root of
 *
 *     x^2 + p x + q = 0,         x_small = -p/2 - sqrt(p^2/4 - q),
 *
 * and shows what happens for values of p so large that p*p overflows to
 * +Infinity in double precision.
 *
 * The numerically stable variant is left as an assignment for the students.
 */
public class QuadraticEquationLossOfSignificance {

	/**
	 * Smallest root of x^2 + p x + q = 0, computed with the classical
	 * formula -p/2 - sqrt(p^2/4 - q). This is the formula whose numerical
	 * behaviour we want to investigate.
	 */
	public static double smallestRootClassical(double p, double q) {
		return -p / 2.0 - Math.sqrt(p * p / 4.0 - q);
	}

	/**
	 * Plug a candidate root back into x^2 + p x + q to see how close to zero
	 * the residual is. For an exact root this is 0; for a poor approximation
	 * it can be very far from 0.
	 */
	public static double residual(double x, double p, double q) {
		return x * x + p * x + q;
	}

	public static void main(String[] args) {


		// ----------------------------------------------------------------
		// Part (a): empirical observation for p = -1E7, q = 1
		// ----------------------------------------------------------------
		// Approximated roots: x_large ~ 1E7, x_small ~ 1E-7 (since x1 * x2 = q = 1
		// and x1 + x2 = -p = 1E7).
		double p = -1.0E7;
		double q = 1.0;

		double xSmallClassical = smallestRootClassical(p, q);

		System.out.println("Part (a):  p = " + p + ",  q = " + q);
		System.out.println("Expected smallest root  ~ 1E-7");
		System.out.println("Classical formula gives  = " + xSmallClassical);
		System.out.println("Residual x^2 + p x + q   = "
				+ residual(xSmallClassical, p, q) + "  <-- loss of significance");
		// The two terms -p/2 = 5E6 and sqrt(p^2/4 - q) ~ 5E6 are almost
		// equal, so subtracting them throws away most of the significant
		// digits. The result is no longer accurate to machine precision
		System.out.println("Ulp at sqrt(p^2/4 - q): " + Math.ulp(Math.sqrt(p*p/4 - q)));
		System.out.println();

		// Push |p| further: now the cancellation becomes total. With
		// p = -1E9 the value (p*p/4) - q is rounded back to p*p/4 (because
		// 1 is well below the ulp of 32), and sqrt then returns
		// exactly -p/2, so the classical formula collapses to 0.
		double pWorse = -1.0E9;
		double qWorse = 1.0;
		double xSmallWorse = smallestRootClassical(pWorse, qWorse);
		System.out.println("More extreme case:  p = " + pWorse
				+ ",  q = " + qWorse);
		System.out.println("Expected smallest root ~ " + (-qWorse / pWorse));
		System.out.println("Ulp at p*p/4: " + Math.ulp(pWorse*pWorse / 4));
		System.out.println("Classical formula gives  = " + xSmallWorse
				+ "   <-- catastrophic cancellation");
		System.out.println("_".repeat(70));

		// ----------------------------------------------------------------
		// Part (c): overflow of p*p
		// ----------------------------------------------------------------
		// Some context on double precision
		// ----------------------------------------------------------------
		// In Java, the type "double" follows IEEE 754 binary64. The largest
		// finite positive value it can represent is Double.MAX_VALUE, and
		// anything strictly larger is rounded to +Infinity.
		System.out.println("About the maximum double:");
		System.out.println("Double.MAX_VALUE       = " + Double.MAX_VALUE);
		System.out.println("sqrt(Double.MAX_VALUE) = " + Math.sqrt(Double.MAX_VALUE));
		System.out.println("Double.MAX_VALUE * 2   = " + (Double.MAX_VALUE * 2.0)
				+ "   <-- overflow to +Infinity");
		System.out.println("_".repeat(70));
		
		// We pick |p| > sqrt(Double.MAX_VALUE) ~ 1.34E154 so that p*p is no
		// longer representable as a finite double and overflows to +Infinity.
		double pBig = -2.0E200;
		double qBig = 1.0;

		System.out.println("Part (c):  p = " + pBig + ",  q = " + qBig);
		System.out.println("p*p in double precision  = " + (pBig * pBig)
				+ "   <-- overflows to +Infinity");

		double xSmallClassicalBig = smallestRootClassical(pBig, qBig);

		// Approximated roots: x_large ~ -p = 2E200 and x_small = q / x_large ~ -q/p,
		// i.e. ~ +5E-201, a finite positive but tiny number. The classical
		// formula, however, evaluates -p/2 - sqrt(p*p/4 - q): p*p/4 ->
		// +Infinity, so sqrt(...) -> +Infinity, and the result is
		// -p/2 - Infinity = -Infinity.
		System.out.println("Expected smallest root ~ " + (-qBig / pBig));
		System.out.println("Classical formula gives  = " + xSmallClassicalBig
				+ "   <-- mathematically inconsistent");
		System.out.println("_".repeat(70));
		System.out.println(Double.MIN_VALUE);
//		// ----------------------------------------------------------------
//		// The implementation of the numerically stable formula is left 
//		// as part of Assignment 1.
//		//----------------------------------------------------------------
	}
}
