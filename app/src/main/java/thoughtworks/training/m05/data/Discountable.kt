package thoughtworks.training.m05.data

interface Discountable {
    fun discount(item: ShoppingCartItem): Float
}

class BuyTwoSendOne : Discountable {

    override fun discount(item: ShoppingCartItem): Float {
        return item.run { (sum % 2) * goods.price }
    }
}