package exercises.session2;

import java.util.Arrays;

/**
 * This class contains one main method that calls method of the class
 * LinearCongruentialGenerator: example of lazy initialization.
 *
 * // initializing an array of longs. +1 because the first one will be the seed
 *
 */

public class PseudoRandomNumbersTesting {
	public static void main(String[] args) {

		long firstSeed = 2814749763100L;// the seed is the first entry of the sequence of pseudo random numbers

		int numberOfPseudoRandomNumbers = 5;

		AdjustedLinearCongruentialGenerator firstGenerator = new AdjustedLinearCongruentialGenerator(numberOfPseudoRandomNumbers,
				firstSeed);

		long[] sequenceGeneratedByTheFirstObject = firstGenerator.getRandomNumberSequence();

		System.out.println("Simulation of " + numberOfPseudoRandomNumbers + " integers with seed " + firstSeed
				+ " : " + Arrays.toString(sequenceGeneratedByTheFirstObject));

		System.out.println();

		int numberOfPseudoRandomNumbersToGetPrintedNow = 3;
		System.out.println("First " + numberOfPseudoRandomNumbersToGetPrintedNow + " numbers of the random sequence, excluded the seed:");
		/*
		 * maybe the user is not interested to have all the sequence, but only in the
		 * first numbers
		 */
		for (int i = 0; i < numberOfPseudoRandomNumbersToGetPrintedNow; i++) {
			System.out.println(firstGenerator.getNextInteger());
		}

		System.out.println();

		long secondSeed = 8;

		AdjustedLinearCongruentialGenerator secondGenerator = new AdjustedLinearCongruentialGenerator(numberOfPseudoRandomNumbers,
				secondSeed);

		long[] sequenceGeneratedByTheSecondObject = secondGenerator.getRandomNumberSequence();

		System.out.println("Simulation of " + numberOfPseudoRandomNumbers + " integers with seed " + secondSeed
				+ " : " + Arrays.toString(sequenceGeneratedByTheSecondObject));
	}
}