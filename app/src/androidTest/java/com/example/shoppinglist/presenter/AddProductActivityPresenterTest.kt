package com.example.shoppinglist.presenter

import com.example.shoppinglist.ValidationResult
import com.example.shoppinglist.constants.SavingContext
import com.example.shoppinglist.contract.AddProductActivityContract
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class AddProductActivityPresenterTest {

    private lateinit var presenter: AddProductActivityPresenter

    @Mock
    private lateinit var mockView: AddProductActivityContract.AddProductActivityView


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = AddProductActivityPresenter(mockView)
    }


    @Test
    fun addValidProduct() {
        val result = presenter.saveData(SavingContext.CREATE, null, "name", "1", "1")
        assertTrue(result == ValidationResult.VALID)
    }

    @Test
    fun addProductWithEmptyName() {
        val result = presenter.saveData(SavingContext.CREATE, null, "", "1", "1")
        assertTrue(result == ValidationResult.EMPTY_NAME)
    }

    @Test
    fun addProductWithEmptyPriority() {
        val result = presenter.saveData(SavingContext.CREATE, null, "name", "1", "")
        assertTrue(result == ValidationResult.EMPTY_PRIORITY)
    }

    @Test
    fun updateProduct() {
        val result = presenter.saveData(SavingContext.EDIT, 1, "new name", "2", "2")
        assertTrue(result == ValidationResult.VALID)
    }

}