import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class BusinessPlan implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Node root;
	ArrayList<Tier> format;
	public BusinessPlan()
	{
		root=new Node("",null);
		format=new ArrayList<Tier>();
	}
	
	public BusinessPlan(ArrayList<Tier> arg)
	{
		root=new Node("",null);
		format=arg;
	}

	public BusinessPlan(String filename)
	{
		BusinessPlan temp=decodeXML(filename);
		root=temp.getRoot();
		format=temp.getFormat();
	}
	public Node getRoot()
	{
		return root;
	}
	
	public void setRoot(Node root)
	{
		this.root=root;
	}
	public ArrayList<Tier> getFormat()
	{
		return format;
	}

	public void setFormat(ArrayList<Tier> format)
	{
		for(int i=0;i<format.size();i++)
		{
			this.addTierF(i, format.get(i));
		}
	}
	
	private void addTierF(int listPos, Tier tier)
	{
		if(listPos<format.size())
		{
			System.out.println("Incorrect listPos, tier list is "+format.size());
		}
		else
		{
			format.add(listPos,tier);
		}
	}
	
	//Returns the root node if the node to be added doesn't work.
	public Node addNode(Node parent,String statement)
	{
		if(format.size()<=parent.getDepth()+1)
		{
			System.out.println("No tier to correspond to new node.");
			return root;
		}
		if(parent.getDepth()==-1)
		{
			if(format.size()==0)
			{
				System.out.println("No tier to correspond to new node.");
				return root;
			}
			if(format.get(0).getMax()>root.getChildren().size())
			{
				Node n=parent.addChild(new Node(statement,parent)); //HERE DUDE
				if(n==null)
				{
					System.out.println("Adding child "+statement+" to "+parent.getStatement()+" didn't work.");
					return root;
				}
				return n;
			}
			System.out.println("Parent has max child nodes already."+parent.getDepth());
			return root;
		}
		if(parent.getChildren().size()>=format.get(parent.getDepth()).getMax())
		{
			System.out.println("Parent has max child nodes already.");
			return root;
		}
		Node n=parent.addChild(new Node(statement,parent));
		if(n==null)
		{
			return root;
		}
		return n;
	}
	
	//encode's the current bp into xml
	public void encodeXML(String filename)
	{
		XMLEncoder encoder=null;
		try
		{
			encoder=new XMLEncoder(new BufferedOutputStream(new FileOutputStream(filename)));
		}
		catch(FileNotFoundException fileNotFound)
		{
			System.out.println("ERROR: While Creating or Opening the File dvd.xml");
		}
		encoder.writeObject(this);
		encoder.close();
	}
	
	//returns the bp in the xml file
	private BusinessPlan decodeXML(String filename)
	{
		XMLDecoder decoder=null;
		try
		{
			decoder=new XMLDecoder(new BufferedInputStream(new FileInputStream(filename)));
		}
		catch(FileNotFoundException fileNotFound)
		{
			System.out.println("ERROR: File not found");
		}
		BusinessPlan temp=(BusinessPlan) decoder.readObject();
		decoder.close();
		return temp;
	}

}
