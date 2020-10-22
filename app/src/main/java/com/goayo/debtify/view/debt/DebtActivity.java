package com.goayo.debtify.view.debt;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.goayo.debtify.R;

/**
 * @author Gabriel Brattgård, Yenan Wang
 * @date 2020-09-22
 * <p>
 * Activity for creating or settling debt
 * <p>
 * 2020-09-28 Modified by Yenan Wang: Removed method call on supportBar since it doesn't exist anymore
 */
public class DebtActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataBindingUtil.setContentView(this, R.layout.activity_debt);
        NavController navController = Navigation.findNavController(this, R.id.debtNavHostFragment);

        String action = getIntent().getStringExtra("DEBT_CREATE");
        try {
            if (action.equals("ADD_DEBT")) {
                navController.setGraph(R.navigation.add_debt_navigation_graph);
            } else if (action.equals("SETTLE_DEBT")) {
                navController.setGraph(R.navigation.settle_debt_navigation_graph);
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}