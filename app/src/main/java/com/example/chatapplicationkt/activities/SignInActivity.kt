package com.example.chatapplicationkt.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapplicationkt.databinding.ActivitySignInBinding
import com.example.chatapplicationkt.utils.Constants
import com.example.chatapplicationkt.utils.PreferenceManager
import com.google.firebase.firestore.FirebaseFirestore

class SignInActivity : AppCompatActivity() {

    private var binding: ActivitySignInBinding? = null
    private var preferenceManager: PreferenceManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceManager = PreferenceManager()
        if (preferenceManager!!.getBoolean(Constants.KEY_IS_SIGNED_IN)) {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        setListener()
    }

    private fun setListener() {
        binding?.textCreateNewAccount?.setOnClickListener {
            startActivity(Intent(applicationContext, SignUpActivity::class.java))
        }
        binding?.buttonSignIn?.setOnClickListener {
            if (isValidSignInDetails()) {
                signIn()
            }
        }
    }

    private fun signIn() {
        loading(true)

        val database = FirebaseFirestore.getInstance()
        database.collection(Constants.KEY_COLLECTION_USERS)
            .whereEqualTo(Constants.KEY_EMAIL, binding?.inputEmail?.text.toString())
            .whereEqualTo(Constants.KEY_PASSWORD, binding?.inputPassword?.text.toString())
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null && task.result.documents.size > 0){

                    val documentSnapshot = task.result.documents[0]
                    preferenceManager?.putBoolean(Constants.KEY_IS_SIGNED_IN, true)
                    preferenceManager?.putString(Constants.KEY_USER_ID, documentSnapshot.id)
                    preferenceManager?.putString(Constants.KEY_NAME, documentSnapshot.getString(Constants.KEY_NAME))
                    Toast.makeText(applicationContext, preferenceManager?.getString(Constants.KEY_NAME), Toast.LENGTH_SHORT).show()
                    preferenceManager?.putString(Constants.KEY_IMAGE, documentSnapshot.getString(Constants.KEY_IMAGE))

                    val intent = Intent(applicationContext, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(intent)
                } else {
                    loading(false)
                    showToast("Unable to sign in")
                }
            }
    }

    private fun loading(isLoading: Boolean) {
        if (isLoading) {
            binding?.buttonSignIn?.visibility = View.INVISIBLE
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.buttonSignIn?.visibility = View.VISIBLE
            binding?.progressBar?.visibility = View.INVISIBLE
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun isValidSignInDetails(): Boolean {
        return if (binding?.inputEmail?.text.toString().trim().isEmpty()) {
            showToast("Enter Email")
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding?.inputEmail?.text.toString()).matches()) {
            showToast("Enter valid Email")
            false
        } else if (binding?.inputPassword?.text.toString().trim().isEmpty()) {
            showToast("Enter password")
            false
        } else {
            true
        }
    }

    /*private fun addDataToFirestore() {
        val database = FirebaseFirestore.getInstance()
        val data = HashMap<String, Any>()
        data["fName"] = "Md"
        data["lName"] = "Shaheen"

        database.collection("users")
            .add(data)
            .addOnCompleteListener {
                Toast.makeText(
                    applicationContext,
                    "Data Inserted",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener { exception: Exception ->
                Toast.makeText(
                    applicationContext,
                    exception.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
    }*/
}