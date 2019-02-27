public class OKRSBPlan extends BusinessPlan
{
	public OKRSBPlan()
	{
		super();
		Tier a=new Tier("Vision");
		Tier b=new Tier("Mission");
		Tier c=new Tier("Long-Term Goals");
		Tier d=new Tier("Short-Term Goals");
		Tier e=new Tier("Objectives and Key Results");
		Tier[] formatArray=new Tier[] {a,b,c,d,e};
		for(Tier t:formatArray)
		{
			format.add(t);
		}
	}
}
