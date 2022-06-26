package pattern;

import java.util.HashMap;
import java.util.Map;

public class BusinessVotingSelectionStrategy<C> implements SelectionStrategy<C> {
    @Override
    public Map<C, Double> Selection(Map<C, Double> statistics, int quantity) {
        for (C p : statistics.keySet()) {
            if (statistics.get(p) >= 0.66) {
                System.out.println("�᰸ͨ��");
                Map<C, Double> c = new HashMap<>();
                c.putAll(statistics);
                return c;
            }
        }
        System.out.println("�᰸δͨ��");
        return null;
    }
}
