package NeuralNetwork;


import java.io.Serializable;



public class Layer implements Serializable
{
	private static final long serialVersionUID = 5573924680421461519L;
	public double input[];
	public Neuron[] neurons;
	
	public Layer(int numberOfNeurons, int numberOfInputs)
	{
		neurons = new Neuron[numberOfNeurons];
		
		for(int i = 0; i < numberOfNeurons; i++)
		{
			neurons[i] = new Neuron(numberOfInputs);
		}
		
		input = new double[numberOfInputs];
	}
	
	public void feedForward()
	{
		double tempOutput;
		
		for(int i = 0; i < neurons.length; i++)
		{
			tempOutput = neurons[i].bias;
			
			for(int j = 0; j < neurons[i].weights.length; j++)
			{
				tempOutput += input[j] * neurons[i].weights[j];
			}
			
			neurons[i].output = Layer.sigmoid(tempOutput);
			
		}
	}
	
	public double[] getOutputs()
	{
		double[] outputs = new double[neurons.length];
		for(int i = 0; i < outputs.length; i++)
		{
			outputs[i] = neurons[i].output;
		}
		return outputs;
	}
	
	public static double sigmoid(double x)
	{
		return 1 / (1 + Math.exp(-x));
	}
	
	public int getNumberOfNeurons()
	{
		return neurons.length;
	}
}
