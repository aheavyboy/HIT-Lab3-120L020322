package poll;

import auxiliary.Person;
import auxiliary.Voter;
import pattern.SelectionStrategy;
import pattern.StatisticsStrategy;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

import java.util.*;

public class Election<Person> extends GeneralPollImpl<Person> implements Poll<Person> {
    // ͶƱ�������
    private String name;
    // ͶƱ������ʱ��
    private Calendar date;
    // ��ѡ���󼯺�
    private List<Person> candidates;
    // ͶƱ�˼��ϣ�keyΪͶƱ�ˣ�valueΪ���ڱ���ͶƱ����ռȨ��
    private Map<Voter, Double> voters;
    // ��ѡ���ĺ�ѡ�����������
    private int quantity;
    // ����ͶƱ����õ�ͶƱ���ͣ��Ϸ�ѡ����Զ�Ӧ�ķ�����
    private VoteType voteType;
    // ����ѡƱ����
    protected Set<Vote<Person>> votes;
    // ��Ʊ�����keyΪ��ѡ����valueΪ��÷�
    protected Map<Person, Double> statistics;
    // ��ѡ�����keyΪ��ѡ����valueΪ������λ��
    private Map<Person, Double> results;
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
    public Election() {
        candidates=new ArrayList<Person>();
        statistics=new HashMap<Person,Double>();
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
    }
    @Override
    public void addVoters(Map<Voter, Double> voters1) {
        if(voters1!=null){
            voters.putAll(voters1);
        }
    }

    @Override
    public void addCandidates(List<Person> candidateS) {
        if(candidateS!=null){
            candidates.addAll(candidateS);
        }

    }
    @Override
    public void addVote(Vote<Person> vote) throws NonLegalVotesException {
        if(vote!=null){
            votes.add(vote);
            int flag=0;
            int k=3;
            for(Person p:candidates){
                if(!(vote.candidateIncluded(p)==1)){
                    throw new NonLegalVotesException("δ����ȫ����ѡ�˻�©ѡ");
                }
            }
            for(Person p:candidates){
                if(vote.candidateIncluded(p)==1){
                    flag++;
                }
            }
            if(flag>k) throw new NonLegalVotesException("����һ���˿�ѡƱ��");
        }
        else System.out.println("vote=null");
    }
    @Override
    public  Set<Vote<Person>> getVotes(){
        return new HashSet<>(votes);
    };
    @Override
    public void statistics(StatisticsStrategy ss) throws NonLegalVotesException {
        // �˴�Ӧ���ȼ�鵱ǰ��ӵ�е�ѡƱ�ĺϷ���
        System.out.println("��Ʊ����");
        if(checkRep()){
            for(Vote<Person> v:votes){
                for(VoteItem<Person> vt:v.getVoteItems()){
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
        List<Person> listCan=new ArrayList<>();
        List<Double> rank=new ArrayList<>();
        for(Person can:results.keySet()){
            listCan.add(can);
        }
        for(Double s:results.values()) {
            rank.add(s);
        }
        String str="";
        int i=1;
        for(Person c:results.keySet()){
            str=str+c.toString()+"��֧����Ϊ��"+results.get(c).toString()+":��"+i+"��\n";
            i++;
        }
        return str;
    }
    public String toString(){
        return "The poll \""+ name + "\" is raised at " + date;
    }
    //TODO
}
