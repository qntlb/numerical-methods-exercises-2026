package exercises.session5;

import java.text.DecimalFormat;
import java.util.function.DoubleUnaryOperator;

/**
 * In this class we test importance sampling with weighted Monte-Carlo
 * by approximating P(X > 2.5), where X is a standard normal random variable.
 * Since this is a tail event, the variance of the standard estimator is very
 * high. We compare it with weighted Monte-Carlo where the proposal distribution
 * is N(2.5, 1), which concentrates samples near the barrier.
 */
public class ImportanceSamplingTesting {

	private final static DecimalFormat formatterPercentage = new DecimalFormat("0.0000 %");
	private final static DecimalFormat formatterDouble = new DecimalFormat("0.0000");

	public static void main(String[] args) {

		final int numberOfDrawings = 10000;
		final int numberOfTests = 1000;

		final double barrier = 2.5;

		final NormalRandomVariable standardNormal = new NormalRandomVariable(0.0, 1.0);
		final NormalRandomVariable shiftedNormal = new NormalRandomVariable(barrier, 1.0);
		final DoubleUnaryOperator indicatorIntegrand = x -> (x > barrier) ? 1.0 : 0.0; // 1_{X > barrier}

		final double analyticResult = 1 - standardNormal.getCumulativeDistributionFunction(barrier);

		int numberOfWinsStandardSampling = 0;
		int numberOfWinsImportanceSampling = 0;

		double sumPercentageErrorStandardSampling = 0.0;
		double sumPercentageErrorImportanceSampling = 0.0;

		for (int i = 0; i < numberOfTests; i++) {

			final double resultStandardSampling = standardNormal.getSampleMean(numberOfDrawings, indicatorIntegrand);
			final double resultImportanceSampling = standardNormal.getSampleMeanWithWeightedMonteCarlo(
					numberOfDrawings, indicatorIntegrand, shiftedNormal);

			final double errorStandardSampling = Math.abs(resultStandardSampling - analyticResult) / analyticResult;
			final double errorImportanceSampling = Math.abs(resultImportanceSampling - analyticResult) / analyticResult;

			sumPercentageErrorStandardSampling += errorStandardSampling;
			sumPercentageErrorImportanceSampling += errorImportanceSampling;

			if (errorStandardSampling > errorImportanceSampling) {
				numberOfWinsImportanceSampling++;
			} else {
				numberOfWinsStandardSampling++;
			}
		}

		double averagePercentageErrorStandardSampling = sumPercentageErrorStandardSampling / numberOfTests;
		double averagePercentageErrorImportanceSampling = sumPercentageErrorImportanceSampling / numberOfTests;

		System.out.println("Analytic probability P(X > " + barrier + "): "
				+ formatterPercentage.format(analyticResult));
		System.out.println();
		System.out.println("Average percentage error of standard sampling:   "
				+ formatterPercentage.format(averagePercentageErrorStandardSampling));
		System.out.println("Average percentage error of importance sampling: "
				+ formatterPercentage.format(averagePercentageErrorImportanceSampling));
		System.out.println();
		System.out.println("Number of times standard sampling is better:   " + numberOfWinsStandardSampling);
		System.out.println("Number of times importance sampling is better: " + numberOfWinsImportanceSampling);
		System.out.println();

		final double varianceStandardSampling = standardNormal.getSampleStdDeviation(numberOfDrawings, indicatorIntegrand);
		System.out.println("Std dev for standard sampling:   " + formatterDouble.format(varianceStandardSampling));

		final double varianceImportanceSampling = standardNormal.getSampleStdWithWeightedMonteCarlo(numberOfDrawings, indicatorIntegrand, shiftedNormal);
		System.out.println("Std dev for importance sampling: " + formatterDouble.format(varianceImportanceSampling));
	}
}
