package yuresko.checkboxlist

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import yuresko.checkboxlist.adapter.MyAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var button: Button
    private lateinit var recyclerView: RecyclerView

    private val adapter = MyAdapter()
    private val repository = Repository()

    private val viewModel = TestViewModel(repository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText = findViewById(R.id.editText)
        button = findViewById(R.id.myButton)
        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        viewModel.items.observe(this, Observer {
            adapter.submitList(it)
        })

        button.setOnClickListener {
            val num = editText.text.toString()
            viewModel.changeCheckBox(num.toIntOrNull() ?: 0)
        }
    }
}