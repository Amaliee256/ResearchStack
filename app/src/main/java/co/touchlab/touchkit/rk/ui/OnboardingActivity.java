package co.touchlab.touchkit.rk.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import co.touchlab.touchkit.rk.R;
import co.touchlab.touchkit.rk.common.model.StudyOverviewModel;
import co.touchlab.touchkit.rk.common.task.SignInTask;
import co.touchlab.touchkit.rk.common.task.SignUpTask;
import co.touchlab.touchkit.rk.ui.adapter.OnboardingPagerAdapter;
import co.touchlab.touchkit.rk.ui.views.PageIndicator;
import co.touchlab.touchkit.rk.utils.JsonUtils;

/**
 * Created by bradleymcdermott on 10/15/15.
 */
public class OnboardingActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_onboarding);

        StudyOverviewModel model = parseStudyOverviewModel();
        OnboardingPagerAdapter adapter = new OnboardingPagerAdapter(this, model.getQuestions());

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setOffscreenPageLimit(2);
        pager.setAdapter(adapter);
//      TODO  pager.setPageTransformer();
//      TODO  pager.setPageMargin();

        final PageIndicator indicator = (PageIndicator) findViewById(R.id.pager_indicator);
        indicator.removeAllMarkers(true);
        indicator.addMarkers(adapter.getCount(),
                             R.drawable.ic_pageindicator_current_dark,
                             R.drawable.ic_pageindicator_default_dark,
                             true);

        pager.clearOnPageChangeListeners();
        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
        {
            @Override
            public void onPageSelected(int position)
            {
                indicator.setActiveMarker(position);
            }
        });
    }

    //TODO Read on main thread for intense UI blockage.
    private StudyOverviewModel parseStudyOverviewModel()
    {
        return JsonUtils.loadClassFromJson(OnboardingActivity.this,
                StudyOverviewModel.class,
                R.raw.study_overview);
    }

    public void onSignUpClicked(View view)
    {
        SignUpTask task = new SignUpTask();
        startActivity(ViewTaskActivity.newIntent(this,
                task));
    }

    public void onSignInClicked(View view)
    {
        SignInTask task = new SignInTask();
        startActivity(ViewTaskActivity.newIntent(this,
                task));
    }
}
