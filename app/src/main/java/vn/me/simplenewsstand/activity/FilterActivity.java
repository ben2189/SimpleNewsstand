package vn.me.simplenewsstand.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.me.simplenewsstand.R;
import vn.me.simplenewsstand.model.SearchRequest;
import vn.me.simplenewsstand.utils.Constants;

/**
 * Created by taq on 23/10/2016.
 */

public class FilterActivity extends AppCompatActivity {

    private SearchRequest mSearchRequest;

    @BindView(R.id.spDate)
    Spinner spDate;

    @BindView(R.id.btnSave)
    Button btnSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_filter);
        ButterKnife.bind(this);

        mSearchRequest = (SearchRequest) getIntent().getSerializableExtra(Constants.SEARCH_REQUEST);
        if (mSearchRequest == null) {
            mSearchRequest = new SearchRequest();
        }
        
        setUpViews();
    }

    private void setUpViews() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setSpinnerToValue(Spinner spinner, String value) {
        int index = 0;
        SpinnerAdapter adapter = spinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (adapter.getItem(i).equals(value)) {
                index = i;
                break;
            }
        }
        spinner.setSelection(index);
    }
}
