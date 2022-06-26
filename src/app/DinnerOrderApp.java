package app;

import auxiliary.Dish;
import auxiliary.Voter;
import pattern.DinnerOrderSelectionStrategy;
import pattern.DinnerOrderStatisticsStrategy;
import poll.NonLegalVotesException;
import poll.Poll;
import vote.RealNameVote;
import vote.VoteItem;
import vote.VoteType;

import java.util.*;

public class DinnerOrderApp {

    public static void main(String[] args) throws Exception {
        Voter Fa = new Voter("Father");
        Voter Mo = new Voter("Mother");
        Voter Gr = new Voter("Grandfather");
        Voter So = new Voter("Son");
        // 设定4个投票人的权重
        Map<Voter, Double> weightedVoters = new HashMap<>();
        weightedVoters.put(Fa, 1.0);
        weightedVoters.put(Mo, 2.0);
        weightedVoters.put(Gr, 4.0);
        weightedVoters.put(So, 2.0);
        // 设定投票类型
        Map<String, Integer> types = new HashMap<>();
        types.put("Like", 2);
        types.put("Unlike", 0);
        types.put("Indifferent", 1);
        VoteType vt = new VoteType(types);

        // 创建候选对象：候选人
        Dish p1 = new Dish("hotpot", 100);
        Dish p2 = new Dish("barbecue", 150);
        Dish p3 = new Dish("sashimi", 200);
        // 创建投票项，前三个是投票人vr1对三个候选对象的投票项，后三个是vr2的投票项
        VoteItem<Dish> vi11 = new VoteItem<>(p1, "Like");
        VoteItem<Dish> vi12 = new VoteItem<>(p2, "Like");
        VoteItem<Dish> vi13 = new VoteItem<>(p3, "Unlike");

        VoteItem<Dish> vi21 = new VoteItem<>(p1, "Indifferent");
        VoteItem<Dish> vi22 = new VoteItem<>(p2, "Like");
        VoteItem<Dish> vi23 = new VoteItem<>(p3, "Like");

        VoteItem<Dish> vi31 = new VoteItem<>(p1, "Unlike");
        VoteItem<Dish> vi32 = new VoteItem<>(p2, "Like");
        VoteItem<Dish> vi33 = new VoteItem<>(p3, "Like");

        VoteItem<Dish> vi41 = new VoteItem<>(p1, "Like");
        VoteItem<Dish> vi42 = new VoteItem<>(p2, "Indifferent");
        VoteItem<Dish> vi43 = new VoteItem<>(p3, "Indifferent");

        List<Dish> candidates=new ArrayList<>();
        candidates.add(p1);
        candidates.add(p2);
        candidates.add(p3);
        // 以下一段为自行添加
        Set<VoteItem<Dish>> Father=new HashSet<>();
        Father.add(vi11);
        Father.add(vi12);
        Father.add(vi13);
        Set<VoteItem<Dish>> Mother=new HashSet<>();
        Mother.add(vi21);
        Mother.add(vi22);
        Mother.add(vi23);
        Set<VoteItem<Dish>> Grandfather=new HashSet<>();
        Grandfather.add(vi31);
        Grandfather.add(vi32);
        Grandfather.add(vi33);
        Set<VoteItem<Dish>> Son=new HashSet<>();
        Son.add(vi41);
        Son.add(vi42);
        Son.add(vi43);
        // 创建4个投票人vr1、vr2的选票
        RealNameVote<Dish> f = new RealNameVote<>(Fa, Father);
        RealNameVote<Dish> m = new RealNameVote<>(Mo, Mother);
        RealNameVote<Dish> g = new RealNameVote<>(Gr, Grandfather);
        RealNameVote<Dish> s = new RealNameVote<>(So, Son);
        // 创建投票活动
        System.out.println("创建投票活动");
        Poll<Dish> poll = Poll.create("DinnerOrder");
        // 设定投票基本信息：名称、日期、投票类型、选出的数量
        String name= "DinnerOrder";
        Calendar date=Calendar.getInstance();
        poll.addCandidates(candidates);
        int num=2;// 设定选出的菜品数量，这里暂定为2
        poll.setInfo(name,date,vt,num);
        // 增加投票人及其权重
        poll.addVoters(weightedVoters);
        // 增加三个投票人的选票
        poll.addVote(f);
        poll.addVote(m);
        poll.addVote(g);
        poll.addVote(s);
        System.out.println("准备计票");
//		// 按规则计票
        try{
            poll.statistics(new DinnerOrderStatisticsStrategy());
            System.out.println("计票成功");
        }catch (NonLegalVotesException e){
            System.out.println("，重新投票");
        }
        System.out.println("计票完成，准备遴选");
//		// 按规则遴选
        poll.selection(new DinnerOrderSelectionStrategy());
        System.out.println("遴选完成");
//		// 输出遴选结果
        System.out.println(poll.result());
    }
}