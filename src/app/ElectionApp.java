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

		// ����2��ͶƱ��
		Voter vr1 = new Voter("v1");
		Voter vr2 = new Voter("v2");

		// �趨2��ͶƱ�˵�Ȩ��
		Map<Voter, Double> weightedVoters = new HashMap<>();
		weightedVoters.put(vr1, 1.0);
		weightedVoters.put(vr2, 1.0);

		// �趨ͶƱ����
		Map<String, Integer> types = new HashMap<>();
		types.put("Support", 1);
		types.put("Oppose", -1);
		types.put("Waive", 0);
		VoteType vt = new VoteType(types);

		// ������ѡ���󣺺�ѡ��
		Person p1 = new Person("ABC", 19);
		Person p2 = new Person("DEF", 20);
		Person p3 = new Person("GHI", 21);
		// ����ͶƱ�ǰ������ͶƱ��vr1��������ѡ�����ͶƱ���������vr2��ͶƱ��
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
		// ����һ��Ϊ�������
		Set<VoteItem<Person>> v1=new HashSet<>();
		v1.add(vi11);
		v1.add(vi12);
		v1.add(vi13);
		Set<VoteItem<Person>> v2=new HashSet<>();
		v2.add(vi21);
		v2.add(vi22);
		v2.add(vi23);
		// ����2��ͶƱ��vr1��vr2��ѡƱ
		Vote<Person> rv1 = new Vote<Person>(v1);
		Vote<Person> rv2 = new Vote<Person>(v2);
		// ����ͶƱ�
		System.out.println("����ͶƱ�");
		Poll<Person> poll = Poll.create("Election");
		// �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
		String name= "Election";
		Calendar date=Calendar.getInstance();
		poll.addCandidates(candidates);
		int num=1;// �趨ѡ���������������ݶ�Ϊ1
		poll.setInfo(name,date,vt,num);
		// ����ͶƱ�˼���Ȩ��
		poll.addVoters(weightedVoters);
		// ��������ͶƱ�˵�ѡƱ
		poll.addVote(rv1);
		poll.addVote(rv2);
		System.out.println("׼����Ʊ");
//		// �������Ʊ
		try{
			poll.statistics(new ElectionStatisticsStrategy());
			System.out.println("��Ʊ�ɹ�");
		}catch (NonLegalVotesException e){
			System.out.println("������ѡ��");
		}
		System.out.println("��Ʊ��ɣ�׼����ѡ");
//		// ��������ѡ
		poll.selection(new ElectionSelectionStrategy());
		System.out.println("��ѡ���");
//		// �����ѡ���
		System.out.println(p1+","+p2+","+p3);
		System.out.println(poll.result());
	}
}
