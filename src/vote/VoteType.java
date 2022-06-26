package vote;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//immutable
public class VoteType {

	// keyΪѡ������valueΪѡ������Ӧ�ķ���
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
	 * ����һ��ͶƱ���Ͷ���
	 * 
	 * ����������Ƹ÷��������õĲ���
	 */
	public VoteType(Map<String, Integer> op) {
		options.putAll(op);
	}

	/**
	 * ���������ض��﷨������ַ���������һ��ͶƱ���Ͷ���
	 * 
	 * @param regex ��ѭ�ض��﷨�ġ�����ͶƱ������Ϣ���ַ�����������12�ٿ��ǣ�
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
	 * �ж�һ��ͶƱѡ���Ƿ�Ϸ�������Poll�ж�ѡƱ�ĺϷ��Լ�飩
	 * 
	 * ���磬ͶƱ�˸�����ͶƱѡ���ǡ�Strongly reject������options�в��������ѡ�������ǷǷ���
	 * 
	 * ���ܸĶ��÷����Ĳ���
	 * 
	 * @param option һ��ͶƱѡ��
	 * @return �Ϸ���true������false
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
	 * ����һ��ͶƱѡ��õ����Ӧ�ķ���
	 * 
	 * ���磬ͶƱ�˸�����ͶƱѡ���ǡ�֧�֡�����ѯ�õ����Ӧ�ķ�����1
	 * 
	 * ���ܸĶ��÷����Ĳ���
	 * 
	 * @param option һ��ͶƱ��ȡֵ
	 * @return ��ѡ������Ӧ�ķ���
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
