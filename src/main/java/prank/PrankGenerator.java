package prank;

import config.IConfigurationManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class PrankGenerator {
    private static final Logger LOG = Logger.getLogger(PrankGenerator.class.getName());
    private final IConfigurationManager configManager;

    public PrankGenerator(IConfigurationManager configurationManager) {
        this.configManager = configurationManager;
    }

    public List<Prank> generatePranks() throws IOException, IllegalArgumentException {
        List<Prank> output = new ArrayList<>();

        // Retrives the list of messages
        List<Message> messages = configManager.getMessages();
        int msgIndex = 0;

        // Checks if messages are empty
        if (messages.isEmpty()) throw new IllegalArgumentException("No messages in message.utf8 file");

        // Retrieves the groups data
        List<Person> victims = configManager.getVictims();
        int nbGroups = configManager.getNumberOfGroup();
        int nbVictims = victims.size();

        // Checks if the groups can be made
        if (nbVictims < 3 || nbGroups == 0)
            throw new IllegalArgumentException("Number of victims and groups must be above 0");

        // Checks nbGroups and victims compatibility
        if (nbVictims / nbGroups < 3) {
            nbGroups = nbVictims / 3;

            LOG.warning("There are not enough victims to generate the desired number of groups." +
                    "We can only generate a max of " + nbGroups + " groups to have at least 3 or less victims per group.");
        }

        // Generates the groups
        List<Group> groups = generateGroups(victims, nbGroups);

        // Generates the pranks per group
        for (Group g : groups) {
            // Gets the sender
            List<Person> members = g.getMembers();
            Collections.shuffle(members);
            Person sender = members.remove(0);
            Prank newPrank = new Prank(sender, members, configManager.getWitnessesToCC(), messages.get(msgIndex));
            msgIndex = (msgIndex + 1) % messages.size();
            output.add(newPrank);
        }
        return output;
    }

    private static List<Group> generateGroups(List<Person> persons, int nbGroups) {
        // Init groups
        List<Group> groups = new ArrayList<>();
        for (int i = 0; i < nbGroups; ++i)
            groups.add(new Group());

        // Fills the groups
        Collections.shuffle(persons);
        int nbPersons = persons.size();
        for (int i = 0; i < nbPersons; ++i)
            groups.get(i % nbGroups).addMember(persons.remove(0));

        return groups;
    }
}