import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

public class Node implements Serializable
{
	String statement;
	ArrayList<Node> children;
	Node parent;

	public Node(String statement,Node parent)
	{
		this.statement = statement;
		children = new ArrayList<Node>();
		this.parent=parent;
	}
	public Node(String statement)
	{
		this.statement=statement;
		children=new ArrayList<Node>();
		this.parent=null;
	}
	public Node()
	{
		this.statement="";
		children = new ArrayList<Node>();
		this.parent=null;
	}

	public String getStatement()
	{
		return statement;
	}

	public void setStatement(String statement)
	{
		this.statement = statement;
	}
	
	public Node getParent()
	{
		return parent;
	}

	public void setParent(Node parent)
	{
		//no circles thanks 
		if(parent==null)
		{
			System.out.println("Something probably went wrong here.");
			return;
		}
		if(!dfsF(this,parent,new Stack<Node>()))
		{
			if(!parent.getChildren().get(parent.getChildren().size()-1).equals(this))
			{
				parent.getChildren().add(this);
			}
			this.parent = parent;
		}
		else
		{
			System.out.println("You're making one of this node's children a parent: "+parent.getStatement());
			System.out.println("Location: setParent()");
		}
	}

	public ArrayList<Node> getChildren()
	{
		return children;
	}

	public void setChildren(ArrayList<Node> children)
	{
		for(Node child:children)
		{
			this.children.add(child);
		}
	}

	public Node addChild(Node child)
	{
		//Cough, replacing a parent fyi
		for(Node c:children)
			{
			if(!(c.getParent()==null)&&!c.getParent().equals(this))
			{
				System.out.println("Replacing "+c.getStatement()+"'s parent,"+
						c.getParent().getStatement());
			}
		}
		
		//No circles thanks.
		
		if(parent==null)
		{
			children.add(child);
			child.setParent(this);
			return child;
		}
		Node movingParent=parent;
		while(!movingParent.equals(getRootF()))
		{
			if(movingParent.equals(child))
			{
				System.out.println("You're making one of this node's parents its child:"+child.getStatement()+", "+parent.getStatement());
				return getRootF();
			}
			movingParent=movingParent.getParent();
		}
		if(child==getRootF())
		{
			return getRootF();
		}
		children.add(child);
		child.setParent(this);
		return child;
	}
	
	public int getDepth()
	{
		int r=-1;
		if(parent==null)
		{
			return r;
		}
		Node movingParent=parent;
		r++;
		while(!(movingParent.getParent()==null))
		{
			r++;
			movingParent=movingParent.getParent();
		}
		return r;
	}
	
	private Node getRootF()
	{
		Node n=this;
		while(n.getParent()!=null)
		{
			n=n.getParent();
		}
		return n;
	}
	
	private boolean dfsF(Node start,Node lookingFor,Stack<Node> yetToBeChecked)
	{
		if(start.equals(lookingFor))
		{
			return true;
		}
		for(Node child:start.getChildren())
		{
			yetToBeChecked.push(child);
		}
		if(yetToBeChecked.isEmpty())
		{
			return false;
		}
		Node newStart=yetToBeChecked.pop();
		return dfsF(newStart,lookingFor,yetToBeChecked);
	}

}
