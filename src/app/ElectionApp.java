package app;

import java.util.*;

import auxiliary.Person;
import auxiliary.Voter;
import pattern.ElectionSelectionStrategy;
import pattern.ElectionStatisticsStrategy;
import poll.GeneralPollImpl;
import poll.NonLegalVotesException;
import poll.Poll;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

public class ElectionApp extends GeneralPollImpl {

	public static void main(String[] args) throws NonLegalVotesException {

		// 创建2个投票人
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");

		// 设定2个投票人的权重
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 1.0);
		weightedVoters.put(vr2, 1.0);

		// 设定投票类型
		Map<String, Integer> types = new HashMap<>();
		types.put("Support", 1);
		types.put("Oppose", -1);
		types.put("Waive", 0);
		VoteType vt = new VoteType(types);

		// 创建候选对象：候选人
		Person p1 = new Person("ABC", 19);
		Person p2 = new Person("DEF", 20);
		Person p3 = new Person("GHI", 21);
		// 创建投票项，前三个是投票人vr1对三个候选对象的投票项，后三个是vr2的投票项
		VoteItem<Person> vi11 = new VoteItem<>(p1, "Support");
		VoteItem<Person> vi12 = new VoteItem<>(p2, "Oppose");
		VoteItem<Person> vi13 = new VoteItem<>(p3, "Support");
		VoteItem<Person> vi21 = new VoteItem<>(p1, "Oppose");
		VoteItem<Person> vi22 = new VoteItem<>(p2, "Waive");
		VoteItem<Person> vi23 = new VoteItem<>(p3, "Waive");
		List<Person> candidates=new ArrayList<>();
		candidates.add(p1);
		candidates.add(p2);
		candidates.add(p3);
		// 以下一段为自行添加
		Set<VoteItem<Person>> v1=new HashSet<>();
		v1.add(vi11);
		v1.add(vi12);
		v1.add(vi13);
		Set<VoteItem<Person>> v2=new HashSet<>();
		v2.add(vi21);
		v2.add(vi22);
		v2.add(vi23);
		// 创建2个投票人vr1、vr2的选票
		Vote<Person> rv1 = new Vote<Person>(v1);
		Vote<Person> rv2 = new Vote<Person>(v2);
		// 创建投票活动
		System.out.println("创建投票活动");
		Poll<Person> poll = Poll.create("Election");
		// 设定投票基本信息：名称、日期、投票类型、选出的数量
		String name= "Election";
		Calendar date=Calendar.getInstance();
		poll.addCandidates(candidates);
		int num=1;// 设定选出的数量，这里暂定为1
		poll.setInfo(name,date,vt,num);
		// 增加投票人及其权重
		poll.addVoters(weightedVoters);
		// 增加三个投票人的选票
		poll.addVote(rv1);
		poll.addVote(rv2);
		System.out.println("准备计票");
//		// 按规则计票
		try{
			poll.statistics(new ElectionStatisticsStrategy());
			System.out.println("计票成功");
		}catch (NonLegalVotesException e){
			System.out.println("，重新选举");
		}
		System.out.println("计票完成，准备遴选");
//		// 按规则遴选
		poll.selection(new ElectionSelectionStrategy());
		System.out.println("遴选完成");
//		// 输出遴选结果
		System.out.println(p1+","+p2+","+p3);
		System.out.println(poll.result());
	}
}
