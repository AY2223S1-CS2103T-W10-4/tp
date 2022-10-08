package modtrekt.model.person;

import static modtrekt.testutil.Assert.assertThrows;
import static modtrekt.testutil.TypicalPersons.ALICE;
import static modtrekt.testutil.TypicalPersons.BOB;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import modtrekt.logic.commands.CommandTestUtil;
import modtrekt.model.person.exceptions.DuplicatePersonException;
import modtrekt.model.person.exceptions.PersonNotFoundException;
import modtrekt.model.task.UniqueTaskList;
import modtrekt.testutil.PersonBuilder;

public class UniqueTaskListTest {

    private final UniqueTaskList uniqueTaskList = new UniqueTaskList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueTaskList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueTaskList.add(ALICE);
        assertTrue(uniqueTaskList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueTaskList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(CommandTestUtil.VALID_ADDRESS_BOB)
                .withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueTaskList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueTaskList.add(ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniqueTaskList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.setPerson(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.setPerson(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueTaskList.setPerson(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueTaskList.add(ALICE);
        uniqueTaskList.setPerson(ALICE, ALICE);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(ALICE);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueTaskList.add(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(CommandTestUtil.VALID_ADDRESS_BOB)
                .withTags(CommandTestUtil.VALID_TAG_HUSBAND)
                .build();
        uniqueTaskList.setPerson(ALICE, editedAlice);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(editedAlice);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueTaskList.add(ALICE);
        uniqueTaskList.setPerson(ALICE, BOB);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(BOB);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueTaskList.add(ALICE);
        uniqueTaskList.add(BOB);
        assertThrows(DuplicatePersonException.class, () -> uniqueTaskList.setPerson(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueTaskList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueTaskList.add(ALICE);
        uniqueTaskList.remove(ALICE);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.setPersons((UniqueTaskList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueTaskList.add(ALICE);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(BOB);
        uniqueTaskList.setPersons(expectedUniqueTaskList);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTaskList.setPersons((List<Person>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueTaskList.add(ALICE);
        List<Person> personList = Collections.singletonList(BOB);
        uniqueTaskList.setPersons(personList);
        UniqueTaskList expectedUniqueTaskList = new UniqueTaskList();
        expectedUniqueTaskList.add(BOB);
        assertEquals(expectedUniqueTaskList, uniqueTaskList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Person> listWithDuplicatePersons = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniqueTaskList.setPersons(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueTaskList.asUnmodifiableObservableList().remove(0));
    }
}
