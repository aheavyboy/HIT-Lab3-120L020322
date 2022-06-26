package poll;

import java.util.*;

import auxiliary.Voter;
import pattern.SelectionStrategy;
import pattern.StatisticsStrategy;
import vote.VoteItem;
import vote.VoteType;
import vote.Vote;

public class GeneralPollImpl<C> implements Poll<C> {

	// ͶƱ�������
	private String name;
	public String getName(){
		return name;
	}
	// ͶƱ������ʱ��
	private Calendar date;
	// ��ѡ���󼯺�
	private List<C> candidates;
	// ͶƱ�˼��ϣ�keyΪͶƱ�ˣ�valueΪ���ڱ���ͶƱ����ռȨ��
	public List<C> getCandidates(){
		return new ArrayList<>(candidates);
	}
	private Map<Voter, Double> voters;
	// ��ѡ���ĺ�ѡ�����������
	public Map<Voter,Double> getVoters(){
		return new HashMap<>(voters);
	}
	private int quantity;
	// ����ͶƱ����õ�ͶƱ���ͣ��Ϸ�ѡ����Զ�Ӧ�ķ�����
	public int getQuantity(){
		return quantity;
	}
	private VoteType voteType;
	public VoteType getVoteType(){
		return voteType;
	}
	// ����ѡƱ����
	protected Set<Vote<C>> votes;
	// ��Ʊ�����keyΪ��ѡ����valueΪ��÷�
	protected Map<C, Double> statistics;
	// ��ѡ�����keyΪ��ѡ����valueΪ������λ��
	private Map<C, Double> results;
	// Rep Invariants
	// name,date,candidates,voters��Ϊ�ռ��ɣ�quantity�������0��С�ں�ѡ������
	// voteType,votes��Ϊ�ռ��ɣ�statistics��results��Ϊ���ҷֱ���candidates��quantity��С���
	// Abstract Function
	// AF(name, date, type, quantity)=һ����Ϊname�Ļ����dateʱ�俪ʼ��ѡ������Ϊtype��ѡ������Ϊquantity
	// Safety from Rep Exposure
	// ������Ϣ����private��protected��
	// ���캯����������immutable��

	private boolean checkRep() {
		if (name==null) {
			System.out.println("name");
			return false;
		}
		if (date==null) {
			System.out.println("date");
			return false;
		}
		if (candidates==null) {
			System.out.println("candidates");
			return false;
		}
		if (voters==null) {
			System.out.println("voters");
			return false;
		}
		if (quantity<=0||quantity>=candidates.size()) {
			System.out.println("quantity");
			return false;
		}
		if (voteType==null) {
			System.out.println("voteType");
			return false;
		}
		if (votes==null) {
			System.out.println("votes");
			return false;
		}
		return true;
	}
	/**
	 * ���캯��
	 */
	public GeneralPollImpl() {
		candidates=new ArrayList<C>();
		statistics=new HashMap<C,Double>();
		results=new HashMap<>();
		votes=new HashSet<>();
		voters=new HashMap<>();
	}
	@Override
	public void setInfo(String name, Calendar date, VoteType type, int quantity) {
		this.name=name;
		this.date=date;
		this.voteType=type;
		this.quantity=quantity;
		System.out.println(this.quantity);
	}
	@Override
	public void addVoters(Map<Voter, Double> voters1) {
		if(voters1!=null){
			voters.putAll(voters1);
		}
	}
	@Override
	public void addCandidates(List<C> candidateS) {
		if(candidateS!=null){
			candidates.addAll(candidateS);
		}
	}
	@Override
	public void addVote(Vote<C> vote) throws NonLegalVotesException {
		if(vote!=null){
			System.out.println("�ɹ�"+vote);
			votes.add(vote);
		}
		else System.out.println("vote=null");
	}
	@Override
	public  Set<Vote<C>> getVotes(){
		return new HashSet<>(votes);
	};
	@Override
	public void statistics(StatisticsStrategy ss) throws NonLegalVotesException {
		// �˴�Ӧ���ȼ�鵱ǰ��ӵ�е�ѡƱ�ĺϷ���
		System.out.println("��Ʊ����");
		if(checkRep()){
			for(Vote<C> v:votes){
				for(VoteItem<C> vt:v.getVoteItems()){
					if(!voteType.checkLegality(vt.getVoteValue())){
						throw new NonLegalVotesException("ѡƱ���Ϸ�");
					}
				}
			}
			System.out.println("ʵ���У�"+votes.size());
			statistics=ss.calculate(voters,votes);
			System.out.println(statistics);
		}
	}

	@Override
	public void selection(SelectionStrategy ss) {
		results=ss.Selection(statistics,quantity);
	}

	@Override
	public String result() {
		List<C> listCan=new ArrayList<>();
		List<Double> rank=new ArrayList<>();
		for(C can:results.keySet()){
			listCan.add(can);
		}
		for(Double s:results.values()) {
			rank.add(s);
		}
		String str="";
		for(C c:results.keySet()){
			str=str+c.toString()+"'scoreΪ��"+results.get(c).toString()+"\n";
		}
		return str;
	}
	public String toString(){
		return "The poll \""+ name + "\" is raised";
	}
	public Double accept1(visitor<C> v){
		return v.visit((BusinessVoting<C>) this);
	}
	public Double accept2(visitor<C> v){
		return v.visit((DinnerOrder<C>) this);
	}
	public Double accept3(visitor<C> v){
		return v.visit((Election<C>)  this);
	}
}
