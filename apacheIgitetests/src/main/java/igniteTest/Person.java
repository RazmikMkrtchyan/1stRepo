package igniteTest;

/**
 * Created by Razmik.Mkrtchyan on 6/2/2016.
 */
public class Person {

	public Person(long salary) {
		this.salary = salary;
	}

	public double getSalary() {
		return salary;
	}

	public Person(PersonKey personKey) {
		this.personKey = personKey;
	}

	private PersonKey personKey;
	public void setSalary(long salary) {
		this.salary = salary;
	}

	private long id;

	private long orgId;

	private String firstName;

	private String lastName;

	private String resume;

	private double salary;

}
