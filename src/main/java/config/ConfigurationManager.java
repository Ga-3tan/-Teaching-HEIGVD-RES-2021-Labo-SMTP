package config;

import prank.Message;
import prank.Person;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Logger;

public class ConfigurationManager implements IConfigurationManager {
    private static final Logger LOG = Logger.getLogger(ConfigurationManager.class.getName());
    private String smtpServerAddress;
    private int smtpServerPort;
    private int numberOfGroup;
    private final List<String> witnessesToCC = new ArrayList<>();

    private final String CONFIG_FOLDER_PATH;

    public ConfigurationManager() throws IOException {
        this("configuration/");
    }

    public ConfigurationManager(String configFolderPath) throws IOException {
        this.CONFIG_FOLDER_PATH = configFolderPath;
        FileReader propertiesReader = null;
        try {
            propertiesReader = new FileReader(CONFIG_FOLDER_PATH + "config.properties");

            // read properties
            Properties properties = new Properties();
            properties.load(propertiesReader);

            // save properties
            smtpServerAddress = properties.getProperty("smtpServerAddress");
            smtpServerPort = Integer.parseInt(properties.getProperty("smtpServerPort"));
            numberOfGroup = Integer.parseInt(properties.getProperty("numberOfGroups"));
            Collections.addAll(witnessesToCC, properties.getProperty("witnessToCC").split(","));
        } finally {
            try {
                if (propertiesReader != null) propertiesReader.close();
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
    public List<String> getWitnessesToCC() {
        return witnessesToCC;
    }

    @Override
    public List<Person> getVictims() throws IOException {
        List<Person> victims = new LinkedList<>();
        BufferedReader emailReader = null;
        try {
            String line;
            emailReader = new BufferedReader(new InputStreamReader(new FileInputStream(CONFIG_FOLDER_PATH + "emails.utf8"), StandardCharsets.UTF_8));
            while ((line = emailReader.readLine()) != null) {
                String firstName = "", lastname = "";
                String[] tokens = line.split("@");
                String[] personInfo = tokens[0].split("\\.");
                if (personInfo.length > 1) {
                    firstName = personInfo[0];
                    lastname  = personInfo[1];
                }
                victims.add(new Person(firstName, lastname, line));
            }
        } finally {
            try {
                if (emailReader != null) emailReader.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return victims;
    }

    @Override
    public List<Message> getMessages() throws IOException {
        List<Message> messages = new LinkedList<>();
        BufferedReader messageReader = null;
        try {
            messageReader = new BufferedReader(new InputStreamReader(new FileInputStream(CONFIG_FOLDER_PATH + "messages.utf8"), StandardCharsets.UTF_8));
            String line;
            String subject = "";
            StringBuilder message = new StringBuilder();
            while ((line = messageReader.readLine()) != null) {
                if (line.contains("Subject:")) {
                    subject = line.substring("Subject:".length());
                } else if (line.contains("====")) {
                    messages.add(new Message(subject, message.toString()));
                    message = new StringBuilder();
                    subject = "";
                } else {
                    message.append(line).append('\n');
                }
            }
            messageReader.close();
        } finally {
            try {
                if (messageReader != null) messageReader.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return messages;
    }
}
