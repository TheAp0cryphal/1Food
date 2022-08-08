package com.project.onefood.MainMenu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.project.onefood.Login.LoginActivity
import com.project.onefood.Login.data.AccountType
import com.project.onefood.Login.data.User
import com.project.onefood.MainMenu.viewmodels.UserProfileViewModel
import com.project.onefood.R
import com.project.onefood.databinding.ActivityUserProfileBinding
import java.util.*

class UserProfileActivity : AppCompatActivity(){

    // UI
    private lateinit var binding: ActivityUserProfileBinding

    // View models
    private lateinit var viewModel: UserProfileViewModel

    // Firebase
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase

    // Actions on create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivity()

        if (viewModel.accountType.value == null) {
            loadDataFromPrevious()
        }

        setUserAccount()
        setListeners()
    }

    // Initialize the activity
    private fun initActivity() {
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(UserProfileViewModel::class.java)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance(getString(R.string.firebase_database_instance_users))
    }

    // Load the data from the previous
    private fun loadDataFromPrevious() {
        viewModel.accountType.value = AccountType.values()[intent.getIntExtra(LoginActivity.ACCOUNT_TYPE_KEY, 0)]
        viewModel.firstName.value = intent.getStringExtra(LoginActivity.FIRST_NAME_KEY)
        viewModel.lastName.value = intent.getStringExtra(LoginActivity.LAST_NAME_KEY)
        viewModel.restaurantName.value = intent.getStringExtra(LoginActivity.RESTAURANT_NAME_KEY)
        viewModel.emailAddress.value = intent.getStringExtra(LoginActivity.EMAIL_ADDRESS_KEY)
        viewModel.homeAddress.value = intent.getStringExtra(LoginActivity.HOME_ADDRESS_KEY)
    }

    // Set the user account
    private fun setUserAccount() {
        if (viewModel.accountType.value == AccountType.RESTAURANT_MANAGER) {
            binding.firstNameTextView.visibility = View.GONE
            binding.firstNameEditText.visibility = View.GONE
            binding.lastNameTextView.visibility = View.GONE
            binding.lastNameEditText.visibility = View.GONE
        } else if (viewModel.accountType.value == AccountType.CUSTOMER || viewModel.accountType.value == AccountType.DEVELOPER) {
            binding.restaurantNameTextView.visibility = View.GONE
            binding.restaurantNameEditText.visibility = View.GONE
        } else if (viewModel.accountType.value == AccountType.ANONYMOUS) {
            binding.restaurantNameTextView.visibility = View.GONE
            binding.restaurantNameEditText.visibility = View.GONE

            binding.firstNameEditText.isEnabled = false
            binding.lastNameEditText.isEnabled = false
            binding.homeAddressEditText.isEnabled = false

            binding.saveButton.visibility = View.GONE
        }

        binding.firstNameEditText.setText(viewModel.firstName.value)
        binding.lastNameEditText.setText(viewModel.lastName.value)
        binding.restaurantNameEditText.setText(viewModel.restaurantName.value)
        binding.emailAddressEditText.setText(viewModel.emailAddress.value)
        binding.homeAddressEditText.setText(viewModel.homeAddress.value)
    }

    // Set the listeners
    private fun setListeners() {
        binding.logoutButton.setOnClickListener {
            clickLogoutButton()
        }

        binding.saveButton.setOnClickListener {
            clickSaveButton()
        }
    }

    // Click the logout button
    private fun clickLogoutButton() {
        firebaseAuth.signOut()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    // Click the save button
    private fun clickSaveButton() {
        if (viewModel.accountType.value == AccountType.CUSTOMER) {
            if (!LoginActivity.checkFirstName(resources, binding.firstNameEditText))
                return
        } else if (viewModel.accountType.value == AccountType.RESTAURANT_MANAGER) {
            if (!LoginActivity.checkRestaurantName(resources, binding.restaurantNameEditText))
                return
        }

        viewModel.firstName.value = binding.firstNameEditText.text.toString()
        viewModel.lastName.value = binding.lastNameEditText.text.toString()
        viewModel.restaurantName.value = binding.restaurantNameEditText.text.toString()
        viewModel.emailAddress.value = binding.emailAddressEditText.text.toString()
        viewModel.homeAddress.value = binding.homeAddressEditText.text.toString()

        val user: User = User(viewModel.accountType.value!!, viewModel.firstName.value!!, viewModel.lastName.value!!, viewModel.restaurantName.value!!, viewModel.emailAddress.value!!, viewModel.homeAddress.value!!)
        val uid: String = firebaseAuth.currentUser!!.uid

        firebaseDatabase.getReference(getString(R.string.firebase_database_users)).child(uid).setValue(user).addOnCompleteListener {
            // Successfully store customer info
            if (it.isSuccessful) {
                Toast.makeText(binding.root.context, R.string.user_profile_activity_toast_succeed_save, Toast.LENGTH_SHORT).show()

                LoginActivity.switchToMainMenuActivity(this, user, true)
            }
            // Unsuccessfully store customer info
            else {
                Toast.makeText(binding.root.context, R.string.user_profile_activity_toast_fail_save, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        var myLanguage = findViewById<TextView>(R.id.myLanguage)
        myLanguage.text = Locale.getDefault().displayName

        var settings = findViewById<LinearLayout>(R.id.settings)

        settings.setOnClickListener {
            var intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
        }
    }
}