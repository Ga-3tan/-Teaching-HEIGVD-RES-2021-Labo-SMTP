package model.mail;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Group {
    private final Person sender;
    private final List<Person> recipients = new ArrayList<>();

    public Group(Person sender) {
        this.sender = sender;
    }

    public void addRecipient(Person p) {
        recipients.add(p);
    }

    public void removeRecipient(Person p) {
        recipients.remove(p);
    }
}
