package config;

import prank.Message;
import prank.Person;
import java.io.IOException;
import java.util.List;

/**
 * Represents a configuration manager that
 * interacts with the config files
 *
 * Name : IConfigurationManager
 * File : IConfigurationManager.java
 * @author Gaétan Zwick
 * @author Marco Maziero
 * @version 1.0
 * @since 01.05.2021
 */
public interface IConfigurationManager {
    /**
     * Retrieves the config SMTP address
     * @return The retrieved address
     */
    String getSmtpServerAddress();

    /**
     * Retrieves the config SMTP port
     * @return The retrieved port
     */
    int getSmtpServerPort();

    /**
     * Retreves the number of groups wanted
     * @return The number of groups
     */
    int getNumberOfGroup();

    /**
     * Reads the emails file
     * @return A list of victims mail addresses
     * @throws IOException If the file is not found or cannot be read
     */
    List<Person> getVictims() throws IOException;

    /**
     * Reads the messages file
     * @return A list of parsed mail messages
     * @throws IOException It the file is not found or cannot be read
     */
    List<Message> getMessages() throws IOException;

    /**
     * Retrieves the witnesses in the config file
     * @return The list of witnesses mail addresses
     */
    List<String> getWitnessesToCC();
}
