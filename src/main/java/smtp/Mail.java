package smtp;

import lombok.Getter;

import java.util.List;

@Getter
public class Mail {
    private final String from;
    private final List<String> to;
    private final List<String> CCs;
    private final String subject;
    private final String content;

    public Mail(String sender, List<String> recipients, List<String> CCs, String subject, String content) {
        this.from = sender;
        this.to = recipients;
        this.CCs = CCs;
        this.subject = subject;
        this.content = content;
    }
}