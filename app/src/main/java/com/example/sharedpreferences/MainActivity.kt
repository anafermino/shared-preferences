package com.example.sharedpreferences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sharedpreferences.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("myPref", MODE_PRIVATE)
        // name e mode - name: nome desse shared preferences (pode ter vários)
        // Mode tem 3 tipos: public - qualquer app pode ter acesso ao dado do shared preferences desse app
        // private - nenhum app pode ver nosso preferences
        // append - vai pegar outros preferences existentes e vai juntar esse aos outros.

        val editor = sharedPref.edit() // função edit retorna o editor da referência do nosso shared pref.

        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val age = binding.etAge.text.toString().toInt()
            val isAdult = binding.cbAdulthood.isChecked

            editor.apply {
                putString("name", name)  // vai salvar a string  no editor, no nosso shared prferences. Em par de chave-valor.
                putInt("age", age)
                putBoolean("isAdult", isAdult)
                apply() // Terminou de salvar os dados necessários, chama a função apply()
                // para finalizar e escrever os dados em shared preferences, assincronamente (não bloqueia a main thread, diferente do commit()).
            }
        }

        // Ao fechar o app e abri-lo novamente, ao clicar em LOAD, os campos serão preenchidos com os
        // dados previamente preenchidos e salvos em shared preferences.
        binding.btnLoad.setOnClickListener {
            val name = sharedPref.getString("name", null)
            val age = sharedPref.getInt("age", 0)
            val isAdult = sharedPref.getBoolean("isAdult", false)

            binding.etName.setText(name)
            binding.etAge.setText(age.toString())
            binding.cbAdulthood.isChecked = isAdult
        }
    }
}
