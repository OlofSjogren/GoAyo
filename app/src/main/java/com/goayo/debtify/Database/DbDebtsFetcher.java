package com.goayo.debtify.Database;

import com.goayo.debtify.model.UserNotFoundException;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Set;

public class DbDebtsFetcher {
    public boolean addDebt(String groupID, String lender, Map<String, String> borrowers, BigDecimal amount, String description) {
        DbObject.Debt[] debts = new DbObject.Debt[borrowers.size()];
        int i = 0;

        DbUserFetcher fetcher = new DbUserFetcher();
        String lenderJson = fetcher.fetchUserFromPhoneNumber(lender);


        for(Map.Entry<String, String> entry : borrowers.entrySet()){
            String borrowerJson = fetcher.fetchUserFromPhoneNumber(entry.getKey());
            MathContext mc = new MathContext(2, RoundingMode.HALF_UP);
            debts[i] = new DbObject.Debt(new Gson().fromJson(lenderJson, DbObject.User.class), new Gson().fromJson(borrowerJson, DbObject.User.class), amount.divide(new BigDecimal(borrowers.size()), mc).toString(), entry.getValue(), new DbObject.Payment[0], description);
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
