package poll;

import vote.Vote;

public abstract class CalculateElection<Person> implements visitor<Person>{
    @Override
    public Double visit(DinnerOrder<Person> gpl) {
        Double legal=0.0;
        Double all= Double.valueOf(gpl.votes.size());
        for(Vote<Person> v: gpl.votes){
            if(v.getVoteItems().size()==gpl.statistics.size()){
                legal++;
            }
        }
        return legal/all;
    }
}
