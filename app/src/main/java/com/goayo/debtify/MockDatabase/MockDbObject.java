package com.goayo.debtify.MockDatabase;

class MockDbObject {
    static class Group {
        public Group(String name, String date, String groupId, User[] members, Debt[] debts) {
            this.name = name;
            this.date = date;
            this.id = groupId;
            this.members = members;
            this.debts = debts;
        }

        String name;
        String date;
        String id;
        User[] members;
        Debt[] debts;
    }

    static class User {

        public User(String name, String phonenumber, String password, String[] contacts) {
            this.name = name;
            this.phonenumber = phonenumber;
            this.password = password;
            this.contacts = contacts;
        }

        String name;
        String phonenumber;
        String password;
        String[] contacts;

    }

    static class DebtPost{
        public DebtPost(String groupId, Debt debt) {
            this.groupId = groupId;
            this.debt = debt;
        }

        String groupId;
        Debt debt;
    }

    static class Debt {
        public Debt(User lender, User borrower, String owed, String debtId, Payment[] payments, String description) {
            this.lender = lender;
            this.borrower = borrower;
            this.owed = owed;
            this.id = debtId;
            this.payments = payments;
            this.description = description;
        }


        String description;
        User lender;
        User borrower;
        String owed;
        String id;
        Payment [] payments;
    }

    static class PaymentPost{
        public PaymentPost(String groupId, String debtId, Payment payment) {
            this.groupId = groupId;
            this.debtId = debtId;
            this.payment = payment;
        }

        String groupId;
        String debtId;
        Payment payment;
    }

    static class Payment {
        public Payment(String amount, String paymentId) {
            this.amount = amount;
            this.id = paymentId;
        }

        String amount;
        String id;
    }

    public static class ContactPost {
        String contactPhoneNumber;
        String userPhoneNumber;

        public ContactPost(String userPhoneNumber, String contactToBeAdded) {
            this.userPhoneNumber = userPhoneNumber;
            this.contactPhoneNumber = contactToBeAdded;
        }
    }

    public static class GroupMemberPost {

        User member;
        String groupId;

        public GroupMemberPost(String groupID, User user) {
            this.member = user;
            groupId = groupID;
        }
    }
    public static class ContactsJsonObject {
        public ContactsJsonObject(User[] contacts) {
            this.contacts = contacts;
        }
        User[] contacts;
    }

    static class GroupsArrayJsonObject {
        Group[] groupJsonObjects;

        public GroupsArrayJsonObject(Group[] groupJsonObjects) {
            this.groupJsonObjects = groupJsonObjects;
        }
    }

}
