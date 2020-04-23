package co.dsproject.gestor

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var fieldEmail :EditText
    private lateinit var fieldPassword: EditText
    lateinit var uid: String

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
        if (!validateForm()) {
            return
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){ task ->  
                    if(task.isSuccessful){
                        val user = auth.currentUser
                        Toast.makeText(this, "Autenticación Exitosa", Toast.LENGTH_SHORT).show()
                        val goToMainActivity = Intent(this, MainActivity::class.java)
                        goToMainActivity.putExtra("user", user)
                        startActivity(goToMainActivity)
                    } else{
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }

    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = fieldEmail.text.toString()
        if (TextUtils.isEmpty(email)) {
            fieldEmail.error = "Requerido."
            valid = false
        } else {
            fieldEmail.error = null
        }

        val password = fieldPassword.text.toString()
        if (TextUtils.isEmpty(password)) {
            fieldPassword.error = "Requerido."
            valid = false
        } else {
            fieldPassword.error = null
        }

        return valid
    }

    override fun onClick(v: View?) {
        val i = v?.id
        when(i){
            R.id.login -> sigIn(fieldEmail.text.toString(), fieldPassword.text.toString())
        }
    }


}
