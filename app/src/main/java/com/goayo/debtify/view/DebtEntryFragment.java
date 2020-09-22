package com.goayo.debtify.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.goayo.debtify.R;

/**
 * @author Gabriel Brattgård, Yenan Wang
 * @date 2020-09-22
 * <p>
 * The entry fragment to DebtActivity
 */
public class DebtEntryFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NavController navController = NavHostFragment.findNavController(this);
        try {
            // get the string representing the choice between add or settle debt
            String DEBT_CREATION = getActivity().getIntent().getStringExtra("DEBT_CREATION");

            assert DEBT_CREATION != null;
            if (DEBT_CREATION.equals("ADD_DEBT")) {
                // navigate to addDebtFragment
                navController.navigate(R.id.action_debtEntryFragment_to_addDebtFragment);
            } else if (DEBT_CREATION.equals("SETTLE_DEBT")) {
                // navigate to settleDebtFragment
                navController.navigate(R.id.action_debtEntryFragment_to_settleDebtFragment);
            } else {
                // TODO add error message
                getActivity().finish();
            }
        } catch (NullPointerException e) {
            // TODO add error message
            getActivity().finish();
        }
    }
}