package ba.sss.lemiforce

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import ba.sss.lemiforce.data.staticdata.PitanjaRepo
import java.io.Console


class OdabirTest : AppCompatActivity() {
    private lateinit var odabirSpn : Spinner
    private var lista : ArrayList<String> = arrayListOf("Nasumično")
    private var kategorija : String? = null
    private lateinit var txtKategorije: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.odabir_testa)
        kategorija = intent.getStringExtra("KATEGORIJA")
        txtKategorije = findViewById(R.id.txtPolozili)
        odabirSpn = findViewById(R.id.odabirSpn)
        for (i in 0 until intent.getIntExtra("BRTESTOVA",0)) {
            lista.add("Test ${i+1}")
        }
        val adapter = ArrayAdapter(this, R.layout.spinner_item, lista)
        odabirSpn.adapter = adapter

        odabirSpn.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        when (kategorija) {
            "KatA" -> txtKategorije.text = "Kategorija A"
            "KatB" -> txtKategorije.text = "Kategorija B"
            "KatC" -> txtKategorije.text = "Kategorija C"
            "KatD" -> txtKategorije.text = "Kategorija D"
            "KatT" -> txtKategorije.text = "Kategorija T"
            "Prva" -> txtKategorije.text = "Prva pomoć"
        }
    }

    private var intentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_CANCELED) {
            finish()
        }
    }

    fun openTest(view: View) {
        var intent = Intent(this, UcenjeTest::class.java)
        var kat: String = ""
        if(txtKategorije.text.contains("Prva")) {
            kat = "P"
        }else{
            kat = txtKategorije.text.takeLast(1).toString()
        }
        intent.putExtra("KATEGORIJA", kat)
        intent.putExtra("TEST", odabirSpn.selectedItemPosition)
        intentLauncher.launch(intent)
    }

    fun openSimulation(view: View) {
        var intent = Intent(this, SimulacijaTest::class.java)
        var kat: String = ""
        if(txtKategorije.text.contains("Prva")) {
            kat = "P"
        }else{
            kat = txtKategorije.text.takeLast(1).toString()
        }
        intent.putExtra("KATEGORIJA", kat)
        intent.putExtra("TEST", odabirSpn.selectedItemPosition)
        intentLauncher.launch(intent)
    }
}