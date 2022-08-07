/*
 * File Name: LoginActivity.kt
 * File Description: Control the login and registration system
 * Author: Ching Hang Lam
 * Last Modified: 2022/08/06
 */
package com.project.onefood.Login

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.project.onefood.Login.data.User
import com.project.onefood.Login.fragments.*
import com.project.onefood.MainMenu.MainMenuActivity
import com.project.onefood.R
import com.project.onefood.databinding.ActivityLoginBinding

private const val TAG: String = "LoginActivity"

class LoginActivity : AppCompatActivity() {

    // UI
    private lateinit var binding: ActivityLoginBinding

    // Actions on create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivity(savedInstanceState)

        setListeners()
    }

    // Actions on back pressed
    override fun onBackPressed() {
        val fragment: Fragment? = supportFragmentManager.findFragmentById(R.id.loginFrameLayout)
        when (fragment) {
            is WhoFragment -> LoginActivity.switchToLoginFragment(supportFragmentManager)
            is CustomerRegisterFragment -> LoginActivity.switchToWhoFragment(supportFragmentManager)
            is RestaurantManagerRegisterFragment -> LoginActivity.switchToWhoFragment(supportFragmentManager)
            is ResetPasswordFragment -> LoginActivity.switchToLoginFragment(supportFragmentManager)
            else -> finishAffinity()
        }
    }

    // Initialize the activity
    private fun initActivity(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            switchToLoginFragment(supportFragmentManager)
        }
    }

    // Set the listeners
    private fun setListeners() {
        binding.logoImageView.setOnClickListener {
            clickLogoImageView()
        }
    }

    // Click the logo image view
    private fun clickLogoImageView() {
        val fragment: Fragment? = supportFragmentManager.findFragmentById(R.id.loginFrameLayout)
        when (fragment) {
            is LoginFragment -> switchToOneFoodWebpage(this)
            else ->  switchToLoginFragment(supportFragmentManager)
        }
    }

    companion object {

        const val ACCOUNT_TYPE_KEY: String = "ACCOUNT_TYPE_KEY"
        const val FIRST_NAME_KEY: String = "FIRST_NAME_KEY"
        const val LAST_NAME_KEY: String = "LAST_NAME_KEY"
        const val RESTAURANT_NAME_KEY: String = "RESTAURANT_NAME_KEY"
        const val EMAIL_ADDRESS_KEY: String = "EMAIL_ADDRESS_KEY"
        const val HOME_ADDRESS_KEY: String = "HOME_ADDRESS_KEY"
        const val DATA_IS_CHANGED_KEY: String = "DATA_IS_CHANGED_KEY"

        // Check the email address
        // Returns
        // true: valid email address, false: invalid email address
        fun checkEmailAddress(resources: Resources, editText: EditText): Boolean {
            val emailAddressString: String = editText.text.toString().trim()

            if (emailAddressString.isEmpty()) {
                editText.error = resources.getString(R.string.login_activity_error_empty_email_address)
                editText.requestFocus()

                return false
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(emailAddressString).matches()) {
                editText.error = resources.getString(R.string.login_activity_error_invalid_email_address)
                editText.requestFocus()

                return false
            }

            return true
        }

        // Check the password
        // Returns
        // true: valid password, false: invalid password
        fun checkPassword(resources: Resources, editText: EditText): Boolean {
            val passwordString: String = editText.text.toString().trim()

            if (passwordString.length < 6) {
                editText.error = resources.getString(R.string.login_activity_error_invalid_password)
                editText.requestFocus()

                return false
            }

            return true
        }

        // Check the first name
        // Returns
        // true: valid first name, false: first name
        fun checkFirstName(resources: Resources, editText: EditText): Boolean {
            val firstNameString: String = editText.text.toString().trim()

            if (firstNameString.isEmpty()) {
                editText.error = resources.getString(R.string.login_activity_error_empty_first_name)
                editText.requestFocus()

                return false
            }

            return true
        }

        // Check the restaurant name
        // Returns
        // true: valid restaurant name, false: restaurant name
        fun checkRestaurantName(resources: Resources, editText: EditText): Boolean {
            val restaurantNameString: String = editText.text.toString().trim()

            if (restaurantNameString.isEmpty()) {
                editText.error = resources.getString(R.string.login_activity_error_empty_restaurant_name)
                editText.requestFocus()

                return false
            }

            return true
        }

        // Switch to login fragment
        fun switchToLoginFragment(supportFragmentManager: FragmentManager) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.loginFrameLayout, LoginFragment())
                commit()
            }
        }

        // Switch to who fragment
        fun switchToWhoFragment(supportFragmentManager: FragmentManager) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.loginFrameLayout, WhoFragment())
                commit()
            }
        }

        // Switch to customer register fragment
        fun switchToCustomerRegisterFragment(supportFragmentManager: FragmentManager) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.loginFrameLayout, CustomerRegisterFragment())
                commit()
            }
        }

        // Switch to restaurant manager register fragment
        fun switchToRestaurantManagerRegisterFragment(supportFragmentManager: FragmentManager) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.loginFrameLayout, RestaurantManagerRegisterFragment())
                commit()
            }
        }

        // Switch to rest password fragment
        fun switchToResetPasswordFragment(supportFragmentManager: FragmentManager) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.loginFrameLayout, ResetPasswordFragment())
                commit()
            }
        }

        // Switch to one food webpage
        fun switchToOneFoodWebpage(context: Context) {
            val intent: Intent = Intent(Intent.ACTION_VIEW, context.resources.getString(R.string.one_food_web_address).toUri())
            context.startActivity(intent)
        }

        // Switch to main menu activity
        fun switchToMainMenuActivity(context: Context, user: User, isDataChanged: Boolean) {
            val intent: Intent = Intent(context, MainMenuActivity::class.java)

            intent.putExtra(ACCOUNT_TYPE_KEY, user.accountType.ordinal)
            intent.putExtra(FIRST_NAME_KEY, user.firstName)
            intent.putExtra(LAST_NAME_KEY, user.lastName)
            intent.putExtra(RESTAURANT_NAME_KEY, user.restaurantName)
            intent.putExtra(EMAIL_ADDRESS_KEY, user.emailAddress)
            intent.putExtra(HOME_ADDRESS_KEY, user.homeAddress)
            intent.putExtra(DATA_IS_CHANGED_KEY, isDataChanged)

            context.startActivity(intent)
        }
    }
}