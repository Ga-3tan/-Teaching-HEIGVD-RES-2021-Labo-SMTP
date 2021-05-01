package config;

import model.mail.Message;
import model.mail.Person;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ConfigurationManagerTest {

    static final String TEMP_CONFIG_FOLDER_PATH = "./configurationTest/";
    static final String SMTP_ADDRESS = "localhost";
    static final int SMTP_PORT = 25;
    static final int NB_GROUPS = 9000;
    static final List<String> CCs = new ArrayList<>(Arrays.asList("gaetan.zwick@heig-vd.ch", "marco.maziero@heig-vd.ch"));

    static final Person P1 = new Person("getan", "zwock", "getan.zwock@jaimail.com");
    static final Person P2 = new Person("morko", "moziero", "morko.moziero@mailmail.com");
    static final Person P3 = new Person("gaetan", "zwick", "gaetan.zwick@heig-vd.ch");
    static final Person P4 = new Person("marco", "maziero", "marco.maziero@heig-vd.ch");

    static final Message M1 = new Message("totally not a prank", "Q: What do you call a busy waiter?\n\nA: A server.");
    static final Message M2 = new Message("important", "REDS is an evil secret organisation whose goal is to control the world");

    @BeforeAll
    public static void initTmpConfig() throws IOException {
        FileWriter fw;
        File folder = new File(TEMP_CONFIG_FOLDER_PATH);
        if (folder.mkdirs()) {
            File config = new File(TEMP_CONFIG_FOLDER_PATH + "config.properties");
            fw = new FileWriter(config, StandardCharsets.UTF_8);
            fw.write("smtpServerAddress=" + SMTP_ADDRESS + "\n");
            fw.write("smtpServerPort=" + SMTP_PORT + "\n");
            fw.write("numberOfGroups=" + NB_GROUPS + "\n");
            fw.write("witnessToCC=" + CCs.get(0) + "," + CCs.get(1) + "\n");
            fw.flush();
            fw.close();
            File emails = new File(TEMP_CONFIG_FOLDER_PATH + "emails.utf8");
            fw = new FileWriter(emails, StandardCharsets.UTF_8);
            fw.write(P1.getAddress() + "\n");
            fw.write(P2.getAddress() + "\n");
            fw.write(P3.getAddress() + "\n");
            fw.write(P4.getAddress() + "\n");
            fw.flush();
            fw.close();
            File messages = new File(TEMP_CONFIG_FOLDER_PATH + "/messages.utf8");
            fw = new FileWriter(messages, StandardCharsets.UTF_8);
            fw.write("Subject:" + M1.getSubject() + "\n" + M1.getContent() + "\n====\n");
            fw.write("Subject:" + M2.getSubject() + "\n" + M2.getContent() + "\n====\n");
            fw.flush();
            fw.close();
        }
    }

    @Test
    public void configurationManagerShouldHaveTheCorrectSmtpServerAddressSmtpServerPortNumberOfGroupWitnessesToCCFromTheConfigPropertiesFile() {
        try {
            ConfigurationManager configManager = new ConfigurationManager(TEMP_CONFIG_FOLDER_PATH);

            Assertions.assertEquals(SMTP_ADDRESS, configManager.getSmtpServerAddress());
            Assertions.assertEquals(SMTP_PORT, configManager.getSmtpServerPort());
            Assertions.assertEquals(NB_GROUPS, configManager.getNumberOfGroup());
            Assertions.assertEquals(CCs, configManager.getWitnessesToCC());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getVictimsShouldReturnTheCorrectListOfVictims() throws IOException {
        ConfigurationManager configManager = new ConfigurationManager(TEMP_CONFIG_FOLDER_PATH);
        List<Person> victims = configManager.getVictims();
        List<Person> victimsExpected = new ArrayList<>(Arrays.asList(P1, P2, P3, P4));

        for (int i = 0; i < victims.size(); i++) {
            Assertions.assertEquals(victimsExpected.get(i).getFirstName(), victims.get(i).getFirstName());
            Assertions.assertEquals(victimsExpected.get(i).getLastName(), victims.get(i).getLastName());
            Assertions.assertEquals(victimsExpected.get(i).getAddress(), victims.get(i).getAddress());
        }
    }

    @Test
    public void getMessagesShouldReturnTheCorrectListOfMessages() throws IOException {
        ConfigurationManager configManager = new ConfigurationManager(TEMP_CONFIG_FOLDER_PATH);
        List<Message> messages = configManager.getMessages();
        List<Message> messagesExpected = new ArrayList<>(Arrays.asList(M1, M2));

        for (int i = 0; i < messages.size(); i++) {
            Assertions.assertEquals(messagesExpected.get(i).getSubject(), messages.get(i).getSubject());
            Assertions.assertEquals(messagesExpected.get(i).getContent() + "\n", messages.get(i).getContent());
        }
    }

    @AfterAll
    public static void deleteTmpConfig() throws IOException {
        File directoryToBeDeleted = new File(TEMP_CONFIG_FOLDER_PATH);
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                file.delete();
            }
        }
        directoryToBeDeleted.delete();
    }

}
