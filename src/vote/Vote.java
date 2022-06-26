package vote;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

//immutable
public class Vote<C> {

	// ȱʡΪ��������ͶƱ��������Ҫ����ͶƱ�˵���Ϣ

	// һ��ͶƱ�˶����к�ѡ�����ͶƱ���
	private Set<VoteItem<C>> voteItems = new HashSet<>();
	// ͶƱʱ��
	private Calendar date = Calendar.getInstance();

	// Rep Invariants
	// voteItemsָ�����к�ѡ�����ͶƱ��¼
	// dataָͶƱʱ��
	// Abstract Function
	// AF(Vote<C>)�൱�ڶ�� VoteItem ����
	// Safety from Rep Exposure
	// date��mutable�ģ���Ҫ����ʽ����
	// voteItems�Ǹ�set��Ҳ��mutable�ģ���Ҫ����ʽ����

	private boolean checkRep() {
		// TODO
		for(VoteItem<C> vc:voteItems){
			if (vc==null) return false;
		}
		if(date==null) return false;
		return true;
	}

	/**
	 * ����һ��ѡƱ����
	 * 
	 * ����������Ƹ÷��������õĲ���
	 * 
	 */
	public Vote(Set<VoteItem<C>> v) {
		this.voteItems.addAll(v);
	}

	/**
	 * ��ѯ��ѡƱ�а���������ͶƱ��
	 * 
	 * @return ����ͶƱ��
	 */
	public Set<VoteItem<C>> getVoteItems() {
		assert checkRep();
		return new HashSet<>(voteItems);
	}

	/**
	 * һ���ض���ѡ���Ƿ������ѡƱ��
	 * 
	 * @param candidate ����ѯ�ĺ�ѡ��
	 * @return �������ú�ѡ�˵�ͶƱ��򷵻�true������false
	 */
	public int candidateIncluded(C candidate) {
		assert checkRep();
		int t=0;
		for(VoteItem<C> v:voteItems){
			if(v.getCandidate().equals(candidate)) t++;
		}
		return t;
	}

	@Override
	public int hashCode() {
		int hash=0;
		for(VoteItem<C> v:voteItems){
			hash+=v.hashCode();
		}
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		int hash=0;
		for(VoteItem<C> v:voteItems){
			hash+=v.hashCode();
		}
		return hash==obj.hashCode();
	}
}
