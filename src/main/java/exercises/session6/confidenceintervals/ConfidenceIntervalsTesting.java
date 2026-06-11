package exercises.session6.confidenceintervals;

import java.text.DecimalFormat;

import exercises.session3.randomvariables.ExponentialRandomVariable;
import exercises.session3.randomvariables.RandomVariableInterface;

/**
 * This class provides some experiments for the computation of confidence
 * intervals based on the Central Limit Theorem and on Chebyshev's inequality.
 */
public class ConfidenceIntervalsTesting {

	static DecimalFormat formatterValue = new DecimalFormat("#0.00000");

	static DecimalFormat formatterPercentage = new DecimalFormat("#0.00%");

	public static void main(String[] args) {
		double lambda = 0.2;
		int numberOfMeanComputations = 100;
		int sampleSize = 100000;
		double confidenceLevel = 0.95;
		/*
		 * exponentially distributed random variables: we want to compute the confidence
		 * intervals for the sample mean of size given by sampleSize.
		 */
		RandomVariableInterface exponential = new ExponentialRandomVariable(lambda);
		// with Chebyshev inequality
		ChebyshevMeanConfidenceInterval chebyshevIntervalCalculator = new ChebyshevMeanConfidenceInterval(exponential,
				sampleSize);
		// and with the Central Limit Theorem
		CLTMeanConfidenceInterval cLTIntervalCalculator = new CLTMeanConfidenceInterval(exponential, sampleSize);

		System.out.println("_".repeat(100));
		System.out.println("Confidence level \tMethod    \tSamples \tLower bound \tUpper bound \tFrequency");
		System.out.println(confidenceLevel + " ".repeat(15) + "\tChebyshev\t" + sampleSize  + " ".repeat(5) + "\t"
				+ formatterValue.format(chebyshevIntervalCalculator.getLowerBoundConfidenceInterval(confidenceLevel)) + " ".repeat(5) + "\t"
				+ formatterValue.format(chebyshevIntervalCalculator.getUpperBoundConfidenceInterval(confidenceLevel))  + " ".repeat(5) + "\t"
				+ formatterPercentage.format(chebyshevIntervalCalculator.frequencyOfInterval(numberOfMeanComputations, confidenceLevel))
				);
		System.out.println(confidenceLevel + " ".repeat(15) + "\tCLT" + " ".repeat(10) + "\t" + sampleSize + " ".repeat(5) + "\t"
				+ formatterValue.format(cLTIntervalCalculator.getLowerBoundConfidenceInterval(confidenceLevel))  + " ".repeat(5) + "\t"
				+ formatterValue.format(cLTIntervalCalculator.getUpperBoundConfidenceInterval(confidenceLevel))  + " ".repeat(5) + "\t"
				+ formatterPercentage.format(cLTIntervalCalculator.frequencyOfInterval(numberOfMeanComputations, confidenceLevel))
				);
	}
}
