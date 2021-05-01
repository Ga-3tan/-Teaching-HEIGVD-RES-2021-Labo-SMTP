import config.ConfigurationManager;
import config.IConfigurationManager;
import model.prank.Prank;
import model.prank.PrankGenerator;
import smtp.ISmtpClient;
import smtp.SmtpClient;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Pranker {
    private static final Logger LOG = Logger.getLogger(Pranker.class.getName());
    private ISmtpClient smtpClient;
    private IConfigurationManager configManager;
    private PrankGenerator prankGenerator;

    public Pranker() {
        configManager = new ConfigurationManager();
        smtpClient = new SmtpClient(configManager.getSmtpServerAddress(), configManager.getSmtpServerPort());
        prankGenerator = new PrankGenerator(configManager);
        List<Prank> pranks = prankGenerator.generatePranks();

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
