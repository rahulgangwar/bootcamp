package com.example.designPatterns.structural;

public class DecoratorPattern {
  public static void main(String[] args) {
    EmailSender.send(new SimpleEmail("Rahul", "abc.impressico.com"));
  }

  private interface Email {
    String getContent();

    String getTo();
  }

  private static class SimpleEmail implements Email {
    private final String content;
    private final String to;

    SimpleEmail(String content, String to) {
      this.content = content;
      this.to = to;
    }

    public String getContent() {
      return content;
    }

    public String getTo() {
      return to;
    }
  }

  private static class ExternalEmailDecorator implements Email {
    Email email;

    ExternalEmailDecorator(Email email) {
      this.email = email;
    }

    public String getContent() {
      return email.getContent() + " \n Disclaimer: message";
    }

    @Override
    public String getTo() {
      return email.getTo();
    }
  }

  private static class EmailSender {
    public static void send(Email email) {
      if (email.getTo().contains("impressico.com")) {
        System.out.println("Sent: " + new ExternalEmailDecorator(email).getContent());
      }
    }
  }
}
