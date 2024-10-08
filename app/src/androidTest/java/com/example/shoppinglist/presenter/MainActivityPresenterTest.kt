package com.example.shoppinglist.presenter

import androidx.test.core.app.ApplicationProvider
import com.example.shoppinglist.SavingContext
import com.example.shoppinglist.contract.AddProductActivityContract
import com.example.shoppinglist.contract.MainActivityContract
import com.example.shoppinglist.database.DBHelper
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MainActivityPresenterTest {

    companion object {
        private const val PRODUCT_1_NAME = "14c81e60-9b3f-43ae-b128-5a6692764c9b"
        private const val PRODUCT_2_NAME = "3a98e130-a15e-49df-979f-d5ebc90403cc"
        private const val PRODUCT_1_QUANTITY = "1 kilo"
        private const val PRODUCT_2_QUANTITY = "10 pcs"
        private const val PRODUCT_PRIORITY = "3"
    }

    private lateinit var mainActivityPresenter: MainActivityPresenter

    @Mock
    private lateinit var mainActivityView: MainActivityContract.MainActivityView

    private lateinit var addProductActivityPresenter: AddProductActivityContract.AddProductActivityPresenter

    @Mock
    private lateinit var addProductActivityView: AddProductActivityContract.AddProductActivityView


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        DBHelper.getInstance(ApplicationProvider.getApplicationContext())
        mainActivityPresenter = MainActivityPresenter(mainActivityView)

        addProductActivityPresenter = AddProductActivityPresenter(addProductActivityView)
        addProductActivityPresenter.saveData(SavingContext.CREATE, null, PRODUCT_1_NAME, PRODUCT_1_QUANTITY, PRODUCT_PRIORITY)
        addProductActivityPresenter.saveData(SavingContext.CREATE, null, PRODUCT_2_NAME, PRODUCT_2_QUANTITY, PRODUCT_PRIORITY)
    }

    @Test
    fun deleteProduct() {
        var condition = false
        mainActivityPresenter.fetchData()
        var products = mainActivityPresenter.returnData()

        if (products != null) {
            var id: Int? = null
            for (product in products) {
                if (product.name == PRODUCT_1_NAME && product.quantity == PRODUCT_1_QUANTITY && product.priority == PRODUCT_PRIORITY.toInt()) {
                    id = product.id
                }
            }

            if (id != null) {
                mainActivityPresenter.deleteItem(id)
                mainActivityPresenter.fetchData()
                products = mainActivityPresenter.returnData()

                if (products != null) {
                    condition = true
                    for (product in products) {
                        if (product.id == id) {
                            condition = false
                        }
                    }
                }
            }
        }

        assertTrue(condition)
    }

    @After
    fun finish() {
        mainActivityPresenter.fetchData()
        val products = mainActivityPresenter.returnData()

        if (products != null) {
            for (product in products) {
                if ((product.name == PRODUCT_1_NAME && product.quantity == PRODUCT_1_QUANTITY && product.priority == PRODUCT_PRIORITY.toInt()) ||
                    (product.name == PRODUCT_2_NAME && product.quantity == PRODUCT_2_QUANTITY && product.priority == PRODUCT_PRIORITY.toInt())
                ) {
                    mainActivityPresenter.deleteItem(product.id)
                }
            }
        }
    }

}