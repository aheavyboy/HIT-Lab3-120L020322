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
        // �趨4��ͶƱ�˵�Ȩ��
        Map<Voter, Double> weightedVoters = new HashMap<>();
        weightedVoters.put(Fa, 1.0);
        weightedVoters.put(Mo, 2.0);
        weightedVoters.put(Gr, 4.0);
        weightedVoters.put(So, 2.0);
        // �趨ͶƱ����
        Map<String, Integer> types = new HashMap<>();
        types.put("Like", 2);
        types.put("Unlike", 0);
        types.put("Indifferent", 1);
        VoteType vt = new VoteType(types);

        // ������ѡ���󣺺�ѡ��
        Dish p1 = new Dish("hotpot", 100);
        Dish p2 = new Dish("barbecue", 150);
        Dish p3 = new Dish("sashimi", 200);
        // ����ͶƱ�ǰ������ͶƱ��vr1��������ѡ�����ͶƱ���������vr2��ͶƱ��
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
        // ����һ��Ϊ�������
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
        // ����4��ͶƱ��vr1��vr2��ѡƱ
        RealNameVote<Dish> f = new RealNameVote<>(Fa, Father);
        RealNameVote<Dish> m = new RealNameVote<>(Mo, Mother);
        RealNameVote<Dish> g = new RealNameVote<>(Gr, Grandfather);
        RealNameVote<Dish> s = new RealNameVote<>(So, Son);
        // ����ͶƱ�
        System.out.println("����ͶƱ�");
        Poll<Dish> poll = Poll.create("DinnerOrder");
        // �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
        String name= "DinnerOrder";
        Calendar date=Calendar.getInstance();
        poll.addCandidates(candidates);
        int num=2;// �趨ѡ���Ĳ�Ʒ�����������ݶ�Ϊ2
        poll.setInfo(name,date,vt,num);
        // ����ͶƱ�˼���Ȩ��
        poll.addVoters(weightedVoters);
        // ��������ͶƱ�˵�ѡƱ
        poll.addVote(f);
        poll.addVote(m);
        poll.addVote(g);
        poll.addVote(s);
        System.out.println("׼����Ʊ");
//		// �������Ʊ
        try{
            poll.statistics(new DinnerOrderStatisticsStrategy());
            System.out.println("��Ʊ�ɹ�");
        }catch (NonLegalVotesException e){
            System.out.println("������ͶƱ");
        }
        System.out.println("��Ʊ��ɣ�׼����ѡ");
//		// ��������ѡ
        poll.selection(new DinnerOrderSelectionStrategy());
        System.out.println("��ѡ���");
//		// �����ѡ���
        System.out.println(poll.result());
    }
}