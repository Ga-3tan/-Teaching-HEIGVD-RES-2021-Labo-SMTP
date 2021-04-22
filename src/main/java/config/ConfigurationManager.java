package config;

import lombok.Getter;
import model.mail.Group;
import model.mail.Message;
import model.mail.Person;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class ConfigurationManager implements IConfigurationManager {
    private String smtpServerAddress;
    private int smtpServerPort;
    private int numberOfGroup;
    private String witnessToCC;

    public ConfigurationManager() {
        FileReader propertiesReader = null;
        try {
            propertiesReader = new FileReader("configuration/config.properties");

            // read properties
            Properties properties = new Properties();
            properties.load(propertiesReader);

            // save properties
            smtpServerAddress = properties.getProperty("smtpServerAddress");
            smtpServerPort = Integer.parseInt(properties.getProperty("smtpServerPort"));
            numberOfGroup = Integer.parseInt(properties.getProperty("numberOfGroups"));
            witnessToCC = properties.getProperty("witnessToCC");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                assert propertiesReader != null;
                propertiesReader.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public String getSmtpServerAddress() {
        return smtpServerAddress;
    }

    @Override
    public int getSmtpServerPort() {
        return smtpServerPort;
    }

    @Override
    public int getNumberOfGroup() {
        return numberOfGroup;
    }

    @Override
    public String getWitnessToCC() {
        return witnessToCC;
    }

    @Override
    public List<Person> getVictims() {
        List<Person> victims = new LinkedList<>();
        BufferedReader emailReader = null;
        try {
            String line;
            emailReader = new BufferedReader(new InputStreamReader(new FileInputStream("configuration/emails.utf8"), StandardCharsets.UTF_8));
            while ((line = emailReader.readLine()) != null) {
                String[] mailToken = line.split("@")[0].split("\\.");
                victims.add(new Person(mailToken[1], mailToken[0], line));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                assert emailReader != null;
                emailReader.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return victims;
    }

    @Override
    public List<Message> getMessages() {
        List<Message> messages = new LinkedList<>();
        BufferedReader messageReader = null;
        try {
            messageReader = new BufferedReader(new InputStreamReader(new FileInputStream("configuration/messages.utf8"), StandardCharsets.UTF_8));
            String line;
            String subject = "";
            StringBuilder message = new StringBuilder();
            while ((line = messageReader.readLine()) != null) {
                if (line.contains("Subject:")) {
                    subject = line.split("Subject:")[1].trim();
                } else if (line.contains("====")) {
                    messages.add(new Message(subject, message.toString()));
                    message = new StringBuilder();
                    subject = "";
                } else {
                    message.append(line).append('\n');
                }
            }
            messages.add(new Message(subject, message.toString()));
            messageReader.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                assert messageReader != null;
                messageReader.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return messages;
    }
}
