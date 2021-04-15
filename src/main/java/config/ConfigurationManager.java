package config;

import lombok.Getter;
import model.mail.Message;
import model.mail.Person;

import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Getter
public class ConfigurationManager implements IConfigurationManager{
    private String smtpServerAddress;
    private int smtpServerPort;
    private int numberOfGroup;

    private final List<Person> witnesses = new LinkedList<>();
    private final List<Person> victims = new LinkedList<>();
    private final List<Message> messages = new LinkedList<>();

    public ConfigurationManager() {
        try {
            FileReader propertiesReader = new FileReader("configuration/config.properties");
            FileReader emailReader = new FileReader("configuration/emails.utf8");
            FileReader messageReader = new FileReader("configuration/messages.utf8");


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
