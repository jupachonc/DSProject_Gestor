package co.dsproject.gestor

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth
    private lateinit var fieldEmail :EditText
    private lateinit var fieldPassword: EditText
    private lateinit var fieldName: EditText
    private lateinit var fieldConfirmPassword: EditText
    private lateinit var singInButton: Button
    private lateinit var registerButton: Button
    private var flag = false

    lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        fieldEmail = findViewById(R.id.email)
        fieldPassword = findViewById(R.id.password)
        fieldConfirmPassword = findViewById(R.id.confirm_password)
        fieldName = findViewById(R.id.name)
        singInButton = findViewById(R.id.login)
        registerButton = findViewById(R.id.register)

        //Listeners
        singInButton.setOnClickListener(this)
        registerButton.setOnClickListener(this)


        auth = FirebaseAuth.getInstance()

    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser

    }

    private fun createAccount(email: String, password: String) {
        if (!validateFormUp()) {
            return
        }

        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_SHORT).show()
                        val userdata = FirebaseDatabase.getInstance().getReference("Users/" + user!!.uid + "/Data")
                        userdata.child("name").setValue(fieldName.text.toString())
                        userdata.child("email").setValue(user.email)
                        val goToMainActivity = Intent(this, MainActivity::class.java)
                        goToMainActivity.putExtra("user", user)
                        startActivity(goToMainActivity)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this, "Registro Fallido",
                                Toast.LENGTH_SHORT).show()
                    }


                }
        // [END create_user_with_email]
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
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }
                }

    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = fieldEmail.text.toString()
        if (TextUtils.isEmpty(email)) {
            fieldEmail.error = "Requerido"
            valid = false
        } else {
            fieldEmail.error = null
        }

        val password = fieldPassword.text.toString()
        if (TextUtils.isEmpty(password)) {
            fieldPassword.error = "Requerido"
            valid = false
        } else {
            fieldPassword.error = null
        }

        return valid
    }

    private fun validateFormUp(): Boolean {
        var valid = true

        val name = fieldName.text.toString()
        if (TextUtils.isEmpty(name)) {
            fieldName.error = "Requerido"
            valid = false
        } else {
            fieldName.error = null
        }

        val email = fieldEmail.text.toString()
        if (TextUtils.isEmpty(email)) {
            fieldEmail.error = "Requerido"
            valid = false
        } else {
            fieldEmail.error = null
        }


        val password = fieldPassword.text.toString()
        if (TextUtils.isEmpty(password)) {
            fieldPassword.error = "Requerido"
            valid = false
        } else {
            fieldPassword.error = null
        }

        val confirmpassword = fieldConfirmPassword.text.toString()
        if (TextUtils.isEmpty(confirmpassword)) {
            fieldConfirmPassword.error = "Requerido"
            valid = false
        } else {
            fieldConfirmPassword.error = null
        }

        if(valid && password != confirmpassword){
            valid = false
            Toast.makeText(this, "La contraseña no coincide", Toast.LENGTH_SHORT).show()
        }

        return valid
    }

    private fun signUp(){
        singInButton.visibility = View.GONE
        fieldName.visibility = View.VISIBLE
        fieldConfirmPassword.visibility = View.VISIBLE
        if(flag){
            createAccount(fieldEmail.text.toString(), fieldPassword.text.toString())
        }
        flag = true


    }

    override fun onClick(v: View?) {
        val i = v?.id
        when(i){
            R.id.login -> sigIn(fieldEmail.text.toString(), fieldPassword.text.toString())
            R.id.register -> signUp()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && flag) {
            singInButton.visibility = View.VISIBLE
            fieldName.visibility = View.GONE
            fieldConfirmPassword.visibility = View.GONE
            flag = false
            return true;
        }
        return  super.onKeyDown(keyCode, event)

    }





}
