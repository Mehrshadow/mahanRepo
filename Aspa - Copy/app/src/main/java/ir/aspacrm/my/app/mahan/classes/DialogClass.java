package ir.aspacrm.my.app.mahan.classes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahan.G;
import ir.aspacrm.my.app.mahan.R;
import ir.aspacrm.my.app.mahan.adapter.AdapterSpinnerPoll;
import ir.aspacrm.my.app.mahan.component.PersianTextViewNormal;
import ir.aspacrm.my.app.mahan.events.EventOnCanceledDialogUpdatingApplication;
import ir.aspacrm.my.app.mahan.events.EventOnCheckGetPollRequest;
import ir.aspacrm.my.app.mahan.events.EventOnClickedEndFeshfeshe;
import ir.aspacrm.my.app.mahan.events.EventOnClickedLogoutButton;
import ir.aspacrm.my.app.mahan.events.EventOnClickedStartFactor;
import ir.aspacrm.my.app.mahan.events.EventOnSendPollRequest;
import ir.aspacrm.my.app.mahan.events.EventOnShowDialogUpdatingApplicationRequest;
import ir.aspacrm.my.app.mahan.gson.FactorDetailResponse;
import ir.aspacrm.my.app.mahan.gson.GetIspInfoResponse;
import ir.aspacrm.my.app.mahan.gson.GetPollResponse;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Microsoft on 3/9/2016.
 */
public class DialogClass {
    Dialog dialog;
    Dialog dialogPoll;
    Dialog dlgWaiting;
    Dialog dialogPayMessage;
    AdapterSpinnerPoll adapterSpinnerPoll;
    Dialog dlgWaitingWithBackground;
    protected LocationManager locationManager;
    boolean isGPSEnabled = false;


    public void DialogWaiting() {
        dlgWaiting = new Dialog(G.currentActivity, android.R.style.Theme_Black_NoTitleBar);
        dlgWaiting.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dlgWaiting.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dlgWaiting.setContentView(R.layout.dialog_waiting);
        dlgWaiting.setCancelable(false);
        dlgWaiting.show();
    }

    public void DialogWaitingClose() {
        if (dlgWaiting != null)
            dlgWaiting.dismiss();
    }

