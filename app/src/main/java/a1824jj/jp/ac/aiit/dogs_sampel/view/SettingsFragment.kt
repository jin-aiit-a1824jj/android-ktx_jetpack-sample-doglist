package a1824jj.jp.ac.aiit.dogs_sampel.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import a1824jj.jp.ac.aiit.dogs_sampel.R
import androidx.preference.PreferenceFragmentCompat

/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

}
