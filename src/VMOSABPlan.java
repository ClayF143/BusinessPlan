
public class VMOSABPlan extends BusinessPlan
{
	public VMOSABPlan()
	{
		super();
		Tier a=new Tier("Vision");
		Tier b=new Tier("Mission");
		Tier c=new Tier("Objective");
		Tier d=new Tier("Strategy");
		Tier e=new Tier("Action Plan");
		Tier[] formatArray=new Tier[] {a,b,c,d,e};
		for(Tier t:formatArray)
		{
			format.add(t);
		}
	}
}
