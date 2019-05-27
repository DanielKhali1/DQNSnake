package Agent;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.Serializable;

import NeuralNetwork.NeuralNetwork;

public class SnakeDQN extends DQN implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8320263365854467870L;
	/**
	 * 
	 */
	private boolean dead;
	private int steps;
	private int score;
	private int fruitX, fruitY;
	private int width, height;
	public double[][] Grid;
	
	public ArrayList<int[]> Positions = new ArrayList<int[]>();
	
	double hitWall = -1;
	double ateApple = 5;
	double idle = 0;
	double hitSelf = -3;
	
	final static int[] topology = {10, 70, 60, 4};
	
	int savedX = -1;
	int savedY = -1;
	
	public double[] headNeighbors = {0, 0, 0, 0};
	
	int f;
	
	public SnakeDQN(double learningRate, double discountFactor, int width, int height)
	{
		
		super(topology, learningRate, discountFactor);
		this.width = width;
		this.height = height;
		Grid = new double[height][width];
		reset();
	}
	
	public SnakeDQN(double learningRate, double discountFactor, int width, int height, double hitWall, double ateApple, double idle, double hitSelf)
	{
		super(topology, learningRate, discountFactor);
		
		this.hitSelf = hitSelf;
		this.idle = idle;
		this.ateApple = ateApple;
		this.hitWall = hitWall;
		this.width = width;
		this.height = height;
		Grid = new double[height][width];
		reset();
	}

	public void UpdateGrid()
	{
		double[] tmp = {0, 0, 0, 0};
		headNeighbors = tmp;
		
		for(int i = 0; i < Grid.length; i++)
		{
			for( int j = 0; j < Grid[0].length; j++)
			{
				if(i == Positions.get(0)[0] && j == Positions.get(0)[1])
					Grid[i][j] = 1;
				else if(i == fruitY && j == fruitX)
					Grid[i][j] = .5;
				else
					Grid[i][j] = 0;
			}
		}
		
		for(int i = 1; i < Positions.size(); i++)
		{			
			try {
			Grid[Positions.get(i)[0]][Positions.get(i)[1]] = 1;
			}
			catch(ArrayIndexOutOfBoundsException e)
			{
				
			}
		}
		
		//update neighbors
		
		//up
		try 
		{
			if(Grid[Positions.get(0)[0]-1][Positions.get(0)[1]] == 1)
			{
				headNeighbors[0] = 1;
			}
		}
		catch(Exception e)
		{
			headNeighbors[0] = 1;
		}
		
		//down
		try 
		{
			if(Grid[Positions.get(0)[0]+1][Positions.get(0)[1]] == 1)
			{
				headNeighbors[1] = 1;
			}
		}
		catch(Exception e)
		{
			headNeighbors[1] = 1;
		}
		
		//left
		try 
		{
			if(Grid[Positions.get(0)[0]][Positions.get(0)[1]-1] == 1)
			{
				headNeighbors[2] = 1;
			}
		}
		catch(Exception e)
		{
			headNeighbors[2] = 1;
		}
		
		//right
		try 
		{
			if(Grid[Positions.get(0)[0]+1][Positions.get(0)[1]+1] == 1)
			{
				headNeighbors[3] = 1;
			}
		}
		catch(Exception e)
		{
			headNeighbors[3] = 1;
		}
	}
	
	public void reset()
	{
		double[] tmp = {0, 0, 0, 0};
		headNeighbors = tmp;
		
		Positions = new ArrayList<int[]>();
		
		Positions.add(new int[2]);
		
		
		Positions.get(0)[0] = 0;
		Positions.get(0)[1] = 0;
		
		do
		{
			fruitX = (int) (Math.random() * width);
			fruitY = (int) (Math.random() * height);
		}
		while(fruitX != Positions.get(0)[0] && fruitY != Positions.get(0)[1]);
		
		score = 0;
		steps = 0;
		dead = false;
		
		UpdateGrid();
	}
	
	public double[] FlattenGrid()
	{
		double[] flattenedGrid = new double[Grid.length*Grid.length];
		
		ArrayList<Double> adderBoi = new ArrayList<Double>();
		
		for(int i = 0; i < Grid.length; i++)
		{
			for( int j = 0; j < Grid[0].length; j++)
			{
				adderBoi.add(Grid[i][j]);
			}
		}
		
		for(int i = 0; i < flattenedGrid.length; i++)
		{
			flattenedGrid[i] = adderBoi.get(i);
		}

		return flattenedGrid;
	}
	
	
	
	@Override
	protected double[] getState()
	{
		return new double[] {Math.signum(Positions.get(0)[1] - fruitX), Math.signum(Positions.get(0)[0] - fruitY), Math.signum(Positions.get(0)[1]), Math.signum(Positions.get(0)[0]), Math.signum(fruitX), Math.signum(fruitY), headNeighbors[0], headNeighbors[1], headNeighbors[2], headNeighbors[3]};
	}

	@Override
	public boolean isDone()
	{
		return steps >= 500 || dead;
	}

	@Override
	protected double executeActionAndGetReward(int actionIndex)
	{		
		double[] tmp = {0, 0, 0, 0};
		headNeighbors = tmp;
		
		
		for(int i = Positions.size()-1; i > 0; i--)
		{
			int[] temp = new int[2];
			temp[0] = Positions.get(i-1)[0];
			temp[1] = Positions.get(i-1)[1];

			Positions.set(i, temp);
		}
		
		if(actionIndex == 0) // go up
		{
			Positions.get(0)[0]++;
		}
		else if(actionIndex == 1) // go down
		{
			Positions.get(0)[0]--;
		}
		else if(actionIndex == 2) // go left
		{
			Positions.get(0)[1]--;
		}
		else if(actionIndex == 3) // go right
		{
			Positions.get(0)[1]++;
		}
		UpdateGrid();		


		
		steps++;
				
		//ran into itself
		for(int i = 1 ; i < Positions.size(); i++)
		{
			if( Positions.get(0)[0] == Positions.get(i)[0] && Positions.get(0)[1] == Positions.get(i)[1] )
			{
				dead = true;
				return hitSelf;
			}
		}
		
		// dead if out of bounds
		if(Positions.get(0)[0] < 0 || Positions.get(0)[1] < 0 || Positions.get(0)[1] >= width || Positions.get(0)[0] >= height)
		{
			dead = true;
			return hitWall;
		}
		
		// award if on fruit
		if(Positions.get(0)[1] == fruitX && Positions.get(0)[0] == fruitY)
		{
			score++;
			
			randomFruit();
			
			
			savedX = Positions.get(0)[1];
			savedY = Positions.get(0)[0];
			
			f = 1;
			f--;
			
			return ateApple;
		}		

		if(f == 0)
		{
			int[] temp = new int[2];
			temp[0] = savedY;
			temp[1] = savedX;
			
			Positions.add(temp);
			f--;
		}		
		return idle;		
	}
	
	public void randomFruit()
	{

		//list of valid grid positions
		ArrayList<int[]> VGP = new ArrayList<int[]>();
		
		for(int i = 0; i < height; i++)
		{
			for(int j = 0; j < width; j++)
			{
				int[] temp  = new int[2];
				temp[0] = i;
				temp[1] = j;
				VGP.add(temp);
			}
		}
		
		//remove snake's positions from the list of valid grid positions
		for(int i = 0; i < VGP.size(); i++)
		{
			for(int j = 0; j < Positions.size(); j++)
			{
				if(Positions.get(j)[0] == VGP.get(i)[0] && Positions.get(j)[1] == VGP.get(i)[1])
				{
					VGP.remove(i);
					if(i != 0)
						i-=1;
				}
			}
		}
		int randomIndex = (int) (Math.random() * VGP.size());
		
		fruitX = VGP.get(randomIndex)[1];
		fruitY = VGP.get(randomIndex)[0];
		
	}
	
	public static void saveSnakeDQN(SnakeDQN snek, String filePath)
	{
		try(FileOutputStream fout = new FileOutputStream(filePath);
				ObjectOutputStream oos = new ObjectOutputStream(fout);)
		{
			oos.writeObject(snek);
		}
		catch(Exception e)
		{
			System.err.println("ERROR: Failure in saving network to " + filePath + ". Reason is " + e.getMessage());
		}
	}
	
	public static SnakeDQN loadSnakeDQN(String filePath)
	{
		try(FileInputStream fin = new FileInputStream(filePath);
				ObjectInputStream ois = new ObjectInputStream(fin);)
			{
				return (SnakeDQN) ois.readObject();
			}
			catch(Exception e)
			{
				System.err.println("ERROR: Failure in loading network from " + filePath + ". Reason is " + e.getMessage());
			}
			return null;
	}
	
	public boolean isDead()
	{
		return dead;
	}

	public int getSteps()
	{
		return steps;
	}

	public int getScore()
	{
		return score;
	}

	public int getFruitX()
	{
		return fruitX;
	}

	public int getFruitY()
	{
		return fruitY;
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}
}