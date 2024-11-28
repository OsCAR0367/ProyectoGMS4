package com.example.proyectogms4

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.assertIsDisplayed
import com.example.proyectogms4.presentation.initial.InitialScreen
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
/*
class InitialScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testInitialScreenComponents() {
        composeTestRule.setContent {
            InitialScreen()
        }

        // Verify that the text "Poder tecnológico." is displayed
        composeTestRule.onNodeWithText("Poder tecnológico.").assertIsDisplayed()

        // Verify that the text "Conciencia social." is displayed
        composeTestRule.onNodeWithText("Conciencia social.").assertIsDisplayed()

        // Verify that the button "Sign up free" is displayed
        composeTestRule.onNodeWithText("Sign up free").assertIsDisplayed()
    }


}*/