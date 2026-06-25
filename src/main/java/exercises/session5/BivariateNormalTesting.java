package exercises.session5;

import java.text.DecimalFormat;
import java.util.concurrent.Callable;

/**
 * In this class we test the precision and the efficiency of inversion sampling
 * and acceptance-rejection to generate a pair of independent normal random
 * variables with expectation mu and standard deviation sigma.
 * The test consists in computing the Monte-Carlo approximation of
 * P(X_1 < mu, X_2 < mu), where X_1, X_2 are independent normal random
 * variables with expectation mu. For each method we compute and print the
 * average percentage error with respect to the exact probability 0.25 and the
 * average time needed to generate the drawings and perform the computations.
 */
public class BivariateNormalTesting {

	static DecimalFormat formatterValue = new DecimalFormat("#0.00000");

	private double exactResult = 0.25;
	private double sumElapsedTime;
	private double sumError;
	private double mu;

	private int numberOfComputations;
	private int numberOfDrawingsPerComputation;

	/**
	 * Constructs a tester object.
	 *
	 * @param numberOfDrawingsPerComputation number of drawings per single Monte-Carlo computation
	 * @param numberOfComputations           number of times the computation is repeated
	 */
	public BivariateNormalTesting(int numberOfDrawingsPerComputation, int numberOfComputations) {
		this.numberOfDrawingsPerComputation = numberOfDrawingsPerComputation;
		this.numberOfComputations = numberOfComputations;
	}

	/**
	 * Tests the precision and efficiency of a selected generation method.
	 * The method is selected through a switch statement based on the enum
	 * {@link GenerationMethods}.
	 *
	 * @param normalTestSampler object of type NormalRandomVariable
	 * @param method            enum value selecting the generation method
	 * @throws Exception if unable to compute a result via the Callable
	 */
	public void testMethod(NormalRandomVariable normalTestSampler, GenerationMethods method) throws Exception {

		double sumElapsedTime = 0;
		double sumError = 0;
		mu = normalTestSampler.getAnalyticMean();

		Callable<double[]> methodToGenerateThePair = null;

		switch (method) {
		case INVERSIONSAMPLING:
			System.out.println("Inversion sampling");
			methodToGenerateThePair = () -> normalTestSampler.generateBivariate();
			break;

		case ACCEPTANCEREJECTION:
			System.out.println("Acceptance rejection");
			methodToGenerateThePair = () -> normalTestSampler.generateBivariateAR();
			break;
		}

		for (int i = 0; i < numberOfComputations; i++) {
			int numberOfTimesBothSmallerThanMu = 0;
			long startTime = System.currentTimeMillis();
			for (int j = 0; j < numberOfDrawingsPerComputation; j++) {
				final double[] generatedPair = methodToGenerateThePair.call();
				if (generatedPair[0] < mu && generatedPair[1] < mu) {
					numberOfTimesBothSmallerThanMu++;
				}
			}
			final double frequency = numberOfTimesBothSmallerThanMu / (double) numberOfDrawingsPerComputation;
			final long endTime = System.currentTimeMillis();
			final double elapsedTime = endTime - startTime;
			final double error = Math.abs(frequency - exactResult) / exactResult * 100;
			sumError += error;
			sumElapsedTime += elapsedTime;
		}

		double averageElapsedTime = sumElapsedTime / numberOfComputations;
		double averageError = sumError / numberOfComputations;

		System.out.println("Average elapsed time: " + formatterValue.format(averageElapsedTime));
		System.out.println("Average percentage error: " + formatterValue.format(averageError));
		System.out.println();
	}

	public static void main(String[] args) throws Exception {

		final double mu = 2;
		final double sigma = 1.0;
		final NormalRandomVariable normalTestSampler = new NormalRandomVariable(mu, sigma);

		final int numberOfDrawings = 10000;
		final int numberOfComputations = 1000;
		final BivariateNormalTesting tester = new BivariateNormalTesting(numberOfDrawings, numberOfComputations);

		for (final GenerationMethods method : GenerationMethods.values()) {
			tester.testMethod(normalTestSampler, method);
		}
	}
}
