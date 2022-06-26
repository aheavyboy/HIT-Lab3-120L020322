package poll;

import static org.junit.jupiter.api.Assertions.*;

import auxiliary.Proposal;
import auxiliary.Voter;
import org.junit.jupiter.api.Test;
import pattern.BusinessVotingSelectionStrategy;
import pattern.BusinessVotingStatisticsStrategy;
import vote.RealNameVote;
import vote.VoteItem;
import vote.VoteType;

import java.util.*;

class BusinessVotingTest {

    // test strategy
    // TODO

    @Test
    void test() throws NonLegalVotesException {
        // 创建2个投票人
        Voter vr1 = new Voter("v1");
        Voter vr2 = new Voter("v2");
        Voter vr3 = new Voter("v3");
        // 设定2个投票人的权重
        Map<Voter, Double> weightedVoters = new HashMap<>();
        weightedVoters.put(vr1, 0.35);
        weightedVoters.put(vr2, 0.25);
        weightedVoters.put(vr3, 0.4);
        // 设定投票类型
        Map<String, Integer> types = new HashMap<>();
        types.put("Support", 1);
        types.put("Oppose", -1);
        types.put("Waive", 0);
        VoteType vt = new VoteType(types);

        // 创建候选对象：候选人
        Calendar date=Calendar.getInstance();
        Proposal p1 = new Proposal("timi", date);
        // 创建投票项，前三个是投票人vr1对三个候选对象的投票项，后三个是vr2的投票项
        VoteItem<Proposal> vi11 = new VoteItem<>(p1, "Oppose");
        VoteItem<Proposal> vi21 = new VoteItem<>(p1, "Support");
        VoteItem<Proposal> vi31 = new VoteItem<>(p1, "Support");
        List<Proposal> candidates=new ArrayList<>();
        candidates.add(p1);
        // 以下一段为自行添加
        Set<VoteItem<Proposal>> v1=new HashSet<>();
        v1.add(vi11);
        Set<VoteItem<Proposal>> v2=new HashSet<>();
        v2.add(vi21);
        Set<VoteItem<Proposal>> v3=new HashSet<>();
        v3.add(vi31);
        // 创建2个投票人vr1、vr2的选票
        RealNameVote<Proposal> rv1 = new RealNameVote<Proposal>(vr1,v1);
        RealNameVote<Proposal> rv2 = new RealNameVote<Proposal>(vr2,v2);
        RealNameVote<Proposal> rv3 = new RealNameVote<Proposal>(vr3,v3);
        // 创建投票活动
        System.out.println("创建投票活动");
        Poll<Proposal> poll = Poll.create("BusinessVoting");
        // 设定投票基本信息：名称、日期、投票类型、选出的数量
        String name= "BusinessVoting";
        poll.addCandidates(candidates);
        int num=1;// 设定选出的数量，这里为1
        poll.setInfo(name,date,vt,num);
        // 增加投票人及其权重
        poll.addVoters(weightedVoters);
        // 增加三个投票人的选票
        poll.addVote(rv1);
        poll.addVote(rv2);
        poll.addVote(rv3);
        System.out.println("准备计票");
//		// 按规则计票
        try{
            poll.statistics(new BusinessVotingStatisticsStrategy());
            System.out.println("计票成功");
        }catch (NonLegalVotesException e){
            System.out.println("，重新选举");
        }
        System.out.println("计票完成，准备遴选");
//		// 按规则遴选
        poll.selection(new BusinessVotingSelectionStrategy());
        System.out.println("遴选完成");
//		// 输出遴选结果
        System.out.println(p1);
        assertEquals("timi政策的支持率为：[0.65]\n",poll.result());
    }

}
