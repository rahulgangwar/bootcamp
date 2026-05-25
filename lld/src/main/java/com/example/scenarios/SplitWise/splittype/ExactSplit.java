package com.example.scenarios.SplitWise.splittype;

import com.example.scenarios.SplitWise.model.User;

public class ExactSplit extends Split {
    public ExactSplit(User user, double amount) {
        super(user);
        this.amount = amount;
    }

    @Override
    public double getAmount() {
        return amount;
    }
}
