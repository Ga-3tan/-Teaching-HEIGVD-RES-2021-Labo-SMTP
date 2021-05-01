package config;

import prank.Message;
import prank.Person;

import java.io.IOException;
import java.util.List;

public interface IConfigurationManager {
    String getSmtpServerAddress();

    int getSmtpServerPort();

    int getNumberOfGroup();

    List<Person> getVictims() throws IOException;

    List<Message> getMessages() throws IOException;

    List<String> getWitnessesToCC();
}
