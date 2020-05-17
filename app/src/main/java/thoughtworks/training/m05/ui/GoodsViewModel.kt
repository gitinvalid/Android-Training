package thoughtworks.training.m05.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import thoughtworks.training.m05.data.BuyTwoSendOne
import thoughtworks.training.m05.data.Goods
import thoughtworks.training.m05.data.ShoppingCart
import thoughtworks.training.m05.data.ShoppingCartItem
import java.lang.IllegalStateException

class GoodsViewModel : ViewModel() {
    private val _item000000 = ShoppingCartItem(
        Goods(
            "ITEM000000", "可口可乐", "瓶", 3.00f
        ), discountable = BuyTwoSendOne()
    )
    private val _item0000001 = ShoppingCartItem(
        Goods(
            "ITEM000001", "雪碧", "瓶", 3.00f
        ), discountable = BuyTwoSendOne()
    )
    private val _item000002 = ShoppingCartItem(
        Goods(
            "ITEM000002", "苹果", "斤", 5.50f
        )
    )
    private val _item000003 = ShoppingCartItem(
        Goods(
            "ITEM000003", "荔枝", "斤", 15.00f
        )
    )
    private val _shoppingCart = ShoppingCart()

    private val _item000000LD = MutableLiveData(_item000000)
    private val _item000001LD = MutableLiveData(_item0000001)
    private val _item000002LD = MutableLiveData(_item000002)
    private val _item000003LD = MutableLiveData(_item000003)

    private val _shoppingCartLD = MutableLiveData(_shoppingCart)

    val item000000LD: LiveData<ShoppingCartItem> = _item000000LD
    val item000001LD: LiveData<ShoppingCartItem> = _item000001LD
    val item000002LD: LiveData<ShoppingCartItem> = _item000002LD
    val item000003LD: LiveData<ShoppingCartItem> = _item000003LD

    fun onItem000000Added() {
        addItemToShoppingCart(_item000000)
        _item000000LD.value = _item000000.apply { ++sum }
    }

    fun onItem000001Added() {
        addItemToShoppingCart(_item0000001)
        _item000001LD.value = _item0000001.apply { ++sum }
    }

    fun onItem000002Added() {
        addItemToShoppingCart(_item000002)
        _item000002LD.value = _item000002.apply { ++sum }
    }

    fun onItem000003Added() {
        addItemToShoppingCart(_item000003)
        _item000003LD.value = _item000003.apply { ++sum }
    }

    private fun addItemToShoppingCart(item: ShoppingCartItem) {
        item.takeUnless { _shoppingCart.items.contains(it) }?.let { _shoppingCart.items.add(it) }
        _shoppingCartLD.value = _shoppingCart
    }

    fun onResetClick() {
        _shoppingCart.items.clear()
        _shoppingCartLD.value = _shoppingCart

        _item000000.sum = 0
        _item000000LD.value = _item000000

        _item0000001.sum = 0
        _item000001LD.value = _item0000001

        _item000002.sum = 0
        _item000002LD.value = _item000002

        _item000003.sum = 0
        _item000003LD.value = _item000003
    }

    val checkoutResult: LiveData<String>
        get() {
            return Transformations.map(_shoppingCartLD) { shoppingCart ->
                val items = shoppingCart.items.filter { it.sum != 0 }.takeIf { it.isNotEmpty() }
                items?.let {
                    var checkoutResult = "***<没钱赚商店>收据***\n"
                    it.map { shoppingCartItem ->
                        checkoutResult += "名称： ${shoppingCartItem.goods.name}，" +
                                "数量：${shoppingCartItem.sum}${shoppingCartItem.goods.unit}，" +
                                "单价：${shoppingCartItem.goods.price}(元)，" +
                                "小计：${shoppingCartItem.cost}(元)\n"
                    }
                    checkoutResult += """
        ----------------------
        总计：${shoppingCart.cost}(元)
        节省：${shoppingCart.saved}(元)
        **********************
    """.trimIndent()
                    checkoutResult
                } ?: ""
            }
        }
}
