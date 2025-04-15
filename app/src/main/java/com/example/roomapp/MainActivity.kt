package com.example.roomapp

import android.media.session.MediaSession.QueueItem
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.example.roomapp.Item
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    // Initialize the widget
    lateinit var itemName : EditText
    lateinit var itemPrice : EditText
    lateinit var itemQuantity : EditText
    lateinit var dbRecordsText : TextView
    lateinit var saveBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        itemName = findViewById(R.id.editText1)
        itemPrice = findViewById(R.id.editText2)
        itemQuantity = findViewById(R.id.editText3)
        dbRecordsText = findViewById(R.id.textView2)
        saveBtn = findViewById(R.id.saveItemBtn)

        saveBtn.setOnClickListener {
            insertItem()
        }

        displayAllRecords()

    }

    private fun insertItem(){
        // Getting the text from EditText
        val name = itemName.text.toString()
        val price = itemPrice.text.toString()
        val quantity = itemQuantity.text.toString()


        // Converting String to Int and Double
        val intPrice = price.toDouble()
        val intQuantity = quantity.toInt()

        // Instance of the Database
        val myDB = MyDB.getDatabase(applicationContext)

        // Instance of DAO
        val myDAO = myDB.itemDAO()

        // Inserting data into Database
        val myItem: Item = Item(0,name,intPrice,intQuantity)
        // COROUTINE
        CoroutineScope(Dispatchers.IO).launch {
            myDAO.inserItem(myItem)
        }

    }
    fun displayAllRecords(){
        // Instance of the Database
        val myDB = MyDB.getDatabase(applicationContext)

        // Instance of DAO
        val myDAO = myDB.itemDAO()

        // Get all items and separate with an \n
        myDAO.getAllItemsInDB().observe(this, Observer{
            var result = ""
            for(item in it){
                result = it.joinToString("\n")
            }
            dbRecordsText.text = result
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)

//        // Example: Handling the search action view
//        val searchItem = menu.findItem(R.id.action_search)
//        val searchView = searchItem?.actionView as? SearchView
//        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                // Handle search submission
//                if (!query.isNullOrEmpty()) {
//                    Toast.makeText(this@MainActivity, "Searching for: $query", Toast.LENGTH_SHORT).show()
//                    // Perform your search operation here
//                    searchView.clearFocus() // Optional: Hide keyboard after submission
//                    return true
//                }
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                // Handle text changes during search (optional)
//                return true
//            }
//        })

        return true
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> {
//                // Handle settings item click
//                Toast.makeText(this, "Settings selected", Toast.LENGTH_SHORT).show()
//                true
//            }
//            R.id.action_share -> {
//                // Handle share item click
//                Toast.makeText(this, "Share selected", Toast.LENGTH_SHORT).show()
//                true
//            }
//            R.id.menu_item_1 -> {
//                // Handle item one click from the overflow menu
//                Toast.makeText(this, "Item One selected", Toast.LENGTH_SHORT).show()
//                true
//            }
//            R.id.menu_item_2 -> {
//                // Handle item two click from the overflow menu
//                Toast.makeText(this, "Item Two selected", Toast.LENGTH_SHORT).show()
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
}
