package config;

import model.mail.Message;
import model.mail.Person;

import java.util.List;

public interface IConfigurationManager {
    String getSmtpServerAddress();

    int getSmtpServerPort();

    int getNumberOfGroup();

    List<Person> getVictims();

    List<Message> getMessages();

    List<String> getWitnessToCC();
}
