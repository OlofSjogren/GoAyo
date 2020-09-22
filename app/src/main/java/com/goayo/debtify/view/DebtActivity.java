package com.goayo.debtify.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.ActivityDebtBinding;

/**
 * @author Gabriel Brattgård, Yenan Wang
 * @date 2020-09-22
 * <p>
 * Activity for creating or settling debt
 */
public class DebtActivity extends AppCompatActivity {

    private ActivityDebtBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_debt);
        navController = Navigation.findNavController(this, R.id.debtNavHostFragment);

        String action = getIntent().getStringExtra("DEBT_CREATE");
        try {
            if (action.equals("ADD_DEBT")) {
                navController.setGraph(R.navigation.add_debt_navigation_graph);
            } else if (action.equals("SETTLE_DEBT")) {
                navController.setGraph(R.navigation.settle_debt_navigation_graph);
            } else {
                finish();
                // TODO send error message
            }
        } catch (NullPointerException e) {
            finish();
            // TODO send error message
        }

        this.getSupportActionBar().hide();
    }
}