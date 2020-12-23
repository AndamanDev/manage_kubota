package com.therdsak.kubota_manage_android.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.therdsak.kubota_manage_android.R
import com.therdsak.kubota_manage_android.model.VisitorDataResponse
import com.therdsak.kubota_manage_android.model.VisitorListDataResponse
import kotlinx.android.synthetic.main.activity_contact_detail.*
import kotlinx.android.synthetic.main.item_person.view.*


class ItemPersonAdapter  (private val list :  List<VisitorListDataResponse>,
                          val clickListener : (VisitorListDataResponse) -> Unit
) : RecyclerView.Adapter<ItemPersonAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_person, parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bind(list[p1]  , clickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    class ViewHolder(itemsView: View) : RecyclerView.ViewHolder(itemsView) {
        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
        @SuppressLint("ResourceType", "SetTextI18n", "UseCompatLoadingForDrawables")
        fun bind(data : VisitorListDataResponse ,  clickListener: (data : VisitorListDataResponse) -> Unit ) {
            itemView.apply {

                if (data.visitor_type_id == 1){
                    linear_main.background = context.resources.getDrawable(R.drawable.border_green)
                    text_view_name.setTextColor(context.resources.getColor(R.color.colorWhite))
                    text_view_type.setTextColor(context.resources.getColor(R.color.colorWhite))
                    text_view_company.setTextColor(context.resources.getColor(R.color.colorWhite))
                    text_view_phone.setTextColor(context.resources.getColor(R.color.colorWhite))
                    text_view_more.setTextColor(context.resources.getColor(R.color.colorWhite))
                    text_view_name_name.setTextColor(context.resources.getColor(R.color.colorWhite))
                    text_view_name_company.setTextColor(context.resources.getColor(R.color.colorWhite))
                    text_view_name_phone.setTextColor(context.resources.getColor(R.color.colorWhite))
                }else{
                    linear_main.background = context.resources.getDrawable(R.drawable.border_white)
                    text_view_name.setTextColor(context.resources.getColor(R.color.colorBlack))
                    text_view_type.setTextColor(context.resources.getColor(R.color.colorBlack))
                    text_view_company.setTextColor(context.resources.getColor(R.color.colorBlack))
                    text_view_phone.setTextColor(context.resources.getColor(R.color.colorBlack))
                    text_view_more.setTextColor(context.resources.getColor(R.color.colorBlack))
                    text_view_name_name.setTextColor(context.resources.getColor(R.color.colorBlack))
                    text_view_name_company.setTextColor(context.resources.getColor(R.color.colorBlack))
                    text_view_name_phone.setTextColor(context.resources.getColor(R.color.colorBlack))
                }
                if (data.visitor_pic_online == ""){
                    linear_no_data.visibility = View.VISIBLE
                    image_view_visitor.visibility = View.GONE
                }else{
                    linear_no_data.visibility = View.GONE
                    image_view_visitor.visibility = View.VISIBLE
                    Glide
                        .with(this)
                        .load(data.visitor_pic_online)
                        .into(image_view_visitor)
                }
                text_view_name.text = data.visitor_nm
                text_view_type.text = data.visitor_type_desc
                text_view_company.text = data.visitor_company
                text_view_phone.text = data.visitor_phone
                text_view_more.setOnClickListener {
                    clickListener(data)
                }


            }
        }



    }




}