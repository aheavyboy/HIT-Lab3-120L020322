package vote;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//immutable
public class VoteType {

	// key为选项名、value为选项名对应的分数
	private Map<String, Integer> options = new HashMap<>();
	// Rep Invariants
	// 	options represent like,unlike,indifferent,support,opposition,waiver
	// 	Abstract Function
	// AF(options)=[-1,0,1,2]
	// Safety from Rep Exposure
	// 	the choices the options represent are string ,so they are immutable
	//	the options map is mutable,so make defensive copies to avoid sharing the rep's options map
	private boolean checkRep() {
		int flag=0;
		for(String s: options.keySet()){
			assert s!=null;
			if(s.equals("Support")||s.equals("Waive")||s.equals("Oppose")){
				if(flag==2) return false;
				flag=1;
			}
			if(s.equals("Like")||s.equals("Unlike")||s.equals("Indifferent")) {
				if(flag==1) return false;
				flag=2;
			}
		}
		if(flag==1){
			for(Integer i:options.values()){
				assert i>=-1 && i<=1;
			}
		}
		if(flag==2){
			for(Integer i:options.values()){
				assert i>=0 && i<=2;
			}
		}
		if(flag==0) return false;
		return true;
	}
	/**
	 * 创建一个投票类型对象
	 * 
	 * 可以自行设计该方法所采用的参数
	 */
	public VoteType(Map<String, Integer> op) {
		options.putAll(op);
	}

	/**
	 * 根据满足特定语法规则的字符串，创建一个投票类型对象
	 * 
	 * @param regex 遵循特定语法的、包含投票类型信息的字符串（待任务12再考虑）
	 */
	public VoteType(String regex) {
		Pattern pattern1=Pattern.compile("\"Like\"\\(2\\)\\|\"Unlike\"\\(0\\)\\|\"Indifferent\"\\(1\\)");
		Pattern pattern2=Pattern.compile("\"Support\"\\|\"Oppose\"\\|\"Waive\"");
		Matcher matcher1=pattern1.matcher(regex);
		Matcher matcher2=pattern2.matcher(regex);
		if(matcher2.matches()){
			Map<String, Integer> types = new HashMap<>();
			types.put("Support", 1);
			types.put("Oppose", -1);
			types.put("Waive", 0);
			options.putAll(types);
		}else if(matcher1.matches()){
			Map<String, Integer> types = new HashMap<>();
			types.put("Like", 2);
			types.put("Unlike", 0);
			types.put("Indifferent", 1);
			options.putAll(types);
		}
	}

	/**
	 * 判断一个投票选项是否合法（用于Poll中对选票的合法性检查）
	 * 
	 * 例如，投票人给出的投票选项是“Strongly reject”，但options中不包含这个选项，因此它是非法的
	 * 
	 * 不能改动该方法的参数
	 * 
	 * @param option 一个投票选项
	 * @return 合法则true，否则false
	 */
	public boolean checkLegality(String option) {
		int flag=0;
		checkRep();
		for(String s:options.keySet()){
			if(s.equals(option)) flag=1;
		}
		if (flag==1) return true;
		else return false;
	}

	/**
	 * 根据一个投票选项，得到其对应的分数
	 * 
	 * 例如，投票人给出的投票选项是“支持”，查询得到其对应的分数是1
	 * 
	 * 不能改动该方法的参数
	 * 
	 * @param option 一个投票项取值
	 * @return 该选项所对应的分数
	 */
	public int getScoreByOption(String option) {
		return options.get(option);
	}

	@Override
	public int hashCode() {
		int r=0;
		for(String s:options.keySet()){
			r+=s.hashCode();
		}
		return r;
	}

	@Override
	public boolean equals(Object obj) {
		int r=0;
		for(String s:options.keySet()){
			r+=s.hashCode();
		}
		if(r==obj.hashCode()) return true;
		else return false;
	}
}
