import java.util.LinkedHashMap

fun main() {
    val receipt = getReceipt()
    println(receipt)
    val result = when (receipt == expectedReceipt) {
        true -> "正确 ✅"
        false -> "错误 ❌"
    }
    println("\n结果：${result}")
}

interface Promotion {
    var barcodes: List<String>
}

data class BuyTwoGetOneFreePromotion(override var barcodes: List<String>) : Promotion {

}

fun loadPromotions(): List<Promotion> =
    listOf(BuyTwoGetOneFreePromotion(listOf("ITEM000000", "ITEM000001", "ITEM000005")))

data class Item(val barcode: String, val name: String, val unit: String, val price: Double) {

}

fun loadAllItems(): List<Item> {
    return listOf(
        Item("ITEM000000", "可口可乐", "瓶", 3.00),
        Item("ITEM000001", "雪碧", "瓶", 3.00),
        Item("ITEM000002", "苹果", "斤", 5.50),
        Item("ITEM000003", "荔枝", "斤", 15.00),
        Item("ITEM000004", "电池", "个", 2.00),
        Item("ITEM000005", "方便面", "袋", 4.50)
    )
}

val purchasedBarcodes = listOf(
    "ITEM000001",
    "ITEM000001",
    "ITEM000001",
    "ITEM000001",
    "ITEM000001",
    "ITEM000003-2",
    "ITEM000005",
    "ITEM000005",
    "ITEM000005"
)

fun getReceipt(): String {
    return Pos.generateReceipt(purchasedBarcodes)
}

const val expectedReceipt = """
***<没钱赚商店>收据***
名称：雪碧，数量：5瓶，单价：3.00(元)，小计：12.0(元)
名称：荔枝，数量：2斤，单价：15.00(元)，小计：30.0(元)
名称：方便面，数量：3袋，单价：4.50(元)，小计：9.0(元)
----------------------
总计：51.00(元)
节省：7.50(元)
**********************
"""

object Pos {

    private const val HEAD_TEXT = "\n***<没钱赚商店>收据***\n"
    private const val ITEM_PRICE_TEMPLATE = "名称：%s，数量：%d%s，单价：%.2f(元)，小计：%.1f(元)\n"
    private const val BREAK_LINE = "----------------------\n"
    private const val TOTAL_PRICE_TEMPLATE = "总计：%.2f(元)\n"
    private const val DISCOUNT_PRICE_TEMPLATE = "节省：%.2f(元)\n"
    private const val FOOT_TEXT = "**********************\n"

    data class CartItem(
        val number: Int,
        val totalPrice: Double,
        val discountPrice: Double,
        val itemInfo: Item
    )

    fun generateReceipt(cartItems: List<String>): String {
        var totalPrice = 0.0
        var totalDiscount = 0.0
        val itemsInfo = countItemNumber(cartItems)

        var result = HEAD_TEXT

        for ((id, number) in itemsInfo) {
            val cartItem = calculatePrice(id, number)
            totalPrice += cartItem.totalPrice
            totalDiscount += cartItem.discountPrice
            result += getItemPrice(cartItem)
        }

        result += BREAK_LINE

        result += getTotalPrice(totalPrice - totalDiscount)
        result += getDiscountPrice(totalDiscount)

        result += FOOT_TEXT

        return result
    }

    private fun getItemPrice(item: CartItem) = item.run {
        String.format(
            ITEM_PRICE_TEMPLATE,
            itemInfo.name,
            number,
            itemInfo.unit,
            itemInfo.price,
            totalPrice - discountPrice
        )
    }

    private fun getTotalPrice(totalPrice: Double) = String.format(TOTAL_PRICE_TEMPLATE, totalPrice)

    private fun getDiscountPrice(discountPrice: Double) = String.format(DISCOUNT_PRICE_TEMPLATE, discountPrice)

    private fun calculatePrice(itemId: String, itemNumber: Int): CartItem {
        val cartItemInfo = loadAllItems().find {
            it.barcode == itemId
        } ?: throw IllegalArgumentException("Cart item not found")

        return CartItem(
            itemNumber,
            itemNumber * cartItemInfo.price,
            itemNumber / 3 * cartItemInfo.price,
            cartItemInfo
        )
    }

    private fun countItemNumber(cartItems: List<String>): Map<String, Int> {
        val result = LinkedHashMap<String, Int>()

        cartItems.forEach {
            val itemInfo = it.split('-')
            val itemId = itemInfo.getOrNull(0) ?: throw IllegalArgumentException("Invalid input")
            val itemNumber = (itemInfo.getOrNull(1) ?: "1").toInt()

            result[itemId] = result.getOrDefault(itemId, 0) + itemNumber
        }

        return result
    }
}