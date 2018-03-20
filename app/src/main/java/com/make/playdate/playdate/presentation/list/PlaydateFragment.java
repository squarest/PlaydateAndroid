package com.make.playdate.playdate.presentation.list;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.make.playdate.R;
import com.make.playdate.databinding.FragmentPlaydateBinding;
import com.make.playdate.entities.PlaydateModel;
import com.make.playdate.main.presentation.main.MainView;
import com.make.playdate.utils.DialogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adib on 13-Apr-17.
 */

public class PlaydateFragment extends MvpAppCompatFragment implements PlaydateView {
    private FragmentPlaydateBinding binding;
    @InjectPresenter
    public PlaydatePresenter presenter;
    private MainView mainView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_playdate, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainView = (MainView) getActivity();
        presenter.viewCreated();
        binding.makePlaydateButton.setOnClickListener(view1 ->
        {
            showLoading();
            presenter.makePlaydateButtonClicked();
        });


    }

    @Override
    public void showLoading() {
        mainView.showLoading();
    }

    @Override
    public void dismissLoading() {
        mainView.dismissLoading();
    }

    private PlaydateSliderAdapter sliderAdapter;

    @Override
    public void setPlaydates(List<PlaydateModel> playdates) {
        if (playdates.size() > 0) {
            sliderAdapter = new PlaydateSliderAdapter(playdates);
            binding.avatarsSlider.setAdapter(sliderAdapter);
            binding.avatarsSlider.setOffscreenItems(2);
            binding.avatarsSlider.setSlideOnFling(false);
            binding.avatarsSlider.addOnItemChangedListener((viewHolder, adapterPosition) ->
            {
                PlaydateModel playdate = playdates.get(adapterPosition);
                binding.playdateDescription.setText(String.format(getString(R.string.playdate_descr),
                        playdate.monthCreated, playdate.dayCreated));
                binding.playdateName.setText(String.format(getString(R.string.playdate_title), playdate.userFirstName));
            });
            binding.avatarsSlider.setVisibility(View.VISIBLE);
        } else {
            binding.avatarsPlaceholder.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void addNewPlaydate(PlaydateModel playdateModel) {
        if (sliderAdapter == null) {
            List<PlaydateModel> playdateModels = new ArrayList<>();
            playdateModels.add(playdateModel);
            setPlaydates(playdateModels);
        }else {
            sliderAdapter.addNew(playdateModel);
        }
        binding.avatarsSlider.smoothScrollToPosition(0);
        mainView.dismissLoading();

    }

    @Override
    public void showPlaydateFailedDialog() {
        DialogUtil dialogUtil = new DialogUtil(getContext());
        dialogUtil.getAlertDialog(getString(R.string.playdate_fail_dialog_title),
                getString(R.string.playdate_fail_dialog_descr)).show();

    }

    @Override
    public void showMessage(int messageId) {
        Toast.makeText(getContext(), messageId, Toast.LENGTH_SHORT).show();
    }
}
