package com.example.staffrecruitment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class ListAdapter(context: Context, employeeList: MutableList<Employee>) :
    ArrayAdapter<Employee>(context, R.layout.employee_list, employeeList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val employee = getItem(position)
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.employee_list, parent, false)
        }
        val nameTV= view?.findViewById<TextView>(R.id.nameTV)
        val secondNameTV= view?.findViewById<TextView>(R.id.secondNameTV)
        val ageTV= view?.findViewById<TextView>(R.id.ageTV)
        val positionTV= view?.findViewById<TextView>(R.id.positionTV)

        nameTV?.text = employee?.name
        secondNameTV?.text = employee?.secondName
        ageTV?.text = employee?.age.toString()
        positionTV?.text = employee?.position

        return view!!
    }
}