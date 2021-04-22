package model.prank;

import config.IConfigurationManager;
import model.mail.Person;

import java.util.List;

public class PrankGenerator {
    private final IConfigurationManager configManager;

    public PrankGenerator(IConfigurationManager configurationManager) {
        this.configManager = configurationManager;
    }

    public List<Prank> generatePranks() {
        // TODO
    }

    private List<Group> generateGroups(List<Person> persons, int nbGroups) {
        // TODO
    }
}
