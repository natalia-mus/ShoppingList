package com.example.shoppinglist.presenter

import androidx.test.core.app.ApplicationProvider
import com.example.shoppinglist.ValidationResult
import com.example.shoppinglist.constants.SavingContext
import com.example.shoppinglist.contract.AddProductActivityContract
import com.example.shoppinglist.contract.MainActivityContract
import com.example.shoppinglist.database.DBHelper
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class AddProductActivityPresenterTest {

    companion object {
        private const val NAME = "name"
        private const val QUANTITY = "5 pcs"
        private const val PRIORITY = 4

        private const val NEW_NAME = "new name"
        private const val NEW_QUANTITY = "3 kilos"
        private const val NEW_PRIORITY = 2
    }

    private lateinit var presenter: AddProductActivityPresenter

    @Mock
    private lateinit var view: AddProductActivityContract.AddProductActivityView

    private lateinit var mainActivityPresenter: MainActivityPresenter

    @Mock
    private lateinit var mainActivityView: MainActivityContract.MainActivityView


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        DBHelper.getInstance(ApplicationProvider.getApplicationContext())
        presenter = AddProductActivityPresenter(view)
        mainActivityPresenter = MainActivityPresenter(mainActivityView)
    }


    @Test
    fun addValidProduct() {
        val result = addProduct()

        mainActivityPresenter.fetchData()
        val products = mainActivityPresenter.returnData()

        var isInDatabase = false
        if (products != null) {
            for (product in products) {
                if (product.name == NAME && product.quantity == QUANTITY && product.priority == PRIORITY) {
                    isInDatabase = true
                }
            }
        }

        assertTrue(result == ValidationResult.VALID && isInDatabase)
    }

    @Test
    fun addProductWithEmptyName() {
        val result = presenter.saveData(SavingContext.CREATE, null, "", QUANTITY, PRIORITY.toString())

        mainActivityPresenter.fetchData()
        val products = mainActivityPresenter.returnData()

        var isInDatabase = false
        if (products != null) {
            for (product in products) {
                if (product.name == "" && product.quantity == QUANTITY && product.priority == PRIORITY) {
                    isInDatabase = true
                }
            }
        }

        assertTrue(result == ValidationResult.EMPTY_NAME && !isInDatabase)
    }

    @Test
    fun addProductWithEmptyPriority() {
        val result = presenter.saveData(SavingContext.CREATE, null, NAME, QUANTITY, "")
        assertTrue(result == ValidationResult.EMPTY_PRIORITY)
    }

    @Test
    fun updateProduct() {
        var result = false
        if (addProduct() == ValidationResult.VALID) {

            mainActivityPresenter.fetchData()
            var products = mainActivityPresenter.returnData()

            var id: Int? = null
            if (products != null) {
                for (product in products) {
                    if (product.name == NAME && product.quantity == QUANTITY && product.priority == PRIORITY) {
                        id = product.id
                    }
                }
            }

            if (id != null) {
                presenter.saveData(SavingContext.EDIT, id, NEW_NAME, NEW_QUANTITY, NEW_PRIORITY.toString())

                mainActivityPresenter.fetchData()
                products = mainActivityPresenter.returnData()

                if (products != null) {
                    for (product in products) {
                        if (product.id == id && product.name == NEW_NAME && product.quantity == NEW_QUANTITY && product.priority == NEW_PRIORITY) {
                            result = true
                        }
                    }
                }
            }
        }

        assertTrue(result)
    }

    private fun addProduct(): ValidationResult {
        return presenter.saveData(SavingContext.CREATE, null, NAME, QUANTITY, PRIORITY.toString())
    }

}