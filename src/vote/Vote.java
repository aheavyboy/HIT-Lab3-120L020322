package vote;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

//immutable
public class Vote<C> {

	// 缺省为“匿名”投票，即不需要管理投票人的信息

	// 一个投票人对所有候选对象的投票项集合
	private Set<VoteItem<C>> voteItems = new HashSet<>();
	// 投票时间
	private Calendar date = Calendar.getInstance();

	// Rep Invariants
	// voteItems指对所有候选对象的投票记录
	// data指投票时间
	// Abstract Function
	// AF(Vote<C>)相当于多个 VoteItem 对象
	// Safety from Rep Exposure
	// date是mutable的，需要防御式拷贝
	// voteItems是个set，也是mutable的，需要防御式拷贝

	private boolean checkRep() {
		// TODO
		for(VoteItem<C> vc:voteItems){
			if (vc==null) return false;
		}
		if(date==null) return false;
		return true;
	}

	/**
	 * 创建一个选票对象
	 * 
	 * 可以自行设计该方法所采用的参数
	 * 
	 */
	public Vote(Set<VoteItem<C>> v) {
		this.voteItems.addAll(v);
	}

	/**
	 * 查询该选票中包含的所有投票项
	 * 
	 * @return 所有投票项
	 */
	public Set<VoteItem<C>> getVoteItems() {
		assert checkRep();
		return new HashSet<>(voteItems);
	}

	/**
	 * 一个特定候选人是否包含本选票中
	 * 
	 * @param candidate 待查询的候选人
	 * @return 若包含该候选人的投票项，则返回true，否则false
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
