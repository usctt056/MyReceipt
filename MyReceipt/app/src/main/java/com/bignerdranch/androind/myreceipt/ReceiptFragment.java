package com.bignerdranch.androind.myreceipt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Date;
import java.util.UUID;

/**
 * Created by t_t056 on 30/10/2018.
 */

public class ReceiptFragment extends Fragment {

    private static final String ARG_RECEIPT_ID = "receipt_id";
    private static final String DIALOG_DATE = "DialogDate";

    private static final int REQUEST_DATE = 0;

    private Receipt mReceipt;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSeenCheckBox;

    public static ReceiptFragment newInstance(UUID receiptId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECEIPT_ID, receiptId);

        ReceiptFragment fragment = new ReceiptFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID receiptId = (UUID) getArguments().getSerializable(ARG_RECEIPT_ID);
        mReceipt = ReceiptLab.get(getActivity()).getReceipt(receiptId);
    }

    @Override
    public void onPause() {
        super.onPause();
        ReceiptLab.get(getActivity())
                .updateReceipt(mReceipt);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_receipt, container, false);

        mTitleField = (EditText) v.findViewById(R.id.receipt_title);
        mTitleField.setText(mReceipt.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
// This space intentionally left blank
            }
            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                mReceipt.setTitle(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
// This one too
            }
        });

        mDateButton = (Button) v.findViewById(R.id.receipt_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mReceipt.getDate());
                dialog.setTargetFragment(ReceiptFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mSeenCheckBox = (CheckBox)v.findViewById(R.id.receipt_seen);
        mSeenCheckBox.setChecked(mReceipt.isSeen());
        mSeenCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                mReceipt.setSeen(isChecked);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mReceipt.setDate(date);
            updateDate();
        }
    }

    private void updateDate() {
        mDateButton.setText(mReceipt.getDate().toString());
    }
}
