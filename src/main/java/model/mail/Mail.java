package model.mail;

import lombok.Getter;
import model.prank.Group;

import java.util.List;

@Getter
public class Mail {
    private final String from;
    private final List<String> to;
    private final List<String> CCs;
    private final Message message;

    public Mail(String sender, List<String> recipients, List<String> CCs, Message message) {
        this.from = sender;
        this.to = recipients;
        this.CCs = CCs;
        this.message = message;
    }
}