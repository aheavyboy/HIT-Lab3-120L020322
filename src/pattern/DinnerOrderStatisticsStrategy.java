package pattern;

import auxiliary.Voter;
import vote.RealNameVote;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

import java.util.*;

public class DinnerOrderStatisticsStrategy<C> implements StatisticsStrategy<C, RealNameVote<C>> {
    @Override
    public Map<C, Double> calculate(Map<Voter, Double> voters, Set<RealNameVote<C>> votes) {
        Map<C, Double> score = new HashMap<>();
        List<Vote<C>> list = new ArrayList<>(votes);
        Double[] sum = new Double[list.get(0).getVoteItems().size()];
        for(int i=0;i<list.get(0).getVoteItems().size();i++){
            sum[i]=0.0;
        }
        Map<String, Integer> types = new HashMap<>();
        types.put("Like", 2);
        types.put("Indifferent", 1);
        types.put("Unlike", 0);
        VoteType voteType = new VoteType(types);
        int i = 0;
        for (RealNameVote<C> vc : votes) {
            for (VoteItem<C> vi : vc.getVoteItems()) {
                sum[i] += voters.get(vc.getVoter()) * voteType.getScoreByOption(vi.getVoteValue());
                i++;
            }
            i=0;
        }
        Vote<C> vc = list.get(0);
        List<VoteItem<C>> vi=new ArrayList<>(vc.getVoteItems());
        for (i = 0;i < list.get(0).getVoteItems().size(); i++) {
            score.put(vi.get(i).getCandidate(), sum[list.get(0).getVoteItems().size()-1-i]);
        }
        System.out.println(score);
        return score;
    }
}