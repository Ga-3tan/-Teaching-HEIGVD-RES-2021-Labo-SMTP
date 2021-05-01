import config.ConfigurationManager;
import config.IConfigurationManager;
import model.prank.Prank;
import model.prank.PrankGenerator;
import smtp.ISmtpClient;
import smtp.SmtpClient;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Pranker {
    private static final Logger LOG = Logger.getLogger(Pranker.class.getName());
    private ISmtpClient smtpClient;

    public Pranker() {
        // Parses the config files and opens
        List<Prank> pranks = new LinkedList<>();
        try {
            IConfigurationManager configManager = new ConfigurationManager();
            PrankGenerator prankGenerator = new PrankGenerator(configManager);
            pranks = prankGenerator.generatePranks();
            smtpClient = new SmtpClient(configManager.getSmtpServerAddress(), configManager.getSmtpServerPort());
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error when parsing config data, there should be a folder \"configuration\" with the following files inside : \"config.properties\", \"emails.utf8\", \"messages.utf8\":\n\t" + e.getMessage(), e);
        }

        for (Prank p : pranks) {
            try {
                smtpClient.sendMail(p.generateMail());
            } catch (IOException e) {
                LOG.log(Level.SEVERE, "Mail was not sent due to an IO exception:\n\t" + e.getMessage(), e);
            } catch (RuntimeException e) {
                LOG.log(Level.SEVERE, "Mail was not sent due to a SMTP unexpected response:\n\t" + e.getMessage(), e);
            }
        }

    }

    public static void main(String[] args) {
        new Pranker();
    }
}
