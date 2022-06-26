package vote;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class VoteTypeTest {

	// test strategy
	// TODO
	
	@Test
	void test() {
		Map<String, Integer> v = new HashMap<>();
		v.put("Support", 1);
		v.put("Oppose", -1);
		v.put("Waive", 0);
		VoteType vt = new VoteType(v);
		assertEquals(false, vt.checkLegality(new String("Strongly reject")));
		assertEquals(true,vt.checkLegality(new String("Waive")));
		Map<String, Integer> v1 = new HashMap<>();
		v1.put("Support", 1);
		v1.put("Oppose", -1);
		v1.put("Waive", 0);
		VoteType vt1 = new VoteType(v1);
		assertEquals(true,vt.equals(vt1));
		assertEquals(1,vt.getScoreByOption("Support"));
	}

}
