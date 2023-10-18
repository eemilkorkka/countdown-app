package com.example.countdown;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.text.format.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class    TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstance) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker timePicker, int i, int i1) {

        String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", i, i1);

        EditText timeText = getActivity().findViewById(R.id.editTextTime);
        timeText.setText(selectedTime);

    }
}
