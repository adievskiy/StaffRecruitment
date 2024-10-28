package com.example.staffrecruitment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var employees: MutableList<Employee> = mutableListOf()
    private lateinit var listAdapter: ListAdapter

    private lateinit var toolbarMain: Toolbar
    private lateinit var nameET: EditText
    private lateinit var secondNameET: EditText
    private lateinit var ageET: EditText
    private lateinit var positionSPNR: Spinner
    private lateinit var employeeLV: ListView
    private lateinit var saveBTN: Button

    private val role = mutableListOf(
        "Директор",
        "Бухгалтер",
        "Менеджер",
        "Аналитик",
        "Охранник"
    )

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbarMain = findViewById(R.id.toolbarMain)
        setSupportActionBar(toolbarMain)
        title = "Персонал"

        nameET = findViewById(R.id.nameET)
        secondNameET = findViewById(R.id.secondNameET)
        ageET = findViewById(R.id.ageET)

        positionSPNR = findViewById(R.id.positionSPNR)
        val adapter = ArrayAdapter(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            role
        )
        positionSPNR.setAdapter(adapter)

        employeeLV = findViewById(R.id.employeeLV)
        saveBTN = findViewById(R.id.saveBTN)

        saveBTN.setOnClickListener {
            checkAndAddEmployee()
        }

        employeeLV.setOnItemClickListener { _, _, position, _ ->
            val item = listAdapter.getItem(position)
            listAdapter.remove(item)
            listAdapter.notifyDataSetChanged()
        }
    }

    private fun checkAndAddEmployee() {
        if (nameET.text.isEmpty() || secondNameET.text.isEmpty() || ageET.text.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_LONG).show()
        } else {
            if (isNumeric(ageET.text.toString())) {
                val employee = createEmployee()
                employees.add(employee)

                listAdapter = ListAdapter(this@MainActivity, employees)
                employeeLV.adapter = listAdapter
                clearFields()
            } else Toast.makeText(this, "Возраст - число", Toast.LENGTH_LONG).show()
        }
    }

    private fun clearFields() {
        nameET.text.clear()
        secondNameET.text.clear()
        ageET.text.clear()
    }

    private fun isNumeric(toCheck: String): Boolean {
        val regex = "-?[0-9]+(\\.[0-9]+)?".toRegex()
        return toCheck.matches(regex)
    }

    private fun createEmployee(): Employee {
        val name = nameET.text.toString()
        val secondName = secondNameET.text.toString()
        val age = ageET.text.toString()
        val position = positionSPNR.getSelectedItem().toString()
        val employee = Employee(name, secondName, age, position)
        return employee
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuExit -> finishAffinity()
        }
        return super.onOptionsItemSelected(item)
    }
}