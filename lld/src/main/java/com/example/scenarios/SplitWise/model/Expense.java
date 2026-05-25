package com.example.scenarios.SplitWise.model;

import com.example.scenarios.SplitWise.splittype.Split;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Expense {
    private final String id;
    private final double amount;
    private final String description;
    private final User paidBy;
    private final List<Split> splits;

    public Expense(String id, double amount, String description, User paidBy) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.paidBy = paidBy;
        this.splits = new ArrayList<>();
    }

    public void addSplit(Split split) {
        splits.add(split);
    }

}
