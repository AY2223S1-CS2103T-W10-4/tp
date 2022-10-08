package modtrekt.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import modtrekt.model.AddressBook;
import modtrekt.model.ReadOnlyAddressBook;
import modtrekt.model.person.Address;
import modtrekt.model.person.Email;
import modtrekt.model.person.Name;
import modtrekt.model.person.Person;
import modtrekt.model.person.Phone;
import modtrekt.model.tag.Tag;
import modtrekt.model.task.Task;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Task[] getSamplePersons() {
        return new Task[] {
//            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
//                new Address("Blk 30 Geylang Street 29, #06-40"),
//                getTagSet("friends")),
//            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
//                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
//                getTagSet("colleagues", "friends")),
//            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
//                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
//                getTagSet("neighbours")),
//            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
//                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
//                getTagSet("family")),
//            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
//                new Address("Blk 47 Tampines Street 20, #17-35"),
//                getTagSet("classmates")),
//            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
//                new Address("Blk 45 Aljunied Street 85, #11-31"),
//                getTagSet("colleagues"))
                new Task(new Name("Alex Yeoh")),
                new Task(new Name("Bernice Yu")),
                new Task(new Name("Charlotte Oliveiro")),
                new Task(new Name("David Li")),
                new Task(new Name("Irfan Ibrahim")),
                new Task(new Name("Roy Balakrishnan"))

        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Task samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
