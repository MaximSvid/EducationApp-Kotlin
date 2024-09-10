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

    // Eine interessante Lösung für die Verfolgung von Profilaktualisierungen
    private val _updateStatus = MutableLiveData<Boolean>()
    val updateStatus: LiveData<Boolean> get() = _updateStatus


    //LiveData currentUser firebaseAuth
    private val _currentUser = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)
    val currentUser: LiveData<FirebaseUser?> = _currentUser

    //LiveData für result Login
    private val _loginResult = MutableLiveData<String>()
    val loginResult: LiveData<String> = _loginResult

    fun getProfileInfo() {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            firebaseRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Prüfen, ob das Profil in der Datenbank existiert
                    val profile = snapshot.getValue(Profile::class.java)

                    // Wenn das Profil existiert, LiveData aktualisieren, andernfalls ein neues Profil mit leeren Werten erstellen
                    if (profile != null) {
                        _profileLiveData.value = profile!!
                    } else {
                        // Standardwerte für das neue Profil
                        val newProfile = Profile(
                            userId = userId,
                            firstName = "",
                            username = "",
                            phoneNumber = "",
                            image = ""
                        )
                        _profileLiveData.value = newProfile
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Error loading profile: ${error.message}")
                }
            })
        }
    }

    private fun saveProfileData(userId: String, firstName: String, lastName: String, phoneNumber: String, imageUrl: String) {
        val profile = Profile(userId, firstName, lastName, phoneNumber, imageUrl)
        firebaseRef.child(userId).setValue(profile).addOnCompleteListener { task ->
            _updateStatus.value = task.isSuccessful
        }
    }

    fun updateProfile(firstName: String, lastName: String, phoneNumber: String, uri: Uri?) {
        val userId = firebaseAuth.currentUser?.uid
        if (userId != null) {
            if (uri != null) {
                val imageRef = storageReference.child(userId)
                val uploadTask = imageRef.putFile(uri)

                // Get URL after upload completes
                uploadTask.addOnCompleteListener { uploadTask ->
                    if (uploadTask.isSuccessful) {
                        imageRef.downloadUrl.addOnCompleteListener { urlTask ->
                            if (urlTask.isSuccessful) {
                                val imageUrl = urlTask.result.toString()
                                saveProfileData(userId, firstName, lastName, phoneNumber, imageUrl)
                            } else {
                                Log.e("Firebase", "Error getting download URL: ${urlTask.exception?.message}")
                            }
                        }
                    } else {
                        Log.e("Firebase", "Image upload failed: ${uploadTask.exception?.message}")
                    }
                }
            } else {
                saveProfileData(userId, firstName, lastName, phoneNumber, "")
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
                            // Das Konto existiert nicht
                            _loginResult.value = "Account does not exist"
                        }

                        is FirebaseAuthInvalidCredentialsException -> {
                            // Falsches Passwort
                            _loginResult.value = "Incorrect password"
                        }

                        else -> {
                            // Andere Fehler
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

