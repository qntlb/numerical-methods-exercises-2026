package exercises.session6.confidenceintervals;

import exercises.session3.randomvariables.RandomVariableInterface;

/**
 * This class computes the lower and the upper bound of the
 * confidence interval at a given level for the mean of a sample of given size
 * of a random variable. Chebyshev's inequality is used.
 */
public class ChebyshevMeanConfidenceInterval extends MeanConfidenceInterval {

	// constructor
	public ChebyshevMeanConfidenceInterval(RandomVariableInterface randomVariable, int sampleSize) {
		// both inherited from MeanConfidenceInterval
		this.randomVariable = randomVariable;
		this.sampleSize = sampleSize;
	}

	/**
	 * It computes the lower bound of a confidence interval of a given level, based
	 * on Chebyshev's inequality.
	 *
	 * @param level, level of confidence
	 * @return value of the lower bound
	 */
	@Override
	public double getLowerBoundConfidenceInterval(double level) {
		return randomVariable.getAnalyticMean()
				- randomVariable.getAnalyticStdDeviation() / Math.sqrt(sampleSize * (1 - level));

	}

	/**
	 * It computes the upper bound of a confidence interval of a given level, based
	 * on Chebyshev's inequality.
	 *
	 * @param level, level of confidence
	 * @return value of the upper bound
	 */
	@Override
	public double getUpperBoundConfidenceInterval(double level) {
		return randomVariable.getAnalyticMean()
				+ randomVariable.getAnalyticStdDeviation() / Math.sqrt(sampleSize * (1 - level));

	}

}
