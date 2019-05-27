package NeuralNetwork;

import java.io.Serializable;

public class Neuron implements Serializable
{
	private static final long serialVersionUID = -7038605375441606727L;
	public double output;
	public double weights[];
	public double weightsDiff[];
	public double bias;
	public double biasDiff;
	public double signalError;
	
	public Neuron(int numberOfWeights)
	{
		bias = -1 + Math.random() * 2;
		biasDiff = 0;
		
		weights = new double[numberOfWeights];
		weightsDiff = new double[numberOfWeights];
		for(int i = 0; i < weights.length; i++)
		{
			weights[i] = -1 + Math.random() * 2;
			weightsDiff[i] = 0;
		}
	}
}
