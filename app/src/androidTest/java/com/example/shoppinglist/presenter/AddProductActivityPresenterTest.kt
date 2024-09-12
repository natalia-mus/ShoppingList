package com.example.shoppinglist.presenter

import androidx.test.core.app.ApplicationProvider
import com.example.shoppinglist.SavingContext
import com.example.shoppinglist.ValidationResult
import com.example.shoppinglist.contract.AddProductActivityContract
import com.example.shoppinglist.contract.MainActivityContract
import com.example.shoppinglist.database.DBHelper
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class AddProductActivityPresenterTest {

    companion object {
        private const val NAME = "6da11f55-ab6b-4d9c-b4ce-b1651221d6c5"
        private const val QUANTITY = "5 pcs"
        private const val PRIORITY = 4

        private const val NEW_NAME = "09d3ffef-0868-4ba2-960f-5f0140e090d6"
        private const val NEW_QUANTITY = "3 kilos"
        private const val NEW_PRIORITY = 2
    }

    private lateinit var presenter: AddProductActivityPresenter

    @Mock
    private lateinit var view: AddProductActivityContract.AddProductActivityView

    private lateinit var mainActivityPresenter: MainActivityPresenter

    @Mock
    private lateinit var mainActivityView: MainActivityContract.MainActivityView

    private val testProductsIds = ArrayList<Int>()


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        DBHelper.getInstance(ApplicationProvider.getApplicationContext())
        presenter = AddProductActivityPresenter(view)
        mainActivityPresenter = MainActivityPresenter(mainActivityView)
    }


    @Test
    fun addValidProduct() {
        val result = presenter.saveData(SavingContext.CREATE, null, NAME, QUANTITY, PRIORITY.toString())
        var isInDatabase = false
        val productId = getProductId(NAME, QUANTITY, PRIORITY)

        if (productId != null) {
            isInDatabase = true
            testProductsIds.add(productId)
        }

        assertTrue(result == ValidationResult.VALID && isInDatabase)
    }

    @Test
    fun addProductWithEmptyName() {
        val result = presenter.saveData(SavingContext.CREATE, null, "", QUANTITY, PRIORITY.toString())
        var isInDatabase = false
        val productId = getProductId("", QUANTITY, PRIORITY)

        if (productId != null) {
            isInDatabase = true
            testProductsIds.add(productId)
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
        var productId = getProductId(NAME, QUANTITY, PRIORITY)

        if (productId == null) {
            presenter.saveData(SavingContext.CREATE, null, NAME, QUANTITY, PRIORITY.toString())
            productId = getProductId(NAME, QUANTITY, PRIORITY)
        }

        if (productId != null) {
            testProductsIds.add(productId)
            presenter.saveData(SavingContext.EDIT, productId, NEW_NAME, NEW_QUANTITY, NEW_PRIORITY.toString())

            mainActivityPresenter.fetchData()
            val products = mainActivityPresenter.returnData()

            if (products != null) {
                for (product in products) {
                    if (product.id == productId && product.name == NEW_NAME && product.quantity == NEW_QUANTITY && product.priority == NEW_PRIORITY) {
                        result = true
                    }
                }
            }
        }

        assertTrue(result)
    }

    @After
    fun finish() {
        mainActivityPresenter.fetchData()
        val products = mainActivityPresenter.returnData()

        if (products != null) {
            for (product in products) {
                for (id in testProductsIds) {
                    if (product.id == id) {
                        mainActivityPresenter.deleteItem(id)
                    }
                }
            }
        }
    }

    private fun getProductId(name: String, quantity: String, priority: Int): Int? {
        var id: Int? = null
        mainActivityPresenter.fetchData()
        val products = mainActivityPresenter.returnData()

        if (products != null) {
            for (product in products) {
                if (product.name == name && product.quantity == quantity && product.priority == priority) {
                    id = product.id
                }
            }
        }

        return id
    }

}