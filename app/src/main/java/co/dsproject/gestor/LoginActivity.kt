package co.dsproject.gestor


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth
    // [END declare_auth]

    private var customToken: String? = null
    private lateinit var tokenReceiver: TokenBroadcastReceiver
    val buttonSignIn = findViewById<Button>(R.id.login)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Button click listeners
        buttonSignIn.setOnClickListener(this)

        // Create token receiver (for demo purposes only)
        tokenReceiver = object : TokenBroadcastReceiver() {
            override fun onNewToken(token: String?) {
                Log.d(TAG, "onNewToken:$token")
                setCustomToken(token.toString())
            }
        }

        // [START initialize_auth]
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        // [END initialize_auth]
    }

    // [START on_start_check_user]
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
    // [END on_start_check_user]

    override fun onResume() {
        super.onResume()
        registerReceiver(tokenReceiver, TokenBroadcastReceiver.filter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(tokenReceiver)
    }

    private fun startSignIn() {
        // Initiate sign in with custom token
        // [START sign_in_custom]
        customToken?.let {
            auth.signInWithCustomToken(it)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCustomToken:success")
                            val user = auth.currentUser
                            updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCustomToken:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                            updateUI(null)
                        }
                    }
        }
        // [END sign_in_custom]
    }

    private fun updateUI(user: FirebaseUser?) {
    }

    private fun setCustomToken(token: String) {
        customToken = token

        val status = "Token:$customToken"

        // Enable/disable sign-in button and show the token
        buttonSignIn.isEnabled = true
    }

    override fun onClick(v: View) {
        val i = v.id
        if (i == R.id.login) {
            startSignIn()
        }
    }

    companion object {
        private const val TAG = "CustomAuthActivity"
    }
}