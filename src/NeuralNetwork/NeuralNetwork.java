package NeuralNetwork;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

public class NeuralNetwork implements Serializable
{
	private static final long serialVersionUID = 9131461560923296729L;
	public Layer[] layers;
	public double momentum;
	public double learningRate;
	public int numberOfLayers;
	
	public NeuralNetwork(int topology[], double learningRate, double momentum)
	{
		this.learningRate = learningRate;
		this.momentum = momentum;
		numberOfLayers = topology.length;
		layers = new Layer[numberOfLayers];
		
		
		layers[0] = new Layer(topology[0],topology[0]);
		for(int i = 1; i < numberOfLayers; i++)
		{
			layers[i] = new Layer(topology[i], topology[i-1]);
		}
	}
	
	public void feedForward()
	{
		for(int i = 0; i < layers[0].neurons.length; i++)
		{
			layers[0].neurons[i].output = layers[0].input[i];
		}
		
		layers[1].input = layers[0].input;
		for(int i = 1; i < numberOfLayers; i++)
		{
			layers[i].feedForward();
			
			if(i != numberOfLayers - 1)
			{
				layers[i+1].input = layers[i].getOutputs();
			}
		}
	}
	
	private void backpropagateError()
	{
		for(int i = numberOfLayers-1; i > 0; i--)
		{
			for(int j = 0; j < layers[i].neurons.length; j++)
			{
				// Calculate bias difference
				layers[i].neurons[j].biasDiff =
						learningRate * 
						layers[i].neurons[j].signalError +
						momentum *
						layers[i].neurons[j].biasDiff;
				
				// Update bias
				layers[i].neurons[j].bias += layers[i].neurons[j].biasDiff;
				
				// Update weights
				for(int k = 0; k < layers[i].input.length; k++)
				{
					// Calculate weight difference
					layers[i].neurons[j].weightsDiff[k] =
							learningRate *
							layers[i].neurons[j].signalError *
							layers[i-1].neurons[k].output +
							momentum *
							layers[i].neurons[j].weightsDiff[k];
					
					// Update weight
					layers[i].neurons[j].weights[k] += layers[i].neurons[j].weightsDiff[k];
				}
			}
		}
	}
	
	private void calculateSignalErrors(double[] expectedOutputs)
	{
		int outputLayer = numberOfLayers - 1;
		for(int i = 0; i < layers[outputLayer].neurons.length; i++)
		{
			layers[outputLayer].neurons[i].signalError = 
					(expectedOutputs[i] -
					layers[outputLayer].neurons[i].output) *
					layers[outputLayer].neurons[i].output *
					(1 - layers[outputLayer].neurons[i].output);
		}
		
		double tempSum = 0;
		for(int i = numberOfLayers-2; i > 0; i--)
		{
			for(int j = 0; j < layers[i].neurons.length; j++)
			{
				tempSum = 0;
				for(int k = 0; k < layers[i+1].neurons.length; k++)
				{
					tempSum +=
							layers[i+1].neurons[k].weights[j] *
							layers[i+1].neurons[k].signalError;
				}
				
				layers[i].neurons[j].signalError =
						layers[i].neurons[j].output *
						(1 - layers[i].neurons[j].output) *
						tempSum;
			}
		}
	}
	
	private void updateWeights(double[] expectedOutputs)
	{
		calculateSignalErrors(expectedOutputs);
		backpropagateError();
	}
	
	public void train(double[] inputs, double[] expectedOutputs, boolean print)
	{
		for(int i = 0; i < layers[0].neurons.length; i++)
		{
			layers[0].input[i] = inputs[i];
		}
		
		feedForward();
		updateWeights(expectedOutputs);
		
		if(print)
		{
			System.out.println("Input: " + Arrays.toString(inputs));
			System.out.println("Actual Output: " + Arrays.toString(layers[numberOfLayers-1].getOutputs()));
			System.out.println("Target Output: " + Arrays.toString(expectedOutputs));
			System.out.println("Total Error: " + getOverallError(expectedOutputs) + "\n");
		}
	}
	
	public double getOverallError(double[] expectedOutputs)
	{
		double totalError = 0;
		for(int i = 0; i < layers[numberOfLayers - 1].neurons.length; i++)
		{
			totalError +=
					0.5 * 
					Math.pow(
							expectedOutputs[i] - 
							layers[numberOfLayers - 1].neurons[i].output,
							2);
		}
		return totalError;
	}
	
	public static void saveNetwork(NeuralNetwork network, String filePath)
	{
		try(FileOutputStream fout = new FileOutputStream(filePath);
			ObjectOutputStream oos = new ObjectOutputStream(fout);)
		{
			oos.writeObject(network);
		}
		catch(Exception e)
		{
			System.err.println("ERROR: Failure in saving network to " + filePath + ". Reason is " + e.getMessage());
		}
	}
	
	public static NeuralNetwork loadNetwork(String filePath)
	{
		try(FileInputStream fin = new FileInputStream(filePath);
			ObjectInputStream ois = new ObjectInputStream(fin);)
		{
			return (NeuralNetwork) ois.readObject();
		}
		catch(Exception e)
		{
			System.err.println("ERROR: Failure in loading network from " + filePath + ". Reason is " + e.getMessage());
		}
		return null;
	}
	
	public void predict(double[] inputs, boolean print)
	{
		for(int i = 0; i < layers[0].neurons.length; i++)
		{
			layers[0].input[i] = inputs[i];
		}
		
		feedForward();
		
		if(print)
		{
			System.out.println("Input: " + Arrays.toString(inputs));
			System.out.println("Actual Output: " + Arrays.toString(layers[numberOfLayers-1].getOutputs()));
		}
	}
	
	public double[] predict(double[] inputs)
	{
		for(int i = 0; i < layers[0].neurons.length; i++)
		{
			layers[0].input[i] = inputs[i];
		}
		
		feedForward();
		
		return layers[numberOfLayers-1].getOutputs();
	}
	
	public double[] getActualOutputs()
	{
		return layers[numberOfLayers-1].getOutputs();
	}
	
	public int getOutputSize()
	{
		return layers[numberOfLayers-1].getNumberOfNeurons();
	}
	
	public int getInputSize()
	{
		return layers[0].getNumberOfNeurons();
	}
}
