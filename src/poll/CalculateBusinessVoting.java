package poll;

import vote.Vote;

public abstract class CalculateBusinessVoting<Proposal> implements visitor<Proposal>{
    @Override
    public Double visit(BusinessVoting<Proposal> gpl) {
        Double legal=0.0;
        Double all= Double.valueOf(gpl.votes.size());
        for(Vote<Proposal> v: gpl.votes){
            if(v.getVoteItems().size()==1){
                legal++;
            }
        }
        return legal/all;
    }
}
