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
	 * This method returns a one-dimensional array of the given length n, filled by
	 * drawings from the random variable obtained calling the method generate().
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
		 * the method is called we call generateValues(n).
		 */
		return UsefulMethodsMatricesAndVectors.getAverage(generateValues(n));
	}

	@Override
	public double getSampleStdDeviation(int n) {
		/*
		 * The method might be called more than once, obtaining different results.
		 * Every time the method is called we call generateValues(n).
		 */
		return UsefulMethodsMatricesAndVectors.getStandardDeviation(generateValues(n));
	}
	
	
	/* 
	 * What follows is needed only for session 5
	 */

	public double[] generateBivariate() {
		return new double[] { generate(), generate() };
	}
	
	/*
	 * This method returns a one-dimensional array of the given length n, filled with
	 * evaluations of the function function at randomly drawn points obtained calling the method
	 * generate(). It is used to compute the
	 * mean and the standard deviation of a sample of independent realizations of
	 * the random variable.
	 */ //(h(x_i), ..., h(x_n))
	private double[] generateValues(int n, DoubleUnaryOperator function) {
		double[] randomVariableRealizationsFunction = new double[n];
		for (int i = 0; i < n; i++) {
			randomVariableRealizationsFunction[i] = function.applyAsDouble(generate());// generation of the new realization
		}
		return randomVariableRealizationsFunction;
	}
	
	@Override // E[h(X)] by Monte Carlo approximation 1/n \sum_{i = 1}^n h(x_i)
	public double getSampleMean(int n, DoubleUnaryOperator function) {
		return UsefulMethodsMatricesAndVectors.getAverage(generateValues(n, function));
	}

	@Override
	public double getSampleStdDeviation(int n, DoubleUnaryOperator function) {
		return UsefulMethodsMatricesAndVectors
				.getStandardDeviation(generateValues(n, function));
	}
	
	@Override // E[h(X)] = E[h(Y) f(Y) / g(Y)] by Monte Carlo
	public double getSampleMeanWithWeightedMonteCarlo(int n, DoubleUnaryOperator function,
			RandomVariableInterface otherRandomVariable) {
		DoubleUnaryOperator weight = x -> (getDensityFunction(x)// the one of the "original" random variable
				/ otherRandomVariable.getDensityFunction(x));
		// the new integrand is the function h*f/g
		DoubleUnaryOperator integrand = (x -> function.applyAsDouble(x) * weight.applyAsDouble(x));
		return otherRandomVariable.getSampleMean(n, integrand);
	}
	
	@Override
	public double getSampleStdWithWeightedMonteCarlo(int n, DoubleUnaryOperator function,
			RandomVariableInterface otherRandomVariable) {
		DoubleUnaryOperator weight = x -> (getDensityFunction(x)// the one of the "original" random variable
				/ otherRandomVariable.getDensityFunction(x));
		DoubleUnaryOperator integrand = (x -> function.applyAsDouble(x) * weight.applyAsDouble(x));
		return otherRandomVariable.getSampleStdDeviation(n, integrand);
	}
}
