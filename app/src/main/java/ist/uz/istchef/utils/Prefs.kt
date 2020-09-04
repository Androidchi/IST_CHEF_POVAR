package ist.uz.istchef.utils

import com.orhanobut.hawk.Hawk
import ist.uz.istchef.App
import ist.uz.istchef.model.BasketModel

const val PREF_TOKEN = "pref_token"
const val PREF_USER_SETTINGS = "pref_user_settings"
const val PREF_FAVOURITE = "pref_favourites"
const val PREF_CART = "pref_cart"
const val PREF_CART_DATA = "pref_cart_data"
const val PREF_DISTRICT = "pref_district"
const val PREF_STORE = "pref_store"
const val PREF_DELIVERY = "pref_delivery"
const val PREF_LANG = "pref_lang"
const val PREF_FCM = "pref_fcm"
const val PREF_SERVER_DATA = "pref_server_data"

class Prefs {
    companion object {
        fun init(app: App) {
            Hawk.init(app).build();
        }

        fun getCartList(): List<BasketModel> {
            return Hawk.get(PREF_CART, emptyList())
        }

        fun add2Cart(item: BasketModel) {
            val items = getCartList().toMutableList()
            if (item.count == null) {
                var index = 0
                var removeIndex = -1
                items.forEach {
                    if (it.id == item.id) {
                        removeIndex = index

                    }
                    index++
                }
                if (removeIndex > -1) {
                    items.removeAt(removeIndex)
                }
            } else {
                var isHave = false
                items.forEach {
                    if (it.id == item.id) {
                        it.count = item.count
                        isHave = true
                    }
                }
                if (!isHave) {
                    items.add(0, item)
                }
            }
            Hawk.put(PREF_CART, items)
        }
    }
}