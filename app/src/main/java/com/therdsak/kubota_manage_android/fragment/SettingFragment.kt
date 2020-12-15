package com.therdsak.kubota_manage_android.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.therdsak.kubota_manage_android.R

class SettingFragment : Fragment() {


    companion object {
        fun newInstance(): SettingFragment {
            val fragment = SettingFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }


    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }
}