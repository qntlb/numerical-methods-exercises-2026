package exercises.session5;

import exercises.session3.randomvariables.RandomVariableAbstract;
import exercises.session3.randomvariables.ExponentialRandomVariable;

/**
 * This class represents normal random variables. It extends
 * RandomVariableAbstract, and gives the implementation of the methods depending
 * directly on the distribution.
 */
public class NormalRandomVariable extends RandomVariableAbstract {

	private double mu; // mean
	private double sigma; // standard deviation
	private final int orderOfApproximationForErf = 10;

	// there are no public setters: every object will have its own mu and sigma
	/**
	 * Creates an object representing a normal random variable with mean mu and
	 * standard deviation sigma.
	 *
	 * @param mu,    the mean
	 * @param sigma, the standard deviation
	 */
	public NormalRandomVariable(double mu, double sigma) {
		this.mu = mu;
		this.sigma = sigma;
	}

	@Override
	public double getAnalyticMean() {
		return mu;
	}

	@Override
	public double getAnalyticStdDeviation() {
		return sigma;
	}

	@Override
	public double getDensityFunction(double x) {
		return Math.exp(-(x - mu) * (x - mu) / (2 * sigma * sigma)) / (sigma * Math.sqrt(2 * Math.PI));
	}

	/*
	 * Returns the value of the Taylor expansion of the error function
	 * (Abramowitz and Stegun 7.1.5) at a given point.
	 */
	private double errorFunction(double x) {
		double product = x;
		double factorial = 1; // double: avoids overflow issues
		double sum = product; // n=0 term (multiplied by 2/sqrt(pi) at the end)
		for (int n = 1; n <= orderOfApproximationForErf; n++) {
			factorial *= n;
			// (-1)^n x^(2n+1) = (-1)^(n-1) x^(2(n-1)+1) * (-1) * x^2
			product *= (-1) * x * x;
			sum += product / (factorial * (2 * n + 1));
		}
		return sum * 2 / Math.sqrt(Math.PI);
	}

	@Override
	public double getCumulativeDistributionFunction(double x) {
		return 0.5 * (1 + errorFunction((x - mu) / (Math.sqrt(2) * sigma)));
	}

	/*
	 * Returns the value at p in (0, 0.5] of the approximation of the quantile
	 * function for a STANDARD normal random variable from Abramowitz and Stegun
	 * 26.2.23. The formula gives x such that P(X >= x) = p, i.e. P(X <= -x) = p,
	 * so we return its negation to get the quantile at level p.
	 */
	private double abramowitzQuantileFunction(double p) {
		final double c0 = 2.515517;
		final double c1 = 0.802853;
		final double c2 = 0.010328;
		final double d1 = 1.432788;
		final double d2 = 0.189269;
		final double d3 = 0.001308;
		double t = Math.sqrt(Math.log(1 / (p * p)));
		return -(t - (c0 + c1 * t + c2 * t * t) / (1 + d1 * t + d2 * t * t + d3 * t * t * t));
	}

	@Override
	public double getQuantileFunction(double p) {
		/*
		 * The Abramowitz formula is for a standard normal and p <= 0.5. We use the
		 * symmetry qFS(p) = -qFS(1-p) for p > 0.5, then rescale: qF(p) = sigma*qFS(p) + mu.
		 */
		double quantileFunctionOfStandardNormal;
		if (p <= 0.5) {
			quantileFunctionOfStandardNormal = abramowitzQuantileFunction(p);
		} else {
			quantileFunctionOfStandardNormal = -abramowitzQuantileFunction(1 - p);
		}
		return sigma * quantileFunctionOfStandardNormal + mu;
	}

	/**
	 * Simulates a realization of a normal random variable by the
	 * acceptance-rejection method, using an Exp(1) random variable as proposal.
	 *
	 * @return a realization of the random variable
	 */
	public double generateAR() {
		// TODO
		return 0;
	}

	/**
	 * Generates a pair of independent realizations of a normal random variable
	 * by acceptance-rejection.
	 *
	 * @return array of length 2 containing the two realizations
	 */
	public double[] generateBivariateAR() {
		// TODO
		return new double[] { 0, 0 };
	}
}
