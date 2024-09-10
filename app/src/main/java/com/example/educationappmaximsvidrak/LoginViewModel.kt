package com.example.educationappmaximsvidrak

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.educationappmaximsvidrak.model.Profile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class LoginViewModel : ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    val firebaseRef: DatabaseReference =
        FirebaseDatabase.getInstance("https://education-app-maxim-svidrak-default-rtdb.europe-west1.firebasedatabase.app")
            .getReference("contacts")

    private var storageReference: StorageReference = FirebaseStorage.getInstance().getReference("Images")

    private val _profileLiveData = MutableLiveData<Profile>()
    val profileLiveData: LiveData<Profile> get() = _profileLiveData

    private val _updateStatus = MutableLiveData<Boolean>()
    val updateStatus: LiveData<Boolean> get() = _updateStatus


    //LiveData currentUser firebaseAuth
    private val _currentUser = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)
    val currentUser: LiveData<FirebaseUser?> = _currentUser

    //LiveData für result Login
    private val _loginResult = MutableLiveData<String>()
    val loginResult: LiveData<String> = _loginResult

    fun getProfileInfo () {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            firebaseRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val profile = snapshot.getValue(Profile::class.java)
                    _profileLiveData.value = profile!!
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Error loading profile: ${error.message}")
                }

            })
        }

    }

    private fun saveProfileData(userId: String, firsName: String, secondName: String, phoneNumber: String, imageUrl: String) {
        val profile = Profile (userId, firsName, secondName, phoneNumber, imageUrl)
        firebaseRef.child(userId).setValue(profile).addOnCompleteListener { tast ->
            _updateStatus.value = tast.isSuccessful
        }
    }

    fun  updateProfile (firsName: String, secondName: String, phoneNumber: String, uri: Uri?) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            if (uri != null) {
                storageReference.child(userId).putFile(uri)
                    .addOnSuccessListener { task ->
                        task.metadata?.reference?.downloadUrl?.addOnCompleteListener { url ->
                            val imageUrl = url.toString()
                            saveProfileData (userId, firsName, secondName, phoneNumber, imageUrl)
                        }
                    }
            } else {
                saveProfileData(userId, firsName, secondName, phoneNumber, "")
            }
        }
    }


    fun login(email: String, pass: String) {
        firebaseAuth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.i("LoginViewModel", "Login done")
                    _currentUser.value = it.result.user
                    _loginResult.value = "Login successful"
                } else {
                    //bearbeitug Fehler
                    val exception = it.exception
                    when (exception) {
                        is FirebaseAuthInvalidUserException -> {
                            // Учетная запись не существует
                            _loginResult.value = "Account does not exist"
                        }

                        is FirebaseAuthInvalidCredentialsException -> {
                            // Неправильный пароль
                            _loginResult.value = "Incorrect password"
                        }

                        else -> {
                            // Другие ошибки
                            _loginResult.value = "Login failed: ${exception?.message}"
                        }
                    }

                    Log.e("LoginViewModel", "Login failed: ${exception?.message}")
                }
            }
    }

    fun register(email: String, pass: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.i("LoginViewModel", "Login done")
                    _currentUser.value = it.result.user
                } else {
                    Log.e("LoginViewModel", "")
                }
            }
    }

    fun logout() {
        firebaseAuth.signOut()
        _currentUser.value = null
    }




}

