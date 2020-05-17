package thoughtworks.training.m05.data

data class Goods(
    val barcode: String,
    val name: String,
    val unit: String,
    val price: Float
)

class ShoppingCartItem(val goods: Goods, var sum: Int = 0, val discountable: Discountable? = null) {
    val cost: Float
        get() {
            return discountable?.discount(this) ?: originalPrice
        }
    val saved: Float
        get() {
            return originalPrice - cost
        }

    private val originalPrice : Float
        get() = goods.price * sum
}

class ShoppingCart(val items: MutableList<ShoppingCartItem> = mutableListOf()) {
    val cost: Float
        get() {
            return items.takeIf { it.isNotEmpty() }?.map { it.cost }?.reduce { acc, fl ->  acc + fl } ?: 0f
        }

    val saved: Float
        get() {
            return items.takeIf { it.isNotEmpty() }?.map { it.saved }?.reduce { acc, fl -> acc + fl } ?: 0f
        }
}


