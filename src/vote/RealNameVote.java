package vote;

import auxiliary.Voter;

import java.util.Set;

//immutable
public class RealNameVote<C> extends Vote<C>{

	//Õ∂∆±»À
	private Voter voter;

	public RealNameVote(Voter v, Set<VoteItem<C>> vote) {
		super(vote);
        this.voter=v;
	}

	public Voter getVoter() {
		return this.voter;
	}
	@Override
	public int hashCode(){
		int hash=0;
		for(VoteItem<C> v:super.getVoteItems()){
			hash+=v.hashCode();
		}
		return voter.hashCode()+hash;
	}
}
