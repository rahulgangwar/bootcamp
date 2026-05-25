package com.example.scenarios.SplitWise.splittype;

import com.example.scenarios.SplitWise.model.User;

public class EqualSplit extends Split {
    public EqualSplit(User user) {
        super(user);
    }

    @Override
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
