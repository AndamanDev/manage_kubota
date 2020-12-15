//package com.therdsak.kubota_manage_android.fragment.contact
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.therdsak.kubota_manage_android.R
//import com.therdsak.kubota_manage_android.adapter.ItemPersonAdapter
//import com.therdsak.kubota_manage_android.fragment.MainFragment
//import com.therdsak.kubota_manage_android.fragment.ScanFragment
//import kotlinx.android.synthetic.main.fragment_contact.*
//import kotlinx.android.synthetic.main.fragment_scan.*
//
//class ContactFragment : Fragment() {
//    var personAdapter: ItemPersonAdapter? = null
//
//    interface ContactListener {
//        fun onContactSuccess()
//
//    }
//
//    private lateinit var listener: ContactListener
//
//    companion object {
//        fun newInstance(): ContactFragment {
//            val fragment = ContactFragment()
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
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        if (context is ContactListener) {
//            listener = context as ContactListener
//        } else {
//            throw ClassCastException(
//                context.toString() + " must implement OnDogSelected.") as Throwable
//        }
//        return inflater.inflate(R.layout.fragment_contact, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        button_next.setOnClickListener {
//           listener.onContactSuccess()
//        }
//        setRecyclerView()
//    }
//
//
//    fun setRecyclerView() {
//        personAdapter =
//            ItemPersonAdapter()
//
//        try {
//            recycler_view.apply {
//                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//                isNestedScrollingEnabled = false
//                adapter = personAdapter
//                onFlingListener = null
//            }
//        }catch (e : Exception){
//
//        }
//    }
//}