package co.dsproject.gestor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var fieldEmail :EditText
    private lateinit var fieldPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViewById<Button>(R.id.login).setOnClickListener(this)
        fieldEmail = findViewById<EditText>(R.id.email)
        fieldPassword = findViewById<EditText>(R.id.password)

        auth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser

    }

    private fun sigIn(email: String, password: String ){
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){ task ->  
                    if(task.isSuccessful){
                        val user = auth.currentUser
                        Toast.makeText(this, "Autenticación Exitosa", Toast.LENGTH_SHORT).show()
                    } else{
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }

    }

    override fun onClick(v: View?) {
        val i = v?.id
        when(i){
            R.id.login -> sigIn(fieldEmail.text.toString(), fieldPassword.text.toString())
        }
    }
}
