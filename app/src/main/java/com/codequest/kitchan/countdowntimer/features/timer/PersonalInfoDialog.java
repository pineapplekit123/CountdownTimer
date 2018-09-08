package com.codequest.kitchan.countdowntimer.features.timer;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.codequest.kitchan.countdowntimer.R;

public class PersonalInfoDialog  {

    public void showDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialogue_personal_info);

        TextView tvName = dialog.findViewById(R.id.tvname);
        TextView tvGender = dialog.findViewById(R.id.tvgender);
        TextView tvMobile = dialog.findViewById(R.id.tvmobile);
        TextView tvEmail = dialog.findViewById(R.id.tvemail);
        TextView tvDob = dialog.findViewById(R.id.tvdob);

        tvName.setText(activity.getResources().getString(R.string.pInfo_name));
        tvGender.setText(activity.getResources().getString(R.string.pInfo_gender));
        tvMobile.setText(activity.getResources().getString(R.string.pInfo_mobile));
        tvEmail.setText(activity.getResources().getString(R.string.pInfo_email));
        tvDob.setText(activity.getResources().getString(R.string.pInfo_dob));

        Button dialogButton = dialog.findViewById(R.id.btnOK);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

}
