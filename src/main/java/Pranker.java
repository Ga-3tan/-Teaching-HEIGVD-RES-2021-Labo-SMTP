import config.ConfigurationManager;
import config.IConfigurationManager;
import smtp.ISmtpClient;
import smtp.SmtpClient;

public class Pranker {
    private ISmtpClient smtpClient;
    private IConfigurationManager configManager;

    public void Pranker() {
        configManager = new ConfigurationManager();
        smtpClient = new SmtpClient(configManager.getSmtpServerAddress(), configManager.getSmtpServerPort());

        // TODO : Generate mails and send them
    }

    public static void main(String[] args) {
        new Pranker();
    }
}
