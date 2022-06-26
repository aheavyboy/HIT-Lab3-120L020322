package vote;

import auxiliary.Person;

import java.util.Date;
import java.util.Random;

//immutable
public class VoteItem<C> {

	// ��ͶƱ������Եĺ�ѡ����
	private C candidate;
	// �Ժ�ѡ����ľ���ͶƱѡ����硰֧�֡��������ԡ���
	// ���豣�������ֵ����Ҫʱ���Դ�ͶƱ���VoteType�����в�ѯ�õ�
	private String value;


	// Rep Invariants
	// candidate means the person to be chosen
	// value means one of the vote choices
	// Abstract Function
	// AF(values)=['Support','Oppose','waive']
	// Safety from Rep Exposure
	// the values are string ,so they are immutable
	// the candidate is general,so make defensive copies to avoid sharing the rep exposure

	private boolean checkRep() {
		if (candidate==null) return false;
		if (value==null) return false;
		return true;
	}

	/**
	 * ����һ��ͶƱ����� ���磺��Ժ�ѡ������������ͶƱѡ���ǡ�֧�֡�
	 * 
	 * @param candidate ����Եĺ�ѡ����
	 * @param value     ��������ͶƱѡ��
	 */
	public VoteItem(C candidate, String value) {
		this.candidate = candidate;
		this.value = value;
	}

	/**
	 * �õ���ͶƱѡ��ľ���ͶƱ��
	 * 
	 * @return
	 */
	public String getVoteValue() {
		return this.value;
	}
	/**
	 * �õ���ͶƱѡ������Ӧ�ĺ�ѡ��
	 * 
	 * @return
	 */
	public C getCandidate() {
		return this.candidate;
	}
	@Override
	public int hashCode() {
		assert checkRep();
		return candidate.hashCode()+value.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		assert checkRep();
		return candidate.hashCode()+value.hashCode()==obj.hashCode();
	}
}
