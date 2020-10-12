package com.goayo.debtify.Database;

import com.goayo.debtify.model.IDebtSplitStrategy;
import com.goayo.debtify.Tuple;
import com.goayo.debtify.modelaccess.IUserData;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.util.Map;

 class DbDebtsFetcher {
    public boolean addDebt(String groupID, String lender, Map<IUserData, String> borrowers, BigDecimal amount, String description, IDebtSplitStrategy splitStrategy) {
        DbObject.Debt[] debts = new DbObject.Debt[borrowers.size()];
        int i = 0;

        Map<IUserData, Tuple<BigDecimal, String>> map = splitStrategy.splitDebt(borrowers, amount);
        DbUserFetcher fetcher = new DbUserFetcher();
        String lenderJson = fetcher.fetchUserFromPhoneNumber(lender);


        for(Map.Entry<IUserData, Tuple<BigDecimal, String>> entry : map.entrySet()){
            String borrowerJson = fetcher.fetchUserFromPhoneNumber(entry.getKey().getPhoneNumber());
            debts[i] = new DbObject.Debt(new Gson().fromJson(lenderJson, DbObject.User.class), new Gson().fromJson(borrowerJson, DbObject.User.class), entry.getValue().getFirst().toString(), entry.getValue().getSecond(), new DbObject.Payment[0], description);
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
