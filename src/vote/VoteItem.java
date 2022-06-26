package vote;

import auxiliary.Person;

import java.util.Date;
import java.util.Random;

//immutable
public class VoteItem<C> {

	// 本投票项所针对的候选对象
	private C candidate;
	// 对候选对象的具体投票选项，例如“支持”、“反对”等
	// 无需保存具体数值，需要时可以从投票活动的VoteType对象中查询得到
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
	 * 创建一个投票项对象 例如：针对候选对象“张三”，投票选项是“支持”
	 * 
	 * @param candidate 所针对的候选对象
	 * @param value     所给出的投票选项
	 */
	public VoteItem(C candidate, String value) {
		this.candidate = candidate;
		this.value = value;
	}

	/**
	 * 得到该投票选项的具体投票项
	 * 
	 * @return
	 */
	public String getVoteValue() {
		return this.value;
	}
	/**
	 * 得到该投票选项所对应的候选人
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
