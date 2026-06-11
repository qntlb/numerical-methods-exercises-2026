package exercises.session3.randomvariables;

/**
 * This class represents exponential random variables. It extends
 * RandomVariableAbstract, and gives the implementation of the methods depending
 * directly on the distribution.
 */
public class ExponentialRandomVariable extends RandomVariableAbstract {

	private final double lambda; // intensity

	// there are no public setters: every object will have its own intensity and will be immutable
	/**
	 * Creates an object representing an exponential random variable with
	 * intensity lambda.
	 *
	 * @param lambda, the intensity of the random variable
	 */
	public ExponentialRandomVariable(double lambda) {
		this.lambda = lambda;
	}

	/**
	 * Returns the intensity of the exponential random variable.
	 *
	 * @return lambda
	 */
	public double getLambda() {
		return lambda;
	}

	@Override
	public double getAnalyticMean() {
		return 1.0 / lambda;
	}

	@Override
	public double getAnalyticStdDeviation() {
		return 1.0 / lambda;
	}

	@Override
	public double getDensityFunction(double x) {
		return lambda * Math.exp(-lambda * x);
	}

	@Override
	public double getCumulativeDistributionFunction(double x) {
		return 1 - Math.exp(-lambda * x);
	}

	@Override
	public double getQuantileFunction(double x) {
		/*
		 * The distribution function is continuous and strictly increasing, so we just
		 * have to find y such that F(y) = x. We have
		 *   F(y) = 1 - e^(-lambda * y) = x,
		 * therefore
		 *   e^(-lambda * y) = 1 - x   --->   y = -log(1 - x) / lambda.
		 */
		return -Math.log(1 - x) / lambda;
	}

}
