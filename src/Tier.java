import java.io.Serializable;

public class Tier implements Serializable
{
	String rowName;
	int max;
	int min;
	//Min is mostly for the gui to indicate more children are needed,
	//it doesn't affect anything yet.
	
	//ex, if you want only one vision statement and never less or more, set the tier
	//with rowName "Vision Statement" to have max=1 and min=1, Not for that node to have
	//a max of one child and a min of one child.

	public Tier(String rowName)
	{
		this.rowName = rowName;
		this.max = 0;
		this.min = 0;
	}
	
	public Tier()
	{
		this.rowName="";
		this.max=0;
		this.min=0;
	}

	public Tier(String rowName, int max, int min)
	{
		if(min>max)
		{
			System.out.println("Min is greater than max, setting min to 0.");
			this.min=0;
		}
		else
		{
			this.min = min;
		}
		this.rowName = rowName;
		this.max = max;
		
	}

	public String getRowName()
	{
		return rowName;
	}

	public void setRowName(String rowName)
	{
		this.rowName = rowName;
	}

	public int getMax()
	{
		return max;
	}

	public void setMax(int max)
	{
		if(max<this.min)
		{
			System.out.println("Need a higher max or lower min. Current min "+this.getMin());
		}
		else
		{
			this.max=max;
		}
		
	}

	public int getMin()
	{
		return min;
	}

	public void setMin(int min)
	{
		if(min<0)
		{
			System.out.println("No negative number of children for a tier.");
		}
		else if(min>max)
		{
			System.out.println("Need a higher max or lower min. Current max "+this.getMax());
		}
		else
		{
			this.min = min;
		}
	}

}
