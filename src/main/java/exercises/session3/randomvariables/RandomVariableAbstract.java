package exercises.session3.randomvariables;

import java.util.function.DoubleUnaryOperator;

import exercises.usefulmethodsmatricesandvectors.UsefulMethodsMatricesAndVectors;

/**
 * This abstract class implements the interface RandomVariableInterface.
 * You can see that the methods whose implementation does not directly depend on
 * the specific type of the random variable are implemented here.
 */
public abstract class RandomVariableAbstract implements RandomVariableInterface {
	
	@Override
	public double generate() {
		/*
		 * Generalized inversion of the distribution function: here we use the fact
		 * that X := F^(-1)(U) with U uniformly distributed in
		 * (0,1) and F^(-1) defined as F^(-1)(y) := inf{x|F(x) >= y} has cumulative
		 * distribution function F.  F^(-1) is here the quantile function of the random
		 * variable. The implementation of getQuantileFunction(double x) will be given
		 * in the classes extending this abstract one, since of course it depends on the
		 * specific distribution.
		 */
		double generationOfUniformRandomVariable = Math.random();
		return getQuantileFunction(generationOfUniformRandomVariable);// X_i
	}

	/*
	 * This method initializes randomVariableRealizations as a one-dimensional
	 * array of the given length n, and it fills it by calling generate() n times.
	 * It is used to compute the mean and the standard deviation of a sample of
	 * independent realizations of the random variable.
	 */
	private double[] generateValues(int n) {
		double[] randomVariableRealizations = new double[n];
		for (int i = 0; i < n; i++) {
			randomVariableRealizations[i] = generate();// generation of the new realization
		}
		return randomVariableRealizations;
	}


	@Override
	public double getSampleMean(int n) {
		/*
		 * The method might be called more than once, obtaining different results. Every time
		 * the method is called we call generateValues(n), that is supposed
		 * to give different values to the one-dimensional array randomVariableRealizations
		 * at each call.
		 */
		return UsefulMethodsMatricesAndVectors.getAverage(generateValues(n));
	}

	@Override
	public double getSampleStdDeviation(int n) {
		/*
		 * The method might be called more than once, obtaining different results.
		 * Every time the method is called we call generateValues(n), that is supposed
		 * to give different values to the one-dimensional array randomVariableRealizations
		 * at each call.
		 */
		return UsefulMethodsMatricesAndVectors.getStandardDeviation(generateValues(n));
	}
	

	public double[] generateBivariate() {
		return new double[] { generate(), generate() };
	}
	
	/*
	 * This method initializes randomVariableRealizationsFunction as a
	 * one-dimensional array of the given length n, and it fills it by calling
	 * generate(DoubleUnaryOperator function) n times. It is used to compute the
	 * mean and the standard deviation of a sample of independent realizations of
	 * the random variable.
	 */
	private double[] generateValues(int n, DoubleUnaryOperator function) {
		double[] randomVariableRealizationsFunction = new double[n];
		for (int i = 0; i < n; i++) {
			randomVariableRealizationsFunction[i] = function.applyAsDouble(generate());// generation of the new realization
		}
		return randomVariableRealizationsFunction;
	}
	
	
	@Override
	public double getSampleMean(int n, DoubleUnaryOperator function) {
		return UsefulMethodsMatricesAndVectors.getAverage(generateValues(n, function));
	}

	@Override
	public double getSampleStdDeviation(int n, DoubleUnaryOperator function) {
		return UsefulMethodsMatricesAndVectors
				.getStandardDeviation(generateValues(n, function));
	}
	
	@Override
	public double getSampleMeanWithWeightedMonteCarlo(int n, DoubleUnaryOperator function,
			RandomVariableInterface otherRandomVariable) {
		// TODO
		return 0.;
	}
}
