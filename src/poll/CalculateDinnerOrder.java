package poll;

import vote.Vote;

public abstract  class CalculateDinnerOrder<Dish> implements visitor<Dish>{
    @Override
    public Double visit(DinnerOrder<Dish> gpl) {
        Double legal=0.0;
        Double all= Double.valueOf(gpl.votes.size());
        for(Vote<Dish> v: gpl.votes){
            if(v.getVoteItems().size()==gpl.statistics.size()){
                legal++;
            }
        }
        return legal/all;
    }
}
