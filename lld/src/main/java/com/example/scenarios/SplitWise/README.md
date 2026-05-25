# Designing Splitwise

## Requirements

1. The system should allow users to create accounts and manage their profile information.
2. Users should be able to create groups and add other users to the groups.
3. Users should be able to add expenses within a group, specifying the amount, description, and participants.
4. The system should automatically split the expenses among the participants based on their share.
5. Users should be able to view their individual balances with other users and settle up the balances.
6. The system should support different split methods, such as equal split, percentage split, and exact amounts.
7. Users should be able to view their transaction history and group expenses.
8. The system should handle concurrent transactions and ensure data consistency.

## Class Diagram

```mermaid
classDiagram
    class SplitwiseService {
        -SplitwiseService instance
        -Map~String, User~ users
        -Map~String, Group~ groups
        +getInstance() : SplitwiseService
        +addUser(User)
        +addGroup(Group)
        +addExpense(String, Expense)
        +settleBalance(String, String)
    }

    class User {
        -String id
        -String name
        -String email
        -Map~String, Double~ balances
        +User(String, String, String)
        +getId() : String
        +getName() : String
        +getEmail() : String
        +getBalances() : Map~String, Double~
    }

    class Group {
        -String id
        -String name
        -List~User~ members
        -List~Expense~ expenses
        +Group(String, String)
        +addMember(User)
        +addExpense(Expense)
        +getId() : String
        +getName() : String
        +getMembers() : List~User~
        +getExpenses() : List~Expense~
    }

    class Expense {
        -String id
        -double amount
        -String description
        -User paidBy
        -List~Split~ splits
        +Expense(String, double, String, User)
        +addSplit(Split)
        +getId() : String
        +getAmount() : double
        +getDescription() : String
        +getPaidBy() : User
        +getSplits() : List~Split~
    }

    class Split {
        <<abstract>>
        -User user
        -double amount
        +Split(User)
        +getUser() : User
        +getAmount() : double
        +setAmount(double)
    }

    class EqualSplit {
        +EqualSplit(User)
    }

    class PercentSplit {
        -double percent
        +PercentSplit(User, double)
        +getPercent() : double
    }

    class ExactSplit {
        +ExactSplit(User, double)
    }

    class Transaction {
        -String id
        -User sender
        -User receiver
        -double amount
        +Transaction(String, User, User, double)
    }

    SplitwiseService --> User
    SplitwiseService --> Group
    Group --> User
    Group --> Expense
    Expense --> Split
    Split <|-- EqualSplit
    Split <|-- PercentSplit
    Split <|-- ExactSplit
    SplitwiseService --> Transaction