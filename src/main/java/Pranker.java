import config.ConfigurationManager;
import config.IConfigurationManager;
import model.prank.Prank;
import model.prank.PrankGenerator;
import smtp.ISmtpClient;
import smtp.SmtpClient;

import java.util.List;

public class Pranker {
    private ISmtpClient smtpClient;
    private IConfigurationManager configManager;
    private PrankGenerator prankGenerator;

    public Pranker() {
        configManager = new ConfigurationManager();
        smtpClient = new SmtpClient(configManager.getSmtpServerAddress(), configManager.getSmtpServerPort());
        prankGenerator = new PrankGenerator(configManager);
        List<Prank> pranks = prankGenerator.generatePranks();

        smtpClient.connect();
        for (Prank p : pranks)
            smtpClient.sendMail(p.generateMail());
    }

    public static void main(String[] args) {
        new Pranker();
    }
}
