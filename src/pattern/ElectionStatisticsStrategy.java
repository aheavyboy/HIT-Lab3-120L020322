package pattern;

import auxiliary.Voter;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

import java.util.*;

public class ElectionStatisticsStrategy<C> implements StatisticsStrategy<C, Vote<C>> {
    @Override
    public Map<C, Double> calculate(Map<Voter, Double> voters, Set<Vote<C>> votes) {
        Map<C, Double> score = new HashMap<>();
        List<Vote<C>> list = new ArrayList<>(votes);
        Double[] sum = new Double[list.get(0).getVoteItems().size()];
        for(int i=0;i<list.get(0).getVoteItems().size();i++){
            sum[i]=0.0;
        }
        Map<String, Integer> types = new HashMap<>();
        types.put("Support", 1);
        types.put("Oppose", -1);
        types.put("Waive", 0);
        VoteType voteType = new VoteType(types);
        int i = 0;
        for (Vote<C> vc : votes) {
            for (VoteItem<C> vi : vc.getVoteItems()) {
                sum[i] +=voteType.getScoreByOption(vi.getVoteValue());
                i++;
            }
            i=0;
        }
        Vote<C> vc = list.get(0);
        List<VoteItem<C>> vi=new ArrayList<>(vc.getVoteItems());
        for (i = 0;i < list.get(0).getVoteItems().size(); i++) {
            System.out.println(vi.get(i).getCandidate()+"sum:"+sum[list.get(0).getVoteItems().size()-1-i]);
            score.put(vi.get(i).getCandidate(), sum[list.get(0).getVoteItems().size()-1-i]);
        }
        System.out.println(score);
        return score;
    }
}
