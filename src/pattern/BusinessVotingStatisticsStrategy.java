package pattern;

import auxiliary.Voter;
import vote.RealNameVote;
import vote.VoteItem;
import vote.VoteType;

import java.util.*;

public class BusinessVotingStatisticsStrategy<C> implements StatisticsStrategy<C, RealNameVote<C>> {
    @Override
    public Map<C, Double> calculate(Map<Voter, Double> voters, Set<RealNameVote<C>> votes) {
        Map<C, Double> score = new HashMap<>();
        List<RealNameVote<C>> list = new ArrayList<>(votes);
        Double sum=0.0;
        Map<String, Integer> types = new HashMap<>();
        types.put("Support", 1);
        types.put("Oppose", -1);
        types.put("Waive", 0);
        VoteType voteType = new VoteType(types);
        for (RealNameVote<C> vc : votes) {
            for (VoteItem<C> vi : vc.getVoteItems()) {
                if(voteType.getScoreByOption(vi.getVoteValue())==1.0){
                    sum+= voters.get(vc.getVoter()) * voteType.getScoreByOption(vi.getVoteValue());
                }
            }
        }
        RealNameVote<C> vc = list.get(0);
        List<VoteItem<C>> vi=new ArrayList<>(vc.getVoteItems());
        System.out.println(vi.get(0).getCandidate()+"sum:"+sum);
        score.put(vi.get(0).getCandidate(), sum);
        return score;
    }
}
