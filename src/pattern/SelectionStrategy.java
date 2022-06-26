package pattern;
import auxiliary.Dish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public interface SelectionStrategy<C> {
    public Map<C,Double> Selection(Map<C,Double> statistics, int quantity);
	// TODO

}

