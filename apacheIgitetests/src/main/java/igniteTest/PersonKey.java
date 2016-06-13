package igniteTest;

import org.apache.ignite.cache.affinity.AffinityKeyMapped;

/**
 * Created by Razmik.Mkrtchyan on 6/7/2016.
 */
public class PersonKey {
    public PersonKey(String personId, String companyId) {
        this.personId = personId;
        this.companyId = companyId;
    }

    // Person ID used to identify a person.
    private String personId;

    // Company ID which will be used for affinity.
    @AffinityKeyMapped
    private String companyId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonKey personKey = (PersonKey) o;

        if (personId != null ? !personId.equals(personKey.personId) : personKey.personId != null) return false;
        return companyId != null ? companyId.equals(personKey.companyId) : personKey.companyId == null;

    }

    @Override
    public int hashCode() {
        int result = personId != null ? personId.hashCode() : 0;
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        return result;
    }
}
