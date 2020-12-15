//package com.therdsak.kubota_manage_android.fragment.emp
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import com.therdsak.kubota_manage_android.R
//import kotlinx.android.synthetic.main.fragment_inform.*
//
//class InformFragment  : Fragment() {
//
//
//    interface InformListener {
//        fun onInformSuccess()
//
//    }
//
//    private lateinit var listener: InformListener
//    companion object {
//        fun newInstance(): InformFragment {
//            val fragment = InformFragment()
//            val args = Bundle()
//            fragment.arguments = args
//            return fragment
//        }
//    }
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }
//
//
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        if (context is InformListener) {
//            listener = context as InformListener
//        } else {
//            throw ClassCastException(
//                context.toString() + " must implement OnDogSelected.") as Throwable
//        }
//        return inflater.inflate(R.layout.fragment_inform, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        button_submit.setOnClickListener {
//            listener.onInformSuccess()
//        }
//    }
//}