package poll;

import auxiliary.Dish;
import auxiliary.Voter;
import pattern.SelectionStrategy;
import pattern.StatisticsStrategy;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

import java.util.*;

public class DinnerOrder<Dish> extends GeneralPollImpl<Dish> implements Poll<Dish> {
    // ͶƱ�������
    private String name;
    // ͶƱ������ʱ��
    private Calendar date;
    // ��ѡ���󼯺�
    private List<Dish> candidates;
    // ͶƱ�˼��ϣ�keyΪͶƱ�ˣ�valueΪ���ڱ���ͶƱ����ռȨ��
    private Map<Voter, Double> voters;
    // ��ѡ���ĺ�ѡ�����������
    private int quantity;
    // ����ͶƱ����õ�ͶƱ���ͣ��Ϸ�ѡ����Զ�Ӧ�ķ�����
    private VoteType voteType;
    // ����ѡƱ����
    protected Set<Vote<Dish>> votes;
    // ��Ʊ�����keyΪ��ѡ����valueΪ��÷�
    protected Map<Dish, Double> statistics;
    // ��ѡ�����keyΪ��ѡ����valueΪ������λ��
    private Map<Dish, Double> results;
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
    public DinnerOrder() {
        candidates=new ArrayList<Dish>();
        statistics=new HashMap<Dish,Double>();
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
    public void addCandidates(List<Dish> candidateS) {
        if(candidateS!=null){
            candidates.addAll(candidateS);
        }

    }
    @Override
    public void addVote(Vote<Dish> vote) throws NonLegalVotesException {
        if(vote!=null){
            votes.add(vote);
            for(Dish p:candidates){
                if(!(vote.candidateIncluded(p)==1)){
                    throw new NonLegalVotesException("δ����ȫ����ѡ�˻�©ѡ");
                }
            }
        }
        else System.out.println("vote=null");
    }
    @Override
    public  Set<Vote<Dish>> getVotes(){
        return new HashSet<>(votes);
    };
    @Override
    public void statistics(StatisticsStrategy ss) throws NonLegalVotesException {
        // �˴�Ӧ���ȼ�鵱ǰ��ӵ�е�ѡƱ�ĺϷ���
        System.out.println("��Ʊ����");
        if(checkRep()){
            for(Vote<Dish> v:votes){
                for(VoteItem<Dish> vt:v.getVoteItems()){
                    if(!voteType.checkLegality(vt.getVoteValue())){
                        throw new NonLegalVotesException("ͶƱ���Ϸ�");
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
        List<Dish> listCan=new ArrayList<>();
        List<Double> rank=new ArrayList<>();
        for(Dish can:results.keySet()){
            listCan.add(can);
        }
        for(Double s:results.values()) {
            rank.add(s);
        }
        String str="";
        int i=1;
        for(Dish c:results.keySet()){
            str=str+c.toString()+"�ĵ÷�Ϊ��"+results.get(c).toString()+"�����ǵ�"+i+"��Եģ�"+"\n";
            i++;
        }
        return str;
    }
    public String toString(){
        return "The poll \""+ name + "\" is raised at " + date;
    }
}
