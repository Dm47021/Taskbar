/* Copyright 2016 Braden Farmer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.farmerbb.taskbar.fragment;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.farmerbb.taskbar.R;
import com.farmerbb.taskbar.util.U;

public class AdvancedFragment extends SettingsFragment {

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        finishedLoadingPrefs = false;

        super.onActivityCreated(savedInstanceState);

        // Add preferences
        addPreferencesFromResource(R.xml.pref_advanced);

        // Set OnClickListeners for certain preferences
        findPreference("clear_pinned_apps").setOnPreferenceClickListener(this);
        findPreference("launcher").setOnPreferenceClickListener(this);
        findPreference("keyboard_shortcut").setOnPreferenceClickListener(this);

        bindPreferenceSummaryToValue(findPreference("hide_when_keyboard_shown"));
        bindPreferenceSummaryToValue(findPreference("icon_pack_use_mask"));
        bindPreferenceSummaryToValue(findPreference("show_search_bar"));

        SharedPreferences pref = U.getSharedPreferences(getActivity());
        boolean searchBarEnabled = pref.getString("show_search_bar", "keyboard").equals("always");
        findPreference("hide_when_keyboard_shown").setEnabled(!searchBarEnabled);

        if(Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
            findPreference("show_search_bar").setSummary(getResources().getStringArray(R.array.pref_show_search_bar_list)[2]);
            findPreference("show_search_bar").setEnabled(false);
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setTitle(R.string.pref_header_advanced);
        ActionBar actionBar = activity.getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        finishedLoadingPrefs = true;
    }
}
