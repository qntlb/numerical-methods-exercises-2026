package exercises.session3.randomvariables;

/**
 * This class tests some methods of the class ExponentialRandomVariable,
 * which (indirectly) implements RandomVariableInterface.
 */
public class RandomVariableTesting {

	public static void main(String[] args) {

		double lambda = 0.2;
		int numberOfSimulations = 1000000;
		ExponentialRandomVariable exponentialSampler = new ExponentialRandomVariable(lambda);

		System.out.println("Exponential random variable: comparing sample mean and std dev with analytic values");
		System.out.println();

		System.out.println("Sample mean:   " + exponentialSampler.getSampleMean(numberOfSimulations));
		System.out.println("Analytic mean: " + exponentialSampler.getAnalyticMean());
		System.out.println();

		System.out.println("Sample std dev:   " + exponentialSampler.getSampleStdDeviation(numberOfSimulations));
		System.out.println("Analytic std dev: " + exponentialSampler.getAnalyticStdDeviation());

	}
}
