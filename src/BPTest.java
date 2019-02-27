import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class BPTest
{
	@Test
	public void tierTest()
	{
		Tier t1=new Tier("first");
		assertEquals(t1.getMax(),0);
		assertEquals(t1.getMin(),0);
		assertEquals(t1.getRowName(),"first");
		t1.setMax(5);
		t1.setMin(3);
		assertEquals(t1.getMax(),5);
		assertEquals(t1.getMin(),3);
		
		//these should be illegal
		t1.setMax(1);
		t1.setMin(6);
		assertEquals(t1.getMax(),5);
		assertEquals(t1.getMin(),3);
		t1.setMin(-1);
		assertEquals(t1.getMin(),3);
		
		t1.setMin(4);
		assertEquals(t1.getMin(),4);
		t1.setMin(1);
		t1.setMax(2);
		assertEquals(t1.getMax(),2);
	}
	
	//test will succeed if the content of the arrays equal
	private void assertListEquals(ArrayList one,ArrayList two)
	{
		assertEquals(one.size(),two.size());
		for(int i=0;i<one.size();i++)
		{
			assertEquals(one.get(i),two.get(i));
		}
	}
	@Test
	public void NodeTest()
	{
		Node n1=new Node("first");
		assertListEquals(n1.getChildren(),new ArrayList<Node>());
		assertEquals(n1.getStatement(),"first");
		n1.setStatement("still first");
		assertEquals(n1.getStatement(),"still first");
		
		//materials to work with for the actual testing
		Node c1=n1.addChild(new Node("c1",n1));
		Node c2=n1.addChild(new Node("c2",n1));
		Node c3=n1.addChild(new Node("c3",n1));
		Node c4=n1.addChild(new Node("c4",n1));
		Node c5=n1.addChild(new Node("c5",n1));
		Node c6=new Node("c6");
		Node c7=new Node("c7");
		ArrayList<Node> children=new ArrayList<Node>();
		children.add(c1);
		children.add(c2);
		children.add(c3);
		children.add(c4);
		children.add(c5);
		assertListEquals(n1.getChildren(),children);
		
		//make sure it goes through the line alright
		c1.addChild(c6);
		c6.addChild(c7);
		assertEquals(n1.getChildren().get(0).getChildren().get(0).getChildren().get(0),c7);
		
		//no circles, thanks
		c7.addChild(n1);
		assertTrue(c7.getChildren().isEmpty());
		c1.setParent(c7);
		assertEquals(c1.getParent(),n1);
	}
	@Test
	public void bPTest()
	{
		BusinessPlan bP1=new BusinessPlan();
		assertListEquals(bP1.getFormat(),new ArrayList<Tier>());
		assertEquals(bP1.getRoot().getStatement(),"");
		assertEquals(bP1.getRoot().getParent(),null);
		ArrayList<Tier> format=new ArrayList<Tier>();
		format.add(new Tier("Vision",1,0));
		format.add(new Tier("Mission",2,0));
		format.add(new Tier("Action",1,0));
		format.add(new Tier("Objective",0,0));
		bP1.setFormat(format);
		assertListEquals(bP1.getFormat(),format);
		Node root=bP1.getRoot();
		Node d0=bP1.addNode(root,"d0");
		Node d1=bP1.addNode(d0,"d1");
		Node d2=bP1.addNode(d1,"d2");
		Node d3=bP1.addNode(d2,"d3");
		assertEquals(d3,root.getChildren().get(0).getChildren().
			get(0).getChildren().get(0).getChildren().get(0));
		assertEquals(bP1.getRoot(),bP1.addNode(d3, "should be too deep"));
		BusinessPlan bP2=new BusinessPlan();
		format=new ArrayList<Tier>();
		format.add(new Tier("Vision",3,0));
		bP2.setFormat(format);
		root=bP2.getRoot();
		d0=bP2.addNode(root, "d0-0");
		d1=bP2.addNode(d0, "should be too deep");
		assertEquals(root,d1);
		Node d01=bP2.addNode(root,"d0-1");
		Node d02=bP2.addNode(root,"d0-2");
		Node d03=bP2.addNode(root, "d0-3");
		assertEquals(d03,root);//Can only be three vision statements.
		assertEquals(d02,root.getChildren().get(2));
	}
	@Test
	public void twoTemplatesTest()
	{
		BusinessPlan vmosa=new VMOSABPlan();
		assertEquals(vmosa.getFormat().get(0).getRowName(),"Vision");
		assertEquals(vmosa.getFormat().get(1).getRowName(),"Mission");
		assertEquals(vmosa.getFormat().get(2).getRowName(),"Objective");
		assertEquals(vmosa.getFormat().get(3).getRowName(),"Strategy");
		assertEquals(vmosa.getFormat().get(4).getRowName(),"Action Plan");
		BusinessPlan okrs=new OKRSBPlan();
		assertEquals(okrs.getFormat().get(0).getRowName(),"Vision");
		assertEquals(okrs.getFormat().get(1).getRowName(),"Mission");
		assertEquals(okrs.getFormat().get(2).getRowName(),"Long-Term Goals");
		assertEquals(okrs.getFormat().get(3).getRowName(),"Short-Term Goals");
		assertEquals(okrs.getFormat().get(4).getRowName(),"Objectives and Key Results");
	}

}
