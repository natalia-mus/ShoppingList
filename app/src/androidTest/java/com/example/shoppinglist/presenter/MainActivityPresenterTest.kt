package com.example.shoppinglist.presenter

import androidx.test.core.app.ApplicationProvider
import com.example.shoppinglist.constants.SavingContext
import com.example.shoppinglist.contract.AddProductActivityContract
import com.example.shoppinglist.contract.MainActivityContract
import com.example.shoppinglist.database.DBHelper
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MainActivityPresenterTest {

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
        addProductActivityPresenter.saveData(SavingContext.CREATE, null, "tomatoes", "2", "1")
        addProductActivityPresenter.saveData(SavingContext.CREATE, null, "orange juice", "1 liter", "3")
        addProductActivityPresenter.saveData(SavingContext.CREATE, null, "soap", "2 bars", "1")
    }

    @Test
    fun deleteProduct() {
        var condition = false
        mainActivityPresenter.fetchData()
        var products = mainActivityPresenter.returnData()

        if (products != null) {
            var id: Int? = null
            for (product in products) {
                if (product.name == "tomatoes" && product.quantity == "2" && product.priority == 1) {
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

}