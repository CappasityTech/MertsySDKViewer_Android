package com.mertsy.javasample;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.mertsy.common.util.exception.MertsyException;
import com.mertsy.view.MertsyModel;
import com.mertsy.view.MertsyModelView;
import com.mertsy.view.MertsyModelViewParams;
import com.mertsy.view.MertsyViewer;

import java.util.Objects;

public class ModelViewActivity extends BaseActivity {

    private AppCompatButton btnSearch;
    private AppCompatEditText etModelIdOrUrl;
    private ProgressBar pbLoading;
    private LinearLayout inputsContainer;
    private MertsyModelView modelView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_model_view);

        btnSearch = findViewById(R.id.btnSearch);
        etModelIdOrUrl = findViewById(R.id.etModelIdOrLink);
        pbLoading = findViewById(R.id.pbLoading);
        inputsContainer = findViewById(R.id.llInputsContainer);
        modelView = findViewById(R.id.mertsyModelView);
        btnSearch.setOnClickListener(v -> onSearchClicked());
    }

    private void onSearchClicked() {
        String searchText = Objects.requireNonNull(etModelIdOrUrl.getText()).toString();
        if (searchText.isEmpty()) {
            return;
        }
        showLoading();
        boolean isUrl = searchText.contains("https://");

        MertsyViewer.ModelCallback callback = new MertsyViewer.ModelCallback() {
            @Override
            public void onSuccess(@NonNull MertsyModel mertsyModel) {
                displayModel(mertsyModel);
            }

            @Override
            public void onFailure(@NonNull MertsyException e) {
                handleError(e);
            }
        };

        if (!isUrl)
            MertsyViewer.getModel(searchText, callback);
        else
            MertsyViewer.getModelByLink(searchText, callback);

    }

    private void displayModel(MertsyModel model) {

        MertsyModelViewParams params = new MertsyModelViewParams.Builder()
                .autoRun(true)
                .hideHints(true)
                .build();

        modelView.loadModel(model, params, new MertsyModelView.OnModelLoadListener() {
            @Override
            public void onLoadingFailed(@NonNull MertsyException e) {
                handleError(e);
            }

            @Override
            public void onModelReady() {
                inputsContainer.setVisibility(View.GONE);
            }
        });

    }

    private void handleError(MertsyException exception) {
        hideLoading();
        showToast(exception.toString());
        exception.printStackTrace();
    }

    private void hideLoading() {
        pbLoading.setVisibility(View.GONE);
        btnSearch.setVisibility(View.VISIBLE);
    }

    private void showLoading() {
        pbLoading.setVisibility(View.VISIBLE);
        btnSearch.setVisibility(View.GONE);
    }

}