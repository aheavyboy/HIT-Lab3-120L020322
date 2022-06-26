package vote;

import static org.junit.jupiter.api.Assertions.*;

import auxiliary.Person;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

class VoteTest {

	// test strategy
	// TODO
	
	@Test
	void test() {
		Person p1 = new Person("ABC", 19);
		Person p2 = new Person("DEF", 20);
		Person p3 = new Person("GHI", 21);

		VoteItem<Person> vi11 = new VoteItem<>(p1, "Support");
		VoteItem<Person> vi12 = new VoteItem<>(p2, "Oppose");
		VoteItem<Person> vi13 = new VoteItem<>(p3, "Support");
		VoteItem<Person> vi21 = new VoteItem<>(p1, "Oppose");
		VoteItem<Person> vi22 = new VoteItem<>(p2, "Waive");
		VoteItem<Person> vi23 = new VoteItem<>(p3, "Waive");
		VoteItem<Person> vi31 = new VoteItem<>(p1, "Support");
		VoteItem<Person> vi32 = new VoteItem<>(p2, "Oppose");
		VoteItem<Person> vi33 = new VoteItem<>(p3, "Support");
		Set<VoteItem<Person>> v1=new HashSet<>();
		v1.add(vi11);
		v1.add(vi12);
		v1.add(vi13);
		Set<VoteItem<Person>> v2=new HashSet<>();
		v2.add(vi21);
		v2.add(vi22);
		v2.add(vi23);
		Set<VoteItem<Person>> v3=new HashSet<>();
		v1.add(vi31);
		v1.add(vi32);
		v1.add(vi33);
		Vote<Person> rv1 = new Vote<Person>(v1);
		Vote<Person> rv2 = new Vote<Person>(v2);
		Vote<Person> rv3 = new Vote<Person>(v3);
		assertEquals(rv1,rv1.getVoteItems());
		assertEquals(1,rv1.candidateIncluded(p1));
		assertEquals(false,rv1.equals(rv3));
	}

}
