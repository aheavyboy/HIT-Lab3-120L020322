package pattern;

import auxiliary.Voter;
import vote.Vote;
import java.util.*;

public interface StatisticsStrategy<C,V extends Vote<C>> {
    Map<C, Double> calculate(Map<Voter,Double> voters, Set<V> votes);
}

