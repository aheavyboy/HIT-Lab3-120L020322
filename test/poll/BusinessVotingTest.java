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
        // ����2��ͶƱ��
        Voter vr1 = new Voter("v1");
        Voter vr2 = new Voter("v2");
        Voter vr3 = new Voter("v3");
        // �趨2��ͶƱ�˵�Ȩ��
        Map<Voter, Double> weightedVoters = new HashMap<>();
        weightedVoters.put(vr1, 0.35);
        weightedVoters.put(vr2, 0.25);
        weightedVoters.put(vr3, 0.4);
        // �趨ͶƱ����
        Map<String, Integer> types = new HashMap<>();
        types.put("Support", 1);
        types.put("Oppose", -1);
        types.put("Waive", 0);
        VoteType vt = new VoteType(types);

        // ������ѡ���󣺺�ѡ��
        Calendar date=Calendar.getInstance();
        Proposal p1 = new Proposal("timi", date);
        // ����ͶƱ�ǰ������ͶƱ��vr1��������ѡ�����ͶƱ���������vr2��ͶƱ��
        VoteItem<Proposal> vi11 = new VoteItem<>(p1, "Oppose");
        VoteItem<Proposal> vi21 = new VoteItem<>(p1, "Support");
        VoteItem<Proposal> vi31 = new VoteItem<>(p1, "Support");
        List<Proposal> candidates=new ArrayList<>();
        candidates.add(p1);
        // ����һ��Ϊ�������
        Set<VoteItem<Proposal>> v1=new HashSet<>();
        v1.add(vi11);
        Set<VoteItem<Proposal>> v2=new HashSet<>();
        v2.add(vi21);
        Set<VoteItem<Proposal>> v3=new HashSet<>();
        v3.add(vi31);
        // ����2��ͶƱ��vr1��vr2��ѡƱ
        RealNameVote<Proposal> rv1 = new RealNameVote<Proposal>(vr1,v1);
        RealNameVote<Proposal> rv2 = new RealNameVote<Proposal>(vr2,v2);
        RealNameVote<Proposal> rv3 = new RealNameVote<Proposal>(vr3,v3);
        // ����ͶƱ�
        System.out.println("����ͶƱ�");
        Poll<Proposal> poll = Poll.create("BusinessVoting");
        // �趨ͶƱ������Ϣ�����ơ����ڡ�ͶƱ���͡�ѡ��������
        String name= "BusinessVoting";
        poll.addCandidates(candidates);
        int num=1;// �趨ѡ��������������Ϊ1
        poll.setInfo(name,date,vt,num);
        // ����ͶƱ�˼���Ȩ��
        poll.addVoters(weightedVoters);
        // ��������ͶƱ�˵�ѡƱ
        poll.addVote(rv1);
        poll.addVote(rv2);
        poll.addVote(rv3);
        System.out.println("׼����Ʊ");
//		// �������Ʊ
        try{
            poll.statistics(new BusinessVotingStatisticsStrategy());
            System.out.println("��Ʊ�ɹ�");
        }catch (NonLegalVotesException e){
            System.out.println("������ѡ��");
        }
        System.out.println("��Ʊ��ɣ�׼����ѡ");
//		// ��������ѡ
        poll.selection(new BusinessVotingSelectionStrategy());
        System.out.println("��ѡ���");
//		// �����ѡ���
        System.out.println(p1);
        assertEquals("timi���ߵ�֧����Ϊ��[0.65]\n",poll.result());
    }

}
