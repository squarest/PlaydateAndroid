package com.prince.logan.playdate.main.presentation.menu;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.prince.logan.playdate.R;
import com.prince.logan.playdate.auth.presentation.LoginActivity;
import com.prince.logan.playdate.databinding.FragmentMenuBinding;
import com.prince.logan.playdate.help.AboutActivity;
import com.prince.logan.playdate.help.faq.FAQActivity;
import com.prince.logan.playdate.main.presentation.main.MainView;
import com.prince.logan.playdate.preference.EditProfileActivity;
import com.prince.logan.playdate.preference.PreferencesActivity;
import com.prince.logan.playdate.utils.DialogUtil;

/**
 * Created by Adib on 13-Apr-17.
 */

public class MenuFragment extends MvpAppCompatFragment implements View.OnClickListener, MenuView {
    private DialogUtil dialogUtil;
    private FragmentMenuBinding binding;
    private MainView mainView;
    @InjectPresenter
    public MenuPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainView = (MainView) getActivity();
        setEvent();
    }

    public void setEvent() {
        binding.linMenuAbout.setOnClickListener(this);
        binding.linMenuFaq.setOnClickListener(this);
        binding.linMenuPreference.setOnClickListener(this);
        binding.linMenuFeedback.setOnClickListener(this);
        binding.linMenuUserProfile.setOnClickListener(this);
        binding.linMenuLogout.setOnClickListener(this);
        binding.linMenuDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lin_menu_about:
                Intent aboutIntent = new Intent(getContext(), AboutActivity.class);
                startActivity(aboutIntent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right,
                        R.anim.slide_out_to_left);
                break;
            case R.id.lin_menu_faq:
                Intent faqIntent = new Intent(getContext(), FAQActivity.class);
                startActivity(faqIntent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right,
                        R.anim.slide_out_to_left);
                break;

            case R.id.lin_menu_feedback:
                Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "contact@makeplaydate.com", null));
                i.putExtra(android.content.Intent.EXTRA_SUBJECT, "Report a bug");
                i.putExtra(android.content.Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(i, "Send email"));
                getActivity().overridePendingTransition(R.anim.slide_in_from_right,
                        R.anim.slide_out_to_left);
                break;
            case R.id.lin_menu_preference:
                Intent preferenceIntent = new Intent(getContext(), PreferencesActivity.class);
                startActivity(preferenceIntent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right,
                        R.anim.slide_out_to_left);
                break;
            case R.id.lin_menu_user_profile:
                Intent profileIntent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(profileIntent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right,
                        R.anim.slide_out_to_left);
                break;
            case R.id.lin_menu_logout:
                presenter.logoutButtonClicked();
                break;
            case R.id.lin_menu_delete:
                dialogUtil = new DialogUtil(getContext());
                dialogUtil.getAlertDialog(getString(R.string.delete_dialog_title), getString(R.string.delete_dialod_msg),
                        (dialogInterface, i1) -> presenter.deleteButtonClicked());
                presenter.deleteButtonClicked();
                break;
        }
    }


    @Override
    public void showLoading() {
        mainView.showLoading();
    }

    @Override
    public void dismissLoading() {
        mainView.dismissLoading();
    }

    @Override
    public void showLoginScreen() {
        Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
        startActivity(loginIntent);

    }
}
