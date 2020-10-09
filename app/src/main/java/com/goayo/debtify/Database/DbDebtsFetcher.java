package com.goayo.debtify.Database;

import com.google.gson.Gson;

import java.math.BigDecimal;
import java.util.Set;

public class DbDebtsFetcher {
    public boolean addDebt(String groupID, String lender, Set<String> borrowers, BigDecimal amount, String description) {
        DbObject.Debt[] debts = new DbObject.Debt[borrowers.size()];
        int i = 0;

        for(String borrower : borrowers){
            debts[i] = new DbObject.Debt(lender, borrower, amount.divide(new BigDecimal(borrowers.size())).toString(), "", new DbObject.Payment[0]);
            i++;
        }

        for(DbObject.Debt debt : debts){
            DbObject.DebtPost debtPost = new DbObject.DebtPost(groupID, debt);
            Gson gson = new Gson();
            String data = gson.toJson(debtPost);
            if (!(DatabaseConnector.getInstance().postData(data, "debts"))){
                return false;
            }
        }
        return true;
    }
}
