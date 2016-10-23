package vn.me.simplenewsstand.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.me.simplenewsstand.R;
import vn.me.simplenewsstand.model.SearchRequest;
import vn.me.simplenewsstand.utils.Constants;
import vn.me.simplenewsstand.utils.DisplayUtil;

/**
 * Created by taq on 23/10/2016.
 */

public class FilterActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private SearchRequest mSearchRequest;

    @BindView(R.id.etDate)
    EditText etDate;

    @BindView(R.id.btnSave)
    Button btnSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_filter);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        mSearchRequest = (SearchRequest) intent.getParcelableExtra(Constants.SEARCH_REQUEST);
        if (mSearchRequest == null) {
            mSearchRequest = new SearchRequest();
        }

        setUpViews();
    }

    private void setUpViews() {
        etDate.setText(DisplayUtil.getFormattedDate(mSearchRequest.getBeginTime()));
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment fragment = new DatePickerFragment();
                Bundle args = new Bundle();
                args.putLong(Constants.TIME, mSearchRequest.getBeginTime());
                fragment.setArguments(args);
                fragment.show(getSupportFragmentManager(), "date_picker");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(Constants.SEARCH_REQUEST, mSearchRequest);
                setResult(RESULT_OK, data);
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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        long time = 0;
        Calendar c = Calendar.getInstance();
        if (c.get(Calendar.YEAR) != year || c.get(Calendar.MONTH) != month || c.get(Calendar.DAY_OF_MONTH) != dayOfMonth) {
            c.set(year, month, dayOfMonth);
            time = c.getTimeInMillis();
        }
        mSearchRequest.setBeginTime(time);
        etDate.setText(DisplayUtil.getFormattedDate(time));
    }
}
