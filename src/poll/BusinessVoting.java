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
    // 投票活动的名称
    private String name;
    // 投票活动发起的时间
    private Calendar date;
    // 候选对象集合
    private List<Proposal> candidates;
    // 投票人集合，key为投票人，value为其在本次投票中所占权重
    private Map<Voter, Double> voters;
    // 拟选出的候选对象最大数量
    private int quantity;
    // 本次投票拟采用的投票类型（合法选项及各自对应的分数）
    private VoteType voteType;
    // 所有选票集合
    protected Set<RealNameVote<Proposal>> votes;
    // 计票结果，key为候选对象，value为其得分
    protected Map<Proposal, Double> statistics;
    // 遴选结果，key为候选对象，value为其排序位次
    private Map<Proposal, Double> results;
    // Rep Invariants
    // name,date,candidates,voters不为空即可，quantity满足大于0且小于候选人数，
    // voteType,votes不为空即可，statistics和results不为空且分别于candidates和quantity大小相等
    // Abstract Function
    // AF(name, date, type, quantity)=一个名为name的活动，在date时间开始，选举类型为type，选择数量为quantity
    // Safety from Rep Exposure
    // 所有信息都是private或protected的
    // 构造函数参数都是immutable的

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
     * 构造函数
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
                    throw new NonLegalVotesException("未包含全部候选人或漏选");
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
        // 此处应首先检查当前所拥有的选票的合法性
        System.out.println("计票如下");
        if(checkRep()){
            for(Vote<Proposal> v:votes){
                for(VoteItem<Proposal> vt:v.getVoteItems()){
                    if(!voteType.checkLegality(vt.getVoteValue())){
                        throw new NonLegalVotesException("选票不合法");
                    }
                }
            }
            System.out.println("实例中："+votes.size());
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
            str=str+candidates.get(0).toString()+"政策的支持率为："+statistics.values()+"\n";
        }
        else {
            for(Proposal can:results.keySet()){
                listCan.add(can);
            }
            for(Double s:results.values()) {
                rank.add(s);
            }
            for(Proposal c:results.keySet()){
                str=str+c.toString()+"政策的支持率为："+results.get(c).toString()+"\n";
            }
        }
        return str;
    }
    public String toString(){
        return "The poll \""+ name + "\" is raised at " + date;
    }
}