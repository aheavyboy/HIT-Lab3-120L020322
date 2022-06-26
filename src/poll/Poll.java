package poll;

import java.util.*;

import auxiliary.Voter;
import pattern.SelectionStrategy;
import pattern.StatisticsStrategy;
import vote.Vote;
import vote.VoteType;

public interface Poll<C> {
	/**
	 * ����ͶƱ�������δ�趨�κ����ԣ�Ҳδ���κ�ͶƱ����
	 * @return һ��Poll<C>����
	 */
	public static <C> Poll<C> create(String s) {
		if(s.equals("Election")){
			return new Election<>();
		}else if(s.equals("DinnerOrder")){
			return new DinnerOrder<>();
		}else if(s.equals("BusinessVoting")){
			return new BusinessVoting<>();
		}else return new GeneralPollImpl<>();
	}
	public String getName();
	public List<C> getCandidates();
	public Map<Voter,Double> getVoters();
	public int getQuantity();
	public VoteType getVoteType();
	/**
	 * �趨ͶƱ��Ļ�������
	 * @param name     ͶƱ�������
	 * @param date     ͶƱ��������
	 * @param type     ͶƱ���ͣ�������ͶƱѡ���Լ���Ӧ�ķ���
	 * @param quantity ��ѡ��������
	 */
	public void setInfo(String name, Calendar date, VoteType type, int quantity);
	/**
	 * �趨��ѡ��
	 * @param candidates ��ѡ���嵥
	 */
	public void addCandidates(List<C> candidates);

	/**
	 * �趨ͶƱ�˼���Ȩ��
	 * 
	 * @param voters KeyΪͶƱ�ˣ�ValueΪͶƱ�˵�Ȩ��
	 */
	public void addVoters(Map<Voter, Double> voters);

	/**
	 * ����һ��ͶƱ�˶�ȫ���ѡ�����ͶƱ
	 * 
	 * @param vote һ��ͶƱ�˶�ȫ���ѡ�����ͶƱ��¼����
	 */
	public void addVote(Vote<C> vote) throws NonLegalVotesException;
	public Set<Vote<C>> getVotes();
	/**
	 * �������Ʊ
	 * 
	 * @param ss ����ȡ�ļ�Ʊ���������
	 */
	public void statistics(StatisticsStrategy ss) throws NonLegalVotesException;
	/**
	 * ��������ѡ
	 * 
	 * @param ss ����ȡ����ѡ���������
	 */
	public void selection(SelectionStrategy ss);
	/**
	 * �����ѡ���
	 * 
	 * @return һ����ʾ��ѡ������ַ�����ÿ�а�������ѡ����ID������
	 */
	public String result();
}
