package vote;

import static org.junit.jupiter.api.Assertions.*;

import auxiliary.Person;
import org.junit.jupiter.api.Test;

class VoteItemTest {

	// test strategy
	// 对VoteItem类中每个方法进行测试
	
	@Test
	void test() {
		Person p1 = new Person("ABC", 19);
		Person p2 = new Person("DEF", 20);
		Person p3 = new Person("GHI", 21);
		VoteItem<Person> vi11 = new VoteItem<>(p1, "Support");
		VoteItem<Person> vi12 = new VoteItem<>(p2, "Oppose");
		VoteItem<Person> vi13 = new VoteItem<>(p3, "Support");
		VoteItem<Person> vi21 = new VoteItem<>(p1, "Oppose");
		VoteItem<Person> vi22 = new VoteItem<>(p2, "Oppose");
		VoteItem<Person> vi23 = new VoteItem<>(p3, "Waive");
		assertEquals("Waive",vi23.getVoteValue());
		assertEquals(p1,vi11.getCandidate());
		assertEquals(true,vi12.equals(vi22));
	}

}
