package com.goayo.debtify.database;

 class DbObject {
    static class Group {
        public Group(String name, String date, String groupId, User[] members, Debt[] debts) {
            this.name = name;
            this.date = date;
            this.id = groupId;
            this.members = members;
            this.debts = debts;
        }

        final String name;
        final String date;
        final String id;
        final User[] members;
        final Debt[] debts;
    }

    static class User {

        public User(String name, String phonenumber, String password, String[] contacts) {
            this.name = name;
            this.phonenumber = phonenumber;
            this.password = password;
            this.contacts = contacts;
        }

        final String name;
        final String phonenumber;
        final String password;
        final String[] contacts;

    }

    static class DebtPost{
        public DebtPost(String groupId, Debt debt) {
            this.groupId = groupId;
            this.debt = debt;
        }

        final String groupId;
        final Debt debt;
    }

    static class Debt {
        public Debt(User lender, User borrower, String owed, String debtId, Payment[] payments, String description, String date) {
            this.lender = lender;
            this.date = date;
            this.borrower = borrower;
            this.owed = owed;
            this.id = debtId;
            this.payments = payments;
            this.description = description;
        }

        final String date;
        final String description;
        final User lender;
        final User borrower;
        final String owed;
        final String id;
        final Payment [] payments;
    }

    static class PaymentPost{
        public PaymentPost(String groupId, String debtId, Payment payment) {
            this.groupId = groupId;
            this.debtId = debtId;
            this.payment = payment;
        }

        final String groupId;
        final String debtId;
        final Payment payment;
    }

    static class Payment {
        public Payment(String amount, String paymentId, String date) {
            this.amount = amount;
            this.id = paymentId;
            this.date = date;
        }


        final String date;
        final String amount;
        final String id;
    }

    public static class ContactPost {
        final String contactPhoneNumber;
        final String userPhoneNumber;

        public ContactPost(String userPhoneNumber, String contactToBeAdded) {
            this.userPhoneNumber = userPhoneNumber;
            this.contactPhoneNumber = contactToBeAdded;
        }
    }

    public static class GroupMemberPost {

        final User member;
        final String groupId;

        public GroupMemberPost(String groupID, User user) {
            this.member = user;
            groupId = groupID;
        }
    }
    public static class ContactsJsonObject {
        public ContactsJsonObject(User[] contacts) {
            this.contacts = contacts;
        }
        final User[] contacts;
    }

    static class GroupsArrayJsonObject {
        final Group[] groupJsonObjects;

        public GroupsArrayJsonObject(Group[] groupJsonObjects) {
            this.groupJsonObjects = groupJsonObjects;
        }
    }

}
