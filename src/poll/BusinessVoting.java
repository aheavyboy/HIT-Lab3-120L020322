package poll;

import auxiliary.Proposal;
import auxiliary.Voter;
import pattern.SelectionStrategy;
import pattern.StatisticsStrategy;
import vote.RealNameVote;
import vote.Vote;
import vote.VoteItem;
import vote.VoteType;

import java.util.*;

public class BusinessVoting<Proposal> extends GeneralPollImpl<Proposal> implements Poll<Proposal> {
    // ͶƱ�������
    private String name;
    // ͶƱ������ʱ��
    private Calendar date;
    // ��ѡ���󼯺�
    private List<Proposal> candidates;
    // ͶƱ�˼��ϣ�keyΪͶƱ�ˣ�valueΪ���ڱ���ͶƱ����ռȨ��
    private Map<Voter, Double> voters;
    // ��ѡ���ĺ�ѡ�����������
    private int quantity;
    // ����ͶƱ����õ�ͶƱ���ͣ��Ϸ�ѡ����Զ�Ӧ�ķ�����
    private VoteType voteType;
    // ����ѡƱ����
    protected Set<RealNameVote<Proposal>> votes;
    // ��Ʊ�����keyΪ��ѡ����valueΪ��÷�
    protected Map<Proposal, Double> statistics;
    // ��ѡ�����keyΪ��ѡ����valueΪ������λ��
    private Map<Proposal, Double> results;
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
        if (candidates==null||candidates.size()!=1) {
            System.out.println("candidates");
            return false;
        }
        if (voters==null) {
            System.out.println("voters");
            return false;
        }
        if (quantity!=1) {
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
    public BusinessVoting() {
        candidates=new ArrayList<Proposal>();
        statistics=new HashMap<Proposal,Double>();
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
    public void addCandidates(List<Proposal> candidateS) {
        if(candidateS!=null){
            candidates.addAll(candidateS);
        }

    }
    @Override
    public void addVote(Vote<Proposal> vote) throws NonLegalVotesException {
        if(vote!=null){
            votes.add((RealNameVote<Proposal>) vote);
            for(Proposal p:candidates){
                if(!(vote.candidateIncluded(p)==1)){
                    throw new NonLegalVotesException("δ����ȫ����ѡ�˻�©ѡ");
                }
            }
        }
        else System.out.println("vote=null");
    }
    @Override
    public  Set<Vote<Proposal>> getVotes(){
        return new HashSet<>(votes);
    };
    @Override
    public void statistics(StatisticsStrategy ss) throws NonLegalVotesException {
        // �˴�Ӧ���ȼ�鵱ǰ��ӵ�е�ѡƱ�ĺϷ���
        System.out.println("��Ʊ����");
        if(checkRep()){
            for(Vote<Proposal> v:votes){
                for(VoteItem<Proposal> vt:v.getVoteItems()){
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
        results=ss.Selection(statistics,1);
    }

    @Override
    public String result() {
        List<Proposal> listCan=new ArrayList<>();
        List<Double> rank=new ArrayList<>();
        String str="";
        if(results==null){
            str=str+candidates.get(0).toString()+"���ߵ�֧����Ϊ��"+statistics.values()+"\n";
        }
        else {
            for(Proposal can:results.keySet()){
                listCan.add(can);
            }
            for(Double s:results.values()) {
                rank.add(s);
            }
            for(Proposal c:results.keySet()){
                str=str+c.toString()+"���ߵ�֧����Ϊ��"+results.get(c).toString()+"\n";
            }
        }
        return str;
    }
    public String toString(){
        return "The poll \""+ name + "\" is raised at " + date;
    }
}