    public void showExitDialog() {
        final Dialog dialog = new Dialog(G.currentActivity, R.style.DialogSlideAnim);
        dialog.setContentView(R.layout.dialog_exit);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout layBtnPositive = (LinearLayout) dialog.findViewById(R.id.layBtnOk);
        LinearLayout layBtnNegative = (LinearLayout) dialog.findViewById(R.id.layBtnCancel);
        PersianTextViewNormal imgCloseDialog = (PersianTextViewNormal) dialog.findViewById(R.id.imgCloseDialog);
        TextView txtBtnOk = (TextView) layBtnPositive.findViewById(R.id.txtValue);
        TextView txtBtnCancel = (TextView) layBtnNegative.findViewById(R.id.txtValue);

        txtBtnOk.setText("بلی");
        txtBtnCancel.setText("خیر");

        layBtnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventOnClickedLogoutButton());
                dialog.dismiss();
            }
        });
        imgCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        layBtnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showSendTicket() {
        final Dialog dialog = new Dialog(G.currentActivity, R.style.DialogSlideAnim);
        dialog.setContentView(R.layout.dialog_exit);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout layBtnPositive = (LinearLayout) dialog.findViewById(R.id.layBtnOk);
        LinearLayout layBtnNegative = (LinearLayout) dialog.findViewById(R.id.layBtnCancel);
        ImageView imgCloseDialog = (ImageView) dialog.findViewById(R.id.imgCloseDialog);
        TextView txtBtnOk = (TextView) layBtnPositive.findViewById(R.id.txtValue);
        TextView txtBtnCancel = (TextView) layBtnNegative.findViewById(R.id.txtValue);

        txtBtnOk.setText("بازگشت");
        txtBtnCancel.setText("ارسال تیکت جدید");

        layBtnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                G.currentActivity.finish();
            }
        });
        imgCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        layBtnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void showChangePasswordDialog() {
        final Dialog dialog = new Dialog(G.currentActivity, R.style.DialogSlideAnim);
        dialog.setContentView(R.layout.dialog_change_password);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        LinearLayout layBtnChange = (LinearLayout) dialog.findViewById(R.id.layBtnChange);
        ImageView imgCloseDialog = (ImageView) dialog.findViewById(R.id.imgCloseDialog);
        final EditText edtCurrentPassword = (EditText) dialog.findViewById(R.id.edtCurrentPassword);
        final EditText edtNewPassword = (EditText) dialog.findViewById(R.id.edtNewPassword);
        final EditText edtReNewPassword = (EditText) dialog.findViewById(R.id.edtReNewPassword);
        final TextView txtShowErrorMessage = (TextView) dialog.findViewById(R.id.txtShowErrorMessage);
        TextView txtBtnChange = (TextView) layBtnChange.findViewById(R.id.txtValue);
        txtBtnChange.setText("ثبت");
        layBtnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentPassword = edtCurrentPassword.getText().toString().trim();
                String newPassword = edtNewPassword.getText().toString().trim();
                String reNewPassword = edtReNewPassword.getText().toString().trim();
                if (!(newPassword.equals(reNewPassword))) {
                    txtShowErrorMessage.setText("رمز عبور جدید با تکرار آن مطابقت ندارد. لطفا رمز عبور جدید را مجددا وارد نمایید.");
                    return;
                }
                if (currentPassword.length() == 0) {
                    txtShowErrorMessage.setText("لطفا رمز عبور قبلی را وارد کنید.");
                    return;
                }
                if (newPassword.length() < 4) {
                    txtShowErrorMessage.setText("رمز عبور جدید باید حداقل 4 کاراکتر داشته باشد.");
                    return;
                } else {
                    WebService.sendChangePasswordRequest(currentPassword, newPassword);
                }
                dialog.dismiss();
            }
        });
        imgCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showMessageDialog(String title, String message) {
        final Dialog dialog = new Dialog(G.currentActivity, R.style.DialogSlideAnim);
        dialog.setContentView(R.layout.dialog_show_message);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView imgCloseDialog = (ImageView) dialog.findViewById(R.id.imgCloseDialog);
        TextView txtMessageTitle = (TextView) dialog.findViewById(R.id.txtMessageTitle);
        TextView txtMessageDescription = (TextView) dialog.findViewById(R.id.txtMessageDescription);
        txtMessageTitle.setText("" + title);
        txtMessageDescription.setText("" + message);
        imgCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showFactorDetail(FactorDetailResponse factor) {
        final Dialog dialog = new Dialog(G.currentActivity, R.style.DialogSlideAnim);
        dialog.setContentView(R.layout.l_factor_detail_item);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setLayout(U.getDeviceWidth() - 30, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView txtFactorDetailDes = (TextView) dialog.findViewById(R.id.txtFactorDetailDes);
        LinearLayout layDescription = (LinearLayout) dialog.findViewById(R.id.layDescription);
        TextView txtFactorDetailTitle = (TextView) dialog.findViewById(R.id.txtFactorDetailTitle);
        TextView txtFactorDetailPrice = (TextView) dialog.findViewById(R.id.txtFactorDetailPrice);

        String name = factor.Name.length() == 0 ? "-" : "" + factor.Name;
        txtFactorDetailTitle.setText(name);
        String price = factor.Price.length() == 0 ? "-" : "" + factor.Price + " تومان";
        txtFactorDetailPrice.setText(price);
        if (factor.Des.length() != 0) {
            layDescription.setVisibility(View.VISIBLE);
            txtFactorDetailDes.setText("" + factor.Des);
        } else {
            layDescription.setVisibility(View.GONE);
        }
        dialog.show();
    }

    public void showCompanyDetailDialog(GetIspInfoResponse response) {
        final Dialog dialog = new Dialog(G.currentActivity, R.style.DialogSlideAnim);
        dialog.setContentView(R.layout.dialog_company_detail);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView imgCloseDialog = (ImageView) dialog.findViewById(R.id.imgCloseDialog);
        TextView txtCompanyUrl = (TextView) dialog.findViewById(R.id.txtCompanyUrl);
        TextView txtCompanyDetail = (TextView) dialog.findViewById(R.id.txtCompanyDetail);
        imgCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        txtCompanyUrl.setText("" + response.Hyperlink);
        txtCompanyDetail.setText("" + response.Contact);
        dialog.show();
    }

    public void showQuestionForEndFeshfesheDialog() {
        final Dialog dialog = new Dialog(G.currentActivity, R.style.DialogSlideAnim);
        dialog.setContentView(R.layout.dialog_question);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout layBtnPositive = (LinearLayout) dialog.findViewById(R.id.layBtnOk);
        LinearLayout layBtnNegative = (LinearLayout) dialog.findViewById(R.id.layBtnCancel);
        ImageView imgCloseDialog = (ImageView) dialog.findViewById(R.id.imgCloseDialog);
        TextView txtBtnOk = (TextView) layBtnPositive.findViewById(R.id.txtValue);
        TextView txtBtnCancel = (TextView) layBtnNegative.findViewById(R.id.txtValue);
        TextView txtDialogTitle = (TextView) dialog.findViewById(R.id.txtDialogTitle);
        TextView txtDialogDescription = (TextView) dialog.findViewById(R.id.txtDialogDescription);


        txtDialogTitle.setText("هشدار");
        txtDialogDescription.setText("آیا مطمئن هستید میخواهید فشفشه جاری را خاتمه دهید؟");

        txtBtnOk.setText("بلی");
        txtBtnCancel.setText("خیر");

        layBtnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventOnClickedEndFeshfeshe());
                dialog.dismiss();
            }
        });
        imgCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        layBtnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showUpdateApplicationDialog(final String newVersion, final boolean isForce, final String url) {
        final Dialog dialog = new Dialog(G.currentActivity, R.style.DialogSlideAnim);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_update_application);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout layBtnCancel = (LinearLayout) dialog.findViewById(R.id.layBtnCancel);
        LinearLayout layBtnUpdate = (LinearLayout) dialog.findViewById(R.id.layBtnUpdate);
        ImageView imgCloseDialog = (ImageView) dialog.findViewById(R.id.imgCloseDialog);
        TextView txtCurrentVersion = (TextView) dialog.findViewById(R.id.txtCurrentVersion);
        TextView txtNewVersion = (TextView) dialog.findViewById(R.id.txtNewVersion);
        TextView txtLayBtnCancel = (TextView) layBtnCancel.findViewById(R.id.txtValue);
        TextView txtLayBtnUpdate = (TextView) layBtnUpdate.findViewById(R.id.txtValue);

        if (isForce) {
            txtLayBtnCancel.setText("خروج از برنامه");
        } else {
            txtLayBtnCancel.setText("انصراف");
        }
        txtLayBtnUpdate.setText("دانلود");
        txtCurrentVersion.setText("نسخه فعلی : " + U.getAppVersionName());
        txtNewVersion.setText("نسخه جدید : " + newVersion);
        if (isForce) {
//            layBtnCancel.setVisibility(View.GONE);
            imgCloseDialog.setVisibility(View.GONE);
        }
        layBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventOnShowDialogUpdatingApplicationRequest(newVersion, isForce, url));
                dialog.dismiss();
            }
        });
        layBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isForce) {
                    G.currentActivity.finish();
                    System.exit(0);
                } else {
                    dialog.dismiss();
                    EventBus.getDefault().post(new EventOnCheckGetPollRequest());
                }
            }
        });
        imgCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                EventBus.getDefault().post(new EventOnCheckGetPollRequest());
            }
        });
        dialog.show();
    }

    public void showUpdatingApplicationDialog(final String newVersion, final boolean isForce, final String url) {
        dialog = new Dialog(G.currentActivity, R.style.DialogSlideAnim);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_updating_application);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout layBtnCancelCurrentUpdate = (LinearLayout) dialog.findViewById(R.id.layBtnCancelCurrentUpdate);
        LinearLayout layBtnInstallUpdate = (LinearLayout) dialog.findViewById(R.id.layBtnInstallUpdate);
        ImageView imgCloseDialog = (ImageView) dialog.findViewById(R.id.imgCloseDialog);
        TextView txtLayBtnInstallUpdate = (TextView) layBtnInstallUpdate.findViewById(R.id.txtValue);
        TextView txtLayBtnCancelCurrentUpdate = (TextView) layBtnCancelCurrentUpdate.findViewById(R.id.txtValue);

        txtLayBtnInstallUpdate.setText("نصب");
        txtLayBtnCancelCurrentUpdate.setText("انصراف");
        layBtnInstallUpdate.setVisibility(View.GONE);

        layBtnCancelCurrentUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        layBtnInstallUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                U.installUpdateApp();
            }
        });
        imgCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                EventBus.getDefault().post(new EventOnCanceledDialogUpdatingApplication(newVersion, isForce, url));
            }
        });
        dialog.show();
    }

    public void changeProgressPercent(float percent) {
        if (dialog != null) {
            ((ProgressWheel) (dialog.findViewById(R.id.prgDownloadUpdatePercent))).setProgress(percent);
            ((TextView) (dialog.findViewById(R.id.txtLoading))).setText((int) (percent * 100) + "%");
        }
    }

    public void showInstallButton() {
        if (dialog != null) {
            ((LinearLayout) (dialog.findViewById(R.id.layBtnInstallUpdate))).setVisibility(View.VISIBLE);
            ((TextView) (dialog.findViewById(R.id.txtDialogDescription))).setText("دانلود با موفقیت انجام شد، حال میتوانید اقدام به نصب نسخه جدید کنید.");
            ((TextView) (dialog.findViewById(R.id.txtDialogTitle))).setText("نصب نسخه جدید");
            U.collapse(((LinearLayout) (dialog.findViewById(R.id.layLoading))));
        }
    }

    public void showPollDialog(final GetPollResponse poll) {
        dialogPoll = new Dialog(G.currentActivity, R.style.DialogSlideAnimAppCompact);
        dialogPoll.setContentView(R.layout.dialog_show_poll);
        dialogPoll.setCancelable(false);
        dialogPoll.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogPoll.getWindow().setGravity(Gravity.CENTER);
        dialogPoll.getWindow().setLayout(U.getDeviceWidth() - 30, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogPoll.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        LinearLayout layMainDialog = (LinearLayout) dialogPoll.findViewById(R.id.layMainDialog);
        LinearLayout laySpinner = (LinearLayout) dialogPoll.findViewById(R.id.laySpinner);
        final LinearLayout layLoading = (LinearLayout) dialogPoll.findViewById(R.id.layLoading);
        final AppCompatSpinner spPollOption = (AppCompatSpinner) dialogPoll.findViewById(R.id.spPollOption);
        TextView txtPollQuestion = (TextView) dialogPoll.findViewById(R.id.txtPollQuestion);
        final TextView txtShowErrorMessage = (TextView) dialogPoll.findViewById(R.id.txtShowErrorMessage);
        final EditText edtPollDescription = (EditText) dialogPoll.findViewById(R.id.edtPollDescription);
        final CardView layBtnSendPoll = (CardView) dialogPoll.findViewById(R.id.layBtnSendPoll);
        TextView txtLayBtnSendPoll = (TextView) layBtnSendPoll.findViewById(R.id.txtValue);

        final String[] codes = poll.OptionID.split("`");
        if (poll.OptionID.length() != 0) {
            String[] names = poll.OptionText.split("`");
            PollOption[] pollOptions = new PollOption[names.length + 1];
            PollOption firstOption = new PollOption();
            firstOption.code = "-1";
            firstOption.name = "یکی از موارد زیر را انتخاب کنید.";
            pollOptions[0] = firstOption;
            for (int i = 1; i < names.length + 1; i++) {
                PollOption newPoll = new PollOption();
                newPoll.code = codes[i - 1];
                newPoll.name = names[i - 1];
                pollOptions[i] = newPoll;
            }
            adapterSpinnerPoll = new AdapterSpinnerPoll(pollOptions);
            spPollOption.setAdapter(adapterSpinnerPoll);
        } else {
            laySpinner.setVisibility(View.GONE);
        }
        layMainDialog.setBackgroundResource(R.drawable.transparent);
        layLoading.setVisibility(View.GONE);
        txtPollQuestion.setText(Html.fromHtml("" + poll.Question));
        txtLayBtnSendPoll.setText("ارسال نظر");

        layBtnSendPoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtShowErrorMessage.setText("");
                String pollDescription = edtPollDescription.getText().toString().trim();
                String selectedOption = "-1";
                if (poll.OptionID.length() != 0) {
                    selectedOption = ((PollOption) (adapterSpinnerPoll.getItem(spPollOption.getSelectedItemPosition()))).code;
                    Logger.d("DialogClass : showPollDialog selected item code is " + selectedOption + " and des is " + pollDescription);
                    if (selectedOption.equals("-1")) {
                        txtShowErrorMessage.setText("لطفا یکی از گزینه های بالا را انتخاب کنید.");
                        return;
                    }
                } else {
                    if (pollDescription.equals("")) {
                        txtShowErrorMessage.setText("لطفا نظر خود را وارد کنید.");
                        return;
                    }
                }
                layLoading.setVisibility(View.VISIBLE);
                layBtnSendPoll.setClickable(false);
                EventBus.getDefault().post(new EventOnSendPollRequest(poll.ID, pollDescription, selectedOption));
            }
        });
        dialogPoll.show();
    }

    public void cancelPollDialog() {
        if (dialogPoll != null) {
            dialogPoll.dismiss();
        }
    }

    public void showErrorOnPollDialog(String message) {
        if (dialogPoll != null) {
            ((LinearLayout) (dialogPoll.findViewById(R.id.layLoading))).setVisibility(View.GONE);
            ((TextView) (dialogPoll.findViewById(R.id.txtShowErrorMessage))).setText(message);
            ((CardView) (dialogPoll.findViewById(R.id.layBtnSendPoll))).setClickable(true);

        }
    }

    public void showPayMessage(final long factorCode, String title, String description) {
        dialogPayMessage = new Dialog(G.currentActivity, R.style.DialogSlideAnim);
        dialogPayMessage.setContentView(R.layout.dialog_show_pay_message);
        dialogPayMessage.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialogPayMessage.getWindow().setGravity(Gravity.CENTER);
        dialogPayMessage.getWindow().setLayout(U.getDeviceWidth() - 30, ViewGroup.LayoutParams.WRAP_CONTENT);

        CardView layBtnStartFactor = (CardView) dialogPayMessage.findViewById(R.id.layBtnStartFactor);
        ImageView imgCloseDialog = (ImageView) dialogPayMessage.findViewById(R.id.imgCloseDialog);
        TextView txtMessageTitle = (TextView) dialogPayMessage.findViewById(R.id.txtMessageTitle);
        TextView txtMessageDescription = (TextView) dialogPayMessage.findViewById(R.id.txtMessageDescription);

        if (factorCode == 0)
            layBtnStartFactor.setVisibility(View.GONE);
        else
            layBtnStartFactor.setVisibility(View.VISIBLE);

        layBtnStartFactor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventOnClickedStartFactor(factorCode));
            }
        });

        imgCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogPayMessage.dismiss();
            }
        });

        dialogPayMessage.show();
    }

    public void cancelPayMessage() {
        if (dialogPayMessage != null)
            dialogPayMessage.dismiss();
    }

    public void DialogWaitingWithBackground(Context context) {
        dlgWaitingWithBackground = new Dialog(context, android.R.style.Theme_Black_NoTitleBar);
        dlgWaitingWithBackground.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dlgWaitingWithBackground.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dlgWaitingWithBackground.setContentView(R.layout.dialog_waiting_with_background);
        dlgWaitingWithBackground.setCancelable(false);
        dlgWaitingWithBackground.show();
    }

    public void cancelDialogWaitingWithBackground() {
        if (dlgWaitingWithBackground != null)
            dlgWaitingWithBackground.dismiss();
    }

}

