package com.example.designPatterns.behavioral;

/*Chain of responsibility pattern is used to achieve loose coupling in software design
 * where a request from client is passed to a chain of objects to process them.
 * Later, the object in the chain will decide themselves who will be processing the request
 * and whether the request is required to be sent to the next object in the chain or not.**/
public class ChainOfResponsibility {
  public static void main(String[] args) {
    NumberProcessor numberProcessor =
        new PositiveNumberProcessor(new NegativeNumberProcessor(null));

    numberProcessor.process(2);
    numberProcessor.process(-1);
  }

  private interface NumberProcessor {
    void process(int number);
  }

  private static class NegativeNumberProcessor implements NumberProcessor {
    private NumberProcessor nextInChain;

    NegativeNumberProcessor(NumberProcessor nextInChain) {
      this.nextInChain = nextInChain;
    }

    public void process(int number) {
      if (number < 0) {
        System.out.println("Negative number processed");
      } else {
        this.nextInChain.process(number);
      }
    }
  }

  private static class PositiveNumberProcessor implements NumberProcessor {
    private NumberProcessor nextInChain;

    PositiveNumberProcessor(NumberProcessor nextInChain) {
      this.nextInChain = nextInChain;
    }

    public void process(int number) {
      if (number >= 0) {
        System.out.println("Positive number processed");
      } else {
        this.nextInChain.process(number);
      }
    }
  }
}
