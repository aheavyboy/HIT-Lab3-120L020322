package pattern;
import java.util.LinkedHashMap;
import java.util.Map;

public class DinnerOrderSelectionStrategy<C> implements SelectionStrategy<C> {
    @Override
    public Map<C, Double> Selection(Map<C, Double> statistics, int quantity) {
        Double[] score = new Double[statistics.size()];
        int i = 0;
        Double t;
        for (Double s : statistics.values()) {
            score[i] = s;
            i++;
        }
        for (i = 0; i < statistics.size(); i++) {
            for (int j = i + 1; j < statistics.size(); j++) {
                if (score[j] > score[i]) {
                    t = score[j];
                    score[j] = score[i];
                    score[i] = t;
                }
            }
        }
        Map<C, Double> c = new LinkedHashMap<>();
        i = 0;
        int flag = 0;
        while (i < quantity) {
            for (C dish : statistics.keySet()) {
                if (statistics.get(dish) == score[i]) {
                    c.put(dish, score[i]);
                    flag++;
                }
            }
            i += flag;
        }
        return c;
    }
}
