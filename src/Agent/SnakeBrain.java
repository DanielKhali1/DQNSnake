package Agent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import Snake.CurrentDirection;

public class SnakeBrain
{
	CurrentDirection DecidedDirection;


	private int score, fruitX, fruitY;
	public double[][] Grid;
	public ArrayList<int[]> Positions = new ArrayList<int[]>();
	public double[] headNeighbors = {0, 0, 0, 0};
	boolean dead;
	int currentActionIndex;

	int savedX, savedY;
	boolean f;

	// Will move the snake based off hard inputs such as objective item location, snake head location, & snake body location.
	public SnakeBrain()
	{
		currentActionIndex = 3;
		Grid = new double[10][10];
		reset();
	}

	public void randomFruit()
	{

		//list of valid grid positions
		ArrayList<int[]> VGP = new ArrayList<int[]>();

		for(int i = 0; i < Grid.length; i++)
		{
			for(int j = 0; j < Grid[0].length; j++)
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
/*
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
	}
*/

	public void MakeDecision()
	{

		//change the action Index //to move the snake

		//remember that 0: down 1: up  2: left 3: right
		int tempActionIndex = currentActionIndex;

		int snakeHeadX = Positions.get(0)[1];
		int snakeHeadY = Positions.get(0)[0];

		int width = Grid[0].length;
		int height = Grid.length;

		//if it's on the same x axis
		if(fruitX == snakeHeadX)
		{
			//if the fruit above the snake
			if(fruitY < snakeHeadY)
			{
				boolean snakeBodyIsCloser = false;
				for(int i = 0; i < Positions.size(); i++)
				{
					//if the body is on the same x axis and the fruit is above
					if(Positions.get(i)[0] == snakeHeadX && Positions.get(i)[1] > fruitY)
					{
						snakeBodyIsCloser = true;
					}

					if(!snakeBodyIsCloser)
					{
						tempActionIndex = 1;
					}
				}
			}
			//if the fruit is below the snake
			else if(fruitY > snakeHeadY)
			{
				boolean snakeBodyIsCloser = false;
				for(int i = 0; i < Positions.size(); i++)
				{
					//if the body is on the same x axis and the fruit is above
					if(Positions.get(i)[0] == snakeHeadX && Positions.get(i)[1] < fruitY)
					{
						snakeBodyIsCloser = true;
					}

					if(!snakeBodyIsCloser)
					{
						tempActionIndex = 0;
					}
				}
			}


		}

		//if the fruit is on the same row as the snake
		else if(fruitY == snakeHeadY)
		{
			//if the fruit is on the left of the snake
			if(fruitX < snakeHeadX)
			{
				boolean snakeBodyIsCloser = false;
				for(int i = 0; i < Positions.size(); i++)
				{
					//if the body is on the same x axis and the fruit is way more right?
					if(Positions.get(i)[1] == snakeHeadY && Positions.get(i)[0] > fruitX)
					{
						snakeBodyIsCloser = true;
					}

					if(!snakeBodyIsCloser)
					{
						tempActionIndex = 2;
					}
				}
			}
			//(y, x)
			//if the fruit is on the right of the snake
			else if(fruitX > snakeHeadX)
			{
				boolean snakeBodyIsCloser = false;
				for(int i = 0; i < Positions.size(); i++)
				{
					//if the body is on the same x axis and the fruit is way more left?
					if(Positions.get(i)[1] == snakeHeadY && Positions.get(i)[0] < fruitX)
					{
						snakeBodyIsCloser = true;
					}

					if(!snakeBodyIsCloser)
					{
						tempActionIndex = 3;
					}
				}
			}
		}
			//I'm going to hit a wall :( to the right
			else if(snakeHeadX == width-1 && tempActionIndex == 3)
			{
				//if I'm in the top right corner go down
				if(snakeHeadY == 0)
				{
					tempActionIndex = 0;
				}
				//if I'm in the bottom right corner go up
				else if(snakeHeadY == height-1)
				{
					tempActionIndex = 1;
				}
				else
				{
					if(fruitY > snakeHeadY)
					{
						tempActionIndex = 0;
					}
					else if(fruitY < snakeHeadY)
					{
						tempActionIndex = 1;
					}
				}
			}

			else if(snakeHeadX == 0 && tempActionIndex == 2)
			{
				//if I'm in the top left corner go down
				if(snakeHeadY == 0)
				{
					tempActionIndex = 0;
				}
				//if I'm in the bottom left corner go up
				else if(snakeHeadY == height-1)
				{
					tempActionIndex = 1;
				}

				//if your not in a corner go towards the fruit
				else
				{
					if(fruitY > snakeHeadY)
					{
						tempActionIndex = 0;
					}
					else if(fruitY < snakeHeadY)
					{
						tempActionIndex = 1;
					}
				}
			}

		//I'm going to hit a wall :( to the right
		else if(snakeHeadY == height-1 && tempActionIndex == 0)
		{
			//if I'm in the top left corner go right
			if(snakeHeadX == 0)
			{
				tempActionIndex = 3;
			}
			//if I'm in the bottom right corner go left
			else if(snakeHeadX == width-1)
			{
				tempActionIndex = 2;
			}
			else
			{
				if(fruitX > snakeHeadX)
				{
					tempActionIndex = 3;
				}
				else if(fruitX < snakeHeadX)
				{
					tempActionIndex = 2;
				}
			}
		}

		else if(snakeHeadY == 0 && tempActionIndex == 1)
		{
			//if I'm in the top left corner go right
			if(snakeHeadX == 0)
			{
				tempActionIndex = 3;
			}
			//if I'm in the bottom right corner go left
			else if(snakeHeadX == width-1)
			{
				tempActionIndex = 2;
			}
			else
			{
				if(fruitX > snakeHeadX)
				{
					tempActionIndex = 3;
				}
				else if(fruitX < snakeHeadX)
				{
					tempActionIndex = 2;
				}
			}
		}



		currentActionIndex = tempActionIndex;
		//if it's below

	}


	public void ExecuteAction()
	{


		for(int i = Positions.size()-1; i > 0; i--)
		{
			int[] temp = new int[2];
			temp[0] = Positions.get(i-1)[0];
			temp[1] = Positions.get(i-1)[1];

			Positions.set(i, temp);
		}

		if(currentActionIndex == 0) // go up
		{
			Positions.get(0)[0]++;
		}
		else if(currentActionIndex == 1) // go down
		{
			Positions.get(0)[0]--;
		}
		else if(currentActionIndex == 2) // go left
		{
			Positions.get(0)[1]--;
		}
		else if(currentActionIndex == 3) // go right
		{
			Positions.get(0)[1]++;
		}
		UpdateGrid();


		//ran into itself
				for(int i = 1 ; i < Positions.size(); i++)
				{
					if( Positions.get(0)[0] == Positions.get(i)[0] && Positions.get(0)[1] == Positions.get(i)[1] )
					{
						dead = true;
					}
				}

				// dead if out of bounds
				if(Positions.get(0)[0] < 0 || Positions.get(0)[1] < 0 || Positions.get(0)[1] >= Grid[0].length || Positions.get(0)[0] >= Grid.length)
				{
					dead = true;
				}

				// Grow if on fruit
				if(Positions.get(0)[1] == fruitX && Positions.get(0)[0] == fruitY)
				{
					score++;

					randomFruit();

					savedX = Positions.get(0)[1];
					savedY = Positions.get(0)[0];

					f = true;

				}

				if(f)
				{
					int[] temp = new int[2];
					temp[0] = savedY;
					temp[1] = savedX;

					Positions.add(temp);

					f = false;
				}
		UpdateGrid();

	}

	public void UpdateGrid()
	{
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
		}
	}


	public void reset()
	{
		{
			currentActionIndex = 3;
			double[] tmp = {0, 0, 0, 0};
			headNeighbors = tmp;

			Positions = new ArrayList<int[]>();

			Positions.add(new int[2]);


			Positions.get(0)[0] = 0;
			Positions.get(0)[1] = 0;

			randomFruit();

			score = 0;
			dead = false;

			UpdateGrid();
		}

	}

	public boolean isDead() {

		return dead;
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




}
